package com.danosoftware.galaxyforce.view;

import com.danosoftware.galaxyforce.models.screens.flashing.FlashingTextListener;

public class FPSCounter {

  private long startTime = System.nanoTime();
  private int frames = 0;
  private int previousFrames = 0;
  private final FlashingTextListener listener;

  public FPSCounter(FlashingTextListener listener) {
    this.listener = listener;
  }

  public int getValue() {
    return previousFrames;
  }

  public void update() {
    frames++;
    if (System.nanoTime() - startTime >= 1000000000) {
      if (frames != previousFrames) {
        listener.onFlashingTextChange();
      }
      previousFrames = frames;
      frames = 0;
      startTime = System.nanoTime();
    }
  }
}
