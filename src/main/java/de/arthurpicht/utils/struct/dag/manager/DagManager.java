package de.arthurpicht.utils.struct.dag.manager;

import de.arthurpicht.utils.struct.dag.Dag;

import java.util.*;

/**
 * This class in conjunction with {@link DagManagerBuilder} is a high level all-in-one API for facilitating DAGs.
 * This includes:
 * <ul>
 *     <li>Creation</li>
 *     <li>Validation</li>
 *     <li>Preprocessing of topological sorts for all sub-DAGs with root nodes marked as entryPoints.</li>
 * </ul>
 * As a precondition for using this functionality, all DAG elements must implement the {@link DagElement} interface.
 *
 */
public class DagManager {

    private final DagWrapper dagWrapper;
    private final Map<String, DagElementsSorted> entryPointMap;

    public DagManager(DagWrapper dagWrapper) {
        this.dagWrapper = dagWrapper;
        this.entryPointMap = createEntryPointMap(dagWrapper);
    }

    private Map<String, DagElementsSorted> createEntryPointMap(DagWrapper dagWrapper) {
        Map<String, DagElementsSorted> entryPointMap = new LinkedHashMap<>();
        Set<String> entryPointIds = dagWrapper.getEntryPointIds();
        for (String entryPoint : entryPointIds) {
            DagElementsSorted dagElementsSorted = new DagElementsSorted(dagWrapper, entryPoint);
            entryPointMap.put(entryPoint, dagElementsSorted);
        }
        return entryPointMap;
    }

    public Dag<String> getDag() {
        return this.dagWrapper.getDag();
    }

    public Set<String> getEntryPointIds() {
        Set<String> targets = this.entryPointMap.keySet();
        return Collections.unmodifiableSet(targets);
    }

    public boolean hasEntryPoint(String entryPointId) {
        return this.entryPointMap.containsKey(entryPointId);
    }

    public List<String> getElementIdsInTopologicalSort(String entryPointId) {
        if (!this.entryPointMap.containsKey(entryPointId))
            throw new IllegalArgumentException("No such entryPointId in " + DagManager.class.getSimpleName()
                    + ": [" + entryPointId + "].");
        return this.entryPointMap.get(entryPointId).getTopologicalSort();
    }

    public boolean hasDagElement(String dagElementId) {
        return this.dagWrapper.hasDagElement(dagElementId);
    }

    public Set<String> getDagElementIds() {
        return this.dagWrapper.getDagElementIds();
    }

    public DagElement getDagElement(String dagElementId) {
        if (!this.dagWrapper.hasDagElement(dagElementId))
            throw new IllegalArgumentException("No such Dag element in " + DagManager.class.getSimpleName()
                    + ": [" + dagElementId + "}.");
        return this.dagWrapper.getDagElement(dagElementId);
    }

}
