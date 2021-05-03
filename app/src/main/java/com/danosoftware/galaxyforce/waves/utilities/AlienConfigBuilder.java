package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.ExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.exploding.SpawningExplosionConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spawning.SpawnOnDemandConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningBySpeedConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningFixedAngularConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.BoundariesConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalDestroyableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalResettableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.ExplodingConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowableHunterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.FollowerConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.HunterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.PathConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.SplitterConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.StaticConfig;

import java.util.Arrays;
import java.util.List;

import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.DOWNWARDS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.HALF_PI;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.NO_POWER_UPS;
import static com.danosoftware.galaxyforce.waves.utilities.WaveFactoryHelper.QUARTER_PI;

public class AlienConfigBuilder {

    /**
     * Create missile config for character
     */
    public static MissileConfig missileConfig(
            AlienCharacter character,
            AlienMissileSpeed speed,
            Float missileFrequency) {

        switch (character) {
                // downwards laser missile
            case NULL:
            case BLOCK:
            case PINKO:
            case PISTON:
            case PURPLE_MEANIE:
            case PAD:
            case HOPPER:
            case AMOEBA:
            case CHEEKY:
            case FIGHTER:
            case RIPLEY:
            case WALKER:
            case ALL_SEEING_EYE:
            case JUMPER:
            case FROGGER:
            case WHIRLPOOL:
            case FISH:
            case LEMMING:
            case SQUEEZE_BOX:
            case YELLOW_BEARD:
            case PILOT:
            case ARACNOID:
            case CRAB:
            case DINO:
            case FRISBIE:
            case OCTOPUS:
            case MINION:
            case SPINNER_GREEN:
            case SPINNER_PULSE_GREEN:
            case MOLECULE:
            case BIG_BOSS:
            case LADY_BIRD:
            case ASTEROID:
            case ASTEROID_MINI:
            case DRAGON_BODY:
            case BABY_DRAGON_BODY:
            case SKULL:
            case DROID:
            case INSECT:
            case GOBBY:
            case BOOK:
            case ZOGG:
            case BOMB:
            case CLOUD:
            case BOUNCER:
            case DROOPY:
            case BAD_CAT:
            case SMOKEY:
            case TELLY:
            case HELMET:
            case EGG:
            case BARRIER:
            case CYCLONE:
            case SPACE_STATION:
            case DEVIL:
            case GREMLIN:
            case PINCER:
            case SPARKLE:
            case SPECTATOR:
            case SQUASHER:
            case TINY_DANCER:
            case WILD_STYLE:
            case CONFUSER:
            case SAUCER:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
                // downwards lightning missile
            case CIRCUIT:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
                // rotated downwards laser missile (will not fire upwards)
            case GHOST:
            case FOXY:
            case CHARLIE:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.ROTATED_DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
                // rotated laser missile (any direction)
            case BATTLE_DROID:
            case JOKER:
            case BEAR:
            case ROTATOR:
            case INSECT_MOTHERSHIP:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.ROTATED)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
            // rotated fireball missile
            case DRAGON_HEAD:
            case BABY_DRAGON_HEAD:
            case BATTY:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.ROTATED)
                        .missileCharacter(AlienMissileCharacter.FIREBALL)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
            default:
                throw new GalaxyForceException("Can not create missile config for unknown character: " + character);
        }
    }

    /**
     * Create energy levels for character
     */
    public static Integer energy(
            AlienCharacter character) {

        switch (character) {
            case NULL:
            case PISTON:
            case PURPLE_MEANIE:
            case PAD:
            case HOPPER:
            case AMOEBA:
            case CHEEKY:
            case FIGHTER:
            case RIPLEY:
            case WALKER:
            case JUMPER:
            case ROTATOR:
            case FROGGER:
            case WHIRLPOOL:
            case BATTLE_DROID:
            case SQUEEZE_BOX:
            case ARACNOID:
            case CRAB:
            case BEAR:
            case DINO:
            case JOKER:
            case OCTOPUS:
            case MINION:
            case MOLECULE:
            case BIG_BOSS:
            case ASTEROID_MINI:
            case DRAGON_BODY:
            case BABY_DRAGON_BODY:
            case SKULL:
            case DROID:
            case INSECT:
            case GOBBY:
            case BOMB:
            case CLOUD:
            case BOUNCER:
            case DROOPY:
            case BAD_CAT:
            case SMOKEY:
            case TELLY:
            case HELMET:
            case EGG:
            case SPACE_STATION:
            case CYCLONE:
            case DEVIL:
            case GREMLIN:
            case SPARKLE:
            case SPECTATOR:
            case SQUASHER:
            case TINY_DANCER:
            case WILD_STYLE:
                return 1;
            case CIRCUIT:
            case PILOT:
            case FRISBIE:
            case ZOGG:
            case LEMMING:
            case YELLOW_BEARD:
            case FISH:
            case ALL_SEEING_EYE:
            case PINKO:
            case GHOST:
            case ASTEROID:
            case SPINNER_GREEN:
            case SPINNER_PULSE_GREEN:
            case FOXY:
            case BABY_DRAGON_HEAD:
            case CHARLIE:
                return 2;
            case PINCER:
            case CONFUSER:
                return 3;
            case INSECT_MOTHERSHIP:
            case BATTY:
            case SAUCER:
            case LADY_BIRD:
                return 10;
            case BOOK:
                return 15;
            case DRAGON_HEAD:
                return 20;
            case BARRIER:
            case BLOCK:
                return Integer.MAX_VALUE;
            default:
                throw new GalaxyForceException("Can not create energy for unknown character: " + character);
        }
    }

    /**
     * Is character angled to path?
     */
    public static Boolean isAngledToPath(
            AlienCharacter character) {

        switch (character) {
            case LADY_BIRD:
            case FIGHTER:
            case MINION:
                return true;
            default:
                return false;
        }

    }

    /**
     * Create alien config for character with missiles
     */
    public static PathConfig alienConfig(AlienCharacter character,
                                         AlienMissileSpeed speed,
                                         Float missileFrequency) {

        return alienConfigBuilder(character, speed, missileFrequency)
                .build();
    }

    /**
     * Create alien config for character without missiles
     */
    public static PathConfig alienConfig(AlienCharacter character) {

        return alienConfigBuilder(character)
                .build();
    }

    /**
     * Create alien config builder for character with missiles
     */
    private static PathConfig.PathConfigBuilder alienConfigBuilder(AlienCharacter character,
                                                                   AlienMissileSpeed speed,
                                                                   Float missileFrequency) {

        final Integer energy = energy(character);
        final MissileConfig missileConfig = missileConfig(character, speed, missileFrequency);
        final Boolean isAngledToPath = isAngledToPath(character);

        switch (character) {
            case NULL:
            case BLOCK:
            case PINKO:
            case PISTON:
            case PURPLE_MEANIE:
            case PAD:
            case GHOST:
            case HOPPER:
            case AMOEBA:
            case CHEEKY:
            case FOXY:
            case FIGHTER:
            case RIPLEY:
            case WALKER:
            case CHARLIE:
            case ALL_SEEING_EYE:
            case JUMPER:
            case ROTATOR:
            case FROGGER:
            case WHIRLPOOL:
            case BATTLE_DROID:
            case FISH:
            case LEMMING:
            case SQUEEZE_BOX:
            case YELLOW_BEARD:
            case PILOT:
            case ARACNOID:
            case CRAB:
            case BEAR:
            case DINO:
            case FRISBIE:
            case JOKER:
            case OCTOPUS:
            case MINION:
            case SPINNER_GREEN:
            case MOLECULE:
            case BIG_BOSS:
            case LADY_BIRD:
            case ASTEROID:
            case ASTEROID_MINI:
            case DRAGON_HEAD:
            case DRAGON_BODY:
            case BABY_DRAGON_HEAD:
            case BABY_DRAGON_BODY:
            case SKULL:
            case DROID:
            case INSECT_MOTHERSHIP:
            case INSECT:
            case GOBBY:
            case BOOK:
            case ZOGG:
            case BOMB:
            case BATTY:
            case CLOUD:
            case BOUNCER:
            case DROOPY:
            case BAD_CAT:
            case SMOKEY:
            case TELLY:
            case HELMET:
            case EGG:
            case BARRIER:
            case SPACE_STATION:
            case CYCLONE:
            case DEVIL:
            case GREMLIN:
            case PINCER:
            case SPARKLE:
            case SPECTATOR:
            case SQUASHER:
            case TINY_DANCER:
            case WILD_STYLE:
            case CONFUSER:
            case SAUCER:
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .missileConfig(missileConfig)
                        .angledToPath(isAngledToPath);
            case CIRCUIT:
            case SPINNER_PULSE_GREEN:
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .spinningConfig(
                                SpinningFixedAngularConfig
                                        .builder()
                                        .angularSpeed(70)
                                        .build())
                        .missileConfig(missileConfig)
                        .angledToPath(isAngledToPath);
            default:
                throw new GalaxyForceException("Can not create alien config for unknown character: " + character);
        }
    }

    /**
     * Create alien config builder for character without missiles
     */
    private static PathConfig.PathConfigBuilder alienConfigBuilder(AlienCharacter character) {

        final Integer energy = energy(character);
        final Boolean isAngledToPath = isAngledToPath(character);

        switch (character) {
            case NULL:
            case BLOCK:
            case PINKO:
            case PISTON:
            case PURPLE_MEANIE:
            case PAD:
            case GHOST:
            case HOPPER:
            case AMOEBA:
            case CHEEKY:
            case FOXY:
            case FIGHTER:
            case RIPLEY:
            case WALKER:
            case CHARLIE:
            case ALL_SEEING_EYE:
            case JUMPER:
            case ROTATOR:
            case FROGGER:
            case WHIRLPOOL:
            case BATTLE_DROID:
            case FISH:
            case LEMMING:
            case SQUEEZE_BOX:
            case YELLOW_BEARD:
            case PILOT:
            case ARACNOID:
            case CRAB:
            case BEAR:
            case DINO:
            case FRISBIE:
            case JOKER:
            case OCTOPUS:
            case MINION:
            case SPINNER_GREEN:
            case MOLECULE:
            case BIG_BOSS:
            case LADY_BIRD:
            case ASTEROID:
            case ASTEROID_MINI:
            case DRAGON_HEAD:
            case DRAGON_BODY:
            case BABY_DRAGON_HEAD:
            case BABY_DRAGON_BODY:
            case SKULL:
            case DROID:
            case INSECT_MOTHERSHIP:
            case INSECT:
            case GOBBY:
            case BOOK:
            case ZOGG:
            case BOMB:
            case BATTY:
            case CLOUD:
            case BOUNCER:
            case DROOPY:
            case BAD_CAT:
            case SMOKEY:
            case TELLY:
            case HELMET:
            case EGG:
            case BARRIER:
            case SPACE_STATION:
            case CYCLONE:
            case DEVIL:
            case GREMLIN:
            case PINCER:
            case SPARKLE:
            case SPECTATOR:
            case SQUASHER:
            case TINY_DANCER:
            case WILD_STYLE:
            case CONFUSER:
            case SAUCER:
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .angledToPath(isAngledToPath);
            case CIRCUIT:
            case SPINNER_PULSE_GREEN:
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .spinningConfig(
                                SpinningFixedAngularConfig
                                        .builder()
                                        .angularSpeed(70)
                                        .build())
                        .angledToPath(isAngledToPath);
            default:
                throw new GalaxyForceException("Can not create alien config for unknown character: " + character);
        }
    }

    /**
     * Create directional alien config for character with missiles
     */
    public static DirectionalDestroyableConfig directionalAlienConfig(
            AlienCharacter character,
            Float directionalAngle,
            AlienSpeed alienSpeed,
            AlienMissileSpeed missileSpeed,
            Float missileFrequency) {

        final Integer energy = energy(character);
        final MissileConfig missileConfig = missileConfig(character, missileSpeed, missileFrequency);

        return DirectionalDestroyableConfig
                    .builder()
                    .alienCharacter(character)
                    .energy(energy)
                    .speed(alienSpeed)
                    .angle(directionalAngle)
                    .missileConfig(missileConfig)
                    .build();
    }

    /**
     * Create directional alien config for character without missiles
     */
    public static DirectionalDestroyableConfig directionalAlienConfig(
            AlienCharacter character,
            Float directionalAngle,
            AlienSpeed alienSpeed) {

        final Integer energy = energy(character);

        return DirectionalDestroyableConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(alienSpeed)
                .angle(directionalAngle)
                .build();
    }

    /**
     * Create alien config row with missiles and power-ups
     */
    public static AlienRowConfig alienRowConfig(
            AlienCharacter character,
            AlienMissileSpeed speed,
            Float missileFrequency,
            List<PowerUpType> powerUps) {

        return AlienRowConfig
                .builder()
                .alienConfig(
                        alienConfig(
                                character,
                                speed,
                                missileFrequency
                        )
                )
                .powerUps(powerUps)
                .build();
    }

    /**
     * Create hunter alien config for character with missiles
     */
    public static HunterConfig hunterAlienConfig(
            AlienCharacter character,
            AlienSpeed alienSpeed,
            BoundariesConfig boundariesConfig,
            AlienMissileSpeed missileSpeed,
            Float missileFrequency) {

        final Integer energy = energy(character);
        final MissileConfig missileConfig = missileConfig(character, missileSpeed, missileFrequency);

        return HunterConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(alienSpeed)
                .boundaries(boundariesConfig)
                .missileConfig(missileConfig)
                .build();
    }

    /**
     * Create hunter alien config for character without missiles
     */
    public static HunterConfig hunterAlienConfig(
            AlienCharacter character,
            AlienSpeed alienSpeed,
            BoundariesConfig boundariesConfig) {

        final Integer energy = energy(character);

        return HunterConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(alienSpeed)
                .boundaries(boundariesConfig)
                .build();
    }

    /**
     * Create aliens that descend from screen and split when destroyed.
     * e.g. asteroids that split into smaller asteroids
     */
    public static DirectionalResettableConfig fallingSpinningSplittingResettableConfig(
            AlienCharacter character,
            AlienSpeed alienSpeed,
            AlienCharacter spawnCharacter
    ) {
        final Integer energy = energy(character);

        return DirectionalResettableConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(alienSpeed)
                .angle(DOWNWARDS)
                .spinningConfig(
                        SpinningBySpeedConfig
                                .builder()
                                .build())
                .explosionConfig(
                        splitOnExplodeConfig(alienSpeed, spawnCharacter)
                )
                .build();
    }

    /**
     * Create aliens that descend from screen and split when destroyed.
     * e.g. asteroids that split into smaller asteroids
     */
    public static DirectionalDestroyableConfig fallingSpinningSplittingDirectionalConfig(
            AlienCharacter character,
            AlienSpeed alienSpeed,
            AlienCharacter spawnCharacter) {

        final Integer energy = energy(character);

        return DirectionalDestroyableConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(alienSpeed)
                .angle(DOWNWARDS)
                .spinningConfig(
                        SpinningBySpeedConfig
                                .builder()
                                .build())
                .explosionConfig(
                        splitOnExplodeConfig(alienSpeed, spawnCharacter)
                )
                .build();
    }

    /**
     * Create explode config that splits and spawns new aliens.
     * e.g. asteroids that split into smaller asteroids
     */
    public static ExplosionConfig splitOnExplodeConfig(
            AlienSpeed alienSpeed,
            AlienCharacter spawnCharacter
    ) {
        return SpawningExplosionConfig
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
                                                        angledSpinningDirectionalConfig(
                                                                spawnCharacter,
                                                                -HALF_PI - QUARTER_PI,
                                                                alienSpeed),
                                                        angledSpinningDirectionalConfig(
                                                                spawnCharacter,
                                                                -HALF_PI + QUARTER_PI,
                                                                alienSpeed)))
                                        .build())
                                .build())
                .build();
    }

    /**
     * Create alien that will travel at set angle until destroyed.
     * Often used to spawn smaller aliens split from original alien.
     * e.g. mini-asteroids split from big asteroid.
     */
    public static AlienConfig angledSpinningDirectionalConfig(
            AlienCharacter character,
            final float angle,
            final AlienSpeed speed) {

        final Integer energy = energy(character);

        return DirectionalDestroyableConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(speed)
                .spinningConfig(
                        SpinningBySpeedConfig
                                .builder()
                                .build())
                .angle(angle)
                .build();
    }

    public static AlienConfig spawningPathAlienConfig(
            final AlienCharacter character,
            final SpawnConfig spawnConfig
    ) {

        return alienConfigBuilder(character)
                .spawnConfig(spawnConfig)
                .build();
    }

    public static FollowableHunterConfig.FollowableHunterConfigBuilder followableHunterConfigBuilder(
            final AlienCharacter hunterCharacter,
            final AlienSpeed speed,
            final AlienMissileSpeed missileSpeed,
            final Float missileFrequency,
            final BoundariesConfig boundariesConfig,
            final int numberOfFollowers,
            final FollowerConfig followerConfig,
            final List<PowerUpType> followerPowerUps) {

        final Integer energy = energy(hunterCharacter);
        final MissileConfig missileConfig = missileConfig(hunterCharacter, missileSpeed, missileFrequency);

        return FollowableHunterConfig
                .builder()
                .alienCharacter(hunterCharacter)
                .energy(energy)
                .speed(speed)
                .missileConfig(missileConfig)
                .boundaries(boundariesConfig)
                .numberOfFollowers(numberOfFollowers)
                .followerConfig(followerConfig)
                .followerPowerUps(followerPowerUps);
    }

    public static FollowableHunterConfig.FollowableHunterConfigBuilder followableHunterConfigBuilder(
            final AlienCharacter hunterCharacter,
            final AlienSpeed speed,
            final BoundariesConfig boundariesConfig,
            final int numberOfFollowers,
            final FollowerConfig followerConfig,
            final List<PowerUpType> followerPowerUps) {

        final Integer energy = energy(hunterCharacter);

        return FollowableHunterConfig
                .builder()
                .alienCharacter(hunterCharacter)
                .energy(energy)
                .speed(speed)
                .boundaries(boundariesConfig)
                .numberOfFollowers(numberOfFollowers)
                .followerConfig(followerConfig)
                .followerPowerUps(followerPowerUps);
    }

    public static FollowerConfig.FollowerConfigBuilder followerConfigBuilder(
            AlienCharacter character,
            AlienSpeed speed) {

        final Integer energy = energy(character);

        return FollowerConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .speed(speed);
    }

    public static AlienConfig spawningStaticAlienConfig(
            final AlienCharacter character,
            final SpawnConfig spawnConfig
    ) {

        final Integer energy = energy(character);

        return StaticConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .spawnConfig(spawnConfig)
                .build();
    }

    public static AlienConfig explodingAlienConfig(
            final AlienCharacter character,
            final AlienMissileCharacter missileCharacter,
            final Float explosionTime
    ) {
        final Integer energy = energy(character);

        return ExplodingConfig
                .builder()
                .alienCharacter(character)
                .energy(energy)
                .explosionTime(explosionTime)
                .explodingMissileCharacter(missileCharacter)
                .build();
    }
}