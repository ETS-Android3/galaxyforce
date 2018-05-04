package com.danosoftware.galaxyforce.sprites.game.factories;

import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.game.beans.PowerUpBean;
import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;
import com.danosoftware.galaxyforce.sound.SoundEffectBank;
import com.danosoftware.galaxyforce.sound.SoundEffectBankSingleton;
import com.danosoftware.galaxyforce.sprites.game.implementations.PowerUpImpl;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpritePowerUp;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class PowerUpFactory
{
    /* initialise sound effects */
    private final static Sound POWER_UP_SOUND;

    static
    {
        /* create reference to sound effects */
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        POWER_UP_SOUND = soundBank.get(SoundEffect.POWER_UP_SPAWN);
    }

    /**
     * Return any random power-up.
     * 
     * @param x
     * @param y
     * @param direction
     * @return SpritePowerUp
     */
    public static PowerUpBean generateRandomPowerUp(int x, int y, Direction direction)
    {
        // create array of possible power-ups
        PowerUpType[] powerUps = PowerUpType.values();

        // use method requiring a power-up type array to generate powerup
        return generateRandomPowerUp(x, y, direction, powerUps);
    }

    /**
     * Return a random power-up from the supplied array of power-up types.
     * 
     * @param x
     * @param y
     * @param direction
     * @param powerUps
     * @return SpritePowerUp
     */
    public static PowerUpBean generateRandomPowerUp(int x, int y, Direction direction, PowerUpType... powerUps)
    {
        // select a random powerup type and generate the power-up
        int index = (int) (Math.random() * powerUps.length);

        return newPowerUp(powerUps[index], x, y, direction);
    }

    /**
     * Return a power-up using the supplied power-up type.
     * 
     * @param powerUpType
     * @param x
     * @param y
     * @param direction
     * @return SpritePowerUp
     */
    public static PowerUpBean newPowerUp(PowerUpType powerUpType, int x, int y, Direction direction)
    {
        ISpriteIdentifier spriteId = null;
        Sound sound = POWER_UP_SOUND;

        switch (powerUpType)
        {
        case ENERGY:
            spriteId = GameSpriteIdentifier.POWERUP_BATTERY;
            break;
        case HELPER_BASES:
            spriteId = GameSpriteIdentifier.POWERUP_HELPER_BASES;
            break;
        case LIFE:
            spriteId = GameSpriteIdentifier.POWERUP_LIFE;
            break;
        case MISSILE_BLAST:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_BLAST;
            break;
        case MISSILE_FAST:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_FAST;
            break;
        case MISSILE_GUIDED:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_GUIDED;
            break;
        case MISSILE_LASER:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_LASER;
            break;
        case MISSILE_PARALLEL:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_PARALLEL;
            break;
        case MISSILE_SPRAY:
            spriteId = GameSpriteIdentifier.POWERUP_MISSILE_SPRAY;
            break;
        case SHIELD:
            spriteId = GameSpriteIdentifier.POWERUP_SHIELD;
            break;
        default:
            throw new IllegalArgumentException("Unsupported PowerUpType: '" + powerUpType + "'.");
        }

        SpritePowerUp powerUp = new PowerUpImpl(x, y, direction, powerUpType, spriteId);

        return new PowerUpBean(powerUp, sound);
    }

    /**
     * Return a random power-up type from the supplied array of power-up types.
     */
    public static PowerUpType selectRandomPowerUpType(PowerUpType[] powerUps)
    {
        // select a random powerup type
        int index = (int) (Math.random() * powerUps.length);
        return powerUps[index];
    }
}
