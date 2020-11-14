package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.flightpath.paths.Path;
import com.danosoftware.galaxyforce.flightpath.paths.PathSpeed;
import com.danosoftware.galaxyforce.sprites.game.aliens.IAlien;
import com.danosoftware.galaxyforce.utilities.Reversed;
import com.danosoftware.galaxyforce.utilities.WaveUtilities;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.SubWave;
import com.danosoftware.galaxyforce.waves.config.SubWaveConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveNoPathConfig;
import com.danosoftware.galaxyforce.waves.config.SubWavePathConfig;
import com.danosoftware.galaxyforce.waves.config.SubWaveRepeatMode;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.MultiExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.SpawningExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileMultiFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnOnDemandConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawningAlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawningAndExplodingAlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawningLimitedAlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningBySpeedConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningFixedAngularConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.BoundariesConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.BoundaryLanePolicy;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalDestroyableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalResettableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.ExplodingConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowableHunterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowerConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.HunterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.PathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.SplitterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.StaticConfig;
import com.danosoftware.galaxyforce.waves.rules.SubWavePathRule;
import com.danosoftware.galaxyforce.waves.rules.SubWaveRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.danosoftware.galaxyforce.constants.GameConstants.GAME_HEIGHT;
import static com.danosoftware.galaxyforce.constants.GameConstants.SCREEN_MID_X;
import static com.danosoftware.galaxyforce.waves.utilities.AlienConfigBuilder.alienConfig;
import static com.danosoftware.galaxyforce.waves.utilities.AlienConfigBuilder.alienRowConfig;
import static com.danosoftware.galaxyforce.waves.utilities.MazePatternCreator.mazePatternOne;
import static com.danosoftware.galaxyforce.waves.utilities.MazePatternCreator.mazePatternThree;
import static com.danosoftware.galaxyforce.waves.utilities.MazePatternCreator.mazePatternTwo;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createDescendingDelayedRowSubWave;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createDescendingOffsetRowsSubWave;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createDiamondDroppers;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createLeftToRightDelayedRowSubWave;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createLeftToRightOffsetRowSubWave;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createRowDroppers;
import static com.danosoftware.galaxyforce.waves.utilities.PathWaveHelper.createStaggeredDroppers;
import static com.danosoftware.galaxyforce.waves.utilities.WaveAsteroidsHelper.createAsteroidField;
import static com.danosoftware.galaxyforce.waves.utilities.WaveAsteroidsHelper.createMiniDirectionalAsteroid;
import static com.danosoftware.galaxyforce.waves.utilities.WaveDriftingHelper.createDriftingWave;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.QUARTER_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.flatten;
import static com.danosoftware.galaxyforce.waves.utilities.WaveMazeHelper.asteroidHorizontalRows;
import static com.danosoftware.galaxyforce.waves.utilities.WaveMazeHelper.asteroidMazeSubWave;
import static com.danosoftware.galaxyforce.waves.utilities.WaveMazeHelper.createBarrierMaze;
import static com.danosoftware.galaxyforce.waves.utilities.WaveMazeHelper.createBarrierMazeWithGuards;


/**
 * Creates a wave of aliens based on the provided wave number. Each wave
 * property can contain multiple sub-waves, each consisting of a number of
 * aliens following a path
 */
public class WaveFactory {

    private final WaveCreationUtils creationUtils;
    private final PowerUpAllocatorFactory powerUpAllocatorFactory;

    public WaveFactory(
            WaveCreationUtils creationUtils,
            PowerUpAllocatorFactory powerUpAllocatorFactory) {
        this.creationUtils = creationUtils;
        this.powerUpAllocatorFactory = powerUpAllocatorFactory;
    }

    /**
     * Return a collection of sub-waves based on the current wave number
     *
     * @param wave - wave number
     * @return collection of sub-waves
     */
    public List<SubWave> createWave(int wave) {
        if (!WaveUtilities.isValidWave(wave)) {
            throw new GalaxyForceException("Wave not recognised '" + wave + "'.");
        }

        // reset power-up allocation factory for a new wave
        powerUpAllocatorFactory.newWave();

        List<SubWave> subWaves = new ArrayList<>();

        switch (wave) {

            case 1:

                /**
                 * Wave 1 - Bouncer alien
                 * Octopus - wavey line from left to right.
                 * Bouncer - wavey line from right to left.
                 * Octopus & Bouncer - wavey line in both directions (above and below).
                 * Bad Cat - travels straight down at various intervals.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE,
                                        alienConfig(
                                                AlienCharacter.ROTATOR,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createStaggeredDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                0.25f),
                                        alienConfig(
                                                AlienCharacter.FROGGER,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE_REVERSE,
                                        alienConfig(
                                                AlienCharacter.BOUNCER,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createStaggeredDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                0.25f),
                                        alienConfig(
                                                AlienCharacter.BAD_CAT,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE,
                                        alienConfig(
                                                AlienCharacter.OCTOPUS,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE_REVERSE_LOWER,
                                        alienConfig(
                                                AlienCharacter.BOUNCER,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createStaggeredDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                0.25f),
                                        alienConfig(
                                                AlienCharacter.BAD_CAT,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        Collections.singletonList(PowerUpType.MISSILE_BLAST)
                                )
                        )
                );
                break;

            /**
             * Diamond formation of aliens followed by a wall of aliens.
             * Wall of aliens are unable to fire.
             * Starts with formation of Droopy, followed by Zogg and then Circuit
             */
            case 2:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                0f),
                                        alienConfig(
                                                AlienCharacter.DROOPY,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                3f),
                                        alienConfig(
                                                AlienCharacter.DROOPY),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                5f),
                                        alienConfig(
                                                AlienCharacter.ZOGG,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                8f),
                                        alienConfig(
                                                AlienCharacter.ZOGG),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                10f),
                                        alienConfig(
                                                AlienCharacter.CIRCUIT,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                13f),
                                        alienConfig(
                                                AlienCharacter.CIRCUIT),
                                        NO_POWER_UPS
                                )
                        )
                );
                break;

            /**
             * Waves of different aliens drop down in diamond formation and then return to
             * top of screen one after the other.
             */
            case 3:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                0f),
                                        alienConfig(
                                                AlienCharacter.TELLY,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                3f),
                                        alienConfig(
                                                AlienCharacter.HELMET,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                5f),
                                        alienConfig(
                                                AlienCharacter.TELLY,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                8f),
                                        alienConfig(
                                                AlienCharacter.HELMET,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                10f),
                                        alienConfig(
                                                AlienCharacter.SPINNER_PULSE_GREEN,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.BOUNCE_DOWN_AND_UP,
                                                PathSpeed.NORMAL,
                                                13f),
                                        alienConfig(
                                                AlienCharacter.SPINNER_PULSE_GREEN,
                                                AlienMissileSpeed.FAST,
                                                6.5f),
                                        NO_POWER_UPS
                                )
                        )
                );
                break;

            case 4:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createLeftToRightOffsetRowSubWave(
                                        Path.VALLEY_DROP,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)),
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))},
                                        2,
                                        0,
                                        0f,
                                        false)
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createLeftToRightDelayedRowSubWave(
                                        Path.VALLEY_DROP,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)),
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))},
                                        2,
                                        0,
                                        0f)
                        )
                );

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createLeftToRightDelayedRowSubWave(
                                        Path.LEFT_AND_RIGHT,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD))},
                                        5,
                                        GAME_HEIGHT - 230,
                                        0f)
                        )
                );

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createLeftToRightOffsetRowSubWave(
                                        Path.LEFT_AND_RIGHT,
                                        PathSpeed.FAST,
                                        new AlienRowConfig[] {
                                                alienRowConfig(
                                                        AlienCharacter.PILOT,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD)),
                                                alienRowConfig(
                                                        AlienCharacter.FRISBIE,
                                                        AlienMissileSpeed.FAST,
                                                        15f,
                                                        Collections.singletonList(PowerUpType.SHIELD))},
                                        5,
                                        GAME_HEIGHT - 230,
                                        0f,
                                        false)
                        )
                );
                break;

            case 5:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.AROUND_EDGE,
                                        alienConfig(
                                                AlienCharacter.CHARLIE,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))));
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DIAGONAL_CROSSOVER,
                                        alienConfig(
                                                AlienCharacter.GHOST,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))));
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DIAGONAL_CROSSOVER_INTERLEAVED,
                                        alienConfig(
                                                AlienCharacter.BATTLE_DROID,
                                                AlienMissileSpeed.MEDIUM,
                                                6.5f),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))));
                break;

                /**
                 * Insect Motherships move at top of screen while spawning aliens that drop downwards.
                 */
            case 6:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        DirectionalDestroyableConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .angle(DOWNWARDS)
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                .missileFrequency(1.5f)
                                                                                                .build())
                                                                                .speed(AlienSpeed.SLOW)
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        DirectionalDestroyableConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .angle(DOWNWARDS)
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                .missileFrequency(1.5f)
                                                                                                .build())
                                                                                .speed(AlienSpeed.SLOW)
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.SHIELD)
                                )
                        )
                );
                break;

            /**
             * Aliens attack in triangular attack patterns
             */
            case 7:

                // triangular attack from left-to-right
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                // triangular attack from right-to-left
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR_REVERSED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                // both triangular attacks at same time
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_BLAST)
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR_REVERSED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        NO_POWER_UPS
                                )
                        )
                );
                break;

            case 8:

                /**
                 * Aliens rise on both sides of screen and the cross to the other side.
                 * Repeat in reverse direction. Some gaps between aliens to allow attack.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSOVER_EXIT_ATTACK_SPACED_REVERSE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.LADY_BIRD)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSOVER_EXIT_ATTACK_SPACED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.LADY_BIRD)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                )
                        )
                );
                break;

            case 9:

                /**
                 * Asteroids falling from the top of screen. Split into two when destroyed.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.ASTEROIDS,
                                        DirectionalResettableConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ASTEROID)
                                                .energy(2)
                                                .speed(AlienSpeed.MEDIUM)
                                                .angle(DOWNWARDS)
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
                                                                                .spawnedPowerUpTypes(
                                                                                        NO_POWER_UPS)
                                                                                .spawnedAlienConfig(SplitterConfig
                                                                                        .builder()
                                                                                        .alienConfigs(
                                                                                                Arrays.asList(
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI - QUARTER_PI, AlienSpeed.MEDIUM),
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI + QUARTER_PI, AlienSpeed.MEDIUM)))
                                                                                        .build())
                                                                                .build())
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                ),
                                new SubWaveNoPathConfig(
                                        SubWaveRule.ASTEROIDS,
                                        DirectionalResettableConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ASTEROID)
                                                .energy(2)
                                                .speed(AlienSpeed.VERY_FAST)
                                                .angle(DOWNWARDS)
                                                .spinningConfig(
                                                        SpinningBySpeedConfig
                                                                .builder()
                                                                .build()
                                                )
                                                .explosionConfig(
                                                        SpawningExplosionConfig
                                                                .builder()
                                                                .spawnConfig(
                                                                        SpawnOnDemandConfig
                                                                                .builder()
                                                                                .spawnedPowerUpTypes(
                                                                                        NO_POWER_UPS)
                                                                                .spawnedAlienConfig(SplitterConfig
                                                                                        .builder()
                                                                                        .alienConfigs(
                                                                                                Arrays.asList(
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI - QUARTER_PI, AlienSpeed.VERY_FAST),
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI + QUARTER_PI, AlienSpeed.VERY_FAST)))
                                                                                        .build())
                                                                                .build())
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                )
                        )
                );
                break;

            /**
             * Space invaders attack from top, moving from left/right and gradually descending.
             */
            case 10:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPACE_INVADER,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.GOBBY)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(10f)
                                                                .build())
                                                .build(),
                                        Arrays.asList(PowerUpType.MISSILE_BLAST, PowerUpType.LIFE, PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPACE_INVADER_REVERSE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.GOBBY)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(10f)
                                                                .build())
                                                .build(),
                                        Arrays.asList(PowerUpType.MISSILE_GUIDED, PowerUpType.MISSILE_PARALLEL)
                                )
                        )
                );
                break;

            /**
             * Aliens drops in columns and then bounce up.
             */
            case 11:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_BOUNCE_ATTACK,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.SHIELD)
                                )
                        )
                );
                break;

            /**
             * Single long-tailed dragon that chases base around the screen.
             */
            case 12:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.RANDOM_TOP,
                                        FollowableHunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.DRAGON_HEAD)
                                                .energy(20)
                                                .speed(AlienSpeed.VERY_FAST)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .numberOfFollowers(20)
                                                .followerConfig(
                                                        FollowerConfig
                                                                .builder()
                                                                .alienCharacter(AlienCharacter.DRAGON_BODY)
                                                                .energy(1)
                                                                .speed(AlienSpeed.VERY_FAST)
                                                                .build())
                                                .followerPowerUps(
                                                        Arrays.asList(
                                                                PowerUpType.MISSILE_GUIDED,
                                                                PowerUpType.MISSILE_PARALLEL,
                                                                PowerUpType.MISSILE_SPRAY))
                                                .boundaries(
                                                        BoundariesConfig.builder().build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                break;

            case 13:

                // ??????

                /**
                 * Aliens descend in spiral patterns from top-to-bottom
                 */
            case 14:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPIRAL,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)

                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SPIRAL,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        NO_POWER_UPS
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DOUBLE_SPIRAL,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                break;

            case 15:


                /**
                 * Single bouncing aliens.
                 * Wave of descending aliens in diamonds and rows.
                 * Double layer of bouncing aliens.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BOUNCING,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.SKULL)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                0f),
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(3f)
                                                                .build())
                                                .build(),
                                        Arrays.asList(PowerUpType.MISSILE_FAST, PowerUpType.MISSILE_FAST)
                                ),
                                new SubWavePathConfig(
                                        createRowDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                3f),
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(2)
                                                .build(),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        createDiamondDroppers(
                                                Path.STRAIGHT_DOWN,
                                                PathSpeed.SLOW,
                                                4f),
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(4.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )));
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BOUNCING,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.FROGGER)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(4.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.BOUNCING_HIGHER,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.JUMPER)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)
                                )
                        )
                );
                break;

            case 16:


                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningLimitedAlienConfig
                                                                .builder()
                                                                .maximumActiveSpawnedAliens(5)
                                                                .spawnedAlienConfig(
                                                                        HunterConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .speed(AlienSpeed.MEDIUM)
                                                                                .boundaries(BoundariesConfig
                                                                                        .builder()
                                                                                        .lanePolicy(BoundaryLanePolicy.VERTICAL)
                                                                                        .lanes(3)
                                                                                        .build())
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningLimitedAlienConfig
                                                                .builder()
                                                                .maximumActiveSpawnedAliens(5)
                                                                .spawnedAlienConfig(
                                                                        HunterConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .speed(AlienSpeed.MEDIUM)
                                                                                .boundaries(BoundariesConfig
                                                                                        .builder()
                                                                                        .lanePolicy(BoundaryLanePolicy.VERTICAL)
                                                                                        .lanes(3)
                                                                                        .build())
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                .missileFrequency(1.5f)
                                                                                                .build())
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                break;

            /**
             * Aliens attack in descending valley attack and then an ascending valley attack.
             */
            case 17:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_INTERLEAVED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_FAST)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_FLIPPED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        NO_POWER_UPS
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.VALLEY_DIVE_INTERLEAVED_FLIPPED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                break;

            /**
             * Base chased by two flying books. One book occupies left half or screen.
             * Other book occupies right half of screen.
             */
            case 18:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.MIDDLE_TOP,
                                        HunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BOOK)
                                                .energy(10)
                                                .speed(AlienSpeed.VERY_FAST)
                                                .boundaries(
                                                        BoundariesConfig
                                                                .builder()
                                                                .maxX(SCREEN_MID_X)
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                ),
                                new SubWaveNoPathConfig(
                                        SubWaveRule.MIDDLE_BOTTOM,
                                        HunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BOOK)
                                                .energy(10)
                                                .speed(AlienSpeed.VERY_FAST)
                                                .boundaries(
                                                        BoundariesConfig
                                                                .builder()
                                                                .minX(SCREEN_MID_X)
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                break;

            case 19:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BELL_CURVE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Arrays.asList(
                                                PowerUpType.MISSILE_LASER,
                                                PowerUpType.SHIELD)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.DOUBLE_BELL_CURVE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Arrays.asList(
                                                PowerUpType.MISSILE_LASER,
                                                PowerUpType.SHIELD,
                                                PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                break;

            case 20:

                /**
                 * Alien drops from top to bottom spawning aliens that move diagnonally
                 * down the screen while firing missiles at the base.
                 */

                // config of two spawned aliens
                AlienConfig bearConfig = DirectionalDestroyableConfig
                        .builder()
                        .alienCharacter(AlienCharacter.BEAR)
                        .energy(1)
                        .speed(AlienSpeed.FAST)
                        .angle(-HALF_PI - QUARTER_PI)
                        .missileConfig(
                                MissileFiringConfig
                                        .builder()
                                        .missileCharacter(AlienMissileCharacter.LASER)
                                        .missileType(AlienMissileType.ROTATED)
                                        .missileFrequency(4f)
                                        .missileSpeed(AlienMissileSpeed.SLOW)
                                        .build())
                        .build();
                AlienConfig jokerConfig = DirectionalDestroyableConfig
                        .builder()
                        .alienCharacter(AlienCharacter.JOKER)
                        .energy(1)
                        .speed(AlienSpeed.FAST)
                        .angle(-HALF_PI + QUARTER_PI)
                        .missileConfig(
                                MissileFiringConfig
                                        .builder()
                                        .missileCharacter(AlienMissileCharacter.LASER)
                                        .missileType(AlienMissileType.ROTATED)
                                        .missileFrequency(4f)
                                        .missileSpeed(AlienMissileSpeed.SLOW)
                                        .build())
                        .build();

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SINGLE_SPIRAL,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.WHIRLPOOL)
                                                .energy(20)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                        .builder()
                                                        .maximumAdditionalRandomSpawnDelayTime(1f)
                                                        .minimumSpawnDelayTime(0.5f)
                                                        .spwanedPowerUpTypes(Arrays.asList(
                                                                PowerUpType.MISSILE_LASER,
                                                                PowerUpType.SHIELD,
                                                                PowerUpType.MISSILE_SPRAY))
                                                        .spawnedAlienConfig(
                                                                SplitterConfig
                                                                        .builder()
                                                                        .alienConfigs(
                                                                                Arrays.asList(bearConfig, jokerConfig))
                                                                        .build())
                                                        .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                )
                        )
                );
                break;

            case 21:

                /**
                 * Maze to navigate through.
                 * First maze is empty.
                 * Second maze has alien guards blocking the gaps.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createBarrierMaze(
                                        15,
                                        AlienSpeed.VERY_FAST,
                                        2)
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createBarrierMazeWithGuards(
                                        15,
                                        AlienSpeed.VERY_FAST,
                                        2,
                                        AlienCharacter.ROTATOR,
                                        1,
                                        Collections.singletonList(PowerUpType.MISSILE_LASER))
                        )
                );
                break;

            case 22:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        DirectionalDestroyableConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .angle(DOWNWARDS)
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                .missileFrequency(1.5f)
                                                                                                .build())
                                                                                .speed(AlienSpeed.SLOW)
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        DirectionalDestroyableConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                .energy(1)
                                                                                .angle(DOWNWARDS)
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                .missileFrequency(1.5f)
                                                                                                .build())
                                                                                .speed(AlienSpeed.SLOW)
                                                                                .build())
                                                                .minimumSpawnDelayTime(0.5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.SHIELD)
                                )
                        )
                );
                break;


            case 23:

                /**
                 * slow looper attack from both sides firing directional missiles.
                 * fast bell curve attack.
                 * fast curving attack from top-to-bottom with aliens on both sides
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.LOOPER_ATTACK,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ROTATOR)
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
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)),
                                new SubWavePathConfig(
                                        SubWavePathRule.LOOPER_ATTACK_REVERSE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ROTATOR)
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
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL))));
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BELL_CURVE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BOUNCER)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER))));
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE_BENDING_RIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.DROOPY)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.HELPER_BASES)),
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVEY_LINE_BENDING_LEFT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.DROOPY)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_FAST))));
                break;

            case 24:
                /**
                 * Alien spawns an egg that cracks and then spawns a dragon!!
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BOMBER_RUN,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        StaticConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.EGG)
                                                                                .energy(1)
                                                                                .spawnConfig(
                                                                                        SpawningAndExplodingAlienConfig.builder()
                                                                                                .spawnedAlienConfig(
                                                                                                        FollowableHunterConfig
                                                                                                                .builder()
                                                                                                                .alienCharacter(AlienCharacter.DRAGON_HEAD)
                                                                                                                .energy(5)
                                                                                                                .speed(AlienSpeed.VERY_FAST)
                                                                                                                .missileConfig(
                                                                                                                        MissileFiringConfig
                                                                                                                                .builder()
                                                                                                                                .missileType(AlienMissileType.ROTATED)
                                                                                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                                                                .missileFrequency(6.5f)
                                                                                                                                .build())
                                                                                                                .numberOfFollowers(5)
                                                                                                                .followerConfig(
                                                                                                                        FollowerConfig
                                                                                                                                .builder()
                                                                                                                                .alienCharacter(AlienCharacter.DRAGON_BODY)
                                                                                                                                .energy(1)
                                                                                                                                .speed(AlienSpeed.VERY_FAST)
                                                                                                                                .build())
                                                                                                                .followerPowerUps(
                                                                                                                        Arrays.asList(
                                                                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                                                                PowerUpType.MISSILE_PARALLEL,
                                                                                                                                PowerUpType.MISSILE_SPRAY))
                                                                                                                .boundaries(
                                                                                                                        BoundariesConfig.builder().build())
                                                                                                                .build())
                                                                                                .spwanedPowerUpType(PowerUpType.MISSILE_GUIDED)
                                                                                                .spawnDelayTime(2.25f)  // aligns to egg cracking animation 9 x 0.25f
                                                                                                .build()
                                                                                )
                                                                                .build())
                                                                .minimumSpawnDelayTime(1f)
                                                                .maximumAdditionalRandomSpawnDelayTime(5f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                break;

            case 25:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                mazePatternOne(
                                        AlienSpeed.MEDIUM,
                                        NO_POWER_UPS)
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                mazePatternTwo(
                                        AlienSpeed.VERY_FAST,
                                        NO_POWER_UPS)
                        )
                );
                break;

            case 26:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createBarrierMazeWithGuards(
                                        15,
                                        AlienSpeed.VERY_FAST,
                                        2,
                                        AlienCharacter.ROTATOR,
                                        1,
                                        Collections.singletonList(PowerUpType.MISSILE_LASER))
                        )
                );
                break;

            case 27:

                // ??????? place-holder need real level
                // old wave 19
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                asteroidMazeSubWave(
                                        15,
                                        AlienSpeed.SLOW,
                                        3,
                                        Collections.singletonList(PowerUpType.MISSILE_LASER))
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                asteroidMazeSubWave(
                                        15,
                                        AlienSpeed.SLOW,
                                        3,
                                        Collections.singletonList(PowerUpType.SHIELD))
                        )
                );
                break;

            case 28:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.ASTEROIDS,
                                        DirectionalDestroyableConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MOLECULE)
                                                .energy(5)
                                                .angle(DOWNWARDS)
                                                .speed(AlienSpeed.MEDIUM)
                                                .spinningConfig(
                                                        SpinningBySpeedConfig
                                                                .builder()
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                )
                        )
                );
                break;

            case 29:

                final MissileConfig twentyNineMissileConfig = MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(AlienMissileSpeed.FAST)
                        .missileFrequency(15f)
                        .build();
                final AlienRowConfig fishRowConfig2 = AlienRowConfig
                        .builder()
                        .alienConfig(
                                PathConfig
                                        .builder()
                                        .alienCharacter(AlienCharacter.FISH)
                                        .energy(2)
                                        .missileConfig(twentyNineMissileConfig)
                                        .build())
                        .powerUps(Collections.singletonList(PowerUpType.SHIELD))
                        .build();

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                flatten(
                                        mazePatternThree(
                                                AlienSpeed.MEDIUM,
                                                NO_POWER_UPS),
                                        createLeftToRightOffsetRowSubWave(
                                                Path.LEFT_AND_RIGHT,
                                                PathSpeed.NORMAL,
                                                new AlienRowConfig[] {fishRowConfig2, fishRowConfig2},
                                                3,
                                                GAME_HEIGHT - 230,
                                                0f,
                                                true
                                        )
                                )
                        )
                );
                break;

            case 30:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BOMBER_RUN,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        ExplodingConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.BOMB)
                                                                                .energy(1)
                                                                                .explosionTime(3f)
                                                                                .explodingMissileCharacter(AlienMissileCharacter.FIREBALL)
                                                                                .build())
                                                                .minimumSpawnDelayTime(1f)
                                                                .maximumAdditionalRandomSpawnDelayTime(0.5f)
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                break;

            case 31:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_LEFT_AND_RIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.CLOUD)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(0.5f)
                                                        .build())
                                                .energy(10)
                                                .missileConfig(
                                                        MissileMultiFiringConfig
                                                                .builder()
                                                                .missileConfigs(
                                                                        Arrays.asList(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.RAIN)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(0.75f)
                                                                                        .build(),
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.ROTATED)
                                                                                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(5f)
                                                                                        .build()))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY))
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_LEFT_AND_RIGHT_REVERSED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.CLOUD)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(0.5f)
                                                        .build())
                                                .energy(10)
                                                .missileConfig(
                                                        MissileMultiFiringConfig
                                                                .builder()
                                                                .missileConfigs(
                                                                        Arrays.asList(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.RAIN)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(2f)
                                                                                        .build(),
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.ROTATED)
                                                                                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(8f)
                                                                                        .build()))
                                                                .build())
                                                .build(),
                                        Arrays.asList(PowerUpType.SHIELD, PowerUpType.HELPER_BASES, PowerUpType.MISSILE_LASER))
                        )
                );
                break;


            case 32:

                // ????????

            case 33:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createBarrierMazeWithGuards(
                                        15,
                                        AlienSpeed.VERY_FAST,
                                        2,
                                        AlienCharacter.ROTATOR,
                                        1,
                                        Collections.singletonList(PowerUpType.MISSILE_LASER))
                        )
                );
                break;
            case 34:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.ASTEROIDS,
                                        DirectionalResettableConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ASTEROID)
                                                .energy(2)
                                                .speed(AlienSpeed.MEDIUM)
                                                .angle(DOWNWARDS)
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
                                                                                .spawnedPowerUpTypes(
                                                                                        NO_POWER_UPS)
                                                                                .spawnedAlienConfig(SplitterConfig
                                                                                        .builder()
                                                                                        .alienConfigs(
                                                                                                Arrays.asList(
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI - QUARTER_PI, AlienSpeed.MEDIUM),
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI + QUARTER_PI, AlienSpeed.MEDIUM)))
                                                                                        .build())
                                                                                .build())
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                ),
                                new SubWaveNoPathConfig(
                                        SubWaveRule.ASTEROIDS,
                                        DirectionalResettableConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ASTEROID)
                                                .energy(2)
                                                .speed(AlienSpeed.VERY_FAST)
                                                .angle(DOWNWARDS)
                                                .spinningConfig(
                                                        SpinningBySpeedConfig
                                                                .builder()
                                                                .build()
                                                )
                                                .explosionConfig(
                                                        SpawningExplosionConfig
                                                                .builder()
                                                                .spawnConfig(
                                                                        SpawnOnDemandConfig
                                                                                .builder()
                                                                                .spawnedPowerUpTypes(
                                                                                        NO_POWER_UPS)
                                                                                .spawnedAlienConfig(SplitterConfig
                                                                                        .builder()
                                                                                        .alienConfigs(
                                                                                                Arrays.asList(
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI - QUARTER_PI, AlienSpeed.VERY_FAST),
                                                                                                        createMiniDirectionalAsteroid(-HALF_PI + QUARTER_PI, AlienSpeed.VERY_FAST)))
                                                                                        .build())
                                                                                .build())
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                )
                        )
                );
                break;

            case 35:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.TEAR_DROP_ATTACK,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.HELPER_BASES)
                                )
                        )
                );
                break;


            case 36:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_LEFT_AND_RIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.CLOUD)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(0.5f)
                                                        .build())
                                                .energy(10)
                                                .missileConfig(
                                                        MissileMultiFiringConfig
                                                                .builder()
                                                                .missileConfigs(
                                                                        Arrays.asList(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.RAIN)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(0.75f)
                                                                                        .build(),
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.ROTATED)
                                                                                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(5f)
                                                                                        .build()))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY))
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.STAGGERED_LEFT_AND_RIGHT_REVERSED,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.CLOUD)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(0.5f)
                                                        .build())
                                                .energy(10)
                                                .missileConfig(
                                                        MissileMultiFiringConfig
                                                                .builder()
                                                                .missileConfigs(
                                                                        Arrays.asList(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.RAIN)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(2f)
                                                                                        .build(),
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.ROTATED)
                                                                                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                                                                                        .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                        .missileFrequency(8f)
                                                                                        .build()))
                                                                .build())
                                                .build(),
                                        Arrays.asList(PowerUpType.SHIELD, PowerUpType.HELPER_BASES, PowerUpType.MISSILE_LASER))
                        )
                );
                break;


            case 37:
            case 38:

                /**
                 * Aliens rise on both sides of screen and the cross to the other side.
                 * Repeat in reverse direction. Very few gaps between aliens to attack.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SINGLE_ARC_NO_REPEAT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                .missileFrequency(1f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSOVER_EXIT_ATTACK_REVERSE,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.LADY_BIRD)
                                                .angledToPath(true)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.HELPER_BASES)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SINGLE_ARC_NO_REPEAT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.INSECT_MOTHERSHIP)
                                                .energy(10)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                .missileFrequency(1f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_LASER)
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSOVER_EXIT_ATTACK,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.LADY_BIRD)
                                                .angledToPath(true)
                                                .energy(2)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.FAST)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.HELPER_BASES)
                                )
                        )
                );
                break;

            case 39:

                // asteroid field
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createAsteroidField(Arrays.asList(
                                        PowerUpType.MISSILE_GUIDED,
                                        PowerUpType.MISSILE_FAST,
                                        PowerUpType.MISSILE_LASER))
                        )
                );
                break;

            case 40:
            case 41:
            case 42:

                /*
                 * Single hunter bat that starts from top of screen.
                 * Aims fireballs at base.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.MIDDLE_TOP,
                                        HunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BATTY)
                                                .energy(10)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                .missileFrequency(2f)
                                                                .build())
                                                .speed(AlienSpeed.VERY_FAST)
                                                .boundaries(
                                                        BoundariesConfig
                                                                .builder()
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.SHIELD))
                        )
                );
                /*
                 * Two hunter bats that start from left and right of screen.
                 * Each bat flies within the left or right hand-side of screen.
                 * Both aim fireballs at base.
                 */
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWaveNoPathConfig(
                                        SubWaveRule.MIDDLE_LEFT,
                                        HunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BATTY)
                                                .energy(10)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                .missileFrequency(2f)
                                                                .build())
                                                .speed(AlienSpeed.VERY_FAST)
                                                .boundaries(
                                                        BoundariesConfig
                                                                .builder()
                                                                .maxX(SCREEN_MID_X)
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.HELPER_BASES)),
                                new SubWaveNoPathConfig(
                                        SubWaveRule.MIDDLE_RIGHT,
                                        HunterConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BATTY)
                                                .energy(10)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.ROTATED)
                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                .missileFrequency(2f)
                                                                .build())
                                                .speed(AlienSpeed.VERY_FAST)
                                                .boundaries(
                                                        BoundariesConfig
                                                                .builder()
                                                                .minX(SCREEN_MID_X)
                                                                .build()
                                                )
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY))
                        )
                );
                break;

            case 43:
            case 44:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SLIDE_FORMATION_LEFT_RIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SLIDE_FORMATION_RIGHT_LEFT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.SLIDE_FORMATION_LEFT_RIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                ),
                                new SubWavePathConfig(
                                        SubWavePathRule.SLIDE_FORMATION_RIGHT_LEFT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.MINION)
                                                .angledToPath(true)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                break;
            case 45:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.FIGURE_OF_EIGHT,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_PARALLEL)
                                )
                        )
                );
                break;

            case 46:
            case 47:

                final MissileConfig fortySevenMissileConfig = MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(AlienMissileSpeed.FAST)
                        .missileFrequency(15f)
                        .build();
                final AlienRowConfig fishRowConfig = AlienRowConfig
                        .builder()
                        .alienConfig(
                                PathConfig
                                        .builder()
                                        .alienCharacter(AlienCharacter.FISH)
                                        .energy(2)
                                        .missileConfig(fortySevenMissileConfig)
                                        .build())
                        .powerUps(Collections.singletonList(PowerUpType.SHIELD))
                        .build();
                final AlienRowConfig pilotRowConfig = AlienRowConfig
                        .builder()
                        .alienConfig(
                                PathConfig
                                        .builder()
                                        .alienCharacter(AlienCharacter.PILOT)
                                        .energy(2)
                                        .missileConfig(fortySevenMissileConfig)
                                        .build())
                        .powerUps(Collections.singletonList(PowerUpType.MISSILE_GUIDED))
                        .build();
                final AlienRowConfig eyeRowConfig = AlienRowConfig
                        .builder()
                        .alienConfig(
                                PathConfig
                                        .builder()
                                        .alienCharacter(AlienCharacter.ALL_SEEING_EYE)
                                        .energy(2)
                                        .missileConfig(fortySevenMissileConfig)
                                        .build())
                        .powerUps(Collections.singletonList(PowerUpType.MISSILE_LASER))
                        .build();

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createDescendingDelayedRowSubWave(
                                        Path.BOUNCE_DOWN_AND_UP,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {fishRowConfig, fishRowConfig, pilotRowConfig, pilotRowConfig, eyeRowConfig, eyeRowConfig},
                                        6,
                                        0f,
                                        true,
                                        false
                                )
                        )
                );

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createDescendingOffsetRowsSubWave(
                                        Path.BOUNCE_DOWN_AND_UP,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {eyeRowConfig, eyeRowConfig, pilotRowConfig, pilotRowConfig, fishRowConfig , fishRowConfig},
                                        6,
                                        0f,
                                        true,
                                        false)
                        )
                );

                // old wave 22
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createDescendingDelayedRowSubWave(
                                        Path.BOUNCE_DOWN_AND_UP,
                                        PathSpeed.NORMAL,
                                        new AlienRowConfig[] {
                                                AlienRowConfig
                                                        .builder()
                                                        .alienConfig(
                                                                PathConfig
                                                                        .builder()
                                                                        .alienCharacter(AlienCharacter.SKULL)
                                                                        .energy(3)
                                                                        .missileConfig(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.LASER)
                                                                                        .missileSpeed(AlienMissileSpeed.FAST)
                                                                                        .missileFrequency(6.5f)
                                                                                        .build())
                                                                        .build()
                                                        )
                                                        .powerUps(
                                                                Collections.singletonList(PowerUpType.SHIELD)
                                                        )
                                                        .build(),
                                                AlienRowConfig
                                                        .builder()
                                                        .alienConfig(
                                                                PathConfig
                                                                        .builder()
                                                                        .alienCharacter(AlienCharacter.GOBBY)
                                                                        .energy(3)
                                                                        .missileConfig(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.LASER)
                                                                                        .missileSpeed(AlienMissileSpeed.FAST)
                                                                                        .missileFrequency(6.5f)
                                                                                        .build())
                                                                        .build()
                                                        )
                                                        .powerUps(
                                                                Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                                        )
                                                        .build(),
                                                AlienRowConfig
                                                        .builder()
                                                        .alienConfig(
                                                                PathConfig
                                                                        .builder()
                                                                        .alienCharacter(AlienCharacter.MINION)
                                                                        .energy(3)
                                                                        .missileConfig(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.LASER)
                                                                                        .missileSpeed(AlienMissileSpeed.FAST)
                                                                                        .missileFrequency(6.5f)
                                                                                        .build())
                                                                        .build()
                                                        )
                                                        .powerUps(
                                                                Collections.singletonList(PowerUpType.HELPER_BASES)
                                                        )
                                                        .build(),
                                                AlienRowConfig
                                                        .builder()
                                                        .alienConfig(
                                                                PathConfig
                                                                        .builder()
                                                                        .alienCharacter(AlienCharacter.CIRCUIT)
                                                                        .energy(3)
                                                                        .missileConfig(
                                                                                MissileFiringConfig
                                                                                        .builder()
                                                                                        .missileType(AlienMissileType.DOWNWARDS)
                                                                                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                                                                                        .missileSpeed(AlienMissileSpeed.FAST)
                                                                                        .missileFrequency(6.5f)
                                                                                        .build())
                                                                        .spinningConfig(
                                                                                SpinningFixedAngularConfig
                                                                                        .builder()
                                                                                        .angularSpeed(70)
                                                                                        .build())
                                                                        .build()
                                                        )
                                                        .powerUps(
                                                                Collections.singletonList(PowerUpType.MISSILE_PARALLEL)
                                                        )
                                                        .build()
                                        },
                                        6,
                                        0f,
                                        true,
                                        false)
                        )
                );
                break;

            case 48:

                // asteroid field
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createAsteroidField(Arrays.asList(
                                        PowerUpType.MISSILE_GUIDED,
                                        PowerUpType.MISSILE_FAST,
                                        PowerUpType.MISSILE_LASER))
                        )
                );
                break;

            case 49:

                // alien test wave to check alien animation
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_TRIANGULAR,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.SPINNER_PULSE_GREEN)
                                                .energy(4)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                break;

            case 50:
            case 51:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createDriftingWave(
                                        AlienCharacter.SPACE_STATION,
                                        2,
                                        AlienSpeed.SLOW,
                                        Arrays.asList(
                                                PowerUpType.MISSILE_GUIDED,
                                                PowerUpType.MISSILE_FAST,
                                                PowerUpType.MISSILE_LASER),
                                        SpinningBySpeedConfig
                                                .builder()
                                                .build(),
                                        MissileFiringConfig
                                                .builder()
                                                .missileType(AlienMissileType.ROTATED)
                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                .missileFrequency(10f)
                                                .build())
                        )
                );
                break;

            case 52:
            case 53:
            case 54:
            case 55:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.CROSSING_STEP_ATTACK,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.OCTOPUS)
                                                .energy(1)
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_BLAST)
                                )
                        )
                );
                break;

            case 56:
            case 57:
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                createDriftingWave(
                                        AlienCharacter.SPACE_STATION,
                                        2,
                                        AlienSpeed.SLOW,
                                        Arrays.asList(
                                                PowerUpType.MISSILE_GUIDED,
                                                PowerUpType.MISSILE_FAST,
                                                PowerUpType.MISSILE_LASER),
                                        SpinningBySpeedConfig
                                                .builder()
                                                .build(),
                                        MissileFiringConfig
                                                .builder()
                                                .missileType(AlienMissileType.ROTATED)
                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                .missileFrequency(10f)
                                                .build())
                        )
                );
                break;

            case 58:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.BOMBER_RUN,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.ZOGG)
                                                .energy(10)
                                                .spawnConfig(
                                                        SpawningLimitedAlienConfig
                                                                .builder()
                                                                .spawnedAlienConfig(
                                                                        FollowableHunterConfig
                                                                                .builder()
                                                                                .alienCharacter(AlienCharacter.DRAGON_HEAD)
                                                                                .energy(10)
                                                                                .speed(AlienSpeed.VERY_FAST)
                                                                                .missileConfig(
                                                                                        MissileFiringConfig
                                                                                                .builder()
                                                                                                .missileType(AlienMissileType.ROTATED)
                                                                                                .missileCharacter(AlienMissileCharacter.FIREBALL)
                                                                                                .missileSpeed(AlienMissileSpeed.VERY_FAST)
                                                                                                .missileFrequency(6.5f)
                                                                                                .build())
                                                                                .numberOfFollowers(5)
                                                                                .followerConfig(
                                                                                        FollowerConfig
                                                                                                .builder()
                                                                                                .alienCharacter(AlienCharacter.DRAGON_BODY)
                                                                                                .energy(1)
                                                                                                .speed(AlienSpeed.VERY_FAST)
                                                                                                .build())
                                                                                .followerPowerUps(
                                                                                        Arrays.asList(
                                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                                PowerUpType.MISSILE_PARALLEL,
                                                                                                PowerUpType.MISSILE_SPRAY))
                                                                                .boundaries(
                                                                                        BoundariesConfig.builder().build())
                                                                                .build())
                                                                .minimumSpawnDelayTime(5f)
                                                                .maximumAdditionalRandomSpawnDelayTime(2f)
                                                                .maximumActiveSpawnedAliens(2)
                                                                .limitedCharacter(AlienCharacter.DRAGON_HEAD)// max 2 dragon heads at any time
                                                                .spwanedPowerUpTypes(
                                                                        Arrays.asList(
                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                PowerUpType.MISSILE_FAST,
                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.MISSILE_SPRAY)
                                )
                        )
                );
                break;

            case 59:
            case 60:

                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BIG_BOSS)
                                                .energy(1)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(1.5f)
                                                        .build())
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );
                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                new SubWavePathConfig(
                                        SubWavePathRule.WAVE_MOTHERSHIP,
                                        PathConfig
                                                .builder()
                                                .alienCharacter(AlienCharacter.BIG_BOSS)
                                                .energy(1)
                                                .explosionConfig(MultiExplosionConfig.builder()
                                                        .numberOfExplosions(10)
                                                        .maximumExplosionStartTime(1.5f)
                                                        .explosionConfig(SpawningExplosionConfig
                                                                .builder()
                                                                .spawnConfig(
                                                                        SpawningAlienConfig
                                                                                .builder()
                                                                                .spawnedAlienConfig(
                                                                                        DirectionalDestroyableConfig
                                                                                                .builder()
                                                                                                .alienCharacter(AlienCharacter.INSECT)
                                                                                                .energy(1)
                                                                                                .angle(DOWNWARDS)
                                                                                                .missileConfig(
                                                                                                        MissileFiringConfig
                                                                                                                .builder()
                                                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                                                .missileFrequency(1.5f)
                                                                                                                .build())
                                                                                                .speed(AlienSpeed.SLOW)
                                                                                                .build())
                                                                                .minimumSpawnDelayTime(0.5f)
                                                                                .maximumAdditionalRandomSpawnDelayTime(0.25f)
                                                                                .spwanedPowerUpTypes(
                                                                                        Arrays.asList(
                                                                                                PowerUpType.MISSILE_GUIDED,
                                                                                                PowerUpType.MISSILE_FAST,
                                                                                                PowerUpType.MISSILE_PARALLEL))
                                                                                .build())
                                                                .build())
                                                        .build())
                                                .missileConfig(
                                                        MissileFiringConfig
                                                                .builder()
                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                .missileFrequency(6.5f)
                                                                .build())
                                                .build(),
                                        Collections.singletonList(PowerUpType.LIFE)
                                )
                        )
                );


                // placeholders until real waves are created


                subWaves.add(
                        createSubWave(
                                SubWaveRepeatMode.REPEAT_UNTIL_DESTROYED,
                                flatten(
                                        new SubWavePathConfig[]{
                                                new SubWavePathConfig(
                                                        SubWavePathRule.BELL_CURVE,
                                                        PathConfig
                                                                .builder()
                                                                .alienCharacter(AlienCharacter.ZOGG)
                                                                .energy(1)
                                                                .missileConfig(
                                                                        MissileFiringConfig
                                                                                .builder()
                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                .missileFrequency(6.5f)
                                                                                .build())
                                                                .build(),
                                                        Collections.singletonList(PowerUpType.HELPER_BASES)
                                                ),
                                                new SubWavePathConfig(
                                                        SubWavePathRule.LOOPER_ATTACK,
                                                        PathConfig
                                                                .builder()
                                                                .alienCharacter(AlienCharacter.SKULL)
                                                                .energy(1)
                                                                .missileConfig(
                                                                        MissileFiringConfig
                                                                                .builder()
                                                                                .missileType(AlienMissileType.DOWNWARDS)
                                                                                .missileCharacter(AlienMissileCharacter.LASER)
                                                                                .missileSpeed(AlienMissileSpeed.MEDIUM)
                                                                                .missileFrequency(6.5f)
                                                                                .build())
                                                                .build(),
                                                        Collections.singletonList(PowerUpType.MISSILE_GUIDED)
                                                )
                                        },
                                        asteroidHorizontalRows(
                                                10,
                                                AlienSpeed.SLOW,
                                                2,
                                                Collections.singletonList(PowerUpType.MISSILE_LASER))
                                ))
                );
                break;

            default:
                throw new IllegalArgumentException("Wave not recognised '" + wave + "'.");

        }

        return subWaves;
    }


    /**
     * Creates a list of aliens on a path using the supplied wave property.
     */
    private SubWave createSubWave(
            final SubWaveRepeatMode repeatedMode,
            final SubWaveConfig... subWaveConfigs) {

        List<IAlien> aliens = new ArrayList<>();

        for (SubWaveConfig config : subWaveConfigs) {

            switch (config.getType()) {
                case PATH:
                    SubWavePathConfig pathConfig = (SubWavePathConfig) config;
                    aliens.addAll(creationUtils.createPathAlienSubWave(pathConfig));
                    break;
                case NO_PATH:
                    SubWaveNoPathConfig noPathConfig = (SubWaveNoPathConfig) config;
                    aliens.addAll(creationUtils.createNoPathAlienSubWave(noPathConfig));
                    break;
                default:
                    throw new GalaxyForceException("Unknown sub-wave config type: " + config.getType().name());
            }
        }

        /*
         * Reverse order of aliens.
         *
         * Collision detection routines are required to iterate through aliens
         * in reverse so aliens on top are hit first.
         *
         * Any subsequent explosions on these aliens must also display on top so
         * reversed order is important for how aliens sprites are displayed.
         */
        List<IAlien> reversedAlienList = reverseAliens(aliens);

        // create subwave from list of aliens and set whether wave should repeat
        // until all destroyed
        return new SubWave(reversedAlienList, repeatedMode);
    }

    /**
     * Reverse order of aliens.
     * <p>
     * Collision detection routines are required to iterate through aliens in
     * reverse so aliens on top are hit first.
     * <p>
     * Any subsequent explosions on these aliens must also display on top so
     * reversed order is important for how aliens sprites are displayed.
     */
    private static List<IAlien> reverseAliens(List<IAlien> aliens) {
        List<IAlien> reversedAlienList = new ArrayList<>();

        for (IAlien eachAlien : Reversed.reversed(aliens)) {
            reversedAlienList.add(eachAlien);
        }

        return reversedAlienList;
    }
}
