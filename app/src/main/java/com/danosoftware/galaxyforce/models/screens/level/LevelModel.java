package com.danosoftware.galaxyforce.models.screens.level;

import com.danosoftware.galaxyforce.models.screens.Model;

public interface LevelModel extends Model {

  /**
   * Returns position of screen scroll. Moves when changing zones.
   *
   * @return speed of screen scroll
   */
  float getScrollPosition();

  /**
   * Some sprites (e.g. starfield) will be rendered separately to level buttons and will not be
   * effected by scrolling so should be provided in a separate method.
   *
   * @return list of static sprites
   */
  //List<ISprite> getStaticSprites();

  /**
   * Some text will be rendered separately to level button text and will not be effected by
   * scrolling so should be provided in a separate method.
   *
   * @return list of static text
   */
  //List<Text> getStaticText();
  //TextProvider getStaticTextProvider();

}
