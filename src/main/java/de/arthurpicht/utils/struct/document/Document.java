package de.arthurpicht.utils.struct.document;

import de.arthurpicht.utils.core.assertion.MethodPreconditions;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class Document implements DocumentInterface {

    private final List<String> strings;
    private final List<Line> lines;

    public static class Builder {

        private final List<String> strings;

        public Builder() {
            this.strings = new ArrayList<>();
        }

        public Builder addString(String line) {
            this.strings.add(line);
            return this;
        }

        public Document build() {
            return new Document(this.strings);
        }
    }

    public Document(List<String> strings) {
        MethodPreconditions.assertArgumentNotNull("lines", strings);
        this.strings = Collections.unmodifiableList(strings);
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < this.strings.size(); i++) {
            lines.add(new Line(i, this.strings.get(i)));
        }
        this.lines = Collections.unmodifiableList(lines);
    }

    @Override
    public List<String> getStrings() {
        return this.strings;
    }

    @Override
    public List<Line> getLines() {
        return this.lines;
    }

    public String asString() {
        return Strings.listing(this.strings, "\n");
    }

    @Override
    public boolean isEmpty() {
        return this.strings.isEmpty();
    }

    @Override
    public int getNrOfLines() {
        return this.strings.size();
    }

    @Override
    public Iterator<String> iterator() {
        return this.strings.iterator();
    }

    @Override
    public String getStringByIndex(int index) {
        if (index < 0) throw new IllegalArgumentException("Index out of bounds. Specified index < 0.");
        if (this.strings.size() <= index) throw new IllegalArgumentException("Index out of bounds. " +
                "Specified index=" + index + ". Size=" + this.strings.size() + ".");
        return this.strings.get(index);
    }

    @Override
    public String getStringByNumber(int number) {
        if (number < 1) throw new IllegalArgumentException("Index out of bounds. Specified number < 1.");
        if (this.lines.size() < number) throw new IllegalArgumentException("Index out of bounds. " +
                "Specified number = " + number + ". Size=" + this.lines.size() + ".");
        return this.strings.get(number - 1);
    }

    @Override
    public Line getLineByIndex(int index) {
        if (index < 0) throw new IllegalArgumentException("Index out of bounds. Specified index < 0.");
        if (this.lines.size() <= index) throw new IllegalArgumentException("Index out of bounds. " +
                "Specified index=" + index + ". Size=" + this.lines.size() + ".");
        return this.lines.get(index);
    }

    @Override
    public Line getLineByNumber(int number) {
        if (number < 1) throw new IllegalArgumentException("Index out of bounds. Specified number < 1.");
        if (this.lines.size() < number) throw new IllegalArgumentException("Index out of bounds. " +
                "Specified number = " + number + ". Size=" + this.lines.size() + ".");
        return this.lines.get(number - 1);
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document that = (Document) o;
        return strings.equals(that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }

}
