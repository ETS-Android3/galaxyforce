package com.danosoftware.galaxyforce.waves.config.aliens.spawning;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SpawningAndExplodingAlienConfig extends SpawnConfig {

  private final AlienConfig spawnedAlienConfig;
  private final float spawnDelayTime;
  private final PowerUpType spawnedPowerUpType;

  @Builder
  public SpawningAndExplodingAlienConfig(
      @NonNull final AlienConfig spawnedAlienConfig,
      @NonNull final PowerUpType spawnedPowerUpType,
      @NonNull final Float spawnDelayTime) {

    super(SpawnType.SPAWN_AND_EXPLODE);
    this.spawnedAlienConfig = spawnedAlienConfig;
    this.spawnedPowerUpType = spawnedPowerUpType;
    this.spawnDelayTime = spawnDelayTime;
  }
}
