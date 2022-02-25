package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AcyclicValidatorTest {

    @Test
    void simplePos() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withEdge("A", "B")
                .withEdge("B", "A")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void simplePosCircle() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withNode("D")
                .withEdge("A", "B")
                .withEdge("B", "C")
                .withEdge("C", "D")
                .withEdge("D", "B")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void simpleTriangleWithCycle() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withEdge("A", "B")
                .withEdge("A", "C")
                .withEdge("C", "B")
                .withEdge("B", "C")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void treeCycle() {
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
                .withEdge("D1", "B")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void tree2Cycle() {
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
                .withEdge("C2", "A")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
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
                .withEdge("D2", "B2")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertFalse(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

}