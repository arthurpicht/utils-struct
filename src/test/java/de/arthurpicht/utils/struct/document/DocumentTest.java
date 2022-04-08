package de.arthurpicht.utils.struct.document;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentTest {

    private Document createStringDocument1() {
        return new Document.Builder()
                .addString("line1")
                .addString("line2")
                .addString("line3")
                .build();
    }

    @Test
    void isEmpty_Pos() {
        Document document = new Document(new ArrayList<>());
        Assertions.assertTrue(document.isEmpty());
    }

    @Test
    void isEmpty_Neg() {
        Document document = createStringDocument1();
        Assertions.assertFalse(document.isEmpty());
    }

    @Test
    void getNrOfLines() {
        Document document = createStringDocument1();
        assertEquals(3, document.getNrOfLines());
    }

    @Test
    void getLineByIndex() {
        Document document = createStringDocument1();
        assertEquals("line1", document.getStringByIndex(0));
    }

    @Test
    void getLineByIndex_min() {
        Document document = createStringDocument1();
        assertEquals("line1", document.getStringByIndex(0));
    }

    @Test
    void getLineByIndex_max() {
        Document document = createStringDocument1();
        assertEquals("line3", document.getStringByIndex(2));
    }

    @Test
    void getLineByIndex_negIndexOutOfBoundsLower() {
        Document document = createStringDocument1();
        assertThrows(IllegalArgumentException.class, () -> document.getStringByIndex(-1));
    }

    @Test
    void getLineByIndex_negIndexOutOfBoundsUpper() {
        Document document = createStringDocument1();
        assertThrows(IllegalArgumentException.class, () -> document.getStringByIndex(3));
    }

    @Test
    void getLineByNumber() {
        Document document = createStringDocument1();
        assertEquals("line1", document.getStringByNumber(1));
    }

    @Test
    void getLineByNumber_min() {
        Document document = createStringDocument1();
        assertEquals("line1", document.getStringByNumber(1));
    }

    @Test
    void getLineByNumber_max() {
        Document document = createStringDocument1();
        assertEquals("line3", document.getStringByNumber(3));
    }

    @Test
    void getLineByNumber_negIndexOutOfBoundsLower() {
        Document document = createStringDocument1();
        assertThrows(IllegalArgumentException.class, () -> document.getStringByNumber(0));
    }

    @Test
    void getLineByNumber_negIndexOutOfBoundsUpper() {
        Document document = createStringDocument1();
        assertThrows(IllegalArgumentException.class, () -> document.getStringByNumber(4));
    }

}