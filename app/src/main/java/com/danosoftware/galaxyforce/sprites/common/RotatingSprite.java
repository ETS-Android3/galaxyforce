package com.danosoftware.galaxyforce.sprites.common;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;

public class RotatingSprite extends AbstractMovingSprite {

  /* angle of sprite rotation */
  private float angle = 0f;


  public RotatingSprite(int xStart, int yStart, SpriteDetails spriteId) {
    super(spriteId, xStart, yStart);
  }

  @Override
  public void animate(float deltaTime) {
    // increase angle rotation
    angle = (angle + (deltaTime * 75)) % 360;
    rotate(angle);
  }
}
