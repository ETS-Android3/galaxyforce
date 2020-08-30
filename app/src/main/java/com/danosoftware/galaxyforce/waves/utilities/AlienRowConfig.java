package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.config.aliens.types.PathConfig;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents the configuration required to create a row of aliens.
 */
@Builder
@Getter
public class AlienRowConfig {

    final PathConfig alienConfig;
    final List<PowerUpType> powerUps;
}
