package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

/**
 * 
 * @author Danny
 */
public abstract class SpriteBaseMissile extends MovingSprite
{
    public SpriteBaseMissile(int xStart, int yStart, ISpriteIdentifier spriteId, int energy, int hitEnergy)
    {
        // default is that all missiles are immediately visible
        super(xStart, yStart, spriteId, energy, hitEnergy, true);
    }

    /**
     * Returns true if alien has been hit before. Default implementation is to
     * return false as most missiles are destroyed on initial impact.
     * 
     * Some base missiles implementations may override this with their own
     * behaviour.
     * 
     * @return true if alien has been hit before
     */
    public boolean hitBefore(SpriteAlien alien)
    {
        return false;
    }
}
