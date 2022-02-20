package de.arthurpicht.utils.struct.dag;

import java.util.List;
import java.util.Set;

public class TopologicalSortValidator<N> {

    private final List<N> topologicalSort;
    private final Dag<N> dag;
    private boolean valid;
    private String message;

    public TopologicalSortValidator(List<N> topologicalSort, Dag<N> dag) {
        this.topologicalSort = topologicalSort;
        this.dag = dag;
        try {
            validate();
            this.valid = true;
            this.message = "";
        } catch (Exception e) {
            this.valid = false;
            this.message = e.getMessage();
        }
    }

    private void validate() throws Exception {
        for (N node : this.topologicalSort) {
            int nodeIndex = this.topologicalSort.indexOf(node);
            Set<N> downstreamNodes = this.dag.getDownstreamNodes(node);
            for (N downstreamNode : downstreamNodes) {
                if (!this.topologicalSort.contains(downstreamNode))
                    throw new Exception("Downstream node not found  of [" + node + "] -> [" + downstreamNode + "].");
                int downstreamNodeIndex = this.topologicalSort.indexOf(downstreamNode);
                if (nodeIndex >= downstreamNodeIndex)
                    throw new Exception("Illegal ordering: [" + node + "][" + nodeIndex + "] " +
                            "-> [" + downstreamNode + "][" + downstreamNodeIndex + "].");

            }
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public String getMessage() {
        return this.message;
    }

}
