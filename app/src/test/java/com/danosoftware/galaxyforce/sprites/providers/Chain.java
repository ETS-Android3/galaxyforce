package com.danosoftware.galaxyforce.sprites.providers;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Chain implements Iterable<Integer> {

  @NonNull
  @Override
  public Iterator<Integer> iterator() {
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
}
