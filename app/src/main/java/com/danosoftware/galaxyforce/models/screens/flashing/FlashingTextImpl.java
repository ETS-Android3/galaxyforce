package com.danosoftware.galaxyforce.models.screens.flashing;

import com.danosoftware.galaxyforce.text.Text;

import java.util.ArrayList;
import java.util.List;

public class FlashingTextImpl implements FlashingText {

  // reference to flashing text
  private final List<Text> flashingText;

  // time delay between flash on/off in seconds
  private final float flashDelay;

  // time since flash state last changed
  private float timeSinceFlashStateChange;

  // current flash state - show text or hide text
  private boolean showText;

  private final FlashingTextListener listener;

  // default constructor with initial flashing text shown
  public FlashingTextImpl(
          List<Text> flashingText,
          float flashDelay,
          FlashingTextListener listener) {
    this(flashingText, flashDelay, listener, true);
  }

  public FlashingTextImpl(
          List<Text> flashingText,
          float flashDelay,
          FlashingTextListener listener,
          boolean showText) {
    this.flashingText = flashingText;
    this.flashDelay = flashDelay;
    this.timeSinceFlashStateChange = 0f;
    this.showText = showText;
    this.listener = listener;
  }

  @Override
  public void update(float deltaTime) {

    timeSinceFlashStateChange += deltaTime;

    /*
     * check to see if flash state should change.
     */
    if (timeSinceFlashStateChange > flashDelay) {
      // invert current flash state
      showText = (!showText);

      // reset time since state change
      timeSinceFlashStateChange = 0f;

      listener.onFlashingTextChange();
    }

  }

  @Override
  public List<Text> text() {

    List<Text> text = new ArrayList<>();
    if (showText) {
      text.addAll(flashingText);
    }
    return text;
  }
}
