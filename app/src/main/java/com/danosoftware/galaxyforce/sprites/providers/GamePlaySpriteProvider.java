package com.danosoftware.galaxyforce.sprites.providers;

import com.danosoftware.galaxyforce.sprites.buttons.IButtonSprite;
import com.danosoftware.galaxyforce.sprites.common.ISprite;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.sprites.game.assets.Flag;
import com.danosoftware.galaxyforce.sprites.game.assets.Life;
import com.danosoftware.galaxyforce.sprites.game.missiles.aliens.IAlienMissile;
import com.danosoftware.galaxyforce.sprites.game.missiles.bases.IBaseMissile;
import com.danosoftware.galaxyforce.sprites.game.powerups.IPowerUp;
import java.util.List;

public interface GamePlaySpriteProvider extends SpriteProvider {

  void setAliens(List<IAlien> aliens);

  void setBases(List<ISprite> bases);

  void setAlienMissiles(List<IAlienMissile> alienMissiles);

  void setBaseMissiles(List<IBaseMissile> baseMissiles);

  void setPowerUps(List<IPowerUp> powerUps);

  void setButtons(List<IButtonSprite> buttons);

  void setFlags(List<Flag> flags);

  void setLives(List<Life> lives);

  List<ISprite> pausedSprites();
}
