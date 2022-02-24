package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class TopologicalSort<N> {

    private static final class StackNode<N> {

        private final N node;
        private final NodeStatus nodeStatus;

        public static <N> StackNode<N> createInstanceNew(N node) {
            return new StackNode<>(node, NodeStatus.NEW);
        }

        public static <N> StackNode<N> createInstanceInProcess(N node) {
            return new StackNode<>(node, NodeStatus.IN_PROCESS);
        }

        public static <N> StackNode<N> createInstanceFinished(N node) {
            return new StackNode<>(node, NodeStatus.FINISHED);
        }

        private StackNode(N node, NodeStatus nodeStatus) {
            this.node = node;
            this.nodeStatus = nodeStatus;
        }

        public N getNode() {
            return node;
        }

        @Override
        public String toString() {
            return this.node.toString() + ":" + this.nodeStatus.toString();
        }
    }

    private enum NodeStatus { NEW, IN_PROCESS, FINISHED }

    private final Dag<N> dag;
    private final N startNode;
    private final List<N> topologicalSortedNodes;

    private final Set<Edge<N>> mutedEdges;
    private final Stack<StackNode<N>> nodesStack;
    private StackNode<N> currentStackNode;
    private StackNode<N> lastVisitedStackNode;
    private N currentNode;

    public static <N> List<N> get(Dag<N> dag, N startNode) {
        return new TopologicalSort<N>(dag, startNode).getTopologicalSortedNodes();
    }

    public TopologicalSort(Dag<N> dag, N startNode) {
        if (!dag.contains(startNode)) throw new UnknownDagNodeException(startNode);
        this.dag = dag;
        this.startNode = startNode;

        this.mutedEdges = new HashSet<>();
        this.nodesStack = new Stack<>();
        this.lastVisitedStackNode = null;
        this.currentStackNode = null;
        this.currentNode = null;

        this.topologicalSortedNodes = new ArrayList<>();

        sort();
    }

    private void sort() {

        if (this.dag.getDownstreamNodes(this.startNode).isEmpty()) {
            this.topologicalSortedNodes.add(this.startNode);
            return;
        }

        nodesStack.push(StackNode.createInstanceNew(this.startNode));

        while (!nodesStack.empty()) {

            this.currentStackNode = nodesStack.pop();
            this.currentNode = currentStackNode.getNode();

            System.out.println("Process         : " + currentStackNode);

            Set<N> nodeOptions = getTraversalOptions();

            System.out.println("nodeStack       : " + Strings.listing(Collections.list(nodesStack.elements()), ", "));
            System.out.println("currentNode     : " + currentNode);
            System.out.println("lastVisitedNode : " + lastVisitedStackNode);
            System.out.println("nodeOptions     : " + Strings.listing(nodeOptions, ", "));
            System.out.println("mutedEdges      : " + Strings.listing(mutedEdges, ", "));

            if (currentStackNode.nodeStatus == NodeStatus.NEW) {

                if (nodeOptions.isEmpty()) {
                    processPrimarySink();
                } else {
                    processNodeWithDownstreamEdges(nodeOptions);
                }
                lastVisitedStackNode = currentStackNode;

            } else if (currentStackNode.nodeStatus == NodeStatus.IN_PROCESS){

                if (lastVisitedStackNode == null) throw new IllegalStateException();
                if (lastVisitedStackNode.nodeStatus == NodeStatus.FINISHED) {
                    processFinishedNode(nodeOptions);
                }

                if (nodeOptions.isEmpty()) {
                    processSecondarySink();
                    lastVisitedStackNode = StackNode.createInstanceFinished(currentStackNode.node);
                } else {
                    processNodeWithDownstreamEdges(nodeOptions);
                    lastVisitedStackNode = currentStackNode;
                }
            }


            System.out.println("sorted nodes    : " + Strings.listing(this.topologicalSortedNodes, ", "));
            System.out.println("----------------------------------");
        }
    }

    private void processPrimarySink() {
        processSink(false);
    }

    private void processSecondarySink() {
        processSink(true);
    }

    private void processSink(boolean backDirection) {
        System.out.println("No downstream edges.");
        addToSorted(this.currentNode);
        if (this.lastVisitedStackNode == null) throw new IllegalStateException();
        Edge<N> edgeToMute;
        if (backDirection) {
            edgeToMute = new Edge<>(currentNode, this.lastVisitedStackNode.node);
        } else {
            edgeToMute = new Edge<>(this.lastVisitedStackNode.node, currentNode);
        }
        this.mutedEdges.add(edgeToMute);
        System.out.println("mute edge       : " + edgeToMute);
    }

    private void processNodeWithDownstreamEdges(Set<N> nodeOptions) {
        System.out.println("With downstream edges.");
        N someOption = Sets.getSomeElement(nodeOptions);
        this.nodesStack.push(StackNode.createInstanceInProcess(this.currentNode));
        this.nodesStack.push(StackNode.createInstanceNew(someOption));

        System.out.println("push to stack   : " + this.currentNode + ":IN_PROCESS");
        System.out.println("push to stack   : " + someOption + ":NEW");
    }

    private void processFinishedNode(Set<N> nodeOptions) {
        System.out.println("Process finished Node: " + this.lastVisitedStackNode.node);
        Edge<N> edge = new Edge<>(this.currentNode, this.lastVisitedStackNode.node);
        this.mutedEdges.add(edge);
        System.out.println("mute edge       : " + edge);
        nodeOptions.remove(this.lastVisitedStackNode.node);
    }

    private void addToSorted(N node) {
        if (!this.topologicalSortedNodes.contains(node)) {
            this.topologicalSortedNodes.add(0, node);
            System.out.println("addToSorted     : " + node);
        }
    }

    public List<N> getTopologicalSortedNodes() {
        return Collections.unmodifiableList(this.topologicalSortedNodes);
    }

    private Set<N> getTraversalOptions() {
        return this.dag.getDownstreamNodes(this.currentNode).stream()
                .map(n -> new Edge<>(this.currentNode, n))
                .filter(n -> !this.mutedEdges.contains(n))
                .map(Edge::getTo)
                .collect(Collectors.toSet());
    }

}
