package com.danosoftware.galaxyforce.textures;

import android.util.Log;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;

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

    // are the textures loaded?
    private boolean texturesLoaded;

    public TextureService(
        final TextureRegionXmlParser xmlParser,
        final TextureLoader textureLoader) {
        this.xmlParser = xmlParser;
        this.textureLoader = textureLoader;
        this.menuTexture = createTexture(TextureMap.MENU);
        this.gameTexture = createTexture(TextureMap.GAME);
        this.texturesLoaded = false;
    }

    /**
     * Retrieve a texture from cache if available.
     * Otherwise create a new one.
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
}
