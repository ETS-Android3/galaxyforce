package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

public class BaseMissileGuided extends SpriteBaseMissile
{

    /* missile sprite */
    public static final ISpriteIdentifier SPRITE = GameSpriteIdentifier.LASER_BASE;

    /* distance missile can move in pixels each second */
    public static final int BASE_MISSILE_MOVE_PIXELS = 7 * 60;

    /* time delay between missile direction changes */
    public static final float MISSILE_DIRECTION_CHANGE_DELAY = 0.1f;

    /* maximum missile change direction in radians */
    public static final float MAX_DIRECTION_CHANGE_ANGLE = 0.3f;

    /* energy of this missile */
    private static final int ENERGY = 1;

    /* how much energy will be lost by base when this missile hits it */
    private static final int HIT_ENERGY = 2;

    /* alien targeted by missile */
    private SpriteAlien alien = null;

    /* reference to model */
    private GameHandler model = null;

    /* direction of missile - only used if no alien is targeted */
    private Direction direction;

    /* offset applied to x and y every move */
    private int xDelta;
    private int yDelta;

    /* current angle of missile direction */
    private float angle;

    /* variable to store time passed since last missile direction change */
    private float timeSinceMissileDirectionChange = 0f;

    public BaseMissileGuided(int xStart, int yStart, Direction direction, GameHandler model)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);
        this.model = model;
        this.direction = direction;

        // initial angle based on direction (i.e. straight up or down)
        this.angle = (this.direction == Direction.UP ? 1.0f : -1.0f) * (float) (Math.PI / 2f);

        // calculate rotation and movement delta based on angle
        calculateMovements();

        // reset timer since last missile direction change
        timeSinceMissileDirectionChange = 0f;
    }

    @Override
    public void move(float deltaTime)
    {
        timeSinceMissileDirectionChange += deltaTime;

        /*
         * Guide missile every x seconds so the missile changes direction to
         * follow any changes in the alien's position.
         */
        if (timeSinceMissileDirectionChange > MISSILE_DIRECTION_CHANGE_DELAY)
        {
            /*
             * if no alien selected or alien no longer active then choose a new
             * target.
             */
            if (alien == null || !alien.isActive())
            {
                chooseActiveAlien();
            }

            // re-target alien based on current position
            targetAlien();

            // reset timer since last missile direction change
            timeSinceMissileDirectionChange = 0f;
        }

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

    /**
     * Choose an active alien for the missile's next target. Called when picking
     * the original alien target or if alien is destroyed before missile hits
     * it.
     */
    private void chooseActiveAlien()
    {
        // choose a random active alien
        alien = model.chooseActiveAlien();
    }

    /**
     * Calculate angle and x and y deltas required to fire missile at alien's
     * current position. May be called several times to ensure missile remains
     * targeted as alien moves.
     */
    private void targetAlien()
    {

        float newAngle;

        /*
         * only re-target if we have a current alien. if we have no alien then
         * don't change missile direction.
         */
        if (alien != null)
        {
            // calculate angle from missile position to alien
            newAngle = (float) Math.atan2(alien.getY() - this.getY(), alien.getX() - this.getX());

            // don't allow sudden changes of direction. limit to MAX radians
            if ((newAngle - angle) > MAX_DIRECTION_CHANGE_ANGLE)
            {
                angle += MAX_DIRECTION_CHANGE_ANGLE;
            }
            else if ((newAngle - angle) < MAX_DIRECTION_CHANGE_ANGLE)
            {
                angle -= MAX_DIRECTION_CHANGE_ANGLE;
            }
            else
            {
                this.angle = newAngle;
            }

            // recalculate rotation and movement delta based on new angle
            calculateMovements();
        }
    }

    private void calculateMovements()
    {
        // convert angle to degrees for sprite rotation.
        // needs to be adjusted by 90 deg for correct rotation.
        setRotation((int) ((angle - Math.PI / 2f) * (180f / Math.PI)));

        // calculate the deltas to be applied each move
        this.xDelta = (int) (BASE_MISSILE_MOVE_PIXELS * (float) Math.cos(angle));
        this.yDelta = (int) (BASE_MISSILE_MOVE_PIXELS * (float) Math.sin(angle));
    }
}
