package com.danosoftware.galaxyforce.sprites.game.starfield;

/**
 * Tuple of Star with it's initial Y position. Helps computation and movement to a new Y position.
 */
class StarTuple {

  private final Star star;
  private final float initialY;

  StarTuple(
      Star star,
      float initialY) {
    this.star = star;
    this.initialY = initialY;
  }

  Star getStar() {
    return star;
  }

  float getInitialY() {
    return initialY;
  }
}
