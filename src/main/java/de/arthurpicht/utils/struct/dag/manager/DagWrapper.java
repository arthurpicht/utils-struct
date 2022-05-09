package de.arthurpicht.utils.struct.dag.manager;

import de.arthurpicht.utils.struct.dag.AcyclicValidator;
import de.arthurpicht.utils.struct.dag.Dag;
import de.arthurpicht.utils.struct.dag.DagBuilder;
import de.arthurpicht.utils.struct.dag.DagCycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DagWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DagWrapper.class);
    private final Dag<String> dag;
    private final Map<String, DagElement> dagElementMap;

    public DagWrapper(Map<String, DagElement> dagElementMap) throws DagValidationException {
        logger.trace("DagWrapper init");
        validateDependencyStructure(dagElementMap);
        logger.trace("DagWrapper validated.");
        Dag<String> dag = createDag(dagElementMap);
        validateAcyclicity(dag);

        this.dagElementMap = dagElementMap;
        this.dag = dag;
    }

    private void validateDependencyStructure(Map<String, DagElement> dagElementMap) throws DagValidationException {
        Set<String> dagElementIds = dagElementMap.keySet();
        for (String dagElementId : dagElementIds) {
            DagElement dagElement = dagElementMap.get(dagElementId);
            assertDependenciesArePreexisting(dagElement, dagElementMap);
        }
    }

    private void assertDependenciesArePreexisting(DagElement dagElement, Map<String, DagElement> dagElementMap) throws DagValidationException {
        for (String dependency : dagElement.getDependencies()) {
            if (!dagElementMap.containsKey(dependency))
                throw new DagValidationException(
                        "Dependency [" + dependency + "] of dagElement [" + dagElement.getId() + "] not specified.");
        }
    }

    private Dag<String> createDag(Map<String, DagElement> dagElementMap) {
        DagBuilder<String> dagBuilder = new DagBuilder<>();
        for (String dagElementId : dagElementMap.keySet()) {
            dagBuilder.withNode(dagElementId);
        }
        for (String dagElementId : dagElementMap.keySet()) {
            DagElement dagElement = dagElementMap.get(dagElementId);
            for (String dependency : dagElement.getDependencies()) {
                dagBuilder.withEdge(dagElementId, dependency);
            }
        }
        return dagBuilder.build();
    }

    private void validateAcyclicity(Dag<String> dag) throws DagValidationException {
        try {
            AcyclicValidator.validate(dag);
        } catch (DagCycleException e) {
            throw new DagValidationException(e.getMessage(), e);
        }
    }

    public Dag<String> getDag() {
        return this.dag;
    }

    public boolean hasDagElement(String dagElementId) {
        return this.dagElementMap.containsKey(dagElementId);
    }

    public DagElement getDagElement(String dagElementId) {
        return this.dagElementMap.get(dagElementId);
    }

    public Set<String> getDagElementIds() {
        return this.dagElementMap.keySet();
    }

    public Set<String> getEntryPointIds() {
        return this.dagElementMap.entrySet().stream()
                .filter(e -> e.getValue().isEntryPoint())
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

}
