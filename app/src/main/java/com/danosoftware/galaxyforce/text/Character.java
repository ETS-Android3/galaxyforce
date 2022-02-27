package com.danosoftware.galaxyforce.text;

import com.danosoftware.galaxyforce.textures.TextureRegion;

public class Character {

    private float x,y;
    private TextureRegion region;

    public Character(
            float x,
            float y,
            TextureRegion region) {
        this.x = x;
        this.y = y;
        this.region = region;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TextureRegion getRegion() {
        return region;
    }
}
