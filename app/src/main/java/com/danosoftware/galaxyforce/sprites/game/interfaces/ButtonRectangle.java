package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;

public class ButtonRectangle extends Sprite
{
    // sprite bounds for collision detection
    private Rectangle bounds = null;

    /**
     * Constructor for basic button where bounds determined by sprite's size
     * 
     * @param xStart
     *            - x centre of button
     * @param yStart
     *            - y centre of button
     * @param spriteId
     *            - button's sprite property
     */
    public ButtonRectangle(int xStart, int yStart, ISpriteIdentifier spriteId)
    {
        super(xStart, yStart, spriteId, true);
        this.bounds = new Rectangle(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    /**
     * Constructor for button where bounds determined by sprite's size plus
     * buffer around all the edges. If sprite has dimensions 64 x 64 and a
     * bounds area of 128 x 128 is wanted then a buffer of 32 should be supplied
     * in the constructor. i.e. 64 normal size + 32 top + 32 bottom = 128
     * overall.
     * 
     * @param xStart
     *            - x centre of button
     * @param yStart
     *            - y centre of button
     * @param spriteId
     *            - button's sprite property
     * @param buffer
     *            - extra buffer around edge of sprite for bounds
     */
    public ButtonRectangle(int xStart, int yStart, ISpriteIdentifier spriteId, int xBuffer, int yBuffer)
    {
        super(xStart, yStart, spriteId, true);
        this.bounds = new Rectangle(getX() - (getWidth() / 2) - xBuffer, getY() - (getHeight() / 2) - yBuffer, getWidth() + (xBuffer * 2),
                getHeight() + (yBuffer * 2));
    }

    public Rectangle getBounds()
    {
        return this.bounds;
    }
}
