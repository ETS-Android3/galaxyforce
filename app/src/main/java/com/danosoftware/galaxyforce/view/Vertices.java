package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import com.danosoftware.galaxyforce.utilities.GlUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

class Vertices {

  private static final int BYTES_PER_FLOAT = 4;
  private static final int BYTES_PER_SHORT = 2;
  private static final int POSITION_DATA_SIZE_IN_ELEMENTS = 2;
//  private static final int STRIDE = (POSITION_DATA_SIZE_IN_ELEMENTS + NORMAL_DATA_SIZE_IN_ELEMENTS + COLOR_DATA_SIZE_IN_ELEMENTS)
//      * BYTES_PER_FLOAT;

  private final GLGraphics glGraphics;
  private final boolean hasColor;
  private final boolean hasTexCoords;
  private final int vertexSize;
  private final IntBuffer vertices;
  private final int[] tmpBuffer;
  private final ShortBuffer indices;

  public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasColor,
      boolean hasTexCoords) {
    this.glGraphics = glGraphics;
    this.hasColor = hasColor;
    this.hasTexCoords = hasTexCoords;
    // each vertex requires 2 values for x,y position
    // plus an optional 4 values for RGBA colour (if colour required)
    // plus an optional 2 values for texture co-ordinates (if texture required)
    // each float value requires 4 bytes
    this.vertexSize = (2 + (hasColor ? 4 : 0) + (hasTexCoords ? 2 : 0)) * 4;
    this.tmpBuffer = new int[maxVertices * vertexSize / 4];

    // allocate a buffer with a byte-size big enough to hold all vertices (floats)
    ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
    buffer.order(ByteOrder.nativeOrder());
    vertices = buffer.asIntBuffer();
    // allocate a buffer with a byte-size big enough to hold all indices (shorts)
    buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
    buffer.order(ByteOrder.nativeOrder());
    indices = buffer.asShortBuffer();
  }

  public void setVertices(float[] vertices, int offset, int length) {
    this.vertices.clear();
    int len = offset + length;
    for (int i = offset, j = 0; i < len; i++, j++) {
      tmpBuffer[j] = Float.floatToRawIntBits(vertices[i]);
    }
    this.vertices.put(tmpBuffer, 0, length);
    this.vertices.flip();
  }

  public void setIndices(short[] indices, int offset, int length) {
    this.indices.clear();
    this.indices.put(indices, offset, length);
    this.indices.flip();
  }

  public void bind() {
    GL10 gl = glGraphics.getGl();

    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    vertices.position(0);
    gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);

    if (hasColor) {
      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
      vertices.position(2);
      gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
    }

    if (hasTexCoords) {
      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      vertices.position(hasColor ? 6 : 2);
      gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
    }
  }

  public void draw(int primitiveType, int offset, int numVertices) {

    // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
    //GLES20.glUniform1i(GLShaderHelper.sTextureHandle, 0);

//    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertices[0]);

//    GLES20.glVertexAttribPointer(GLShaderHelper.sPositionHandle, POSITION_DATA_SIZE_IN_ELEMENTS, GLES20.GL_FLOAT, false,
//        vertexSize, 0);
    vertices.position(0);
    GLES20.glVertexAttribPointer(GLShaderHelper.sPositionHandle, POSITION_DATA_SIZE_IN_ELEMENTS,
        GLES20.GL_FLOAT, false,
        vertexSize, vertices);
    GLES20.glEnableVertexAttribArray(GLShaderHelper.sPositionHandle);
    GlUtils.checkGlError("glDrawArrays");

    vertices.position(hasColor ? 6 : 2);
    GLES20.glVertexAttribPointer(GLShaderHelper.sTexturePositionHandle,
        POSITION_DATA_SIZE_IN_ELEMENTS, GLES20.GL_FLOAT, false, vertexSize, vertices);
    GLES20.glEnableVertexAttribArray(GLShaderHelper.sTexturePositionHandle);
    GlUtils.checkGlError("glDrawArrays");

    // Draw
    //GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices[0]);
    GLES20.glDrawElements(GLES20.GL_TRIANGLES, numVertices, GLES20.GL_UNSIGNED_SHORT, indices);
    GlUtils.checkGlError("glDrawArrays");

    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    GlUtils.checkGlError("glDrawArrays");

    //GL10 gl = glGraphics.getGl();

//    if (indices != null) {
//      indices.position(offset);
//      gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
//    } else {
//      gl.glDrawArrays(primitiveType, offset, numVertices);
//    }

    // Pass the triangle coordinate data to vertex shader
//    GLES20.glVertexAttribPointer(GLShaderHelper.sPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
//    GlUtils.checkGlError("glDrawArrays");

    // Pass the texture coordinate data to fragment shader
//    GLES20.glVertexAttribPointer(GLShaderHelper.sTexturePositionHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
//    GlUtils.checkGlError("glDrawArrays");

    // set view matrix??
    //GLES20.glUniformMatrix4fv(u_MVPMatrix, 1, false, mtrxMVP, 0);

    // Draw the triangles
//    GLES20.glDrawElements(GLES20.GL_TRIANGLES, numVertices, GLES20.GL_UNSIGNED_SHORT, indices);
//    GlUtils.checkGlError("glDrawArrays");
  }

  public void unbind() {
    GL10 gl = glGraphics.getGl();
    if (hasTexCoords) {
      gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    if (hasColor) {
      gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
  }
}
