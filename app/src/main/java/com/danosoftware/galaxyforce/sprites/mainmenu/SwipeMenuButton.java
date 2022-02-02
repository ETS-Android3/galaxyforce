package com.danosoftware.galaxyforce.sprites.mainmenu;

import com.danosoftware.galaxyforce.buttons.sprite_text_button.SpriteTextButton;
import com.danosoftware.galaxyforce.models.buttons.ButtonModel;
import com.danosoftware.galaxyforce.models.buttons.ButtonType;
import com.danosoftware.galaxyforce.models.screens.level.LevelModel;
import com.danosoftware.galaxyforce.sprites.buttons.ButtonSprite;
import com.danosoftware.galaxyforce.sprites.buttons.IButtonSprite;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.utilities.Rectangle;

/**
 * Represents a menu button that responds to user clicks on the select level screen even if the user
 * is swiping. This is achieved by shifting the button's getBounds() by the current swipe offset.
 */
public class SwipeMenuButton implements SpriteTextButton {

  // reference to Text representing level number
  private final Text text;

  // reference to button's parent model
  private final ButtonModel model;

  // reference to model that allows us to determine screen swipe offset
  private final LevelModel swipeModel;

  // reference to level selector button sprite
  private final IButtonSprite buttonSprite;

  // sprites to be used for when button is up (not pressed) or down (pressed)
  private final SpriteDetails spriteButtonUp;
  private final SpriteDetails spriteButtonDown;

  // this button's type
  private final ButtonType buttonType;

  public SwipeMenuButton(
      ButtonModel model,
      LevelModel swipeModel,
      int xPos,
      int yPos,
      String text,
      ButtonType buttonType,
      SpriteDetails spriteButtonUp,
      SpriteDetails spriteButtonDown) {
    this.model = model;
    this.swipeModel = swipeModel;
    this.buttonSprite = new ButtonSprite(spriteButtonUp, xPos, yPos);
    this.buttonType = buttonType;
    this.spriteButtonUp = spriteButtonUp;
    this.spriteButtonDown = spriteButtonDown;
    this.text = Text.newTextAbsolutePosition(text, xPos, yPos);
  }

  @Override
  public Rectangle getBounds() {
    // get the current swipe offset
    float swipeOffset = swipeModel.getScrollPosition();

    /*
     * Return a new rectangle that represents the button which is offset by
     * the swipe offset. We don't want to create a new button as we'll lose
     * the existing button's state.
         */
        return new Rectangle(
            swipeOffset + buttonSprite.x() - buttonSprite.width() / 2f,
            buttonSprite.y() - buttonSprite.height() / 2f,
            buttonSprite.width(),
            buttonSprite.height());
    }

    @Override
    public void buttonUp() {
        buttonSprite.changeType(spriteButtonUp);

        model.processButton(buttonType);
    }

    @Override
    public void buttonDown() {
        buttonSprite.changeType(spriteButtonDown);
    }

    @Override
    public void buttonReleased() {
        buttonSprite.changeType(spriteButtonUp);
    }

    @Override
    public IButtonSprite getSprite() {
        return buttonSprite;
    }

    @Override
    public Text getText() {
        return text;
    }
}
