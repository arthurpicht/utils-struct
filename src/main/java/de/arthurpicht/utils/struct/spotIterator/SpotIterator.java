package de.arthurpicht.utils.struct.spotIterator;

public interface SpotIterator <E> {

    boolean hasNext();

    E getNext();

    void stepForward();

    boolean hasCurrent();

    E getCurrent();

    boolean hasPrevious();

    E getPrevious();

    void stepBack();
}
