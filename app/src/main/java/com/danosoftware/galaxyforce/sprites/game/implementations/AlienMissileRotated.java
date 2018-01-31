package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

/**
 * Alien missile that targets the supplied base.
 * 
 * The missile will calculate it's direction on construction and will continue
 * to move in that direction until off the screen.
 * 
 * This missile will not change direction once fired even if the targeted base
 * moves.
 * 
 * @author Danny
 * 
 */
public class AlienMissileRotated extends SpriteAlienMissile
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

    /* offset applied to x and y every move */
    private int xDelta;
    private int yDelta;

    // private constructor - must use static method
    private AlienMissileRotated(int xStart, int yStart, SpriteBase base)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);

        // calculate angle from missile position to base
        float angle = 0;
        if (base != null)
        {
            angle = (float) Math.atan2(base.getY() - yStart, base.getX() - xStart);
        }
        else
        {
            // if base is null fire downwards
            angle = (float) Math.atan2(-1, 0);
        }

        // convert angle to degrees for sprite rotation.
        // needs to be adjusted by 90 deg for correct rotation.
        setRotation((int) ((angle - Math.PI / 2f) * (180f / Math.PI)));

        // calculate the deltas to be applied each move
        this.xDelta = (int) (ALIEN_MISSILE_MOVE_PIXELS * (float) Math.cos(angle));
        this.yDelta = (int) (ALIEN_MISSILE_MOVE_PIXELS * (float) Math.sin(angle));
    }

    /**
     * return an instance of missile at supplied position
     */
    public static SpriteAlienMissile newMissile(int xStart, int yStart, SpriteBase base)
    {
        return new AlienMissileRotated(xStart, yStart, base);
    }

    @Override
    public void move(float deltaTime)
    {
        // move missile by calculated deltas
        setX(getX() + (int) (xDelta * deltaTime));
        setY(getY() + (int) (yDelta * deltaTime));

        // move sprite bounds
        updateBounds();

        // if missile is now off screen then destory it
        if (getY() > GameConstants.GAME_HEIGHT - getHeight() || getY() < 0 - getHeight() || getX() > GameConstants.GAME_WIDTH - getWidth()
                || getX() < 0 - getWidth())
        {
            setState(SpriteState.DESTROYED);
        }

    }
}
