package com.danosoftware.galaxyforce.waves.config.aliens.spinning;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoSpinningConfig extends SpinningConfig {

  @Builder
  public NoSpinningConfig() {
    super(SpinningConfigType.NO_SPINNING);
  }
}
