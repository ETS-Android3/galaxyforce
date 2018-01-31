package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.BaseController;
import com.danosoftware.galaxyforce.controller.interfaces.TouchBaseControllerModel;
import com.danosoftware.galaxyforce.controller.interfaces.TouchController;
import com.danosoftware.galaxyforce.interfaces.Input.TouchEvent;
import com.danosoftware.galaxyforce.view.Vector2;

public class ControllerJoystick implements BaseController, TouchController
{
    // pointer to finger currently controlling joystick
    public static int JOYSTICK_POINTER = -1;

    /* reference to joystick model */
    private TouchBaseControllerModel joystickModel = null;

    public ControllerJoystick(TouchBaseControllerModel joystickModel)
    {
        this.joystickModel = joystickModel;
    }

    /*
     * wanted joystick behaviour: on touch within joystick area, create pointer
     * for finger and update joystick. if same finger is dragged outside area,
     * still allow joystick updates. if same finger is released (anywhere on
     * screen) reset joystick and pointer.
     */
    @Override
    public boolean processTouchEvent(TouchEvent event, Vector2 touchPoint, int pointerID, float deltaTime)
    {
        boolean processed = false;

        // re-centre joystick centre when user touches anywhere on screen
        if (event.type == TouchEvent.TOUCH_DOWN)
        {
            JOYSTICK_POINTER = pointerID;

            // move joystick to new position. if we use a fixed joystick
            // position, we should replace this with updateTouchPoint().
            joystickModel.setCentre(touchPoint.x, touchPoint.y);

            processed = true;
        }

        // if joystick finger is dragged (outside joystick area) still
        // allow joystick updating
        //
        if (event.type == TouchEvent.TOUCH_DRAGGED && pointerID == JOYSTICK_POINTER)
        {
            joystickModel.updateTouchPoint(touchPoint.x, touchPoint.y);
            processed = true;
        }

        // if joystick finger is released, reset joystick (even if
        // outside joystick area)
        //
        if (event.type == TouchEvent.TOUCH_UP && pointerID == JOYSTICK_POINTER)
        {
            // reset joystick to centre
            joystickModel.releaseTouchPoint();
            JOYSTICK_POINTER = -1;
            processed = true;
        }

        return processed;
    }

    @Override
    public void reset()
    {
        // resets weighting and sets target to base position
        joystickModel.reset();
    }
}
