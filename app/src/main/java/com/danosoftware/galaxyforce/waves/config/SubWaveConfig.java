package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

/**
 * Describes a subwave consisting of a set of rules and an alien type
 */
public class SubWaveConfig {

    private final SubWaveRule subWaveRule;
    private final AlienType alien;

    public SubWaveConfig(
            final SubWaveRule subWaveRule,
            final AlienType alien) {
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
