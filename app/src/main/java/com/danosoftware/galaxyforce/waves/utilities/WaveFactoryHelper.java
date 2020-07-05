package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WaveFactoryHelper {

    public static final List<PowerUpType> NO_POWER_UPS = Collections.emptyList();
    public static final float HALF_PI = (float)(Math.PI/2f);
    public static final float DOWNWARDS = -HALF_PI;
    public static final float PI = (float)(Math.PI);
    public static final float QUARTER_PI = (float)(Math.PI/4f);

    /*
     * Flatten an array of sub-wave configs into a single array
     */
    public static SubWaveConfig[] flatten(SubWaveConfig[]... configs) {
        List<SubWaveConfig> list = new ArrayList<>();
        for (SubWaveConfig[] array: configs) {
            list.addAll(Arrays.asList(array));
        }
        SubWaveConfig[] itemsArray = new SubWaveConfig[list.size()];
        return list.toArray(itemsArray);
    }

    /**
     * Create an subwave property for alien at the wanted position
     */
    public static SubWaveRuleProperties createAlienSubWaveProperty(
            final int row,
            final int xPos,
            final int delayBetweenRows) {
        return new SubWaveRuleProperties(
                false,
                false,
                xPos,
                GameConstants.SCREEN_TOP,
                1,
                0,
                row * delayBetweenRows,
                false);
    }
}
