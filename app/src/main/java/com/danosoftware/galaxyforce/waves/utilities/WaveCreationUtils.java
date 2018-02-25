package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.flightpath.paths.PathFactory;
import com.danosoftware.galaxyforce.flightpath.paths.Point;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sprites.game.factories.AlienFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.config.SubWavePathConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRuleProperties;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Wave creation utilities
 */
public final class WaveCreationUtils {

    private WaveCreationUtils() {
    }

    /**
     * Create a list of aliens that follow a path from
     * the supplied config and model.
     *
     * @param config - contains sub-wave configuration
     * @param model - reference to model
     * @return list of aliens
     */
    public static List<SpriteAlien> createPathAlienSubWave(
            final SubWavePathConfig config,
            final GameHandler model) {

        List<SpriteAlien> aliens = new ArrayList<>();

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
                    createAliens(alienType, path, props, model)
            );
        }

        return aliens;
    }

    /**
     * Create a list of aliens from the supplied config and model. These
     * aliens do not follow a normal pre-defined path.
     *
     * @param config - contains sub-wave configuration
     * @param model - reference to model
     * @return list of aliens
     */
    public static List<SpriteAlien> createNoPathAlienSubWave(
            final SubWaveNoPathConfig config,
            final GameHandler model) {

        List<SpriteAlien> aliens = new ArrayList<>();

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

        return aliens;
    }

    /**
     * adds a wanted number of aliens with a path. each alien is spaced by
     * the delay seconds specified.
     */
    private static List<SpriteAlien> createAliens(
            final AlienType alienType,
            final List<Point> path,
            final SubWavePathRuleProperties props,
            final GameHandler model)
    {

        List<SpriteAlien> aliensOnPath = new ArrayList<SpriteAlien>();

        for (int i = 0; i < props.getNumberOfAliens(); i++)
        {
            aliensOnPath.addAll(
                    AlienFactory.createAlien(
                            alienType,
                            path,
                            (i * props.getDelayBetweenAliens()) + props.getDelayOffet(),
                            model,
                            props.isRestartImmediately()
                    ));
        }

        return aliensOnPath;
    }
}
