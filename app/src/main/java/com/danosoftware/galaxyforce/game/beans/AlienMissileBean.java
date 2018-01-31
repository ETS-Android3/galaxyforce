package com.danosoftware.galaxyforce.game.beans;

import java.util.List;

import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlienMissile;

/**
 * Alien missile bean holding the alien missiles and sound effects.
 */
public class AlienMissileBean
{
    private final List<SpriteAlienMissile> missiles;
    private final Sound soundEffect;

    public AlienMissileBean(List<SpriteAlienMissile> missiles, Sound soundEffect)
    {
        this.missiles = missiles;
        this.soundEffect = soundEffect;
    }

    public List<SpriteAlienMissile> getMissiles()
    {
        return missiles;
    }

    public Sound getSoundEffect()
    {
        return soundEffect;
    }
}
