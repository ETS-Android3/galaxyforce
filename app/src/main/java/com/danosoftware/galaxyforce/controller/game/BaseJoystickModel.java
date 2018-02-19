package com.danosoftware.galaxyforce.controller.game;

import android.util.Log;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controller.interfaces.ControllerBase;
import com.danosoftware.galaxyforce.controller.interfaces.TouchBaseControllerModel;
import com.danosoftware.galaxyforce.sprites.game.interfaces.JoystickArea;
import com.danosoftware.galaxyforce.sprites.game.interfaces.JoystickPoint;
import com.danosoftware.galaxyforce.sprites.game.interfaces.Sprite;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;

import java.util.ArrayList;
import java.util.List;

public class BaseJoystickModel implements TouchBaseControllerModel
{

    /* logger tag */
    private static final String TAG = "JoystickModel";

    /* starting position of joystick and boundary radius */
    /* value of 75 would move joystick to bottom-left */
    public static int JOYSTICK_X = 125;
    public static int JOYSTICK_Y = 125;
    public static final int JOYSTICK_BOUNDARY_RADIUS = 150;

    // stores the calculated weighting of the joystick (ranges from 0 to 1)
    // used to determine how far to move base
    private float weightingX = 0f;
    private float weightingY = 0f;

    // stores the calculated position of the joystick
    // used to position joystick on screen
    private float actualX = 0f;
    private float actualY = 0f;

    // store the position of joystick when centred (default position)
    // used in calculations relative to joystick position or touch points
    private float centreX = 0f;
    private float centreY = 0f;

    // the maximum possible radius to keep the joystick within the controller
    private float radiusMax = 0f;

    /* reference to joystick sprite objects */
    private JoystickPoint joystickPoint = null;
    private JoystickArea joystickControl = null;

    /* reference to list of sprites */
    List<Sprite> sprites = null;

    public BaseJoystickModel(ControllerBase controller)
    {
        this.centreX = JOYSTICK_X;
        this.centreY = JOYSTICK_Y;

        // create sprite models
        this.joystickControl = new JoystickArea((int) centreX, (int) centreY, GameSpriteIdentifier.CONTROL);
        this.joystickPoint = new JoystickPoint((int) centreX, (int) centreY, GameSpriteIdentifier.JOYSTICK);

        // add sprite models to list
        this.sprites = new ArrayList<Sprite>();
        sprites.add(joystickControl);
        sprites.add(joystickPoint);

        this.releaseTouchPoint();

        float controlRadius = joystickControl.getWidth() / 2;
        float joystickRadius = joystickPoint.getWidth() / 2;

        // max radius is the distance from centre of joystick to centre of
        // control circle
        this.radiusMax = controlRadius - joystickRadius;

        // set controller's base controller as a joystick
        controller.setBaseController(new ControllerJoystick(this));
    }

    // move joystick back to centre and set weightings to zero.
    @Override
    public void releaseTouchPoint()
    {
        this.actualX = centreX;
        this.actualY = centreY;

        joystickPoint.setX((int) actualX);
        joystickPoint.setY((int) actualY);

        this.weightingX = 0f;
        this.weightingY = 0f;
    }

    @Override
    public void setCentre(float newCentreX, float newCentreY)
    {
        /*
         * positions centre of joystick. if position would cause some of
         * joystick to be shown off the screen, we should correct it.
         */
        float controlRadius = joystickControl.getWidth() / 2;

        // adjust if X too far right
        if ((newCentreX + controlRadius) > GameConstants.GAME_WIDTH)
        {
            newCentreX = GameConstants.GAME_WIDTH - controlRadius;
        }

        // adjust if X too far left
        if ((newCentreX - controlRadius) < 0)
        {
            newCentreX = controlRadius;
        }

        // adjust if Y too far up
        if ((newCentreY + controlRadius) > GameConstants.GAME_HEIGHT)
        {
            newCentreY = GameConstants.GAME_HEIGHT - controlRadius;
        }

        // adjust if Y too low down
        if ((newCentreY - controlRadius) < 0)
        {
            newCentreY = controlRadius;
        }

        this.centreX = newCentreX;
        this.centreY = newCentreY;

        // centre the joystick outline sprite to new centre position
        joystickControl.setX((int) newCentreX);
        joystickControl.setY((int) newCentreY);

        // reset the joystick position
        this.releaseTouchPoint();
    }

    // calculate new joystick position and weightings based on current touch
    // point.
    // actual position used for drawing joystick location (limited by max radius
    // from centre).
    // weighting used to calculate how far to move base's x and y (ranges from 0
    // to 1).
    //
    @Override
    public void updateTouchPoint(float touchX, float touchY)
    {
        // calculate the radius from the centre location to the touch location
        float radius = (float) Math.sqrt(Math.pow(touchX - centreX, 2) + Math.pow(touchY - centreY, 2));

        // joystick radius from centre can not exceed radiusMax as otherwise it
        // will go outside control circle
        radius = Math.min(radius, radiusMax);

        // calculate angle from circle centre to touch point
        float theta = (float) Math.atan2(touchY - centreY, touchX - centreX);

        // atan2 doesn't have limitations of atan but is PI/2 greater than
        // expected so manually correct this.
        theta = (float) (Math.PI / 2f) - theta;

        // calculate actual position based on calculated radius and angle
        this.actualX = (float) (radius * Math.sin(theta)) + this.centreX;
        this.actualY = (float) (radius * Math.cos(theta)) + this.centreY;

        joystickPoint.setX((int) actualX);
        joystickPoint.setY((int) actualY);

        // calculate weighting (used to calculate how far to move base's x and
        // y). Ranges from 0 to 1.
        this.weightingX = (float) ((radius / radiusMax) * Math.sin(theta));
        this.weightingY = (float) ((radius / radiusMax) * Math.cos(theta));

        Log.i(TAG, "Centre: X=" + this.centreX + ". Y=" + this.centreY + ". RadiusMax=" + this.radiusMax + ".");
        Log.i(TAG, "Calcs: radius=" + radius + ". theta=" + theta + ".");
        Log.i(TAG, "Actual: X=" + this.actualX + ". Y=" + this.actualY + ".");
        Log.i(TAG, "Weighting: X=" + this.weightingX + ". Y=" + this.weightingY + ".");

    }

    @Override
    public float getWeightingX()
    {
        return weightingX;
    }

    @Override
    public float getWeightingY()
    {
        return weightingY;
    }

    @Override
    public List<Sprite> getSprites()
    {
        return sprites;
    }

    @Override
    public void reset()
    {
        // TODO line below removed as it would move joystick location for each
        // new life.
        // is this still wanted behaviour?
        // setCentre(JOYSTICK_X, JOYSTICK_Y);

        // no additional action needed to reset joystick
    }

    @Override
    public void update(float deltaTime)
    {
        // no implementation
    }
}
