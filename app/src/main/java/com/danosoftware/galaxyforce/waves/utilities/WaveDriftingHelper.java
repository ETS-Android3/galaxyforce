package com.danosoftware.galaxyforce.waves.utilities;

import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.flatten;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromBottom;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromLeft;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromRight;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromTop;

import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DriftingConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;
import java.util.Collections;
import java.util.List;

public class WaveDriftingHelper {

  /**
   * Create drifting aliens starting at random positions, moving at specific angles.
   */
  public static SubWaveConfig[] createDriftingWave(
      final AlienCharacter alien,
      final Integer energy,
      final AlienSpeed speed,
      final List<PowerUpType> powerUpTypes,
      final SpinningConfig spinningConfig,
      final MissileConfig missileConfig) {

    /*
     * Normally power-ups are allocated across a sub-wave config.
     * In this case, each alien has its own sub-wave config so we need
     * to create our own local power-up allocator to share power-ups across our
     * aliens.
     */
    final PowerUpAllocator powerUpAllocator = new PowerUpAllocator(
        powerUpTypes,
        3 * 4,  // expected number of aliens
        0);

    return flatten(
        createSurroundingAliens(
            alien,
            energy,
            DOWNWARDS - (PI / 5.0f),
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator),
        createSurroundingAliens(
            alien,
            energy,
            DOWNWARDS + (PI / 7.0f),
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator),
        createSurroundingAliens(
            alien,
            energy,
            DOWNWARDS + (PI / 3.0f),
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator)
    );
  }

  /**
   * Create aliens that start off screen: top, bottom, left and right. The angles are adjusted so
   * aliens move towards centre of screen.
   */
  private static SubWaveConfig[] createSurroundingAliens(
      final AlienCharacter alien,
      final Integer energy,
      final float angle,
      final AlienSpeed speed,
      final SpinningConfig spinningConfig,
      final MissileConfig missileConfig,
      final PowerUpAllocator powerUpAllocator) {
    return new SubWaveConfig[]{
        createAliens(
            randomStartFromTop(2f),
            alien,
            energy,
            angle,
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator),
        createAliens(
            randomStartFromLeft(2f),
            alien,
            energy,
            angle + HALF_PI,
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator),
        createAliens(
            randomStartFromBottom(2f),
            alien,
            energy,
            angle + PI,
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator),
        createAliens(
            randomStartFromRight(2f),
            alien,
            energy,
            angle - HALF_PI,
            speed,
            spinningConfig,
            missileConfig,
            powerUpAllocator)
    };
  }

  private static SubWaveConfig createAliens(
      final SubWaveRuleProperties subWaveRuleProperties,
      final AlienCharacter alien,
      final Integer energy,
      final float angle,
      final AlienSpeed speed,
      final SpinningConfig spinningConfig,
      final MissileConfig missileConfig,
      final PowerUpAllocator powerUpAllocator) {

    final PowerUpType powerUp = powerUpAllocator.allocate();

    return new SubWaveNoPathConfig(
        Collections.singletonList(subWaveRuleProperties),
        DriftingConfig
            .builder()
            .alienCharacter(alien)
            .energy(energy)
            .speed(speed)
            .angle(angle)
            .spinningConfig(
                spinningConfig)
            .missileConfig(
                missileConfig)
            .build(),
        powerUp == null ? NO_POWER_UPS : Collections.singletonList(powerUp)
    );
  }
}
