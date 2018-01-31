package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteHitGlow;

/**
 * Represents a missile hit sprite that is displayed when a missile hits a base.
 * 
 * The missile hit is displayed for a set amount of seconds and then destroyed.
 */
public class HitBaseGlow extends SpriteHitGlow
{
    public HitBaseGlow(int xStart, int yStart)
    {
        super(xStart, yStart);
    }
}
