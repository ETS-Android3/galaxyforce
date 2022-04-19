package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureRegion;

public class SpriteBatcher {

  private static final String TAG = "SpriteBatcher";

  private static final int VERTICES_PER_SPRITE = 4;         // 4 vertices per rectangle
  private static final int INDICES_PER_SPRITE = 6;          // 6 indices for a rectangle made from 2 triangles
  private static final int POSITION_COORDS_PER_VERTEX = 2;  // x,y
  private static final int TEXTURE_COORDS_PER_VERTEX = 2;   // u,v
  private static final int SPRITE_BUFFER = 25;
  private static final int SPRITE_REDUCE_BUFFER = 20;

  private float[] verticesBuffer;
  private Vertices vertices;
  private int bufferIndex;
  private int numSprites;
  private int maxSprites;

  public SpriteBatcher() {
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

    this.maxSprites = spriteCount;
    this.bufferIndex = 0;
    this.numSprites = 0;

    // create an vertices buffer that holds position and texture co-ords per vertex
    this.verticesBuffer = new float[spriteCount * VERTICES_PER_SPRITE * (POSITION_COORDS_PER_VERTEX
        + TEXTURE_COORDS_PER_VERTEX)];

    // create vertices object to hold all vertices and indices
    this.vertices = new Vertices(
        spriteCount * VERTICES_PER_SPRITE,
        spriteCount * INDICES_PER_SPRITE,
        false,
        true);

    // the indices contain 6 vertices for each sprite.
    // each sprite requires two triangles containing 6 vertices.
    // triangle 1 = vertices 0,1,2
    // triangle 2 = vertices 2,3,0
    short[] indices = new short[spriteCount * INDICES_PER_SPRITE];
    int len = indices.length;
    short j = 0;
    for (int i = 0; i < len; i += INDICES_PER_SPRITE, j += VERTICES_PER_SPRITE) {
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
    verticesBuffer[bufferIndex++] = region.u1;
    verticesBuffer[bufferIndex++] = region.v2;

    // vertex 1 - bottom-right
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.u2;
    verticesBuffer[bufferIndex++] = region.v2;

    // vertex 2 - top-right
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.u2;
    verticesBuffer[bufferIndex++] = region.v1;

    // vertex 3 - top-left
    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.u1;
    verticesBuffer[bufferIndex++] = region.v1;

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
    verticesBuffer[bufferIndex++] = region.u1;
    verticesBuffer[bufferIndex++] = region.v2;

    // vertex 1 - bottom-right (with rotation)
    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.u2;
    verticesBuffer[bufferIndex++] = region.v2;

    // vertex 2 - top-right (with rotation)
    verticesBuffer[bufferIndex++] = x3;
    verticesBuffer[bufferIndex++] = y3;
    verticesBuffer[bufferIndex++] = region.u2;
    verticesBuffer[bufferIndex++] = region.v1;

    // vertex 3 - top-left (with rotation)
    verticesBuffer[bufferIndex++] = x4;
    verticesBuffer[bufferIndex++] = y4;
    verticesBuffer[bufferIndex++] = region.u1;
    verticesBuffer[bufferIndex++] = region.v1;

    numSprites++;
  }

  public void endBatch() {
    if (numSprites == 0) {
      return;
    }

    vertices.setVertices(verticesBuffer, 0, bufferIndex);
    vertices.bind();
    vertices.draw(GLES20.GL_TRIANGLES, 0, numSprites * INDICES_PER_SPRITE);
    vertices.unbind();
  }
}
