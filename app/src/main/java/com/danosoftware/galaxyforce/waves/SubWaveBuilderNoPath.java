package com.danosoftware.galaxyforce.waves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;

/**
 * Each sub-wave consists of one or more sub-wave properties. Each sub-wave
 * property has sprites, x-start random, y-start random, x-start position,
 * y-start position, number of aliens, delay between each alien, delay offset
 * and restart immediately
 */
public enum SubWaveBuilderNoPath
{

    /**
     * Asteroids that fall from top to bottom at random x positions and random
     * speeds
     */
    ASTEROIDS(true, new SubWavePropertyNoPath(AlienType.ASTEROID, true, false, 0, GameConstants.SCREEN_TOP, 30, 0.5f, 0f, false,
            Direction.DOWN)),

    /**
     * Asteroids that fall from bottom to top at random x positions and random
     * speeds
     */
    ASTEROIDS_REVERSE(true, new SubWavePropertyNoPath(AlienType.ASTEROID, true, false, 0, GameConstants.SCREEN_BOTTOM, 30, 0.5f, 0f, false,
            Direction.UP)),

    /**
     * Asteroids that form a path through an asteroid field.
     */
    ASTEROID_FIELD(true, asteroidFieldSubWave());

    /* references list of sub-waves */
    private List<SubWavePropertyNoPath> waveList;

    /* repeat sub-wave until all destroyed */
    private boolean repeatSubWave;

    /**
     * construct wave
     */
    SubWaveBuilderNoPath(boolean repeatSubWave, SubWavePropertyNoPath... waveArray)
    {
        this.waveList = Arrays.asList(waveArray);
        this.repeatSubWave = repeatSubWave;
    }

    public List<SubWavePropertyNoPath> getWaveList()
    {
        return waveList;
    }

    public boolean isRepeatSubWave()
    {
        return repeatSubWave;
    }

    /*
     * ******************************************
     * HELPER METHODS TO BUILD SPECIFIC SUB-WAVES
     * ******************************************
     */

    private static SubWavePropertyNoPath[] asteroidFieldSubWave()
    {
        List<SubWavePropertyNoPath> subWaves = new ArrayList<SubWavePropertyNoPath>();

        // distance between asteroids on same row. 6 asteroids
        // per row so 5 gaps between asteroids.
        int distBetweenAsteroid = (GameConstants.SCREEN_RIGHT_EDGE - GameConstants.SCREEN_LEFT_EDGE) / 5;

        for (int row = 0; row < 10; row++)
        {
            for (int col = 0; col < 6; col++)
            {
                subWaves.add(new SubWavePropertyNoPath(AlienType.ASTEROID_SIMPLE, false, false, GameConstants.SCREEN_LEFT_EDGE
                        + (col * distBetweenAsteroid), GameConstants.SCREEN_TOP, 1, 0, row * 2, false, Direction.DOWN));
            }
        }

        return toArray(subWaves);
    }

    /**
     * Converts a list of subwaves to an array of subwaves.
     * 
     * @param subWavesList
     * @return
     */
    private static SubWavePropertyNoPath[] toArray(List<SubWavePropertyNoPath> subWavesList)
    {
        SubWavePropertyNoPath[] subWavesArray = new SubWavePropertyNoPath[subWavesList.size()];

        int i = 0;
        for (SubWavePropertyNoPath subWave : subWavesList)
        {
            subWavesArray[i] = subWave;
            i++;
        }

        return subWavesArray;
    }
}
