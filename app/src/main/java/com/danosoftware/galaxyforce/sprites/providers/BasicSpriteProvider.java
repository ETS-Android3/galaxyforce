package com.danosoftware.galaxyforce.sprites.providers;

import androidx.annotation.NonNull;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BasicSpriteProvider implements SpriteProvider {

  private final List<ISprite> sprites;
  private int count;

  public BasicSpriteProvider() {
    this.sprites = new ArrayList<>();
    this.count = 0;
  }

  @Override
  public void clear() {
    sprites.clear();
    count = 0;
  }

  @Override
  public int count() {
    return count;
  }

  @Override
  public void add(ISprite aSprite) {
    sprites.add(aSprite);
    count += 1;
  }

  @Override
  public void addAll(Collection<ISprite> spriteColl) {
    sprites.addAll(spriteColl);
    count += spriteColl.size();
  }

  @NonNull
  @Override
  public Iterator<ISprite> iterator() {
    return sprites.iterator();
  }
}
