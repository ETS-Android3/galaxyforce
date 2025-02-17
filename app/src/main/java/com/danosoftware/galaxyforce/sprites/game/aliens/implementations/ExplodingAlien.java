package com.danosoftware.galaxyforce.sprites.game.aliens.implementations;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.models.assets.AlienMissilesDto;
import com.danosoftware.galaxyforce.models.screens.game.GameModel;
import com.danosoftware.galaxyforce.sprites.game.aliens.AbstractAlien;
import com.danosoftware.galaxyforce.sprites.game.behaviours.explode.ExplosionBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.behaviours.fire.FireBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.behaviours.hit.HitBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.behaviours.powerup.PowerUpBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.behaviours.spawn.SpawnBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.behaviours.spinner.SpinningBehaviourFactory;
import com.danosoftware.galaxyforce.sprites.game.factories.AlienMissileFactory;
import com.danosoftware.galaxyforce.waves.config.aliens.types.ExplodingConfig;
import lombok.Builder;
import lombok.NonNull;

/**
 * Alien that stays in a fixed position once spawned for a defined duration
 * and then explodes by spraying missiles in all directions.
 */
public class ExplodingAlien extends AbstractAlien {

    /* how many seconds before bomb explodes */
    private final float timeBeforeExplosion;

    // exploding missile
    private final AlienMissileCharacter explodingMissileCharacter;

    private final GameModel model;

    /* variable to store time passed */
    private float timer;

    private boolean isExploding;

    @Builder
    public ExplodingAlien(
        @NonNull final ExplosionBehaviourFactory explosionFactory,
        @NonNull final SpawnBehaviourFactory spawnFactory,
        @NonNull final SpinningBehaviourFactory spinningFactory,
        @NonNull final PowerUpBehaviourFactory powerUpFactory,
        @NonNull final FireBehaviourFactory fireFactory,
        @NonNull final HitBehaviourFactory hitFactory,
        @NonNull final GameModel model,
        @NonNull final ExplodingConfig alienConfig,
        final PowerUpType powerUpType,
        @NonNull final Float xStart,
        @NonNull final Float yStart) {

      super(
          alienConfig.getAlienCharacter(),
          alienConfig.getAlienCharacter().getAnimation(),
          xStart,
          yStart,
          alienConfig.getEnergy(),
          fireFactory.createFireBehaviour(
              alienConfig.getMissileConfig()),
          powerUpFactory.createPowerUpBehaviour(
              powerUpType),
          spawnFactory.createSpawnBehaviour(
              alienConfig.getSpawnConfig()),
          hitFactory.createHitBehaviour(
              alienConfig.getAlienCharacter().getHitAnimation()),
          explosionFactory.createExplosionBehaviour(
              alienConfig.getExplosionConfig(),
              alienConfig.getAlienCharacter()),
          spinningFactory.createSpinningBehaviour(
              alienConfig.getSpinningConfig()));

      this.model = model;
      this.timeBeforeExplosion = alienConfig.getExplosionTime();
      this.explodingMissileCharacter = alienConfig.getExplodingMissileCharacter();

      // reset timer
      timer = 0f;
      isExploding = false;
    }

    @Override
    public void animate(float deltaTime) {

        super.animate(deltaTime);

        if (!isExploding) {
          timer += deltaTime;
          if (timer > timeBeforeExplosion) {
            explode();
            // send missiles to model
            AlienMissilesDto missiles = AlienMissileFactory.createAlienMissile(
                model.getBase(),
                this,
                AlienMissileType.SPRAY,
                AlienMissileSpeed.MEDIUM,
                explodingMissileCharacter);
            model.fireAlienMissiles(missiles);
            isExploding = true;
          }
        }
    }
}
