package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteShield;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

public class ShieldBase extends SpriteShield
{

    // shield animation that pulses every 0.5 seconds
    private static final Animation SHIELD_PULSE = new Animation(0.5f, GameSpriteIdentifier.CONTROL, GameSpriteIdentifier.JOYSTICK);

    public ShieldBase(int xStart, int yStart, float syncTime)
    {
        super(xStart, yStart, SHIELD_PULSE, syncTime);
    }
}
