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
  private final float[] mvp;

  public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight) {
    this.glGraphics = glGraphics;
    this.frustumWidth = frustumWidth;
    this.frustumHeight = frustumHeight;
    this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
    this.zoom = 1.0f;
    this.mvp = new float[16];
    createMatrices();
  }

  public void setViewportAndMatrices() {
    // set viewport to match view
    //GLES20.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());

    // Copy the model / view / projection matrix over.
    GLES20.glUniformMatrix4fv(GLShaderHelper.sMVPMatrixHandle, 1, false, mvp, 0);

    // report any errors seen during matrices set-up - removed in release
    GlUtils.checkGlError("setViewportAndMatrices");
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
    if (position.x != xPos) {
      this.position.x = xPos;
      createMatrices();
    }
  }

  /**
   * Reset x-position of the camera.
   */
  public void resetPosition() {
    float resetXPosition = frustumWidth / 2;
    if (position.x != resetXPosition) {
      this.position.x = resetXPosition;
      createMatrices();
    }
  }

  private void createMatrices() {
    // Create an orthographic projection that maps the desired arena size to the viewport dimensions.
    final float[] mProjectionMatrix = new float[16];
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
    final float[] mModelView = new float[16];
    Matrix.setIdentityM(mModelView, 0);

    // Calculate model-view-projection matrix
    Matrix.multiplyMM(mvp, 0, mProjectionMatrix, 0, mModelView, 0);
  }
}
