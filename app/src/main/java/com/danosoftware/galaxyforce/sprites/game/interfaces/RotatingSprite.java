package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class RotatingSprite extends Sprite
{
    public RotatingSprite(int xStart, int yStart, ISpriteIdentifier spriteId)
    {
        super(xStart, yStart, spriteId, true);
    }
}
