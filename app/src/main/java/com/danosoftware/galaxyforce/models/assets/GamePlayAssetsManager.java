package com.danosoftware.galaxyforce.models.assets;

import com.danosoftware.galaxyforce.sprites.game.assets.Flag;
import com.danosoftware.galaxyforce.sprites.game.assets.Life;
import com.danosoftware.galaxyforce.sprites.game.missiles.aliens.IAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.missiles.bases.IBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.powerups.IPowerUp;
import com.danosoftware.galaxyforce.sprites.providers.GamePlaySpriteProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GamePlayAssetsManager implements IGamePlayAssetsManager {

  private final GamePlaySpriteProvider spriteProvider;
  private final List<IAlienMissile> aliensMissiles;
  private final List<IBaseMissile> baseMissiles;
  private final List<IPowerUp> powerUps;

  public GamePlayAssetsManager(GamePlaySpriteProvider spriteProvider) {

    this.spriteProvider = spriteProvider;
    this.aliensMissiles = new ArrayList<>();
    this.baseMissiles = new ArrayList<>();
    this.powerUps = new ArrayList<>();
  }

  @Override
  public void animate(float deltaTime) {

    // animate base missiles
    boolean baseMissilesChanged = false;
    ListIterator<IBaseMissile> baseMissileIterator = baseMissiles.listIterator();
    while (baseMissileIterator.hasNext()) {
      IBaseMissile baseMissile = baseMissileIterator.next();
      baseMissile.animate(deltaTime);
      if (baseMissile.isDestroyed()) {
        baseMissileIterator.remove();
        baseMissilesChanged = true;
      }
    }
    if (baseMissilesChanged) {
      spriteProvider.setBaseMissiles(baseMissiles);
    }

    // animate alien missiles
    boolean alienMissilesChanged = false;
    ListIterator<IAlienMissile> alienMissileIterator = aliensMissiles.listIterator();
    while (alienMissileIterator.hasNext()) {
      IAlienMissile alienMissile = alienMissileIterator.next();
      alienMissile.animate(deltaTime);
      if (alienMissile.isDestroyed()) {
        alienMissileIterator.remove();
        alienMissilesChanged = true;
      }
    }
    if (alienMissilesChanged) {
      spriteProvider.setAlienMissiles(aliensMissiles);
    }

    // animate power-ups
    boolean powerUpsChanged = false;
    ListIterator<IPowerUp> powerUpIterator = powerUps.listIterator();
    while (powerUpIterator.hasNext()) {
      IPowerUp powerUp = powerUpIterator.next();
      powerUp.animate(deltaTime);
      if (powerUp.isDestroyed()) {
        powerUpIterator.remove();
        powerUpsChanged = true;
      }
    }
    if (powerUpsChanged) {
      spriteProvider.setPowerUps(powerUps);
    }
  }

  @Override
  public void setLevelFlags(int wave) {
    spriteProvider.setFlags(Flag.getFlagList(wave));
  }

  @Override
  public void addPowerUp(PowerUpsDto powerUp) {
    powerUps.add(powerUp.getPowerUp());
    spriteProvider.setPowerUps(powerUps);
  }

  @Override
  public void fireBaseMissiles(BaseMissilesDto missiles) {
    baseMissiles.addAll(missiles.getMissiles());
    spriteProvider.setBaseMissiles(baseMissiles);
  }

  @Override
  public void fireAlienMissiles(AlienMissilesDto missiles) {
    aliensMissiles.addAll(missiles.getMissiles());
    spriteProvider.setAlienMissiles(aliensMissiles);
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
  public void setLives(int livesRemaining) {
    spriteProvider.setLives(Life.getLives(livesRemaining));
  }

  @Override
  public boolean alienMissilesDestroyed() {
    return aliensMissiles.isEmpty();
  }
}
