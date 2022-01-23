package com.danosoftware.galaxyforce.textures;

import android.util.Log;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
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
    private final EnumMap<TextureMap, Texture> textures;

    // texture Ids for each TextureMap
    private final EnumMap<TextureMap, Integer> textureIds;

    private Texture menuTexture;
    private Texture gameTexture;

    public TextureService(
        final TextureRegionXmlParser xmlParser,
        final TextureLoader textureLoader) {
        this.xmlParser = xmlParser;
        this.textureLoader = textureLoader;
        this.textures = new EnumMap<>(TextureMap.class);
        this.textureIds = new EnumMap<>(TextureMap.class);
        this.menuTexture = loadTexture(TextureMap.MENU);
        menuTexture.dispose();
        this.gameTexture = loadTexture(TextureMap.GAME);
        gameTexture.dispose();
        this.menuTexture = loadTexture(TextureMap.MENU);
        this.gameTexture = loadTexture(TextureMap.GAME);

        // create unique ids for our textures
//        TextureMap[] textures2 = TextureMap.values();
//        int[] ids = new int[textures2.length];
//        GLES20.glGenTextures(textures2.length, ids, 0);
//        Log.i(TAG, "id: " + ids);
//        int idx = 0;
//        for(TextureMap map : textures2) {
//            textureIds.put(map, ids[idx]);
//            Log.i(TAG, "Create texture id: " + ids[idx] + " for: " + map.name());
//            idx++;
//        }

        // load all textures
//        for (TextureMap textureMap : TextureMap.values()) {
//            Log.i(TAG, "Create new texture for: " + textureMap.name());
//            Texture texture = new Texture(xmlParser, textureLoader, textureMap);
//            texture.reload(textureIds.get(textureMap));
//            textures.put(textureMap, texture);
//        }
    }

    private Texture loadTexture(TextureMap textureMap) {
        Log.i(TAG, "Create new texture for: " + textureMap.name());
        return new Texture(xmlParser, textureLoader, textureMap);
        //textures.put(textureMap, texture);
    }

    /**
     * Retrieve a texture from cache if available.
     * Otherwise create a new one.
     */
    public Texture getOrCreateTexture(TextureMap textureMap) {

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

        texture.bindActiveTexture();
        return texture;
//        final Texture texture;
//
//        // create unique ids for our textures
//        TextureMap[] textures2 = TextureMap.values();
//        int[] ids = new int[textures2.length];
//        GLES20.glGenTextures(textures2.length, ids, 0);
//        int idx = 0;
//        for(TextureMap map : textures2) {
//            textureIds.put(map, ids[idx]);
//            Log.i(TAG, "Create texture id: " + ids[idx] + " for: " + map.name());
//            idx++;
//        }
//
////        if (textureMaps.containsKey(textureMap)) {
//            Log.i(TAG, "Reload existing texture for: " + textureMap.name());
//            texture = textures.get(textureMap);
//            // if retrieving an existing texture then reload/bind for OpenGL
//            texture.reload(textureIds.get(textureMap));
////        } else {
////            Log.i(TAG, "Create new texture for: " + textureMap.name());
////            texture = new Texture(xmlParser, textureLoader, textureMap);
////            textureMaps.put(textureMap, texture);
////        }
//
//        return texture;
    }

    /**
     * Retrieve a texture from cache if available.
     * Otherwise create a new one.
     */
    public Texture getTexture(TextureMap textureMap) {
        Log.i(TAG, "Get texture: " + textureMap.name());
        Texture texture = textures.get(textureMap);
        texture.bindActiveTexture();
        return texture;
    }

    /**
     * Retrieve a texture from cache if available.
     * Otherwise create a new one.
     */
    public void reloadTextures() {

        final Texture texture;

        // create unique ids for our textures
//        TextureMap[] textures2 = TextureMap.values();
//        int[] ids = new int[textures2.length];
//        GLES20.glGenTextures(textures2.length, ids, 0);
//        int idx = 0;
//        for(TextureMap map : textures2) {
//            textureIds.put(map, ids[idx]);
//            Log.i(TAG, "Create texture id: " + ids[idx] + " for: " + map.name());
//            idx++;
//        }
//
//        for(TextureMap map : TextureMap.values()) {
//            Texture text = textures.get(map);
//            Integer textureId = textureIds.get(map);
//            text.reload(textureId);
//            Log.i(TAG, "Reload texture id: " + textureId + " for: " + map.name());
//        }

//        if (textureMaps.containsKey(textureMap)) {
//        Log.i(TAG, "Reload existing texture for: " + textureMap.name());
//        texture = textures.get(textureMap);
        // if retrieving an existing texture then reload/bind for OpenGL
//        texture.reload(textureIds.get(textureMap));
//        } else {
//            Log.i(TAG, "Create new texture for: " + textureMap.name());
//            texture = new Texture(xmlParser, textureLoader, textureMap);
//            textureMaps.put(textureMap, texture);
//        }

       // return texture;
    }
}
