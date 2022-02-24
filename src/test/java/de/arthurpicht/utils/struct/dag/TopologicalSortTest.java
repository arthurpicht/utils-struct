package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopologicalSortTest {

    private static <N> void check(List<N> topologicalSort, Dag<N> dag) {
        topologicalSort.forEach(System.out::println);

        TopologicalSortValidator<N> topologicalSortValidator = new TopologicalSortValidator<>(topologicalSort, dag);
        assertTrue(topologicalSortValidator.isValid());
        dag.getAllNodes()
                .forEach(n -> assertTrue(topologicalSort.contains(n), "topological sort contains all nodes."));
        assertEquals(dag.getAllNodes().size(), topologicalSort.size());
    }

    @Test
    void simpleTree() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withEdge("A", "B")
                .withEdge("A", "C")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void simpleTriangleReversed() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withEdge("A", "B")
                .withEdge("A", "C")
                .withEdge("C", "B")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void simpleTriangle() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withEdge("A", "B")
                .withEdge("A", "C")
                .withEdge("B", "C")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void tree() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C1")
                .withNode("C2")
                .withNode("D1")
                .withNode("D2")
                .withEdge("A", "B")
                .withEdge("B", "C1")
                .withEdge("B", "C2")
                .withEdge("C1", "D1")
                .withEdge("C1", "D2")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void tree2() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B1")
                .withNode("B2")
                .withNode("C1")
                .withNode("C2")
                .withNode("C3")
                .withNode("D1")
                .withNode("D2")
                .withEdge("A", "B1")
                .withEdge("A", "B2")
                .withEdge("B1", "C1")
                .withEdge("B1", "C2")
                .withEdge("B1", "C3")
                .withEdge("C2", "D1")
                .withEdge("C2", "D2")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void tree2WithShortcut() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B1")
                .withNode("B2")
                .withNode("C1")
                .withNode("C2")
                .withNode("C3")
                .withNode("D1")
                .withNode("D2")
                .withEdge("A", "B1")
                .withEdge("A", "B2")
                .withEdge("B1", "C1")
                .withEdge("B1", "C2")
                .withEdge("B1", "C3")
                .withEdge("C2", "D1")
                .withEdge("C2", "D2")
                .withEdge("B2", "D2")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void tree2WithShortcutReverse() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B1")
                .withNode("B2")
                .withNode("C1")
                .withNode("C2")
                .withNode("C3")
                .withNode("D1")
                .withNode("D2")
                .withEdge("A", "B1")
                .withEdge("A", "B2")
                .withEdge("B2", "C1")
                .withEdge("B2", "C2")
                .withEdge("B2", "C3")
                .withEdge("C2", "D1")
                .withEdge("C2", "D2")
                .withEdge("B1", "D2")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void justOneNode() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }

    @Test
    void twoNodesChain() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withEdge("A", "B")
                .build();

        List<String> topologicalSortedNodes = TopologicalSort.get(dag, "A");
        check(topologicalSortedNodes, dag);
    }


}