package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

/**
 * Point translators convert supplied points
 */
public interface PointTranslator {

    /**
     * Return a converted version of the supplied point
     *
     * @param point
     * @return converted point
     */
    Point2 convert(final Point2 point);
}
