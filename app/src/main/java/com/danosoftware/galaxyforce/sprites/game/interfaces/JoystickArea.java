package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class JoystickArea extends Sprite
{

    /*
     * Sprite model for the area around a joystick. Creates concrete class using
     * Sprite class.
     */
    public JoystickArea(int xStart, int yStart, ISpriteIdentifier spriteId)
    {
        super(xStart, yStart, spriteId, true);
    }

}
