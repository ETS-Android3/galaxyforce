package com.danosoftware.galaxyforce.waves.config.aliens.spawning;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SpawningAlienConfig extends SpawnConfig {

  private final AlienConfig spawnedAlienConfig;
  private final float minimumSpawnDelayTime;
  private final float maximumAdditionalRandomSpawnDelayTime;
  private final List<PowerUpType> spawnedPowerUpTypes;

  @Builder
  public SpawningAlienConfig(
      @NonNull final AlienConfig spawnedAlienConfig,
      @NonNull final List<PowerUpType> spawnedPowerUpTypes,
      @NonNull final Float minimumSpawnDelayTime,
      @NonNull final Float maximumAdditionalRandomSpawnDelayTime) {

    super(SpawnType.SPAWN);
    this.spawnedAlienConfig = spawnedAlienConfig;
    this.spawnedPowerUpTypes = spawnedPowerUpTypes;
    this.minimumSpawnDelayTime = minimumSpawnDelayTime;
    this.maximumAdditionalRandomSpawnDelayTime = maximumAdditionalRandomSpawnDelayTime;
  }
}
