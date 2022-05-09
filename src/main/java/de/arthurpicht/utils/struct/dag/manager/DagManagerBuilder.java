package de.arthurpicht.utils.struct.dag.manager;

import java.util.LinkedHashMap;
import java.util.Map;

public class DagManagerBuilder {

    private final Map<String, DagElement> dagElementMap;

    public DagManagerBuilder() {
        this.dagElementMap = new LinkedHashMap<>();
    }

    public DagManagerBuilder withDagElement(DagElement dagElement) throws DagValidationException {
        assertDagElementNotPreexisting(dagElement);
        this.dagElementMap.put(dagElement.getId(), dagElement);
        return this;
    }

    public DagManager build() throws DagValidationException {
        DagWrapper dagWrapper = new DagWrapper(this.dagElementMap);
        return new DagManager(dagWrapper);
    }

    private void assertDagElementNotPreexisting(DagElement dagElement) throws DagValidationException {
        if (this.dagElementMap.containsKey(dagElement.getId()))
            throw new DagValidationException("DagElement already registered [" + dagElement.getId() + "].");
    }

}
