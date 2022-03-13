package com.danosoftware.galaxyforce.sprites.providers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainedIteratorTest {

  private final static Logger logger = LoggerFactory.getLogger(ChainedIteratorTest.class);

  @Test
  public void shouldLoopThroughNonEmptyChainedCollections() {
    ChainedIterator<Integer> chainedIterator = createNonEmptyChainedIterator();
    assertThat(loop(chainedIterator), equalTo(6));
  }

  @Test
  public void shouldLoopThroughChainedCollectionsWithSomeEmptyCollections() {
    ChainedIterator<Integer> chainedIterator = createChainedIteratorWithSomeEmptyCollections();
    assertThat(loop(chainedIterator), equalTo(6));
  }

  @Test
  public void shouldLoopThroughEmptyChainedIterator() {
    ChainedIterator<Integer> chainedIterator = createEmptyChainedIterator();
    assertThat(loop(chainedIterator), equalTo(0));
  }

  @Test
  public void shouldLoopThroughEmptyChainedCollections() {
    ChainedIterator<Integer> chainedIterator = createChainedIteratorWithEmptyCollections();
    assertThat(loop(chainedIterator), equalTo(0));
  }

  @Test
  public void shouldNotProvideValuesForACompletedChainedIterator() {
    ChainedIterator<Integer> chainedIterator = createNonEmptyChainedIterator();
    assertThat(loop(chainedIterator), equalTo(6));

    // an attempt to iterate through a completed iterator, should return no values
    assertThat(loop(chainedIterator), equalTo(0));
  }


  private ChainedIterator<Integer> createNonEmptyChainedIterator() {
    List<Integer> list1 = new ArrayList<>();
    list1.add(1);
    list1.add(2);
    list1.add(3);
    List<Integer> list2 = new ArrayList<>();
    list1.add(4);
    list1.add(5);
    List<Integer> list3 = new ArrayList<>();
    list1.add(6);
    Collection<Collection<Integer>> chainedList = new ArrayList<>();
    chainedList.add(list1);
    chainedList.add(list2);
    chainedList.add(list3);
    return new ChainedIterator<>(chainedList);
  }

  private ChainedIterator<Integer> createChainedIteratorWithSomeEmptyCollections() {
    List<Integer> list1 = new ArrayList<>();
    list1.add(1);
    list1.add(2);
    list1.add(3);
    List<Integer> empty1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    list1.add(4);
    list1.add(5);
    List<Integer> empty2 = new ArrayList<>();
    List<Integer> list3 = new ArrayList<>();
    list1.add(6);
    Collection<Collection<Integer>> chainedList = new ArrayList<>();
    chainedList.add(list1);
    chainedList.add(empty1);
    chainedList.add(list2);
    chainedList.add(empty2);
    chainedList.add(list3);
    return new ChainedIterator<>(chainedList);
  }

  private ChainedIterator<Integer> createEmptyChainedIterator() {
    Collection<Collection<Integer>> chainedList = new ArrayList<>();
    return new ChainedIterator<>(chainedList);
  }

  private ChainedIterator<Integer> createChainedIteratorWithEmptyCollections() {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    List<Integer> list3 = new ArrayList<>();
    Collection<Collection<Integer>> chainedList = new ArrayList<>();
    chainedList.add(list1);
    chainedList.add(list2);
    chainedList.add(list3);
    return new ChainedIterator<>(chainedList);
  }

  private int loop(ChainedIterator<Integer> chainedIterator) {
    int i = 1;
    for (Iterator<Integer> it = chainedIterator; it.hasNext(); ) {
      int next = it.next();
      logger.info(Integer.toString(next));
      assertThat(next, equalTo(i++));
    }
    // return highest number
    return i - 1;
  }
}
