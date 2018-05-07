package com.danosoftware.galaxyforce.controller.game;

import com.danosoftware.galaxyforce.controller.interfaces.BaseController;
import com.danosoftware.galaxyforce.controller.interfaces.TouchBaseControllerModel;
import com.danosoftware.galaxyforce.controller.interfaces.TouchController;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.interfaces.Input.TouchEvent;
import com.danosoftware.galaxyforce.view.Vector2;

public class ControllerDrag implements BaseController, TouchController
{
    // pointer to finger currently controlling this drag
    public static int DRAG_POINTER = -1;

    /* contains reference to game model */
    private GameHandler model = null;

    /* reference to drag model */
    private TouchBaseControllerModel dragModel = null;

    public ControllerDrag(GameHandler model, TouchBaseControllerModel dragModel)
    {
        this.model = model;
        this.dragModel = dragModel;
    }

    @Override
    public boolean processTouchEvent(TouchEvent event, Vector2 touchPoint, int pointerID, float deltaTime)
    {
        boolean processed = false;

        // on touch down: set drag pointer, set centre based on base's location,
        // update touch point
        if (event.type == TouchEvent.TOUCH_DOWN && DRAG_POINTER == -1)
        {
            DRAG_POINTER = pointerID;
            dragModel.setCentre(model.getBaseX(), model.getBaseY());
            dragModel.updateTouchPoint(touchPoint.x, touchPoint.y);
            processed = true;
        }

        // on drag: set centre on base's location and update touch point
        if (event.type == TouchEvent.TOUCH_DRAGGED && pointerID == DRAG_POINTER)
        {
            dragModel.setCentre(model.getBaseX(), model.getBaseY());
            dragModel.updateTouchPoint(touchPoint.x, touchPoint.y);
            processed = true;
        }

        // on release: release touch point and reset drag pointer
        if (event.type == TouchEvent.TOUCH_UP && pointerID == DRAG_POINTER)
        {
            // reset joystick to centre
            dragModel.setCentre(model.getBaseX(), model.getBaseY());
            dragModel.releaseTouchPoint();
            DRAG_POINTER = -1;
            processed = true;
        }

        return processed;
    }

    @Override
    public void reset()
    {
        // resets centre to current base position
        dragModel.setCentre(model.getBaseX(), model.getBaseY());

        // resets weighting and sets target to base position
        dragModel.reset();
    }
}
