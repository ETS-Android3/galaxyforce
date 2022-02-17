package com.danosoftware.galaxyforce.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskService {

  private final ExecutorService executor;

  public TaskService() {
    this.executor = Executors.newFixedThreadPool(5);
  }

  public void execute(Runnable task) {
    executor.execute(task);
  }

  public void dispose() {
    executor.shutdown();
  }
}
