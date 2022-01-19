package com.danosoftware.galaxyforce.view;

import static com.danosoftware.galaxyforce.waves.utilities.Randomiser.random;

import android.opengl.GLES20;
import com.danosoftware.galaxyforce.constants.GameConstants;

public class StarBatcher {

  private static final String TAG = "StarBatcher";

  private static final int POSITION_COORDS_PER_VERTEX = 2;  // x,y
  private static final int COLOUR_COORDS_PER_VERTEX = 0;   // r,g,b,a
  private static final int COORDS_PER_VERTEX =
      POSITION_COORDS_PER_VERTEX + COLOUR_COORDS_PER_VERTEX;
  private static final int VERTICES_PER_STAR = 1;

  private float[] verticesBuffer;
  private Vertices vertices;
  private int bufferIndex;
  private int numStars;

  /* number of stars to show */
  private static final int MAX_STARS = 250;

  public StarBatcher() {
    setUpVertices(MAX_STARS);
  }

  private void setUpVertices(int starsCount) {

    this.bufferIndex = 0;
    this.numStars = starsCount;

    // create an vertices buffer that holds star's position and colour per vertex
    this.verticesBuffer = new float[starsCount * (POSITION_COORDS_PER_VERTEX
        + COLOUR_COORDS_PER_VERTEX)];

    // create vertices object to hold all vertices
    this.vertices = new Vertices(
        starsCount * VERTICES_PER_STAR,
        0,
        false,
        false);

    int index = 0;
    for (int i = 0; i < starsCount; i++) {

      // position vertex - x,y position
      verticesBuffer[index++] = (float) (GameConstants.GAME_WIDTH * random());
      verticesBuffer[index++] = (float) (GameConstants.GAME_HEIGHT * random());

      // colour vertex - rgba colour
//      verticesBuffer[index++] = 1.0f; // red
//      verticesBuffer[index++] = 1.0f; // green
//      verticesBuffer[index++] = 1.0f; // blue
//      verticesBuffer[index++] = 1.0f; // alpha
    }
  }

  public void beginBatch() {

    //numStars = 0;

    // since each star's x-position and colour will never change,
    // we will only modify the y-position of our stars.
    // the first star's y-position is at index = 1
    bufferIndex = 1;
  }

  public void drawStar(float y) {

    // update y-position of star and increment index to next vertex
    verticesBuffer[bufferIndex += COORDS_PER_VERTEX] = y;

    //numStars++;
  }

  public void endBatch() {
    vertices.setVertices(verticesBuffer, 0, numStars * COORDS_PER_VERTEX);
    vertices.bind();
    vertices.draw(GLES20.GL_POINTS, 0, numStars * VERTICES_PER_STAR);
    vertices.unbind();
  }
}
