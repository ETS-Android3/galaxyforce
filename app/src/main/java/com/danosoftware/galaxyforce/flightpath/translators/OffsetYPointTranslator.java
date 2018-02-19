package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.paths.Point;

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
    public Point convert(Point point) {
        return new Point(point.getX(), point.getY() + yOffset);
    }
}
