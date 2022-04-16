package com.danosoftware.galaxyforce.sprites.providers;

import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.Collection;

public interface MenuSpriteProvider extends SpriteProvider {

  // empties the contents of any cached sprites
  void clear();

  // add a single sprite
  void add(ISprite sprite);

  // add a collection of sprites
  void addAll(Collection<ISprite> sprites);
}
