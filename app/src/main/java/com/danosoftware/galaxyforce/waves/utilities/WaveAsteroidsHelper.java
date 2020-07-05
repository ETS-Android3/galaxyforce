package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.config.SubWavePathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.SpawningExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnOnDemandConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningBySpeedConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalDestroyableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DriftingConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.PathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.SplitterConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.QUARTER_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.flatten;

public class WaveAsteroidsHelper {

    /**
     * Create an asteroid field with asteroids at random positions, moving at specific angles.
     * On explosion, each asteroid will spawn two mini-asteroids at 45 degree angles
     */
    public static SubWaveConfig[] createAsteroidField(final List<PowerUpType> powerUpTypes) {

        /*
         * Normally power-ups are allocated across a sub-wave config.
         * For this asteroid field, each asteroid has its own sub-wave config so we need
         * to create our own local power-up allocator to share power-ups across our
         * asteroids.
         */
        final PowerUpAllocator powerUpAllocator = new PowerUpAllocator(
                powerUpTypes,
                3 * 4,  // expected number of asteroids
                0);

        return flatten(
                createSurroundingAsteroids(DOWNWARDS - (PI / 5.0f), AlienSpeed.MEDIUM, powerUpAllocator),
                createSurroundingAsteroids(DOWNWARDS + (PI / 7.0f), AlienSpeed.FAST, powerUpAllocator),
                createSurroundingAsteroids(DOWNWARDS + (PI / 3.0f), AlienSpeed.MEDIUM, powerUpAllocator),
                flyingSaucer()
        );
    }

    /**
     * Create asteroids that start off screen: top, bottom, left and right.
     * The angles are adjusted so asteroids move towards centre of screen.
     */
    public static SubWaveConfig[] createSurroundingAsteroids(
            final float angle,
            final AlienSpeed speed,
            final PowerUpAllocator powerUpAllocator) {
        return new SubWaveConfig[] {
                createAsteroids(SubWaveRule.RANDOM_TOP, angle, speed, powerUpAllocator),
                createAsteroids(SubWaveRule.RANDOM_LEFT, angle + HALF_PI, speed, powerUpAllocator),
                createAsteroids(SubWaveRule.RANDOM_BOTTOM, angle + PI, speed, powerUpAllocator),
                createAsteroids(SubWaveRule.RANDOM_RIGHT, angle - HALF_PI, speed, powerUpAllocator)
        };
    }

    private static SubWaveConfig createAsteroids(
            final SubWaveRule subWaveRule,
            final float angle,
            final AlienSpeed speed,
            final PowerUpAllocator powerUpAllocator) {

        final PowerUpType powerUp = powerUpAllocator.allocate();

        return new SubWaveNoPathConfig(
                subWaveRule,
                DriftingConfig
                        .builder()
                        .alienCharacter(AlienCharacter.ASTEROID)
                        .energy(2)
                        .speed(speed)
                        .angle(angle)
                        .spinningConfig(
                                SpinningBySpeedConfig
                                        .builder()
                                        .build())
                        .explosionConfig(
                                SpawningExplosionConfig
                                        .builder()
                                        .spawnConfig(
                                                SpawnOnDemandConfig
                                                        .builder()
                                                        // do not assign power-ups. would result in same
                                                        // power-up being given to both split asteroids.
                                                        .spwanedPowerUpTypes(
                                                                NO_POWER_UPS)
                                                        .spawnedAlienConfig(
                                                                SplitterConfig
                                                                    .builder()
                                                                    .alienConfigs(
                                                                            Arrays.asList(
                                                                                    createMiniDriftingAsteroid(angle - QUARTER_PI, speed),
                                                                                    createMiniDriftingAsteroid(angle + QUARTER_PI, speed)))
                                                                .build())
                                                        .build())
                                        .build()
                        )
                        .build(),
                powerUp == null ? NO_POWER_UPS : Collections.singletonList(powerUp)
        );
    }

    // drifting mini-asteroid that will split off
    private static AlienConfig createMiniDriftingAsteroid(
            final float angle,
            final AlienSpeed speed) {

        return DriftingConfig
            .builder()
            .alienCharacter(AlienCharacter.ASTEROID_MINI)
            .energy(1)
            .speed(speed)
            .spinningConfig(
                    SpinningBySpeedConfig
                            .builder()
                            .build())
            .angle(angle)
            .build();
    }

    // directional mini-asteroid that will split off
    public static AlienConfig createMiniDirectionalAsteroid(
            final float angle,
            final AlienSpeed speed) {

        return DirectionalDestroyableConfig
            .builder()
            .alienCharacter(AlienCharacter.ASTEROID_MINI)
            .energy(1)
            .speed(speed)
            .spinningConfig(
                    SpinningBySpeedConfig
                            .builder()
                            .build())
            .angle(angle)
            .build();
    }

    private static SubWaveConfig[] flyingSaucer() {
        return new SubWaveConfig[] {new SubWavePathConfig(
                SubWavePathRule.SINGLE_ARC,
                PathConfig
                        .builder()
                        .alienCharacter(AlienCharacter.DROOPY)
                        .energy(1)
                        .missileConfig(
                                MissileFiringConfig
                                        .builder()
                                        .missileType(AlienMissileType.ROTATED)
                                        .missileCharacter(AlienMissileCharacter.LASER)
                                        .missileSpeed(AlienMissileSpeed.MEDIUM)
                                        .missileFrequency(6.5f)
                                        .build())
                        .build(),
                Collections.singletonList(PowerUpType.LIFE)
        )};
    }
}
