package com.danosoftware.galaxyforce.sprites.game.factories;

import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.game.beans.AlienMissileBean;
import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;
import com.danosoftware.galaxyforce.sound.SoundEffectBank;
import com.danosoftware.galaxyforce.sound.SoundEffectBankSingleton;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienMissileRotated;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienMissileSimple;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;

import java.util.ArrayList;
import java.util.List;

public class AlienMissileFactory
{
    /* initialise sound effects */
    private final static Sound SIMPLE_MISSILE_SOUND;

    static
    {
        /* create reference to sound effects */
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        SIMPLE_MISSILE_SOUND = soundBank.get(SoundEffect.ALIEN_FIRE);
    }

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
    public static AlienMissileBean createAlienMissile(SpriteBase base, SpriteAlien alien, AlienMissileType missileType)
    {

        List<SpriteAlienMissile> missiles = new ArrayList<SpriteAlienMissile>();
        Sound soundEffect;

        /*
         * if base is null or alien is above base then fire downwards else fire
         * upwards
         */
        Direction direction = (base == null || base.getY() >= alien.getY()) ? Direction.DOWN : Direction.UP;

        /*
         * create missile's starting x and y positions based on alien position
         * and direction
         */
        int x = alien.getX();
        int y = direction == Direction.DOWN ? alien.getY() + (alien.getHeight() / 2) : alien.getY() - (alien.getHeight() / 2);

        /*
         * Create new missiles
         */
        switch (missileType)
        {

        case SIMPLE:

            missiles.add(AlienMissileSimple.newMissile(x, y, direction));
            soundEffect = SIMPLE_MISSILE_SOUND;
            break;

        case ROTATED:

            missiles.add(AlienMissileRotated.newMissile(x, y, base));
            soundEffect = SIMPLE_MISSILE_SOUND;
            break;

        default:

            throw new IllegalStateException("Unsupported missile type '" + missileType.name() + "'.");
        }

        // create bean of missiles and sound effect
        return new AlienMissileBean(missiles, soundEffect);
    }
}
