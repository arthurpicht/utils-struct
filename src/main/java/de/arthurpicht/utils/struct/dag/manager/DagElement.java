package de.arthurpicht.utils.struct.dag.manager;

import java.util.Set;

/**
 * Interface for DAG elements. Each element must contain an ID that is unique over all elements in the DAG.
 * Elements marked as an entry point will be considered as root nodes of a sub-DAG for which topological sorts
 * will per performed.
 *
 */
public interface DagElement {

    String getId();

    Set<String> getDependencies();

    boolean isEntryPoint();

}
