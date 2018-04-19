package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.behaviours.ExplodeBehaviourSimple;
import com.danosoftware.galaxyforce.sprites.game.behaviours.FireDisabled;
import com.danosoftware.galaxyforce.sprites.game.behaviours.PowerUpRandom;
import com.danosoftware.galaxyforce.sprites.game.behaviours.SpawnDisabled;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

public class AlienHunter extends SpriteAlien {
    /*
     * ******************************************************
     * PRIVATE STATIC VARIABLES
     * ******************************************************
     */

    /* energy of this sprite */
    private static final int ENERGY = 10;

    /* how much energy will be lost by another sprite when this sprite hits it */
    private static final int HIT_ENERGY = 2;

    /* chance that this alien will generate a power-up when destroyed */
    private static final double CHANCE_OF_POWER_UP = 0.2D;

    /* distance alien can move each cycle in pixels each second */
    public static final int ALIEN_MOVE_PIXELS = 5 * 60;

    /* time delay between alien direction changes */
    public static final float ALIEN_DIRECTION_CHANGE_DELAY = 0.1f;

    /* maximum alien change direction in radians */
    public static final float MAX_DIRECTION_CHANGE_ANGLE = 0.3f;

    // alien animation
    private static final Animation ANIMATION = new Animation(0f, GameSpriteIdentifier.ALIEN_HELMET);

    /*
     * ******************************************************
     * PRIVATE INSTANCE VARIABLES
     * ******************************************************
     */

    /* current for sprite rotation */
    private float angle;


    /* how many seconds to delay before alien starts to follow path */
    private float timeDelayStart;

    /* variable to store time passed since last alien direction change */
    private float timeSinceLastDirectionChange = 0f;

    private final GameHandler model;

    /**
     * Create Alien Asteroid.
     *
     * @param xStart
     * @param yStart
     * @param timeDelayStart
     * @param model
     */
    public AlienHunter(int xStart, int yStart, float timeDelayStart, GameHandler model) {
        // default is that asteroids are initially invisible
        super(
                new FireDisabled(),
                new PowerUpRandom(model, CHANCE_OF_POWER_UP, PowerUpType.MISSILE_LASER, PowerUpType.ENERGY, PowerUpType.MISSILE_SPRAY),
                new SpawnDisabled(),
                new ExplodeBehaviourSimple(),
                ANIMATION,
                xStart,
                yStart,
                ENERGY,
                HIT_ENERGY,
                false
        );

        setState(SpriteState.INACTIVE);

        this.model = model;

        // set positional and movement behaviour
        this.timeDelayStart = timeDelayStart;

        // reset timer since last alien direction change
        timeSinceLastDirectionChange = 0f;

        // set starting direction angle
        this.angle = recalculateAngle(0f);
    }

    @Override
    public void move(float deltaTime) {

        /* if active then alien can move */
        if (isActive()) {
            timeSinceLastDirectionChange += deltaTime;

        /*
         * Guide alien every x seconds so the alien changes direction to
         * follow any changes in the base's position.
         */
            if (timeSinceLastDirectionChange > ALIEN_DIRECTION_CHANGE_DELAY) {

                // recalculate direction angle
                this.angle = recalculateAngle(this.angle);

                // reset timer since last missile direction change
                timeSinceLastDirectionChange = 0f;
            }


            // calculate the deltas to be applied each move
            int xDelta = (int) (ALIEN_MOVE_PIXELS * (float) Math.cos(this.angle));
            int yDelta = (int) (ALIEN_MOVE_PIXELS * (float) Math.sin(this.angle));

            // move alien by calculated deltas
            setX(getX() + (int) (xDelta * deltaTime));
            setY(getY() + (int) (yDelta * deltaTime));

            // move sprite bounds
            updateBounds();

        } else if (isInactive()) {

            /* if delayStart still > 0 then count down delay */
            if (timeDelayStart > 0) {
                timeDelayStart -= deltaTime;
            }
            /* otherwise activate alien. can only happen once! */
            else {
                setState(SpriteState.ACTIVE);
                setVisible(true);
            }
        }

        /* use superclass for any explosions */
        super.move(deltaTime);
    }

    /**
     * Recalculate the angle direction of the alien so it heads towards base.
     */
    private float recalculateAngle(float angle) {

        SpriteBase base = model.getBase();
        if (base != null) {

            // calculate angle from alien position to base
            float newAngle = (float) Math.atan2(base.getY() - getY(), base.getX() - getX());

            // if alien is off screen, return it back immediately (can get lost!).
            // don't allow sudden changes of direction. limit to MAX radians.
            // otherwise return calculated angle.
            if (getY() > GameConstants.GAME_HEIGHT + getHeight()
                    || getY() < 0 - getHeight()
                    || getX() > GameConstants.GAME_WIDTH + getWidth()
                    || getX() < 0 - getWidth()) {
                return newAngle;
            } else if ((newAngle - angle) > MAX_DIRECTION_CHANGE_ANGLE) {
                return angle + MAX_DIRECTION_CHANGE_ANGLE;
            } else if ((newAngle - angle) < MAX_DIRECTION_CHANGE_ANGLE) {
                return angle - MAX_DIRECTION_CHANGE_ANGLE;
            } else {
                return newAngle;
            }
        }

        return angle;
    }
}
