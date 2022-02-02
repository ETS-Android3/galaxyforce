package com.danosoftware.galaxyforce.common;

import static org.mockito.Mockito.mock;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDimensions;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import java.util.EnumMap;

public class SpriteDetailsCommon {

  /**
   * Set-up sprite details enums for tests with known texture regions and sprite dimensions. We can
   * not mock enums so must set them up in advance of tests.
   */
  public static void setUpSpriteDetailsForTests(
      SpriteDetails[] sprites,
      TextureRegion mockTextureRegion,
      SpriteDimensions mockSpriteDimensions) {
    EnumMap<SpriteDetails, TextureRegion> mockTextureRegionMap = new EnumMap<>(SpriteDetails.class);
    EnumMap<SpriteDetails, SpriteDimensions> mockSpriteDimensionsMap = new EnumMap<>(
        SpriteDetails.class);
    for (SpriteDetails spriteId : sprites) {
      mockTextureRegionMap.put(spriteId, mockTextureRegion);
      mockSpriteDimensionsMap.put(spriteId, mockSpriteDimensions);
    }
    SpriteDetails.initialise(mockTextureRegionMap, mockSpriteDimensionsMap);
  }

  public static void setUpSpriteDetailsForTests(SpriteDetails[] sprites, int width, int height) {
    Texture mockTexture = mock(Texture.class);
    final TextureRegion mockTextureRegion = new TextureRegion(mockTexture, 0, 0, width, height);
    final SpriteDimensions mockSpriteDimensions = new SpriteDimensions(width, height);
    EnumMap<SpriteDetails, TextureRegion> mockTextureRegionMap = new EnumMap<>(SpriteDetails.class);
    EnumMap<SpriteDetails, SpriteDimensions> mockSpriteDimensionsMap = new EnumMap<>(
        SpriteDetails.class);
    for (SpriteDetails spriteId : sprites) {
      mockTextureRegionMap.put(spriteId, mockTextureRegion);
      mockSpriteDimensionsMap.put(spriteId, mockSpriteDimensions);
    }
    SpriteDetails.initialise(mockTextureRegionMap, mockSpriteDimensionsMap);
  }

  public static void setUpSpriteDetailsForTests(int width, int height) {
    setUpSpriteDetailsForTests(SpriteDetails.values(), width, height);
  }

  public static void setUpSpriteDetailsForTests() {
    setUpSpriteDetailsForTests(100, 100);
  }
}
