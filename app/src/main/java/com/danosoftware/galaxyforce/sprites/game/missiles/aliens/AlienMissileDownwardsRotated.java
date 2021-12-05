package com.danosoftware.galaxyforce.sprites.game.missiles.aliens;

import static com.danosoftware.galaxyforce.sprites.game.missiles.aliens.AlienMissileRotater.calculateAngle;
import static com.danosoftware.galaxyforce.utilities.OffScreenTester.offScreenAnySide;

import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.sprites.game.bases.IBasePrimary;
import com.danosoftware.galaxyforce.view.Animation;

/**
 * Alien missile that targets the supplied base.
 * <p>
 * The missile will calculate it's direction on construction and will continue
 * to move in that direction until off the screen.
 * <p>
 * This missile will not change direction once fired even if the targeted base
 * moves.
 * <p>
 * This missile will not fire upwards. If the missile starts below the base,
 * the missile will fire directly downwards instead.
 */
public class AlienMissileDownwardsRotated extends AbstractAlienMissile {

    private static final float DOWNWARDS = (float) Math.atan2(-1, 0);

    // offset applied to x and y every move
    private final int xDelta;
    private final int yDelta;

    // created rotated missile aimed at base
    public AlienMissileDownwardsRotated(
            int xStart,
            int yStart,
            final Animation animation,
            final AlienMissileSpeed missileSpeed,
            final IBasePrimary base) {

        super(
                animation,
                xStart,
                yStart);

        // if missile is starting above base then calculate a direct angle to the base.
        final float angle;
      if (this.y > base.y()) {
        final AlienMissileRotateCalculation calculation = calculateAngle(this, base);
        angle = calculation.getAngle();
        rotate(calculation.getRotation());
      } else {
        // otherwise fire directly downwards
        angle = DOWNWARDS;
      }

        // calculate the deltas to be applied each move
        this.xDelta = (int) (missileSpeed.getSpeed() * (float) Math.cos(angle));
        this.yDelta = (int) (missileSpeed.getSpeed() * (float) Math.sin(angle));
    }

    @Override
    public void animate(float deltaTime) {
        super.animate(deltaTime);

        // move missile by calculated deltas
        moveByDelta(
                (int) (xDelta * deltaTime),
                (int) (yDelta * deltaTime));

        // if missile is now off screen then destroy it
        if (offScreenAnySide(this)) {
            destroy();
        }
    }
}