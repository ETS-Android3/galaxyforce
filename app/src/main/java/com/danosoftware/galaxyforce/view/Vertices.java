package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.utilities.GlUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

class Vertices {

  private static final String TAG = "Vertices";

  private static final int BYTES_PER_FLOAT = 4;
  private static final int BYTES_PER_SHORT = 2;

  private static final int POSITION_COORDS_PER_VERTEX = 2;   // x,y
  private static final int TEXTURE_COORDS_PER_VERTEX = 2;    // u,v
  private static final int COLOUR_ELEMENTS_PER_VERTEX = 4;   // r,g,b,a

  private final boolean hasColor;
  private final boolean hasTexCoords;
  private final int vertexStride;
  private final FloatBuffer vertices;
  private final ShortBuffer indices;

  public Vertices(int maxVertices, int maxIndices, boolean hasColor,
      boolean hasTexCoords) {
    this.hasColor = hasColor;
    this.hasTexCoords = hasTexCoords;

    // each vertex requires 2 values for x,y position
    // plus an optional 4 values for RGBA colour (if colour required)
    // plus an optional 2 values for texture co-ordinates (if texture required)
    // each float value requires 4 bytes
    this.vertexStride = (POSITION_COORDS_PER_VERTEX
        + (hasColor ? COLOUR_ELEMENTS_PER_VERTEX : 0)
        + (hasTexCoords ? TEXTURE_COORDS_PER_VERTEX : 0))
        * BYTES_PER_FLOAT;

    // allocate a buffer with a byte-size big enough to hold all vertices (floats)
    ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexStride);
    buffer.order(ByteOrder.nativeOrder());
    vertices = buffer.asFloatBuffer();
    // allocate a buffer with a byte-size big enough to hold all indices (shorts)
    buffer = ByteBuffer.allocateDirect(maxIndices * BYTES_PER_SHORT);
    buffer.order(ByteOrder.nativeOrder());
    indices = buffer.asShortBuffer();
  }

  public void setVertices(float[] vertices, int offset, int length) {
    this.vertices.clear();
    this.vertices.put(vertices, offset, length);
    this.vertices.flip();
  }

  public void setIndices(short[] indices, int offset, int length) {
    this.indices.clear();
    this.indices.put(indices, offset, length);
    this.indices.flip();
  }

  public void bind() {

    // holds the offset to the wanted values from the start of each vertex
    int offset = 0;

    // prepare position co-ordinates from vertices
    vertices.position(offset);
    GLES20.glVertexAttribPointer(
        GLShaderHelper.sPositionHandle,
        POSITION_COORDS_PER_VERTEX,
        GLES20.GL_FLOAT,
        false,
        vertexStride,
        vertices);
    GLES20.glEnableVertexAttribArray(GLShaderHelper.sPositionHandle);

    offset += POSITION_COORDS_PER_VERTEX;

    // prepare colour values from vertices (if enabled)
    if (hasColor) {
      Log.e(TAG, "Colour vertices not yet supported by shader program");
      vertices.position(offset);
      GLES20.glVertexAttribPointer(
          -1, // tbc,
          COLOUR_ELEMENTS_PER_VERTEX,
          GLES20.GL_FLOAT,
          false,
          vertexStride,
          vertices);
      GLES20.glEnableVertexAttribArray(-1); // tbc

      offset += COLOUR_ELEMENTS_PER_VERTEX;
    }

    // prepare texture co-ordinates from vertices (if enabled)
    if (hasTexCoords) {
      vertices.position(offset);
      GLES20.glVertexAttribPointer(
          GLShaderHelper.sTexturePositionHandle,
          TEXTURE_COORDS_PER_VERTEX,
          GLES20.GL_FLOAT,
          false,
          vertexStride,
          vertices);
      GLES20.glEnableVertexAttribArray(GLShaderHelper.sTexturePositionHandle);

      // report any errors seen during binding - removed in release
      GlUtils.checkGlError("bindSpriteVertices");
    }
  }

  public void draw(int primitiveType, int offset, int numVertices) {

    if (indices != null) {
      indices.position(offset);
      GLES20.glDrawElements(primitiveType, numVertices, GLES20.GL_UNSIGNED_SHORT, indices);
    } else {
      GLES20.glDrawArrays(primitiveType, offset, numVertices);
    }

    // report any errors seen during drawing - removed in release
    GlUtils.checkGlError("drawSpriteVertices");
  }

  public void unbind() {

    // Disable vertex array.  Not strictly necessary.
    GLES20.glDisableVertexAttribArray(GLShaderHelper.sPositionHandle);
    if (hasColor) {
      GLES20.glDisableVertexAttribArray(-1); // tbc
    }
    if (hasTexCoords) {
      GLES20.glDisableVertexAttribArray(GLShaderHelper.sTexturePositionHandle);
    }

    // report any errors seen during unbinding - removed in release
    GlUtils.checkGlError("unbindSpriteVertices");
  }
}