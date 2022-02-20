package de.arthurpicht.utils.struct.dag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dag<N> {

    private final Map<N, DagNode<N>> dagNodeMap;

    public Dag() {
        this.dagNodeMap = new HashMap<>();
    }

    public void addNode(N node) {
        if (this.dagNodeMap.containsKey(node)) throw new PreexistingDagNodeException(node);
        DagNode<N> dagNode = new DagNode<>(node);
        this.dagNodeMap.put(node, dagNode);
    }

    public void addEdge(N fromNode, N toNode) {
        DagNode<N> dagNodeFrom = getDagNode(fromNode);
        DagNode<N> dagNodeTo = getDagNode(toNode);
        dagNodeFrom.addDownstreamNode(dagNodeTo);
        dagNodeTo.addUpstreamNodes(dagNodeFrom);
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

}
