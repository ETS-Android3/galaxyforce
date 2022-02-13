package com.danosoftware.galaxyforce.textures;

import com.danosoftware.galaxyforce.text.Font;

/**
 * Stores created texture and font
 */
public class TextureWithFont {

  private final Texture texture;
  private final Font font;

  public TextureWithFont(Texture texture, Font font) {
    this.texture = texture;
    this.font = font;
  }

  public Texture getTexture() {
    return texture;
  }

  public Font getFont() {
    return font;
  }
}
