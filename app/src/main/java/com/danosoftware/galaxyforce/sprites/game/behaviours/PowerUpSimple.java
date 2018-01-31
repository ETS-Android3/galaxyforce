package com.danosoftware.galaxyforce.sprites.game.behaviours;

import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.factories.PowerUpFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;

public class PowerUpSimple implements PowerUpBehaviour
{
    /*
     * ******************************************************
     * PRIVATE INSTANCE VARIABLES
     * ******************************************************
     */

    /* reference to game model */
    private final GameHandler model;

    /* optional list of power-ups to randomly choose from */
    private PowerUpType[] powerUps;

    /**
     * Behaviour that will create a random power-up when alien is destroyed.
     * 
     * @param model
     */
    public PowerUpSimple(GameHandler model)
    {
        this.model = model;
    }

    /**
     * Behaviour that will create a random power-up from a supplied list when
     * alien is destroyed.
     * 
     * @param model
     * @param powerUps
     *            - list of power-ups to choose from
     */
    public PowerUpSimple(GameHandler model, PowerUpType... powerUps)
    {
        this(model);
        this.powerUps = powerUps;
    }

    @Override
    public void releasePowerUp(SpriteAlien alien)
    {
        /*
         * if base is null or alien is above base then drop power-up downwards
         * else drop upwards.
         */
        SpriteBase base = model.getBase();
        Direction direction = (base == null || base.getY() <= alien.getY()) ? Direction.DOWN : Direction.UP;

        /*
         * Choose from supplied list of power-ups if one exists. Otherwise
         * choose a completely random power-up.
         */
        if (powerUps != null)
        {
            model.addPowerUp(PowerUpFactory.generateRandomPowerUp(alien.getX(), alien.getY(), direction, powerUps));
        }
        else
        {
            model.addPowerUp(PowerUpFactory.generateRandomPowerUp(alien.getX(), alien.getY(), direction));
        }
    }
}
