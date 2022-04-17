package com.danosoftware.galaxyforce.sprites.providers;

import androidx.annotation.NonNull;
import com.danosoftware.galaxyforce.sprites.buttons.IButtonSprite;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.assets.Flag;
import com.danosoftware.galaxyforce.sprites.game.assets.Life;
import com.danosoftware.galaxyforce.sprites.game.missiles.aliens.IAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.missiles.bases.IBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.powerups.IPowerUp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSpriteProvider implements GamePlaySpriteProvider {

  // holds in-game sprites
  private final List<ISprite> aliens;
  private final List<ISprite> bases;
  private final List<ISprite> alienMissiles;
  private final List<ISprite> baseMissiles;
  private final List<ISprite> powerUps;
  private final List<ISprite> buttons;
  private final List<ISprite> flags;
  private final List<ISprite> lives;

  private final List<ISprite> allSprites;

  private int aliensCount;
  private int basesCount;
  private int alienMissilesCount;
  private int baseMissilesCount;
  private int powerUpsCount;
  private int buttonsCount;
  private int flagsCount;
  private int livesCount;

  private int totalCount;
  private boolean spritesChanged;


  public GameSpriteProvider() {
    this.aliens = new ArrayList<>();
    this.bases = new ArrayList<>();
    this.alienMissiles = new ArrayList<>();
    this.baseMissiles = new ArrayList<>();
    this.powerUps = new ArrayList<>();
    this.buttons = new ArrayList<>();
    this.flags = new ArrayList<>();
    this.lives = new ArrayList<>();

    this.allSprites = new ArrayList<>();

    this.aliensCount = 0;
    this.basesCount = 0;
    this.alienMissilesCount = 0;
    this.baseMissilesCount = 0;
    this.powerUpsCount = 0;
    this.buttonsCount = 0;
    this.flagsCount = 0;
    this.livesCount = 0;

    this.totalCount = 0;
    this.spritesChanged = false;
  }

  @Override
  public int count() {
    if (spritesChanged) {
      refreshSprites();
    }

    return totalCount;
  }

  @NonNull
  @Override
  public Iterator<ISprite> iterator() {
    if (spritesChanged) {
      refreshSprites();
    }

    return allSprites.iterator();
  }

  // sprites to display when paused
  @Override
  public List<ISprite> pausedSprites() {
    List<ISprite> pausedSprites = new ArrayList<>();
    pausedSprites.addAll(aliens);
    pausedSprites.addAll(bases);
    pausedSprites.addAll(alienMissiles);
    pausedSprites.addAll(baseMissiles);
    pausedSprites.addAll(powerUps);
    pausedSprites.addAll(flags);
    pausedSprites.addAll(lives);

    return pausedSprites;
  }

  @Override
  public void setAliens(List<IAlien> aliens) {
    this.aliens.clear();
    this.aliens.addAll(aliens);
    aliensCount = aliens.size();
    spritesChanged = true;
  }

  @Override
  public void setBases(List<ISprite> bases) {
    this.bases.clear();
    this.bases.addAll(bases);
    basesCount = bases.size();
    spritesChanged = true;
  }

  @Override
  public void setAlienMissiles(List<IAlienMissile> alienMissiles) {
    this.alienMissiles.clear();
    this.alienMissiles.addAll(alienMissiles);
    alienMissilesCount = alienMissiles.size();
    spritesChanged = true;
  }

  @Override
  public void setBaseMissiles(List<IBaseMissile> baseMissiles) {
    this.baseMissiles.clear();
    this.baseMissiles.addAll(baseMissiles);
    baseMissilesCount = baseMissiles.size();
    spritesChanged = true;
  }

  @Override
  public void setPowerUps(List<IPowerUp> powerUps) {
    this.powerUps.clear();
    this.powerUps.addAll(powerUps);
    powerUpsCount = powerUps.size();
    spritesChanged = true;
  }

  @Override
  public void setButtons(List<IButtonSprite> buttons) {
    this.buttons.clear();
    this.buttons.addAll(buttons);
    buttonsCount = buttons.size();
    spritesChanged = true;
  }

  @Override
  public void setFlags(List<Flag> flags) {
    this.flags.clear();
    this.flags.addAll(flags);
    flagsCount = flags.size();
    spritesChanged = true;
  }

  @Override
  public void setLives(List<Life> lives) {
    this.lives.clear();
    this.lives.addAll(lives);
    livesCount = lives.size();
    spritesChanged = true;
  }

  private void refreshSprites() {
    totalCount = aliensCount +
        basesCount +
        alienMissilesCount +
        baseMissilesCount +
        powerUpsCount +
        buttonsCount +
        flagsCount +
        livesCount;

    allSprites.clear();
    allSprites.addAll(aliens);
    allSprites.addAll(bases);
    allSprites.addAll(alienMissiles);
    allSprites.addAll(baseMissiles);
    allSprites.addAll(powerUps);
    allSprites.addAll(buttons);
    allSprites.addAll(flags);
    allSprites.addAll(lives);

    spritesChanged = false;
  }
}
