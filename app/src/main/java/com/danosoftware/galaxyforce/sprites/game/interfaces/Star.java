package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.view.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Danny
 */
public class Star extends Sprite
{
    /* number of stars to show */
    private static final int MAX_STARS = 250;

    /* max and min speed of stars - how many pixels star moves in 1 second */
    private static final int STAR_MOVE_PIXELS_MIN = 60;
    private static final int STAR_MOVE_PIXELS_MAX = 60 * 3;

    /*
     * weightings of star speed changes as direction changes. star speed slows
     * down from 1 to 0 and then increases negatively from 0 to -1 (i.e.
     * opposite direction)
     */
    private static final float UP_TO_DOWN_SPEED_WEIGHTING[] =
    { 1f, (3f / 4f), (2f / 4f), (1f / 4f), 0f, (-1f / 4f), (-2f / 4f), (-3f / 4f), -1f };
    private static final float DOWN_TO_UP_SPEED_WEIGHTING[] =
    { -1f, (-3f / 4f), (-2f / 4f), (-1f / 4f), -0f, (1f / 4f), (2f / 4f), (3f / 4f), 1f };

    /*
     * current speed and direction weighting of stars speed - set-up in static
     * initialiser and altered by a direction change.
     */
    private static Direction DIRECTION;
    private static float SPEED_WEIGHTING;

    // default speed of star
    private float speed;

    // used to help calculate how far star moves each time
    private float starPosition;

    // stores the height of the visible screen area
    private int height;

    // star's animation loop
    private Animation animation;

    // state time used for how far we are through animation cycles
    private float animationStateTime;

    // private constructor
    private Star(int xStart, int yStart, int height, Animation animation, float animationStateTime, float speed)
    {
        // set star's start position and initial sprite from animation
        super(xStart, yStart, animation.getKeyFrame(animationStateTime, Animation.ANIMATION_LOOPING), true);

        this.height = height;
        this.starPosition = yStart;

        this.animation = animation;
        this.animationStateTime = animationStateTime;
        this.speed = speed;

        Star.DIRECTION = Direction.DOWN;
    }

    /**
     * initialise background stars. returns a list of stars in random positions.
     */
    public static List<Star> setupStars(int width, int height, Animation[] starAnimations)
    {
        List<Star> stars = new ArrayList<Star>();

        for (int i = 0; i < Star.MAX_STARS; i++)
        {
            int x = (int) (width * Math.random());
            int y = (int) (height * Math.random());

            Animation animation = getRandomAnimation(starAnimations);
            float animationStateTime = getRandomAnimationStartTime();
            float speed = getSpeedRandom();

            stars.add(new Star(x, y, height, animation, animationStateTime, speed));
        }

        // reset stars direction and speed
        Star.reset();

        return stars;
    }

    /**
     * reset stars back to default speed and direction
     */
    public static void reset()
    {
        /*
         * reset speed weighting so that stars move in the correct direction and
         * correct speed
         */
        Star.SPEED_WEIGHTING = -1f;
        Star.DIRECTION = Direction.DOWN;
    }

    /**
     * change star speed based on current direction and direction change
     * transition time.
     * 
     * @param directionChangeStateTime
     */
    public static void updateDirectionChange(float directionChangeStateTime)
    {
        // choose direction array (based on direction)
        float[] starSpeedWeightingArray = (DIRECTION == Direction.UP ? Star.UP_TO_DOWN_SPEED_WEIGHTING : DOWN_TO_UP_SPEED_WEIGHTING);

        // get speed weighting based on the direction state time
        Star.SPEED_WEIGHTING = getSpeedWeighting(starSpeedWeightingArray, directionChangeStateTime,
                GameConstants.DIRECTION_CHANGE_TRANSITION_TIME);
    }

    /**
     * Complete a direction change so switch direction and speed weighting.
     */
    public static void completeDirectionChange()
    {
        DIRECTION = (DIRECTION == Direction.DOWN ? Direction.UP : Direction.DOWN);
        SPEED_WEIGHTING = (DIRECTION == Direction.DOWN ? -1f : 1f);
    }

    /**
     * move this star using the current speed and weighting.
     * 
     * if star leaves the screen then re-position on the other side.
     */
    public void move(float deltaTime)
    {
        /* calculate new start position based on timing and speed weightings */
        starPosition += (Star.SPEED_WEIGHTING * speed * deltaTime);

        /*
         * if star has reached the top or bottom of screen then re-position the
         * star on the other side.
         * 
         * ideally we should also factor in the height of the specific star
         * sprite but since stars are so small this would not be noticeable so
         * has been removed to speed up the calculation.
         */
        if (starPosition < 0)
        {
            starPosition = height + starPosition;
        }
        else if (starPosition > height)
        {
            starPosition = starPosition - height;
        }

        // place star at new position
        setY((int) starPosition);

        // set sprite using animation loop
        animationStateTime = animationStateTime + deltaTime;
        setSpriteIdentifier(animation.getKeyFrame(animationStateTime, Animation.ANIMATION_LOOPING));
    }

    /**
     * set random speed between STAR_MOVE_PIXELS_MIN and STAR_MOVE_PIXELS_MAX
     */
    private static float getSpeedRandom()
    {
        return ((int) (((STAR_MOVE_PIXELS_MAX + 1 - STAR_MOVE_PIXELS_MIN) * Math.random()) + STAR_MOVE_PIXELS_MIN));
    }

    /**
     * get random animation
     */
    private static Animation getRandomAnimation(Animation[] starAnimations)
    {
        int index = (int) (starAnimations.length * Math.random());
        return (starAnimations[index]);
    }

    /**
     * get random animation start time so all stars are not synchronised
     */
    private static float getRandomAnimationStartTime()
    {
        return (float) (1f * Math.random());
    }

    /**
     * return a speed weighting based on the current direction change state
     * time. used when changing direction.
     * 
     * @param starSpeedWeightingArray
     * @param directionChangeStateTime
     * @param transitionDuration
     * @return speed weighting
     */
    private static float getSpeedWeighting(float starSpeedWeightingArray[], float directionChangeStateTime, float transitionDuration)
    {
        int transistionNumber = (int) (directionChangeStateTime / transitionDuration);

        transistionNumber = Math.min(starSpeedWeightingArray.length - 1, transistionNumber);

        return starSpeedWeightingArray[transistionNumber];
    }
}
