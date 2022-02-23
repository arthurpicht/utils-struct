package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class TopologicalSort<N> {

    private static class StackNode<N> {

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

        public NodeStatus getNodeStatus() {
            return nodeStatus;
        }

        @Override
        public String toString() {
            return this.node.toString() + ":" + this.nodeStatus.toString();
        }
    }

    private enum NodeStatus { NEW, IN_PROCESS, FINISHED };

    private final Dag<N> dag;
    private final N startNode;

    private final Set<Edge<N>> mutedEdges;
    private final List<N> topologicalSortedNodes;

    public static <N> List<N> get(Dag<N> dag, N startNode) {
        return new TopologicalSort<N>(dag, startNode).getTopologicalSortedNodes();
    }

    public TopologicalSort(Dag<N> dag, N startNode) {
        if (!dag.contains(startNode)) throw new UnknownDagNodeException(startNode);
        this.dag = dag;
        this.startNode = startNode;

        this.mutedEdges = new HashSet<>();
        this.topologicalSortedNodes = new ArrayList<>();

        sort();
    }

    private void sort() {
        Stack<StackNode<N>> nodesStack = new Stack<>();
        nodesStack.push(StackNode.createInstanceNew(this.startNode));
        StackNode<N> lastVisitedStackNode = null;

        int counter = 0;

        while (!nodesStack.empty()) {

            counter++;
            if (counter > 20) return;

            StackNode<N> currentStackNode = nodesStack.pop();
            N currentNode = currentStackNode.getNode();

            System.out.println("Process         : " + currentStackNode);

            Set<N> nodeOptions = getTraversalOptions(currentNode);

            System.out.println("nodeStack       : " + Strings.listing(Collections.list(nodesStack.elements()), ", "));
            System.out.println("currentNode     : " + currentNode);
            System.out.println("lastVisitedNode : " + lastVisitedStackNode);
            System.out.println("nodeOptions     : " + Strings.listing(nodeOptions, ", "));
            System.out.println("mutedEdges      : " + Strings.listing(this.mutedEdges, ", "));

            if (currentStackNode.nodeStatus == NodeStatus.NEW) {
                if (nodeOptions.isEmpty()) {
                    System.out.println("No downstream edges.");
                    addToSorted(currentNode);

                    Edge<N> edgeToMute = new Edge<>(lastVisitedStackNode.node, currentNode);
                    this.mutedEdges.add(edgeToMute);
                    System.out.println("mute edge       : " + edgeToMute);

                    lastVisitedStackNode = currentStackNode;
                } else {
                    System.out.println("With downstream edges.");
                    N someOption = Sets.getSomeElement(nodeOptions);
                    nodesStack.push(StackNode.createInstanceInProcess(currentNode));
                    nodesStack.push(StackNode.createInstanceNew(someOption));

                    System.out.println("push to stack   : " + currentNode + ":IN_PROCESS");
                    System.out.println("push to stack   : " + someOption + ":NEW");

                    lastVisitedStackNode = currentStackNode;
                }

            } else if (currentStackNode.nodeStatus == NodeStatus.IN_PROCESS){
                if (lastVisitedStackNode.nodeStatus == NodeStatus.FINISHED) {
                    System.out.println("Process finished Node: " + lastVisitedStackNode.node);
                    Edge<N> edge = new Edge<>(currentNode, lastVisitedStackNode.node);
                    this.mutedEdges.add(edge);
                    System.out.println("mute edge       : " + edge);
                    nodeOptions.remove(lastVisitedStackNode.node);
                }


                if (nodeOptions.isEmpty()) {
                    System.out.println("No downstream edges.");
                    addToSorted(currentNode);

                    Edge<N> edgeToMute = new Edge<>(currentNode, lastVisitedStackNode.node);
                    this.mutedEdges.add(edgeToMute);
                    System.out.println("mute edge       : " + edgeToMute);

                    lastVisitedStackNode = StackNode.createInstanceFinished(currentStackNode.node);
                } else {
                    System.out.println("With downstream edges.");

                    N someOption = Sets.getSomeElement(nodeOptions);
                    nodesStack.push(StackNode.createInstanceInProcess(currentNode));
                    nodesStack.push(StackNode.createInstanceNew(someOption));

                    System.out.println("push to stack   : " + currentNode + ":IN_PROCESS");
                    System.out.println("push to stack   : " + someOption + ":NEW");

                    lastVisitedStackNode = currentStackNode;
                }
            } else if (currentStackNode.nodeStatus == NodeStatus.FINISHED) {
                System.out.println("FINISHED!");
            }


            System.out.println("sorted nodes    : " + Strings.listing(this.topologicalSortedNodes, ", "));

            System.out.println("----------------------------------");

        }
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

    private Set<N> getTraversalOptions(N node) {
        return this.dag.getDownstreamNodes(node).stream()
                .map(n -> new Edge<>(node, n))
                .filter(n -> !this.mutedEdges.contains(n))
                .map(Edge::getTo)
                .collect(Collectors.toSet());
    }

}
