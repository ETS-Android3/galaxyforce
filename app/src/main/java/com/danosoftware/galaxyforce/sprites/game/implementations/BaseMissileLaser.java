package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteState;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;

import java.util.HashSet;
import java.util.Set;

public class BaseMissileLaser extends SpriteBaseMissile
{

    /* missile sprite */
    public static final ISpriteIdentifier SPRITE = GameSpriteIdentifier.LASER_ALIEN;

    /* distance missile can move per cycle */
    public static final int BASE_MISSILE_MOVE_PIXELS = 7 * 60;

    /* energy of this missile */
    private static final int ENERGY = 0;

    /* how much energy will be lost by base when this missile hits it */
    private static final int HIT_ENERGY = 1;

    /* direction of missile */
    private Direction direction;

    /* list of any aliens laser has already hit */
    private Set<SpriteAlien> hitAliens = null;

    public BaseMissileLaser(int xStart, int yStart, Direction direction)
    {
        super(xStart, yStart, SPRITE, ENERGY, HIT_ENERGY);
        this.direction = direction;
        this.hitAliens = new HashSet<SpriteAlien>();
    }

    @Override
    public void move(float deltaTime)
    {

        // move missile until off the screen and then destroy it
        switch (direction)
        {
        case UP:

            setY(getY() + (int) (BASE_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() > GameConstants.GAME_HEIGHT - getHeight())
            {
                // setDestroy();
                setState(SpriteState.DESTROYED);
            }
            break;

        case DOWN:

            setY(getY() - (int) (BASE_MISSILE_MOVE_PIXELS * deltaTime));
            updateBounds();

            // if missile is now off screen then destory it
            if (getY() < 0 - getHeight())
            {
                // setDestroy();
                setState(SpriteState.DESTROYED);
            }
            break;
        }
    }

    /**
     * Lasers have infinite energy and so can never be destroyed by a hit.
     * 
     * Override the loseEnergy method so energy is never lost
     */
    @Override
    public void loseEnergy(int loseEnergy)
    {
        // do nothing
    }

    /**
     * Since laser can never be destroyed if should only hurt an alien once.
     * 
     * Otherwise it will provide the same damage after every collision detection
     * cycle. This will unfairly result in the destruction of any alien with one
     * laser bolt.
     * 
     * Keep a reference to any aliens hit so they can't be hit twice by the same
     * laser bolt.
     * 
     * @return true if alien has been hit before
     */
    @Override
    public boolean hitBefore(SpriteAlien alien)
    {
        if (hitAliens.contains(alien))
        {
            // hit before return true
            return true;
        }
        else
        {
            // not hit before so add to list and return false
            hitAliens.add(alien);
            return false;
        }
    }

}
