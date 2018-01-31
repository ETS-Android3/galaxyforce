package com.danosoftware.galaxyforce.sprites.game.interfaces;

import com.danosoftware.galaxyforce.sprites.properties.GameSpriteIdentifier;
import com.danosoftware.galaxyforce.view.Animation;

/**
 * Represents a missile hit sprite that is displayed when a missile hits an
 * alien.
 * 
 * The missile hit is displayed for a set amount of seconds and then destroyed.
 */
public abstract class SpriteHitGlow extends MovingSprite
{
    /* missile hit has no energy */
    private static final int ENERGY = 0;

    /* missile hit causes no energy damage to a base */
    private static final int HIT_ENERGY = 0;

    /* missile hit animation */
    private static final Animation HIT_ANIMATION = new Animation(0.1f, GameSpriteIdentifier.MISSILE_HIT_1,
            GameSpriteIdentifier.MISSILE_HIT_3);

    /* timer to store how long missile hit has been displayed */
    private float timeDisplayed = 0f;

    public SpriteHitGlow(int xStart, int yStart)
    {
        super(xStart, yStart, HIT_ANIMATION.getKeyFrame(0, Animation.ANIMATION_NONLOOPING), ENERGY, HIT_ENERGY, true);
        timeDisplayed = 0f;
    }

    @Override
    public void move(float deltaTime)
    {
        timeDisplayed += deltaTime;

        // once animation has finished, destroy the missile flash
        if (HIT_ANIMATION.isAnimationComplete())
        {
            setState(SpriteState.DESTROYED);
        }
        else
        {
            setSpriteIdentifier(HIT_ANIMATION.getKeyFrame(timeDisplayed, Animation.ANIMATION_NONLOOPING));
        }
    }
}
