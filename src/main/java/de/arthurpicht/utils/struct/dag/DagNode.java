package de.arthurpicht.utils.struct.dag;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DagNode<N> {

    private final N content;

    public Set<DagNode<N>> downstreamNodes;
    public Set<DagNode<N>> upstreamNodes;

    public DagNode(N content) {
        this.content = content;
        this.downstreamNodes = new HashSet<>();
        this.upstreamNodes = new HashSet<>();
    }

    public N getContent() {
        return this.content;
    }

    public void addDownstreamNode(DagNode<N> downstreamNode) {
        this.downstreamNodes.add(downstreamNode);
    }

    public void addUpstreamNodes(DagNode<N> upstreamNode) {
        this.upstreamNodes.add(upstreamNode);
    }

    public Set<DagNode<N>> getDownstreamNodes() {
        return this.downstreamNodes;
    }

    public Set<DagNode<N>> getUpstreamNodes() {
        return this.upstreamNodes;
    }

    public boolean isSource() {
        return this.upstreamNodes.isEmpty();
    }

    public boolean isSink() {
        return this.downstreamNodes.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DagNode<?> dagNode = (DagNode<?>) o;
        return content.equals(dagNode.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    public String toString() {
        String string = DagNode.class.getSimpleName() + " [" + this.content.toString() + "]";

        Set<String> upstreamStrings = getUpstreamNodes().stream()
                .map(DagNode::getContent)
                .map(Objects::toString)
                .collect(Collectors.toUnmodifiableSet());

        string += "\n    Upstream: " + Strings.listing(upstreamStrings, ", ", "{", "}", "[", "]");

        Set<String> downstreamStrings = getDownstreamNodes().stream()
                .map(DagNode::getContent)
                .map(Objects::toString)
                .collect(Collectors.toUnmodifiableSet());

        string += "\n    Downstream: " + Strings.listing(downstreamStrings, ", ", "{", "}", "[", "]");

        return string;
    }

}
