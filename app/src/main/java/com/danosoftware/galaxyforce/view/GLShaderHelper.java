package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.utilities.GlUtils;

public class GLShaderHelper {

  private static final String TAG = "GLShaderHelper";

  // Handles to the GL program and various components of it.
  public static int sProgramHandle = -1;
  public static int sPositionHandle = -1;
  public static int sTexturePositionHandle = -1;
  public static int sTextureHandle = -1;
  public static int sMVPMatrixHandle = -1;

  // Handles to the created shaders.
  public static int vertexShader;
  public static int fragmentShader;

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
    sProgramHandle = GlUtils.createProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
    Log.d(TAG, "Created shader program " + sProgramHandle);

    // get handle to vertex shader's position co-ordinate member
    sPositionHandle = GLES20.glGetAttribLocation(sProgramHandle, "a_Position");

    // get handle to vertex shader's texture co-ordinate member
    sTexturePositionHandle = GLES20.glGetAttribLocation(sProgramHandle, "a_texCoord");

    // get handle to fragment shader's texture member
    sTextureHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_texture");

    // get handle to vertex shader's transformation matrix
    sMVPMatrixHandle = GLES20.glGetUniformLocation(sProgramHandle, "u_MVPMatrix");

    // capture any GL errors while creating program
    GlUtils.checkGlError("createProgram");
  }

  /**
   * Dispose of the GL program and shaders.
   */
  public static void dispose() {
    GLES20.glDetachShader(sProgramHandle, vertexShader);
    GLES20.glDetachShader(sProgramHandle, fragmentShader);

    GLES20.glDeleteShader(vertexShader);
    GLES20.glDeleteShader(fragmentShader);

    GLES20.glDeleteProgram(sProgramHandle);
  }

  /**
   * Creates a program, given source code for vertex and fragment shaders.
   *
   * @param vertexShaderCode   Source code for vertex shader.
   * @param fragmentShaderCode Source code for fragment shader.
   * @return Handle to program.
   */
  public static int createProgram(String vertexShaderCode, String fragmentShaderCode) {
    // Load the shaders.
    vertexShader =
        GlUtils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    fragmentShader =
        GlUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

    // Build the program.
    int programHandle = GLES20.glCreateProgram();
    GLES20.glAttachShader(programHandle, vertexShader);
    GLES20.glAttachShader(programHandle, fragmentShader);
    GLES20.glLinkProgram(programHandle);

    // Check for failure.
    int[] linkStatus = new int[1];
    GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
    if (linkStatus[0] != GLES20.GL_TRUE) {
      // Extract the detailed failure message.
      String msg = GLES20.glGetProgramInfoLog(programHandle);
      GLES20.glDeleteProgram(programHandle);
      Log.e(TAG, "glLinkProgram: " + msg);
      throw new GalaxyForceException("glLinkProgram failed");
    }

    return programHandle;
  }
}
