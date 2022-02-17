package com.danosoftware.galaxyforce.tasks;

public interface OnTaskCompleteListener<T> {

  void onCompletion(T result);
}
