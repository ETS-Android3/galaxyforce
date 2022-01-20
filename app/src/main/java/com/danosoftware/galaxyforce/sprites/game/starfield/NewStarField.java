package com.danosoftware.galaxyforce.sprites.game.starfield;

import static com.danosoftware.galaxyforce.waves.utilities.Randomiser.random;
import static com.danosoftware.galaxyforce.waves.utilities.Randomiser.randomFloat;

/**
 * Star field of multiple animated stars.
 * <p>
 * Stars are created from a supplied template.
 * <p>
 * The created stars can be fast-forwarded to the current state. This allows
 * a new starfield to be created using different sprite IDs that should be
 * the identical state to the previous starfield.
 * <p>
 * This ensures seamless animation of the starfield when switching screens.
 */
public class NewStarField {

    /* number of stars to show */
    private static final int NUMBER_OF_STARS = 250;

    // Star speed will either be slow, normal or fast.
    // Stars with an index less than "slow index" will be slow.
    // Stars with an index greater than "fast index" will be fast.
    // All others will be normal speed.
    private static final int SLOW_STAR_INDEX = NUMBER_OF_STARS / 3;
    private static final int FAST_STAR_INDEX = SLOW_STAR_INDEX * 2;

    // stars in our star-field
    private final Star2[] starField;

    // height of our playing area
    private final int height;

    public NewStarField(int width, int height) {
        this.starField = setupStars(width, height);
        this.height = height;
    }

    /**
     * Animate our star-field. Each star will descend according to time
     * since last animation frame and speed of star.
     *
     * @param deltaTime - time since last animation frame
     */
    public void animate(float deltaTime) {

        // compute distances moved for each star speed
        final float slowDistanceDelta = distanceDelta(StarSpeed.SLOW, deltaTime);
        final float normalDistanceDelta = distanceDelta(StarSpeed.MEDIUM, deltaTime);
        final float fastDistanceDelta = distanceDelta(StarSpeed.FAST, deltaTime);

        for (int idx = 0; idx < starField.length; idx++) {

            // move star down screen according to speed.
            float starY;
            if (idx < SLOW_STAR_INDEX) {
                starY = starField[idx].y - slowDistanceDelta;
            } else if (idx < FAST_STAR_INDEX) {
                starY = starField[idx].y - normalDistanceDelta;
            } else {
                starY = starField[idx].y - fastDistanceDelta;
            }

            // if star has reached the bottom of screen then re-position at the top.
            if (starY < 0) {
                starY = height + starY;
            }

            starField[idx].y = starY;
        }
    }

    /**
     * return the star-field
     */
    public Star2[] getStarField() {
        return starField;
    }

    /**
     * initialise background stars.
     * returns an array of stars in random positions with random colours.
     */
    private Star2[] setupStars(int width, int height) {
        Star2[] stars = new Star2[NUMBER_OF_STARS];

        for (int idx = 0; idx < stars.length; idx++) {
            float x = width * randomFloat();
            float y = height * randomFloat();
            StarColour starColour = getRandomStarColour();

            stars[idx] = Star2
                    .builder()
                    .x(x)
                    .y(y)
                    .colour(starColour.getRgb())
                    .build() ;
        }

        return stars;
    }

    /**
     * return a random star colour
     */
    private StarColour getRandomStarColour() {
        StarColour[] possibleColours = StarColour.values();
        int colourIndex = (int) (possibleColours.length * random());
        return possibleColours[colourIndex];
    }

    /**
     * compute the distance a star has moved based on the speed and time elapsed.
     */
    private float distanceDelta(StarSpeed speed, float timeElapsed) {
        return (speed.getPixelsPerSecond() * timeElapsed) % height;
    }
}
