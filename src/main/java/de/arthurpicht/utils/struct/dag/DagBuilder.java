package de.arthurpicht.utils.struct.dag;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

public final class DagBuilder<N> {

    private final Map<N, DagNode<N>> dagNodeMap;
    private final Set<Edge<N>> edgeSet;

    public DagBuilder() {
        this.dagNodeMap = new LinkedHashMap<>();
        this.edgeSet = new LinkedHashSet<>();
    }

    public DagBuilder<N> withNode(N node) {
        assertArgumentNotNull("node", node);
        if (this.dagNodeMap.containsKey(node)) throw new PreexistingDagNodeException(node);
        DagNode<N> dagNode = new DagNode<>(node);
        this.dagNodeMap.put(node, dagNode);
        return this;
    }

    public DagBuilder<N> withEdge(N fromNode, N toNode) {
        assertArgumentNotNull("fromNode", fromNode);
        assertArgumentNotNull("toNode", toNode);
        DagNode<N> dagNodeFrom = getDagNode(fromNode);
        DagNode<N> dagNodeTo = getDagNode(toNode);
        dagNodeFrom.addDownstreamNode(dagNodeTo);
        dagNodeTo.addUpstreamNodes(dagNodeFrom);
        this.edgeSet.add(new Edge<>(fromNode, toNode));
        return this;
    }

    public Dag<N> build() {
        return new Dag<>(this.dagNodeMap, this.edgeSet);
    }

    private DagNode<N> getDagNode(N node) {
        if (!this.dagNodeMap.containsKey(node)) throw new UnknownDagNodeException(node);
        return this.dagNodeMap.get(node);
    }

}
