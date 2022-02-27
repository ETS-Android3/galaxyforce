package com.danosoftware.galaxyforce.screen;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.controllers.common.Controller;
import com.danosoftware.galaxyforce.models.screens.level.LevelModel;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.game.starfield.StarField;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.tasks.TaskService;
import com.danosoftware.galaxyforce.text.Text;
import com.danosoftware.galaxyforce.text.TextProvider;
import com.danosoftware.galaxyforce.textures.TextureMap;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import com.danosoftware.galaxyforce.textures.TextureService;
import com.danosoftware.galaxyforce.view.Camera2D;
import com.danosoftware.galaxyforce.view.GLShaderHelper;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import com.danosoftware.galaxyforce.view.StarBatcher;

import java.util.List;

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
      Camera2D camera,
      SpriteBatcher batcher,
      StarBatcher starBatcher,
      TaskService taskService,
      StarField starField) {

    super(model, controller, textureService, textureMap, camera, batcher, starBatcher, taskService,
        starField);
    this.levelModel = model;
  }

  /**
   * Overridden version of draw stars that keeps stars in position as screen wipes from left to
   * right.
   */
  @Override
  void drawStars() {
    GLShaderHelper.setPointShaderProgram();
    camera.moveX(SCREEN_CENTRE);
    camera.setViewportAndMatrices();
    starBatcher.drawStars();
  }

  /*
   * Overridden version of draw sprites that handles swiping.
   */
  @Override
  void drawSprites() {

    if (texture == null) {
      return;
    }

    // count sprites to draw
    //final List<ISprite> levelSprites = levelModel.getStaticSprites();
    final List<ISprite> sprites = model.getSprites();
    //final TextProvider staticTextProvider = levelModel.getStaticTextProvider();
    final TextProvider textProvider = model.getTextProvider();
//    final List<Text> levelTexts = levelModel.getStaticText();
//    final List<Text> texts = model.getText();
    final int spriteCount = //levelSprites.size()
        sprites.size()
        //+ staticTextProvider.count()
        + textProvider.count();

    // Use our sprite shader program for GL
    GLShaderHelper.setSpriteShaderProgram();

    // move camera's x position on screen by model's current scroll speed
    float cameraOffset = levelModel.getScrollPosition();
    camera.moveX(SCREEN_CENTRE + cameraOffset);
    camera.setViewportAndMatrices();

    batcher.beginBatch(texture, spriteCount);

    /*
     * gets static sprites from model (e.g. stars) - these sprites must not
     * scroll with other elements so offset stars by current camera offset.
     */
//    for (ISprite sprite : levelSprites) {
//      SpriteDetails spriteDetails = sprite.spriteDetails();
//      TextureRegion textureRegion = spriteDetails.getTextureRegion();
//
//      if (textureRegion != null) {
//        batcher.drawSprite(
//            sprite.x() + cameraOffset,
//            sprite.y(),
//            sprite.width(),
//            sprite.height(),
//            textureRegion);
//      }
//    }

    // gets sprites from model
    for (ISprite sprite : sprites) {
      SpriteDetails spriteDetails = sprite.spriteDetails();
      TextureRegion textureRegion = spriteDetails.getTextureRegion();

      if (textureRegion != null) {
        batcher.drawSprite(
            sprite.x(),
            sprite.y(),
            sprite.width(),
            sprite.height(),
            textureRegion);
      }
    }

    /*
     * gets static text from model - this text must not scroll with other
     * elements so offset stars by current camera offset.
     */
    if (gameFont != null) {
//      for (Text text : levelTexts) {
//        gameFont.drawText(
//            batcher,
//            text.getText(),
//            text.getX() + cameraOffset,
//            text.getY(),
//            text.getTextPositionX(),
//            text.getTextPositionY());
//      }
 //     gameFont.drawText(batcher, staticTextProvider);

      // draw any text
      gameFont.drawText(batcher, textProvider);
//      for (Text text : texts) {
//        gameFont.drawText(
//            batcher,
//            text.getText(),
//            text.getX(),
//            text.getY(),
//            text.getTextPositionX(),
//            text.getTextPositionY());
//      }
    }

    batcher.endBatch();
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
