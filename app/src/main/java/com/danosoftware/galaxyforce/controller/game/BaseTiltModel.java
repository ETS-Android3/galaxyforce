package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.ControllerBase;
import com.danosoftware.galaxyforce.controller.interfaces.TiltBaseControllerModel;
import com.danosoftware.galaxyforce.sprites.game.interfaces.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Base move model for a tilt controller (i.e. accelerometer).
 */
public class BaseTiltModel implements TiltBaseControllerModel
{

    // model's current x, y weightings
    private float xWeighting = 0f;
    private float yWeighting = 0f;

    /* list of sprites to be returned */
    private List<Sprite> sprites = null;

    public BaseTiltModel(ControllerBase controller)
    {
        // empty sprite list returned when requested
        this.sprites = new ArrayList<Sprite>();

        // set tilt controller as current base controller
        controller.setBaseController(new ControllerTilt(this));
    }

    @Override
    public float getWeightingX()
    {
        return xWeighting;
    }

    @Override
    public float getWeightingY()
    {
        return yWeighting;
    }

    @Override
    public List<Sprite> getSprites()
    {
        // return empty list of sprites
        return sprites;
    }

    @Override
    public void reset()
    {
        // no action for tilt control
    }

    @Override
    public void setWeighting(float weightingX, float weightingY)
    {
        xWeighting = weightingX;
        yWeighting = weightingY;
    }

    @Override
    public void update(float deltaTime)
    {
        // no implementation
    }

}
