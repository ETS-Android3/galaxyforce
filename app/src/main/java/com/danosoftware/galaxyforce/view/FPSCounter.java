package com.danosoftware.galaxyforce.view;

import com.danosoftware.galaxyforce.text.TextChangeListener;

/**
 * Measures the number of frames drawn per second
 */
public class FPSCounter {

  // start time of current second
  private long startTime = System.nanoTime();
  // frames drawn so far in the current second
  private int frames = 0;
  // number of frames drawn in the previous second
  private int previousFrames = 0;
  // listener to be notified if frame rate changes
  private final TextChangeListener listener;

  public FPSCounter(TextChangeListener listener) {
    this.listener = listener;
  }

  public int getValue() {
    return previousFrames;
  }

  public void update() {
    frames++;

    // update frame count if we have reached the end of the current second
    if (System.nanoTime() - startTime >= 1_000_000_000) {
      if (frames != previousFrames) {
        listener.onTextChange();
      }
      previousFrames = frames;
      frames = 0;
      startTime = System.nanoTime();
    }
  }
}
