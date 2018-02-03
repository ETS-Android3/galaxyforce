package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

/**
 * Point translator that flips the point in the x-axis
 */
public class FlipXPointTranslator implements PointTranslator {

    // width of x dimension
    private final int width;

    public FlipXPointTranslator(int width) {
        this.width = width;
    }

    @Override
    public Point2 convert(Point2 point) {
        return new Point2(width - point.getX(), point.getY());
    }
}
