package com.danosoftware.galaxyforce.services.music;

import android.media.AudioAttributes;
import android.media.AudioAttributes.Builder;
import android.media.MediaPlayer;
import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;

/**
 * This helper uses the AudioAttributes class that is not available to older handsets so has been
 * moved in a separate class to avoid errors on these handsets.
 */
public class MusicPlayerHelper {

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public static MediaPlayer createModernMediaPlayer() {
    MediaPlayer mediaPlayer = new MediaPlayer();
    mediaPlayer.setAudioAttributes(
        new Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_GAME)
            .build());
    return mediaPlayer;
  }
}
