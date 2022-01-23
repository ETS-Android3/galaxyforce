package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.utilities.GlUtils;

public class GLShaderHelper {

  private static final String TAG = "GLShaderHelper";

  // handle to the sprite shader program
  public static int spriteProgramHandle = -1;
  // handle to the point shader program
  public static int pointProgramHandle = -1;

  // Handles to the various components of the GL program.
  public static int sPositionHandle = -1;
  public static int sColourHandle = -1;
  public static int sTexturePositionHandle = -1;
  public static int sTextureHandle = -1;
  public static int sMVPMatrixHandle = -1;
  public static int sPointSizeHandle = -1;

  // Handles to the created shaders.
  public static int vertexShader;
  public static int fragmentShader;

  static final String VERTEX_SHADER_CODE =
      "uniform mat4 u_MVPMatrix;" +
          "attribute vec4 a_position;" +
          "attribute vec2 a_texCoord;" +
          "varying vec2 v_texCoord;" +
          "void main() {" +
          "  gl_Position = u_MVPMatrix * a_position;" +
          "  v_texCoord = a_texCoord;" +
          "}";

  static final String FRAGMENT_SHADER_CODE =
      "precision mediump float;" +
          "uniform sampler2D u_texture;" +
          "varying vec2 v_texCoord;" +
          "void main() {" +
          "  gl_FragColor = texture2D(u_texture, v_texCoord);" +
          "}";

  static final String POINT_VERTEX_SHADER_CODE =
      "uniform mat4 u_MVPMatrix;" +
          "attribute vec4 a_position;" +
          "attribute vec4 a_color;" +
          "varying vec4 v_color;" +
          "uniform float u_pointSize;" +
          "void main() {" +
          "   gl_Position = u_MVPMatrix * a_position;" +
          "   gl_PointSize = u_pointSize;" +
          "   v_color = a_color;" +
          "}";

  static final String POINT_FRAGMENT_SHADER_CODE =
      "precision mediump float;" +
          "varying vec4 v_color;" +
          "void main() {" +
          "   gl_FragColor = v_color;" +
          "}";

  /**
   * Creates the GL program and associated references.
   */
  public static void createProgram() {
    spriteProgramHandle = GlUtils.createProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
    Log.d(TAG, "Created sprite shader program " + spriteProgramHandle);

    pointProgramHandle = GlUtils
        .createProgram(POINT_VERTEX_SHADER_CODE, POINT_FRAGMENT_SHADER_CODE);
    Log.d(TAG, "Created point shader program " + pointProgramHandle);

    // capture any GL errors while creating program - removed in release
    GlUtils.checkGlError("createProgram");
  }

  public static void setSpriteShaderProgram() {
    // Use our shader program for GL
    resetHandles();
    GLES20.glUseProgram(spriteProgramHandle);

    // get handle to vertex shader's position co-ordinate member
    sPositionHandle = GLES20.glGetAttribLocation(spriteProgramHandle, "a_position");

    // get handle to vertex shader's texture co-ordinate member
    sTexturePositionHandle = GLES20.glGetAttribLocation(spriteProgramHandle, "a_texCoord");

    // get handle to fragment shader's texture member
    sTextureHandle = GLES20.glGetUniformLocation(spriteProgramHandle, "u_texture");

    // get handle to vertex shader's transformation matrix
    sMVPMatrixHandle = GLES20.glGetUniformLocation(spriteProgramHandle, "u_MVPMatrix");

    // capture any GL errors while setting handles
    GlUtils.checkGlError("setSpriteShaderProgram");

  }

  public static void setPointShaderProgram() {
    // Use our shader program for GL
    resetHandles();
    GLES20.glUseProgram(pointProgramHandle);

    // get handle to vertex shader's position co-ordinate member
    sPositionHandle = GLES20.glGetAttribLocation(pointProgramHandle, "a_position");

    // get handle to vertex shader's texture co-ordinate member
    sColourHandle = GLES20.glGetAttribLocation(pointProgramHandle, "a_color");

    // get handle to vertex shader's transformation matrix
    sMVPMatrixHandle = GLES20.glGetUniformLocation(pointProgramHandle, "u_MVPMatrix");

    // get handle to vertex shader's point size
    sPointSizeHandle = GLES20.glGetUniformLocation(pointProgramHandle, "u_pointSize");

    // capture any GL errors while setting handles
    GlUtils.checkGlError("setPointShaderProgram");
  }

  private static void resetHandles() {
    sPositionHandle = -1;
    sColourHandle = -1;
    sTexturePositionHandle = -1;
    sTextureHandle = -1;
    sMVPMatrixHandle = -1;
    sPointSizeHandle = -1;
  }

//  /**
//   * Dispose of the GL program and shaders.
//   */
//  public static void dispose() {
//    GLES20.glDetachShader(sProgramHandle, vertexShader);
//    GLES20.glDetachShader(sProgramHandle, fragmentShader);
//
//    GLES20.glDeleteShader(vertexShader);
//    GLES20.glDeleteShader(fragmentShader);
//
//    GLES20.glDeleteProgram(sProgramHandle);
//  }

//  /**
//   * Creates a program, given source code for vertex and fragment shaders.
//   *
//   * @param vertexShaderCode   Source code for vertex shader.
//   * @param fragmentShaderCode Source code for fragment shader.
//   * @return Handle to program.
//   */
//  public static int createProgram(String vertexShaderCode, String fragmentShaderCode) {
//    // Load the shaders.
//    vertexShader =
//        GlUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
//    fragmentShader =
//        GlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
//
//    // Build the program.
//    int programHandle = GLES20.glCreateProgram();
//    GLES20.glAttachShader(programHandle, vertexShader);
//    GLES20.glAttachShader(programHandle, fragmentShader);
//    GLES20.glLinkProgram(programHandle);
//
//    // Check for failure.
//    int[] linkStatus = new int[1];
//    GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
//    if (linkStatus[0] != GLES20.GL_TRUE) {
//      // Extract the detailed failure message.
//      String msg = GLES20.glGetProgramInfoLog(programHandle);
//      GLES20.glDeleteProgram(programHandle);
//      Log.e(TAG, "glLinkProgram: " + msg);
//      throw new GalaxyForceException("glLinkProgram failed");
//    }
//
//    return programHandle;
//  }
}
