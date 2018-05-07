package com.danosoftware.galaxyforce.buttons.impl;

import com.danosoftware.galaxyforce.buttons.interfaces.Button;
import com.danosoftware.galaxyforce.controller.interfaces.Controller;
import com.danosoftware.galaxyforce.controller.utilities.DetectButtonTouch;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.utilities.Rectangle;

/**
 * Represents a button representing the moving base. When base is pressed, it
 * should flip. Calls game model when pressed.
 */
public class MovingBaseButton implements Button
{
    // reference to button's parent model
    private GameHandler model;

    public MovingBaseButton(GameHandler model, Controller controller)
    {
        this.model = model;

        // add to controller's list of touch controllers
        controller.addTouchController(new DetectButtonTouch(this));
    }

    @Override
    public Rectangle getBounds()
    {
        return model.getBaseTouchBounds();
    }

    @Override
    public void buttonUp()
    {
        // no action when button released
    }

    @Override
    public void buttonDown()
    {
        // flip immediately on button down for quick flip
        model.flipBase();
    }

    @Override
    public void buttonReleased()
    {
        // no action when button released
    }
}
