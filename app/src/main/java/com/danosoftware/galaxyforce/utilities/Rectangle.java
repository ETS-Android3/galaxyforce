package com.danosoftware.galaxyforce.utilities;

public class Rectangle {

    public final float left;
    public final float right;
    public final float top;
    public final float bottom;

    public Rectangle(float x, float y, float width, float height) {
        this.left = x;
        this.right = x + width;
        this.bottom = y;
        this.top = y + height;
    }
}
