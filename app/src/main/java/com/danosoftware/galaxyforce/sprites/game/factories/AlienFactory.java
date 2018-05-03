package com.danosoftware.galaxyforce.sprites.game.factories;

import android.util.Log;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.enumerations.PowerUpType;
import com.danosoftware.galaxyforce.flightpath.paths.Point;
import com.danosoftware.galaxyforce.game.beans.SpawnedAlienBean;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;
import com.danosoftware.galaxyforce.sound.SoundEffectBank;
import com.danosoftware.galaxyforce.sound.SoundEffectBankSingleton;
import com.danosoftware.galaxyforce.sprites.game.behaviours.PowerUpBehaviour;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienAsteroid;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienAsteroidSimple;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienDragonBody;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienDragonHead;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienDroid;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienGobby;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienHunter;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienInsectPath;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienMinion;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienMothership;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienOctopus;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienSpawnedInsect;
import com.danosoftware.galaxyforce.sprites.game.implementations.AlienStork;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteAlien;
import com.danosoftware.galaxyforce.waves.AlienType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlienFactory
{

    private final static String TAG = "AlienFactory";

    /* initialise sound effects */
    private final static Sound SPAWNED_ALIEN_SOUND;

    static
    {
        /* create reference to sound effects */
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        SPAWNED_ALIEN_SOUND = soundBank.get(SoundEffect.ALIEN_SPAWN);
    }

    /**
     * Creates an alien with a supplied path. Starting position will be based on
     * path.
     */
    public static List<SpriteAlien> createAlien(
            final AlienType alienType,
            final PowerUpType powerUp,
            final List<Point> path,
            final float delay,
            final GameHandler model,
            final boolean restartImmediately)
    {
        List<SpriteAlien> aliens = new ArrayList<>();

        /*
         * Create new path per alien as different aliens may modify paths
         * differently
         */
        List<Point> alienPath = new ArrayList<>();

        /*
         * convert path so alien is positioned at centre of provided point. This
         * is dependent on sprite size.
         */
        for (Point currentPoint : path)
        {
            int x = currentPoint.getX();
            int y = currentPoint.getY();

            alienPath.add(new Point(x, y));
        }

        // create instance of the wanted alien
        switch (alienType)
        {
        case OCTOPUS:
            aliens.add(new AlienOctopus(model, powerUp, alienPath, delay, restartImmediately));
            break;

        case GOBBY:
            aliens.add(new AlienGobby(model, powerUp, alienPath, delay, restartImmediately));
            break;

        case MINION:
            aliens.add(new AlienMinion(model, powerUp, alienPath, delay, restartImmediately));
            break;

        case MOTHERSHIP:
            aliens.add(new AlienMothership(
                    model,
                    powerUp,
                    Arrays.asList(PowerUpType.ENERGY, PowerUpType.MISSILE_FAST, PowerUpType.MISSILE_PARALLEL),
                    alienPath,
                    delay,
                    restartImmediately));
            break;

        case STORK:
            aliens.add(new AlienStork(model, powerUp, alienPath, delay, restartImmediately));
            break;

        case DROID:
            aliens.add(new AlienDroid(model, powerUp, alienPath, delay, restartImmediately));
            break;

        case INSECT:
            aliens.add(new AlienInsectPath(model, powerUp, alienPath, delay, restartImmediately));
            break;

        default:
            String errorMessage = "Error: Unrecognised AlienType: '" + alienType + "'";
            Log.e(TAG, errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        // return alien;
        return aliens;
    }

    /**
     * Creates an alien with no path in the specified position.
     */
    public static List<SpriteAlien> createAlien(
            final AlienType alienType,
            final PowerUpType powerUp,
            final boolean xRandom,
            final boolean yRandom,
            final int xStart,
            final int yStart,
            final float delay,
            final GameHandler model,
            final boolean restartImmediately,
            final Direction direction)
    {

        List<SpriteAlien> aliens = new ArrayList<>();

        // choose a x start position
        int xStartPos;
        if (xRandom)
        {
            /*
             * set random start position - but don't allow creation right at
             * edge where it may be impossible to hit. ensure min 32 pixels from
             * screen edges
             */
            xStartPos = 32 + (int) (Math.random() * (GameConstants.GAME_WIDTH - 64));
        }
        else
        {
            xStartPos = xStart;
        }

        // choose a y start position
        int yStartPos;
        if (yRandom)
        {
            /*
             * set random start position - but don't allow creation right at
             * edge where it may be impossible to hit. ensure min 32 pixels from
             * screen edges
             */
            yStartPos = 32 + (int) (Math.random() * (GameConstants.GAME_HEIGHT - 64));
        }
        else
        {
            yStartPos = yStart;
        }

        // create instance of the wanted alien
        switch (alienType)
        {
        case ASTEROID:
            aliens.add(new AlienAsteroid(powerUp, xStartPos, yStartPos, delay, restartImmediately, direction, model));
            break;

        case ASTEROID_SIMPLE:
            aliens.add(new AlienAsteroidSimple(powerUp, xStartPos, yStartPos, delay, restartImmediately, direction, model));
            break;

        case HUNTER:
            aliens.add(new AlienHunter(powerUp, xStartPos, yStartPos, delay, model));
            break;

        case DRAGON:

            /*
             * dragon consists of multiple body parts and a head. The head
             * contains a reference to the body parts as all body parts will be
             * destroyed when the head is destroyed.
             */
                List<AlienDragonBody> dragonBodies = new ArrayList<>();
                for (int i = 0; i < 20; i++)
                {
                    AlienDragonBody dragonBody = new AlienDragonBody(xStartPos, yStartPos, model);
                    dragonBodies.add(dragonBody);
                }

                aliens.add(new AlienDragonHead(powerUp, xStartPos, yStartPos, delay, model, dragonBodies));
                aliens.addAll(dragonBodies);
                break;

        default:
            String errorMessage = "Error: Unrecognised AlienType: '" + alienType + "'";
            Log.e(TAG, errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        return aliens;
    }

    /**
     * Creates a spawned alien with no path in the specified position. Simple
     * implementation that has no random elements or direction.
     * 
     * Returns spawned alien bean containing alien and sound effect.
     */
    public static SpawnedAlienBean createSpawnedAlien(
            final AlienType alienType,
            final PowerUpType powerUpType,
            final int xStart,
            final int yStart,
            final GameHandler model)
    {
        List<SpriteAlien> aliens = new ArrayList<>();
        Sound soundEffect;

        switch (alienType)
        {
        case SPAWNED_INSECT:
            aliens.add(new AlienSpawnedInsect(powerUpType, xStart, yStart, model));
            soundEffect = SPAWNED_ALIEN_SOUND;
            break;

        default:
            String errorMessage = "Error: Unrecognised AlienType: '" + alienType + "'";
            Log.e(TAG, errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        return new SpawnedAlienBean(aliens, soundEffect);
    }
}
