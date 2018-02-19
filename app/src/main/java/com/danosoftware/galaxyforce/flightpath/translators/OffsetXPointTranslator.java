package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.paths.Point;

/**
 * Point translator that offsets the point in the x-axis
 */
public class OffsetXPointTranslator implements PointTranslator {

    // offset in x dimension
    private final int xOffset;

    public OffsetXPointTranslator(int xOffset) {
        this.xOffset = xOffset;
    }

    @Override
    public Point convert(Point point) {
        return new Point(point.getX() + xOffset, point.getY());
    }
}
