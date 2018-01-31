package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
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
public abstract class SpritePowerUp extends MovingSprite
{

    /* power-up has no energy */
    private static final int ENERGY = 0;

    /* power-up caused no energy damage to a base */
    private static final int HIT_ENERGY = 0;

    public SpritePowerUp(int xStart, int yStart, ISpriteIdentifier spriteId)
    {
        super(xStart, yStart, spriteId, ENERGY, HIT_ENERGY, true);
    }

    /**
     * Return the power-up type for this power-up
     * 
     * @return power up type
     */
    public abstract PowerUpType getPowerUpType();
}
