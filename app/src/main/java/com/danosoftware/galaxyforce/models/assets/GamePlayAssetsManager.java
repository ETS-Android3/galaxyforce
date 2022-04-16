package com.danosoftware.galaxyforce.models.assets;

import com.danosoftware.galaxyforce.sprites.game.assets.Flag;
import com.danosoftware.galaxyforce.sprites.game.assets.Life;
import com.danosoftware.galaxyforce.sprites.game.missiles.aliens.IAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.missiles.bases.IBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.powerups.IPowerUp;
import com.danosoftware.galaxyforce.sprites.providers.GamePlaySpriteProvider;
import java.util.ArrayList;
import java.util.List;

public class GamePlayAssetsManager implements IGamePlayAssetsManager {

  private final GamePlaySpriteProvider spriteProvider;
  private List<IAlienMissile> aliensMissiles;
  private List<IBaseMissile> baseMissiles;
  private List<IPowerUp> powerUps;
  private List<Flag> flags;
  private List<Life> lives;

  public GamePlayAssetsManager(GamePlaySpriteProvider spriteProvider) {

    this.spriteProvider = spriteProvider;
    this.aliensMissiles = new ArrayList<>();
    this.baseMissiles = new ArrayList<>();
    this.powerUps = new ArrayList<>();
    this.flags = new ArrayList<>();
    this.lives = new ArrayList<>();
  }

  @Override
  public void animate(float deltaTime) {

    List<IBaseMissile> nonDestroyedBaseMissiles = new ArrayList<>();
    for (IBaseMissile baseMissile : baseMissiles) {
      baseMissile.animate(deltaTime);
      if (!baseMissile.isDestroyed()) {
        nonDestroyedBaseMissiles.add(baseMissile);
      }
    }
    baseMissiles = nonDestroyedBaseMissiles;
    spriteProvider.setBaseMissiles(baseMissiles);

    List<IAlienMissile> nonDestroyedAlienMissiles = new ArrayList<>();
    for (IAlienMissile alienMissile : aliensMissiles) {
      alienMissile.animate(deltaTime);
      if (!alienMissile.isDestroyed()) {
        nonDestroyedAlienMissiles.add(alienMissile);
      }
    }
    aliensMissiles = nonDestroyedAlienMissiles;
    spriteProvider.setAlienMissiles(aliensMissiles);

    List<IPowerUp> nonDestroyedPowerUps = new ArrayList<>();
    for (IPowerUp powerUp : powerUps) {
      powerUp.animate(deltaTime);
      if (!powerUp.isDestroyed()) {
        nonDestroyedPowerUps.add(powerUp);
      }
    }
    powerUps = nonDestroyedPowerUps;
    spriteProvider.setPowerUps(powerUps);
  }

  @Override
  public void setLevelFlags(int wave) {
    flags = Flag.getFlagList(wave);
    spriteProvider.setFlags(flags);
  }

  @Override
  public void addPowerUp(PowerUpsDto powerUp) {
    powerUps.add(powerUp.getPowerUp());
  }

  @Override
  public void fireBaseMissiles(BaseMissilesDto missiles) {
    baseMissiles.addAll(missiles.getMissiles());
  }

  @Override
  public void fireAlienMissiles(AlienMissilesDto missiles) {
    aliensMissiles.addAll(missiles.getMissiles());
  }

  @Override
  public List<IAlienMissile> getAliensMissiles() {
    return aliensMissiles;
  }

  @Override
  public List<IBaseMissile> getBaseMissiles() {
    return baseMissiles;
  }

  @Override
  public List<IPowerUp> getPowerUps() {
    return powerUps;
  }

  @Override
  public List<Flag> getFlags() {
    return flags;
  }

  @Override
  public List<Life> getLives() {
    return lives;
  }

  @Override
  public void setLives(int livesRemaining) {
    lives = Life.getLives(livesRemaining);
    spriteProvider.setLives(lives);
  }

  @Override
  public boolean alienMissilesDestroyed() {
    return aliensMissiles.isEmpty();
  }
}
