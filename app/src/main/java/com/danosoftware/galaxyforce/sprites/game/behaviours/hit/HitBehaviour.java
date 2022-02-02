package com.danosoftware.galaxyforce.sprites.game.behaviours.hit;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;

/**
 * Interface that handles behaviour following a sprite being hit.
 */
public interface HitBehaviour {

    /**
     * Start hit behaviour. Supply stateTime to keep hit animation
     * in sync with the parent sprite.
     */
    void startHit(float stateTime);

    /**
     * Start hit behaviour. Supply stateTime to keep hit animation
     * in sync with the parent sprite.
     * <p>
     * This implementation used for followers when head
     * followable sprite will handle main hit effects.
     */
    void startHitFollower(float stateTime);

  /**
   * Is sprite still performing hit behaviour?
   */
  boolean isHit();

  /**
   * Get the current hit sprite.
   */
  SpriteDetails getHit(float deltaTime);
}
