package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.paths.Point;

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
    public Point convert(Point point) {
        return new Point(point.getX(), height - point.getY());
    }
}
