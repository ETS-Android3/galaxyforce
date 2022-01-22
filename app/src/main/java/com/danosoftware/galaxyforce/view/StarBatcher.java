package com.danosoftware.galaxyforce.view;

import android.opengl.GLES20;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;
import com.danosoftware.galaxyforce.sprites.game.starfield.Star;

public class StarBatcher {

  private static final int POSITION_COORDS_PER_VERTEX = 2;  // x,y
  private static final int COLOUR_COORDS_PER_VERTEX = 4;   // r,g,b,a
  private static final int COORDS_PER_VERTEX =
      POSITION_COORDS_PER_VERTEX + COLOUR_COORDS_PER_VERTEX;
  private static final int VERTICES_PER_STAR = 1;

  private final float[] verticesBuffer;
  private final Vertices vertices;
  private final int numStars;
  private final Star[] starField;

  public StarBatcher(Star[] starField) {
    this.starField = starField;
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

    setUpVertices();
  }

  /**
   * initialises the vertices buffer with the initial star-field.
   */
  private void setUpVertices() {
    int index = 0;
    for (Star star2 : starField) {
      // position vertex - x,y position
      verticesBuffer[index++] = star2.getX();
      verticesBuffer[index++] = star2.getY();

      // colour vertex - rgba colour
      final RgbColour colour = star2.colour();
      verticesBuffer[index++] = colour.getRed(); // red
      verticesBuffer[index++] = colour.getGreen(); // green
      verticesBuffer[index++] = colour.getBlue(); // blue
      verticesBuffer[index++] = 1.0f; // alpha
    }
  }

  public void drawStars() {
    // since each star's x-position will never change,
    // we will only modify the y-position and the colour of our stars.

    // the first star's y-position within the buffer is at index = 1
    int bufferIndex = 1;

    // update y-position and colour of each star and increment index to next vertex.
    for (Star star2 : starField) {
      verticesBuffer[bufferIndex++] = star2.getY();

      final RgbColour colour = star2.colour();
      verticesBuffer[bufferIndex++] = colour.getRed();
      verticesBuffer[bufferIndex++] = colour.getGreen();
      verticesBuffer[bufferIndex++] = colour.getBlue();

      // skip modifying colour's alpha or next star's x-pos
      bufferIndex += 2;
    }

    // draw the points in the vertices buffer
    vertices.setVertices(verticesBuffer, 0, numStars * COORDS_PER_VERTEX);
    vertices.bind();
    vertices.draw(GLES20.GL_POINTS, 0, numStars * VERTICES_PER_STAR);
    vertices.unbind();
  }
}
