package com.danosoftware.galaxyforce.screen;

import static com.danosoftware.galaxyforce.constants.GameConstants.BACKGROUND_ALPHA;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.models.screens.level.LevelModel;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteProperties;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.textures.TextureMap;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import com.danosoftware.galaxyforce.textures.TextureService;
import com.danosoftware.galaxyforce.view.Camera2D;
import com.danosoftware.galaxyforce.view.GLGraphics;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

public class SelectLevelScreen extends AbstractScreen {

  private static final float SCREEN_CENTRE = GameConstants.GAME_WIDTH / 2f;

  /*
   * contains reference to level model needed for local override. This a
   * version of a model that has extra methods required for this screen.
   */
  private final LevelModel levelModel;

  public SelectLevelScreen(
      LevelModel model,
      Controller controller,
      TextureService textureService,
      TextureMap textureMap,
      GLGraphics glGraphics,
      Camera2D camera,
      SpriteBatcher batcher) {

    super(model, controller, textureService, textureMap, glGraphics, camera, batcher);
    this.levelModel = model;
  }

  /*
   * Overridden version that keeps stars in position as screen wipes from left
   * to right.
   */
  @Override
  public void draw() {
    GL10 gl = glGraphics.getGl();

    // clear screen
    final RgbColour backgroundColour = GameConstants.DEFAULT_BACKGROUND_COLOUR;
    gl.glClearColor(
        backgroundColour.getRed(),
        backgroundColour.getGreen(),
        backgroundColour.getBlue(),
        BACKGROUND_ALPHA);
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    // move camera's x position on screen by model's current scroll speed
    float cameraOffset = levelModel.getScrollPosition();
    camera.moveX(SCREEN_CENTRE + cameraOffset);

    camera.setViewportAndMatrices();
    gl.glEnable(GL10.GL_TEXTURE_2D);
    gl.glEnable(GL10.GL_BLEND);
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

    // count sprites to draw
    final List<ISprite> levelSprites = levelModel.getStaticSprites();
    final List<ISprite> sprites = model.getSprites();
    final List<Text> levelTexts = levelModel.getStaticText();
    final List<Text> texts = model.getText();
    final int spriteCount = levelSprites.size()
        + sprites.size()
        + countCharacters(levelTexts)
        + countCharacters(texts);

    batcher.beginBatch(texture, spriteCount);

    /*
     * gets static sprites from model (e.g. stars) - these sprites must not
     * scroll with other elements so offset stars by current camera offset.
     */
    for (ISprite sprite : levelSprites) {
      ISpriteIdentifier spriteId = sprite.spriteId();
      ISpriteProperties props = spriteId.getProperties();
      TextureRegion textureRegion = textureRegions.get(spriteId);
      if (textureRegion != null) {
        batcher.drawSprite(
            sprite.x() + cameraOffset,
            sprite.y(),
            props.getWidth(),
            props.getHeight(),
            textureRegion);
      }
    }

    // gets sprites from model
    for (ISprite sprite : sprites) {
      ISpriteIdentifier spriteId = sprite.spriteId();
      ISpriteProperties props = spriteId.getProperties();
      TextureRegion textureRegion = textureRegions.get(spriteId);
      if (textureRegion != null) {
        batcher.drawSprite(
            sprite.x(),
            sprite.y(),
            props.getWidth(),
            props.getHeight(),
            textureRegion);
      }
    }

    /*
     * gets static text from model - this text must not scroll with other
     * elements so offset stars by current camera offset.
     */
    for (Text text : levelTexts) {
      gameFont.drawText(
          batcher,
          text.getText(),
          text.getX() + cameraOffset,
          text.getY(),
          text.getTextPositionX(),
          text.getTextPositionY());
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
    gl.glDisable(GL10.GL_BLEND);
  }

  @Override
  public void pause() {
    super.pause();

    // reset camera position
    // other screens rely on default camera position
    camera.resetPosition();
  }

  private int countCharacters(List<Text> texts) {
    int charCount = 0;
    for (Text text : texts) {
      charCount += text.getText().length();
    }
    return charCount;
  }
}
