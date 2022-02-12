package com.danosoftware.galaxyforce.sprites.properties;

import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;

public class SpriteDimensions {

  private final int width;
  private final int height;
  private final int boundsReduction;

  public SpriteDimensions(int width, int height, int boundsReduction) {
    this.width = width;
    this.height = height;
    this.boundsReduction = boundsReduction;

    if (width <= boundsReduction * 2) {
      throw new GalaxyForceException(
          String.format(
              "Sprite has width of %d but bounds reduction of %d.",
              width,
              boundsReduction));
    }
    if (height <= boundsReduction * 2) {
      throw new GalaxyForceException(
          String.format(
              "Sprite has height of %d but bounds reduction of %d.",
              height,
              boundsReduction));
    }
  }

  public SpriteDimensions(int width, int height) {
    this.width = width;
    this.height = height;
    this.boundsReduction = 0;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getBoundsReduction() {
    return boundsReduction;
  }
}
