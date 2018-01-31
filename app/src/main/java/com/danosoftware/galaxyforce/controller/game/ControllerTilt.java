package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.BaseControllerTilt;
import com.danosoftware.galaxyforce.controller.interfaces.TiltBaseControllerModel;
import com.danosoftware.galaxyforce.interfaces.Input;

/**
 * Used to update the tilt model with the current weightings when called by the
 * controller.
 * 
 * Reads accelerometer values and uses them to set weightings.
 */
public class ControllerTilt implements BaseControllerTilt
{
    /* max range of the accelerometer */
    private static final float MAX_RANGE = 2f;

    /* Tilt Model */
    TiltBaseControllerModel tiltModel = null;

    public ControllerTilt(TiltBaseControllerModel tiltModel)
    {
        this.tiltModel = tiltModel;
    }

    @Override
    public void updateController(Input input)
    {
        float x = input.getAccelX();
        float y = input.getAccelY();
        float z = input.getAccelZ();

        // limit x, y, z to -MAX_RANGE to +MAX_RANGE
        x = Math.min(x, MAX_RANGE);
        x = Math.max(x, -MAX_RANGE);
        y = Math.min(y, MAX_RANGE);
        y = Math.max(y, -MAX_RANGE);
        z = Math.min(z, MAX_RANGE);
        z = Math.max(z, -MAX_RANGE);

        /*
         * results for acceleronmeter.
         * 
         * xWeight = -x / MAX_RANGE.
         * 
         * yWeight = y / MAX_RANGE.
         * 
         * zWeight = z /MAX_RANGE;
         */

        // send weighting back to model
        tiltModel.setWeighting(-x / MAX_RANGE, y / MAX_RANGE);
    }

    @Override
    public void reset()
    {
        // no action for tilt control
    }
}
