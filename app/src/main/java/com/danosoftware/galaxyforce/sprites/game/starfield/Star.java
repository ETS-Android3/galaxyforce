package com.danosoftware.galaxyforce.sprites.game.starfield;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;

import lombok.Builder;
import lombok.Data;

/**
 * Star sprite that drifts down the screen over time and animates.
 * When a star reaches the bottom of the screen, it returns to the top.
 * Many stars will be contained within an animated star-field.
 */
@Builder
@Data
public class Star {

    // offset used for star animation
    float animationStateTime;

    // star's position
    float x, y;

    // star's colour animation
    StarColourAnimation colourAnimation;

    public void animate(float deltaTime) {
        animationStateTime += deltaTime;
    }

    public RgbColour colour() {
        StarColour colour = colourAnimation.getKeyFrame(animationStateTime);
        return colour.getRgb();
    }
}
