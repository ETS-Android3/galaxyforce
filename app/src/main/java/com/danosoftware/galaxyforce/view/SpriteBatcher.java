package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import com.danosoftware.galaxyforce.utilities.GlUtils;
import javax.microedition.khronos.opengles.GL10;

public class SpriteBatcher {

  private static final String TAG = SpriteBatcher.TAG;

  // Handles to the GL program and various components of it.
  static int sProgramHandle = -1;
  static int sColorHandle = -1;
  static int sPositionHandle = -1;
  static int sMVPMatrixHandle = -1;

  /**
   * Simple square, specified as a triangle strip.  The square is centered on (0,0) and has a size
   * of 1x1.
   * <p>
   * Triangles are 0-1-2 and 2-1-3 (counter-clockwise winding).
   */
  private static final float COORDS[] = {
      -0.5f, -0.5f,   // 0 bottom left
      0.5f, -0.5f,    // 1 bottom right
      -0.5f, 0.5f,   // 2 top left
      0.5f, 0.5f,    // 3 top right
  };

  public static final int COORDS_PER_VERTEX = 2;         // x,y
  public static final int TEX_COORDS_PER_VERTEX = 2;     // s,t
  public static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per float
  public static final int TEX_VERTEX_STRIDE = TEX_COORDS_PER_VERTEX * 4;
  public static final int VERTEX_COUNT = COORDS.length / COORDS_PER_VERTEX;

  // RGBA color vector.
  float[] mColor = new float[4];

  private final int SPRITE_BUFFER = 25;
  private final int SPRITE_REDUCE_BUFFER = 20;
  private final GLGraphics glGraphics;
  private float[] verticesBuffer;
  private Vertices vertices;
  private int bufferIndex;
  private int numSprites;
  private int maxSprites;

  static final String VERTEX_SHADER_CODE =
      "uniform mat4 u_mvpMatrix;" +
          "attribute vec4 a_position;" +

          "void main() {" +
          "  gl_Position = u_mvpMatrix * a_position;" +
          "}";

  static final String FRAGMENT_SHADER_CODE =
      "precision mediump float;" +
          "uniform vec4 u_color;" +

          "void main() {" +
          "  gl_FragColor = u_color;" +
          "}";

  public SpriteBatcher(GLGraphics glGraphics) {
    this.glGraphics = glGraphics;
    setUpVertices(0);
  }

  private void setUpVertices(int spriteCount) {
    // each rectangular sprite has 4 vertices (total = 4 * number of sprites).
    // vertex 0 = bottom-left
    // vertex 1 = bottom-right
    // vertex 2 = top-right
    // vertex 3 = top-left
    //
    // each rectangular sprite is drawn as two triangles, each having three vertices (6 in total).
    // triangle 1 = vertices 0,1,2
    // triangle 2 = vertices 2,3,0
    //
    // the indices hold an array of these 6 vertices for each sprite (total = 6 * number of sprites).
    this.maxSprites = spriteCount;
    this.verticesBuffer = new float[spriteCount * 4
        * 4]; // holds position and texture co-ords per vertex
    this.vertices = new Vertices(glGraphics, spriteCount * 4, spriteCount * 6, false, true);
    this.bufferIndex = 0;
    this.numSprites = 0;

    short[] indices = new short[spriteCount * 6];
    int len = indices.length;
    short j = 0;
    for (int i = 0; i < len; i += 6, j += 4) {
      indices[i] = j;
      indices[i + 1] = (short) (j + 1);
      indices[i + 2] = (short) (j + 2);
      indices[i + 3] = (short) (j + 2);
      indices[i + 4] = (short) (j + 3);
      indices[i + 5] = j;
    }
    this.vertices.setIndices(indices, 0, indices.length);
  }

  public void beginBatch(Texture texture, int spriteCount) {

    /*
     * The vertices arrays need to handle the number of sprites we intend to draw.
     * Keeping this as small as possible ensures good drawing performance.
     *
     * If the number of sprites to be drawn exceeds the maximum we can support,
     * we must increase the array size. if the number of sprites drops, we should reduce the array.
     *
     * Re-sizing the array is an expensive operation and will require garbage collection of the old array.
     * We should attempt to minimise the amount of re-sizing events.
     *
     * When we increase the array, we increase by a buffer to allow some headroom for growth before next resize.
     *
     * When we reduce the array, we still want to keep some headroom for future growth.
     * We use a larger buffer when deciding when to reduce the array to avoid flip-flopping between increase and reduce events.
     */
    if (spriteCount > maxSprites) {
      // increase vertices array if we exceed maximum allowed
      setUpVertices(spriteCount + SPRITE_BUFFER);
      Log.d("SpriteBatcher",
          "Increased sprite capacity to " + maxSprites + " sprites (current count = " + spriteCount
              + ")");
    } else if (spriteCount < maxSprites - (SPRITE_BUFFER + SPRITE_REDUCE_BUFFER)) {
      // decrease vertices array if we drop below threshold
      setUpVertices(spriteCount + SPRITE_BUFFER);
      Log.d("SpriteBatcher",
          "Decreased sprite capacity to " + maxSprites + " sprites (current count = " + spriteCount
              + ")");
    }
    texture.bind();
    numSprites = 0;
    bufferIndex = 0;

    // Select the program.
//    GLES20.glUseProgram(sProgramHandle);
//    GlUtils.checkGlError("glUseProgram");
//
//    // Enable the "a_position" vertex attribute.
//    GLES20.glEnableVertexAttribArray(sPositionHandle);
//    GlUtils.checkGlError("glEnableVertexAttribArray");
//
//    // Connect sVertexBuffer to "a_position".
//    GLES20.glVertexAttribPointer(sPositionHandle, COORDS_PER_VERTEX,
//        GLES20.GL_FLOAT, false, VERTEX_STRIDE, sVertexBuffer);
//    GlUtils.checkGlError("glVertexAttribPointer");
  }

  public void drawSprite(float x, float y, float width, float height, TextureRegion region) {
    if (numSprites >= maxSprites) {
      Log.e(TAG,
          "Attempt to draw sprite beyond allowed maximum of " + maxSprites + " sprites");
      return;
    }

    float halfWidth = width / 2;
    float halfHeight = height / 2;
    float x1 = x - halfWidth;
    float y1 = y - halfHeight;
    float x2 = x + halfWidth;
    float y2 = y + halfHeight;

    // vertex 0 - bottom-left
    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV2();

    // vertex 1 - bottom-right
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV2();

    // vertex 2 - top-right
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV1();

    // vertex 3 - top-left
    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV1();

    numSprites++;
  }

  public void drawSprite(float x, float y, float width, float height, float angle,
      TextureRegion region) {
    if (numSprites >= maxSprites) {
      Log.e(TAG,
          "Attempt to draw sprite beyond allowed maximum of " + maxSprites + " sprites");
      return;
    }

    float halfWidth = width / 2;
    float halfHeight = height / 2;

    float rad = angle * Vector2.TO_RADIANS;
    float cos = (float) Math.cos(rad);
    float sin = (float) Math.sin(rad);

    float x1 = -halfWidth * cos - (-halfHeight) * sin;
    float y1 = -halfWidth * sin + (-halfHeight) * cos;
    float x2 = halfWidth * cos - (-halfHeight) * sin;
    float y2 = halfWidth * sin + (-halfHeight) * cos;
    float x3 = halfWidth * cos - halfHeight * sin;
    float y3 = halfWidth * sin + halfHeight * cos;
    float x4 = -halfWidth * cos - halfHeight * sin;
    float y4 = -halfWidth * sin + halfHeight * cos;

    x1 += x;
    y1 += y;
    x2 += x;
    y2 += y;
    x3 += x;
    y3 += y;
    x4 += x;
    y4 += y;

    // vertex 0 - bottom-left (with rotation)
    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV2();

    // vertex 1 - bottom-right (with rotation)
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV2();

    // vertex 2 - top-right (with rotation)
    verticesBuffer[bufferIndex++] = x3;
    verticesBuffer[bufferIndex++] = y3;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV1();

    // vertex 3 - top-left (with rotation)
    verticesBuffer[bufferIndex++] = x4;
    verticesBuffer[bufferIndex++] = y4;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV1();

    numSprites++;
  }

  public void endBatch() {
    vertices.setVertices(verticesBuffer, 0, bufferIndex);
    //vertices.bind();
    vertices.draw(GL10.GL_TRIANGLES, 0, numSprites * 6);
    //vertices.unbind();

//    mColor[0] = 255;
//    mColor[1] = 255;
//    mColor[2] = 255;
//    mColor[3] = 1.0f;

    // Compute model/view/projection matrix.
//    float[] mvp = sTempMVP;     // scratch storage
//    Matrix.multiplyMM(mvp, 0, GameSurfaceRenderer.mProjectionMatrix, 0, mModelView, 0);

    // Copy the model / view / projection matrix over.
//    GLES20.glUniformMatrix4fv(sMVPMatrixHandle, 1, false, mvp, 0);
//    GlUtils.checkGlError("glUniformMatrix4fv");

    // Copy the color vector into the program.
//    GLES20.glUniform4fv(sColorHandle, 1, mColor, 0);
//    GlUtils.checkGlError("glUniform4fv ");

    // Draw the rect.
//    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT);
//    GlUtils.checkGlError("glDrawArrays");

    // Disable vertex array and program.  Not strictly necessary.
//    GLES20.glDisableVertexAttribArray(sPositionHandle);
//    GLES20.glUseProgram(0);
  }

  /**
   * Creates the GL program and associated references.
   */
  public static void createProgram() {
    sProgramHandle = GlUtils.createProgram(VERTEX_SHADER_CODE,
        FRAGMENT_SHADER_CODE);
    Log.d(TAG, "Created program " + sProgramHandle);

    // get handle to vertex shader's a_position member
    sPositionHandle = GLES20.glGetAttribLocation(sProgramHandle, "a_position");
    GlUtils.checkGlError("glGetAttribLocation");

    // get handle to fragment shader's u_color member
    sColorHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_color");
    GlUtils.checkGlError("glGetUniformLocation");

    // get handle to transformation matrix
    sMVPMatrixHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_mvpMatrix");
    GlUtils.checkGlError("glGetUniformLocation");
  }
}
