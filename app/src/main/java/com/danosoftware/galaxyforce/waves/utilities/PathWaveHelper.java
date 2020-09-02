package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.flightpath.paths.Path;
import com.danosoftware.galaxyforce.flightpath.paths.PathSpeed;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetXPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.OffsetYPointTranslator;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWavePathConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRuleProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.SCREEN_MID_X;

public class PathWaveHelper {

    // gap to be left at each edge of an offset row
    private static final int HORIZONTAL_ROW_EDGE_GAP = 40;

    private static final int HORIZONTAL_SPACE_REMAINING =
            GameConstants.GAME_WIDTH - HORIZONTAL_ROW_EDGE_GAP - HORIZONTAL_ROW_EDGE_GAP;

    // space remaining from edge to centre
    private static final int SPACE_FROM_ROW_EDGE_TO_CENTRE =
            (GameConstants.GAME_WIDTH / 2) - HORIZONTAL_ROW_EDGE_GAP;

    // default gap between aliens in rows
    private static final int DEFAULT_ALIEN_GAP = 80;

    // multiplier to be used to calculate delays between aliens within a row
    private static final float HORIZONTAL_DELAY_MULTIPLIER = 0.4f;

    // multiplier to be used to calculate delays between rows
    private static final float VERTICAL_DELAY_MULTIPLIER = 0.3f;

    //
    // ******************************************
    // OFFSET SUB-WAVES
    // ******************************************
    //

    /**
     * Create a subwave of aliens that descend in rows.
     * Each alienRowConfig represents the aliens to be created for each row.
     *
     * The aliens are offset meaning they keep their positions in relation to each other.
     * The aliens starting position is calculated so that all aliens start off the top
     * of the screen edge.
     *
     * Aliens are always centred horiziontally. However, setting fillSpace allows
     * the aliens to also be spaced to fill the row space available.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param delayStart - delay time before subwave starts.
     * @param fillSpace - spread-out aliens to fill available horizontal space.
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createDescendingOffsetRowsSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final float delayStart,
            final boolean fillSpace) {

        // set yOffset so all aliens start off top-edge of screen
        final int rows = alienRowConfigs.length;
        final int yOffset = (rows - 1) * DEFAULT_ALIEN_GAP;

        int xGapBetweenAliens = calculateHorizontalGapBetweenAliens(fillSpace, aliensPerRow);

        // set xOffset so aliens rows are centered
        int halfSpaceOccupiedByAliens = ((aliensPerRow - 1) * xGapBetweenAliens) / 2;
        int spaceUnoccupiedFromEdgeToAliens = SPACE_FROM_ROW_EDGE_TO_CENTRE - halfSpaceOccupiedByAliens;
        int xOffset = HORIZONTAL_ROW_EDGE_GAP + spaceUnoccupiedFromEdgeToAliens;

        return createOffsetRowsSubWave(
                path,
                pathSpeed,
                alienRowConfigs,
                aliensPerRow,
                delayStart,
                xOffset,
                yOffset,
                xGapBetweenAliens,
                DEFAULT_ALIEN_GAP
        );
    }

    /**
     * Create a subwave of aliens rows that start from the left.
     * Each alienRowConfig represents the aliens to be created for each row.
     *
     * The aliens are offset meaning they keep their positions in relation to each other.
     * The aliens starting position is calculated so that all aliens start off the left
     * side of the screen edge.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param yOffset - offset y of all aliens in subwave (set position of row 1).
     * @param delayStart - delay time before subwave starts.
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createLeftToRightOffsetRowSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final int yOffset,
            final float delayStart) {

        // set xOffset so all aliens start off left edge of screen
        final int xOffset = -(aliensPerRow - 1) * DEFAULT_ALIEN_GAP;

        return createOffsetRowsSubWave(
                path,
                pathSpeed,
                alienRowConfigs,
                aliensPerRow,
                delayStart,
                xOffset,
                yOffset,
                DEFAULT_ALIEN_GAP,
                DEFAULT_ALIEN_GAP
        );
    }

    /**
     * Create a subwave of aliens in rows.
     * Each row is offset in the y-axis.
     * Within each row, each alien is offset in the x-axis by its column.
     * All aliens start their path at the same time.
     *
     * Offset Subwaves are best used with paths that return to their starting position.
     * This avoids aliens suddenly appearing at the start of a path or disappearing
     * at the end of a path.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param delayStart - delay before subwave starts
     * @param xOffset - x offset applied to each alien (column)
     * @param yOffset - y offset applied to each alien (row)
     * @param xGapBetweenAliens - gap between adjacent aliens on same row
     * @param yGapBetweenAliens - gap between adjacent alien rows
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createOffsetRowsSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final float delayStart,
            final int xOffset,
            final int yOffset,
            final int xGapBetweenAliens,
            final int yGapBetweenAliens) {

        SubWavePathConfig[] waveConfigs = new SubWavePathConfig[alienRowConfigs.length];

        for (int row = 0; row < alienRowConfigs.length; row++) {
            waveConfigs[row] = new SubWavePathConfig(
                    createRowWithOffsetAliens(
                            row,
                            aliensPerRow,
                            path,
                            pathSpeed,
                            delayStart,
                            xOffset,
                            yOffset,
                            xGapBetweenAliens,
                            yGapBetweenAliens),
                    alienRowConfigs[row].alienConfig,
                    alienRowConfigs[row].powerUps);
        }

        return waveConfigs;
    }

    //
    // ******************************************
    // DELAYED SUB-WAVES
    // ******************************************
    //

    /**
     * Create a subwave of aliens in rows.
     * Within each row, the aliens start at the same position but
     * start after a different delay time (determined by their column).
     *
     * Each row starts at the same time but offset in the y-axis.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param yOffset - y offset of row 1.
     * @param delayStart - delay before subwave starts (0f to start immediately).
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createLeftToRightDelayedRowSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final int yOffset,
            final float delayStart) {

        SubWavePathConfig[] waveConfigs = new SubWavePathConfig[alienRowConfigs.length];

        // adjust delay so we have a constant gap between aliens regardless of speed
        final float delayBetweenAliens = HORIZONTAL_DELAY_MULTIPLIER * pathSpeed.getMultiplier();

        for (int row = 0; row < alienRowConfigs.length; row++) {
            waveConfigs[row] = new SubWavePathConfig(
                    createRowWithTimeDelayedAliens(
                            row,
                            aliensPerRow,
                            delayBetweenAliens,
                            path,
                            pathSpeed,
                            delayStart,
                            yOffset),
                    alienRowConfigs[row].alienConfig,
                    alienRowConfigs[row].powerUps);
        }

        return waveConfigs;
    }

    /**
     * Create a subwave of aliens that descend from the top edge in rows.
     * Each alienRowConfig represents the aliens to be created for each row.
     *
     * The aliens are delayed meaning each row is delayed by a different amount so they
     * appear to follow each other.
     *
     * Aliens are always centred horiziontally. However, setting fillSpace allows
     * the aliens to also be spaced to fill the row space available.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param delayStart - delay before subwave starts (0f to start immediately).
     * @param fillSpace - spread-out aliens to fill available horizontal space.
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createDescendingDelayedRowSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final float delayStart,
            final boolean fillSpace) {

        int xGapBetweenAliens = calculateHorizontalGapBetweenAliens(fillSpace, aliensPerRow);

        // set xOffset so aliens rows are centered
        int halfSpaceOccupiedByAliens = ((aliensPerRow - 1) * xGapBetweenAliens) / 2;
        int spaceUnoccupiedFromEdgeToAliens = SPACE_FROM_ROW_EDGE_TO_CENTRE - halfSpaceOccupiedByAliens;
        int xOffset = HORIZONTAL_ROW_EDGE_GAP + spaceUnoccupiedFromEdgeToAliens;

        return createDelayedRowSubWave(
                path,
                pathSpeed,
                alienRowConfigs,
                aliensPerRow,
                xOffset,
                0,
                xGapBetweenAliens,
                0,
                delayStart);
    }

    /**
     * Create a subwave of aliens in rows.
     * Within each row, the aliens start at a different position (determined by their column)
     * and all start at the same time.
     *
     * Each row starts after a different delay.
     *
     * @param path - path aliens will follow.
     * @param pathSpeed - speed of aliens.
     * @param alienRowConfigs - array of alien row configs (1 config per row).
     * @param aliensPerRow - number of aliens in each row.
     * @param xOffset - offset x of all aliens in subwave.
     * @param yOffset - offset y of all aliens in subwave (set position of row 1).
     * @param xGapBetweenAliens - space between adjacent aliens in same row.
     * @param yGapBetweenAliens - space between adjacent rows.
     * @param delayStart - delay before subwave starts (0f to start immediately).
     * @return array of subwave configs (1 per row).
     */
    public static SubWaveConfig[] createDelayedRowSubWave(
            final Path path,
            final PathSpeed pathSpeed,
            final AlienRowConfig[] alienRowConfigs,
            final int aliensPerRow,
            final int xOffset,
            final int yOffset,
            final int xGapBetweenAliens,
            final int yGapBetweenAliens,
            final float delayStart) {

        SubWavePathConfig[] waveConfigs = new SubWavePathConfig[alienRowConfigs.length];

        // adjust delay so we have a constant gap between aliens regardless of speed
        final float delayBetweenAliens = VERTICAL_DELAY_MULTIPLIER * pathSpeed.getMultiplier();
        float rowStartDelay = delayStart;

        for (int row = 0; row < alienRowConfigs.length; row++) {
            waveConfigs[row] = new SubWavePathConfig(
                    createRowWithOffsetAliens(
                            row,
                            aliensPerRow,
                            path,
                            pathSpeed,
                            rowStartDelay,
                            xOffset,
                            yOffset,
                            xGapBetweenAliens,
                            yGapBetweenAliens
                            ),
                    alienRowConfigs[row].alienConfig,
                    alienRowConfigs[row].powerUps);

            rowStartDelay += delayBetweenAliens;
        }

        return waveConfigs;
    }

    /**
     * Create a row of aliens, each starting after a different time-delay.
     */
    private static List<SubWavePathRuleProperties> createRowWithTimeDelayedAliens(
            final int row,
            final int aliensPerRow,
            final float delayBetweenAliens,
            final Path path,
            final PathSpeed speed,
            final float delayStart,
            final int yOffset) {
        List<SubWavePathRuleProperties> subWaves = new ArrayList<>();
        
        subWaves.add(new SubWavePathRuleProperties(
                path,
                speed,
                aliensPerRow,
                delayBetweenAliens,
                delayStart,
                false,
                new PointTranslatorChain()
                        .add(new OffsetYPointTranslator(yOffset - (row * DEFAULT_ALIEN_GAP)))
        ));

        return subWaves;
    }

    //
    // ******************************************
    // COMMON HELPERS
    // ******************************************
    //

    private static int calculateHorizontalGapBetweenAliens(
            boolean fillSpace,
            int aliensPerRow) {

        if (fillSpace) {
            return aliensPerRow > 1
                    ? HORIZONTAL_SPACE_REMAINING / (aliensPerRow - 1)
                    : DEFAULT_ALIEN_GAP;
        }

        return DEFAULT_ALIEN_GAP;
    }

    /**
     * Create new row with aliens in a different position.
     */
    private static List<SubWavePathRuleProperties> createRowWithOffsetAliens(
            final int row,
            final int aliensPerRow,
            final Path path,
            final PathSpeed speed,
            final float delayStart,
            final int xOffset,
            final int yOffset,
            final int xGapBetweenAliens,
            final int yGapBetweenAliens) {
        List<SubWavePathRuleProperties> subWaves = new ArrayList<>();

        for (int col = 0; col < aliensPerRow; col++) {
            subWaves.add(new SubWavePathRuleProperties(
                    path,
                    speed,
                    1,
                    0f,
                    delayStart,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(xOffset + (col * xGapBetweenAliens)))
                            .add(new OffsetYPointTranslator(yOffset - (row * yGapBetweenAliens)))
            ));
        }

        return subWaves;
    }

    //
    // ******************************************
    // CUSTOM PATTERNS
    // ******************************************
    //

    /**
     * Create a staggered row.
     * One center alien dropping with two adjacent aliens slightly behind.
     */
    public static List<SubWavePathRuleProperties> createStaggeredDroppers(
            final Path path,
            final PathSpeed speed,
            final float delayStart) {

        float delayMultiplier = speed.getMultiplier();
        float staggeredDelayStart = delayMultiplier * 0.25f;

        return Arrays.asList(
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        1,
                        0f,
                        delayStart + staggeredDelayStart,
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X - (SCREEN_MID_X / 2)))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        1,
                        0f,
                        delayStart,
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        1,
                        0f,
                        delayStart + staggeredDelayStart,
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X + (SCREEN_MID_X / 2)))
                )
        );
    }

    /**
     * Create a diamond pattern of descending aliens.
     */
    public static List<SubWavePathRuleProperties> createDiamondDroppers(
            final Path path,
            final PathSpeed speed,
            final float delayStart) {

        float delayMultiplier = speed.getMultiplier();

        return Arrays.asList(
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        1,
                        0f,
                        delayStart + (delayMultiplier * 0.5f),
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X - (2 * (SCREEN_MID_X / 3))))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        2,
                        0.5f * delayMultiplier,
                        delayStart + (delayMultiplier * 0.25f),
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X - (SCREEN_MID_X / 3)))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        2,
                        1f * delayMultiplier,
                        delayStart,
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        2,
                        0.5f * delayMultiplier,
                        delayStart + (delayMultiplier * 0.25f),
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X + (SCREEN_MID_X / 3)))
                ),
                new SubWavePathRuleProperties(
                        path,
                        speed,
                        1,
                        0f,
                        delayStart + (delayMultiplier * 0.5f),
                        false,
                        new PointTranslatorChain()
                                .add(new OffsetXPointTranslator(SCREEN_MID_X + (2 * (SCREEN_MID_X / 3))))
                )
        );
    }

    /**
     * Creates an attack of 6 aliens in a row
     */
    public static List<SubWavePathRuleProperties> createRowDroppers(
            final Path path,
            final PathSpeed speed,
            final float delayStart) {

        List<SubWavePathRuleProperties> subWaves = new ArrayList<>();
        int gapBetweenAliens = HORIZONTAL_SPACE_REMAINING / 6;

        for (int col = 0; col < 6; col++) {
            subWaves.add(new SubWavePathRuleProperties(
                    path,
                    speed,
                    1,
                    0f,
                    delayStart,
                    false,
                    new PointTranslatorChain()
                            .add(new OffsetXPointTranslator(HORIZONTAL_ROW_EDGE_GAP + (col * gapBetweenAliens)))
            ));
        }

        return subWaves;
    }
}
