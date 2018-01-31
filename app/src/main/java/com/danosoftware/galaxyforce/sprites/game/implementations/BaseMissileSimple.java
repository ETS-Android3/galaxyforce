package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class BaseMissileSimple extends SpriteBaseMissile
{

    /* missile sprite */
    public static final ISpriteIdentifier SPRITE = GameSpriteIdentifier.LASER_BASE;

    /* distance missile can move per second */
    public static final int BASE_MISSILE_MOVE_PIXELS = 7 * 60;

    /* energy of this missile */
    private static final int ENERGY = 1;

    /* how much energy will be lost by base when this missile hits it */
    private static final int HIT_ENERGY = 1;

    /* direction of missile */
    private Direction direction;

    public BaseMissileSimple(int xStart, int yStart, Direction direction)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);
        this.direction = direction;
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

}
