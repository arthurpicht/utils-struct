package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import java.util.List;

class TopologicalSortTest {

    @Test
    void simple() {

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

        TopologicalSort<String> topologicalSort = new TopologicalSort<>(dag, "A");
        List<String> topologicalSortedNodes = topologicalSort.getTopologicalSortedNodes();

        topologicalSortedNodes.forEach(System.out::println);

    }

}