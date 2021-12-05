package com.danosoftware.galaxyforce.waves.config;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRuleProperties;
import java.util.List;

/**
 * Describes a sub-wave consisting of a set of path rules and an alien config
 */
public class SubWavePathConfig extends SubWaveConfig {

  // list of properties for a sub-wave
  private final List<SubWavePathRuleProperties> subWaveRuleProperties;

  public SubWavePathConfig(
      final SubWavePathRule subWaveRule,
      final AlienConfig alienConfig,
      final List<PowerUpType> powerUps) {

    super(Type.PATH, alienConfig, powerUps);
    this.subWaveRuleProperties = subWaveRule.subWaveProps();
  }

  public SubWavePathConfig(
      final List<SubWavePathRuleProperties> subWaveRuleProperties,
      final AlienConfig alienConfig,
      final List<PowerUpType> powerUps) {

    super(Type.PATH, alienConfig, powerUps);
    this.subWaveRuleProperties = subWaveRuleProperties;
  }

  public List<SubWavePathRuleProperties> getSubWaveRuleProperties() {
    return subWaveRuleProperties;
  }
}
