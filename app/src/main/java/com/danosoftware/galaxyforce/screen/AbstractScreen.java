package com.danosoftware.galaxyforce.screen;

import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_ALPHA;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.models.screens.Model;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteProperties;
import com.danosoftware.galaxyforce.text.Font;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureDetail;
import com.danosoftware.galaxyforce.textures.TextureMap;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import com.danosoftware.galaxyforce.textures.TextureService;
import com.danosoftware.galaxyforce.view.Camera2D;
import com.danosoftware.galaxyforce.view.GLShaderHelper;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import com.danosoftware.galaxyforce.view.StarBatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractScreen implements IScreen {

  /* logger tag */
  private static final String LOCAL_TAG = "Screen";

  // font glyphs per row - i.e. characters in a row within texture map
  private final static int FONT_GLYPHS_PER_ROW = 8;

  // font glyphs width - i.e. width of individual character
  private final static int FONT_GLYPHS_WIDTH = 30;

  // font glyphs height - i.e. height of individual character
  private final static int FONT_GLYPHS_HEIGHT = 38;

  /**
   * Reference to model and controller. Each screen will have different implementations of models
   * and controllers.
   * <p>
   * All screen implementations of this abstract class must construct their models and controller
   * after calling this abstract super constructor.
   * <p>
   * Some models/controllers require views to be created before they can be constructed.
   */
  final Model model;
  // sprite batcher used for displaying sprites
  final SpriteBatcher batcher;
  final StarBatcher starBatcher;
  // camera used for display views
  final Camera2D camera;
  final Map<ISpriteIdentifier, TextureRegion> textureRegions;
  private final Controller controller;
  private final TextureService textureService;
  // TextureState identifies the texture map being used
  private final TextureMap textureMap;
  // reference to graphics texture map - set on resume
  Texture texture;
  // font used for displaying text sprites
  Font gameFont;

  AbstractScreen(
      Model model,
      Controller controller,
      TextureService textureService,
      TextureMap textureMap,
      Camera2D camera,
      SpriteBatcher batcher,
      StarBatcher starBatcher) {

    this.textureService = textureService;
    this.textureMap = textureMap;
    this.batcher = batcher;
    this.camera = camera;
    this.controller = controller;
    this.model = model;
    this.textureRegions = new HashMap<>();
    this.starBatcher = starBatcher;
  }

  @Override
  public void draw() {

    // clear screen
    final RgbColour backgroundColour = model.background();
    GLES20.glClearColor(
        backgroundColour.getRed(),
        backgroundColour.getGreen(),
        backgroundColour.getBlue(),
        BACKGROUND_ALPHA);
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    // Enable alpha blending and blend based on the fragment's alpha value
    GLES20.glEnable(GLES20.GL_BLEND);
    GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

    // Draw Stars
    GLShaderHelper.setPointShaderProgram();
    camera.setViewportAndMatrices();
    starBatcher.drawStars();

    // count sprites to draw
    final List<ISprite> sprites = model.getSprites();
    final List<Text> texts = model.getText();
    final int spriteCount = sprites.size() + countCharacters(texts);

    // Use our sprite shader program for GL
    GLShaderHelper.setSpriteShaderProgram();

    camera.setViewportAndMatrices();

    batcher.beginBatch(texture, spriteCount);

    // gets sprites from model
    for (ISprite sprite : sprites) {
      ISpriteIdentifier spriteId = sprite.spriteId();
      ISpriteProperties props = spriteId.getProperties();
      TextureRegion textureRegion = textureRegions.get(spriteId);

      if (textureRegion != null) {
        if (sprite.rotation() != 0) {
          // use sprite with rotation method
          batcher.drawSprite(
              sprite.x(),
              sprite.y(),
              props.getWidth(),
              props.getHeight(),
              sprite.rotation(),
              textureRegion);
        } else {
          // use normal sprite method
          batcher.drawSprite(
              sprite.x(),
              sprite.y(),
              props.getWidth(),
              props.getHeight(),
              textureRegion);
        }
      }
    }

    // draw any text
    for (Text text : texts) {
      gameFont.drawText(
          batcher,
          text.getText(),
          text.getX(),
          text.getY(),
          text.getTextPositionX(),
          text.getTextPositionY());
    }

    batcher.endBatch();

    // Turn alpha blending off
    GLES20.glDisable(GLES20.GL_BLEND);
  }

  @Override
  public void update(float deltaTime) {
    controller.update(deltaTime);
    model.update(deltaTime);
  }

  @Override
  public void pause() {
    // pause model if whole game is paused (e.g. user presses home button)
    model.pause();

    /*
     * dispose of texture when screen paused. it will be reloaded next time
     * screen resumes
     */
    texture.dispose();
  }

  @Override
  public void resume() {
    Log.i(GameConstants.LOG_TAG, LOCAL_TAG + ": Screen Resume.");

    /*
     * set-up texture map for screen. this will cause texture to be
     * re-loaded. re-loading must happen each time screen is resumed as
     * textures can be disposed by OpenGL when the game is paused.
     */
    this.texture = textureService.getOrCreateTexture(textureMap);
//    textureService.reloadTextures();
//    this.texture = textureService.getTexture(textureMap);

    /*
     * create each sprite's individual properties (e.g. width, height) from
     * the xml file and create texture regions for sprite display. must be
     * called after a new texture is re-loaded and before sprites can be
     * displayed.
     */
    textureRegions.clear();
    for (ISpriteIdentifier sprite : textureMap.getSpriteIdentifiers()) {
      sprite.updateProperties(texture);
      TextureDetail textureDetails = texture.getTextureDetail(sprite.getName());
      textureRegions.put(
          sprite,
          new TextureRegion(
              texture,
              textureDetails.getXPos(),
              textureDetails.getYPos(),
              textureDetails.getWidth(),
              textureDetails.getHeight()));
    }

    // set-up fonts - can be null if sprite map has no fonts
    ISpriteIdentifier fontId = textureMap.getFontIdentifier();
    TextureDetail fontTextureDetails = texture.getTextureDetail(fontId.getName());
    this.gameFont = new Font(
        texture,
        fontTextureDetails.getXPos(),
        fontTextureDetails.getYPos(),
        FONT_GLYPHS_PER_ROW,
        FONT_GLYPHS_WIDTH,
        FONT_GLYPHS_HEIGHT,
        GameConstants.FONT_CHARACTER_MAP);

    model.resume();
  }

  @Override
  public void dispose() {
    model.dispose();
  }

  @Override
  public boolean handleBackButton() {
    model.goBack();
    return true;
  }

  private int countCharacters(List<Text> texts) {
    int charCount = 0;
    for (Text text : texts) {
      charCount += text.getText().length();
    }
    return charCount;
  }
}
