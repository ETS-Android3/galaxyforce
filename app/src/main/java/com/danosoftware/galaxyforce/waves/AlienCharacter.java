package com.danosoftware.galaxyforce.waves;

import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

/**
 * Enum representing the different types of alien that can be created
 * (with their associated animations).
 * <p>
 * Used by AlienFactory to create instances of the wanted alien.
 */
public enum AlienCharacter {

    // special null character reserved for multi-explosions that spawn immediately as exploding aliens
    NULL(
            new ISpriteIdentifier[]{GameSpriteIdentifier.NULL},
            new ISpriteIdentifier[]{GameSpriteIdentifier.NULL},
            0f),

    BLOCK(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BLOCK_01,
                    GameSpriteIdentifier.BLOCK_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BLOCK_01,
                    GameSpriteIdentifier.BLOCK_02},
            0.5f
    ),

    FROGGER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FROGGER,
                    GameSpriteIdentifier.FROGGER_TALL,
                    GameSpriteIdentifier.FROGGER,
                    GameSpriteIdentifier.FROGGER_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FROGGER_HIT,
                    GameSpriteIdentifier.FROGGER_TALL_HIT,
                    GameSpriteIdentifier.FROGGER_HIT,
                    GameSpriteIdentifier.FROGGER_WIDE_HIT},
            0.25f
    ),

    CYCLONE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CYCLONE_01,
                    GameSpriteIdentifier.CYCLONE_02,
                    GameSpriteIdentifier.CYCLONE_03,
                    GameSpriteIdentifier.CYCLONE_04,
                    GameSpriteIdentifier.CYCLONE_05},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CYCLONE_01_HIT,
                    GameSpriteIdentifier.CYCLONE_02_HIT,
                    GameSpriteIdentifier.CYCLONE_03_HIT,
                    GameSpriteIdentifier.CYCLONE_04_HIT,
                    GameSpriteIdentifier.CYCLONE_05_HIT},
            0.25f
    ),

    DEVIL(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DEVIL,
                    GameSpriteIdentifier.DEVIL_DOWN,
                    GameSpriteIdentifier.DEVIL,
                    GameSpriteIdentifier.DEVIL_UP},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DEVIL_HIT,
                    GameSpriteIdentifier.DEVIL_DOWN_HIT,
                    GameSpriteIdentifier.DEVIL_HIT,
                    GameSpriteIdentifier.DEVIL_UP_HIT},
            0.25f
    ),

    GREMLIN(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GREMLIN_01,
                    GameSpriteIdentifier.GREMLIN_02,
                    GameSpriteIdentifier.GREMLIN_03,
                    GameSpriteIdentifier.GREMLIN_04,
                    GameSpriteIdentifier.GREMLIN_03,
                    GameSpriteIdentifier.GREMLIN_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GREMLIN_01_HIT,
                    GameSpriteIdentifier.GREMLIN_02_HIT,
                    GameSpriteIdentifier.GREMLIN_03_HIT,
                    GameSpriteIdentifier.GREMLIN_04_HIT,
                    GameSpriteIdentifier.GREMLIN_03_HIT,
                    GameSpriteIdentifier.GREMLIN_02_HIT},
            0.25f
    ),

    SPARKLE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPARKLE,
                    GameSpriteIdentifier.SPARKLE_TALL,
                    GameSpriteIdentifier.SPARKLE,
                    GameSpriteIdentifier.SPARKLE_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPARKLE_HIT,
                    GameSpriteIdentifier.SPARKLE_TALL_HIT,
                    GameSpriteIdentifier.SPARKLE_HIT,
                    GameSpriteIdentifier.SPARKLE_WIDE_HIT},
            0.25f
    ),

    PINCER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PINCER,
                    GameSpriteIdentifier.PINCER_LEFT,
                    GameSpriteIdentifier.PINCER,
                    GameSpriteIdentifier.PINCER_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PINCER_HIT,
                    GameSpriteIdentifier.PINCER_LEFT_HIT,
                    GameSpriteIdentifier.PINCER_HIT,
                    GameSpriteIdentifier.PINCER_RIGHT_HIT},
            0.25f
    ),

    SPECTATOR(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPECTATOR,
                    GameSpriteIdentifier.SPECTATOR_UP,
                    GameSpriteIdentifier.SPECTATOR,
                    GameSpriteIdentifier.SPECTATOR_DOWN},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPECTATOR_HIT,
                    GameSpriteIdentifier.SPECTATOR_UP_HIT,
                    GameSpriteIdentifier.SPECTATOR_HIT,
                    GameSpriteIdentifier.SPECTATOR_DOWN_HIT},
            0.25f
    ),

    SQUASHER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SQUASHER,
                    GameSpriteIdentifier.SQUASHER_WIDE,
                    GameSpriteIdentifier.SQUASHER,
                    GameSpriteIdentifier.SQUASHER_TALL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SQUASHER_HIT,
                    GameSpriteIdentifier.SQUASHER_WIDE_HIT,
                    GameSpriteIdentifier.SQUASHER_HIT,
                    GameSpriteIdentifier.SQUASHER_TALL_HIT},
            0.25f
    ),

    TINY_DANCER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.TINY_DANCER,
                    GameSpriteIdentifier.TINY_DANCER_LEFT,
                    GameSpriteIdentifier.TINY_DANCER,
                    GameSpriteIdentifier.TINY_DANCER_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.TINY_DANCER_HIT,
                    GameSpriteIdentifier.TINY_DANCER_LEFT_HIT,
                    GameSpriteIdentifier.TINY_DANCER_HIT,
                    GameSpriteIdentifier.TINY_DANCER_RIGHT_HIT},
            0.25f
    ),

    WILD_STYLE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WILD_STYLE,
                    GameSpriteIdentifier.WILD_STYLE_FLAT,
                    GameSpriteIdentifier.WILD_STYLE,
                    GameSpriteIdentifier.WILD_STYLE_TALL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WILD_STYLE_HIT,
                    GameSpriteIdentifier.WILD_STYLE_FLAT_HIT,
                    GameSpriteIdentifier.WILD_STYLE_HIT,
                    GameSpriteIdentifier.WILD_STYLE_TALL_HIT},
            0.25f
    ),

    CONFUSER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CONFUSER,
                    GameSpriteIdentifier.CONFUSER_FLAT,
                    GameSpriteIdentifier.CONFUSER,
                    GameSpriteIdentifier.CONFUSER_TALL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CONFUSER_HIT,
                    GameSpriteIdentifier.CONFUSER_FLAT_HIT,
                    GameSpriteIdentifier.CONFUSER_HIT,
                    GameSpriteIdentifier.CONFUSER_TALL_HIT},
            0.25f
    ),

    SAUCER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SAUCER,
                    GameSpriteIdentifier.SAUCER_RIGHT,
                    GameSpriteIdentifier.SAUCER,
                    GameSpriteIdentifier.SAUCER_LEFT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SAUCER_HIT,
                    GameSpriteIdentifier.SAUCER_RIGHT_HIT,
                    GameSpriteIdentifier.SAUCER_HIT,
                    GameSpriteIdentifier.SAUCER_LEFT_HIT},
            0.25f
    ),

    PINKO(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PINKO,
                    GameSpriteIdentifier.PINKO_TALL,
                    GameSpriteIdentifier.PINKO,
                    GameSpriteIdentifier.PINKO_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PINKO_HIT,
                    GameSpriteIdentifier.PINKO_TALL_HIT,
                    GameSpriteIdentifier.PINKO_HIT,
                    GameSpriteIdentifier.PINKO_WIDE_HIT},
            0.25f
    ),

    PISTON(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PISTON,
                    GameSpriteIdentifier.PISTON_TALL,
                    GameSpriteIdentifier.PISTON,
                    GameSpriteIdentifier.PISTON_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PISTON_HIT,
                    GameSpriteIdentifier.PISTON_TALL_HIT,
                    GameSpriteIdentifier.PISTON_HIT,
                    GameSpriteIdentifier.PISTON_WIDE_HIT},
            0.25f
    ),

    PURPLE_MEANIE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PURPLE_MEANIE_01,
                    GameSpriteIdentifier.PURPLE_MEANIE_02,
                    GameSpriteIdentifier.PURPLE_MEANIE_03,
                    GameSpriteIdentifier.PURPLE_MEANIE_04,
                    GameSpriteIdentifier.PURPLE_MEANIE_05},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PURPLE_MEANIE_01_HIT,
                    GameSpriteIdentifier.PURPLE_MEANIE_02_HIT,
                    GameSpriteIdentifier.PURPLE_MEANIE_03_HIT,
                    GameSpriteIdentifier.PURPLE_MEANIE_04_HIT,
                    GameSpriteIdentifier.PURPLE_MEANIE_05_HIT},
            0.25f
    ),

    PAD(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PAD_01,
                    GameSpriteIdentifier.PAD_02,
                    GameSpriteIdentifier.PAD_03,
                    GameSpriteIdentifier.PAD_04,
                    GameSpriteIdentifier.PAD_05},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PAD_01_HIT,
                    GameSpriteIdentifier.PAD_02_HIT,
                    GameSpriteIdentifier.PAD_03_HIT,
                    GameSpriteIdentifier.PAD_04_HIT,
                    GameSpriteIdentifier.PAD_05_HIT},
            0.25f
    ),

    GHOST(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GHOST,
                    GameSpriteIdentifier.GHOST_LEFT,
                    GameSpriteIdentifier.GHOST,
                    GameSpriteIdentifier.GHOST_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GHOST_HIT,
                    GameSpriteIdentifier.GHOST_LEFT_HIT,
                    GameSpriteIdentifier.GHOST_HIT,
                    GameSpriteIdentifier.GHOST_RIGHT_HIT},
            0.25f
    ),

    HOPPER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.HOPPER,
                    GameSpriteIdentifier.HOPPER_LEFT,
                    GameSpriteIdentifier.HOPPER,
                    GameSpriteIdentifier.HOPPER_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.HOPPER_HIT,
                    GameSpriteIdentifier.HOPPER_LEFT_HIT,
                    GameSpriteIdentifier.HOPPER_HIT,
                    GameSpriteIdentifier.HOPPER_RIGHT_HIT},
            0.25f
    ),

    AMOEBA(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.AMOEBA,
                    GameSpriteIdentifier.AMOEBA_LEFT,
                    GameSpriteIdentifier.AMOEBA,
                    GameSpriteIdentifier.AMOEBA_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.AMOEBA_HIT,
                    GameSpriteIdentifier.AMOEBA_LEFT_HIT,
                    GameSpriteIdentifier.AMOEBA_HIT,
                    GameSpriteIdentifier.AMOEBA_RIGHT_HIT},
            0.25f
    ),

    CHEEKY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CHEEKY,
                    GameSpriteIdentifier.CHEEKY_LEFT,
                    GameSpriteIdentifier.CHEEKY,
                    GameSpriteIdentifier.CHEEKY_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CHEEKY_HIT,
                    GameSpriteIdentifier.CHEEKY_LEFT_HIT,
                    GameSpriteIdentifier.CHEEKY_HIT,
                    GameSpriteIdentifier.CHEEKY_RIGHT_HIT},
            0.25f
    ),

    FOXY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FOXY,
                    GameSpriteIdentifier.FOXY_TALL,
                    GameSpriteIdentifier.FOXY,
                    GameSpriteIdentifier.FOXY_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FOXY_HIT,
                    GameSpriteIdentifier.FOXY_TALL_HIT,
                    GameSpriteIdentifier.FOXY_HIT,
                    GameSpriteIdentifier.FOXY_WIDE_HIT},
            0.25f
    ),

    FIGHTER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FIGHTER,
                    GameSpriteIdentifier.FIGHTER_TALL,
                    GameSpriteIdentifier.FIGHTER,
                    GameSpriteIdentifier.FIGHTER_WIDE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FIGHTER_HIT,
                    GameSpriteIdentifier.FIGHTER_TALL_HIT,
                    GameSpriteIdentifier.FIGHTER_HIT,
                    GameSpriteIdentifier.FIGHTER_WIDE_HIT},
            0.25f
    ),

    RIPLEY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.RIPLEY,
                    GameSpriteIdentifier.RIPLEY_LEFT,
                    GameSpriteIdentifier.RIPLEY,
                    GameSpriteIdentifier.RIPLEY_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.RIPLEY_HIT,
                    GameSpriteIdentifier.RIPLEY_LEFT_HIT,
                    GameSpriteIdentifier.RIPLEY_HIT,
                    GameSpriteIdentifier.RIPLEY_RIGHT_HIT},
            0.25f
    ),

    WALKER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WALKER,
                    GameSpriteIdentifier.WALKER_RIGHT,
                    GameSpriteIdentifier.WALKER_LEFT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WALKER_HIT,
                    GameSpriteIdentifier.WALKER_RIGHT_HIT,
                    GameSpriteIdentifier.WALKER_LEFT_HIT},
            0.25f
    ),

    CHARLIE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CHARLIE,
                    GameSpriteIdentifier.CHARLIE_LEFT,
                    GameSpriteIdentifier.CHARLIE,
                    GameSpriteIdentifier.CHARLIE_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CHARLIE_HIT,
                    GameSpriteIdentifier.CHARLIE_LEFT_HIT,
                    GameSpriteIdentifier.CHARLIE_HIT,
                    GameSpriteIdentifier.CHARLIE_RIGHT_HIT},
            0.25f
    ),

    ALL_SEEING_EYE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ALL_SEEING_EYE,
                    GameSpriteIdentifier.ALL_SEEING_EYE_LEFT,
                    GameSpriteIdentifier.ALL_SEEING_EYE,
                    GameSpriteIdentifier.ALL_SEEING_EYE_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ALL_SEEING_EYE_HIT,
                    GameSpriteIdentifier.ALL_SEEING_EYE_LEFT_HIT,
                    GameSpriteIdentifier.ALL_SEEING_EYE_HIT,
                    GameSpriteIdentifier.ALL_SEEING_EYE_RIGHT_HIT},
            0.25f
    ),

    JUMPER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.JUMPER,
                    GameSpriteIdentifier.JUMPER_UP,
                    GameSpriteIdentifier.JUMPER,
                    GameSpriteIdentifier.JUMPER_DOWN},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.JUMPER_HIT,
                    GameSpriteIdentifier.JUMPER_UP_HIT,
                    GameSpriteIdentifier.JUMPER_HIT,
                    GameSpriteIdentifier.JUMPER_DOWN_HIT},
            0.25f
    ),

    ROTATOR(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ROTATOR_01,
                    GameSpriteIdentifier.ROTATOR_02,
                    GameSpriteIdentifier.ROTATOR_03,
                    GameSpriteIdentifier.ROTATOR_04,
                    GameSpriteIdentifier.ROTATOR_05,},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ROTATOR_01_HIT,
                    GameSpriteIdentifier.ROTATOR_02_HIT,
                    GameSpriteIdentifier.ROTATOR_03_HIT,
                    GameSpriteIdentifier.ROTATOR_04_HIT,
                    GameSpriteIdentifier.ROTATOR_05_HIT,},
            0.15f
    ),

    WHIRLPOOL(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WHIRLPOOL_01,
                    GameSpriteIdentifier.WHIRLPOOL_02,
                    GameSpriteIdentifier.WHIRLPOOL_03,
                    GameSpriteIdentifier.WHIRLPOOL_04,
                    GameSpriteIdentifier.WHIRLPOOL_05,},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.WHIRLPOOL_01_HIT,
                    GameSpriteIdentifier.WHIRLPOOL_02_HIT,
                    GameSpriteIdentifier.WHIRLPOOL_03_HIT,
                    GameSpriteIdentifier.WHIRLPOOL_04_HIT,
                    GameSpriteIdentifier.WHIRLPOOL_05_HIT,},
            0.15f
    ),

    BATTLE_DROID(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BATTLE_DROID_01,
                    GameSpriteIdentifier.BATTLE_DROID_02,
                    GameSpriteIdentifier.BATTLE_DROID_01,
                    GameSpriteIdentifier.BATTLE_DROID_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BATTLE_DROID_01_HIT,
                    GameSpriteIdentifier.BATTLE_DROID_02_HIT,
                    GameSpriteIdentifier.BATTLE_DROID_01_HIT,
                    GameSpriteIdentifier.BATTLE_DROID_03_HIT},
            0.20f
    ),

    FISH(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FISH_01,
                    GameSpriteIdentifier.FISH_02,
                    GameSpriteIdentifier.FISH_01,
                    GameSpriteIdentifier.FISH_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FISH_01_HIT,
                    GameSpriteIdentifier.FISH_02_HIT,
                    GameSpriteIdentifier.FISH_01_HIT,
                    GameSpriteIdentifier.FISH_03_HIT},
            0.20f
    ),

    LEMMING(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.LEMMING_01,
                    GameSpriteIdentifier.LEMMING_02,
                    GameSpriteIdentifier.LEMMING_01,
                    GameSpriteIdentifier.LEMMING_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.LEMMING_01_HIT,
                    GameSpriteIdentifier.LEMMING_02_HIT,
                    GameSpriteIdentifier.LEMMING_01_HIT,
                    GameSpriteIdentifier.LEMMING_03_HIT},
            0.20f
    ),

    SQUEEZE_BOX(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SQUEEZE_BOX_01,
                    GameSpriteIdentifier.SQUEEZE_BOX_02,
                    GameSpriteIdentifier.SQUEEZE_BOX_01,
                    GameSpriteIdentifier.SQUEEZE_BOX_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SQUEEZE_BOX_01_HIT,
                    GameSpriteIdentifier.SQUEEZE_BOX_02_HIT,
                    GameSpriteIdentifier.SQUEEZE_BOX_01_HIT,
                    GameSpriteIdentifier.SQUEEZE_BOX_03_HIT},
            0.20f
    ),

    YELLOW_BEARD(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.YELLOW_BEARD_01,
                    GameSpriteIdentifier.YELLOW_BEARD_02,
                    GameSpriteIdentifier.YELLOW_BEARD_01,
                    GameSpriteIdentifier.YELLOW_BEARD_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.YELLOW_BEARD_01_HIT,
                    GameSpriteIdentifier.YELLOW_BEARD_02_HIT,
                    GameSpriteIdentifier.YELLOW_BEARD_01_HIT,
                    GameSpriteIdentifier.YELLOW_BEARD_03_HIT},
            0.20f
    ),

    PILOT(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PILOT_01,
                    GameSpriteIdentifier.PILOT_02,
                    GameSpriteIdentifier.PILOT_01,
                    GameSpriteIdentifier.PILOT_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.PILOT_01_HIT,
                    GameSpriteIdentifier.PILOT_02_HIT,
                    GameSpriteIdentifier.PILOT_01_HIT,
                    GameSpriteIdentifier.PILOT_03_HIT},
            0.20f
    ),

    ARACNOID(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ARACNOID_01,
                    GameSpriteIdentifier.ARACNOID_02,
                    GameSpriteIdentifier.ARACNOID_01,
                    GameSpriteIdentifier.ARACNOID_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ARACNOID_01_HIT,
                    GameSpriteIdentifier.ARACNOID_02_HIT,
                    GameSpriteIdentifier.ARACNOID_01_HIT,
                    GameSpriteIdentifier.ARACNOID_03_HIT},
            0.20f
    ),

    CRAB(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CRAB_01,
                    GameSpriteIdentifier.CRAB_02,
                    GameSpriteIdentifier.CRAB_01,
                    GameSpriteIdentifier.CRAB_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CRAB_01_HIT,
                    GameSpriteIdentifier.CRAB_02_HIT,
                    GameSpriteIdentifier.CRAB_01_HIT,
                    GameSpriteIdentifier.CRAB_03_HIT},
            0.20f
    ),

    BEAR(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BEAR_01,
                    GameSpriteIdentifier.BEAR_02,
                    GameSpriteIdentifier.BEAR_01,
                    GameSpriteIdentifier.BEAR_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BEAR_01_HIT,
                    GameSpriteIdentifier.BEAR_02_HIT,
                    GameSpriteIdentifier.BEAR_01_HIT,
                    GameSpriteIdentifier.BEAR_03_HIT},
            0.20f
    ),

    DINO(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DINO_01,
                    GameSpriteIdentifier.DINO_02,
                    GameSpriteIdentifier.DINO_03,
                    GameSpriteIdentifier.DINO_04,
                    GameSpriteIdentifier.DINO_03,
                    GameSpriteIdentifier.DINO_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DINO_01_HIT,
                    GameSpriteIdentifier.DINO_02_HIT,
                    GameSpriteIdentifier.DINO_03_HIT,
                    GameSpriteIdentifier.DINO_04_HIT,
                    GameSpriteIdentifier.DINO_03_HIT,
                    GameSpriteIdentifier.DINO_02_HIT},
            0.20f
    ),

    FRISBIE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FRISBIE_01,
                    GameSpriteIdentifier.FRISBIE_02,
                    GameSpriteIdentifier.FRISBIE_03,
                    GameSpriteIdentifier.FRISBIE_04,
                    GameSpriteIdentifier.FRISBIE_05},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.FRISBIE_01_HIT,
                    GameSpriteIdentifier.FRISBIE_02_HIT,
                    GameSpriteIdentifier.FRISBIE_03_HIT,
                    GameSpriteIdentifier.FRISBIE_04_HIT,
                    GameSpriteIdentifier.FRISBIE_05_HIT},
            0.20f
    ),

    JOKER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.JOKER_01,
                    GameSpriteIdentifier.JOKER_02,
                    GameSpriteIdentifier.JOKER_03,
                    GameSpriteIdentifier.JOKER_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.JOKER_01_HIT,
                    GameSpriteIdentifier.JOKER_02_HIT,
                    GameSpriteIdentifier.JOKER_03_HIT,
                    GameSpriteIdentifier.JOKER_02_HIT},
            0.20f
    ),

    // octopus alien with flapping legs
    OCTOPUS(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.OCTOPUS_LEFT,
                    GameSpriteIdentifier.OCTOPUS_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.OCTOPUS_LEFT_HIT,
                    GameSpriteIdentifier.OCTOPUS_RIGHT_HIT},
            0.5f),

    MINION(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MINION},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MINION_HIT},
            0f),

    // green spinning balls
    SPINNER_GREEN(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPINNER_GREEN_01,
                    GameSpriteIdentifier.SPINNER_GREEN_02,
                    GameSpriteIdentifier.SPINNER_GREEN_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPINNER_GREEN_01_HIT,
                    GameSpriteIdentifier.SPINNER_GREEN_02_HIT,
                    GameSpriteIdentifier.SPINNER_GREEN_03_HIT},
            0.2f),

    // green pulsating spinning balls
    SPINNER_PULSE_GREEN(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_01,
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_02,
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_01_HIT,
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_02_HIT,
                    GameSpriteIdentifier.SPINNER_PULSE_GREEN_03_HIT},
            0.2f),

    // molecule - made from joined spheres
    MOLECULE(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MOLECULE},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MOLECULE_HIT},
            0f),

    // big boss alien with claws
    BIG_BOSS(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BIG_BOSS_01,
                    GameSpriteIdentifier.BIG_BOSS_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BIG_BOSS_01_HIT,
                    GameSpriteIdentifier.BIG_BOSS_02_HIT},
            0.25f),

    // flapping lady bird
    LADY_BIRD(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.LADYBIRD_01,
                    GameSpriteIdentifier.LADYBIRD_02},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.LADYBIRD_01_HIT,
                    GameSpriteIdentifier.LADYBIRD_02_HIT},
            0.25f),


    // rocky asteroid
    ASTEROID(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ASTEROID},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ASTEROID_HIT},
            0f),

    // small rocky asteroid
    ASTEROID_MINI(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ASTEROID_MINI},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ASTEROID_MINI_HIT},
            0f),

    // dragon
    DRAGON_HEAD(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DRAGON_HEAD_LEFT,
                    GameSpriteIdentifier.DRAGON_HEAD_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DRAGON_HEAD_LEFT_HIT,
                    GameSpriteIdentifier.DRAGON_HEAD_RIGHT_HIT},
            0.5f),

    DRAGON_BODY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DRAGON_BODY},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DRAGON_BODY_HIT},
            0.5f),

    // skull
    SKULL(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SKULL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SKULL_HIT},
            0f),

    // droid
    DROID(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DROID},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DROID_HIT},
            0f),

    // big flapping mothership alien
    INSECT_MOTHERSHIP(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MOTHER_BUZZER_WINGS_DOWN,
                    GameSpriteIdentifier.MOTHER_BUZZER_WINGS_UP},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.MOTHER_BUZZER_WINGS_DOWN_HIT,
                    GameSpriteIdentifier.MOTHER_BUZZER_WINGS_UP_HIT},
            0.5f),

    // flying insects
    INSECT(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.INSECT_WINGS_UP,
                    GameSpriteIdentifier.INSECT_WINGS_DOWN},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.INSECT_WINGS_UP,
                    GameSpriteIdentifier.INSECT_WINGS_DOWN},
            0.5f),

    // gobby alien
    GOBBY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GOBBY_LEFT,
                    GameSpriteIdentifier.GOBBY_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.GOBBY_LEFT_HIT,
                    GameSpriteIdentifier.GOBBY_RIGHT_HIT},
            0.5f),

    // flapping book
    BOOK(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOOK_FLAT,
                    GameSpriteIdentifier.BOOK_BEND,
                    GameSpriteIdentifier.BOOK_CLOSED,
                    GameSpriteIdentifier.BOOK_BEND},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOOK_FLAT_HIT,
                    GameSpriteIdentifier.BOOK_BEND_HIT,
                    GameSpriteIdentifier.BOOK_CLOSED_HIT,
                    GameSpriteIdentifier.BOOK_BEND_HIT},
            0.2f),

    // big blobby alien
    ZOGG(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ZOGG_UP,
                    GameSpriteIdentifier.ZOGG_DOWN},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.ZOGG_UP_HIT,
                    GameSpriteIdentifier.ZOGG_DOWN_HIT},
            0.5f),

    // bomb
    BOMB(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOMB_01,
                    GameSpriteIdentifier.BOMB_02,
                    GameSpriteIdentifier.BOMB_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOMB_01_HIT,
                    GameSpriteIdentifier.BOMB_02_HIT,
                    GameSpriteIdentifier.BOMB_03_HIT},
            0.5f),

    // circuit board
    CIRCUIT(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CIRCUIT_LEFT,
                    GameSpriteIdentifier.CIRCUIT_RIGHT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CIRCUIT_LEFT_HIT,
                    GameSpriteIdentifier.CIRCUIT_RIGHT_HIT},
            0.5f),

    // flying fire bat
    BATTY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BATTY_FLAP_DOWN,
                    GameSpriteIdentifier.BATTY_FLAP_UP},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BATTY_FLAP_DOWN_HIT,
                    GameSpriteIdentifier.BATTY_FLAP_UP_HIT},
            0.2f),

    // circuit board
    CLOUD(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CLOUD},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CLOUD_HIT},
            0f,
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.CLOUD_EXPLODE_01,
                    GameSpriteIdentifier.CLOUD_EXPLODE_02,
                    GameSpriteIdentifier.CLOUD_EXPLODE_03,
                    GameSpriteIdentifier.CLOUD_EXPLODE_04,
                    GameSpriteIdentifier.CLOUD_EXPLODE_05},
            0.075f),

    // green bouncy alien
    BOUNCER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOUNCER_IN,
                    GameSpriteIdentifier.BOUNCER_OUT},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BOUNCER_IN_HIT,
                    GameSpriteIdentifier.BOUNCER_OUT_HIT},
            0.2f),

    // alien with droopy tendrils
    DROOPY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DROOPY_UP,
                    GameSpriteIdentifier.DROOPY_DOWN},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.DROOPY_UP_HIT,
                    GameSpriteIdentifier.DROOPY_DOWN_HIT},
            0.2f),

    // evil yellow cat
//    BAD_CAT(
//            new ISpriteIdentifier[]{
//                    GameSpriteIdentifier.BAD_CAT},
//            new ISpriteIdentifier[]{
//                    GameSpriteIdentifier.BAD_CAT_HIT},
//            0f),
    BAD_CAT(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_WIDE,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_TALL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_HIT,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_WIDE_HIT,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_HIT,
                    GameSpriteIdentifier.BAD_CAT_SQUEEZE_TALL_HIT},
            0.25f),

    SMOKEY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SMOKEY_FLAME_NONE,
                    GameSpriteIdentifier.SMOKEY_FLAME_SMALL,
                    GameSpriteIdentifier.SMOKEY_FLAME_BIG,
                    GameSpriteIdentifier.SMOKEY_FLAME_SMALL},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SMOKEY_FLAME_NONE_HIT,
                    GameSpriteIdentifier.SMOKEY_FLAME_SMALL_HIT,
                    GameSpriteIdentifier.SMOKEY_FLAME_BIG_HIT,
                    GameSpriteIdentifier.SMOKEY_FLAME_SMALL_HIT},
            0.2f),

    TELLY(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.TELLY_FUZZ_ONE,
                    GameSpriteIdentifier.TELLY_FUZZ_TWO},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.TELLY_FUZZ_ONE_HIT,
                    GameSpriteIdentifier.TELLY_FUZZ_TWO_HIT},
            0.3f),

    HELMET(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.HELMET},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.HELMET_HIT},
            0f),

    EGG(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.EGG_CRACK_01,
                    GameSpriteIdentifier.EGG_CRACK_02,
                    GameSpriteIdentifier.EGG_CRACK_03,
                    GameSpriteIdentifier.EGG_CRACK_02,
                    GameSpriteIdentifier.EGG_CRACK_03,
                    GameSpriteIdentifier.EGG_CRACK_02,
                    GameSpriteIdentifier.EGG_CRACK_03,
                    GameSpriteIdentifier.EGG_CRACK_02,
                    GameSpriteIdentifier.EGG_CRACK_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.EGG_CRACK_01_HIT,
                    GameSpriteIdentifier.EGG_CRACK_02_HIT,
                    GameSpriteIdentifier.EGG_CRACK_03_HIT,
                    GameSpriteIdentifier.EGG_CRACK_02_HIT,
                    GameSpriteIdentifier.EGG_CRACK_03_HIT,
                    GameSpriteIdentifier.EGG_CRACK_02_HIT,
                    GameSpriteIdentifier.EGG_CRACK_03_HIT,
                    GameSpriteIdentifier.EGG_CRACK_02_HIT,
                    GameSpriteIdentifier.EGG_CRACK_03_HIT},
            0.25f,
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.EGG_CRACK_04,
                    GameSpriteIdentifier.EGG_CRACK_05,
                    GameSpriteIdentifier.EGG_CRACK_06,
                    GameSpriteIdentifier.EGG_CRACK_07},
            0.075f),

    BARRIER(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BARRIER_01,
                    GameSpriteIdentifier.BARRIER_02,
                    GameSpriteIdentifier.BARRIER_03},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.BARRIER_01,
                    GameSpriteIdentifier.BARRIER_02,
                    GameSpriteIdentifier.BARRIER_03},
            0.75f),

    SPACE_STATION(
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPACE_STATION},
            new ISpriteIdentifier[]{
                    GameSpriteIdentifier.SPACE_STATION_HIT},
            0f);

    private static final float DEFAULT_EXPLOSION_FRAME_DURATION = 0.075f;

    // alien animation frames
    private final ISpriteIdentifier[] animationFrames;

    // alien animation frames when hit - to be in sync with normal animation frames
    private final ISpriteIdentifier[] hitAnimationFrames;

    // time between frame transitions
    private final float frameDuration;

    // alien animation frames when exploding
    private final ISpriteIdentifier[] explosionAnimationFrames;
    private final float explosionFrameDuration;

    AlienCharacter(
            final ISpriteIdentifier[] animationFrames,
            final ISpriteIdentifier[] hitAnimationFrames,
            final float frameDuration) {
        this(
                animationFrames,
                hitAnimationFrames,
                frameDuration,
                new ISpriteIdentifier[]{
                        GameSpriteIdentifier.EXPLODE_BIG_01,
                        GameSpriteIdentifier.EXPLODE_BIG_02,
                        GameSpriteIdentifier.EXPLODE_BIG_03,
                        GameSpriteIdentifier.EXPLODE_BIG_04,
                        GameSpriteIdentifier.EXPLODE_BIG_05},
                DEFAULT_EXPLOSION_FRAME_DURATION);
    }

    AlienCharacter(
            final ISpriteIdentifier[] animationFrames,
            final ISpriteIdentifier[] hitAnimationFrames,
            final float frameDuration,
            final ISpriteIdentifier[] explosionAnimationFrames,
            final float explosionFrameDuration) {
        this.animationFrames = animationFrames;
        this.hitAnimationFrames = hitAnimationFrames;
        this.frameDuration = frameDuration;
        this.explosionAnimationFrames = explosionAnimationFrames;
        this.explosionFrameDuration = explosionFrameDuration;
    }

    public Animation getAnimation() {
        return new Animation(frameDuration, animationFrames);
    }

    public Animation getHitAnimation() {
        return new Animation(frameDuration, hitAnimationFrames);
    }

    public Animation getExplosionAnimation() {
        return new Animation(explosionFrameDuration, explosionAnimationFrames);
    }
}
