package com.danosoftware.galaxyforce.game.beans;

import java.util.List;

import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;

/**
 * Spawned aliens bean holding the aliens and spawning sound effect.
 */
public class SpawnedAlienBean
{
    private final List<SpriteAlien> aliens;
    private final Sound soundEffect;

    public SpawnedAlienBean(List<SpriteAlien> aliens, Sound soundEffect)
    {
        this.aliens = aliens;
        this.soundEffect = soundEffect;
    }

    public List<SpriteAlien> getAliens()
    {
        return aliens;
    }

    public Sound getSoundEffect()
    {
        return soundEffect;
    }
}
