package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;

public abstract class MovingSprite extends Sprite
{

    // sprite bounds for collision detection
    private Rectangle bounds = null;

    // state of current sprite
    protected SpriteState state = null;

    // energy of this sprite
    private int energy;

    // how much energy will be lost by another sprite when this sprite hits it
    private int hitEnergy;

    public MovingSprite(int xStart, int yStart, ISpriteIdentifier spriteId, int energy, int hitEnergy, boolean visible)
    {
        super(xStart, yStart, spriteId, visible);
        state = SpriteState.ACTIVE;
        updateBounds();

        this.energy = energy;
        this.hitEnergy = hitEnergy;
    }

    /**
     * If this sprite is hit by another object. The amount of energy this sprite
     * should lose is supplied as a parameter.
     * 
     * If this missile's energy drops to 0 or below, it is destroyed.
     * 
     * @param loseEnergy
     *            - the amount of energy to lose
     */
    public void loseEnergy(int loseEnergy)
    {
        energy = energy - loseEnergy;

        if (energy <= 0)
        {
            setDestroyed();
        }
    }

    /**
     * If this sprite hits another object. This method returns how much energy
     * the other object should lose.
     * 
     * @return amount of energy to lose
     */
    public int hitEnergy()
    {
        return hitEnergy;
    }

    public boolean isDestroyed()
    {
        return (state == SpriteState.DESTROYED);
    }

    public void setDestroyed()
    {
        state = SpriteState.DESTROYED;
    }

    public boolean isEndOfPass()
    {
        return (state == SpriteState.FINISHED_PASS);
    }

    public boolean isActive()
    {
        return (state == SpriteState.ACTIVE);
    }

    public boolean isInactive()
    {
        return (state == SpriteState.INACTIVE);
    }

    public Rectangle getBounds()
    {
        return this.bounds;
    }

    public void updateBounds()
    {
        this.bounds = new Rectangle(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    protected void setState(SpriteState state)
    {
        this.state = state;
    }

    public abstract void move(float deltaTime);
}
