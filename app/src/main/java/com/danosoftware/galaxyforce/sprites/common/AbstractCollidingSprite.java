package com.danosoftware.galaxyforce.sprites.common;

import com.danosoftware.galaxyforce.sprites.properties.SpriteDetails;
import com.danosoftware.galaxyforce.sprites.properties.SpriteDimensions;
import com.danosoftware.galaxyforce.utilities.Rectangle;

public abstract class AbstractCollidingSprite extends AbstractMovingSprite implements
    ICollidingSprite {

  // sprite bounds for collision detection
  private Rectangle bounds;

  // are bounds cached?
  // once cached, bounds are valid until sprite moves or changes size
  private boolean boundsCached;

  // dimensions used to calculate bounds
  private boolean dimensionsOffsetsCached;
  private int twiceBoundsReduction;
  private int xOffset;
  private int yOffset;

  AbstractCollidingSprite(
      final SpriteDetails spriteId,
      final float x,
      final float y,
      final int rotation) {
    super(spriteId, x, y, rotation);
    this.boundsCached = false;
    this.dimensionsOffsetsCached = false;
  }

  protected AbstractCollidingSprite(
      final SpriteDetails spriteId,
      final float x,
      final float y) {
    this(spriteId, x, y, 0);
  }

  @Override
  public Rectangle getBounds() {
    if (boundsCached) {
      return bounds;
    }
    return bounds();
  }

  @Override
  public void move(float x, float y) {
    super.move(x, y);
    this.boundsCached = false;
  }

  @Override
  public void moveY(float y) {
    super.moveY(y);
    this.boundsCached = false;
  }

  @Override
  public void moveByDelta(float xDelta, float yDelta) {
    super.moveByDelta(xDelta, yDelta);
    this.boundsCached = false;
  }

  @Override
  public void moveYByDelta(float yDelta) {
    super.moveYByDelta(yDelta);
    this.boundsCached = false;
  }

  @Override
  public void changeType(SpriteDetails newSpriteId) {
    if (this.spriteDetails() != newSpriteId) {
      this.boundsCached = false;
      this.dimensionsOffsetsCached = false;
    }
    super.changeType(newSpriteId);
  }

  // create and return bounds.
    // will try to create bounds from sprite properties (if available) and cache result
    // otherwise zero width/height bounds are returned
    private Rectangle bounds() {
      SpriteDimensions dimensions = spriteDetails().getDimensions();
      if (dimensions != null) {
        cacheBounds(dimensions);
        return bounds;
      }
      return new Rectangle(x(), y(), 0, 0);
    }

  // cache dimensions and bounds
  private void cacheBounds(SpriteDimensions dimensions) {

    // update offsets used for computing rectangle
    if (!dimensionsOffsetsCached) {
      updateDimensionsOffsets(dimensions);
    }

    // bounds represents bottom-left position then width and height
    this.bounds = new Rectangle(
        x() - xOffset,
        y() - yOffset,
        width() - twiceBoundsReduction,
        height() - twiceBoundsReduction);
    this.boundsCached = true;
  }

  // compute offsets used for bounds creation
  private void updateDimensionsOffsets(SpriteDimensions dimensions) {
    final int boundsReduction = dimensions.getBoundsReduction();
    twiceBoundsReduction = boundsReduction * 2;
    xOffset = halfWidth() - boundsReduction;
    yOffset = halfHeight() - boundsReduction;
    dimensionsOffsetsCached = true;
  }
}
