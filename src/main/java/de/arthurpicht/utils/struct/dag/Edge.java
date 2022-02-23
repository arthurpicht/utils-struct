package de.arthurpicht.utils.struct.dag;

import java.util.Objects;

import static de.arthurpicht.utils.core.assertion.AssertMethodPrecondition.parameterNotNull;

public class Edge<N> {

    private final N from;
    private final N to;

    public Edge(N from, N to) {
        parameterNotNull("from", from);
        parameterNotNull("to", to);
        this.from = from;
        this.to = to;
    }

    public N getFrom() {
        return this.from;
    }

    public N getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return Edge.class.getSimpleName() + " [" + this.from + "] -> [" + this.to + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> that = (Edge<?>) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

}
