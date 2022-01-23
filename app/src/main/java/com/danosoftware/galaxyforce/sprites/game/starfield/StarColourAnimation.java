package com.danosoftware.galaxyforce.sprites.game.starfield;

/**
 * Manages animation of star colours
 */
public class StarColourAnimation {

  private final StarColour[] keyFrames;
  private final float frameDuration;

  public StarColourAnimation(float frameDuration, StarColour... keyFrames) {
    this.frameDuration = frameDuration;
    this.keyFrames = keyFrames;
  }

  public StarColour getKeyFrame(float stateTime) {
    int frameNumber = ((int) (stateTime / frameDuration)) % keyFrames.length;
    return keyFrames[frameNumber];
  }
}
