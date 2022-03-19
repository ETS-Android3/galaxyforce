package com.danosoftware.galaxyforce.models.screens;

import static com.danosoftware.galaxyforce.constants.GameConstants.DEFAULT_BACKGROUND_COLOUR;

import com.danosoftware.galaxyforce.buttons.button.Button;
import com.danosoftware.galaxyforce.buttons.button.ScreenTouch;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.controllers.touch.DetectButtonTouch;
import com.danosoftware.galaxyforce.games.Game;
import com.danosoftware.galaxyforce.models.buttons.TouchScreenModel;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.screen.enums.ScreenType;
import com.danosoftware.galaxyforce.sprites.common.RotatingSprite;
import com.danosoftware.galaxyforce.sprites.game.splash.SplashSprite;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.sprites.providers.BasicSpriteProvider;
import com.danosoftware.galaxyforce.sprites.providers.SpriteProvider;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextPositionX;
import com.danosoftware.galaxyforce.text.TextProvider;
import java.util.ArrayList;
import java.util.List;

public class GameCompleteModelImpl implements Model, TouchScreenModel {

  private final Game game;

  // reference to all sprites in model to be rotated
  private final List<RotatingSprite> rotatedSprites;
  // reference to all text objects in model
  private final TextProvider textProvider;
  private final SpriteProvider spriteProvider;
  private ModelState modelState;

  public GameCompleteModelImpl(
      Game game,
      Controller controller) {
    this.game = game;
    this.rotatedSprites = new ArrayList<>();
    this.textProvider = new TextProvider();
    this.spriteProvider = new BasicSpriteProvider();
    this.modelState = ModelState.RUNNING;

    // add model sprites
    addSprites();

    // add button that covers the entire screen
    Button screenTouch = new ScreenTouch(this);
    controller.addTouchController(new DetectButtonTouch(screenTouch));
  }

  private void addSprites() {
    for (int column = 0; column < 3; column++) {
      RotatingSprite base = new RotatingSprite(100 + (column * 170), 580,
          SpriteDetails.BASE_LARGE);
      rotatedSprites.add(base);
      spriteProvider.add(base);
    }

    spriteProvider
        .add(new SplashSprite(GameConstants.SCREEN_MID_X, 817, SpriteDetails.GALAXY_FORCE));

    textProvider.add(
        Text.newTextRelativePositionX("GAME COMPLETED!", TextPositionX.CENTRE, 175 + (3 * 170)));
  }

  @Override
  public TextProvider getTextProvider() {
    return textProvider;
  }

  @Override
  public SpriteProvider getSpriteProvider() {
    return spriteProvider;
  }

  @Override
  public void update(float deltaTime) {
    if (modelState == ModelState.GO_BACK) {
      game.changeToScreen(ScreenType.MAIN_MENU);
    }

    // rotate sprites
    for (RotatingSprite eachSprite : rotatedSprites) {
      eachSprite.animate(deltaTime);
    }
  }

  @Override
  public void dispose() {
    // no action for this model
  }

  @Override
  public void resume() {
    // no action for this model
  }

  @Override
  public RgbColour background() {
    return DEFAULT_BACKGROUND_COLOUR;
  }

  @Override
  public boolean animateStars() {
    return true;
  }

  @Override
  public void pause() {
    // no action for this model
  }

  @Override
  public void screenTouched() {
    this.modelState = ModelState.GO_BACK;
  }

  @Override
  public void goBack() {
    this.modelState = ModelState.GO_BACK;
  }
}
