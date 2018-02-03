package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

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
    public Point2 convert(Point2 point) {
        return new Point2(point.getX() + xOffset, point.getY());
    }
}
