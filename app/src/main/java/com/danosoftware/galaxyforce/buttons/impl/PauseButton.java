package com.danosoftware.galaxyforce.buttons.impl;

import com.danosoftware.galaxyforce.buttons.interfaces.SpriteButton;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controller.interfaces.Controller;
import com.danosoftware.galaxyforce.controller.utilities.DetectButtonTouch;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.interfaces.ButtonRectangle;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;

/**
 * Represents a pause button. Calls game model when pressed.
 */
public class PauseButton implements SpriteButton
{

    /* Constants for the pause button's position */
    public static final int PAUSE_BUTTON_X = GameConstants.GAME_WIDTH - 50 - 10;
    public static final int PAUSE_BUTTON_Y = GameConstants.GAME_HEIGHT - 50 - 10;

    // reference to button's parent model
    private final GameHandler model;

    // reference to button sprite
    private final ButtonRectangle buttonSprite;

    // sprites to be used for when button is up (not pressed) or down (pressed)
    private final ISpriteIdentifier spriteButtonUp;
    private final ISpriteIdentifier spriteButtonDown;

    public PauseButton(GameHandler model, Controller controller, ISpriteIdentifier spriteButtonUp, ISpriteIdentifier spriteButtonDown)
    {
        this.model = model;
        this.spriteButtonUp = spriteButtonUp;
        this.spriteButtonDown = spriteButtonDown;
        // create button and increase bounds by a 10px border.
        this.buttonSprite = new ButtonRectangle(PAUSE_BUTTON_X, PAUSE_BUTTON_Y, spriteButtonUp, 10, 10);

        // add a new button to controller's list of touch controllers
        controller.addTouchController(new DetectButtonTouch(this));
    }

    @Override
    public Rectangle getBounds()
    {
        return buttonSprite.getBounds();
    }

    @Override
    public void buttonUp()
    {
        buttonSprite.setSpriteIdentifier(spriteButtonUp);
        model.pause();
    }

    @Override
    public void buttonDown()
    {
        buttonSprite.setSpriteIdentifier(spriteButtonDown);
    }

    @Override
    public void buttonReleased()
    {
        buttonSprite.setSpriteIdentifier(spriteButtonUp);
    }

    @Override
    public ButtonRectangle getSprite()
    {
        return buttonSprite;
    }
}
