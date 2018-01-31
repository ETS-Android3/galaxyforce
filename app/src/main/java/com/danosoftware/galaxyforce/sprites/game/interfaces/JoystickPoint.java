package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class JoystickPoint extends Sprite
{

    /*
     * Sprite model for the joystick point. Creates concrete class using Sprite
     * class.
     */
    public JoystickPoint(int xStart, int yStart, ISpriteIdentifier spriteId)
    {
        super(xStart, yStart, spriteId, true);
    }

}
