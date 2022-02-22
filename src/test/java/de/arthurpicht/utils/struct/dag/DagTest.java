package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DagTest {

    @Test
    void simple() {

        Dag<String> dag = new DagBuilder<String>()
                .withNode("Düsseldorf")
                .withNode("Frankfurt")
                .withNode("Berlin")
                .withNode("Hamburg")
                .withEdge("Düsseldorf", "Frankfurt")
                .withEdge("Frankfurt", "Berlin")
                .withEdge("Frankfurt", "Hamburg")
                .build();

        Assertions.assertTrue(dag.isSource("Düsseldorf"));
        Assertions.assertFalse(dag.isSink("Düsseldorf"));

        Set<String> downstreamNodes = dag.getDownstreamNodes("Düsseldorf");
        assertEquals(1, downstreamNodes.size());
        assertTrue(downstreamNodes.contains("Frankfurt"));

        System.out.println(dag.getStringRepresentation("Düsseldorf"));

        System.out.println(dag.getStringRepresentation("Frankfurt"));
    }

}