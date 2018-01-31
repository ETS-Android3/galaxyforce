package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class BaseMissileBlast extends SpriteBaseMissile
{

    /* missile sprite */
    public static final ISpriteIdentifier SPRITE = GameSpriteIdentifier.LASER_BASE;

    /* distance missile can move in pixels each second */
    public static final int BASE_MISSILE_MOVE_PIXELS = 7 * 60;

    /* energy of this missile */
    private static final int ENERGY = 1;

    /* how much energy will be lost by base when this missile hits it */
    private static final int HIT_ENERGY = 2;

    /* offset applied to x and y every move */
    private int xDelta;
    private int yDelta;

    public BaseMissileBlast(int xStart, int yStart, float angle)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);

        // convert angle to degrees for sprite rotation.
        // needs to be adjusted by 90 deg for correct rotation.
        setRotation((int) ((angle - Math.PI / 2f) * (180f / Math.PI)));

        // calculate the deltas to be applied each move
        this.xDelta = (int) (BASE_MISSILE_MOVE_PIXELS * (float) Math.cos(angle));
        this.yDelta = (int) (BASE_MISSILE_MOVE_PIXELS * (float) Math.sin(angle));
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
