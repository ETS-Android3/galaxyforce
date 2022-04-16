package com.danosoftware.galaxyforce.sprites.providers;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_WIDTH;

import androidx.annotation.NonNull;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A sprite provider that only provides on-screen sprites when the screen can be scrolled
 * left-or-right.
 */
public class ScrollableSpriteProvider implements MenuSpriteProvider {

  private final List<ISprite> sprites;
  private final List<ISprite> onScreenSprites;
  private int count;
  private float scrollPosition;
  private boolean refreshOnScreenSprites;

  public ScrollableSpriteProvider() {
    this.sprites = new ArrayList<>();
    this.onScreenSprites = new ArrayList<>();
    this.count = 0;
    this.scrollPosition = -1;
  }

  @Override
  public void clear() {
    sprites.clear();
    refreshOnScreenSprites = true;
  }

  @Override
  public void add(ISprite aSprite) {
    sprites.add(aSprite);
    refreshOnScreenSprites = true;
  }

  @Override
  public void addAll(Collection<ISprite> spriteColl) {
    sprites.addAll(spriteColl);
    refreshOnScreenSprites = true;
  }

  @Override
  public int count() {
    if (refreshOnScreenSprites) {
      refreshOnScreenSprites(scrollPosition);
    }
    return count;
  }

  @NonNull
  @Override
  public Iterator<ISprite> iterator() {
    if (refreshOnScreenSprites) {
      refreshOnScreenSprites(scrollPosition);
    }
    return onScreenSprites.iterator();
  }

  public void updateScrollPosition(float newScrollPosition) {
    if (scrollPosition != newScrollPosition) {
      scrollPosition = newScrollPosition;
      refreshOnScreenSprites = true;
    }
  }

  // refresh the on-screen sprites based on the current scroll position
  private void refreshOnScreenSprites(float minX) {
    onScreenSprites.clear();

    // calculate min and max X bounds for current scroll-position.
    // a sprite will be displayed if it falls within these bounds.
    float maxX = minX + GAME_WIDTH;

    // find all sprites to show on-screen
    for (ISprite sprite : sprites) {
      float xPos = sprite.x();
      int halfWidth = sprite.halfWidth();
      float leftX = xPos - halfWidth;
      float rightX = xPos + halfWidth;
      // add to on-screen sprites if any part of sprite is within bounds
      if ((leftX >= minX && leftX <= maxX) || (rightX >= minX && rightX <= maxX)) {
        onScreenSprites.add(sprite);
      }
    }
    count = onScreenSprites.size();
    refreshOnScreenSprites = false;
  }
}
