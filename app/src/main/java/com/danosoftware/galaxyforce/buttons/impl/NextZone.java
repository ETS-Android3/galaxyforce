package com.danosoftware.galaxyforce.buttons.impl;

import com.danosoftware.galaxyforce.buttons.interfaces.SpriteButton;
import com.danosoftware.galaxyforce.controller.interfaces.Controller;
import com.danosoftware.galaxyforce.controller.utilities.DetectButtonTouch;
import com.danosoftware.galaxyforce.model.screens.SelectLevelModel;
import com.danosoftware.galaxyforce.sprites.game.interfaces.ButtonRectangle;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;

public class NextZone implements SpriteButton
{

    // wanted buffer around the button
    private final int buffer = 32;

    // reference to button's parent model
    private final SelectLevelModel model;

    // reference to button's parent zone
    private final int zone;

    // reference to level selector button sprite
    private final ButtonRectangle levelSprite;

    // sprites to be used for when button is up (not pressed) or down (pressed)
    private final ISpriteIdentifier spriteButtonUp;
    private final ISpriteIdentifier spriteButtonDown;

    public NextZone(SelectLevelModel model, Controller controller, int xPos, int yPos, int zone, ISpriteIdentifier spriteButtonUp,
            ISpriteIdentifier spriteButtonDown)
    {
        this.model = model;
        this.levelSprite = new ButtonRectangle(xPos, yPos, spriteButtonUp, buffer, buffer);
        this.spriteButtonUp = spriteButtonUp;
        this.spriteButtonDown = spriteButtonDown;
        this.zone = zone;

        // add a new menu button to controller's list of touch controllers
        controller.addTouchController(new DetectButtonTouch(this));

    }

    @Override
    public Rectangle getBounds()
    {
        return levelSprite.getBounds();
    }

    @Override
    public void buttonUp()
    {
        levelSprite.setSpriteIdentifier(spriteButtonUp);

        model.changeZone(zone + 1);
    }

    @Override
    public void buttonDown()
    {
        levelSprite.setSpriteIdentifier(spriteButtonDown);
    }

    @Override
    public void buttonReleased()
    {
        levelSprite.setSpriteIdentifier(spriteButtonUp);
    }

    @Override
    public ButtonRectangle getSprite()
    {
        return levelSprite;
    }

}
