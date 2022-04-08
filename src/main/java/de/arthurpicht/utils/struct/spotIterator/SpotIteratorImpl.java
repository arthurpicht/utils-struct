package de.arthurpicht.utils.struct.spotIterator;

import java.util.Collections;
import java.util.List;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

/**
 * Draft.
 *
 * @param <E>
 */
public class SpotIteratorImpl<E> implements SpotIterator<E> {

    private final List<E> elementList;
    private int index;

    public SpotIteratorImpl(List<E> elementList) {
        assertArgumentNotNull("elementList", elementList);
        this.elementList = Collections.unmodifiableList(elementList);
        this.index = -1;
    }

    @Override
    public boolean hasNext() {
        return (this.elementList.size() - 1 > this.index);
    }

    @Override
    public E getNext() {
        if (!hasNext()) throw new IllegalStateException("No next element.");
        this.index++;
        return this.elementList.get(this.index);
    }

    @Override
    public void stepForward() {
        if (!hasNext()) throw new IllegalStateException("No next element.");
        this.index++;
    }

    @Override
    public boolean hasCurrent() {
        return (this.index >= 0 && !this.elementList.isEmpty());
    }

    @Override
    public E getCurrent() {
        if (!hasCurrent()) throw new IllegalStateException("No current element.");
        return this.elementList.get(this.index);
    }

    @Override
    public boolean hasPrevious() {
        return (this.index >= 1);
    }

    @Override
    public E getPrevious() {
        if (!hasPrevious()) throw new IllegalStateException("No previous element.");
        this.index--;
        return this.elementList.get(this.index);
    }

    @Override
    public void stepBack() {
        if (!hasPrevious()) throw new IllegalStateException("No previous element.");
        this.index--;
    }

}
