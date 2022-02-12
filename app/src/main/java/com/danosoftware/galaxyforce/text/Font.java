package com.danosoftware.galaxyforce.text;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import com.danosoftware.galaxyforce.view.SpriteBatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Font {

  private final int glyphWidth;

  // text position must be offset by half a character glyph's width.
  // pre-calculate to save time later
  private final float glyphHalfWidth;
  private final int glyphHeight;
  private final TextureRegion[] glyphs;
  private final String charsInMap;

  // each string is converted into character indexes.
  // each index represents each character's position within the character map.
  private final Map<String, List<Integer>> characterIndexesCache;

  /**
   * font constructor setting up fonts using the texture map. alternative constructor where list of
   * characters in map is passed
   *
   * @param texture      - texture map containing fonts
   * @param offsetX      - position of fonts in texture map
   * @param offsetY      - position of fonts in texture map
   * @param glyphsPerRow - font characters per row
   * @param glyphWidth   - width of a font character
   * @param glyphHeight  - height of a font character
   * @param charsInMap   - string of valid characters
   */
  public Font(Texture texture, int offsetX, int offsetY, int glyphsPerRow, int glyphWidth,
      int glyphHeight, String charsInMap) {

    int charCount = charsInMap.length();

    this.glyphWidth = glyphWidth;
    this.glyphHalfWidth = glyphWidth / 2f;
    this.glyphHeight = glyphHeight;
    this.charsInMap = charsInMap;
    this.glyphs = new TextureRegion[charCount];
    this.characterIndexesCache = new HashMap<>();

    int x = offsetX;
    int y = offsetY;
    for (int i = 0; i < charCount; i++) {
      glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
      x += glyphWidth;
      if (x == offsetX + glyphsPerRow * glyphWidth) {
        x = offsetX;
        y += glyphHeight;
      }
    }
  }

  // drawText method that calculates x and y from Position enums (e.g. LEFT,
  // BOTTOM) if supplied. otherwise uses absolute x, y values.
  //
  public void drawText(SpriteBatcher batcher, String text, float x, float y, TextPositionX textPosX,
      TextPositionY textPosY) {

    // choose the correct drawText method depending on whether text
    // position enums have been set.
    //
    if (textPosX != null && textPosY != null) {
      drawText(batcher, text, calculateX(textPosX, text), calculateY(textPosY));
    } else if (textPosX != null) {
      drawText(batcher, text, calculateX(textPosX, text), y);
    } else if (textPosY != null) {
      drawText(batcher, text, x, calculateY(textPosY));
    } else {
      drawText(batcher, text, calculateCentreX(text, x), y);
    }
  }

  private void drawText(SpriteBatcher batcher, String text, float x, float y) {

    // compute indexes for text or retrieve from cache
    final List<Integer> characterIndexes;
    if (characterIndexesCache.containsKey(text)) {
      characterIndexes = characterIndexesCache.get(text);
    } else {
      characterIndexes = computeTextIndexes(text);
      characterIndexesCache.put(text, characterIndexes);
    }

    for (Integer i : characterIndexes) {
      TextureRegion glyph = glyphs[i];
      batcher.drawSprite(x, y, glyphWidth, glyphHeight, glyph);
      x += glyphWidth;
    }
  }

  // calculate x position using text width and screen width based on if text
  // required at LEFT, CENTRE or RIGHT of screen.
  //
  private float calculateX(TextPositionX posX, String text) {
    float x;

    // calculate total width of text
    int textLength = text.length() * glyphWidth;

    switch (posX) {
      case CENTRE:
        x = ((GameConstants.GAME_WIDTH - textLength) / 2f) + glyphHalfWidth;
        break;
      case LEFT:
        x = glyphHalfWidth;
        break;
      case RIGHT:
        x = (GameConstants.GAME_WIDTH - textLength) + glyphHalfWidth;
        break;
      default:
        x = 0;
        break;
    }

    return x;
  }

  // calculate y position using text height and screen height based on if text
  // required at TOP, CENTRE or BOTTOM of screen.
  //
  private float calculateY(TextPositionY posY) {
    float y;

    switch (posY) {
      case TOP:
        y = GameConstants.GAME_HEIGHT - glyphHalfWidth;
        break;
      case CENTRE:
        y = GameConstants.GAME_HEIGHT / 2f;
        break;
      case BOTTOM:
        y = glyphHalfWidth;
        break;
      default:
        y = 0;
        break;
    }

    return y;
  }

  // calculate new adjusted x position so that text is centred on the provided
  // x. this takes into account the number of characters and adjusts x so that
  // the whole text string is centred. An unadjusted x will only centre on the
  // first character.
  private float calculateCentreX(String text, float x) {

    // offsets text positions by half text width for number of text
    // characters minus one. so...
    // 1 character - no offset
    // 2 characters - offset by half width
    // 3 characters - offset by one width
    // ...etc...
    float offset = glyphHalfWidth * (text.length() - 1);

    return x - offset;
  }

  // Each string must be converted into a list of character indexes.
  // Each index represents each character's position in the character map
  private List<Integer> computeTextIndexes(String text) {

    int len = text.length();
    final List<Integer> characterIndexes = new ArrayList<>(len);

    for (int i = 0; i < len; i++) {

      int c;

      // if character map was supplied find current character's position in map
      if (charsInMap != null) {
        // returns index of current character within the character map
        c = charsInMap.indexOf(text.charAt(i));

        if (c == -1) {
          continue;
        }

      }
      // otherwise get index based on ASCII value
      else {
        c = text.charAt(i) - ' ';

        if (c < 0 || c > glyphs.length - 1) {
          continue;
        }
      }

      characterIndexes.add(c);
    }

    return characterIndexes;
  }
}
