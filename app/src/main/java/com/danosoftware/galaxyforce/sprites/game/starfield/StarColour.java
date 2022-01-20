package com.danosoftware.galaxyforce.sprites.game.starfield;

import com.danosoftware.galaxyforce.models.screens.background.RgbColour;

import lombok.Getter;

@Getter
public enum StarColour {

    RED(1f, 0f, 0f),
    BLUE(0f, 0f, 1f),
    WHITE(1f, 1f, 1f);

    private final RgbColour rgb;

    StarColour(float red, float green, float blue) {
        this.rgb = new RgbColour(red, green, blue);
    }
}
