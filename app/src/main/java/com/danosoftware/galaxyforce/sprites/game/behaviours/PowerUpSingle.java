package com.danosoftware.galaxyforce.sprites.game.behaviours;

import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.factories.PowerUpFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;

public class PowerUpSingle implements PowerUpBehaviour
{
    /*
     * ******************************************************
     * PRIVATE INSTANCE VARIABLES
     * ******************************************************
     */

    /* reference to game model */
    private final GameHandler model;

    /* power-up type */
    private final PowerUpType powerUp;

    /**
     * Behaviour that will create the supplied power-up when
     * alien is destroyed.
     *
     * @param model
     * @param powerUp
     */
    public PowerUpSingle(GameHandler model, PowerUpType powerUp)
    {
        this.model = model;
        this.powerUp = powerUp;
    }

    @Override
    public void releasePowerUp(SpriteAlien alien)
    {
        /*
         * Add power-up (if one exists).
         * If base is null or alien is above base then drop power-up downwards
         * else drop upwards.
         */
        if (powerUp != null) {
            SpriteBase base = model.getBase();
            Direction direction = (base == null || base.getY() <= alien.getY()) ? Direction.DOWN : Direction.UP;
            model.addPowerUp(PowerUpFactory.newPowerUp(powerUp, alien.getX(), alien.getY(), direction));
        }
    }
}
