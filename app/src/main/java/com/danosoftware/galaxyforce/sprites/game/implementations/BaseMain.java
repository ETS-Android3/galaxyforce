package com.danosoftware.galaxyforce.sprites.game.implementations;

import com.danosoftware.galaxyforce.constants.GameConstants;
import com.danosoftware.galaxyforce.enumerations.BaseMissileType;
import com.danosoftware.galaxyforce.enumerations.Direction;
import com.danosoftware.galaxyforce.game.beans.BaseMissileBean;
import com.danosoftware.galaxyforce.game.handlers.GameHandler;
import com.danosoftware.galaxyforce.sound.Sound;
import com.danosoftware.galaxyforce.sound.SoundEffect;
import com.danosoftware.galaxyforce.sound.SoundEffectBank;
import com.danosoftware.galaxyforce.sound.SoundEffectBankSingleton;
import com.danosoftware.galaxyforce.sound.SoundPlayer;
import com.danosoftware.galaxyforce.sound.SoundPlayerSingleton;
import com.danosoftware.galaxyforce.sprites.game.behaviours.ExplodeBehaviourSimple;
import com.danosoftware.galaxyforce.sprites.game.factories.BaseMissileFactory;
import com.danosoftware.galaxyforce.sprites.game.interfaces.EnergyBar;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteShield;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;
import com.danosoftware.galaxyforce.view.Animation;

import java.util.ArrayList;
import java.util.List;

public class BaseMain extends SpriteBase
{

    /*
     * Distance base can move each second in pixels. If base moves once every
     * 1/60 second, it will move 10 pixels each time.
     */
    public static final int BASE_MOVE_PIXELS = 10 * 60;
    // public static final int BASE_MOVE_PIXELS = 10;

    /* delay between firing missiles in seconds */
    private static final float DEFAULT_BASE_MISSILE_DELAY = 0.5f;

    /* variable to store current delay between missile fires */
    private float baseMissileDelay = 0f;

    // default base sprite
    public static final ISpriteIdentifier BASE_SPRITE = GameSpriteIdentifier.BASE;

    // default base missile sprite
    public static final BaseMissileType DEFAULT_MISSILE_TYPE = BaseMissileType.SIMPLE;

    /* how much energy will be lost by another sprite when this sprite hits it */
    private static final int HIT_ENERGY = 2;

    /* width and height of actual usable screen */
    private int width, height;

    // reference to base's energy bar
    private EnergyBar energyBar = null;

    // energy of this base
    private int energy;

    /* variable to store time passed since base last fired */
    private float timeSinceBaseLastFired = 0f;

    /* variable to store current base missile type */
    private BaseMissileType baseMissileType = null;

    /* references current direction of base */
    private Direction direction;

    /*
     * variable to store time until missile should be reverted to default. Value
     * of zero indicates no change is needed.
     */
    private float timeUntilDefaultMissile = 0f;

    /*
     * variable to store time until shield can be removed. Value of zero
     * indicates no change is needed.
     */
    private float timeUntilShieldRemoved = 0f;

    // sprite bounds for touch detection (slightly bigger than for collision
    // detection)
    private Rectangle touchBounds = null;

    /*
     * small buffer so that bounds is slightly outside sprite area.
     * helps if finger touch just misses sprite.
     */
    private static int BOUNDS_BUFFER = 20;

    /*
     * holds time base has been steady for (i.e. not turning left or right).
     */
    private float baseTurnSteadyTime = 0f;

    /* is base turning left or right */
    private boolean baseTurning = false;

    /* weighting that must be exceeded to trigger base left or right turn */
    private static float WEIGHTING_TURN = 0.5f;

    /* weighting that must be less than to trigger base steady */
    // private static float WEIGHTING_STEADY = 0.001f;

    /*
     * increased for tilt controller as weighting never goes close to zero in
     * tilt mode.
     */
    private static float WEIGHTING_STEADY = 0.1f;

    /*
     * number of seconds for base to be steady for before reseting left or right
     * turn
     */
    private static float STEADY_DELAY = 0.1f;

    /* is base flipping */
    private boolean flipping = false;

    /* does base have shield */
    private boolean shielded = false;

    /* reference to base's shield */
    private SpriteShield shieldSprite = null;

    /* list of any helper bases */
    private List<SpriteBase> helperBases = null;

    /* reference to model */
    private GameHandler model = null;

    /* reference to sound player and sounds */
    private final SoundPlayer soundPlayer;
    private final Sound explosionSound;

    // FIXME - Correct animation sequence - removed one sprite from sequence

    // flip from up to down animation
    // private static final Animation FLIP_UP_TO_DOWN = new
    // Animation(GameConstants.DIRECTION_CHANGE_TRANSITION_TIME,
    // SpriteProperty.BASE,
    // SpriteProperty.BASE_SQUEEZE_1, SpriteProperty.BASE_SQUEEZE_2,
    // SpriteProperty.BASE_SQUEEZE_3,
    // SpriteProperty.BASE_FLIP_SQUEEZE_3, SpriteProperty.BASE_FLIP_SQUEEZE_2,
    // SpriteProperty.BASE_FLIP_SQUEEZE_1,
    // SpriteProperty.BASE_FLIP);
    //
    private static final Animation FLIP_UP_TO_DOWN = new Animation(GameConstants.DIRECTION_CHANGE_TRANSITION_TIME,
            GameSpriteIdentifier.BASE, GameSpriteIdentifier.BASE_SPIN_1, GameSpriteIdentifier.BASE_SPIN_2,
            GameSpriteIdentifier.BASE_SPIN_3, GameSpriteIdentifier.BASE_FLAT, GameSpriteIdentifier.BASE_FLIP_SPIN_3,
            GameSpriteIdentifier.BASE_FLIP_SPIN_2, GameSpriteIdentifier.BASE_FLIP_SPIN_1, GameSpriteIdentifier.BASE_FLIP);

    // flip from down to up animation
    private static final Animation FLIP_DOWN_TO_UP = new Animation(GameConstants.DIRECTION_CHANGE_TRANSITION_TIME,
            GameSpriteIdentifier.BASE_FLIP, GameSpriteIdentifier.BASE_FLIP_SPIN_1, GameSpriteIdentifier.BASE_FLIP_SPIN_2,
            GameSpriteIdentifier.BASE_FLIP_SPIN_3, GameSpriteIdentifier.BASE_FLAT, GameSpriteIdentifier.BASE_SPIN_3,
            GameSpriteIdentifier.BASE_SPIN_2, GameSpriteIdentifier.BASE_SPIN_1, GameSpriteIdentifier.BASE);

    public BaseMain(int xStart, int yStart, ISpriteIdentifier spriteId, int width, int height, EnergyBar energyBar, Direction direction,
            GameHandler model)
    {
        /*
         * Energy set to zero in constructor since energy is over-ridden in this
         * class. Actual base energy comes from the energy bar object.
         */
        super(xStart, yStart, spriteId, new ExplodeBehaviourSimple(), 0, HIT_ENERGY);
        super.updateBounds();
        this.width = width;
        this.height = height;
        this.energyBar = energyBar;
        this.direction = direction;
        this.model = model;

        this.soundPlayer = SoundPlayerSingleton.getInstance();

        // get sound effects from sound bank
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        this.explosionSound = soundBank.get(SoundEffect.EXPLOSION);

        // default missile type
        this.baseMissileType = DEFAULT_MISSILE_TYPE;

        // set delay between missile fires
        this.baseMissileDelay = DEFAULT_BASE_MISSILE_DELAY;

        // set an empty list of helper bases
        this.helperBases = new ArrayList<SpriteBase>();

        /* reset time until missile should change type */
        timeUntilDefaultMissile = 0f;

        /* reset time since base last fired missile */
        timeSinceBaseLastFired = 0f;

        /* reset time until shield should be removed */
        timeUntilShieldRemoved = 0f;

        /* reset base turning left or right variables */
        baseTurnSteadyTime = 0f;
        baseTurning = false;

        /* base is not currently flipping */
        flipping = false;

        /* base is not currently shielded */
        shielded = false;

        // reset energy bar to maximum. new energy level returned.
        energy = energyBar.resetEnergy();
    }

    /**
     * return an instance of base at supplied position
     */
    public static SpriteBase newBase(int xStart, int yStart, int width, int height, EnergyBar energyBar, Direction direction,
            GameHandler model)
    {
        return new BaseMain(xStart, yStart, BaseMain.BASE_SPRITE, width, height, energyBar, direction, model);
    }

    @Override
    public void move(float deltaTime)
    {
        // move sprite bounds
        updateBounds();

        // move sprite touch bounds - TODO removed as no longer using base touch
        // - remove permanently??
        // updateTouchBounds();

        /* use superclass for any explosions */
        super.move(deltaTime);

        /*
         * check to see if shield should be removed.
         */
        if (timeUntilShieldRemoved > 0)
        {
            timeUntilShieldRemoved = timeUntilShieldRemoved - deltaTime;

            // if shield time now expired, remove shield
            if (timeUntilShieldRemoved <= 0)
            {
                removeShield();
            }
        }

        // if base has a shield, move it with base
        if (shielded)
        {
            shieldSprite.setX(getX());
            shieldSprite.setY(getY());
        }

        /* check to see if base (and any helpers) should fire their missiles */
        fireBaseMissile(deltaTime);
    }

    @Override
    public void setExploding()
    {
        super.setExploding();

        // if base has a shield, remove it
        if (shielded)
        {
            removeShield();
        }

        // play explosion sound effect
        soundPlayer.playSound(explosionSound);

        /*
         * if primary base explodes - all helper bases must also explode. this
         * will also cause helpers to de-register
         * 
         * de-registration process will remove helper bases from list. however
         * we need to iterate over this list of helper bases. to avoid a
         * concurrent modification exception we must iterate over a copy of the
         * list
         */
        if (helperBases.size() > 0)
        {
            List<SpriteBase> helperBasesCopy = new ArrayList<SpriteBase>(helperBases);

            for (SpriteBase aHelperBase : helperBasesCopy)
            {
                aHelperBase.setExploding();
            }
        }
    }

    public void setDestroyed()
    {
        super.setDestroyed();

        // if base has a shield, remove it
        if (shielded)
        {
            removeShield();
        }

        /*
         * if primary base is destroyed - all helper bases must also be
         * destroyed. this will also cause helpers to de-register
         * 
         * de-registration process will remove helper bases from list. however
         * we need to iterate over this list of helper bases. to avoid a
         * concurrent modification exception we must iterate over a copy of the
         * list
         */
        if (helperBases.size() > 0)
        {
            List<SpriteBase> helperBasesCopy = new ArrayList<SpriteBase>(helperBases);

            for (SpriteBase aHelperBase : helperBasesCopy)
            {
                aHelperBase.setDestroyed();
            }
        }
    }

    @Override
    public void loseEnergy(int loseEnergy)
    {
        // can not lose energy if shielded
        if (!shielded)
        {
            energy = energyBar.decreaseEnergy(loseEnergy);

            if (energy <= 0)
            {
                setExploding();
            }
        }
    }

    @Override
    public int hitEnergy()
    {
        /*
         * when shielded, the base has no hit energy.
         * 
         * this stops the base crashing into all the aliens on the screen and
         * destroying them all while being shielded.
         */
        if (!shielded)
        {
            return super.hitEnergy();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void addEnergy(int addEnergy)
    {
        energy = energyBar.increaseEnergy(addEnergy);
    }

    /**
     * Moves base by the supplied weighting
     * 
     * @param weightingX
     * @param weightingY
     * @param deltaTime
     */
    public void moveBase(float weightingX, float weightingY, float deltaTime)
    {
        if (this.isActive())
        {

            /*
             * can cause jittery movement if game is running very slowly as base
             * can overshoot target but ensures that base movement doesn't slow
             * down when game slows down.
             */
            this.setX(this.getX() + (int) (BASE_MOVE_PIXELS * weightingX * deltaTime));
            this.setY(this.getY() + (int) (BASE_MOVE_PIXELS * weightingY * deltaTime));

            // this.setX((int) (this.getX() + (BASE_MOVE_PIXELS * weightingX)));
            // this.setY((int) (this.getY() + (BASE_MOVE_PIXELS * weightingY)));

            /*
             * if base is going right then use base right turn sprite. reset
             * time since base was steady so that base doesn't immediately
             * un-turn.
             */
            if (!flipping && weightingX > WEIGHTING_TURN)
            {
                if (direction == Direction.UP)
                {
                    setSpriteIdentifier(GameSpriteIdentifier.BASE_RIGHT);
                }
                else
                {
                    setSpriteIdentifier(GameSpriteIdentifier.BASE_FLIP_RIGHT);
                }
                baseTurnSteadyTime = 0f;
                baseTurning = true;
            }
            /*
             * if base is going left then use base left turn sprite. reset time
             * since base was steady so that base doesn't immediately un-turn.
             */
            else if (!flipping && weightingX < -WEIGHTING_TURN)
            {
                if (direction == Direction.UP)
                {
                    setSpriteIdentifier(GameSpriteIdentifier.BASE_LEFT);
                }
                else
                {
                    setSpriteIdentifier(GameSpriteIdentifier.BASE_FLIP_LEFT);
                }
                baseTurnSteadyTime = 0f;
                baseTurning = true;
            }
            /*
             * if base not moving increment time base has been steady for. if
             * base has been steady for more that set time then revert to normal
             * sprite.
             */
            else if (!flipping && baseTurning && weightingX > -WEIGHTING_STEADY && weightingX < WEIGHTING_STEADY)
            {
                baseTurnSteadyTime = baseTurnSteadyTime + deltaTime;

                if (baseTurnSteadyTime > STEADY_DELAY)
                {
                    if (direction == Direction.UP)
                    {
                        setSpriteIdentifier(GameSpriteIdentifier.BASE);
                    }
                    else
                    {
                        setSpriteIdentifier(GameSpriteIdentifier.BASE_FLIP);
                    }
                    baseTurnSteadyTime = 0f;
                    baseTurning = false;
                }
            }

            int baseHalfWidth = getWidth() / 2;
            int baseHalfHeight = getHeight() / 2;

            // don't allow base to go off screen left
            if (getX() - baseHalfWidth < 0)
            {
                setX(baseHalfWidth);
            }

            // don't allow base to go off screen right
            if ((getX() + baseHalfWidth) > width)
            {
                setX(width - baseHalfWidth);
            }

            // don't allow base to go off screen bottom
            if ((getY() - baseHalfHeight) < 0)
            {
                setY(baseHalfHeight);
            }

            // don't allow base to go off screen top
            if ((getY() + baseHalfHeight) > height)
            {
                setY(height - baseHalfHeight);
            }

            /* move helper bases using built in offset from this primary base */
            for (SpriteBase aHelperBase : helperBases)
            {
                if (aHelperBase.isActive())
                {
                    aHelperBase.setX(this.getX());
                    aHelperBase.setY(this.getY());
                }
            }
        }

    }

    @Override
    public void updateDirectionChange(float directionChangeStateTime)
    {
        // choose animation loop (based on current direction)
        Animation animation = (direction == Direction.UP ? FLIP_UP_TO_DOWN : FLIP_DOWN_TO_UP);

        // set base sprite using animation loop and time through animation
        setSpriteIdentifier(animation.getKeyFrame(directionChangeStateTime, Animation.ANIMATION_NONLOOPING));

        /* update direction change for any helper bases */
        for (SpriteBase aHelperBase : helperBases)
        {
            if (aHelperBase.isActive())
            {
                aHelperBase.updateDirectionChange(directionChangeStateTime);
            }
        }
    }

    /**
     * fire base missile
     */
    private void fireBaseMissile(float deltaTime)
    {
        // if base is ready to fire and not changing direction - fire!!
        if (readyToFire(deltaTime) && !flipping)
        {
            List<BaseMissileBean> missiles = new ArrayList<BaseMissileBean>();

            // primary base fires
            missiles.add(fire(direction));

            // any helper bases fire
            for (SpriteBase aHelperBase : helperBases)
            {
                // create new missile and add to missile list.
                missiles.add(aHelperBase.fire(direction));
            }

            // send missiles to model
            for (BaseMissileBean aMissile : missiles)
            {
                model.fireBaseMissiles(aMissile);
            }
        }
    }

    /**
     * Returns true if base is ready to fire. measures total time since base
     * last fired compared to a set delay
     * 
     * @param deltaTime
     */
    private boolean readyToFire(float deltaTime)
    {
        /*
         * check to see if current missile type should be changed back to
         * default.
         */
        if (timeUntilDefaultMissile > 0)
        {
            timeUntilDefaultMissile = timeUntilDefaultMissile - deltaTime;

            // if base missile time now expired, change back to default missile
            if (timeUntilDefaultMissile <= 0)
            {
                setBaseMissileType(DEFAULT_MISSILE_TYPE, DEFAULT_BASE_MISSILE_DELAY, 0f);

                // baseMissileType = DEFAULT_MISSILE_TYPE;
                // baseMissileDelay = DEFAULT_BASE_MISSILE_DELAY;
                // timeUntilDefaultMissile = 0f;
            }
        }

        // increment timer referencing time since base last fired
        timeSinceBaseLastFired = timeSinceBaseLastFired + deltaTime;

        // if missile timer has exceeded delay time and base is active - ready
        // to fire!!
        return (timeSinceBaseLastFired > baseMissileDelay && isActive());
    }

    /**
     * Returns the base's current missile type when base fires. Reset time when
     * last fired.
     * 
     * @return current base missile
     */
    @Override
    public BaseMissileBean fire(Direction direction)
    {
        // reset timer since base last fired
        timeSinceBaseLastFired = 0f;

        // create and return missile
        return BaseMissileFactory.createBaseMissile(this, baseMissileType, direction, model);
    }

    @Override
    public void setBaseMissileType(BaseMissileType baseMissileType, float baseMissileDelay, float timeActive)
    {
        this.baseMissileType = baseMissileType;
        this.baseMissileDelay = baseMissileDelay;
        this.timeUntilDefaultMissile = timeActive;

        // change time since last fired so new missile type fires immediately.
        if (baseMissileType != DEFAULT_MISSILE_TYPE)
        {
            timeSinceBaseLastFired = baseMissileDelay;
        }

        // change the missile type for any helper bases
        for (SpriteBase aHelperBase : helperBases)
        {
            aHelperBase.setBaseMissileType(baseMissileType, baseMissileDelay, timeActive);
        }

    }

    @Override
    public void addShield(float timeActive, float syncTime)
    {
        // reset the shield timer even if already shielded
        timeUntilShieldRemoved = timeActive;

        // only create new shields if we are not shielded
        if (!shielded)
        {
            shielded = true;

            // add shield to model's list of sprites
            shieldSprite = new ShieldBase(getX(), getY(), syncTime);
            model.addShield(shieldSprite);

            // add shield for any helper bases
            for (SpriteBase aHelperBase : helperBases)
            {
                aHelperBase.addShield(timeActive, syncTime);
            }
        }
    }

    @Override
    public void removeShield()
    {
        timeUntilShieldRemoved = 0f;
        shielded = false;

        // remove shield from model's list of sprites
        model.removeShield(shieldSprite);
        shieldSprite = null;

        // remove shield for any helper bases
        for (SpriteBase aHelperBase : helperBases)
        {
            aHelperBase.removeShield();
        }
    }

    @Override
    public void flip()
    {
        flipping = true;

        /* flip any active helper bases */
        for (SpriteBase aHelperBase : helperBases)
        {
            // only allow helper base to flip if hasn't been hit
            if (aHelperBase.isActive())
            {
                aHelperBase.flip();
            }
        }
    }

    @Override
    public void flipComplete()
    {
        flipping = false;

        // flip completed - reverse direction
        direction = (direction == Direction.UP ? Direction.DOWN : Direction.UP);

        /* update direction change for any helper bases */
        for (SpriteBase aHelperBase : helperBases)
        {
            if (aHelperBase.isActive())
            {
                aHelperBase.flipComplete();
            }
        }
    }

    @Override
    public void registerHelperBase(SpriteBase helperBase)
    {
        helperBases.add(helperBase);

        if (baseMissileType != DEFAULT_MISSILE_TYPE)
        {
            helperBase.setBaseMissileType(baseMissileType, baseMissileDelay, timeUntilDefaultMissile);
        }

        if (shielded)
        {
            // need to synchronise shield timings so they all animate in phase
            float syncTime = shieldSprite.getSynchronisation();

            helperBase.addShield(timeUntilShieldRemoved, syncTime);
        }
    }

    @Override
    public void deRegisterHelperBase(SpriteBase helperBase)
    {
        helperBases.remove(helperBase);
    }

    @Override
    public Direction getDirection()
    {
        return direction;
    }

    @Override
     public Rectangle getTouchBounds()
    {
        return new Rectangle(
                getX() - getWidth() / 2,
                getY() - getHeight() / 2,
                getWidth() + BOUNDS_BUFFER,
                getHeight() + BOUNDS_BUFFER);
     }

}
