package com.danosoftware.galaxyforce.waves;

import java.util.Collection;

/**
 * Alien waves consist of a collection of sub-waves.
 */
public class Wave
{

    // collection of subwaves in this wave
    private final Collection<SubWave> subWaves;

    public Wave(Collection<SubWave> subWaves)
    {
        this.subWaves = subWaves;
    }

    public Collection<SubWave> getSubWaves()
    {
        return subWaves;
    }
}
