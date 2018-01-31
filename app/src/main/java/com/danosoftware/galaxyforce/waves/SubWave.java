package com.danosoftware.galaxyforce.waves;

import java.util.List;

import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;

/**
 * Represents a sub-wave. A wave represents a collection of sub-waves.
 * 
 * Each sub-wave contains a collection of aliens and parameters about the
 * sub-wave
 */
public class SubWave
{
    // list of aliens in this sub-wave
    private final List<SpriteAlien> aliens;

    // should the sub-wave be repeated until all aliens are destroyed
    private final boolean waveRepeated;

    public SubWave(List<SpriteAlien> aliens, boolean repeatWave)
    {
        this.aliens = aliens;
        this.waveRepeated = repeatWave;
    }

    public List<SpriteAlien> getAliens()
    {
        return aliens;
    }

    public boolean isWaveRepeated()
    {
        return waveRepeated;
    }
}
