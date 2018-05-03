package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;

import java.util.List;

/**
 * Describes a sub-wave consisting of a set of path rules and an alien type
 */
public class SubWavePathConfig extends SubWaveConfig {

    private final SubWavePathRule subWaveRule;
    private final AlienType alien;
    private final List<PowerUpType> powerUps;

    public SubWavePathConfig(
            final SubWavePathRule subWaveRule,
            final AlienType alien,
            final List<PowerUpType> powerUps) {

        super(Type.PATH);
        this.subWaveRule = subWaveRule;
        this.alien = alien;
        this.powerUps = powerUps;
    }

    public SubWavePathRule getSubWaveRule() {
        return subWaveRule;
    }

    public AlienType getAlien() {
        return alien;
    }

    public List<PowerUpType> getPowerUps() {
        return powerUps;
    }
}
