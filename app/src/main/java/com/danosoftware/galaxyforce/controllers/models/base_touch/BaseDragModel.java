package com.danosoftware.galaxyforce.controllers.models.base_touch;

import com.danosoftware.galaxyforce.sprites.game.bases.IBasePrimary;

public class BaseDragModel implements TouchBaseControllerModel {

    // how far new touch point must be away from current touch point to be
    // updated
    private static final float TOUCH_MOVE_RADIUS = 1;

    private static final double HALF_PI = (float) Math.PI / 2f;

    /*
     * large movement increased as base movement can be very jittery when the
     * game is running slowly as base appears to be overshoot target so next
     * move goes back in other opposite direction as so on. the slower the game
     * is, the more the base will move and so the greater chance of overshooting
     * we have.
     *
     * game optimised to run at 1/60 fps so increase BASE_MOVE_RADIUS_LARGE by
     * ratio of actual speed to ideal speed.
     */
    private static final float BASE_MOVE_RADIUS_LARGE = 20;
    private static final float BASE_MOVE_RADIUS_SMALL = 5;
    private static final float BASE_MOVE_RADIUS_DELTA = BASE_MOVE_RADIUS_LARGE - BASE_MOVE_RADIUS_SMALL;

    // offset from touch point to base position
    private static final int BASE_Y_OFFSET = -96;

    // stores the position of the touch point (target position for base)
    private float targetX;
    private float targetY;

    // reference to base being moved
    private final IBasePrimary base;

    public BaseDragModel(IBasePrimary base) {
        this.base = base;
        this.targetX = base.x();
        this.targetY = base.y() + BASE_Y_OFFSET;
    }

    // when finger released, reset weightings so base no longer moves
    @Override
    public void releaseTouchPoint() {
        // no action - should we tell base?
    }

    // update finger position when touched down or dragged
    @Override
    public void updateTouchPoint(float touchX, float touchY, float deltaTime) {

        /*
        to avoid base jitter - check how far new touch point is away from
        current touch point.
        if distance is small then don't change current point.
         */
        float touchDistance = (float) Math.sqrt(
                Math.pow(touchX - targetX, 2) + Math.pow(touchY - targetY, 2));
        if (touchDistance > TOUCH_MOVE_RADIUS) {
            // update target position based on new touch point
            this.targetX = touchX;
            this.targetY = touchY;
        }

        // ensures weighting is recalculated to avoid jitter
        updateWeighting(
                targetX - base.x(),
                targetY - (base.y() + BASE_Y_OFFSET),
                deltaTime);
    }

    private void updateWeighting(float deltaX, float deltaY, float deltaTime) {

        /*
        to avoid jitter when moving - check how far base is away from current
        touch point.
         */
        float baseDistance = (float) Math.sqrt(
                (deltaX * deltaX) + (deltaY * deltaY));

        /*
         if distance large then calculate weighting purely on angle between
         base and current touch point
          */
        if (baseDistance > BASE_MOVE_RADIUS_LARGE) {
            /*
             calculate angle from circle centre to touch point
             atan2 doesn't have limitations of atan but is PI/2 greater than
             expected so manually correct this.
              */
            float theta = (float) (HALF_PI - Math.atan2(deltaY, deltaX));

            /*
            calculate weighting (used to calculate how far to move base's x
            and y). Ranges from 0 to 1.
             */
            base.moveBase(
                    (float) Math.sin(theta),
                    (float) Math.cos(theta),
                    deltaTime);
        }

        /*
         if distance small then calculate weighting based on angle but
         multiply by a calculated weight. Much smoother movement for small
         moves than previous calculation.
          */
        else if (baseDistance > BASE_MOVE_RADIUS_SMALL) {
            /*
             calculate angle from circle centre to touch point
             atan2 doesn't have limitations of atan but is PI/2 greater than
             expected so manually correct this.
              */
            float theta = (float) (HALF_PI - Math.atan2(deltaY, deltaX));

            /*
             calculate weighting using distance and threshold.
             result ranges from 0-1.
              */
            float distanceWeight = (baseDistance - BASE_MOVE_RADIUS_SMALL)
                    / (BASE_MOVE_RADIUS_DELTA);
            base.moveBase(
                    (float) (distanceWeight * Math.sin(theta)),
                    (float) (distanceWeight * Math.cos(theta)),
                    deltaTime);
        }
        // if tiny movements (maybe involuntary) then do not move
//        else
//        {
//            this.weightingX = 0;
//            this.weightingY = 0;
//        }

//        recalculateWeighting = false;
    }
}
