package com.danosoftware.galaxyforce.textures;

import static com.danosoftware.galaxyforce.textures.TextureMap.GAME;
import static com.danosoftware.galaxyforce.textures.TextureMap.MENU;

import android.util.Log;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.MenuSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDimensions;
import java.util.EnumMap;

/**
 * Service to create or retrieve textures.
 */
public class TextureService {

  private static final String TAG = "Textures";

  // XML file loader
  private final TextureRegionXmlParser xmlParser;

  // PNG texture image file loader
  private final TextureLoader textureLoader;

  // textures for each TextureMap
  private final Texture menuTexture;
  private final Texture gameTexture;

  // texture regions for each TextureMap.
  // can't be populated until we load the texture for the first time
  // as we need to know the texture image's dimensions.
  private final EnumMap<SpriteDetails, TextureRegion> menuTextureRegions;
  private final EnumMap<SpriteDetails, TextureRegion> gameTextureRegions;

  // dimensions for each sprite
  private final EnumMap<SpriteDetails, SpriteDimensions> menuSpriteDimensions;
  private final EnumMap<SpriteDetails, SpriteDimensions> gameSpriteDimensions;

  // are the textures loaded?
  private boolean texturesLoaded;

  // are the textures regions loaded?
  private boolean texturesRegionsLoaded;

  public TextureService(
      final TextureRegionXmlParser xmlParser,
      final TextureLoader textureLoader) {
    this.xmlParser = xmlParser;
    this.textureLoader = textureLoader;
    this.menuTexture = createTexture(MENU);
    this.gameTexture = createTexture(GAME);
    this.menuTextureRegions = new EnumMap<>(SpriteDetails.class);
    this.gameTextureRegions = new EnumMap<>(SpriteDetails.class);
    this.menuSpriteDimensions = new EnumMap<>(SpriteDetails.class);
    this.gameSpriteDimensions = new EnumMap<>(SpriteDetails.class);
    this.texturesLoaded = false;
    this.texturesRegionsLoaded = false;
  }

  /**
   * Retrieve a texture from cache if available. Otherwise create a new one.
   */
  public Texture getOrCreateTexture(TextureMap textureMap) {

    // typically we will need to load textures of the first attempt
    // to get a texture or following an application resume.
    // we can't load textures on construction as we must wait for the
    // surface view to be created first.
    if (!texturesLoaded) {
      reloadTextures();
    }

    final Texture texture;
    switch (textureMap) {
      case GAME:
        texture = gameTexture;
        break;
      case MENU:
        texture = menuTexture;
        break;
      default:
        throw new GalaxyForceException("Unknown texture map: " + textureMap);
    }

    // set our chosen texture as the bound one
    texture.bindActiveTexture();
    return texture;
  }

  /**
   * Retrieve texture regions for each sprite from cache if available. Otherwise create a new one.
   */
  public EnumMap<SpriteDetails, TextureRegion> getOrCreateTextureRegions(TextureMap textureMap) {

    // textures must be loaded before we attempt to create texture regions.
    // we need to know the texture map's dimensions.
    if (!texturesLoaded) {
      throw new GalaxyForceException("Textures not loaded for texture map: " + textureMap);
    }

    // if we have not created texture regions yet, create them now.
    // they only need to be created once and then kept in our cache.
    // this will happen just before the first frame is drawn.
    if (!texturesRegionsLoaded) {
      createTextureRegions();
    }

    final EnumMap<SpriteDetails, TextureRegion> textureRegions;
    switch (textureMap) {
      case GAME:
        textureRegions = gameTextureRegions;
        break;
      case MENU:
        textureRegions = menuTextureRegions;
        break;
      default:
        throw new GalaxyForceException("Unknown texture map: " + textureMap);
    }

    return textureRegions;
  }

  private Texture createTexture(TextureMap textureMap) {
    Log.i(TAG, "Create new texture for: " + textureMap.name());
    return new Texture(xmlParser, textureLoader, textureMap);
  }

  /**
   * Dispose textures - typically when application is being paused. Textures will need be reloaded
   * and next time screen resumes.
   */
  public void disposeTextures() {
    menuTexture.dispose();
    gameTexture.dispose();
    texturesLoaded = false;
  }

  /**
   * Load textures - typically when application is resuming. Textures can be lost while the
   * application is paused.
   */
  public void reloadTextures() {
    menuTexture.load();
    gameTexture.load();
    texturesLoaded = true;
  }

  /**
   * create each sprite's texture regions for sprite display. must be created after a texture is
   * loaded and before sprites can be displayed.
   */
  private void createTextureRegions() {

    // create texture regions for menu sprites
    for (MenuSpriteIdentifier menuSprite : MenuSpriteIdentifier.values()) {
      menuTextureRegions.put(
          menuSprite.getSprite(),
          createTextureRegion(menuTexture, menuSprite.getImageName()));
    }

    // create texture regions for game sprites
    for (GameSpriteIdentifier gameSprite : GameSpriteIdentifier.values()) {
      gameTextureRegions.put(
          gameSprite.getSprite(),
          createTextureRegion(gameTexture, gameSprite.getImageName()));
    }

    texturesRegionsLoaded = true;
  }

  /**
   * Create a texture region for a specific image within the texture map.
   *
   * @param texture   - loaded texture
   * @param imageName - image to create region for
   * @return texture region
   */
  private TextureRegion createTextureRegion(Texture texture, String imageName) {

    TextureDetail textureDetail = texture.getTextureDetail(imageName);

    return
        new TextureRegion(
            texture,
            textureDetail.getXPos(),
            textureDetail.getYPos(),
            textureDetail.getWidth(),
            textureDetail.getHeight());
  }

  /**
   * Create a texture region for a specific image within the texture map.
   *
   * @param texture   - loaded texture
   * @param imageName - image to create region for
   * @return texture region
   */
  private SpriteDimensions createSpriteDimensions(Texture texture, String imageName) {

    TextureDetail textureDetail = texture.getTextureDetail(imageName);

    return
        new SpriteDimensions(
            textureDetail.getWidth(),
            textureDetail.getHeight());
  }
}
