package com.danosoftware.galaxyforce.waves.utilities;

import com.danosoftware.galaxyforce.enumerations.AlienMissileCharacter;
import com.danosoftware.galaxyforce.enumerations.AlienMissileSpeed;
import com.danosoftware.galaxyforce.enumerations.AlienMissileType;
import com.danosoftware.galaxyforce.enumerations.AlienSpeed;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.waves.AlienCharacter;
import com.danosoftware.galaxyforce.waves.config.aliens.AlienConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.missiles.MissileFiringConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.spinning.SpinningFixedAngularConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.DirectionalDestroyableConfig;
import com.danosoftware.galaxyforce.waves.config.aliens.types.PathConfig;

import java.util.List;

public class AlienConfigBuilder {

    /**
     * Create missile config for character
     */
    public static MissileConfig missileConfig(
            AlienCharacter character,
            AlienMissileSpeed speed,
            Float missileFrequency) {

        switch (character) {
            case NULL:
            case BLOCK:
            case PINKO:
            case PISTON:
            case PURPLE_MEANIE:
            case PAD:
            case HOPPER:
            case AMOEBA:
            case CHEEKY:
            case FOXY:
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
            case DRAGON_HEAD:
            case DRAGON_BODY:
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
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LASER)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
            case CIRCUIT:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.DOWNWARDS)
                        .missileCharacter(AlienMissileCharacter.LIGHTNING)
                        .missileSpeed(speed)
                        .missileFrequency(missileFrequency)
                        .build();
            case CHARLIE:
            case GHOST:
            case BATTLE_DROID:
            case JOKER:
            case BEAR:
            case ROTATOR:
                return MissileFiringConfig
                        .builder()
                        .missileType(AlienMissileType.ROTATED)
                        .missileCharacter(AlienMissileCharacter.LASER)
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
            case SQUEEZE_BOX:
            case ARACNOID:
            case CRAB:
            case BEAR:
            case DINO:
            case JOKER:
            case OCTOPUS:
            case MINION:
            case SPINNER_GREEN:
            case SPINNER_PULSE_GREEN:
            case MOLECULE:
            case BIG_BOSS:
            case LADY_BIRD:
            case ASTEROID:
            case ASTEROID_MINI:
            case DRAGON_HEAD:
            case DRAGON_BODY:
            case SKULL:
            case DROID:
            case INSECT:
            case GOBBY:
            case BOOK:
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
            case SPACE_STATION:
                return 1;
            case CIRCUIT:
            case PILOT:
            case FRISBIE:
            case ZOGG:
            case LEMMING:
            case YELLOW_BEARD:
                return 2;
            case INSECT_MOTHERSHIP:
                return 10;
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
    public static Boolean angledToPath(
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
    public static AlienConfig alienConfig(AlienCharacter character,
                                          AlienMissileSpeed speed,
                                          Float missileFrequency) {

        final Integer energy = energy(character);
        final MissileConfig missileConfig = missileConfig(character, speed, missileFrequency);
        final Boolean angledToPath = angledToPath(character);

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
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .missileConfig(missileConfig)
                        .angledToPath(angledToPath)
                        .build();
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
                        .angledToPath(angledToPath)
                        .build();
            default:
                throw new GalaxyForceException("Can not create alien config for unknown character: " + character);
        }
    }

    /**
     * Create alien config for character without missiles
     */
    public static AlienConfig alienConfig(AlienCharacter character) {

        final Integer energy = energy(character);
        final Boolean angledToPath = angledToPath(character);

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
                return PathConfig
                        .builder()
                        .alienCharacter(character)
                        .energy(energy)
                        .angledToPath(angledToPath)
                        .build();
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
                        .angledToPath(angledToPath)
                        .build();
            default:
                throw new GalaxyForceException("Can not create alien config for unknown character: " + character);
        }
    }

    /**
     * Create directional alien config for character with missiles
     */
    public static AlienConfig directionalAlienConfig(
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
    public static AlienConfig directionalAlienConfig(
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

        final Integer energy = energy(character);
        final MissileConfig missileConfig = missileConfig(character, speed, missileFrequency);

        return AlienRowConfig
                .builder()
                .alienConfig(
                        PathConfig
                                .builder()
                                .alienCharacter(character)
                                .energy(energy)
                                .missileConfig(missileConfig)
                                .build())
                .powerUps(powerUps)
                .build();
    }
}