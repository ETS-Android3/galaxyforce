package com.danosoftware.galaxyforce.models.screens;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextPositionX;
import com.danosoftware.galaxyforce.text.TextPositionY;
import com.danosoftware.galaxyforce.view.FPSCounter;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator that adds frame-rate calculations and display functionality.
 */
public class ModelFrameRateDecorator implements Model {

  // decorated model
  private final Model model;

  // FPS counter
  private final FPSCounter fpsCounter;

  // FPS display text
  private Text tempFps;

  public ModelFrameRateDecorator(Model model) {
    this.model = model;
    this.fpsCounter = new FPSCounter();
    this.tempFps = createFpsText();
  }

  private Text createFpsText() {
    return Text.newTextRelativePositionBoth(
        "FPS " + fpsCounter.getValue(),
        TextPositionX.CENTRE,
        TextPositionY.TOP);
  }

  @Override
  public List<Text> getText() {
    List<Text> text = new ArrayList<>(model.getText());
    text.add(tempFps);

    return text;
  }

  @Override
  public void update(float deltaTime) {
    model.update(deltaTime);

    // update fps text
    tempFps = createFpsText();
  }

  @Override
  public List<ISprite> getSprites() {
    return model.getSprites();
  }

  @Override
  public void dispose() {
    model.dispose();
  }

  @Override
  public void goBack() {
    model.goBack();
  }

  @Override
  public void pause() {
    model.pause();
  }

  @Override
  public void resume() {
    model.resume();
  }

  @Override
  public RgbColour background() {
    return model.background();
  }

  @Override
  public boolean animateStars() {
    return model.animateStars();
  }
}
