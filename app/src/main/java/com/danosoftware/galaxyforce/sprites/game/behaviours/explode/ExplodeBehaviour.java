package com.danosoftware.galaxyforce.sprites.game.behaviours.explode;

import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlienFollower;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public interface ExplodeBehaviour {

    /**
     * Initialise start of explosion
     */
    void startExplosion(IAlien alien);

    /**
     * Initialise start of explosion for follower.
     * <p>
     * Used for followers when another followable sprite is exploding.
     */
    void startExplosionFollower(IAlienFollower alien);

    /**
     * Get the current explosion sprite.
     */
    ISpriteIdentifier getExplosion(float deltaTime);

    /**
     * Has the explosion finished?
     */
    boolean finishedExploding();

}