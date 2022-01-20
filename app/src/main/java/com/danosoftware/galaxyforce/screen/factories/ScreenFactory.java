package com.danosoftware.galaxyforce.screen.factories;

import static com.danosoftware.galaxyforce.constants.GameConstants.SHOW_FPS;

import android.content.res.AssetManager;

import com.danosoftware.galaxyforce.billing.BillingService;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.controllers.common.ControllerImpl;
import com.danosoftware.galaxyforce.games.Game;
import com.danosoftware.galaxyforce.input.Input;
import com.danosoftware.galaxyforce.models.screens.GameCompleteModelImpl;
import com.danosoftware.galaxyforce.models.screens.MainMenuModelImpl;
import com.danosoftware.galaxyforce.models.screens.Model;
import com.danosoftware.galaxyforce.models.screens.SplashModelImpl;
import com.danosoftware.galaxyforce.models.screens.UnlockFullVersionModelImpl;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.models.screens.game.GameOverModelImpl;
import com.danosoftware.galaxyforce.models.screens.game.GamePausedModelImpl;
import com.danosoftware.galaxyforce.models.screens.game.GamePlayModelFrameRateDecorator;
import com.danosoftware.galaxyforce.models.screens.game.GamePlayModelImpl;
import com.danosoftware.galaxyforce.models.screens.level.SelectLevelModelImpl;
import com.danosoftware.galaxyforce.models.screens.options.OptionsModelImpl;
import com.danosoftware.galaxyforce.screen.ExitingScreen;
import com.danosoftware.galaxyforce.screen.IScreen;
import com.danosoftware.galaxyforce.screen.Screen;
import com.danosoftware.galaxyforce.screen.SelectLevelScreen;
import com.danosoftware.galaxyforce.screen.enums.ScreenType;
import com.danosoftware.galaxyforce.services.achievements.AchievementService;
import com.danosoftware.galaxyforce.services.configurations.ConfigurationService;
import com.danosoftware.galaxyforce.services.googleplay.GooglePlayServices;
import com.danosoftware.galaxyforce.services.music.Music;
import com.danosoftware.galaxyforce.services.music.MusicPlayerService;
import com.danosoftware.galaxyforce.services.savedgame.SavedGame;
import com.danosoftware.galaxyforce.services.sound.SoundPlayerService;
import com.danosoftware.galaxyforce.services.vibration.VibrationService;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.game.starfield.NewStarField;
import com.danosoftware.galaxyforce.sprites.game.starfield.StarFieldTemplate;
import com.danosoftware.galaxyforce.textures.TextureLoader;
import com.danosoftware.galaxyforce.textures.TextureMap;
import com.danosoftware.galaxyforce.textures.TextureRegionXmlParser;
import com.danosoftware.galaxyforce.textures.TextureService;
import com.danosoftware.galaxyforce.view.Camera2D;
import com.danosoftware.galaxyforce.view.GLGraphics;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import com.danosoftware.galaxyforce.view.StarBatcher;

import java.util.List;

public class ScreenFactory {

  private final SpriteBatcher batcher;
  private final StarBatcher starBatcher;
  private final Camera2D camera;
  private final BillingService billingService;
  private final ConfigurationService configurationService;
  private final SoundPlayerService sounds;
  private final MusicPlayerService music;
  private final VibrationService vibrator;
  private final GooglePlayServices playService;
  private final SavedGame savedGame;
  private final AssetManager assets;
  private final Game game;
  private final Input input;
  private final String versionName;
  private final StarFieldTemplate starFieldTemplate;
  private final NewStarField starField;
  private final TextureService textureService;

  public ScreenFactory(
      GLGraphics glGraphics,
      BillingService billingService,
      ConfigurationService configurationService,
      SoundPlayerService sounds,
      MusicPlayerService music,
      VibrationService vibrator,
      GooglePlayServices playService,
      SavedGame savedGame,
      AssetManager assets,
      Game game,
      Input input,
      String versionName) {

    this.billingService = billingService;
    this.configurationService = configurationService;
    this.sounds = sounds;
    this.music = music;
    this.vibrator = vibrator;
    this.playService = playService;
    this.savedGame = savedGame;
    this.assets = assets;
    this.game = game;
    this.input = input;
    this.versionName = versionName;
    this.batcher = new SpriteBatcher();
    this.camera = new Camera2D(glGraphics, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
    this.starFieldTemplate = new StarFieldTemplate(GameConstants.GAME_WIDTH,
        GameConstants.GAME_HEIGHT);
    this.starField = new NewStarField(
            GameConstants.GAME_WIDTH,
            GameConstants.GAME_HEIGHT);
    this.starBatcher = new StarBatcher(starField.getStarField());
    this.textureService = new TextureService(
        new TextureRegionXmlParser(assets),
        new TextureLoader(assets));
  }

  public IScreen newScreen(ScreenType screenType) {

    // each screen needs it's own instance of a controller
    // to handle specific user interactions.
    Controller controller = new ControllerImpl(input, camera);

    switch (screenType) {

      case SPLASH:
        return new ExitingScreen(
            new SplashModelImpl(game, controller, billingService, versionName, starFieldTemplate,
                sounds),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher,
                starField);

      case MAIN_MENU:
        return new ExitingScreen(
            new MainMenuModelImpl(game, controller, billingService, starFieldTemplate),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher,
                starField);

      case OPTIONS:
        return new Screen(
            new OptionsModelImpl(game, controller, configurationService, sounds, music, vibrator,
                playService, starFieldTemplate),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher,
                starField);

      case SELECT_LEVEL:
        this.music.load(Music.MAIN_TITLE);
        this.music.play();
        return new SelectLevelScreen(
            new SelectLevelModelImpl(game, controller, billingService, savedGame,
                starFieldTemplate),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher,
                starField);

      case UPGRADE_FULL_VERSION:
        return new Screen(
            new UnlockFullVersionModelImpl(game, controller, billingService, starFieldTemplate),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher, starField);

      case GAME_COMPLETE:
        return new Screen(
            new GameCompleteModelImpl(game, controller, starFieldTemplate),
            controller,
            textureService,
            TextureMap.MENU,
            camera,
            batcher,
            starBatcher, starField);

      default:
        throw new IllegalArgumentException("Unsupported screen type: '" + screenType + "'.");
    }
  }

  public IScreen newGameScreen(int startingWave) {
    this.music.load(Music.GAME_LOOP);
    this.music.play();
    Controller controller = new ControllerImpl(input, camera);
    Model gameModel = createGameModel(controller, startingWave);
    return new Screen(
        gameModel,
        controller,
        textureService,
        TextureMap.GAME,
        camera,
        batcher,
        starBatcher,
            starField);
  }

  /**
   * Returns a normal game model or a Frames-per-Second decorated version.
   */
  private Model createGameModel(Controller controller, int startingWave) {
    final AchievementService achievements = new AchievementService(playService);
    GamePlayModelImpl gameModel = new GamePlayModelImpl(
        game,
        controller,
        startingWave,
        billingService,
        sounds,
        vibrator,
        savedGame,
        achievements,
        assets,
        starFieldTemplate);

    if (SHOW_FPS) {
      return new GamePlayModelFrameRateDecorator(gameModel);
    }

    return gameModel;
  }

  public IScreen newPausedGameScreen(List<ISprite> pausedSprites, RgbColour backgroundColour) {
    Controller controller = new ControllerImpl(input, camera);
    return new Screen(
        new GamePausedModelImpl(game, controller, pausedSprites, backgroundColour),
        controller,
        textureService,
        TextureMap.GAME,
        camera,
        batcher,
        starBatcher,
            starField);
  }

  public IScreen newGameOverScreen(int previousWave) {
    Controller controller = new ControllerImpl(input, camera);
    return new Screen(
        new GameOverModelImpl(game, controller, previousWave, starFieldTemplate),
        controller,
        textureService,
        TextureMap.GAME,
        camera,
        batcher,
        starBatcher, starField);
  }
}
