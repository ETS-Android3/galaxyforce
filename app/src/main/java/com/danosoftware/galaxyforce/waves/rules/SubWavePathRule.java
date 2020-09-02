package com.danosoftware.galaxyforce.waves.rules;

import com.danosoftware.galaxyforce.flightpath.paths.Path;
import com.danosoftware.galaxyforce.flightpath.paths.PathSpeed;
import com.danosoftware.galaxyforce.flightpath.translators.FlipXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.FlipYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;
import com.danosoftware.galaxyforce.flightpath.translators.RotatePointTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_HEIGHT;
import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;
import static com.danosoftware.galaxyforce.constants.GameConstants.SCREEN_MID_X;

/**
 * Each sub-wave consists of one or more sub-wave properties.
 * <p>
 * Each sub-wave property contains enough data to create a sub-wave
 * of aliens that follow a path.
 */
public enum SubWavePathRule {

    /**
     * space invader style attack with 6 aliens in a batch.
     * A timing-delay gap is inserted between each batch.
     */
    SPACE_INVADER(
            waveWithGaps(
                    Path.SPACE_INVADER_EASY,
                    PathSpeed.NORMAL,
                    6,
                    0.3f,
                    5,
                    0.3f * 7,
                    null)
    ),

    /**
     * space invader style attack reverse (i.e. from bottom to top)
     * with 6 aliens in a batch.
     * A timing-delay gap is inserted between each batch.
     */
    SPACE_INVADER_REVERSE(
            waveWithGaps(
                    Path.SPACE_INVADER_EASY,
                    PathSpeed.NORMAL,
                    6,
                    0.3f,
                    5,
                    0.3f * 7,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    /**
     * figure of eight path
     */
    FIGURE_OF_EIGHT(
            new SubWavePathRuleProperties(
                    Path.FIGURE_OF_EIGHT,
                    PathSpeed.NORMAL,
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
            new SubWavePathRuleProperties(
                    Path.BELL_CURVE,
                    PathSpeed.NORMAL,
                    10,
                    0.3f,
                    0,
                    false
            )
    ),

    /**
     * bell curve attack from top and bottom
     */
    DOUBLE_BELL_CURVE(
            new SubWavePathRuleProperties(
                    Path.BELL_CURVE,
                    PathSpeed.NORMAL,
                    10,
                    0.3f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.BELL_CURVE,
                    PathSpeed.NORMAL,
                    15,
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
            new SubWavePathRuleProperties(
                    Path.LOOPER,
                    PathSpeed.VERY_SLOW,
                    10,
                    1.6f,
                    0,
                    false
            )
    ),

    LOOPER_ATTACK_REVERSE(
            new SubWavePathRuleProperties(
                    Path.LOOPER,
                    PathSpeed.VERY_SLOW,
                    10,
                    1.6f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * multiple tear drop attacks
     */
    TEAR_DROP_ATTACK(
            new SubWavePathRuleProperties(
                    Path.TEAR_DROP,
                    PathSpeed.NORMAL,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(-200))
            ),
            new SubWavePathRuleProperties(
                    Path.TEAR_DROP,
                    PathSpeed.NORMAL,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.TEAR_DROP,
                    PathSpeed.NORMAL,
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
            new SubWavePathRuleProperties(
                    Path.BEZIER_STEP_UP,
                    PathSpeed.NORMAL,
                    20,
                    0.3f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.BEZIER_STEP_UP,
                    PathSpeed.NORMAL,
                    20,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * spaced out crossover attack where aliens drop from top, pause and then exit on
     * opposite side of screen
     */
    CROSSOVER_EXIT_ATTACK_SPACED(
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    1.5f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    1.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),
    /**
     * spaced out crossover attack where aliens rise from bottom, pause and then exit on
     * opposite side of screen
     */
    CROSSOVER_EXIT_ATTACK_SPACED_REVERSE(
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    1.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            ),
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    1.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    /**
     * crossover attack where aliens drop from top, pause and then exit on
     * opposite side of screen
     */
    CROSSOVER_EXIT_ATTACK(
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    0.75f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    0.75f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    /**
     * crossover attack where aliens rise from bottom, pause and then exit on
     * opposite side of screen
     */
    CROSSOVER_EXIT_ATTACK_REVERSE(
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    0.75f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            ),
            new SubWavePathRuleProperties(
                    Path.EXIT_STAGE_RIGHT,
                    PathSpeed.NORMAL,
                    5,
                    0.75f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),


    /**
     * staggered attack where 5 columns of aliens attack from top to bottom and
     * then bounce back up. each adjacent column is delayed so each column is
     * delayed compared to the previous one.
     */
    STAGGERED_BOUNCE_ATTACK(
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40))
            ),
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + 92))
            ),
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 2)))
            ),
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    1.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 3)))
            ),
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    2f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 4)))
            ),
            new SubWavePathRuleProperties(
                    Path.BOUNCE_DOWN_AND_UP,
                    PathSpeed.NORMAL,
                    5,
                    0.3f,
                    2.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(40 + (92 * 5)))
            )
    ),

    WAVEY_LINE(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    10,
                    0.5f,
                    0,
                    false
            )
    ),

    WAVEY_LINE_REVERSE(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),

    // curves from top to bottom arcing to right
    WAVEY_LINE_BENDING_RIGHT(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    5,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new RotatePointTranslator(RotatePointTranslator.Rotation.CLOCKWISE))
            )
    ),
    // curves from top to bottom arcing to left
    WAVEY_LINE_BENDING_LEFT(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    5,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new RotatePointTranslator(RotatePointTranslator.Rotation.ANTI_CLOCKWISE))
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    WAVEY_LINE_REVERSE_LOWER(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(-200))
            )
    ),

    WAVEY_LINE_DOUBLE(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    10,
                    0.5f,
                    0,
                    false
            ),
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(-200))
            )
    ),

    /**
     * Drops from top-left down to valley and up again to top right.
     * Use interleaved version to interleave a different alien type.
     */
    VALLEY_DIVE(
            new SubWavePathRuleProperties(
                    Path.VALLEY_DROP,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0,
                    false
            )
    ),
    VALLEY_DIVE_INTERLEAVED(
            new SubWavePathRuleProperties(
                    Path.VALLEY_DROP,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0.5f,
                    false
            )
    ),

    VALLEY_DIVE_FLIPPED(
            new SubWavePathRuleProperties(
                    Path.VALLEY_DROP,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),
    VALLEY_DIVE_INTERLEAVED_FLIPPED(
            new SubWavePathRuleProperties(
                    Path.VALLEY_DROP,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipYPointTranslator(GAME_HEIGHT))
            )
    ),

    WAVE_MOTHERSHIP(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.NORMAL,
                    1,
                    0,
                    0,
                    true,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(125))
            )
    ),

    SINGLE_ARC(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.VERY_SLOW,
                    1,
                    0,
                    2.5f,
                    true,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(125))
            )
    ),

    SINGLE_ARC_NO_REPEAT(
            new SubWavePathRuleProperties(
                    Path.SINGLE_ARC,
                    PathSpeed.VERY_SLOW,
                    1,
                    0,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(125))
            )
    ),

    BOMBER_RUN(
            new SubWavePathRuleProperties(
                    Path.SPACE_INVADER,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0,
                    true
            )
    ),

    STAGGERED_LEFT_AND_RIGHT(
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 230))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 200))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 200))
            )
    ),

    STAGGERED_LEFT_AND_RIGHT_REVERSED(
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 230))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 200))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 200))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0.5f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 430))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 400))
            ),
            new SubWavePathRuleProperties(
                    Path.LEFT_AND_RIGHT,
                    PathSpeed.NORMAL,
                    1,
                    0f,
                    1f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(GAME_HEIGHT - 400))
            )
    ),


    SINGLE_SPIRAL(
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    1,
                    0f,
                    0,
                    false
            )
    ),

    /**
     * One spiral path from top to bottom
     */
    SPIRAL(
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    10,
                    0.5f,
                    0,
                    false
            )
    ),

    /**
     * Two side-by-side spiral path from top to bottom
     */
    DOUBLE_SPIRAL(
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    10,
                    0.5f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(SCREEN_MID_X / 2))
            ),
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(-SCREEN_MID_X / 2))
            )
    ),

    BOUNCING(
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new RotatePointTranslator(RotatePointTranslator.Rotation.CLOCKWISE))
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),
    BOUNCING_HIGHER(
            new SubWavePathRuleProperties(
                    Path.SPIRAL,
                    PathSpeed.FAST,
                    10,
                    1f,
                    0,
                    false,
                    new PointTranslatorChain()
                            .add(new RotatePointTranslator(RotatePointTranslator.Rotation.CLOCKWISE))
                            .add(new FlipXPointTranslator(GAME_WIDTH))
                            .add(new OffsetYPointTranslator(200))
            )
    ),

    /**
     * Triangular attack path
     */
    WAVE_TRIANGULAR(
            new SubWavePathRuleProperties(
                    Path.TRIANGULAR,
                    PathSpeed.FAST,
                    10,
                    0.25f,
                    0f,
                    false)
    ),
    WAVE_TRIANGULAR_REVERSED(
            new SubWavePathRuleProperties(
                    Path.TRIANGULAR,
                    PathSpeed.FAST,
                    10,
                    0.25f,
                    0f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH)))
    ),
    SLIDE_FORMATION_LEFT_RIGHT(
            new SubWavePathRuleProperties(
                    Path.SLIDE_LEFT,
                    PathSpeed.SLOW,
                    1,
                    0,
                    0.25f,
                    true,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            ),
            new SubWavePathRuleProperties(
                    Path.SLIDE_CENTRE,
                    PathSpeed.SLOW,
                    1,
                    0,
                    0f,
                    true,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            ),
            new SubWavePathRuleProperties(
                    Path.SLIDE_RIGHT,
                    PathSpeed.SLOW,
                    1,
                    0,
                    0.25f,
                    true,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH))
            )
    ),
    SLIDE_FORMATION_RIGHT_LEFT(
            new SubWavePathRuleProperties(
            Path.SLIDE_LEFT,
            PathSpeed.SLOW,
                    1,
                    0,
                    0.25f,
                    true
    ),
            new SubWavePathRuleProperties(
            Path.SLIDE_CENTRE,
            PathSpeed.SLOW,
                    1,
                    0,
                    0f,
                    true
    ),
            new SubWavePathRuleProperties(
            Path.SLIDE_RIGHT,
            PathSpeed.SLOW,
                    1,
                    0,
                    0.25f,
                    true
    )
    ),

    /**
     * Aliens follow diagonal path from top-left
     */
    DIAGONAL(
            new SubWavePathRuleProperties(
                    Path.DIAGONAL,
                    PathSpeed.NORMAL,
                    5,
                    0.25f,
                    0f,
                    false)
    ),

    /**
     * Aliens move diagonally from top-left to bottom-right
     * followed by top-right to bottom-left
     */
    DIAGONAL_CROSSOVER(
            new SubWavePathRuleProperties(
                    Path.DIAGONAL,
                    PathSpeed.NORMAL,
                    5,
                    0.25f,
                    0f,
                    false),
            new SubWavePathRuleProperties(
                    Path.DIAGONAL,
                    PathSpeed.NORMAL,
                    5,
                    0.25f,
                    1.25f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH)))
    ),

    /**
     * Aliens move diagonally from top-left to bottom-right
     * and from top-right to bottom-left.
     *
     * Paths are timed so that they just miss each other at the cross-over.
     */
    DIAGONAL_CROSSOVER_INTERLEAVED(
            new SubWavePathRuleProperties(
                    Path.DIAGONAL,
                    PathSpeed.NORMAL,
                    5,
                    0.5f,
                    0f,
                    false),
            new SubWavePathRuleProperties(
                    Path.DIAGONAL,
                    PathSpeed.NORMAL,
                    5,
                    0.5f,
                    0.25f,
                    false,
                    new PointTranslatorChain()
                            .add(new FlipXPointTranslator(GAME_WIDTH)))
    ),

    /**
     * Aliens follow square path around edge of screen
     */
    AROUND_EDGE(
            new SubWavePathRuleProperties(
                    Path.SQUARE,
                    PathSpeed.FAST,
                    10,
                    0.25f,
                    0f,
                    false)
    );

    // list of properties for a sub-wave
    private final List<SubWavePathRuleProperties> subWaveProps;

    SubWavePathRule(SubWavePathRuleProperties... subWaveProps) {
        this.subWaveProps = Arrays.asList(subWaveProps);
    }

    SubWavePathRule(List<SubWavePathRuleProperties> subWaveProps) {
        this.subWaveProps = subWaveProps;
    }

    /**
     * Properties to create a sub-wave
     */
    public List<SubWavePathRuleProperties> subWaveProps() {
        return subWaveProps;
    }

    /**
     * Combines batches of waves with a timing delay between each batch.
     * Allows a gap to be created between batches of aliens.
     */
    private static List<SubWavePathRuleProperties> waveWithGaps(
            final Path path,
            final PathSpeed speed,
            final int aliensInBatch,
            final float delayBetweenAliens,
            final int batches,
            final float delayBetweenBatches,
            final PointTranslatorChain translatorChain) {

        float batchDelay = 0f;

        List<SubWavePathRuleProperties> subWaves = new ArrayList<>();

        for (int batch = 0; batch < batches; batch++) {
            subWaves.add(
                    translatorChain != null
                            ? new SubWavePathRuleProperties(
                                path,
                                speed,
                                aliensInBatch,
                                delayBetweenAliens,
                                batchDelay,
                                false,
                                translatorChain)
                            : new SubWavePathRuleProperties(
                                    path,
                                    speed,
                                    aliensInBatch,
                                    delayBetweenAliens,
                                    batchDelay,
                                    false)
            );
            batchDelay += (delayBetweenAliens * aliensInBatch) + delayBetweenBatches;
        }

        return subWaves;
    }
}
