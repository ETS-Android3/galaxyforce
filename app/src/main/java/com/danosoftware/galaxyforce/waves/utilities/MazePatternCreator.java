package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.SpawningExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnOnDemandConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningBySpeedConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalDestroyableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.SplitterConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;
import static com.danosoftware.galaxyforce.waves.utilities.AlienConfigBuilder.directionalAlienConfig;
import static com.danosoftware.galaxyforce.waves.utilities.WaveAsteroidsHelper.createMiniDirectionalAsteroid;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.QUARTER_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.createAlienSubWaveProperty;

public class MazePatternCreator {

    // maze blocks options
    private static final int BLOCKS_PER_ROW = 7;
    private static final int BLOCK_SPRITE_WIDTH = 64;

    // bit mask (1 followed by six 0s).
    // used to check most-significant bit from a 7 bit number.
    private static final Short bitMask = 1 << 6;

    /**
     * Maze Patterns
     * <p>
     * Each row is represented as a binary pattern.
     * Each contains 7 blocks.
     * 1 represents a maze block.
     * 0 represents an empty space.
     */

    private static final int[] MAZE_ONE = {
            0b1110111,
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
            0b0010100,
            0b0010100,
            0b0000100,
            0b1111100,
            0b1111001,
            0b1110010,
            0b1100100,
            0b1001000,
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
            0b1110111
    };

    private static final int[] MAZE_TWO = {
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
            0b1000011,
            0b1000001,
            0b0000000,
            0b0000000,
            0b1111100,
            0b1110000,
            0b1100001,
            0b1000001,
            0b0000000
    };

    public static final int[] MAZE_FOUR = {
            0b1000001,
            0b1000001,
            0b0100010,
            0b0100010,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0001000,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0001000,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0100010,
            0b1000001,
            0b1000001,
            0b0000000,
            0b0001000,
            0b0011100,
            0b0001000,
            0b0000000,
            0b1000001,
            0b1000001,
            0b0100010,
            0b0100010,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0001000,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0010001,
            0b0001000,
            0b0010001,
            0b0100010,
            0b1000100,
            0b0100010,
            0b0100010,
            0b1000001,
            0b1000001,
    };

    /**
     * Create descending rows of blocks in a maze pattern
     */
    public static SubWaveConfig[] mazePatternOne(
            final AlienSpeed speed,
            final List<PowerUpType> powerUps) {

          return new SubWaveConfig[]{
                new SubWaveNoPathConfig(
                        createMaze(
                                MAZE_ONE,
                                BLOCKS_PER_ROW,
                                BLOCK_SPRITE_WIDTH),
                        directionalAlienConfig(
                                AlienCharacter.BLOCK,
                                DOWNWARDS,
                                speed),
                        powerUps)
        };
    }

    /**
     * Create descending rows of blocks in a maze pattern
     */
    public static SubWaveConfig[] mazePatternTwo(
            final AlienSpeed speed,
            final List<PowerUpType> powerUps) {

        return new SubWaveConfig[]{
                new SubWaveNoPathConfig(
                        createMaze(
                                MAZE_TWO,
                                BLOCKS_PER_ROW,
                                BLOCK_SPRITE_WIDTH),
                        directionalAlienConfig(
                                AlienCharacter.BLOCK,
                                DOWNWARDS,
                                speed),
                        powerUps)
        };
    }

    /**
     * Create descending rows of blocks in a maze pattern
     */
    public static SubWaveConfig[] mazePatternThree(
            final AlienSpeed speed,
            final List<PowerUpType> powerUps) {

        return new SubWaveConfig[]{
                new SubWaveNoPathConfig(
                        createMaze(
                                MAZE_THREE,
                                BLOCKS_PER_ROW,
                                BLOCK_SPRITE_WIDTH),
                        DirectionalDestroyableConfig
                                .builder()
                                .alienCharacter(AlienCharacter.ASTEROID)
                                .energy(2)
                                .speed(speed)
                                .angle(DOWNWARDS)
                                .spinningConfig(
                                        SpinningBySpeedConfig
                                                .builder()
                                                .build())
                                .explosionConfig(
                                        SpawningExplosionConfig
                                                .builder()
                                                .spawnConfig(
                                                        SpawnOnDemandConfig
                                                                .builder()
                                                                .spawnedPowerUpTypes(
                                                                        NO_POWER_UPS)
                                                                .spawnedAlienConfig(SplitterConfig
                                                                        .builder()
                                                                        .alienConfigs(
                                                                                Arrays.asList(
                                                                                        createMiniDirectionalAsteroid(-HALF_PI - QUARTER_PI, AlienSpeed.MEDIUM),
                                                                                        createMiniDirectionalAsteroid(-HALF_PI + QUARTER_PI, AlienSpeed.MEDIUM)))
                                                                        .build())
                                                                .build())
                                                .build()
                                )
                                .build(),
                        powerUps)
        };
    }

    /**
     * Create descending rows of blocks in a maze pattern
     */
    public static SubWaveConfig[] mazePatternFour(
            final AlienSpeed speed,
            final List<PowerUpType> powerUps) {

        return new SubWaveConfig[]{
                new SubWaveNoPathConfig(
                        createMaze(
                                MAZE_FOUR,
                                BLOCKS_PER_ROW,
                                BLOCK_SPRITE_WIDTH),
                        directionalAlienConfig(
                                AlienCharacter.BLOCK,
                                DOWNWARDS,
                                speed),
                        powerUps)
        };
    }

    /**
     * Creates maze of multiple rows.
     * Each row containing multiple blocks to navigate around.
     */
    private static List<SubWaveRuleProperties> createMaze(
            int[] maze,
            int columnsPerRow,
            int alienWidth) {

        List<SubWaveRuleProperties> subWaves = new ArrayList<>();

        // calculates min/max x positions for aliens per row
        final int minX = alienWidth / 2;
        final int maxX = GAME_WIDTH - (alienWidth / 2);
        final int distanceBetweenAliens = (maxX - minX) / (columnsPerRow - 1);

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
                    subWaves.add(createAlienSubWaveProperty(
                            xPos,
                            GameConstants.SCREEN_TOP + (row * distanceBetweenAliens)));
                }
                // shift left for next column
                bits = bits << 1;
            }
        }
        return subWaves;
    }
}
