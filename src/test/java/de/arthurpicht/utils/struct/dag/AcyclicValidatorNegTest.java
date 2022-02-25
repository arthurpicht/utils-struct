package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AcyclicValidatorNegTest {

    @Test
    void simpleTree() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withNode("C")
                .withEdge("A", "B")
                .withEdge("A", "C")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
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

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
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

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
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

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
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

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
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
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
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

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void justOneNode() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

    @Test
    void twoNodesChain() {
        Dag<String> dag = new DagBuilder<String>()
                .withNode("A")
                .withNode("B")
                .withEdge("A", "B")
                .build();

        AcyclicValidator<String> validator = new AcyclicValidator<>(dag, "A");
        assertTrue(validator.isAcyclic());
        System.out.println(validator.getMessage());
    }

}