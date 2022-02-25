package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class AcyclicValidator<N> {

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

    private static final class CycleException extends Exception {
        public CycleException() {
            super();
        }
    }


    private final Logger logger = LoggerFactory.getLogger(AcyclicValidator.class);

    private enum NodeStatus { NEW, IN_PROCESS, FINISHED }

    private final Dag<N> dag;
    private final N startNode;
    private boolean acyclic;
    private List<N> cycleNodeList;
    private String message;

    private final Set<Edge<N>> mutedEdges;
    private final Stack<StackNode<N>> nodesStack;
    private StackNode<N> currentStackNode;
    private StackNode<N> lastVisitedStackNode;
    private N currentNode;

    public static <N> boolean validate(Dag<N> dag, N startNode) {
        return new AcyclicValidator<>(dag, startNode).isAcyclic();
    }

    public AcyclicValidator(Dag<N> dag, N startNode) {
        if (!dag.contains(startNode)) throw new UnknownDagNodeException(startNode);
        this.dag = dag;
        this.startNode = startNode;

        this.mutedEdges = new HashSet<>();
        this.nodesStack = new Stack<>();
        this.lastVisitedStackNode = null;
        this.currentStackNode = null;
        this.currentNode = null;

        this.acyclic = true;
        this.cycleNodeList = new ArrayList<>();
        this.message = "";

        try {
            sort();
        } catch (CycleException e) {
            this.acyclic = false;
        }
    }

    private void sort() throws CycleException {

        if (this.dag.getDownstreamNodes(this.startNode).isEmpty()) {
            return;
        }

        nodesStack.push(StackNode.createInstanceNew(this.startNode));

        while (!nodesStack.empty()) {

            this.currentStackNode = nodesStack.pop();
            this.currentNode = currentStackNode.getNode();

            logger.trace("Process         : " + currentStackNode);

            Set<N> nodeOptions = getTraversalOptions();

            logger.trace("nodeStack       : " + Strings.listing(Collections.list(nodesStack.elements()), ", "));
            logger.trace("currentNode     : " + currentNode);
            logger.trace("lastVisitedNode : " + lastVisitedStackNode);
            logger.trace("nodeOptions     : " + Strings.listing(nodeOptions, ", "));
            logger.trace("mutedEdges      : " + Strings.listing(mutedEdges, ", "));

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

            logger.trace("----------------------------------");
        }
    }

    private void processPrimarySink() {
        processSink(false);
    }

    private void processSecondarySink() {
        processSink(true);
    }

    private void processSink(boolean backDirection) {
        logger.trace("No downstream edges.");
        if (this.lastVisitedStackNode == null) throw new IllegalStateException();
        Edge<N> edgeToMute;
        if (backDirection) {
            edgeToMute = new Edge<>(currentNode, this.lastVisitedStackNode.node);
        } else {
            edgeToMute = new Edge<>(this.lastVisitedStackNode.node, currentNode);
        }
        this.mutedEdges.add(edgeToMute);
        logger.trace("mute edge       : " + edgeToMute);
    }

    private void processNodeWithDownstreamEdges(Set<N> nodeOptions) throws CycleException {
        logger.trace("With downstream edges.");
        N someOption = Sets.getSomeElement(nodeOptions);

        checkForCycle(someOption);

        this.nodesStack.push(StackNode.createInstanceInProcess(this.currentNode));
        this.nodesStack.push(StackNode.createInstanceNew(someOption));

        logger.trace("push to stack   : " + this.currentNode + ":IN_PROCESS");
        logger.trace("push to stack   : " + someOption + ":NEW");
    }

    private void processFinishedNode(Set<N> nodeOptions) {
        logger.trace("Process finished Node: " + this.lastVisitedStackNode.node);
        Edge<N> edge = new Edge<>(this.currentNode, this.lastVisitedStackNode.node);
        this.mutedEdges.add(edge);
        logger.trace("mute edge       : " + edge);
        nodeOptions.remove(this.lastVisitedStackNode.node);
    }

    private Set<N> getTraversalOptions() {
        return this.dag.getDownstreamNodes(this.currentNode).stream()
                .map(n -> new Edge<>(this.currentNode, n))
                .filter(n -> !this.mutedEdges.contains(n))
                .map(Edge::getTo)
                .collect(Collectors.toSet());
    }

    private void checkForCycle(N node) throws CycleException {
        List<N> stackAsNodeList = this.nodesStack.stream()
                .map(StackNode::getNode)
                .collect(Collectors.toUnmodifiableList());
        if (stackAsNodeList.contains(node)) {
            int index = stackAsNodeList.indexOf(node);
            this.cycleNodeList = Utils.sublist(stackAsNodeList, index);
            this.cycleNodeList.add(this.currentNode);
            this.cycleNodeList.add(this.cycleNodeList.get(0));
            this.message = "cycle: " + Strings.listing(this.cycleNodeList, ", ");
            throw new CycleException();
        }
    }

    public boolean isAcyclic() {
        return this.acyclic;
    }

    public String getMessage() {
        return this.message;
    }

    public List<N> getCycleNodeList() {
        return Collections.unmodifiableList(this.cycleNodeList);
    }

}
