package com.danosoftware.galaxyforce.sprites.providers;

import androidx.annotation.NonNull;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BasicMenuSpriteProvider implements MenuSpriteProvider {

  private final List<ISprite> sprites;
  private int count;

  public BasicMenuSpriteProvider() {
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

  // synchronised since can be called from task thread and main thread
  @Override
  public synchronized void add(ISprite aSprite) {
    sprites.add(aSprite);
    count += 1;
  }

  // synchronised since can be called from task thread and main thread
  @Override
  public synchronized void addAll(Collection<ISprite> spriteColl) {
    sprites.addAll(spriteColl);
    count += spriteColl.size();
  }

  @NonNull
  @Override
  public Iterator<ISprite> iterator() {
    return sprites.iterator();
  }
}
