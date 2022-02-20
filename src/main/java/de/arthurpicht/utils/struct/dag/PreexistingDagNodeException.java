package de.arthurpicht.utils.struct.dag;

public class PreexistingDagNodeException extends RuntimeException {

    public <N> PreexistingDagNodeException(N node) {
        super("Node is already existing: [" + node.toString() + "].");
    }

}
