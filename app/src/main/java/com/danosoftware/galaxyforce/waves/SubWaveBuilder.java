package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.flightpath.paths.Path;
import com.danosoftware.galaxyforce.flightpath.translators.FlipXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.FlipYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;

import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_HEIGHT;
import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;

/**
 * Each sub-wave consists of one or more sub-wave properties. Each sub-wave
 * property has sprites, paths, x-invert, y-invert, x-Offset, y-Offset,
 * number of aliens, delay between each alien, delay offset and restart
 * immediately
 */
public enum SubWaveBuilder
{

    WAVE_01(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.VALLEY_DROP,
                    10,
                    0.5f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.VALLEY_DROP,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(100))
            )
    ),

    /**
     * space invader style attack
     */
    SPACE_INVADER(
            true,
            new SubWaveProperty(
                    AlienType.GOBBY,
                    Path.SPACE_INVADER,
                    20,
                    0.3f,
                    0,
                    false
            )
    ),

    /**
     * space invader attack in reverse (i.e. from bottom to top)
     */
    SPACE_INVADER_REVERSE(
            true,
            new SubWaveProperty(
                    AlienType.GOBBY,
                    Path.SPACE_INVADER,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    /**
     * figure of eight path
     */
    FIGURE_OF_EIGHT(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.FIGURE_OF_EIGHT,
                    20,
                    0.3f,
                    0,
                    false
            )
    ),

    /**
     * bell curve attack
     */
    BELL_CURVE(
            true,
            new SubWaveProperty(
                    AlienType.STORK,
                    Path.BELL_CURVE,
                    20,
                    0.3f,
                    0,
                    false
            )
    ),

    /**
     * bell curve attack from top and bottom
     */
    DOUBLE_BELL_CURVE(
            true,
            new SubWaveProperty(
                    AlienType.STORK,
                    Path.BELL_CURVE,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.STORK,
                    Path.BELL_CURVE,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    /**
     * twisted loop attack
     */
    LOOPER_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.DROID,
                    Path.LOOPER,
                    20,
                    0.3f,
                    0,
                    false
            )
    ),

    /**
     * multiple tear drop attacks
     */
    TEAR_DROP_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.INSECT,
                    Path.TEAR_DROP,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(-200))
            ),
            new SubWaveProperty(
                    AlienType.INSECT,
                    Path.TEAR_DROP,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.INSECT,
                    Path.TEAR_DROP,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(200))
            )
    ),

    /**
     * crossing aliens that attack from the top, cross over then leave at the
     * bottom
     */
    CROSSING_STEP_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BEZIER_STEP_UP,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BEZIER_STEP_UP,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * crossover attack where aliens drop from top, pause and then exit on
     * opposite side of screen
     */
    CROSSOVER_EXIT_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.EXIT_STAGE_RIGHT,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.EXIT_STAGE_RIGHT,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * staggered attack where 5 columns of aliens attack from top to bottom and
     * then bounce back up. each adjacent column is delayed so each column is
     * delayed compared to the previous one.
     */
    STAGGERED_BOUNCE_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40))
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + 92))
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 2)))
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    1.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 3)))
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    2f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 4)))
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BOUNCE_DOWN_AND_UP,
                    5,
                    0.3f,
                    2.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 5)))
            )
    ),

    /**
     * Dragon attack - only head needs to be created. body parts will be spawned
     * by the head.
     */
    DRAGON_ATTACK(
            true,
            new SubWaveProperty(
                    AlienType.DRAGON,
                    Path.TRIANGLULAR_PATH,
                    1,
                    0f,
                    0f,
                    false
            )
    ),

    WAVEY_LINE(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.SINGLE_ARC,
                    10,
                    0.5f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.SINGLE_ARC,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(-200))
            )
    ),

    WAVE_02(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BEZIER_DEMO,
                    10,
                    0.5f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.BEZIER_DEMO,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    WAVE_03(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.CIRCULAR_DEMO,
                    10,
                    0.5f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.CIRCULAR_DEMO,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * Drops from top-left down to valley and up again to top right. Alternates
     * alien types.
     */
    VALLEY_DIVE(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.VALLEY_DROP,
                    10,
                    1f,
                    0,
                    false
            ),
            new SubWaveProperty(
                    AlienType.MINION,
                    Path.VALLEY_DROP,
                    10,
                    1f,
                    0.5f,
                    false
            )
    ),

    WAVE_MOTHERSHIP(
            true,
            new SubWaveProperty(
                    AlienType.MOTHERSHIP,
                    Path.WAVEY_HORIZONTAL,
                    1,
                    0,
                    0,
                    true
            )
    ),

    /**
     * Triangular attack path
     */
    WAVE_TRIANGULAR(
            true,
            new SubWaveProperty(
                    AlienType.OCTOPUS,
                    Path.TRIANGLULAR_PATH,
                    10,
                    1f,
                    0f,
                    false
            )
    );

    /* references list of sub-waves */
    private final List<SubWaveProperty> waveList;

    /* repeat sub-wave until all destroyed */
    private final boolean repeatSubWave;

    /**
     * construct wave
     */
    SubWaveBuilder(boolean repeatSubWave, SubWaveProperty... waveArray)
    {
        this.waveList = Arrays.asList(waveArray);
        this.repeatSubWave = repeatSubWave;
    }

    public List<SubWaveProperty> getWaveList()
    {
        return waveList;
    }

    public boolean isRepeatSubWave()
    {
        return repeatSubWave;
    }
}
