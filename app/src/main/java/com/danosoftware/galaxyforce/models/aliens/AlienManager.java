package com.danosoftware.galaxyforce.models.aliens;

import static com.danosoftware.galaxyforce.models.aliens.AlienManager.SubWaveState.DESTROYED;
import static com.danosoftware.galaxyforce.models.aliens.AlienManager.SubWaveState.END_OF_PASS;
import static com.danosoftware.galaxyforce.models.aliens.AlienManager.SubWaveState.IDLE;
import static com.danosoftware.galaxyforce.models.aliens.AlienManager.SubWaveState.PLAYING;
import static com.danosoftware.galaxyforce.models.aliens.AlienManager.SubWaveState.WAVE_COMPLETE;
import static com.danosoftware.galaxyforce.utilities.OffScreenTester.offScreenAnySide;
import static com.danosoftware.galaxyforce.waves.utilities.Randomiser.random;

import android.util.Log;
import com.danosoftware.galaxyforce.services.achievements.AchievementService;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.aliens.IResettableAlien;
import com.danosoftware.galaxyforce.sprites.providers.GamePlaySpriteProvider;
import com.danosoftware.galaxyforce.waves.SubWave;
import com.danosoftware.galaxyforce.waves.managers.WaveManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AlienManager implements IAlienManager {

  /* logger tag */
  private static final String TAG = AlienManager.class.getSimpleName();
  // provides waves
  private final WaveManager waveManager;
  private final GamePlaySpriteProvider spriteProvider;
  private final AchievementService achievements;
  // temporary queue of spawned aliens that will be added to the
  // main alien list at start of each animation loop
  private final List<IAlien> spawnedAliens;
  // state of current sub-wave
  private final List<IAlien> aliens;
  private final List<IAlien> activeAliens;
  private SubWaveState subWaveState;
  private boolean repeatedSubWave;

  public AlienManager(
      WaveManager waveManager,
      AchievementService achievements,
      GamePlaySpriteProvider spriteProvider) {
    this.waveManager = waveManager;
    this.achievements = achievements;
    this.spriteProvider = spriteProvider;
    this.aliens = new ArrayList<>();
    this.activeAliens = new ArrayList<>();
    this.spawnedAliens = new ArrayList<>();
    this.subWaveState = IDLE;
  }

  @Override
  public List<IAlien> activeAliens() {
    return activeAliens;
  }

  @Override
  public void animate(float deltaTime) {
    // track finished aliens
    int finishedAliens = 0;

    // track if any aliens change visibility
    boolean alienVisibilityChanged = false;

    // add queued spawned aliens.
    // spawned aliens are newly created aliens to the wave.
    // they should appear behind existing sprites so are added to beginning of list
    if (!spawnedAliens.isEmpty()) {
      aliens.addAll(0, spawnedAliens);
      spawnedAliens.clear();
      alienVisibilityChanged = true;
    }

    // create list to track visible aliens
    List<IAlien> visibleAliens = new ArrayList<>(aliens.size());

    // clear existing list of active aliens
    activeAliens.clear();

    ListIterator<IAlien> alienIterator = aliens.listIterator();
    while (alienIterator.hasNext()) {
      IAlien alien = alienIterator.next();
      boolean alienVisibleBefore = alien.isVisible();

      // animate
      alien.animate(deltaTime);

      // has visibility changed after animation?
      boolean alienVisibleAfter = alien.isVisible();
      if (alienVisibleAfter != alienVisibleBefore) {
        alienVisibilityChanged = true;
      }

      // update list of visible aliens
      if (alien.isVisible()) {
        visibleAliens.add(alien);
      }

      // update list of active aliens
      if (alien.isActive() && !offScreenAnySide(alien)) {
        activeAliens.add(alien);
      }

      // has alien finished it's pass
      // (wave will be reset when all have finished)
      if (alien instanceof IResettableAlien && ((IResettableAlien) alien).isEndOfPass()) {
        finishedAliens++;
      }

      // has alien been destroyed
      if (alien.isDestroyed()) {
        alienIterator.remove();
        alienVisibilityChanged = true;
        achievements.alienDestroyed(alien.character());
      }
    }

    // if the visibility of any alien has changed,
    // send the list of visible aliens to the sprite provider
    if (alienVisibilityChanged) {
      spriteProvider.setAliens(visibleAliens);
    }

    // have all aliens finished pass or been destroyed
    if (subWaveState == PLAYING && aliens.size() == 0) {
      subWaveState = DESTROYED;
    } else if (subWaveState == PLAYING && aliens.size() == finishedAliens) {
      subWaveState = END_OF_PASS;
    }

    // handles complex sub-wave scenarios
    updateSubWaveState();
  }

  @Override
  public void spawnAliens(List<IAlien> spawnedAliens) {
    // add spawned aliens to temporary queue.
    // we can't add them directly to the main alien list as this would cause a
    // concurrent modification exception within the animation loop.
    this.spawnedAliens.addAll(spawnedAliens);
  }

  @Override
  public void setUpWave(int wave) {
        /*
          asks wave manager to set-up next level. this is an asynchronous task
          that can be time-consuming so is run in the background.

          This method will return immediately but the wave will not be
          ready until waveManager.isWaveReady() responds with true.
         */
    waveManager.setUpWave(wave);
  }

  @Override
  public boolean isWaveReady() {
    if (waveManager.isWaveReady() && waveManager.hasNext()) {
      createAlienSubWave(waveManager.next());
      return true;
    }
    return false;
  }

  @Override
  public boolean isWaveComplete() {
    return (subWaveState == WAVE_COMPLETE);
  }

  @Override
  public IAlien chooseActiveAlien() {

    // if no aliens are active return null
    if (activeAliens.size() == 0) {
      return null;
    }

    // choose a random active alien
    int index = (int) (random() * activeAliens.size());
    return activeAliens.get(index);
  }

  /**
   * Initialises a new sub alien sub-wave
   */
  private void createAlienSubWave(final SubWave subWave) {
    aliens.clear();
    aliens.addAll(subWave.getAliens());
    this.repeatedSubWave = subWave.isWaveRepeated();
    this.subWaveState = PLAYING;

    activeAliens.clear();
    List<IAlien> visibleAliens = new ArrayList<>(aliens.size());

    for (IAlien alien : aliens) {
      if (alien.isActive() && !offScreenAnySide(alien)) {
        activeAliens.add(alien);
      }
      if (alien.isVisible()) {
        visibleAliens.add(alien);
      }
    }

    spriteProvider.setAliens(visibleAliens);
  }

  /**
   * This method performs the following functionality:
   * <p>
   * 1) Restarts a repeated sub-wave if all aliens are finished but not all were destroyed.
   * <p>
   * 2) Sets-up the next sub-wave if the current one is finished and another sub-wave is available.
   * <p>
   * 3) Sets the state to WAVE_COMPLETE if all sub-waves are completed.
   */
  private void updateSubWaveState() {
        /*
          if no aliens left then decide what action to take.
         */
    if (subWaveState == DESTROYED || subWaveState == END_OF_PASS) {
      /*
       * Have we reached the end of a sub-wave that repeats?
       */
      if (repeatedSubWave && subWaveState == END_OF_PASS) {
        /*
         * if there are finished aliens that were not destroyed and
         * the current sub-wave should be repeated (until all aliens are
         * destroyed) then reset the aliens and repeat the sub-wave.
         */
        List<IResettableAlien> aliensToRepeat = new ArrayList<>();

        Float minDelay = null;
        for (IAlien anAlien : aliens) {
          /*
           * if aliens are resettable then we want to restart
           * them immediately and not have to wait for their initial delay
           * to expire. find the lowest time delay and reduce all
           * aliens by this offset so the first alien starts
           * immediately.
           */
          if (anAlien instanceof IResettableAlien) {
            IResettableAlien alienWithPath = (IResettableAlien) anAlien;
            aliensToRepeat.add(alienWithPath);

            float timeDelay = alienWithPath.getTimeDelay();

            if (minDelay == null || timeDelay < minDelay) {
              minDelay = timeDelay;
            }
          }
        }

        /*
         * reduce offset of all repeated resettable aliens by minimum
         * offset. causes first alien to start immediately.
         */
        for (IResettableAlien anAlienToRepeat : aliensToRepeat) {
          anAlienToRepeat.reset(minDelay);
        }

        this.subWaveState = PLAYING;
        Log.i(TAG, "Wave: Reset SubWave");
      }
      /*
       * if there is another sub-wave, get it and assign new list to
       * aliens.
       */
      else if (waveManager.hasNext()) {
        createAlienSubWave(waveManager.next());
        Log.i(TAG, "Wave: Next SubWave");
      }
      /*
       * otherwise wave is finished. there are no more sub-waves.
       * we can advance to next wave.
       */
      else {
        this.subWaveState = WAVE_COMPLETE;
        Log.i(TAG, "Wave: All SubWaves Complete");
      }
    }
  }

  public enum SubWaveState {
    IDLE, PLAYING, END_OF_PASS, DESTROYED, WAVE_COMPLETE
  }
}
