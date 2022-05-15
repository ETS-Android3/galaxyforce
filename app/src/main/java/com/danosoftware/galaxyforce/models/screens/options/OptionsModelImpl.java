package com.danosoftware.galaxyforce.models.screens.options;

import static com.danosoftware.galaxyforce.constants.GameConstants.DEFAULT_BACKGROUND_COLOUR;

import android.util.Log;
import com.danosoftware.galaxyforce.buttons.sprite_text_button.OptionButton;
import com.danosoftware.galaxyforce.buttons.toggle_group.ToggleButtonGroup;
import com.danosoftware.galaxyforce.buttons.toggle_group.ToggleOption;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.controllers.touch.DetectButtonTouch;
import com.danosoftware.galaxyforce.games.Game;
import com.danosoftware.galaxyforce.models.buttons.ButtonModel;
import com.danosoftware.galaxyforce.models.buttons.ButtonType;
import com.danosoftware.galaxyforce.models.screens.ModelState;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.options.Option;
import com.danosoftware.galaxyforce.options.OptionMusic;
import com.danosoftware.galaxyforce.options.OptionSound;
import com.danosoftware.galaxyforce.options.OptionVibration;
import com.danosoftware.galaxyforce.services.configurations.ConfigurationService;
import com.danosoftware.galaxyforce.services.googleplay.GooglePlayConnectionObserver;
import com.danosoftware.galaxyforce.services.googleplay.GooglePlayServices;
import com.danosoftware.galaxyforce.services.music.MusicPlayerService;
import com.danosoftware.galaxyforce.services.sound.SoundEffect;
import com.danosoftware.galaxyforce.services.sound.SoundPlayerService;
import com.danosoftware.galaxyforce.services.vibration.VibrateTime;
import com.danosoftware.galaxyforce.services.vibration.VibrationService;
import com.danosoftware.galaxyforce.sprites.game.splash.SplashSprite;
import com.danosoftware.galaxyforce.sprites.mainmenu.MenuButton;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDimensions;
import com.danosoftware.galaxyforce.sprites.providers.BasicMenuSpriteProvider;
import com.danosoftware.galaxyforce.sprites.providers.MenuSpriteProvider;
import com.danosoftware.galaxyforce.sprites.providers.SpriteProvider;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextPositionX;
import com.danosoftware.galaxyforce.text.TextProvider;

public class OptionsModelImpl implements OptionsModel, ButtonModel, GooglePlayConnectionObserver {

  /* logger tag */
  private static final String TAG = "OptionsModelImpl";

  private final Game game;
  private final Controller controller;

  private final ConfigurationService configurationService;
  private final SoundPlayerService sounds;
  private final MusicPlayerService music;
  private final VibrationService vibrator;
  private final GooglePlayServices playService;

  // reference to all text objects in model
  private final TextProvider textProvider;
  private final MenuSpriteProvider spriteProvider;
  private ModelState modelState;
  private boolean reBuildAssets;

  // is user signed-in or out?
  // it's possible to be neither if sign-in process still executing
  private boolean playerSignedOut;
  private boolean playerSignedIn;

  private final static SpriteDetails GOOGLE_PLAY_ICON = SpriteDetails.GOOGLE_PLAY;
  private final static int DEFAULT_GOOGLE_PLAY_ICON_WIDTH = 52; // fallback is dimensions not loaded

  public OptionsModelImpl(
      Game game,
      Controller controller,
      ConfigurationService configurationService,
      SoundPlayerService sounds,
      MusicPlayerService music,
      VibrationService vibrator,
      GooglePlayServices playService) {
    this.game = game;
    this.controller = controller;
    this.configurationService = configurationService;
    this.sounds = sounds;
    this.music = music;
    this.vibrator = vibrator;
    this.playService = playService;
    this.textProvider = new TextProvider();
    this.spriteProvider = new BasicMenuSpriteProvider();
    this.playerSignedOut = playService.isPlayerSignedOut();
    this.playerSignedIn = playService.isPlayerSignedIn();

    // build screen assets on next update
    // sprites won't be initialised until screen resumes
    this.reBuildAssets = true;

    // register for connection changes from the google play service
    playService.registerConnectionObserver(this);
  }

  private void buildAssets() {

    // clear any current touch controllers prior to adding buttons
    controller.clearTouchControllers();

    // clear current sprites prior to rebuilding
    spriteProvider.clear();
    textProvider.clear();

    // add buttons
    textProvider.add(Text.newTextRelativePositionX(
        "SOUND EFFECTS",
        TextPositionX.CENTRE,
        175 + (4 * 170)));

    ToggleButtonGroup soundToggleGroup = new ToggleOption(
        this,
        configurationService.getSoundOption());
    addOptionsButton(
        controller,
        4,
        0,
        OptionSound.ON,
        soundToggleGroup,
        90);
    addOptionsButton(
        controller,
        4,
        1,
        OptionSound.OFF,
        soundToggleGroup,
        90);

    textProvider.add(Text.newTextRelativePositionX(
        "MUSIC",
        TextPositionX.CENTRE,
        175 + (3 * 170)));

    ToggleButtonGroup musicToggleGroup = new ToggleOption(
        this,
        configurationService.getMusicOption());
    addOptionsButton(
        controller,
        3,
        0,
        OptionMusic.ON,
        musicToggleGroup,
        90);
    addOptionsButton(
        controller,
        3,
        1,
        OptionMusic.OFF,
        musicToggleGroup,
        90);

    textProvider.add(Text.newTextRelativePositionX(
        "VIBRATION",
        TextPositionX.CENTRE,
        175 + (2 * 170)));

    ToggleButtonGroup vibrationToggleGroup = new ToggleOption(
        this,
        configurationService.getVibrationOption());
    addOptionsButton(
        controller,
        2,
        0,
        OptionVibration.ON,
        vibrationToggleGroup,
        90);
    addOptionsButton(
        controller,
        2,
        1,
        OptionVibration.OFF,
        vibrationToggleGroup,
        90);

    // We will only show the sign-in option if they failed to sign-in
    // automatically when the game started.
    //
    // Once signed-in, we remove the button as they can't sign-out.
    // We will replace it with an achievements button.
    if (playerSignedIn || playerSignedOut) {

      // we will place google play icon alongside text.
      // compute the positions of each so combined icon/text is centred.
      final String text = "GOOGLE PLAY";
      final SpriteDimensions dimensions = GOOGLE_PLAY_ICON.getDimensions();
      final int halfIconWidth =
          dimensions != null ? dimensions.getWidth() / 2 : DEFAULT_GOOGLE_PLAY_ICON_WIDTH / 2;
      final int fontWidth = 30;
      final int buffer = 10;
      final int textLength = text.length() * fontWidth;
      final int xPos = (GameConstants.GAME_WIDTH / 2) + halfIconWidth + buffer;
      final int iconXPos = xPos - (textLength / 2) - halfIconWidth - buffer;

      // add google play icon and text
      spriteProvider.add(
          new SplashSprite(
              iconXPos,
              175 + 170,
              GOOGLE_PLAY_ICON));
      textProvider.add(Text.newTextAbsolutePosition(
          text,
          xPos,
          175 + 170));

      if (playerSignedOut) {
        // add sign-in button
        addNewOptionsMenuButton(controller, 1, "SIGN IN", ButtonType.SIGN_IN);
      } else if (playerSignedIn) {
        // add achievements button
        addNewOptionsMenuButton(controller, 1, "AWARDS", ButtonType.ACHIEVEMENTS);
      }
    }

    addNewMenuButton(controller, 0, "BACK", ButtonType.EXIT);
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
      // return back to previous screen
      game.screenReturn();
    }

    if (reBuildAssets) {
      buildAssets();
      reBuildAssets = false;
    }
  }

  @Override
  public void dispose() {
    // unregister for connection changes from the google play service
    playService.unregisterConnectionObserver(this);
  }

  private void addOptionsButton(
      Controller controller,
      int row,
      int column,
      Option optionType,
      ToggleButtonGroup toggleGroup,
      int offset) {

    OptionButton button = new OptionButton(
        90 + (column * 180) + offset,
        100 + (row * 170),
        optionType,
        SpriteDetails.OPTION_UNSELECTED,
        SpriteDetails.OPTION_SELECTED,
        toggleGroup);

    // add a new button to controller's list of touch controllers
    controller.addTouchController(new DetectButtonTouch(button));

    // add new button's sprite to list of sprites
    spriteProvider.add(button.getSprite());

    // add new button's text to list of text objects
    textProvider.add(button.getText());

    // add button to option group
    toggleGroup.addOption(button, optionType);
  }

  /**
   * add wanted menu button using the supplied row, label and type.
   */
  private void addNewMenuButton(
      Controller controller,
      int row,
      String label,
      ButtonType buttonType) {

    // create button
    MenuButton button = new MenuButton(
        this,
        GameConstants.GAME_WIDTH / 2,
        100 + (row * 170),
        label,
        buttonType,
        SpriteDetails.MAIN_MENU,
        SpriteDetails.MAIN_MENU_PRESSED);

    // add a new menu button to controller's list of touch controllers
    controller.addTouchController(new DetectButtonTouch(button));

    /// add new button
    spriteProvider.add(button.getSprite());
    textProvider.add(button.getText());
  }

  /**
   * add wanted options button using the supplied row, label and type.
   */
  private void addNewOptionsMenuButton(
      Controller controller,
      int row,
      String label,
      ButtonType buttonType) {

    // create button
    MenuButton button = new MenuButton(
        this,
        GameConstants.GAME_WIDTH / 2,
        100 + (row * 170),
        label,
        buttonType,
        SpriteDetails.OPTIONS_MENU,
        SpriteDetails.OPTIONS_MENU_PRESSED);

    // add a new menu button to controller's list of touch controllers
    controller.addTouchController(new DetectButtonTouch(button));

    /// add new button
    spriteProvider.add(button.getSprite());
    textProvider.add(button.getText());
  }

  @Override
  public void optionSelected(Option optionSelected) {

    if (optionSelected instanceof OptionSound) {
      OptionSound soundType = (OptionSound) optionSelected;
      Log.d(TAG, "Sound Option Selected: " + soundType.getText());
      configurationService.setSoundOption(soundType);

      // update sound service
      sounds.setSoundEnabled(soundType == OptionSound.ON);

      // play sound (if enabled) to prove sound is on
      sounds.play(SoundEffect.ALIEN_FIRE);
    }

    if (optionSelected instanceof OptionMusic) {
      OptionMusic musicType = (OptionMusic) optionSelected;
      Log.d(TAG, "Music Option Selected: " + musicType.getText());
      configurationService.setMusicOption(musicType);

      // update music service
      boolean musicEnabled = (musicType == OptionMusic.ON);
      music.setMusicEnabled(musicEnabled);

      // play music
      if (musicEnabled) {
        music.play();
      } else {
        music.pause();
      }
    }

    if (optionSelected instanceof OptionVibration) {
      OptionVibration vibrationType = (OptionVibration) optionSelected;
      Log.d(TAG, "Vibration Option Selected: " + vibrationType.getText());
      configurationService.setVibrationOption(vibrationType);

      // update vibration service
      vibrator.setVibrationEnabled(vibrationType == OptionVibration.ON);

      // vibrate (if enabled) to prove vibrator is on
      vibrator.vibrate(VibrateTime.MEDIUM);
    }
  }

  @Override
  public void goBack() {
    Log.d(TAG, "Persist Configurations.");
    configurationService.persistConfigurations();

    this.modelState = ModelState.GO_BACK;
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
  public void processButton(ButtonType buttonType) {
    switch (buttonType) {
      case EXIT:
        Log.d(GameConstants.LOG_TAG, "Exit Options.");
        goBack();
        break;
      case SIGN_IN:
        Log.d(GameConstants.LOG_TAG, "Attempt to sign-in player.");
        playService.signIn();
        break;
      case ACHIEVEMENTS:
        Log.d(GameConstants.LOG_TAG, "Show player achievements.");
        playService.showAchievements();
        break;
      default:
        Log.e(GameConstants.LOG_TAG, "Unsupported button: '" + buttonType + "'.");
        break;
    }
  }

  /**
   * Receives notifications whenever a player signs-in to Google Play service (or fails to sign-in).
   * Once a player is signed-in, they can not sign-out.
   */
  @Override
  public void onPlayerSignInStateChange() {
    this.playerSignedOut = playService.isPlayerSignedOut();
    this.playerSignedIn = playService.isPlayerSignedIn();
    this.reBuildAssets = true;
    if (playerSignedIn) {
      Log.d(GameConstants.LOG_TAG, "Player is now signed-in.");
    } else if (playerSignedOut) {
      Log.d(GameConstants.LOG_TAG, "Player is now signed-out.");
    }
  }
}
