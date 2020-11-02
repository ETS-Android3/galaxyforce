package com.danosoftware.galaxyforce.sprites.properties;

import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.textures.Texture;

public enum GameSpriteIdentifier implements ISpriteIdentifier {

    // Block
    BLOCK_01("block01",2),
    BLOCK_02("block02",2),

    // Foxy
    FOXY("foxy01",8),
    FOXY_TALL("foxy02", 8),
    FOXY_WIDE("foxy03", 8),
    FOXY_HIT("foxy01Hit",8),
    FOXY_TALL_HIT("foxy02Hit", 8),
    FOXY_WIDE_HIT("foxy03Hit", 8),

    // Pinko
    PINKO("pinko01",6),
    PINKO_TALL("pinko02", 6),
    PINKO_WIDE("pinko03", 6),
    PINKO_HIT("pinko01Hit",6),
    PINKO_TALL_HIT("pinko02Hit", 6),
    PINKO_WIDE_HIT("pinko03Hit", 6),

    // Piston
    PISTON("piston01",8),
    PISTON_TALL("piston02", 8),
    PISTON_WIDE("piston03", 8),
    PISTON_HIT("piston01Hit",8),
    PISTON_TALL_HIT("piston02Hit", 8),
    PISTON_WIDE_HIT("piston03Hit", 8),

    // Hopper
    HOPPER("hopper01",8),
    HOPPER_LEFT("hopper02", 8),
    HOPPER_RIGHT("hopper03", 8),
    HOPPER_HIT("hopper01Hit",8),
    HOPPER_LEFT_HIT("hopper02Hit", 8),
    HOPPER_RIGHT_HIT("hopper03Hit", 8),

    // Ghost
    GHOST("ghost01",8),
    GHOST_LEFT("ghost02", 8),
    GHOST_RIGHT("ghost03", 8),
    GHOST_HIT("ghost01Hit",8),
    GHOST_LEFT_HIT("ghost02Hit", 8),
    GHOST_RIGHT_HIT("ghost03Hit", 8),

    // Anomeba
    AMOEBA("amoeba01",8),
    AMOEBA_LEFT("amoeba02", 8),
    AMOEBA_RIGHT("amoeba03", 8),
    AMOEBA_HIT("amoeba01Hit",8),
    AMOEBA_LEFT_HIT("amoeba02Hit", 8),
    AMOEBA_RIGHT_HIT("amoeba03Hit", 8),

    // Cheeky
    CHEEKY("cheeky01",6),
    CHEEKY_LEFT("cheeky02", 6),
    CHEEKY_RIGHT("cheeky03", 6),
    CHEEKY_HIT("cheeky01Hit",6),
    CHEEKY_LEFT_HIT("cheeky02Hit", 6),
    CHEEKY_RIGHT_HIT("cheeky03Hit", 6),

    // Purple Meanie
    PURPLE_MEANIE_01("purpleMeanie01",8),
    PURPLE_MEANIE_02("purpleMeanie02", 8),
    PURPLE_MEANIE_03("purpleMeanie03", 8),
    PURPLE_MEANIE_04("purpleMeanie04", 8),
    PURPLE_MEANIE_05("purpleMeanie05", 8),
    PURPLE_MEANIE_01_HIT("purpleMeanie01Hit",8),
    PURPLE_MEANIE_02_HIT("purpleMeanie02Hit", 8),
    PURPLE_MEANIE_03_HIT("purpleMeanie03Hit", 8),
    PURPLE_MEANIE_04_HIT("purpleMeanie04Hit", 8),
    PURPLE_MEANIE_05_HIT("purpleMeanie05Hit", 8),

    // Pad
    PAD_01("pad01",6),
    PAD_02("pad02", 6),
    PAD_03("pad03", 6),
    PAD_04("pad04", 6),
    PAD_05("pad05", 6),
    PAD_01_HIT("pad01Hit",6),
    PAD_02_HIT("pad02Hit", 6),
    PAD_03_HIT("pad03Hit", 6),
    PAD_04_HIT("pad04Hit", 6),
    PAD_05_HIT("pad05Hit", 6),

    // Jumper
    JUMPER("jumper",8),
    JUMPER_UP("jumperUp", 8),
    JUMPER_DOWN("jumperDown", 8),
    JUMPER_HIT("jumperHit",8),
    JUMPER_UP_HIT("jumperUpHit", 8),
    JUMPER_DOWN_HIT("jumperDownHit", 8),

    // Ripley
    RIPLEY("ripley",6),
    RIPLEY_LEFT("ripleyLeft", 6),
    RIPLEY_RIGHT("ripleyRight", 6),
    RIPLEY_HIT("ripleyHit",6),
    RIPLEY_LEFT_HIT("ripleyLeftHit", 6),
    RIPLEY_RIGHT_HIT("ripleyRightHit", 6),

    // All Seeing Eye
    ALL_SEEING_EYE("allSeeingEye",8),
    ALL_SEEING_EYE_LEFT("allSeeingEyeLeft", 8),
    ALL_SEEING_EYE_RIGHT("allSeeingEyeRight", 8),
    ALL_SEEING_EYE_HIT("allSeeingEyeHit",8),
    ALL_SEEING_EYE_LEFT_HIT("allSeeingEyeLeftHit", 8),
    ALL_SEEING_EYE_RIGHT_HIT("allSeeingEyeRightHit", 8),

    // Charlie
    CHARLIE("charlie",8),
    CHARLIE_LEFT("charlieLeft", 8),
    CHARLIE_RIGHT("charlieRight", 8),
    CHARLIE_HIT("charlieHit",8),
    CHARLIE_LEFT_HIT("charlieLeftHit", 8),
    CHARLIE_RIGHT_HIT("charlieRightHit", 8),

    // Walker
    WALKER("walker",8),
    WALKER_RIGHT("walkerRight", 8),
    WALKER_LEFT("walkerLeft", 8),
    WALKER_HIT("walkerHit",8),
    WALKER_RIGHT_HIT("walkerRightHit", 8),
    WALKER_LEFT_HIT("walkerLeftHit", 8),

    // Frogger
    FROGGER("frogger",4),
    FROGGER_TALL("froggerTall", 4),
    FROGGER_WIDE("froggerWide", 4),
    FROGGER_HIT("froggerHit",4),
    FROGGER_TALL_HIT("froggerTallHit", 4),
    FROGGER_WIDE_HIT("froggerWideHit", 4),

    // Fighter
    FIGHTER("fighter",8),
    FIGHTER_TALL("fighterTall", 8),
    FIGHTER_WIDE("fighterWide", 8),
    FIGHTER_HIT("fighterHit",8),
    FIGHTER_TALL_HIT("fighterTallHit", 8),
    FIGHTER_WIDE_HIT("fighterWideHit", 8),

    // Rotator
    ROTATOR_01("rotator01",8),
    ROTATOR_02("rotator02",8),
    ROTATOR_03("rotator03",8),
    ROTATOR_04("rotator04",8),
    ROTATOR_05("rotator05",8),
    ROTATOR_01_HIT("rotatorHit01",8),
    ROTATOR_02_HIT("rotatorHit02",8),
    ROTATOR_03_HIT("rotatorHit03",8),
    ROTATOR_04_HIT("rotatorHit04",8),
    ROTATOR_05_HIT("rotatorHit05",8),

    // Whirlpool
    WHIRLPOOL_01("whirlpool01",6),
    WHIRLPOOL_02("whirlpool02",6),
    WHIRLPOOL_03("whirlpool03",6),
    WHIRLPOOL_04("whirlpool04",6),
    WHIRLPOOL_05("whirlpool05",6),
    WHIRLPOOL_01_HIT("whirlpool01Hit",6),
    WHIRLPOOL_02_HIT("whirlpool02Hit",6),
    WHIRLPOOL_03_HIT("whirlpool03Hit",6),
    WHIRLPOOL_04_HIT("whirlpool04Hit",6),
    WHIRLPOOL_05_HIT("whirlpool05Hit",6),

    // Battle Droid
    BATTLE_DROID_01("battleDroid01",6),
    BATTLE_DROID_02("battleDroid02",6),
    BATTLE_DROID_03("battleDroid03",6),
    BATTLE_DROID_01_HIT("battleDroid01Hit",6),
    BATTLE_DROID_02_HIT("battleDroid02Hit",6),
    BATTLE_DROID_03_HIT("battleDroid03Hit",6),

    // Fish
    FISH_01("fish01",6),
    FISH_02("fish02",6),
    FISH_03("fish03",6),
    FISH_01_HIT("fish01Hit",6),
    FISH_02_HIT("fish02Hit",6),
    FISH_03_HIT("fish03Hit",6),

    // Lemming
    LEMMING_01("lemming01",6),
    LEMMING_02("lemming02",6),
    LEMMING_03("lemming03",6),
    LEMMING_01_HIT("lemming01Hit",6),
    LEMMING_02_HIT("lemming02Hit",6),
    LEMMING_03_HIT("lemming03Hit",6),

    // Squeeze Box
    SQUEEZE_BOX_01("squeezeBox01",6),
    SQUEEZE_BOX_02("squeezeBox02",6),
    SQUEEZE_BOX_03("squeezeBox03",6),
    SQUEEZE_BOX_01_HIT("squeezeBox01Hit",6),
    SQUEEZE_BOX_02_HIT("squeezeBox02Hit",6),
    SQUEEZE_BOX_03_HIT("squeezeBox03Hit",6),

    // Yellow Beard
    YELLOW_BEARD_01("yellowBeard01",8),
    YELLOW_BEARD_02("yellowBeard02",8),
    YELLOW_BEARD_03("yellowBeard03",8),
    YELLOW_BEARD_01_HIT("yellowBeard01Hit",8),
    YELLOW_BEARD_02_HIT("yellowBeard02Hit",8),
    YELLOW_BEARD_03_HIT("yellowBeard03Hit",8),

    // Pilot
    PILOT_01("pilot01",6),
    PILOT_02("pilot02",6),
    PILOT_03("pilot03",6),
    PILOT_01_HIT("pilot01Hit",6),
    PILOT_02_HIT("pilot02Hit",6),
    PILOT_03_HIT("pilot03Hit",6),

    // Aracnoid
    ARACNOID_01("aracnoid01",4),
    ARACNOID_02("aracnoid02",4),
    ARACNOID_03("aracnoid03",4),
    ARACNOID_01_HIT("aracnoid01Hit",4),
    ARACNOID_02_HIT("aracnoid02Hit",4),
    ARACNOID_03_HIT("aracnoid03Hit",4),

    // Crab
    CRAB_01("crab01",6),
    CRAB_02("crab02",6),
    CRAB_03("crab03",6),
    CRAB_01_HIT("crab01Hit",6),
    CRAB_02_HIT("crab02Hit",6),
    CRAB_03_HIT("crab03Hit",6),

    // Bear
    BEAR_01("bear01",6),
    BEAR_02("bear02",6),
    BEAR_03("bear03",6),
    BEAR_01_HIT("bear01Hit",6),
    BEAR_02_HIT("bear02Hit",6),
    BEAR_03_HIT("bear03Hit",6),

    // Dino
    DINO_01("dino01",6),
    DINO_02("dino02",6),
    DINO_03("dino03",6),
    DINO_04("dino04",6),
    DINO_01_HIT("dino01Hit",6),
    DINO_02_HIT("dino02Hit",6),
    DINO_03_HIT("dino03Hit",6),
    DINO_04_HIT("dino04Hit",6),

    // Frisbie
    FRISBIE_01("frisbie01",8),
    FRISBIE_02("frisbie02",8),
    FRISBIE_03("frisbie03",8),
    FRISBIE_04("frisbie04",8),
    FRISBIE_05("frisbie05",8),
    FRISBIE_01_HIT("frisbie01Hit",8),
    FRISBIE_02_HIT("frisbie02Hit",8),
    FRISBIE_03_HIT("frisbie03Hit",8),
    FRISBIE_04_HIT("frisbie04Hit",8),
    FRISBIE_05_HIT("frisbie05Hit",8),

    // Joker
    JOKER_01("joker01",8),
    JOKER_02("joker02",8),
    JOKER_03("joker03",8),
    JOKER_01_HIT("joker01Hit",8),
    JOKER_02_HIT("joker02Hit",8),
    JOKER_03_HIT("joker03Hit",8),

    // octopus
    OCTOPUS_LEFT("octopusLeft",4),
    OCTOPUS_RIGHT("octopusRight", 4),
    OCTOPUS_LEFT_HIT("octopusLeftHit", 4),
    OCTOPUS_RIGHT_HIT("octopusRightHit", 4),

    // yellow minion
    MINION("minion", 2),
    MINION_HIT("minionHit", 2),

    // green ball spinner
    SPINNER_GREEN_01("spinnerGreen01", 4),
    SPINNER_GREEN_02("spinnerGreen02", 4),
    SPINNER_GREEN_03("spinnerGreen03", 4),
    SPINNER_GREEN_01_HIT("spinnerGreenHit01", 4),
    SPINNER_GREEN_02_HIT("spinnerGreenHit02", 4),
    SPINNER_GREEN_03_HIT("spinnerGreenHit03", 4),

    // pulsating green ball spinner
    SPINNER_PULSE_GREEN_01("spinnerPulseGreen01", 4),
    SPINNER_PULSE_GREEN_02("spinnerPulseGreen02", 4),
    SPINNER_PULSE_GREEN_03("spinnerPulseGreen03", 4),
    SPINNER_PULSE_GREEN_01_HIT("spinnerPulseGreenHit01", 4),
    SPINNER_PULSE_GREEN_02_HIT("spinnerPulseGreenHit02", 4),
    SPINNER_PULSE_GREEN_03_HIT("spinnerPulseGreenHit03", 4),

    // spinning space stations
    SPACE_STATION("spaceStation", 4),
    SPACE_STATION_HIT("spaceStationHit", 4),

    // pulsating barrier
    BARRIER_01("barrierGirder01", 2),
    BARRIER_02("barrierGirder02", 2),
    BARRIER_03("barrierGirder03", 2),

    // molecule
    MOLECULE("molecule", 2),
    MOLECULE_HIT("moleculeHit", 2),

    // big boss
    BIG_BOSS_01("bigBoss01", 6),
    BIG_BOSS_02("bigBoss02", 6),
    BIG_BOSS_01_HIT("bigBossHit01", 6),
    BIG_BOSS_02_HIT("bigBossHit02", 6),

    // flapping lady bird
    LADYBIRD_01("ladyBird01", 6),
    LADYBIRD_02("ladyBird02", 6),
    LADYBIRD_01_HIT("ladyBirdHit01", 6),
    LADYBIRD_02_HIT("ladyBirdHit02", 6),

    // zogg
    ZOGG_UP("zogg01", 4),
    ZOGG_DOWN("zogg02", 4),
    ZOGG_UP_HIT("zoggHit01",4),
    ZOGG_DOWN_HIT("zoggHit02", 4),

    // asteroid
    ASTEROID("asteroid", 4),
    ASTEROID_HIT("asteroidHit", 4),

    // mini-asteroid
    ASTEROID_MINI("miniAsteroid", 3),
    ASTEROID_MINI_HIT("miniAsteroidHit", 3),

    // egg
    EGG_CRACK_01("eggCrack01", 3),
    EGG_CRACK_02("eggCrack02", 3),
    EGG_CRACK_03("eggCrack03", 3),
    EGG_CRACK_01_HIT("eggCrackHit01", 3),
    EGG_CRACK_02_HIT("eggCrackHit02", 3),
    EGG_CRACK_03_HIT("eggCrackHit03", 3),

    // egg explosion
    EGG_CRACK_04("eggCrack04"),
    EGG_CRACK_05("eggCrack05"),
    EGG_CRACK_06("eggCrack06"),
    EGG_CRACK_07("eggCrack07"),

    // dragon - head and body
    DRAGON_HEAD_LEFT("dragonLeft", 5),
    DRAGON_HEAD_RIGHT("dragonRight", 5),
    DRAGON_HEAD_LEFT_HIT("dragonHitLeft", 5),
    DRAGON_HEAD_RIGHT_HIT("dragonHitRight", 5),
    DRAGON_BODY("dragonBody", 5),
    DRAGON_BODY_HIT("dragonBodyHit",5),

    // buzzer - small flying insect
    INSECT_WINGS_UP("buzzerWingsUp", 3),
    INSECT_WINGS_DOWN("buzzerWingsDown", 3),
    INSECT_WINGS_UP_HIT("buzzerWingsUpHit", 3),
    INSECT_WINGS_DOWN_HIT("buzzerWingsDownHit", 3),

    // mother buzzer - big flying insect
    MOTHER_BUZZER_WINGS_DOWN("motherBuzzerWingsDown", 4),
    MOTHER_BUZZER_WINGS_UP("motherBuzzerWingsUp", 4),
    MOTHER_BUZZER_WINGS_DOWN_HIT("motherBuzzerWingsDownHit", 4),
    MOTHER_BUZZER_WINGS_UP_HIT("motherBuzzerWingsUpHit", 4),

    // gobby
    GOBBY_LEFT("gobby01", 2),
    GOBBY_RIGHT("gobby02", 2),
    GOBBY_LEFT_HIT("gobbyHit01", 2),
    GOBBY_RIGHT_HIT("gobbyHit02", 2),

    // bad cat
    BAD_CAT("badCat", 2),
    BAD_CAT_HIT("badCatHit", 2),

    // bomb
    BOMB_01("bomb01", 3),
    BOMB_02("bomb02", 3),
    BOMB_03("bomb03", 3),
    BOMB_01_HIT("bombHit01", 3),
    BOMB_02_HIT("bombHit02", 3),
    BOMB_03_HIT("bombHit03", 3),

    // batty
    BATTY_FLAP_DOWN("bat01", 3),
    BATTY_FLAP_UP("bat02", 3),
    BATTY_FLAP_DOWN_HIT("bat01Hit", 3),
    BATTY_FLAP_UP_HIT("bat02Hit", 3),

    // book
    BOOK_FLAT("book01", 3),
    BOOK_BEND("book02", 3),
    BOOK_CLOSED("book03", 3),
    BOOK_FLAT_HIT("bookHit01", 3),
    BOOK_BEND_HIT("bookHit02", 3),
    BOOK_CLOSED_HIT("bookHit03", 3),

    // bouncer
    BOUNCER_IN("bouncer01", 4),
    BOUNCER_OUT("bouncer02", 4),
    BOUNCER_IN_HIT("bouncer01Hit", 4),
    BOUNCER_OUT_HIT("bouncer02Hit", 4),

    // droopy
    DROOPY_DOWN("droopyDown", 4),
    DROOPY_UP("droopyUp", 4),
    DROOPY_DOWN_HIT("droopyDownHit",4),
    DROOPY_UP_HIT("droopyUpHit", 4),

    // circuit
    CIRCUIT_LEFT("circuit01", 2),
    CIRCUIT_RIGHT("circuit02", 2),
    CIRCUIT_LEFT_HIT("circuit01Hit", 2),
    CIRCUIT_RIGHT_HIT("circuit02Hit", 2),

    // smokey
    SMOKEY_FLAME_BIG("smokey01", 4),
    SMOKEY_FLAME_SMALL("smokey02", 4),
    SMOKEY_FLAME_NONE("smokey03", 4),
    SMOKEY_FLAME_BIG_HIT("smokeyHit01", 4),
    SMOKEY_FLAME_SMALL_HIT("smokeyHit02", 4),
    SMOKEY_FLAME_NONE_HIT("smokeyHit03",4),

    // telly
    TELLY_FUZZ_ONE("telly01", 4),
    TELLY_FUZZ_TWO("telly02", 4),
    TELLY_FUZZ_ONE_HIT("tellyHit01", 4),
    TELLY_FUZZ_TWO_HIT("tellyHit02", 4),

    // cloud
    CLOUD("cloud", 5),
    CLOUD_HIT("cloudHit", 5),

    // helmet
    HELMET("helmet", 5),
    HELMET_HIT("helmetHit", 5),

    // skull alien
    SKULL("skull", 3),
    SKULL_HIT("skullHit", 3),

    // droid alien
    DROID("droid", 3),
    DROID_HIT("droidHit", 3),

    // bases
    BASE("base", 4),
    BASE_RIGHT("baseRight", 3),
    BASE_LEFT("baseLeft", 3),
    HELPER("baseHelper", 3),

    // pulsing shield when base stationary
    BASE_SHIELD_ONE("baseShieldOutline01"),
    BASE_SHIELD_TWO("baseShieldOutline02"),
    BASE_SHIELD_THREE("baseShieldOutline03"),
    BASE_SHIELD_FOUR("baseShieldOutline04"),

    // pulsing shield when base turning left
    BASE_LEFT_SHIELD_ONE("baseShieldLeftOutline01"),
    BASE_LEFT_SHIELD_TWO("baseShieldLeftOutline02"),
    BASE_LEFT_SHIELD_THREE("baseShieldLeftOutline03"),
    BASE_LEFT_SHIELD_FOUR("baseShieldLeftOutline04"),

    // pulsing shield when base turning right
    BASE_RIGHT_SHIELD_ONE("baseShieldRightOutline01"),
    BASE_RIGHT_SHIELD_TWO("baseShieldRightOutline02"),
    BASE_RIGHT_SHIELD_THREE("baseShieldRightOutline03"),
    BASE_RIGHT_SHIELD_FOUR("baseShieldRightOutline04"),

    // pulsing shield for helper base
    HELPER_SHIELD_ONE("baseHelperShieldOutline01"),
    HELPER_SHIELD_TWO("baseHelperShieldOutline02"),
    HELPER_SHIELD_THREE("baseHelperShieldOutline03"),
    HELPER_SHIELD_FOUR("baseHelperShieldOutline04"),

    // power ups
    POWERUP_LIFE("pwrUpLife34px"),
    POWERUP_MISSILE_FAST("pwrUpFast34px"),
    POWERUP_MISSILE_BLAST("pwrUpBlast34px"),
    POWERUP_MISSILE_GUIDED("pwrUpDirectional34px"),
    POWERUP_MISSILE_PARALLEL("pwrUpParallel34px"),
    POWERUP_MISSILE_SPRAY("pwrUpSpray34px"),
    POWERUP_MISSILE_LASER("pwrUpLaser34px"),
    POWERUP_SHIELD("pwrUpShield34px"),
    POWERUP_HELPER_BASES("pwrUpHelper34px"),

    // base missiles
    BASE_MISSILE("baseMissile"),
    BASE_MISSILE_LASER("baseMissileLaser"),
    BASE_MISSILE_ROCKET("baseMissileRocket"),
    BASE_MISSILE_BLAST("baseMissileBlast"),

    // alien laser
    ALIEN_LASER("alienLaser"),

    // fireball
    FIREBALL01("fireBall01"),
    FIREBALL02("fireBall02"),

    // lightning and rain missile
    LIGHTNING("lightningLong"),
    RAIN("rain"),

    // explosions
    EXPLODE_01("explodeOne"),
    EXPLODE_02("explodeTwo"),
    EXPLODE_03("explodeThree"),
    EXPLODE_04("explodeFour"),
    EXPLODE_05("explodeFive"),

    // cloud explosions
    CLOUD_EXPLODE_01("cloudExplode01"),
    CLOUD_EXPLODE_02("cloudExplode02"),
    CLOUD_EXPLODE_03("cloudExplode03"),
    CLOUD_EXPLODE_04("cloudExplode04"),
    CLOUD_EXPLODE_05("cloudExplode05"),

    // big-explosions
    EXPLODE_BIG_01("bigExplosionOne"),
    EXPLODE_BIG_02("bigExplosionTwo"),
    EXPLODE_BIG_03("bigExplosionThree"),
    EXPLODE_BIG_04("bigExplosionFour"),
    EXPLODE_BIG_05("bigExplosionFive"),

    // massive-explosions
    BASE_EXPLODE_01("baseExplosion01"),
    BASE_EXPLODE_02("baseExplosion02"),
    BASE_EXPLODE_03("baseExplosion03"),
    BASE_EXPLODE_04("baseExplosion04"),
    BASE_EXPLODE_05("baseExplosion05"),
    BASE_EXPLODE_06("baseExplosion06"),
    BASE_EXPLODE_07("baseExplosion07"),
    BASE_EXPLODE_08("baseExplosion08"),
    BASE_EXPLODE_09("baseExplosion09"),
    BASE_EXPLODE_10("baseExplosion10"),
    BASE_EXPLODE_11("baseExplosion11"),

    // stars
    STAR("Star"),
    STAR_BLACK("StarBLACK"),
    STAR_RED("StarRED"),
    STAR_BLUE("StarBLUE"),
    STAR_SPARKLE("StarSPARKLE"),

    // pause buttons
    PAUSE_BUTTON_UP("pauseButtonUp"),
    PAUSE_BUTTON_DOWN("pauseButtonDown"),

    // wave flags
    FLAG_1("Flag_1"),
    FLAG_5("Flag_5"),
    FLAG_10("Flag_10"),
    FLAG_50("Flag_50"),

    // menu buttons
    MENU_BUTTON_UP("blueButtonUp"),
    MENU_BUTTON_DOWN("blueButtonDown"),

    // lives remaining
    LIVES("Life_32px"),

    // fonts
    FONT_MAP("GalaxyForceFont_30x38-crop"),

    // null - transparent 1 x 1 sprite (essentially an invisible sprite)
    NULL("null");


    private final String name;
    private ISpriteProperties properties;

    // for less sensitive collision detection we can optionally
    // reduce the sprite bounds.
    // this value will reduce bounds by this value all-around
    // e.g. a boundsReduction value of 2 will give a 64x64 pixel
    // a bounding box of 60x60 (2 pixels less top, bottom, left, right)
    private final int boundsReduction;

    GameSpriteIdentifier(String name) {
        this(name, 0);
    }

    GameSpriteIdentifier(String name, int boundsReduction) {
        this.name = name;
        this.boundsReduction = boundsReduction;
    }

    @Override
    public ISpriteProperties getProperties() {
        return properties;
    }

    /**
     * Update the sprite properties using this sprite's name and the supplied
     * texture map.
     * <p>
     * This method can be called once a texture is available or refreshed after
     * a resume.
     */
    @Override
    public void updateProperties(Texture texture) {
        this.properties = new SpriteProperties(name, texture);
        if (properties.getWidth() <= boundsReduction * 2) {
            throw new GalaxyForceException(
                    String.format(
                            "Sprite: %s has width of %d but bounds reduction of %d.",
                            name,
                            properties.getWidth(),
                            boundsReduction));
        }
        if (properties.getHeight() <= boundsReduction * 2) {
            throw new GalaxyForceException(
                    String.format(
                            "Sprite: %s has height of %d but bounds reduction of %d.",
                            name,
                            properties.getHeight(),
                            boundsReduction));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int boundsReduction() {
        return boundsReduction;
    }
}
