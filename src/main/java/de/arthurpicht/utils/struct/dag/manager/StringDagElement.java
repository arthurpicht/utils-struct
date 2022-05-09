package de.arthurpicht.utils.struct.dag.manager;

import java.util.Collections;
import java.util.Set;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;
import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNullAndNotEmpty;

/**
 * Implementation of the {@link DagElement} interface with a String object as payload.
 */
public class StringDagElement implements DagElement {

    private final String string;
    private final Set<String> dependencies;
    private final boolean isEntryPoint;

    public StringDagElement(String string, Set<String> dependencies, boolean isEntryPoint) {
        assertArgumentNotNullAndNotEmpty("string", string);
        assertArgumentNotNull("dependencies", dependencies);

        this.string = string;
        this.dependencies = Collections.unmodifiableSet(dependencies);
        this.isEntryPoint = isEntryPoint;
    }

    @Override
    public String getId() {
        return this.string;
    }

    @Override
    public Set<String> getDependencies() {
        return this.dependencies;
    }

    @Override
    public boolean isEntryPoint() {
        return this.isEntryPoint;
    }

}
