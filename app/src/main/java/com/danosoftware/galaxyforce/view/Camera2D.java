package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.danosoftware.galaxyforce.utilities.GlUtils;

public class Camera2D {

  private final Vector2 position;
  private final float zoom;
  private final float frustumWidth;
  private final float frustumHeight;
  private final GLGraphics glGraphics;
  private final float[] mProjectionMatrix;
  private final float[] mModelView;

  public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight) {
    this.glGraphics = glGraphics;
    this.frustumWidth = frustumWidth;
    this.frustumHeight = frustumHeight;
    this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
    this.zoom = 1.0f;
    this.mProjectionMatrix = new float[16];
    this.mModelView = new float[16];
  }

  public void setViewportAndMatrices() {
    GLES20.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
    GlUtils.checkGlError("glViewport");

    // Create an orthographic projection that maps the desired arena size to the viewport dimensions.
    Matrix.orthoM(
        mProjectionMatrix,
        0,
        position.x - frustumWidth * zoom / 2,
        position.x + frustumWidth * zoom / 2,
        position.y - frustumHeight * zoom / 2,
        position.y + frustumHeight * zoom / 2,
        1,
        -1);

    // Create model-view matrix
    Matrix.setIdentityM(mModelView, 0);

    // Calculate model-view-projection matrix
    float[] mvp = new float[16];
    Matrix.multiplyMM(mvp, 0, mProjectionMatrix, 0, mModelView, 0);

    // Copy the model / view / projection matrix over.
    GLES20.glUniformMatrix4fv(GLShaderHelper.sMVPMatrixHandle, 1, false, mvp, 0);
    GlUtils.checkGlError("mvpMatrix");

//    GL10 gl = glGraphics.getGl();
//    gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
//    gl.glMatrixMode(GL10.GL_PROJECTION);
//    gl.glLoadIdentity();
//    gl.glOrthof(position.x - frustumWidth * zoom / 2, position.x + frustumWidth * zoom / 2,
//        position.y - frustumHeight * zoom / 2,
//        position.y + frustumHeight * zoom / 2, 1, -1);
//    gl.glMatrixMode(GL10.GL_MODELVIEW);
//    gl.glLoadIdentity();
  }

  public void touchToWorld(Vector2 touch) {
    touch.x = (touch.x / (float) glGraphics.getWidth()) * frustumWidth * zoom;
    touch.y = (1 - touch.y / (float) glGraphics.getHeight()) * frustumHeight * zoom;
    touch.add(position).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
  }

  /**
   * Moves the x-position of the camera. Allows camera to move left and right from original
   * position.
   */
  public void moveX(float xPos) {
    this.position.x = xPos;
  }

  /**
   * Reset x-position of the camera.
   */
  public void resetPosition() {
    this.position.x = frustumWidth / 2;
  }
}
