package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class AlienMissileSimple extends SpriteAlienMissile
{

    /*
     * ******************************************************
     * PRIVATE STATIC VARIABLES
     * ******************************************************
     */

    /* energy of this missile */
    private static final int ENERGY = 1;

    /* how much energy will be lost by base when this missile hits it */
    private static final int HIT_ENERGY = 2;

    /* distance missile can move each cycle in pixels each second */
    public static final int ALIEN_MISSILE_MOVE_PIXELS = 5 * 60;

    /* Sprite id */
    private static final ISpriteIdentifier SPRITE = GameSpriteIdentifier.LASER_ALIEN;

    /*
     * ******************************************************
     * PRIVATE INSTANCE VARIABLES
     * ******************************************************
     */

    /* current direction of missile */
    private Direction direction;

    // private constructor - must use static method
    private AlienMissileSimple(int xStart, int yStart, Direction direction)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);
        this.direction = direction;
    }

    /**
     * return an instance of missile at supplied position
     */
    public static SpriteAlienMissile newMissile(int xStart, int yStart, Direction direction)
    {
        /*
         * Provided x,y will be at top or bottom of alien sprite depending on
         * direction. Adjust starting missile y so it is offset by half the
         * missile's height. This will ensure the missile starts in the correct
         * position below/above the alien.
         */
        yStart = direction == Direction.DOWN ? yStart + (SPRITE.getProperties().getHeight() / 2) : yStart
                - (SPRITE.getProperties().getHeight() / 2);

        return new AlienMissileSimple(xStart, yStart, direction);
    }

    @Override
    public void move(float deltaTime)
    {
        // TODO code to move missile

        // move sprite bounds
        updateBounds();

        // move missile until off the screen and then destroy it
        switch (direction)
        {
        case DOWN:

            setY(getY() + (int) (ALIEN_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() > GameConstants.GAME_HEIGHT - getHeight())
            {
                setState(SpriteState.DESTROYED);
            }
            break;

        case UP:

            setY(getY() - (int) (ALIEN_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() < 0 - getHeight())
            {
                setState(SpriteState.DESTROYED);
            }
            break;
        }
    }

}
