package com.danosoftware.galaxyforce.sprites.providers;

import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.Collection;

public interface SpriteProvider extends Iterable<ISprite> {

  // empties the contents of any cached sprites
  void clear();

  // how many sprites are contained in provider
  int count();

  // add a single sprite
  void add(ISprite sprite);

  // add a collection of sprites
  void addAll(Collection<ISprite> sprites);
}
