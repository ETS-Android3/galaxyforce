package com.danosoftware.galaxyforce.waves.rules;

import com.danosoftware.galaxyforce.constants.GameConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Each sub-wave consists of one or more sub-wave properties.
 * <p>
 * Each sub-wave property contains enough data to create a sub-wave
 * of aliens that follow some rules.
 */
public enum SubWaveRule {

    /**
     * Asteroids that fall from top to bottom at random x positions and random
     * speeds
     */
    ASTEROIDS(
            new SubWaveRuleProperties(
                    true,
                    false,
                    0,
                    GameConstants.SCREEN_TOP,
                    30,
                    0.5f,
                    0f,
                    false
            )),

    /**
     * Aliens that start at different positions at the edges of the screen.
     */
    MIDDLE_TOP(
            new SubWaveRuleProperties(
                    false,
                    false,
                    GameConstants.SCREEN_MID_X,
                    GameConstants.SCREEN_TOP,
                    1,
                    0f,
                    0f,
                    false
            )),
    MIDDLE_BOTTOM(
            new SubWaveRuleProperties(
                    false,
                    false,
                    GameConstants.SCREEN_MID_X,
                    GameConstants.SCREEN_BOTTOM,
                    1,
                    0f,
                    0f,
                    false
            )),
    MIDDLE_LEFT(
            new SubWaveRuleProperties(
                    false,
                    false,
                    GameConstants.SCREEN_LEFT,
                    GameConstants.SCREEN_MID_Y,
                    1,
                    0f,
                    0f,
                    false
            )),
    MIDDLE_RIGHT(
            new SubWaveRuleProperties(
                    false,
                    false,
                    GameConstants.SCREEN_RIGHT,
                    GameConstants.SCREEN_MID_Y,
                    1,
                    0f,
                    0f,
                    false
            )),

    /**
     * Alien that starts at random x position at top of screen.
     */
    RANDOM_TOP(
            new SubWaveRuleProperties(
                    true,
                    false,
                    0,
                    GameConstants.SCREEN_TOP,
                    1,
                    0f,
                    0f,
                    false
            )
    );


    // list of properties for a sub-wave
    private final List<SubWaveRuleProperties> waveList;

    SubWaveRule(SubWaveRuleProperties... waveArray) {
        this.waveList = Arrays.asList(waveArray);
    }

    SubWaveRule(List<SubWaveRuleProperties> waveList) {
        this.waveList = waveList;
    }

    /**
     * Properties to create a sub-wave
     */
    public List<SubWaveRuleProperties> subWaveProps() {
        return waveList;
    }
}
