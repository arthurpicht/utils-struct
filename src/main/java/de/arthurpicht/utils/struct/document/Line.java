package de.arthurpicht.utils.struct.document;

import java.util.Objects;

public class Line {

    private final String line;
    private final int lineIndex;

    public Line(int lineIndex, String line) {
        this.lineIndex = lineIndex;
        this.line = line;
    }

    public String asString() {
        return this.line;
    }

    /**
     * Returns the index value of that line.
     * @return
     */
    public int getLineIndex() {
        return this.lineIndex;
    }

    public int getLineNr() {
        return this.lineIndex + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line1 = (Line) o;
        return lineIndex == line1.lineIndex &&
                line.equals(line1.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, lineIndex);
    }

    @Override
    public String toString() {
        return "[i=" + this.lineIndex + "] " + this.line;
    }

}
