package com.danosoftware.galaxyforce.sprites.game.missiles.aliens;

import lombok.Builder;
import lombok.Value;

/**
 * Holds the calculated angle of travel and sprite rotation of an alien missile.
 */
@Value
@Builder
public class AlienMissileRotateCalculation {

  float angle;
  float rotation;

}
