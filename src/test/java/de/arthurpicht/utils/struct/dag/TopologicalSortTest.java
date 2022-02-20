package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import java.util.List;

class TopologicalSortTest {

    @Test
    void simple() {

        Dag<String> dag = new Dag<>();

        dag.addNode("A");
        dag.addNode("B");
        dag.addNode("C1");
        dag.addNode("C2");
        dag.addNode("D1");
        dag.addNode("D2");

        dag.addEdge("A", "B");
        dag.addEdge("B", "C1");
        dag.addEdge("B", "C2");
        dag.addEdge("C1", "D1");
        dag.addEdge("C1", "D2");

        TopologicalSort<String> topologicalSort = new TopologicalSort<>(dag, "A");
        List<String> topologicalSortedNodes = topologicalSort.getTopologicalSortedNodes();

        topologicalSortedNodes.forEach(System.out::println);

    }

}