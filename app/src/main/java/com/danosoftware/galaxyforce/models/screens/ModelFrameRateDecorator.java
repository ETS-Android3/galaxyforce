package com.danosoftware.galaxyforce.models.screens;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.providers.SpriteProvider;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextChangeListener;
import com.danosoftware.galaxyforce.text.TextPositionX;
import com.danosoftware.galaxyforce.text.TextPositionY;
import com.danosoftware.galaxyforce.text.TextProvider;
import com.danosoftware.galaxyforce.view.FPSCounter;

/**
 * Decorator that adds frame-rate calculations and display functionality.
 */
public class ModelFrameRateDecorator implements Model, TextChangeListener {

  // decorated model
  private final Model model;

  // FPS counter
  private final FPSCounter fpsCounter;

  // text provider that includes model text and FPS text
  private final TextProvider textProvider;

  // has FPS text changed?
  private boolean updateText;

  public ModelFrameRateDecorator(Model model) {
    this.model = model;
    this.fpsCounter = new FPSCounter(this);
    this.textProvider = new TextProvider();
    this.updateText = false;
  }

  private Text createFpsText() {
    return Text.newTextRelativePositionBoth(
        "FPS " + fpsCounter.getValue(),
        TextPositionX.CENTRE,
        TextPositionY.TOP);
  }

  @Override
  public TextProvider getTextProvider() {
    // update text if FPS or model text has changed
    TextProvider modelTextProvider = model.getTextProvider();
    if (updateText || modelTextProvider.hasUpdated()) {
      textProvider.clear();
      textProvider.addAll(modelTextProvider.text());
      textProvider.add(createFpsText());
      updateText = false;
    }
    return textProvider;
  }

  @Override
  public SpriteProvider getSpriteProvider() {
    return model.getSpriteProvider();
  }

  @Override
  public void update(float deltaTime) {
    model.update(deltaTime);

    // update fps
    fpsCounter.update();
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

  @Override
  public void onTextChange() {
    updateText = true;
  }
}
