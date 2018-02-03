package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

/**
 * Point translator that offsets the point in the y-axis
 */
public class OffsetYPointTranslator implements PointTranslator {

    // offset in y dimension
    private final int yOffset;

    public OffsetYPointTranslator(int yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public Point2 convert(Point2 point) {
        return new Point2(point.getX(), point.getY() + yOffset);
    }
}
