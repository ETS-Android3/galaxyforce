package com.danosoftware.galaxyforce.view;

import android.util.Log;
import com.danosoftware.galaxyforce.textures.Texture;
import com.danosoftware.galaxyforce.textures.TextureRegion;
import javax.microedition.khronos.opengles.GL10;

public class SpriteBatcher {

  private final int SPRITE_BUFFER = 25;
  private final int SPRITE_REDUCE_BUFFER = 20;
  private final GLGraphics glGraphics;
  private float[] verticesBuffer;
  private Vertices vertices;
  private int bufferIndex;
  private int numSprites;
  private int maxSprites;

  public SpriteBatcher(GLGraphics glGraphics) {
    this.glGraphics = glGraphics;
    setUpVertices(0);
  }

  private void setUpVertices(int spriteCount) {
    this.maxSprites = spriteCount;
    this.verticesBuffer = new float[spriteCount * 4 * 4];
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
  }

  public void endBatch() {
    vertices.setVertices(verticesBuffer, 0, bufferIndex);
    vertices.bind();
    vertices.draw(GL10.GL_TRIANGLES, 0, numSprites * 6);
    vertices.unbind();
  }

  public void drawSprite(float x, float y, float width, float height, TextureRegion region) {
    if (numSprites >= maxSprites) {
      Log.e("SpriteBatcher",
          "Attempt to draw sprite beyond allowed maximum of " + maxSprites + " sprites");
      return;
    }

    float halfWidth = width / 2;
    float halfHeight = height / 2;
    float x1 = x - halfWidth;
    float y1 = y - halfHeight;
    float x2 = x + halfWidth;
    float y2 = y + halfHeight;

    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV2();

    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV2();

    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV1();

    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV1();

    numSprites++;
  }

  public void drawSprite(float x, float y, float width, float height, float angle,
      TextureRegion region) {
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

    verticesBuffer[bufferIndex++] = x1;
    verticesBuffer[bufferIndex++] = y1;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV2();

    verticesBuffer[bufferIndex++] = x2;
    verticesBuffer[bufferIndex++] = y2;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV2();

    verticesBuffer[bufferIndex++] = x3;
    verticesBuffer[bufferIndex++] = y3;
    verticesBuffer[bufferIndex++] = region.getU2();
    verticesBuffer[bufferIndex++] = region.getV1();

    verticesBuffer[bufferIndex++] = x4;
    verticesBuffer[bufferIndex++] = y4;
    verticesBuffer[bufferIndex++] = region.getU1();
    verticesBuffer[bufferIndex++] = region.getV1();

    numSprites++;
  }
}
