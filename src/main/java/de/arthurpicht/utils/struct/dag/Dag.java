package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.collection.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Dag<N> {

    private final Map<N, DagNode<N>> dagNodeMap;
    private final Set<Edge<N>> edgeSet;

    public Dag(Map<N, DagNode<N>> dagNodeMap, Set<Edge<N>> edgeSet) {
        this.dagNodeMap = Maps.immutableMap(dagNodeMap);
        this.edgeSet = Collections.unmodifiableSet(edgeSet);
    }

    public Set<N> getAllNodes() {
        return this.dagNodeMap.keySet();
    }

    public int getNrOfNodes() {
        return this.dagNodeMap.size();
    }

    public Set<Edge<N>> getAllEdges() {
        return this.edgeSet;
    }

    public int getNrOfEdges() {
        return this.edgeSet.size();
    }

    public Set<N> getDownstreamNodes(N node) {
        DagNode<N> dagNode = getDagNode(node);
        Set<DagNode<N>> downStreamDagNodes = dagNode.getDownstreamNodes();
        return mapToContent(downStreamDagNodes);
    }

    public Set<N> getUpstreamNodes(N node) {
        DagNode<N> dagNode = getDagNode(node);
        Set<DagNode<N>> upstreamDagNodes = dagNode.getUpstreamNodes();
        return mapToContent(upstreamDagNodes);
    }

    public boolean isSource(N node) {
        DagNode<N> dagNode = getDagNode(node);
        return dagNode.isSource();
    }

    public boolean isSink(N node) {
        DagNode<N> dagNode = getDagNode(node);
        return dagNode.isSink();
    }

    public String getStringRepresentation(N node) {
        DagNode<N> dagNode = getDagNode(node);
        return dagNode.toString();
    }

    public boolean contains(N node) {
        return this.dagNodeMap.containsKey(node);
    }

    private DagNode<N> getDagNode(N node) {
        if (!this.dagNodeMap.containsKey(node)) throw new UnknownDagNodeException(node);
        return this.dagNodeMap.get(node);
    }

    private Set<N>mapToContent(Set<DagNode<N>>dagNodes) {
        return dagNodes.stream().map(DagNode::getContent).collect(Collectors.toUnmodifiableSet());
    }

    public boolean containsEdge(N from, N to) {
        return this.edgeSet.contains(new Edge<>(from, to));
    }

}
