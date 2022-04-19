package com.danosoftware.galaxyforce.sprites.providers;

import com.danosoftware.galaxyforce.sprites.common.ISprite;

public interface SpriteProvider extends Iterable<ISprite> {

  // how many sprites are contained in provider
  int count();
}
