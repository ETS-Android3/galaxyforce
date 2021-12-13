package com.danosoftware.galaxyforce.flightpath.paths;

/**
 * Represents a point object (an x-y co-ordinate). Normally used to represent a position on a path.
 */
public class PathPoint {

  private final float x;
  private final float y;
  private final float angle;

  public PathPoint(float x, float y, float angle) {
    this.x = x;
    this.y = y;
    this.angle = angle;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getAngle() {
    return angle;
  }
}
