package com.danosoftware.galaxyforce.waves.managers;

import com.danosoftware.galaxyforce.tasks.TaskService;
import com.danosoftware.galaxyforce.waves.SubWave;
import com.danosoftware.galaxyforce.waves.Wave;
import com.danosoftware.galaxyforce.waves.utilities.WaveFactory;
import java.util.Iterator;
import java.util.List;

public class WaveManagerImpl implements WaveManager {

  // factory used to create waves
  private final WaveFactory waveFactory;
  // iterator for sub-waves
  private Iterator<SubWave> iterator;
  // wave manager state
  private WaveManagerState state;

  // service for threads to create waves
  private final TaskService taskService;

  public WaveManagerImpl(
      WaveFactory waveFactory,
      TaskService taskService) {
    this.waveFactory = waveFactory;
    this.taskService = taskService;

    // set initial state
    this.state = WaveManagerState.NONE;
  }

  @Override
  public synchronized void setUpWave(int wave) {
    this.state = WaveManagerState.CREATING_WAVE;
    WaveCreator waveCreator = new WaveCreator(this, waveFactory, wave);
    taskService.execute(waveCreator);
  }

  /*
   * Method is synchronised as it is called by the main UI thread and alters
   * state. Could happen concurrently with setWaveReady() method.
   */
  @Override
  public synchronized boolean isWaveReady() {
    // check if wave is ready
    boolean isReady = (state == WaveManagerState.WAVE_READY);

    // if ready switch to iterating state
    if (isReady) {
      this.state = WaveManagerState.ITERATING_WAVE;
    }

    return isReady;
  }

  /*
   * Method is synchronised as it is called by the wave creation thread and
   * alters state. Could happen concurrently with isWaveReady() method.
   */
  @Override
  public synchronized void setWaveReady(Wave wave) {
    List<SubWave> subWaves = wave.getSubWaves();
    this.iterator = subWaves.iterator();
    this.state = WaveManagerState.WAVE_READY;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public SubWave next() {
    return iterator.next();
  }

  private enum WaveManagerState {
    NONE, CREATING_WAVE, WAVE_READY, ITERATING_WAVE
  }
}
