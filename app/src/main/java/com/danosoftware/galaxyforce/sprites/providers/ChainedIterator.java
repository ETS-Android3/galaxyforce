package com.danosoftware.galaxyforce.sprites.providers;

import java.util.Collection;
import java.util.Iterator;

/**
 * Iterator designed to iterate through chained collections of items.
 * <p>
 * Some parts of the game maintain separate collections of the same type.
 * <p>
 * For example: - list of alien sprites - list of missile sprites - list of power-up sprites
 * <p>
 * It is required to iterate through all of these in a single pass. It would degrade performance to
 * re-create a new collections from these individual collections every time.
 * <p>
 * This chained iterator allows the individual collections to be passed in. This iterator will
 * iterator through each collection in-turn until the final item in the final collection is
 * provided.
 *
 * @param <T>
 */
public class ChainedIterator<T> implements Iterator<T> {

  private final Iterator<Collection<T>> chainIt;
  private Iterator<T> iterator;

  public ChainedIterator(
      Collection<Collection<T>> chain) {
    this.chainIt = chain.iterator();
    this.iterator = getNextIterator();
  }

  @Override
  public boolean hasNext() {
    // iterator will be null if we have finished or there was nothing to iterate through
    if (iterator == null) {
      return false;
    }

    if (iterator.hasNext()) {
      return true;
    }

    // get next non-empty iterator in the chain
    iterator = getNextIterator();

    // have we reached the end of the chain?
    return iterator != null;
  }

  @Override
  public T next() {
    return iterator.next();
  }

  /**
   * Recursively fetch next iterator. Guarantees to return a non-empty iterator. Returns null if no
   * more iterators are found.
   */
  private Iterator<T> getNextIterator() {
    if (chainIt.hasNext()) {
      Collection<T> nextCollection = chainIt.next();
      // if next collection is empty return the iterator for the following collection (recursive)
      if (nextCollection.isEmpty()) {
        return getNextIterator();
      }
      // return iterator for next collection
      return nextCollection.iterator();
    }
    // no more iterators
    return null;
  }
}
