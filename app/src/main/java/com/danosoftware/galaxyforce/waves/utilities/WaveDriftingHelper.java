package com.danosoftware.galaxyforce.waves.utilities;

import static com.danosoftware.galaxyforce.waves.utilities.AlienConfigBuilder.driftingAlienConfig;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.flatten;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromBottom;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromLeft;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromRight;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.randomStartFromTop;

import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRuleProperties;
import java.util.Collections;
import java.util.List;

public class WaveDriftingHelper {

  /**
   * Create drifting aliens starting at random positions, moving at specific angles.
   */
  public static SubWaveConfig[] createDriftingWave(
      final AlienCharacter character,
      final AlienSpeed speed,
      final AlienMissileSpeed missileSpeed,
      final Float missileFrequency,
      final List<PowerUpType> powerUpTypes) {

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
            character,
            DOWNWARDS - (PI / 5.0f),
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator),
        createSurroundingAliens(
            character,
            DOWNWARDS + (PI / 7.0f),
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator),
        createSurroundingAliens(
            character,
            DOWNWARDS + (PI / 3.0f),
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator)
    );
  }

  /**
   * Create drifting aliens starting at random positions, moving at specific angles. The aliens move
   * at varying speeds.
   */
  public static SubWaveConfig[] createDriftingWaveWithVaryingSpeeds(
      final AlienCharacter character,
      final List<PowerUpType> powerUpTypes) {

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
            character,
            DOWNWARDS - (PI / 5.0f),
            AlienSpeed.MEDIUM,
            powerUpAllocator),
        createSurroundingAliens(
            character,
            DOWNWARDS + (PI / 7.0f),
            AlienSpeed.FAST,
            powerUpAllocator),
        createSurroundingAliens(
            character,
            DOWNWARDS + (PI / 3.0f),
            AlienSpeed.MEDIUM,
            powerUpAllocator)
    );
  }

  /**
   * Create aliens that start off screen: top, bottom, left and right. The angles are adjusted so
   * aliens move towards centre of screen.
   */
  private static SubWaveConfig[] createSurroundingAliens(
      final AlienCharacter character,
      final float angle,
      final AlienSpeed speed,
      final PowerUpAllocator powerUpAllocator) {
    return new SubWaveConfig[]{
        createAliens(
            randomStartFromTop(2f),
            character,
            angle,
            speed,
            powerUpAllocator),
        createAliens(
            randomStartFromLeft(2f),
            character,
            angle + HALF_PI,
            speed,
            powerUpAllocator),
        createAliens(
            randomStartFromBottom(2f),
            character,
            angle + PI,
            speed,
            powerUpAllocator),
        createAliens(
            randomStartFromRight(2f),
            character,
            angle - HALF_PI,
            speed,
            powerUpAllocator)
    };
  }

  /**
   * Create firing aliens that start off screen: top, bottom, left and right. The angles are
   * adjusted so aliens move towards centre of screen.
   */
  private static SubWaveConfig[] createSurroundingAliens(
      final AlienCharacter character,
      final float angle,
      final AlienSpeed speed,
      final AlienMissileSpeed missileSpeed,
      final Float missileFrequency,
      final PowerUpAllocator powerUpAllocator) {
    return new SubWaveConfig[]{
        createAliens(
            randomStartFromTop(2f),
            character,
            angle,
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator),
        createAliens(
            randomStartFromLeft(2f),
            character,
            angle + HALF_PI,
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator),
        createAliens(
            randomStartFromBottom(2f),
            character,
            angle + PI,
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator),
        createAliens(
            randomStartFromRight(2f),
            character,
            angle - HALF_PI,
            speed,
            missileSpeed,
            missileFrequency,
            powerUpAllocator)
    };
  }

  /**
   * Create drifting aliens
   */
  private static SubWaveConfig createAliens(
      final SubWaveRuleProperties subWaveRuleProperties,
      final AlienCharacter character,
      final float angle,
      final AlienSpeed speed,
      final PowerUpAllocator powerUpAllocator) {

    final PowerUpType powerUp = powerUpAllocator.allocate();

    return new SubWaveNoPathConfig(
        Collections.singletonList(subWaveRuleProperties),
        driftingAlienConfig(
            character,
            speed,
            angle),
        powerUp == null ? NO_POWER_UPS : Collections.singletonList(powerUp)
    );
  }

  /**
   * Create drifting aliens with missiles
   */
  private static SubWaveConfig createAliens(
      final SubWaveRuleProperties subWaveRuleProperties,
      final AlienCharacter character,
      final float angle,
      final AlienSpeed speed,
      final AlienMissileSpeed missileSpeed,
      final Float missileFrequency,
      final PowerUpAllocator powerUpAllocator) {

    final PowerUpType powerUp = powerUpAllocator.allocate();

    return new SubWaveNoPathConfig(
        Collections.singletonList(subWaveRuleProperties),
        driftingAlienConfig(
            character,
            speed,
            angle,
            missileSpeed,
            missileFrequency),
        powerUp == null ? NO_POWER_UPS : Collections.singletonList(powerUp)
    );
  }
}
