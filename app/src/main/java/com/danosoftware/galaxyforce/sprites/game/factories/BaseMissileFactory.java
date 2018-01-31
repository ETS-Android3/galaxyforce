package com.danosoftware.galaxyforce.sprites.game.factories;

import java.util.ArrayList;
import java.util.List;

import com.danosoftware.galaxyforce.enumerations.BaseMissileType;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.game.beans.BaseMissileBean;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;
import com.danosoftware.galaxyforce.sound.SoundEffectBank;
import com.danosoftware.galaxyforce.sound.SoundEffectBankSingleton;
import com.danosoftware.galaxyforce.sprites.game.implementations.BaseMissileBlast;
import com.danosoftware.galaxyforce.sprites.game.implementations.BaseMissileFast;
import com.danosoftware.galaxyforce.sprites.game.implementations.BaseMissileGuided;
import com.danosoftware.galaxyforce.sprites.game.implementations.BaseMissileLaser;
import com.danosoftware.galaxyforce.sprites.game.implementations.BaseMissileSimple;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;

public class BaseMissileFactory
{
    /* initialise sound effects */
    private final static Sound SIMPLE_MISSILE_SOUND;

    static
    {
        /* create reference to sound effects */
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        SIMPLE_MISSILE_SOUND = soundBank.get(SoundEffect.BASE_FIRE);
    }

    // offset applied to blast angle based on current direction
    private static final float BLAST_OFFSET_UP = 0;
    private static final float BLAST_OFFSET_DOWN = (float) Math.PI;

    // angle delta between blast missiles directions in radians
    private static final float BLAST_ANGLE_DELTA = (float) (Math.PI / 8);

    // parallel fire x offset from centre of base
    private static final int PARALLEL_FIRE_X_OFFSET = 25;

    // fire y offset from top of base
    private static final int FIRE_Y_OFFSET = 2;

    /**
     * Return a list of missiles based on the type. Missile initial position
     * will be based on the base's position. Direction will be used to determine
     * initial position and direction of travel.
     * 
     * @param base
     * @param baseMissileType
     * @param direction
     * @param model
     * @return
     */
    public static BaseMissileBean createBaseMissile(SpriteBase base, BaseMissileType baseMissileType, Direction direction, GameHandler model)
    {
        List<SpriteBaseMissile> baseMissiles = new ArrayList<SpriteBaseMissile>();
        Sound sound;

        switch (baseMissileType)
        {
        case BLAST:

            // offset applied to angle based on current direction
            float offsetAngle = (direction == Direction.UP ? BLAST_OFFSET_UP : BLAST_OFFSET_DOWN);

            // angle delta between blast missiles in radians
            float angleDelta = BLAST_ANGLE_DELTA;

            // create multiple missiles that fire in different directions
            for (float angle = 0; angle <= Math.PI; angle += angleDelta)
            {
                baseMissiles.add(createMissile(base, baseMissileType, 0, direction, model, angle + offsetAngle));
            }
            sound = SIMPLE_MISSILE_SOUND;
            break;

        case FAST:
        case GUIDED:
        case SIMPLE:
        case LASER:

            // create single missile with no x offset
            baseMissiles.add(createMissile(base, baseMissileType, 0, direction, model, 0));
            sound = SIMPLE_MISSILE_SOUND;
            break;

        case PARALLEL:
        case SPRAY:

            // create two missiles with x offsets
            baseMissiles.add(createMissile(base, baseMissileType, PARALLEL_FIRE_X_OFFSET, direction, model, 0));
            baseMissiles.add(createMissile(base, baseMissileType, -PARALLEL_FIRE_X_OFFSET, direction, model, 0));
            sound = SIMPLE_MISSILE_SOUND;
            break;

        default:
            throw new IllegalArgumentException("Unsupported Base Missile Type: '" + baseMissileType.name() + "'.");
        }

        return new BaseMissileBean(baseMissiles, sound);
    }

    private static SpriteBaseMissile createMissile(SpriteBase base, BaseMissileType baseMissileType, int xOffset, Direction direction,
            GameHandler model, float angle)
    {

        SpriteBaseMissile missile = null;

        // set missile default starting x and y positions.
        // y initially set to 0 and corrected later.
        int x = base.getX() + xOffset;
        int y = 0;

        // create correct instance of base missile
        switch (baseMissileType)
        {
        case BLAST:

            missile = new BaseMissileBlast(x, y, angle);
            break;

        case FAST:

            missile = new BaseMissileFast(x, y, direction);
            break;

        case GUIDED:

            missile = new BaseMissileGuided(x, y, direction, model);
            break;

        case LASER:

            missile = new BaseMissileLaser(x, y, direction);
            break;

        case PARALLEL:

            missile = new BaseMissileSimple(x, y, direction);
            break;

        case SIMPLE:

            missile = new BaseMissileSimple(x, y, direction);
            break;

        case SPRAY:

            missile = new BaseMissileGuided(x, y, direction, model);
            break;

        default:
            throw new IllegalArgumentException("Unsupported Base Missile Type: '" + baseMissileType.name() + "'.");

        }

        // correct y position based on sprite height and direction
        int correctedY;

        switch (direction)
        {
        case UP:
            correctedY = base.getY() + (base.getHeight() / 2) + (missile.getHeight() / 2) - FIRE_Y_OFFSET;
            break;
        case DOWN:
            correctedY = base.getY() - (base.getHeight() / 2) - (missile.getHeight() / 2) + FIRE_Y_OFFSET;
            break;
        default:
            throw new IllegalArgumentException("Unsupported Direction Type: '" + direction.name() + "'.");
        }

        missile.setY(correctedY);

        // return new missile
        return missile;
    }
}
