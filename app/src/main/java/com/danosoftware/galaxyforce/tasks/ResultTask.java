package com.danosoftware.galaxyforce.tasks;

@FunctionalInterface
public interface ResultTask<T> {

  T execute();
}
