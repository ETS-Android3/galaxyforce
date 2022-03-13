package com.danosoftware.galaxyforce.models.screens.game;

import com.danosoftware.galaxyforce.models.assets.AlienMissilesDto;
import com.danosoftware.galaxyforce.models.assets.BaseMissilesDto;
import com.danosoftware.galaxyforce.models.assets.PowerUpsDto;
import com.danosoftware.galaxyforce.models.assets.SpawnedAliensDto;
import com.danosoftware.galaxyforce.models.screens.Model;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.bases.IBasePrimary;
import com.danosoftware.galaxyforce.sprites.providers.SpriteProvider;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextChangeListener;
import com.danosoftware.galaxyforce.text.TextPositionX;
import com.danosoftware.galaxyforce.text.TextPositionY;
import com.danosoftware.galaxyforce.text.TextProvider;
import com.danosoftware.galaxyforce.view.FPSCounter;
import java.util.List;

/**
 * Game Handler decorator that adds frame-rate calculations and display functionality.
 */
public class GamePlayModelFrameRateDecorator implements Model, GameModel, TextChangeListener {

  // decorated game model
  private final GameModel gameModel;

  // decorated model
  private final Model model;

  // FPS counter
  private final FPSCounter fpsCounter;

  // text provider that includes model text and FPS text
  private final TextProvider textProvider;

  // has FPS text changed?
  private boolean updateText;

  public GamePlayModelFrameRateDecorator(GamePlayModelImpl gameHandler) {
    this.gameModel = gameHandler;
    this.model = gameHandler;
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
  public IBasePrimary getBase() {
    return gameModel.getBase();
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
    gameModel.pause();
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
  public void addPowerUp(PowerUpsDto powerUp) {
    gameModel.addPowerUp(powerUp);
  }

  @Override
  public void fireBaseMissiles(BaseMissilesDto missiles) {
    gameModel.fireBaseMissiles(missiles);
  }

  @Override
  public void fireAlienMissiles(AlienMissilesDto missiles) {
    gameModel.fireAlienMissiles(missiles);
  }

  @Override
  public IAlien chooseActiveAlien() {
    return gameModel.chooseActiveAlien();
  }

  @Override
  public void spawnAliens(SpawnedAliensDto aliens) {
    gameModel.spawnAliens(aliens);
  }

  @Override
  public int getLives() {
    return gameModel.getLives();
  }

  @Override
  public void addLife() {
    gameModel.addLife();
  }

  @Override
  public List<IAlien> getActiveAliens() {
    return gameModel.getActiveAliens();
  }

  @Override
  public void onTextChange() {
    updateText = true;
  }
}
