package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.util.Log;
import com.danosoftware.galaxyforce.utilities.GlUtils;
import com.danosoftware.galaxyforce.utilities.ShaderProgram;

public class GLShaderHelper {

  private static final String TAG = "GLShaderHelper";

  // shader programs
  private static ShaderProgram spriteShader;
  private static ShaderProgram pointShader;

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
    spriteShader = GlUtils.createProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
    spriteProgramHandle = spriteShader.getProgramHandle();
    Log.d(TAG, "Created sprite shader program " + spriteShader);

    pointShader = GlUtils.createProgram(POINT_VERTEX_SHADER_CODE, POINT_FRAGMENT_SHADER_CODE);
    pointProgramHandle = pointShader.getProgramHandle();
    Log.d(TAG, "Created point shader program " + pointShader);

    // capture any GL errors while creating program - removed in release
    GlUtils.checkGlError("createProgram");
  }

  /**
   * Destroys the GL program and associated references.
   */
  public static void deleteProgram() {
    if (spriteShader != null) {
      GlUtils.dispose(spriteShader);
      Log.d(TAG, "Deleted sprite shader program " + spriteShader);
      spriteShader = null;
    }
    if (pointShader != null) {
      GlUtils.dispose(pointShader);
      Log.d(TAG, "Deleted point shader program " + pointShader);
      pointShader = null;
    }
    resetHandles();

    // capture any GL errors while creating program - removed in release
    GlUtils.checkGlError("deleteProgram");
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
}
