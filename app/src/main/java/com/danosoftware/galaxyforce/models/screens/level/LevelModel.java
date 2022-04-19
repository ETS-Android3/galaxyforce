package com.danosoftware.galaxyforce.models.screens.level;

import com.danosoftware.galaxyforce.models.screens.Model;

public interface LevelModel extends Model {

  /**
   * Returns position of screen scroll. Moves when changing zones.
   *
   * @return speed of screen scroll
   */
  float getScrollPosition();
}
