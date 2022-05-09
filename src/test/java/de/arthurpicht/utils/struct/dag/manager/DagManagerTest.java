package de.arthurpicht.utils.struct.dag.manager;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.struct.dag.TopologicalSortValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DagManagerTest {

    private static DagManager dagManager;

    @BeforeAll
    static void createDagManager() throws DagValidationException {
        dagManager = new DagManagerBuilder()
                .withDagElement(new StringDagElement("A", Sets.newHashSet("B", "C"), true))
                .withDagElement(new StringDagElement("B", Sets.newHashSet("D"), false))
                .withDagElement(new StringDagElement("D", Sets.newHashSet(), false))
                .withDagElement(new StringDagElement("C", Sets.newHashSet("E", "F"), true))
                .withDagElement(new StringDagElement("E", Sets.newHashSet(), false))
                .withDagElement(new StringDagElement("F", Sets.newHashSet(), false))
                .build();
    }

    @Test
    void getDagElementIds() {
        assertEquals(Sets.newHashSet("A", "B", "C", "D", "E", "F"), dagManager.getDagElementIds());
    }

    @Test
    void getEntryPointIds() {
        Set<String> entryPointIds = dagManager.getEntryPointIds();
        assertEquals(Sets.newHashSet("A", "C"), entryPointIds);
    }

    @Test
    void hasDagElement() {
        assertTrue(dagManager.hasDagElement("A"));
        assertTrue(dagManager.hasDagElement("B"));
        assertFalse(dagManager.hasDagElement("NN"));
    }

    @Test
    void hasEntryPoint() {
        assertTrue(dagManager.hasEntryPoint("A"));
        assertFalse(dagManager.hasEntryPoint("NN"));
    }

    @Test
    void getDagElement() {
        DagElement dagElement = dagManager.getDagElement("A");
        StringDagElement stringDagElement = (StringDagElement) dagElement;
        assertEquals("A", stringDagElement.getId());
    }

    @Test
    void getDagElement_neg() {
        IllegalArgumentException e =
                assertThrows(IllegalArgumentException.class, () -> dagManager.getDagElement("NN"));
        assertTrue(e.getMessage().startsWith("No such Dag element"));
    }

    @Test
    void getElementIdsInTopologicalSort_root() {
        List<String> sort = dagManager.getElementIdsInTopologicalSort("A");
        System.out.println(Strings.listing(sort, ", "));
        TopologicalSortValidator<String> topologicalSortValidator = new TopologicalSortValidator<>(sort, dagManager.getDag());
        System.out.println(topologicalSortValidator.getMessage());
        assertTrue(topologicalSortValidator.isValid());
    }

    @Test
    void getElementIdsInTopologicalSort_nonRoot() {
        List<String> sort = dagManager.getElementIdsInTopologicalSort("C");
        System.out.println(Strings.listing(sort, ", "));
        TopologicalSortValidator<String> topologicalSortValidator = new TopologicalSortValidator<>(sort, dagManager.getDag());
        System.out.println(topologicalSortValidator.getMessage());
        assertTrue(topologicalSortValidator.isValid());
    }

    @Test
    void createDagManager_negDependentMissing() {
        DagValidationException e = assertThrows(DagValidationException.class, () -> new DagManagerBuilder()
                .withDagElement(new StringDagElement("A", Sets.newHashSet("B", "C"), true))
                .withDagElement(new StringDagElement("B", Sets.newHashSet("D"), false))
                .build());

        assertEquals("Dependency [C] of dagElement [A] not specified.", e.getMessage());
    }

    @Test
    void createDagManager_negCycle() {
        DagValidationException e = assertThrows(DagValidationException.class, () -> new DagManagerBuilder()
                .withDagElement(new StringDagElement("A", Sets.newHashSet("B"), true))
                .withDagElement(new StringDagElement("B", Sets.newHashSet("C"), false))
                .withDagElement(new StringDagElement("C", Sets.newHashSet("A"), false))
                .build());

        assertEquals("Dag is a cycle. No nodes without an upstream found.", e.getMessage());
    }

}