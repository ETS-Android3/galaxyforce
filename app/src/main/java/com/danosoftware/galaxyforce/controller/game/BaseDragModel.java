package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.ControllerBase;
import com.danosoftware.galaxyforce.controller.interfaces.TouchBaseControllerModel;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.interfaces.DirectionListener;
import com.danosoftware.galaxyforce.sprites.game.interfaces.JoystickPoint;
import com.danosoftware.galaxyforce.sprites.game.interfaces.Sprite;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

import java.util.ArrayList;
import java.util.List;

public class BaseDragModel implements TouchBaseControllerModel, DirectionListener
{

    /* logger tag */
    private static final String TAG = "DragModelImpl";

    /* drag sprite animation */
    private static final Animation DRAG_ANIMATION = new Animation(0.75f, GameSpriteIdentifier.DRAG_1, GameSpriteIdentifier.DRAG_2);

    // how far new touch point must be away from current touch point to be
    // updated
    private static final float TOUCH_MOVE_RADIUS = 1;

    // how far current touch point must be away from base point to move base
    // private static final float BASE_MOVE_RADIUS_LARGE = 10;
    // private static final float BASE_MOVE_RADIUS_SMALL = 5;

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

    // offset from touch point to base position
    private static final int BASE_X_OFFSET = 0;
    private static final int BASE_Y_OFFSET = -96;

    // direction of base
    private Direction direction;

    // stores the calculated weighting (ranges from 0 to 1)
    // used to determine how far to move base
    private float weightingX = 0f;
    private float weightingY = 0f;

    // stores the position of the touch point (target position for base)
    private float targetX = 0f;
    private float targetY = 0f;

    // store the position of the base
    // used in calculations relative to touch points
    private float baseX = 0f;
    private float baseY = 0f;

    /*
     * does weighting need re-calculating - to be set to true follwoing any
     * change to actual or base positions
     */
    private boolean recalculateWeighting = false;

    /* reference to list of sprites */
    private Sprite dragSprite = null;
    List<Sprite> sprites = new ArrayList<Sprite>();

    /* contains reference to game model */
    private GameHandler model = null;

    /* contains reference to game model */
    private ControllerBase controller = null;

    /* timer to store time since animation timer reset */
    private float animationTime = 0f;

    public BaseDragModel(GameHandler model, ControllerBase controller)
    {
        this.model = model;
        this.controller = controller;

        this.weightingX = 0f;
        this.weightingY = 0f;

        this.direction = Direction.UP;

        dragSprite = new JoystickPoint(0, 0, DRAG_ANIMATION.getKeyFrame(0, Animation.ANIMATION_LOOPING));

        sprites = new ArrayList<Sprite>();
        sprites.add(dragSprite);

        // new
        // add this drag controller as a direction listener
        model.addDirectionListener(this);

        // set controller's base controller to use drag
        controller.setBaseController(new ControllerDrag(model, this));
    }

    @Override
    public void update(float deltaTime)
    {
        animationTime += deltaTime;
        dragSprite.setSpriteIdentifier(DRAG_ANIMATION.getKeyFrame(animationTime, Animation.ANIMATION_LOOPING));
    }

    // when finger released, reset weightings so base no longer moves
    @Override
    public void releaseTouchPoint()
    {
        // reset weighting so that base stops moving
        this.weightingX = 0f;
        this.weightingY = 0f;

        // reset target to current base position when finger lifted
        this.targetX = this.baseX;
        this.targetY = this.baseY;

        // show drag sprite when touch released
        sprites.clear();
        sprites.add(dragSprite);
        animationTime = 0f;
    }

    // no action for drag implementation
    @Override
    public void setCentre(float centreX, float centreY)
    {
        this.baseX = centreX + BaseDragModel.BASE_X_OFFSET;

        // if direction is UP use default Y offset
        // if direction is DOWN use inverse of default Y offset
        this.baseY = centreY + ((direction == Direction.UP ? 1 : -1) * BaseDragModel.BASE_Y_OFFSET);

        // ensures weighting is recalculated to avoid jitter
        recalculateWeighting = true;

        dragSprite.setX((int) baseX);
        dragSprite.setY((int) baseY);
    }

    // update finger position when touched down or dragged
    @Override
    public void updateTouchPoint(float touchX, float touchY)
    {
        // to avoid base jitter - check how far new touch point is away from
        // current touch point.
        // if distance is small then don't change current point.
        float touchDistance = (float) Math.sqrt(Math.pow(touchX - targetX, 2) + Math.pow(touchY - targetY, 2));
        if (touchDistance > BaseDragModel.TOUCH_MOVE_RADIUS)
        {
            // update actual position based on touch point
            this.targetX = touchX;
            this.targetY = touchY;
            recalculateWeighting = true;

            // remove drag sprite when touching screen
            sprites.clear();
        }
        else
        {
            // if very small movement then exit
            return;
        }
    }

    @Override
    public float getWeightingX()
    {

        // new
        setCentre(model.getBaseX(), model.getBaseY());

        if (recalculateWeighting)
        {
            updateWeighting();
        }
        return weightingX;
    }

    @Override
    public float getWeightingY()
    {

        // new
        setCentre(model.getBaseX(), model.getBaseY());

        if (recalculateWeighting)
        {
            updateWeighting();
        }
        return weightingY;
    }

    @Override
    public List<Sprite> getSprites()
    {
        return sprites;
    }

    private void updateWeighting()
    {
        // to avoid jitter when moving - check how far base is away from current
        // touch point.
        // if distance is small then don't move base
        float baseDistance = (float) Math.sqrt(Math.pow(targetX - baseX, 2) + Math.pow(targetY - baseY, 2));

        // if distance large then calculate weighting purely on angle between
        // base and current touch point
        if (baseDistance > BaseDragModel.BASE_MOVE_RADIUS_LARGE)
        {
            // calculate angle from circle centre to touch point
            float theta = (float) Math.atan2(targetY - baseY, targetX - baseX);

            // atan2 doesn't have limitations of atan but is PI/2 greater than
            // expected so manually correct this.
            theta = (float) (Math.PI / 2f) - theta;

            // calculate weighting (used to calculate how far to move base's x
            // and y). Ranges from 0 to 1.
            this.weightingX = (float) Math.sin(theta);
            this.weightingY = (float) Math.cos(theta);
        }

        // if distance small then calculate weighting based on angle but
        // multiply by a calculated weight. Much smoother movement for small
        // moves than previous calculation.
        else if (baseDistance > BaseDragModel.BASE_MOVE_RADIUS_SMALL)
        {
            // calculate angle from circle centre to touch point
            float theta = (float) Math.atan2(targetY - baseY, targetX - baseX);

            // atan2 doesn't have limitations of atan but is PI/2 greater than
            // expected so manually correct this.
            theta = (float) (Math.PI / 2f) - theta;

            // calculate weighting using distance and threshold.
            // result ranges from 0-1.
            float distanceWeight = (baseDistance - BaseDragModel.BASE_MOVE_RADIUS_SMALL)
                    / (BaseDragModel.BASE_MOVE_RADIUS_LARGE - BaseDragModel.BASE_MOVE_RADIUS_SMALL);

            this.weightingX = (float) (distanceWeight * Math.sin(theta));
            this.weightingY = (float) (distanceWeight * Math.cos(theta));
        }
        // if tiny movements (maybe involuntary) then do not move
        else
        {
            this.weightingX = 0;
            this.weightingY = 0;
        }

        recalculateWeighting = false;
    }

    @Override
    public void completeDirectionChange()
    {
        // show drag sprite when direction change completed
        sprites.clear();
        sprites.add(dragSprite);
        animationTime = 0f;
    }

    @Override
    public void reset()
    {
        // reset direction using base if available
        if (model.getBase() != null)
        {
            direction = model.getBase().getDirection();
        }
        else
        {
            direction = Direction.UP;
        }

        // resets centre to current base position
        setCentre(model.getBaseX(), model.getBaseY());

        // reset weighting so that base stops moving
        releaseTouchPoint();
    }

    @Override
    public void startDirectionChange()
    {
        /*
         * calculate base's real centre by reversing the offsets - using the
         * current direction
         */
        float realBaseX = this.baseX + BaseDragModel.BASE_X_OFFSET;
        float realBaseY = this.baseY + ((this.direction == Direction.UP ? -1 : 1) * BaseDragModel.BASE_Y_OFFSET);

        /* reverse the current direction */
        this.direction = (direction == Direction.UP ? Direction.DOWN : Direction.UP);

        // reset centre which will use new direction
        setCentre(realBaseX, realBaseY);

        updateTouchPoint(this.baseX, this.baseY);

        // remove drag sprite when starting flip
        sprites.clear();
    }

}
