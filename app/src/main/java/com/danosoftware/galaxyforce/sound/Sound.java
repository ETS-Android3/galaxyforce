package com.danosoftware.galaxyforce.sound;

public interface Sound
{

    /**
     * Play sound at specified volume
     * 
     * @param volume
     */
    public void play(float volume);

    /**
     * Dipose of sound when finished
     */
    public void dispose();
}
