package com.danosoftware.galaxyforce.sprites.providers;

import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.ArrayList;
import java.util.List;

public class SpriteList {

  private List<ISprite> sprites = new ArrayList<>();

  public List<ISprite> list() {
    return sprites;
  }

}
