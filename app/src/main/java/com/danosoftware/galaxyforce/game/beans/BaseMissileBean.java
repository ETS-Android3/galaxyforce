package com.danosoftware.galaxyforce.game.beans;

import java.util.List;

import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBaseMissile;

/**
 * Base missile bean holding the base missiles and sound effects.
 */
public class BaseMissileBean
{
    private final List<SpriteBaseMissile> missiles;
    private final Sound soundEffect;

    public BaseMissileBean(List<SpriteBaseMissile> missiles, Sound soundEffect)
    {
        this.missiles = missiles;
        this.soundEffect = soundEffect;
    }

    public List<SpriteBaseMissile> getMissiles()
    {
        return missiles;
    }

    public Sound getSoundEffect()
    {
        return soundEffect;
    }
}
