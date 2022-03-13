package com.danosoftware.galaxyforce.sprites.providers;

import androidx.annotation.NonNull;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GameSpriteProvider implements SpriteProvider {

  // chained-collection of all sprites
  private final Collection<Collection<ISprite>> chainedList;

  public GameSpriteProvider() {
    this.chainedList = new ArrayList<>();
  }

  @Override
  public void clear() {

  }

  @Override
  public int count() {
    return 0;
  }

//  @Override
//  public Iterable<ISprite> sprites() {
//    List<ISprite> sprites = new ArrayList<>();
//    List<ISprite> cheese = new ArrayList<>();
//    Iterable<ISprite> it = sprites;
//    return it;
//  }

//  @Override
//  public boolean hasUpdated() {
//    return false;
//  }

  @Override
  public void add(ISprite sprite) {

  }

  @Override
  public void addAll(Collection<ISprite> sprites) {

  }

  @NonNull
  @Override
  public Iterator<ISprite> iterator() {
//    List<ISprite> sprites = new ArrayList<>();
//    List<ISprite> cheese = new ArrayList<>();
//    Collection<Collection<ISprite>> chainedList = new ArrayList<>();
//    chainedList.add(sprites);
//    chainedList.add(cheese);
    return new ChainedIterator<>(chainedList);
  }
}
