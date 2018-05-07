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
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteBase;
import com.danosoftware.galaxyforce.sprites.game.interfaces.SpriteShield;
import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.sprites.properties.ISpriteIdentifier;
import com.danosoftware.galaxyforce.utilities.Rectangle;
import com.danosoftware.galaxyforce.view.Animation;

public class BaseHelper extends SpriteBase
{
    // default base sprite
    public static final ISpriteIdentifier BASE_SPRITE = GameSpriteIdentifier.HELPER;
    public static final ISpriteIdentifier BASE_SPRITE_FLIP = GameSpriteIdentifier.HELPER_FLIP;

    // default base missile sprite
    public static final BaseMissileType DEFAULT_MISSILE_TYPE = BaseMissileType.SIMPLE;

    /*
     * small buffer so that increases bounds is slightly outside sprite area.
     * helps if finger touch just misses sprite.
     */
    private static final int BOUNDS_BUFFER = 20;


    /* how much energy will be lost by another sprite when this sprite hits it */
    private static final int HIT_ENERGY = 2;

    /* energy of this sprite */
    private static final int ENERGY = 2;

    /* helper base x offset from primary base's x position */
    private int xOffset;

    /* variable to store current base missile type */
    private BaseMissileType baseMissileType = null;

    /* references current direction of base */
    private Direction direction;

    /* is base flipping */
    private boolean flipping = false;

    /* does base have shield */
    private boolean shielded = false;

    /* reference to base's shield */
    private SpriteShield shieldSprite = null;

    /* reference to parent base */
    private SpriteBase parentBase = null;

    /* reference to model */
    private GameHandler model = null;

    /* reference to sound player and sounds */
    private final SoundPlayer soundPlayer;
    private final Sound explosionSound;

    private static final Animation FLIP_UP_TO_DOWN = new Animation(GameConstants.DIRECTION_CHANGE_TRANSITION_TIME,
            GameSpriteIdentifier.HELPER, GameSpriteIdentifier.HELPER_SPIN_1, GameSpriteIdentifier.HELPER_SPIN_2,
            GameSpriteIdentifier.HELPER_SPIN_3, GameSpriteIdentifier.HELPER_FLAT, GameSpriteIdentifier.HELPER_FLIP_SPIN_3,
            GameSpriteIdentifier.HELPER_FLIP_SPIN_2, GameSpriteIdentifier.HELPER_FLIP_SPIN_1, GameSpriteIdentifier.HELPER_FLIP);

    // flip from down to up animation
    private static final Animation FLIP_DOWN_TO_UP = new Animation(GameConstants.DIRECTION_CHANGE_TRANSITION_TIME,
            GameSpriteIdentifier.HELPER_FLIP, GameSpriteIdentifier.HELPER_FLIP_SPIN_1, GameSpriteIdentifier.HELPER_FLIP_SPIN_2,
            GameSpriteIdentifier.HELPER_FLIP_SPIN_3, GameSpriteIdentifier.HELPER_FLAT, GameSpriteIdentifier.HELPER_SPIN_3,
            GameSpriteIdentifier.HELPER_SPIN_2, GameSpriteIdentifier.HELPER_SPIN_1, GameSpriteIdentifier.HELPER);

    private BaseHelper(SpriteBase parentBase, int xStart, int yStart, ISpriteIdentifier spriteId, Direction direction, GameHandler model,
            int xOffset)
    {
        /*
         * Energy set to zero in constructor since energy is over-ridden in this
         * class. Actual base energy comes from the energy bar object.
         */
        super(xStart, yStart, spriteId, new ExplodeBehaviourSimple(), ENERGY, HIT_ENERGY);
        super.updateBounds();
        this.parentBase = parentBase;
        this.direction = direction;
        this.model = model;
        this.xOffset = xOffset;

        this.soundPlayer = SoundPlayerSingleton.getInstance();

        // get sound effects from sound bank
        SoundEffectBank soundBank = SoundEffectBankSingleton.getInstance();
        this.explosionSound = soundBank.get(SoundEffect.EXPLOSION);

        // default missile type
        this.baseMissileType = DEFAULT_MISSILE_TYPE;

        /* base is not currently flipping */
        flipping = false;

        /* base is not currently shielded */
        shielded = false;

        /* register new helper base with it's parent */
        parentBase.registerHelperBase(this);
    }

    /**
     * return an instance of base at supplied position
     */
    public static SpriteBase newBase(SpriteBase parentBase, GameHandler model, int xOffset)
    {

        Direction direction = parentBase.getDirection();
        ISpriteIdentifier sprite = (direction == Direction.UP ? BaseHelper.BASE_SPRITE : BaseHelper.BASE_SPRITE_FLIP);

        return new BaseHelper(parentBase, parentBase.getX(), parentBase.getY(), sprite, direction, model, xOffset);
    }

    /**
     * override set x for helper bases so they apply an x offset from primary
     * base
     */
    @Override
    public void setX(int x)
    {
        super.setX(x + xOffset);
    }

    @Override
    public void move(float deltaTime)
    {
        // move sprite bounds
        updateBounds();

        /* use superclass for any explosions */
        super.move(deltaTime);

        // if base has a shield, move it with base
        if (shielded)
        {
            shieldSprite.setX(getX());
            shieldSprite.setY(getY());
        }
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

        // de-register helper base when exploding
        parentBase.deRegisterHelperBase(this);
    }

    public void setDestroyed()
    {
        super.setDestroyed();

        // if base has a shield, remove it
        if (shielded)
        {
            removeShield();
        }

        // de-register helper base when destroyed
        parentBase.deRegisterHelperBase(this);
    }

    @Override
    public void loseEnergy(int loseEnergy)
    {
        // can not lose energy if shielded
        if (!shielded)
        {
            super.loseEnergy(loseEnergy);
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
        throw new IllegalAccessError("Add Energy for Helper Base not permitted.");
    }

    @Override
    public void moveBase(float weightingX, float weightingY, float deltaTime)
    {
        throw new IllegalAccessError("Move Base for Helper Base not permitted.");
    }

    @Override
    public BaseMissileBean fire(Direction direction)
    {
        // create and return missile
        return BaseMissileFactory.createBaseMissile(this, baseMissileType, direction, model);
    }

    @Override
    public void setBaseMissileType(BaseMissileType baseMissileType, float baseMissileDelay, float timeActive)
    {
        this.baseMissileType = baseMissileType;
    }

    @Override
    public void addShield(float timeActive, float syncTime)
    {
        shielded = true;

        // add shield to model's list of sprites
        shieldSprite = new ShieldHelper(getX(), getY(), syncTime);
        model.addShield(shieldSprite);
    }

    @Override
    public void removeShield()
    {
        shielded = false;

        // remove shield from model's list of sprites
        model.removeShield(shieldSprite);
        shieldSprite = null;
    }

    @Override
    public void flip()
    {
        flipping = true;
    }

    @Override
    public void flipComplete()
    {
        flipping = false;

        // flip completed - reverse direction
        direction = (direction == Direction.UP ? Direction.DOWN : Direction.UP);
    }

    @Override
    public void updateDirectionChange(float directionChangeStateTime)
    {
        // choose animation loop (based on current direction)
        Animation animation = (direction == Direction.UP ? FLIP_UP_TO_DOWN : FLIP_DOWN_TO_UP);

        // set base sprite using animation loop and time through animation
        setSpriteIdentifier(animation.getKeyFrame(directionChangeStateTime, Animation.ANIMATION_NONLOOPING));
    }

    @Override
    public void registerHelperBase(SpriteBase helperBase)
    {
        throw new IllegalAccessError("Registration of Helper Base not permitted.");
    }

    @Override
    public void deRegisterHelperBase(SpriteBase helperBase)
    {
        throw new IllegalAccessError("De-registration of Helper Base not permitted.");
    }

    @Override
    public Rectangle getTouchBounds() {
        return new Rectangle(
                getX() - getWidth() / 2,
                getY() - getHeight() / 2,
                getWidth() + BOUNDS_BUFFER,
                getHeight() + BOUNDS_BUFFER);
    }

    @Override
    public Direction getDirection()
    {
        return direction;
    }
}
