package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.utilities.GlUtils;

public class GLShaderHelper {

  private static final String TAG = "GLShaderHelper";

  // Handles to the GL program and various components of it.
  public static int sProgramHandle = -1;
  public static int sPositionHandle = -1;
  public static int sTexturePositionHandle = -1;
  public static int sTextureHandle = -1;
  public static int sMVPMatrixHandle = -1;

  static final String VERTEX_SHADER_CODE =
      "uniform mat4 u_MVPMatrix;" +
          "attribute vec4 a_Position;" +
          "attribute vec2 a_texCoord;" +
          "varying vec2 v_texCoord;" +
          "void main() {" +
          "  gl_Position = u_MVPMatrix * a_Position;" +
          "  v_texCoord = a_texCoord;" +
          "}";

  static final String FRAGMENT_SHADER_CODE =
      "precision mediump float;" +
          "uniform sampler2D u_texture;" +
          "varying vec2 v_texCoord;" +
          "void main() {" +
          "  gl_FragColor = texture2D(u_texture, v_texCoord);" +
          "}";

  /**
   * Creates the GL program and associated references.
   */
  public static void createProgram() {
    sProgramHandle = GlUtils.createProgram(VERTEX_SHADER_CODE,
        FRAGMENT_SHADER_CODE);
    Log.d(TAG, "Created program " + sProgramHandle);

    // get handle to vertex shader's a_position member
    sPositionHandle = GLES20.glGetAttribLocation(sProgramHandle, "a_Position");
    GlUtils.checkGlError("glGetAttribLocation");

    // get handle to vertex shader's a_texCoord member
    sTexturePositionHandle = GLES20.glGetAttribLocation(sProgramHandle, "a_texCoord");
    GlUtils.checkGlError("glGetUniformLocation");

    // get handle to fragment shader's u_texture member
    sTextureHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_texture");
    GlUtils.checkGlError("glGetUniformLocation");
    Log.d(TAG, "Created Texture Handle " + sTextureHandle);

    // get handle to transformation matrix
    sMVPMatrixHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_MVPMatrix");
    GlUtils.checkGlError("glGetUniformLocation");
  }

//  public static void dispose() {
//    GLES20.glDetachShader(sProgramHandle, ShaderHelper.vertexShaderImage);
//    GLES20.glDetachShader(sProgramHandle, ShaderHelper.fragmentShaderImage);
//
//    GLES20.glDeleteShader(ShaderHelper.fragmentShaderImage);
//    GLES20.glDeleteShader(ShaderHelper.vertexShaderImage);
//
//    GLES20.glDeleteProgram(ShaderHelper.programTexture);
//  }
}
