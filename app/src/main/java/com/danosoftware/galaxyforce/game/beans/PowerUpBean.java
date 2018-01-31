package com.danosoftware.galaxyforce.game.beans;

import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpritePowerUp;

/**
 * Power-up bean holding the power-up and sound effects.
 */
public class PowerUpBean
{
    private final SpritePowerUp powerUp;
    private final Sound soundEffect;

    public PowerUpBean(SpritePowerUp powerUp, Sound soundEffect)
    {
        this.powerUp = powerUp;
        this.soundEffect = soundEffect;
    }

    public SpritePowerUp getPowerUp()
    {
        return powerUp;
    }

    public Sound getSoundEffect()
    {
        return soundEffect;
    }
}
