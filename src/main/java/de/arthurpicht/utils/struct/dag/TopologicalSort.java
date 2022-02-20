package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.collection.Sets;

import java.util.*;
import java.util.stream.Collectors;

public class TopologicalSort<N> {

    private final Dag<N> dag;
    private final N startNode;

    private final Set<N> visitedNodes;
    private final Stack<N> nodesToBeRevisited;
    private final List<N> topologicalSortedNodes;

    public TopologicalSort(Dag<N> dag, N startNode) {
        if (!dag.contains(startNode)) throw new UnknownDagNodeException(startNode);
        this.dag = dag;
        this.startNode = startNode;

        this.visitedNodes = new HashSet<>();
        this.nodesToBeRevisited = new Stack<>();
        this.topologicalSortedNodes = new ArrayList<>();

        sort();
    }

    private void sort() {
        Stack<N> nodesStack = new Stack<>();
        nodesStack.push(this.startNode);

        while (!nodesStack.empty()) {
            N currentNode = nodesStack.pop();
            Set<N> nodeOptions = getTraversalOptions(currentNode);
            nodeOptions.forEach(nodesStack::push);
            this.visitedNodes.add(currentNode);
//            this.topologicalSortedNodes.add(currentNode);
            insertNodeToTopologicalSort(currentNode);
        }
    }

    private void insertNodeToTopologicalSort(N node) {
        if (this.dag.isSource(node)) {
            this.topologicalSortedNodes.add(node);
        } else {
            Set<N> upstreamNodes = this.dag.getUpstreamNodes(node);
            N anyUpstreamNode = Sets.getSomeElement(upstreamNodes);
            int i = this.topologicalSortedNodes.indexOf(anyUpstreamNode);
            this.topologicalSortedNodes.add(i + 1, node);
        }


    }

    public List<N> getTopologicalSortedNodes() {
        return this.topologicalSortedNodes;
    }

//    private void goDeep(N node) {
//        N currentNode = node;
//        while (true) {
//            Set<N> nodeOptions = getTraversalOptions(currentNode);
//            if (nodeOptions.isEmpty()) return;
//            currentNode = Sets.getSomeElement(nodeOptions);
//        }
//    }

    private Set<N> getTraversalOptions(N node) {
        return this.dag.getDownstreamNodes(node).stream()
                .filter(n -> !this.visitedNodes.contains(n))
                .collect(Collectors.toUnmodifiableSet());
    }



}
