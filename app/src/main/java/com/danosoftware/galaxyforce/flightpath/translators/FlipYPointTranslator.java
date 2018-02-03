package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

/**
 * Point translator that flips the point in the y-axis
 */
public class FlipYPointTranslator implements PointTranslator {

    // height of y dimension
    private final int height;

    public FlipYPointTranslator(int height) {
        this.height = height;
    }

    @Override
    public Point2 convert(Point2 point) {
        return new Point2(point.getX(), height - point.getY());
    }
}
