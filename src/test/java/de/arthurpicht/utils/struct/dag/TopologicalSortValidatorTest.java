package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortValidatorTest {

    private Dag<String> createDag() {
        Dag<String> dag = new Dag<>();

        dag.addNode("Düsseldorf");
        dag.addNode("Frankfurt");
        dag.addNode("Berlin");
        dag.addNode("Hamburg");

        dag.addEdge("Düsseldorf", "Frankfurt");
        dag.addEdge("Frankfurt", "Berlin");
        dag.addEdge("Frankfurt", "Hamburg");

        return dag;
    }

    @Test
    void positive() {
        Dag<String> dag = createDag();
        List<String> topologicalSort = Arrays.asList("Düsseldorf", "Frankfurt", "Berlin", "Hamburg");

        TopologicalSortValidator<String> topologicalSortValidator =
                new TopologicalSortValidator<>(topologicalSort, dag);

        assertTrue(topologicalSortValidator.isValid());
    }

    @Test
    void negMissing() {
        Dag<String> dag = createDag();
        List<String> topologicalSort = Arrays.asList("Düsseldorf", "Frankfurt", "Berlin");

        TopologicalSortValidator<String> topologicalSortValidator =
                new TopologicalSortValidator<>(topologicalSort, dag);

        assertFalse(topologicalSortValidator.isValid());
        assertEquals("Downstream node not found  of [Frankfurt] -> [Hamburg].", topologicalSortValidator.getMessage());
    }

    @Test
    void negOrder() {
        Dag<String> dag = createDag();
        List<String> topologicalSort = Arrays.asList("Düsseldorf", "Hamburg", "Frankfurt", "Berlin");

        TopologicalSortValidator<String> topologicalSortValidator =
                new TopologicalSortValidator<>(topologicalSort, dag);

        assertFalse(topologicalSortValidator.isValid());
        assertEquals("Illegal ordering: [Frankfurt][2] -> [Hamburg][1].", topologicalSortValidator.getMessage());
    }

}