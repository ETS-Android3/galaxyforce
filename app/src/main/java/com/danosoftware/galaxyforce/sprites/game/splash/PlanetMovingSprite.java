package com.danosoftware.galaxyforce.sprites.game.splash;

import static com.danosoftware.galaxyforce.constants.GameConstants.PLANET_Y_POS;

import com.danosoftware.galaxyforce.sprites.common.AbstractMovingSprite;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;

public class PlanetMovingSprite extends AbstractMovingSprite {

  // variables to track planet movements
  private static final int DISTANCE_PER_SECOND = 100;
  private static final float DELAY_IN_SECONDS_BEFORE_START = 1f;

  private final int startPosition;
  private final int maxDistanceToTravel;
  private float timeElapsed;

  public PlanetMovingSprite(int xStart, int yStart, SpriteDetails spriteId) {
    super(spriteId, xStart, yStart);
    this.startPosition = yStart;
    this.maxDistanceToTravel = PLANET_Y_POS - yStart;
    this.timeElapsed = 0f;
  }

  @Override
  public void animate(float deltaTime) {
    timeElapsed += deltaTime;
    float distance = Math
        .min((timeElapsed - DELAY_IN_SECONDS_BEFORE_START) * DISTANCE_PER_SECOND,
            maxDistanceToTravel);
    moveY(startPosition + distance);
  }
}
