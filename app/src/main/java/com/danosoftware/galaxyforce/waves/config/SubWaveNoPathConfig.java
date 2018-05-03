package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienType;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

import java.util.List;

/**
 * Describes a sub-wave consisting of a set of rules and an alien type
 */
public class SubWaveNoPathConfig extends SubWaveConfig {

    private final SubWaveRule subWaveRule;
    private final AlienType alien;
    private final List<PowerUpType> powerUps;

    public SubWaveNoPathConfig(
            final SubWaveRule subWaveRule,
            final AlienType alien,
            final List<PowerUpType> powerUps) {

        super(Type.NO_PATH);
        this.subWaveRule = subWaveRule;
        this.alien = alien;
        this.powerUps = powerUps;
    }

    public SubWaveRule getSubWaveRule() {
        return subWaveRule;
    }

    public AlienType getAlien() {
        return alien;
    }

    public List<PowerUpType> getPowerUps() {
        return powerUps;
    }
}
