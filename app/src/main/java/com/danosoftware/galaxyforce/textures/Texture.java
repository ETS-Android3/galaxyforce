package com.danosoftware.galaxyforce.textures;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.utilities.GlUtils;
import com.danosoftware.galaxyforce.view.GLGraphics;
import com.danosoftware.galaxyforce.view.GLShaderHelper;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single texture map and associated properties/utilities.
 * <p>
 * Loads the texture map PNG image file.
 * Loads the texture map XML properties file.
 * <p>
 * Texture map is bound to Open GL and a texture Id is assigned for future reference.
 * Supports re-loading of the texture map (maybe after application pause).
 */
public class Texture {

    private final GLGraphics glGraphics;
    private final TextureMap textureMap;
    private final TextureLoader textureLoader;

    // properties for each texture region in texture map
    private final Map<String, TextureDetail> textureDetailMap;

    private int textureId;
    private int minFilter;
    private int magFilter;
    private int width;
    private int height;

    private static final String TAG = "Texture";

    public Texture(
            final GLGraphics glGraphics,
            final TextureRegionXmlParser xmlParser,
            final TextureLoader textureLoader,
            final TextureMap textureMap) {
        this.textureMap = textureMap;
        this.glGraphics = glGraphics;
        this.textureLoader = textureLoader;
        this.textureDetailMap = buildTextureRegionMap(
                xmlParser,
                textureMap.getTextureXml());
        load();
    }

    private void load() {
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        GlUtils.checkGlError("glGenTextures");

        // load bitmap and copy pixels into a buffer
        Bitmap bitmap = textureLoader.load(textureMap.getTextureImage());
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buffer);

        // Bind the texture id to the 2D texture target.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // Configure min/mag filtering, i.e. what scaling method do we use if what we're rendering
        // is smaller or larger than the source image.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_NEAREST);
        GlUtils.checkGlError("loadImageTexture");

        // Load the data from the buffer into the texture id.
//        GLES20.glTexImage2D(
//            GLES20.GL_TEXTURE_2D,
//            0,
//            GLES20.GL_RGBA,
//            width,
//            height,
//            0,
//            GLES20.GL_RGBA,
//            GLES20.GL_UNSIGNED_BYTE,
//            buffer);
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GlUtils.checkGlError("loadImageTexture");

        bitmap.recycle();

        Log.d(TAG,
            "Loaded texture. Id: " + textureId + ". Filename: " + textureMap.getTextureImage()
                + ".");
    }

    public void reload() {
        load();
        GlUtils.checkGlError("load");
        bind();
        GlUtils.checkGlError("bind");
        setFilters(minFilter, magFilter);
        GlUtils.checkGlError("filters");
        //glGraphics.getGl().glBindTexture(GL10.GL_TEXTURE_2D, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GlUtils.checkGlError("bindText");
    }

    private void setFilters(int minFilter, int magFilter) {
//        this.minFilter = minFilter;
//        this.magFilter = magFilter;
//        GL10 gl = glGraphics.getGl();
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_NEAREST);
    }

    public void bind() {
//        GL10 gl = glGraphics.getGl();
//        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GlUtils.checkGlError("bindText");

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        //Log.d(TAG, "Use texture handle: " + GLShaderHelper.sTextureHandle);
//        GLES20.glUseProgram(GLShaderHelper.sProgramHandle);
//        GlUtils.checkGlError("glUseProgram");
        GLES20.glUniform1i(GLShaderHelper.sTextureHandle, 0);
        // ???? do we need this????
        GlUtils.checkGlError("glUniform");
    }

    public void dispose() {
//        GL10 gl = glGraphics.getGl();
//        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        int[] textureIds =
            {textureId};
//        gl.glDeleteTextures(1, textureIds, 0);
        GLES20.glDeleteTextures(1, textureIds, 0);

        Log.d(TAG,
            "Disposed texture. Id: " + textureId + ". Filename: " + textureMap.getTextureImage()
                + ".");
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /**
     * Create map of texture region name to region's texture properties.
     */
    private Map<String, TextureDetail> buildTextureRegionMap(
            final TextureRegionXmlParser xmlParser,
            final String textureXmlFile) {

        final Map<String, TextureDetail> textureDetailMap = new HashMap<>();
        for (TextureDetail texture : xmlParser.loadTextures(textureXmlFile)) {
            textureDetailMap.put(texture.getName(), texture);
        }
        return textureDetailMap;
    }

    /**
     * Return texture detail for supplied texture region name.
     */
    public TextureDetail getTextureDetail(String textureRegion) {

        if (!textureDetailMap.containsKey(textureRegion)) {
            String errorMessage = "Error: No texture details have been returned for region name: '" + textureRegion + "'.";
            Log.e(TAG, errorMessage);
            throw new GalaxyForceException(errorMessage);
        }

        return textureDetailMap.get(textureRegion);
    }
}
