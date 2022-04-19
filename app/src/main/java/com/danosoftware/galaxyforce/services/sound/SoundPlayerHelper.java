package com.danosoftware.galaxyforce.services.sound;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;

/**
 * This helper uses the AudioAttributes and Builder classes that are not available to older handsets
 * so have been moved in a separate class to avoid errors on these handsets.
 */
public class SoundPlayerHelper {

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public static SoundPool createModernSoundPool(int maxStreams) {
    return new SoundPool.Builder()
        .setAudioAttributes(
            new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build())
        .setMaxStreams(maxStreams)
        .build();
  }
}
