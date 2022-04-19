package com.danosoftware.galaxyforce.models.assets;

import com.danosoftware.galaxyforce.sprites.game.missiles.aliens.IAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.missiles.bases.IBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.powerups.IPowerUp;
import java.util.List;

/**
 * Manages all in-game assets
 */
public interface IGamePlayAssetsManager {

  void animate(float deltaTime);

  void setLevelFlags(int wave);

  void addPowerUp(PowerUpsDto powerUp);

  void fireBaseMissiles(BaseMissilesDto missiles);

  void fireAlienMissiles(AlienMissilesDto missiles);

  List<IAlienMissile> getAliensMissiles();

  List<IBaseMissile> getBaseMissiles();

  List<IPowerUp> getPowerUps();

  void setLives(int lives);

  boolean alienMissilesDestroyed();
}
