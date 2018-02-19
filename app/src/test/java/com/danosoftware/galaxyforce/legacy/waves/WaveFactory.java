package com.danosoftware.galaxyforce.legacy.waves;

import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.legacy.flightpath.PathFactory;
import com.danosoftware.galaxyforce.legacy.flightpath.Point;
import com.danosoftware.galaxyforce.sprites.game.factories.AlienFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.utilities.Reversed;
import com.danosoftware.galaxyforce.utilities.WaveUtilities;
import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.SubWave;
import com.danosoftware.galaxyforce.waves.SubWaveBuilderNoPath;
import com.danosoftware.galaxyforce.waves.SubWavePropertyNoPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Creates a wave of aliens based on the provided wave number. Each wave
 * property can contain multiple sub-waves, each consisting of a number of
 * aliens following a path
 */
public class WaveFactory
{
    // references width and height of screen
    private final int width;
    private final int height;
    private final GameHandler model;

    public WaveFactory(int width, int height, GameHandler model)
    {
        this.width = width;
        this.height = height;
        this.model = model;
    }

    /**
     * Return a collection of sub-waves based on the current wave number
     * 
     * @param wave
     *            - wave number
     * @return collection of sub-waves
     */
    public Collection<SubWave> createWave(int wave)
    {
        if (!WaveUtilities.isValidWave(wave))
        {
            throw new IllegalArgumentException("Wave not recognised '" + wave + "'.");
        }

        List<SubWave> subWaves = new ArrayList<SubWave>();

        switch (wave)
        {

        case 1:
            subWaves.add(createWave(SubWaveBuilder.WAVE_TRIANGULAR));
            subWaves.add(createWave(SubWaveBuilder.WAVE_03));
            subWaves.add(createWave(SubWaveBuilder.VALLEY_DIVE));
            break;

        case 2:
            subWaves.add(createWave(SubWaveBuilder.WAVE_03));
            subWaves.add(createWave(SubWaveBuilder.VALLEY_DIVE));
            subWaves.add(createWave(SubWaveBuilder.WAVE_02));
            break;

        case 3:
            subWaves.add(createWave(SubWaveBuilder.VALLEY_DIVE));
            break;

        case 4:
            subWaves.add(createWave(SubWaveBuilder.WAVE_02));
            break;

        case 5:
            subWaves.add(createWave(SubWaveBuilder.WAVE_MOTHERSHIP));
            subWaves.add(createWave(SubWaveBuilder.WAVE_MOTHERSHIP));
            break;

        case 6:
            subWaves.add(createWave(SubWaveBuilder.WAVEY_LINE));
            break;

        case 7:
            subWaves.add(createWave(SubWaveBuilderNoPath.ASTEROIDS));
            subWaves.add(createWave(SubWaveBuilderNoPath.ASTEROIDS_REVERSE));
            break;

        case 8:
            subWaves.add(createWave(SubWaveBuilder.SPACE_INVADER));
            subWaves.add(createWave(SubWaveBuilder.SPACE_INVADER_REVERSE));
            break;

        case 9:
            subWaves.add(createWave(SubWaveBuilder.STAGGERED_BOUNCE_ATTACK));
            break;

        case 10:
            subWaves.add(createWave(SubWaveBuilder.DRAGON_ATTACK));
            break;

        case 11:
            subWaves.add(createWave(SubWaveBuilder.FIGURE_OF_EIGHT));
            break;

        case 12:
            subWaves.add(createWave(SubWaveBuilder.CROSSING_STEP_ATTACK));
            break;

        case 13:
            subWaves.add(createWave(SubWaveBuilder.CROSSOVER_EXIT_ATTACK));
            break;

        case 14:
            subWaves.add(createWave(SubWaveBuilder.BELL_CURVE));
            subWaves.add(createWave(SubWaveBuilder.DOUBLE_BELL_CURVE));
            break;

        case 15:
            subWaves.add(createWave(SubWaveBuilder.LOOPER_ATTACK));
            break;

        case 16:
            subWaves.add(createWave(SubWaveBuilder.TEAR_DROP_ATTACK));
            break;

        case 17:
            subWaves.add(createWave(SubWaveBuilderNoPath.ASTEROID_FIELD));
            break;

        default:
            throw new IllegalArgumentException("Wave not recognised '" + wave + "'.");

        }

        return subWaves;
    }

    /**
     * Creates a list of aliens on a path using the supplied wave property.
     * 
     * @param waveProperty
     * @return list of alien sprites
     */
    private SubWave createWave(SubWaveBuilder waveProperty)
    {
        List<SpriteAlien> aliens = new ArrayList<SpriteAlien>();

        /* iterate through each wave in wave list and create/add aliens */
        for (SubWaveProperty wave : waveProperty.getWaveList())
        {

            // create current alien path
            List<Point> alienPath = PathFactory.createPath(wave.getPath(), wave.isxInvert(), wave.isyInvert(), wave.getxOffset(),
                    wave.getyOffset(), width, height);

            // create aliens using the current path
            aliens.addAll(addToPath(wave.getAlienType(), wave.getNumberOfAliens(), alienPath, wave.getDelayBetweenAliens(),
                    wave.getDelayOffet(), wave.isRestartImmediately()));
        }

        /*
         * Reverse order of aliens.
         * 
         * Collision detection routines are required to iterate through aliens
         * in reverse so aliens on top are hit first.
         * 
         * Any subsequent explosions on these aliens must also display on top so
         * reversed order is important for how aliens sprites are displayed.
         */
        List<SpriteAlien> reversedAlienList = revserseAliens(aliens);

        // create subwave from list of aliens and set whether wave should repeat
        // until all destroyed
        SubWave subWave = new SubWave(reversedAlienList, waveProperty.isRepeatSubWave());

        return subWave;
    }

    /**
     * adds a wanted number of aliens without a path. each alien is spaced by
     * the delay seconds specified.
     */
    private List<SpriteAlien> addToPath(AlienType alienType, int numberOfAliens, List<Point> alienPath, float delayBetweenAliens,
                                        float delayOffset, boolean restartImmediately)
    {

        List<SpriteAlien> aliensOnPath = new ArrayList<SpriteAlien>();

        for (int i = 0; i < numberOfAliens; i++)
        {
            // temporary commented out as no longer compiles
//            aliensOnPath.addAll(AlienFactory.createAlien(alienType, alienPath, (i * delayBetweenAliens) + delayOffset, model,
//                    restartImmediately));
        }

        return aliensOnPath;
    }

    /**
     * Creates a list of aliens using the supplied wave property.
     * 
     * @param waveProperty
     * @return list of alien sprites
     */
    private SubWave createWave(SubWaveBuilderNoPath waveProperty)
    {
        List<SpriteAlien> aliens = new ArrayList<SpriteAlien>();

        /* iterate through each wave in wave list and create/add aliens */
        for (SubWavePropertyNoPath wave : waveProperty.getWaveList())
        {

            for (int i = 0; i < wave.getNumberOfAliens(); i++)
            {
                aliens.addAll(AlienFactory.createAlien(wave.getAlienType(), wave.isxRandom(), wave.isyRandom(), wave.getxStart(),
                        wave.getyStart(), (i * wave.getDelayBetweenAliens()) + wave.getDelayOffet(), model, wave.isRestartImmediately(),
                        wave.getDirection()));
            }
        }

        /*
         * Reverse order of aliens.
         * 
         * Collision detection routines are required to iterate through aliens
         * in reverse so aliens on top are hit first.
         * 
         * Any subsequent explosions on these aliens must also display on top so
         * reversed order is important for how aliens sprites are displayed.
         */
        List<SpriteAlien> reversedAlienList = revserseAliens(aliens);

        // create subwave from list of aliens and set whether wave should repeat
        // until all destroyed
        SubWave subWave = new SubWave(reversedAlienList, waveProperty.isRepeatSubWave());

        return subWave;
    }

    /**
     * Reverse order of aliens.
     * 
     * Collision detection routines are required to iterate through aliens in
     * reverse so aliens on top are hit first.
     * 
     * Any subsequent explosions on these aliens must also display on top so
     * reversed order is important for how aliens sprites are displayed.
     */
    private List<SpriteAlien> revserseAliens(List<SpriteAlien> aliens)
    {
        List<SpriteAlien> reversedAlienList = new ArrayList<SpriteAlien>();

        for (SpriteAlien eachAlien : Reversed.reversed(aliens))
        {
            reversedAlienList.add(eachAlien);
        }

        return reversedAlienList;
    }
}
