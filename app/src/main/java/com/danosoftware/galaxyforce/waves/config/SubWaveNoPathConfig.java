package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

/**
 * Describes a sub-wave consisting of a set of rules and an alien type
 */
public class SubWaveNoPathConfig extends SubWaveConfig {

    private final SubWaveRule subWaveRule;
    private final AlienType alien;

    public SubWaveNoPathConfig(
            final SubWaveRule subWaveRule,
            final AlienType alien) {

        super(Type.NO_PATH);
        this.subWaveRule = subWaveRule;
        this.alien = alien;
    }

    public SubWaveRule getSubWaveRule() {
        return subWaveRule;
    }

    public AlienType getAlien() {
        return alien;
    }
}
