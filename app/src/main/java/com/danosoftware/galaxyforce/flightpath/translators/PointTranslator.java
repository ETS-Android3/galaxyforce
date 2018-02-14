package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.paths.Point;

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
    Point convert(final Point point);
}
