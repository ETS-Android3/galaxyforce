package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.textures.TextureRegion;

/**
 * abstract class for all sprites. to be extended by all concrete sprites.
 */
public abstract class Sprite
{

    /* instance variables for Sprite x,y position */
    private int x, y;

    /* instance variables for Sprite rotation */
    private int rotation;

    /* instance variables for Sprite visibility */
    private boolean visible;

    /* reference to sprite identifier */
    private ISpriteIdentifier spriteIdentifier = null;

    public Sprite(int xStart, int yStart, ISpriteIdentifier spriteIdentifier, boolean visible)
    {
        this.x = xStart;
        this.y = yStart;
        this.spriteIdentifier = spriteIdentifier;
        this.rotation = 0;
        this.visible = visible;
    }

    public Sprite(int xStart, int yStart, int rotation, ISpriteIdentifier spriteIdentifier, boolean visible)
    {
        this.x = xStart;
        this.y = yStart;
        this.spriteIdentifier = spriteIdentifier;
        this.rotation = rotation;
        this.visible = visible;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public ISpriteIdentifier getSpriteIdentifier()
    {
        return spriteIdentifier;
    }

    public void setSpriteIdentifier(ISpriteIdentifier spriteIdentifier)
    {
        this.spriteIdentifier = spriteIdentifier;
    }

    public TextureRegion getTextureRegion()
    {
        return spriteIdentifier.getProperties().getTextureRegion();
    }

    public int getWidth()
    {
        return spriteIdentifier.getProperties().getWidth();
    }

    public int getHeight()
    {
        return spriteIdentifier.getProperties().getHeight();
    }

    public int getRotation()
    {
        return rotation;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
