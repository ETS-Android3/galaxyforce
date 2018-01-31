package com.danosoftware.galaxyforce.interfaces;

import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;

public interface Audio
{
    public Music newMusic(String filename);

    public Sound newSound(SoundEffect soundEffect);
}
