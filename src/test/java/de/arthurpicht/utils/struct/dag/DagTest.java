package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DagTest {

    @Test
    void simple() {

        Dag<String> dag = new Dag<>();

        dag.addNode("Düsseldorf");
        dag.addNode("Frankfurt");
        dag.addNode("Berlin");
        dag.addNode("Hamburg");

        dag.addEdge("Düsseldorf", "Frankfurt");
        dag.addEdge("Frankfurt", "Berlin");
        dag.addEdge("Frankfurt", "Hamburg");

        Assertions.assertTrue(dag.isSource("Düsseldorf"));
        Assertions.assertFalse(dag.isSink("Düsseldorf"));

        Set<String> downstreamNodes = dag.getDownstreamNodes("Düsseldorf");
        assertEquals(1, downstreamNodes.size());
        assertTrue(downstreamNodes.contains("Frankfurt"));

        System.out.println(dag.getStringRepresentation("Düsseldorf"));

        System.out.println(dag.getStringRepresentation("Frankfurt"));
    }

}