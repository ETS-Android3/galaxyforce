package com.danosoftware.galaxyforce.sprites.game.bases.explode;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;

public interface IBaseExploder {

  /**
   * Initialise start of explosion
   */
  void startExplosion();

  /**
   * Get the current explosion sprite.
   */
  SpriteDetails getExplosion(float deltaTime);

  /**
   * Has the explosion finished?
   */
  boolean finishedExploding();
}
