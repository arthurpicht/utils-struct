package de.arthurpicht.utils.struct.dag;

public class UnknownDagNodeException extends RuntimeException {

    public <N> UnknownDagNodeException(N node) {
        super("Unknown node: [" + node.toString() + "].");
    }

}
