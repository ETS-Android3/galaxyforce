package com.danosoftware.galaxyforce.waves.utilities;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.createAlienSubWaveProperty;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;
import java.util.ArrayList;
import java.util.List;

public class MazePatternCreator {

  /**
   * Maze Patterns
   * <p>
   * Each row is represented as a binary pattern. Each contains 7 blocks. 1 represents a maze block.
   * 0 represents an empty space.
   */

  public static final int[] MAZE_ONE = {
      0b1000001,
      0b1100011,
      0b1110111,
      0b1100011,
      0b1000001,
      0b0000000,
      0b0000000,
      0b0111111,
      0b0001000,
      0b0001000,
      0b0000000,
      0b1111100,
      0b1111110,
      0b1111100,
      0b1111000,
      0b1110000,
      0b1100000,
      0b1000001,
      0b1000011,
      0b1000111,
      0b1001111,
      0b1001111,
      0b0011111,
      0b0010000,
      0b0010000,
      0b0010010,
      0b0010010,
      0b0000010,
      0b0000010,
      0b1111110,
      0b1111100,
      0b1111000,
      0b1110001,
      0b1100010,
      0b1000100,
      0b0001000,
      0b0010000,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b1111001,
      0b0001001,
      0b0001001,
      0b0001001,
      0b1000001,
      0b1000001,
      0b0000000,
      0b0111111,
      0b0001000,
      0b0001000,
      0b0000000,
      0b1111100,
      0b1000000,
      0b1000000,
      0b1011111,
      0b1000001,
      0b1000001,
      0b1111001,
      0b0000001,
      0b0000001,
      0b0001111,
      0b0001111,
      0b0001111,
      0b0001111,
      0b0000001,
      0b0000001,
      0b0000001,
      0b1110000,
      0b1000000,
      0b1000000,
      0b1000000,
      0b1111110,
      0b1111100,
      0b1111000,
      0b1110000,
      0b1100000,
      0b1000001,
      0b1000010,
      0b1000100,
      0b1001000,
      0b1001000,
      0b0010000,
      0b0010000,
      0b1001000,
      0b1001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0000100,
      0b0000010,
      0b0000010,
      0b0000100,
      0b0001000,
      0b0010000,
      0b0100000,
      0b1000001,
      0b1000010,
      0b1000100,
      0b1001000,
      0b1001000,
      0b1000100,
      0b1100011,
      0b0010000,
      0b0001000,
      0b0000100,
      0b0000000,
      0b0000000,
      0b1110111,
      0b1100011,
      0b1000001,
  };
  public static final int[] MAZE_TWO = {
      0b1110111,
      0b0000000,
      0b0000000,
      0b0001000,
      0b0011100,
      0b0111110,
      0b0111110,
      0b0011100,
      0b0001000,
      0b0000000,
      0b0000000,
      0b1111100,
      0b1000000,
      0b1000000,
      0b1001111,
      0b1000001,
      0b1000001,
      0b1111001,
      0b0000000,
      0b0000000,
      0b0011111,
      0b0000001,
      0b0000001,
      0b1111001,
      0b1000001,
      0b1000001,
      0b1001111,
      0b0000000,
      0b0000000,
      0b1110111,
      0b1100011,
      0b1001001,
      0b0011100,
      0b0011100,
      0b1001001,
      0b1100011,
      0b1110111,
      0b0000000,
      0b0000000,
      0b1110111,
      0b1110111,
      0b0000000,
      0b0000000,
      0b1111110,
      0b1111100,
      0b1111000,
      0b1110000,
      0b1100000,
      0b1000001,
      0b1000011,
      0b1000111,
      0b1001111,
      0b1001111,
      0b0011111,
      0b0011111,
      0b1001111,
      0b1001111,
      0b1000111,
      0b1100011,
      0b1110001,
      0b1111000,
      0b1111100,
      0b1111100,
      0b1111100,
      0b1111001,
      0b1110001,
      0b1100011,
      0b1000011,
      0b1000111,
      0b1000111,
      0b1001111,
      0b1001111,
      0b1000111,
      0b1100011,
      0b1110000,
      0b1111100,
      0b0000000,
      0b0000000,
      0b1110111,
      0b1110111,
      0b0000000,
      0b0000000,
      0b1110111,
      0b1100011,
      0b1001001,
      0b0011100,
      0b0011100,
      0b1001001,
      0b1100011,
      0b1110111,
      0b0000000,
      0b0000000,
      0b0011111,
      0b0000001,
      0b0000001,
      0b1111001,
      0b1000001,
      0b1000001,
      0b1001111,
      0b0000000,
      0b0000000,
      0b1111100,
      0b1000000,
      0b1000000,
      0b1001111,
      0b1000001,
      0b1000001,
      0b1111001,
      0b0000000,
      0b0001000,
      0b0011100,
      0b0111110,
      0b0111110,
      0b0011100,
      0b0001000,
      0b0000000,
      0b1110111
  };
  public static final int[] MAZE_THREE = {
      0b1110111,
      0b0000000,
      0b0000000,
      0b0001000,
      0b0011100,
      0b0111110,
      0b0111110,
      0b0011100,
      0b0001000,
      0b0000000,
      0b0000000,
      0b1111100,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0011111,
      0b0000111,
      0b0000011,
      0b0000001,
      0b0000000,
      0b0000000,
      0b1111100,
      0b1110000,
      0b1100000,
      0b1000000,
      0b0000000
  };
  public static final int[] MAZE_THREE_ALIENS = {
      0b0001000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000011,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b1100000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000011,
      0b0000000,
      0b0000000,
      0b0000000,
      0b0000000
  };
  public static final int[] MAZE_FOUR = {
      0b1000001,
      0b1000001,
      0b0100010,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0100010,
      0b1000001,
      0b1110001,
      0b1111000,
      0b1111100,
      0b1111110,
      0b1111100,
      0b1111000,
      0b1110000,
      0b0000000,
      0b0011111,
      0b0111111,
      0b0011111,
      0b0001111,
      0b0000000,
      0b1000001,
      0b1000001,
      0b0100010,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0100010,
      0b1000001,
      0b1000001,
      0b0000000,
      0b0001000,
      0b0011100,
      0b0111110,
      0b0011100,
      0b0001000,
      0b0000000,
      0b1000001,
      0b1000001,
      0b0100010,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0010001,
      0b0001000,
      0b0010001,
      0b0100010,
      0b1000100,
      0b0001000,
      0b1000100,
      0b0100010,
      0b0100010,
      0b1000001,
      0b1000001,
  };
  // maze blocks options
  private static final int BLOCKS_PER_ROW = 7;
  private static final int BLOCK_SPRITE_WIDTH = 64;
  // bit mask (1 followed by six 0s).
  // used to check most-significant bit from a 7 bit number.
  private static final Short bitMask = 1 << 6;

  /**
   * Creates maze of multiple rows. Each row containing multiple blocks to navigate around.
   */
  public static List<SubWaveRuleProperties> createMaze(
      int[] maze) {

    List<SubWaveRuleProperties> subWaves = new ArrayList<>();

    // calculates min/max x positions for aliens per row
    final int minX = BLOCK_SPRITE_WIDTH / 2;
    final int maxX = GAME_WIDTH - (BLOCK_SPRITE_WIDTH / 2);
    final int distanceBetweenAliens = (maxX - minX) / (BLOCKS_PER_ROW - 1);

    final int mazeLength = maze.length;

    for (int row = 0; row < mazeLength; row++) {
      // grab bits representing next row.
      // iterate the maze array in reverse order
      // as the bottom of the maze will appear first.
      int bits = maze[mazeLength - 1 - row];

      for (int col = 0; col < 7; col++) {
        // check most significant bit.
        if ((bits & bitMask) != 0) {
          // insert a maze block
          final int xPos = minX + (col * distanceBetweenAliens);
          subWaves.add(
              createAlienSubWaveProperty(
                  xPos,
                  GameConstants.SCREEN_TOP + (row * distanceBetweenAliens),
                  false));
        }
        // shift left for next column
        bits = bits << 1;
      }
    }
    return subWaves;
  }

  /**
   * Doubles the length of a maze by repeating each row twice. Makes maze easier (and longer).
   */
  public static int[] mazeDoubler(int[] maze) {
    final int[] doubleMaze = new int[maze.length * 2];
    for (int row = 0; row < maze.length; row++) {
      doubleMaze[row * 2] = maze[row];
      doubleMaze[(row * 2) + 1] = maze[row];
    }
    return doubleMaze;
  }

  /**
   * Creates longer mazes by appending mazes together.
   */
  public static int[] mazeCombiner(int[]... mazes) {
    int[] maze = new int[0];
    for (int[] nextMaze : mazes) {
      maze = concatArrays(maze, nextMaze);
    }
    return maze;
  }

  private static int[] concatArrays(int[] src1, int[] src2) {
    int[] result = new int[src1.length + src2.length];
    System.arraycopy(src1, 0, result, 0, src1.length);
    System.arraycopy(src2, 0, result, src1.length, src2.length);
    return result;
  }
}
