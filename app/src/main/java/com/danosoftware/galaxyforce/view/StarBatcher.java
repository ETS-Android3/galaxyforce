package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.game.starfield.Star2;

public class StarBatcher {

  private static final int POSITION_COORDS_PER_VERTEX = 2;  // x,y
  private static final int COLOUR_COORDS_PER_VERTEX = 4;   // r,g,b,a
  private static final int COORDS_PER_VERTEX =
      POSITION_COORDS_PER_VERTEX + COLOUR_COORDS_PER_VERTEX;
  private static final int VERTICES_PER_STAR = 1;

  private float[] verticesBuffer;
  private Vertices vertices;
  private int bufferIndex;
  private int numStars;

  public StarBatcher(Star2[] starField) {
    setUpVertices(starField);
  }

  /**
   * initialises the star batcher with the initial star-field.
   */
  private void setUpVertices(Star2[] starField) {

    this.bufferIndex = 0;
    this.numStars = starField.length;

    // create an vertices buffer that holds star's position and colour per vertex
    this.verticesBuffer = new float[numStars * (POSITION_COORDS_PER_VERTEX
        + COLOUR_COORDS_PER_VERTEX)];

    // create vertices object to hold all vertices
    this.vertices = new Vertices(
            numStars * VERTICES_PER_STAR,
        0,
        true,
        false);

    int index = 0;
    for (Star2 star2 : starField) {
      // position vertex - x,y position
      verticesBuffer[index++] = star2.getX();
      verticesBuffer[index++] = star2.getY();

      // colour vertex - rgba colour
      final RgbColour colour = star2.getColour();
      verticesBuffer[index++] = colour.getRed(); // red
      verticesBuffer[index++] = colour.getGreen(); // green
      verticesBuffer[index++] = colour.getBlue(); // blue
      verticesBuffer[index++] = 1.0f; // alpha
    }
  }

  public void beginBatch() {
    // since each star's x-position and colour will never change,
    // we will only modify the y-position of our stars.
    // the first star's y-position is at index = 1
    bufferIndex = 1;
  }

  public void drawStar(Star2[] starField) {
    // update y-position of star and increment index to next vertex
    for (Star2 star2 : starField) {
      verticesBuffer[bufferIndex] = star2.getY();
      bufferIndex += COORDS_PER_VERTEX;
    }
    //verticesBuffer[bufferIndex+=COORDS_PER_VERTEX] = y;
  }

  public void endBatch() {
    vertices.setVertices(verticesBuffer, 0, numStars * COORDS_PER_VERTEX);
    vertices.bind();
    vertices.draw(GLES20.GL_POINTS, 0, numStars * VERTICES_PER_STAR);
    vertices.unbind();
  }
}
