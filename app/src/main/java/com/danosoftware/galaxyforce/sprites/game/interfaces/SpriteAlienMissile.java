package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package invaders;

/**
 * 
 * @author Danny
 */
public abstract class SpriteAlienMissile extends MovingSprite
{

    public SpriteAlienMissile(int xStart, int yStart, ISpriteIdentifier spriteId, int energy, int hitEnergy)
    {
        // default is that all missiles are immediately visible
        super(xStart, yStart, spriteId, energy, hitEnergy, true);
    }
}
