package com.danosoftware.galaxyforce.view;

import android.opengl.GLSurfaceView;

public class GLGraphics {

  private final GLSurfaceView glView;

  public GLGraphics(GLSurfaceView glView) {
    this.glView = glView;
  }

  public int getWidth() {
    return glView.getWidth();
  }

  public int getHeight() {
    return glView.getHeight();
  }
}
