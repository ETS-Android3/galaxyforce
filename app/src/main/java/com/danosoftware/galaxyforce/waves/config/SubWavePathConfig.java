package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;

/**
 * Describes a sub-wave consisting of a set of path rules and an alien type
 */
public class SubWavePathConfig extends SubWaveConfig {

    private final SubWavePathRule subWaveRule;
    private final AlienType alien;

    public SubWavePathConfig(
            final SubWavePathRule subWaveRule,
            final AlienType alien) {

        super(Type.PATH);
        this.subWaveRule = subWaveRule;
        this.alien = alien;
    }

    public SubWavePathRule getSubWaveRule() {
        return subWaveRule;
    }

    public AlienType getAlien() {
        return alien;
    }
}
