package com.danosoftware.galaxyforce.tasks;

/**
 * Will run a provided task and then return result to the provided listener.
 */
public class TaskCallback<T> implements Runnable {

  private final ResultTask<T> task;
  private final OnTaskCompleteListener<T> listener;

  public TaskCallback(
      ResultTask<T> task,
      OnTaskCompleteListener<T> listener) {
    this.task = task;
    this.listener = listener;
  }

  public void run() {
    T result = task.execute();
    listener.onCompletion(result);
  }
}
