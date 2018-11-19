package com.danosoftware.galaxyforce.buttons.sprite_text_button;

import com.danosoftware.galaxyforce.buttons.sprite_button.SpriteButton;
import com.danosoftware.galaxyforce.text.Text;

/**
 * Button that has a text label.
 */
public interface SpriteTextButton extends SpriteButton {

    /**
     * Get the button's text label (if any).
     *
     * @return the text object of the button's label
     */
    Text getText();

}
