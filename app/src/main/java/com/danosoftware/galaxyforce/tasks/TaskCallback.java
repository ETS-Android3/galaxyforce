package com.danosoftware.galaxyforce.tasks;

import android.util.Log;

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
    Log.i("TASK", "Task started: " + task.toString());
    T result = task.execute();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Log.i("TASK", "Task completed: " + task.toString());
    listener.onCompletion(result);
  }
}
