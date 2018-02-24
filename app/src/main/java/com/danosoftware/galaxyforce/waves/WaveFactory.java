package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.flightpath.paths.PathFactory;
import com.danosoftware.galaxyforce.flightpath.paths.Point;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.factories.AlienFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.utilities.Reversed;
import com.danosoftware.galaxyforce.utilities.WaveUtilities;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWavePathConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveRepeatMode;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRuleProperties;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Creates a wave of aliens based on the provided wave number. Each wave
 * property can contain multiple sub-waves, each consisting of a number of
 * aliens following a path
 */
public class WaveFactory
{
    private final GameHandler model;

    public WaveFactory(GameHandler model)
    {
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
            throw new GalaxyForceException("Wave not recognised '" + wave + "'.");
        }

        List<SubWave> subWaves = new ArrayList<>();

        switch (wave)
        {

            case 1:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_03,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE,
                                        AlienType.OCTOPUS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_INTERLEAVED,
                                        AlienType.MINION
                                )
                        )
                );
                break;

            case 2:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_03,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE,
                                        AlienType.OCTOPUS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_INTERLEAVED,
                                        AlienType.MINION
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_02,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 3:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE,
                                        AlienType.OCTOPUS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_INTERLEAVED,
                                        AlienType.MINION
                                )
                        )
                );
                break;

            case 4:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_02,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 5:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        AlienType.MOTHERSHIP
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        AlienType.MOTHERSHIP
                                )
                        )
                );
                break;

            case 6:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 7:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveConfig(
                                        SubWaveRule.ASTEROIDS,
                                        AlienType.ASTEROID
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveConfig(
                                        SubWaveRule.ASTEROIDS_REVERSE,
                                        AlienType.ASTEROID
                                )
                        )
                );
                break;

            case 8:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPACE_INVADER,
                                        AlienType.GOBBY
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPACE_INVADER_REVERSE,
                                        AlienType.GOBBY
                                )
                        )
                );
                break;

            case 9:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_BOUNCE_ATTACK,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 10:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DRAGON_ATTACK,
                                        AlienType.DRAGON
                                )
                        )
                );
                break;

            case 11:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.FIGURE_OF_EIGHT,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 12:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSING_STEP_ATTACK,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 13:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSOVER_EXIT_ATTACK,
                                        AlienType.OCTOPUS
                                )
                        )
                );
                break;

            case 14:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BELL_CURVE,
                                        AlienType.STORK
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DOUBLE_BELL_CURVE,
                                        AlienType.STORK
                                )
                        )
                );
                break;

            case 15:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.LOOPER_ATTACK,
                                        AlienType.DROID
                                )
                        )
                );
                break;

            case 16:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.TEAR_DROP_ATTACK,
                                        AlienType.INSECT
                                )
                        )
                );
                break;

            case 17:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveConfig(
                                        SubWaveRule.ASTEROID_FIELD,
                                        AlienType.ASTEROID_SIMPLE
                                )
                        )
                );
                break;

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
                // placeholders until real waves are created

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveConfig(
                                        SubWaveRule.ASTEROID_FIELD,
                                        AlienType.ASTEROID_SIMPLE
                                )
                        )
                );
                break;

            default:
                throw new IllegalArgumentException("Wave not recognised '" + wave + "'.");

        }

        return subWaves;
    }

    /**
     * Creates a list of aliens on a path using the supplied wave property.
     *
     * @param repeatedMode
     * @param subWaveConfigs
     * @return list of alien sprites
     */
    private SubWave createSubWave(
            final SubWaveRepeatMode repeatedMode,
            final SubWavePathConfig... subWaveConfigs) {

        List<SpriteAlien> aliens = new ArrayList<>();

        for (SubWavePathConfig config : subWaveConfigs) {
            final AlienType alienType = config.getAlien();
            final SubWavePathRule rules = config.getSubWaveRule();

            for (SubWavePathRuleProperties props : rules.subWaveProps()) {

                // create path points (that alien will follow) for sub-wave
                List<Point> path = PathFactory.createPath(
                        props.getPath(),
                        props.getTranslators()
                );

                // create and add a sub-wave of aliens according to provided properties
                aliens.addAll(
                        createAliens(alienType, path, props)
                );
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
        List<SpriteAlien> reversedAlienList = reverseAliens(aliens);

        // create subwave from list of aliens and set whether wave should repeat
        // until all destroyed
        SubWave subWave = new SubWave(reversedAlienList, repeatedMode);

        return subWave;

        }

        /**
         * adds a wanted number of aliens without a path. each alien is spaced by
         * the delay seconds specified.
         */
        private List<SpriteAlien> createAliens(
                AlienType alienType,
                List<Point> path,
                SubWavePathRuleProperties props)
        {

            List<SpriteAlien> aliensOnPath = new ArrayList<SpriteAlien>();

            for (int i = 0; i < props.getNumberOfAliens(); i++)
            {
                aliensOnPath.addAll(AlienFactory.createAlien(alienType, path, (i * props.getDelayBetweenAliens()) + props.getDelayOffet(), model,
                        props.isRestartImmediately()));
            }

            return aliensOnPath;
        }

    /**
     * adds a wanted number of aliens without a path. each alien is spaced by
     * the delay seconds specified.
     */
    private List<SpriteAlien> createAliens(
            AlienType alienType,
            int numberOfAliens,
            List<Point> alienPath,
            float delayBetweenAliens,
            float delayOffset,
            boolean restartImmediately)
    {

        List<SpriteAlien> aliensOnPath = new ArrayList<SpriteAlien>();

        for (int i = 0; i < numberOfAliens; i++)
        {
            aliensOnPath.addAll(AlienFactory.createAlien(alienType, alienPath, (i * delayBetweenAliens) + delayOffset, model,
                    restartImmediately));
        }

        return aliensOnPath;
    }

    /**
     * Creates a list of aliens using the supplied wave property.
     * 
     * @param waveProperty
     * @return list of alien sprites
     */
    private SubWave createSubWave(final SubWaveRepeatMode repeatedMode,
                                  final SubWaveConfig... subWaveConfigs)

            //SubWaveRule waveProperty)
    {
        List<SpriteAlien> aliens = new ArrayList<SpriteAlien>();


        for (SubWaveConfig config : subWaveConfigs) {

            final AlienType alienType = config.getAlien();
            final SubWaveRule rules = config.getSubWaveRule();

            for (SubWaveRuleProperties props : rules.subWaveProps()) {
                for (int i = 0; i < props.getNumberOfAliens(); i++) {
                    aliens.addAll(AlienFactory.createAlien(
                            alienType,
                            props.isxRandom(),
                            props.isyRandom(),
                            props.getxStart(),
                            props.getyStart(),
                            (i * props.getDelayBetweenAliens()) + props.getDelayOffet(),
                            model,
                            props.isRestartImmediately(),
                            props.getDirection()));
                }
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
        List<SpriteAlien> reversedAlienList = reverseAliens(aliens);

        // create subwave from list of aliens and set whether wave should repeat
        // until all destroyed
        SubWave subWave = new SubWave(reversedAlienList, repeatedMode);

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
    private List<SpriteAlien> reverseAliens(List<SpriteAlien> aliens)
    {
        List<SpriteAlien> reversedAlienList = new ArrayList<SpriteAlien>();

        for (SpriteAlien eachAlien : Reversed.reversed(aliens))
        {
            reversedAlienList.add(eachAlien);
        }

        return reversedAlienList;
    }
}
