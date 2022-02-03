package com.danosoftware.galaxyforce.services.music;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioAttributes.Builder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import java.io.IOException;

public class MusicPlayerServiceImpl implements
    MusicPlayerService,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;

    // is player prepared for playing (prepares asynchronously)
    private volatile boolean isPrepared;

    // is music player enabled?
    private boolean musicEnabled;

    // currently playing music
    private Music currentlyLoaded;

    private final AssetManager assets;

    public MusicPlayerServiceImpl(Context context, boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
        this.assets = context.getAssets();
        this.currentlyLoaded = null;

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            // allows audio stream to have volume controlled by hardware buttons
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     * Load music (if different from currently loaded music) and prepare asynchronously.
     */
    @Override
    public void load(Music music) {

        Log.i(GameConstants.LOG_TAG, "Load Music");

        // no action if wanted music is already loaded
        if (currentlyLoaded == music) {
            return;
        }

        // dispose old media player
        if (this.mediaPlayer != null) {
            dispose();
        }

        // create new media player
        this.mediaPlayer = createMediaPlayer();

        // set file as music source
        try (AssetFileDescriptor assetDescriptor = assets.openFd("music/" + music.getFileName())) {
            mediaPlayer.setDataSource(
                assetDescriptor.getFileDescriptor(),
                assetDescriptor.getStartOffset(),
                assetDescriptor.getLength());
        } catch (IOException | IllegalArgumentException e) {
            throw new GalaxyForceException("Couldn't load music: " + music.getFileName(), e);
        }

        currentlyLoaded = music;
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1f, 1f);

        isPrepared = false;
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
    }

    @SuppressWarnings("deprecation")
    private MediaPlayer createMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(
                new Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build()
            );
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return mediaPlayer;
    }

    @Override
    public void play() {
        Log.i(GameConstants.LOG_TAG, "Play Music");
        // no action if already playing or disabled
        if (mediaPlayer.isPlaying() || !musicEnabled) {
            return;
        }

        // if not prepared then exit
        // will play automatically once prepared
        if (!isPrepared) {
            return;
        }

        // otherwise start playing
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        Log.i(GameConstants.LOG_TAG, "Pause Music");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void setMusicEnabled(boolean enableMusic) {
        this.musicEnabled = enableMusic;
    }

    @Override
    public void dispose() {
        Log.i(GameConstants.LOG_TAG, "Dispose Music");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    // music is now prepared - so we can play it.
    @Override
    public void onPrepared(MediaPlayer player) {
        Log.i(GameConstants.LOG_TAG, "Music Prepared");
        isPrepared = true;
        play();
    }

    // called if media player encounters an error
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(GameConstants.LOG_TAG,
            String.format("Music exception. type: %d. code: %d", what, extra));
        return false;
    }
}
