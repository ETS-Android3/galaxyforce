package com.danosoftware.galaxyforce.waves;

import java.util.Collection;

import com.danosoftware.galaxyforce.services.SavedGame;

/**
 * Class is run in a new tread and creates a new wave allowing the main thread
 * to continue servicing the UI.
 */
public class WaveCreator implements Runnable
{
    // wave number of wave to be created
    private final int waveNumber;

    // reference to calling wave manager
    private final WaveManager waveManager;

    // factory to create waves
    private final WaveFactory2 waveFactory;

    public WaveCreator(WaveManager waveManager, WaveFactory2 waveFactory, int waveNumber)
    {
        this.waveFactory = waveFactory;
        this.waveManager = waveManager;
        this.waveNumber = waveNumber;
    }

    @Override
    public void run()
    {
        // persist new wave if this is the highest level reached so far.
        // since this could involve I/O operation it is best to run this
        // within this separate thread.
        SavedGame savedGame = SavedGame.getInstance();
        int maxLevelUnlocked = savedGame.getGameLevel();
        if (waveNumber > maxLevelUnlocked)
        {
            savedGame.setGameLevel(waveNumber);
            savedGame.persistSavedGame();
        }

        // create wave - i.e. collection of sub-waves
        Collection<SubWave> subWaves = waveFactory.createWave(waveNumber);
        Wave wave = new Wave(subWaves);

        // callback to wave manager with completed wave
        waveManager.setWaveReady(wave);
    }
}
