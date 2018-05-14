package com.danosoftware.galaxyforce.sprites.game.behaviours;

import com.danosoftware.galaxyforce.sprites.game.interfaces.ExplodingSprite;

/**
 * Make alien flash when hit by making sprite invisible for a short time
 * and then visible again.
 */
public class HitBehaviourFlash implements HitBehaviour {

    private final static float FLASH_DELAY_INVERT = 0.1f;
    private final static float FLASH_DELAY_TOTAL = FLASH_DELAY_INVERT * 3f;

    private boolean hit;
    private boolean visible;
    private float timeSinceFirstHit;

    public HitBehaviourFlash() {
        this.hit = false;
        this.visible = true;
        this.timeSinceFirstHit = 0f;
    }

    // when hit make sprite invisible
    @Override
    public void startHit(ExplodingSprite sprite) {
        hit = true;
        visible = false;
        timeSinceFirstHit = 0f;
        sprite.setVisible(visible);
    }

    @Override
    public boolean isHit() {
        return hit;
    }

    // after delay make sprite visible again
    @Override
    public void updateHit(ExplodingSprite sprite, float deltaTime) {
        timeSinceFirstHit = timeSinceFirstHit + deltaTime;

        // invert visibility after every delay
        if (timeSinceFirstHit > FLASH_DELAY_INVERT) {
            visible = visible ? false : true;
            sprite.setVisible(visible);
        }

        // has hit time finished
        if (timeSinceFirstHit > FLASH_DELAY_TOTAL) {
            hit = false;
            sprite.setVisible(true);
        }
    }
}
