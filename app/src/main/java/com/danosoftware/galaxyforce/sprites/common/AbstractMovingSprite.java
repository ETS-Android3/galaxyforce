package com.danosoftware.galaxyforce.sprites.common;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;

public abstract class AbstractMovingSprite extends AbstractSprite implements IMovingSprite {

  AbstractMovingSprite(
      SpriteDetails spriteId,
      float x,
      float y,
      float rotation) {

    super(spriteId, x, y, rotation);
  }

  protected AbstractMovingSprite(
      SpriteDetails spriteId,
      float x,
      float y) {

    this(spriteId, x, y, 0);
  }

  @Override
  public void move(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void moveY(float y) {
    this.y = y;
  }

  @Override
  public void moveByDelta(float xDelta, float yDelta) {
    this.x += xDelta;
    this.y += yDelta;
  }

  @Override
  public void moveYByDelta(float yDelta) {
    this.y += yDelta;
  }

  @Override
  public void moveXByDelta(float xDelta) {
    this.x += xDelta;
  }

  @Override
  public void rotate(float rotation) {
    this.rotation = rotation;
  }
}
