package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpritePowerUp;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class PowerUpImpl extends SpritePowerUp
{
    /* distance power-up can move per second */
    public static final int BASE_MISSILE_MOVE_PIXELS = 2 * 60;

    /* direction of missile */
    private Direction direction;

    /* power up type */
    private PowerUpType powerUpType;

    public PowerUpImpl(int xStart, int yStart, Direction direction, PowerUpType powerUpType, ISpriteIdentifier sprite)
    {
        super(xStart, yStart, sprite);
        this.direction = direction;
        this.powerUpType = powerUpType;
    }

    @Override
    public void move(float deltaTime)
    {

        // move missile until off the screen and then destroy it
        switch (direction)
        {
        case UP:

            setY(getY() + (int) (BASE_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() > GameConstants.GAME_HEIGHT - getHeight())
            {
                // setDestroy();
                setState(SpriteState.DESTROYED);
            }
            break;

        case DOWN:

            setY(getY() - (int) (BASE_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() < 0 - getHeight())
            {
                // setDestroy();
                setState(SpriteState.DESTROYED);
            }
            break;
        }
    }

    @Override
    public PowerUpType getPowerUpType()
    {
        return powerUpType;
    }

}
