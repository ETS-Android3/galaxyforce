package com.danosoftware.galaxyforce.sprites.about;

import com.danosoftware.galaxyforce.buttons.interfaces.SpriteButton;
import com.danosoftware.galaxyforce.controller.interfaces.Controller;
import com.danosoftware.galaxyforce.controller.utilities.DetectButtonTouch;
import com.danosoftware.galaxyforce.interfaces.AboutModel;
import com.danosoftware.galaxyforce.model.screens.ButtonType;
import com.danosoftware.galaxyforce.sprites.game.interfaces.ButtonRectangle;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;

/**
 * Represents a level selector. Level selector has a border, level number and
 * position.
 */
public class SocialButton implements SpriteButton
{

    // reference to button's parent model
    private final AboutModel model;

    // reference to level selector button sprite
    private final ButtonRectangle buttonSprite;

    // sprites to be used for when button is up (not pressed) or down (pressed)
    private final ISpriteIdentifier spriteButtonUp;
    private final ISpriteIdentifier spriteButtonDown;

    // this button's type
    private final ButtonType buttonType;

    public SocialButton(AboutModel model, Controller controller, int xPos, int yPos, ButtonType buttonType,
            ISpriteIdentifier spriteButtonUp, ISpriteIdentifier spriteButtonDown)
    {
        this.model = model;
        this.buttonSprite = new ButtonRectangle(xPos, yPos, spriteButtonUp);
        this.buttonType = buttonType;
        this.spriteButtonUp = spriteButtonUp;
        this.spriteButtonDown = spriteButtonDown;

        // add a new menu button to controller's list of touch controllers
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

        model.processButton(buttonType);
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
