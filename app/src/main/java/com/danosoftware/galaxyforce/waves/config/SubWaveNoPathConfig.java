package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;
import java.util.List;

/**
 * Describes a sub-wave consisting of a set of rules and an alien config
 */
public class SubWaveNoPathConfig extends SubWaveConfig {

  // list of properties for a sub-wave
  private final List<SubWaveRuleProperties> subWaveRuleProperties;

  public SubWaveNoPathConfig(
      final SubWaveRule subWaveRule,
      final AlienConfig alienConfig,
      final List<PowerUpType> powerUps) {

    super(Type.NO_PATH, alienConfig, powerUps);
    this.subWaveRuleProperties = subWaveRule.subWaveProps();
  }

  public SubWaveNoPathConfig(
      final List<SubWaveRuleProperties> subWaveRuleProperties,
      final AlienConfig alienConfig,
      final List<PowerUpType> powerUps) {

    super(Type.NO_PATH, alienConfig, powerUps);
    this.subWaveRuleProperties = subWaveRuleProperties;
  }

  public List<SubWaveRuleProperties> getSubWaveRuleProperties() {
    return subWaveRuleProperties;
  }
}
