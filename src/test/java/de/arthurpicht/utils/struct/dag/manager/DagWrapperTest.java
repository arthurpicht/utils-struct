package de.arthurpicht.utils.struct.dag.manager;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.struct.dag.AcyclicValidator;
import de.arthurpicht.utils.struct.dag.Dag;
import de.arthurpicht.utils.struct.dag.DagCycleException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DagWrapperTest {

    @Test
    void test() throws DagValidationException {

        HashMap<String, DagElement> dagElementMap = new HashMap<>();
        dagElementMap.put("A", new StringDagElement("A", Sets.newHashSet(), false));
        dagElementMap.put("B", new StringDagElement("B", new HashSet<>(), false));

        DagWrapper dagWrapper = new DagWrapper(dagElementMap);
        Dag<String> dag = dagWrapper.getDag();

        assertTrue(dag.contains("A"));
        assertTrue(dag.contains("B"));
        assertFalse(dag.contains("NN"));

        assertEquals(2, dag.getAllNodes().size());
        assertEquals(0, dag.getAllEdges().size());
    }

    @Test
    void withEdges() throws DagValidationException {

        HashMap<String, DagElement> dagElementMap = new HashMap<>();
        dagElementMap.put("A", new StringDagElement("A", Sets.newHashSet("B"), false));
        dagElementMap.put("B", new StringDagElement("B", Sets.newHashSet("C"), false));
        dagElementMap.put("C", new StringDagElement("C", Sets.newHashSet(), false));

        DagWrapper dagWrapper = new DagWrapper(dagElementMap);
        Dag<String> dag = dagWrapper.getDag();

        assertTrue(dag.contains("A"));
        assertTrue(dag.contains("B"));
        assertTrue(dag.contains("C"));
        assertFalse(dag.contains("NN"));

        assertTrue(dag.containsEdge("A", "B"));
        assertTrue(dag.containsEdge("B", "C"));

        assertEquals(3, dag.getAllNodes().size());
        assertEquals(2, dag.getAllEdges().size());
    }

    @Test
    void withEdgesCyclic() throws DagValidationException {

        HashMap<String, DagElement> dagElementMap = new HashMap<>();
        dagElementMap.put("A", new StringDagElement("A", Sets.newHashSet("B"), false));
        dagElementMap.put("B", new StringDagElement("B", Sets.newHashSet("C"), false));
        dagElementMap.put("C", new StringDagElement("C", Sets.newHashSet("A"), false));

        assertThrows(DagValidationException.class, () -> new DagWrapper(dagElementMap));
    }


}