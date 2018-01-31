package com.danosoftware.galaxyforce.buttons.interfaces;

import com.danosoftware.galaxyforce.sprites.game.interfaces.ButtonRectangle;

/**
 * Button that can be selected by a user.
 */
public interface SpriteButton extends Button
{
    /**
     * Get the button's sprite.
     * 
     * @return button's sprite
     */
    public ButtonRectangle getSprite();
}
