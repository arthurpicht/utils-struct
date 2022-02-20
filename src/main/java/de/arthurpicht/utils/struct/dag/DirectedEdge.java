package de.arthurpicht.utils.struct.dag;

import java.util.Objects;

public class DirectedEdge<N> {

    private final N from;
    private final N to;

    public DirectedEdge(N from, N to) {
        if (from == null) throw new IllegalArgumentException("Parameter [from] is both null.");
        if (from == to) throw new IllegalArgumentException("Parameter [to] is both null.");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedEdge<?> that = (DirectedEdge<?>) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

}
