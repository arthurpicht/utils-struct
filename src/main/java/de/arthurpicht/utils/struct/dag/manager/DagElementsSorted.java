package de.arthurpicht.utils.struct.dag.manager;

import de.arthurpicht.utils.struct.dag.Dag;
import de.arthurpicht.utils.struct.dag.TopologicalSort;

import java.util.Collections;
import java.util.List;

public class DagElementsSorted {

    private final List<String> topologicalSort;

    public DagElementsSorted(DagWrapper dagWrapper, String entryPointId) {
        this.topologicalSort = initTopologicalSort(dagWrapper.getDag(), entryPointId);
    }

    public List<String> getTopologicalSort() {
        return this.topologicalSort;
    }

    private List<String> initTopologicalSort(Dag<String> dag, String entryPointId) {
        TopologicalSort<String> sort = new TopologicalSort<>(dag, entryPointId);
        List<String> dagElementsSorted = sort.getTopologicalSortedNodes();
        return Collections.unmodifiableList(dagElementsSorted);
    }

}
