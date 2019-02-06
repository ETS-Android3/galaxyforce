package com.danosoftware.galaxyforce.flightpath.utilities;

import com.danosoftware.galaxyforce.flightpath.paths.Point;

import java.util.ArrayList;
import java.util.List;

import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.addition;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.multiply;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.subtraction;

/**
 * Linear Mathematics Utilities
 */
public final class LinearMathematics {

    private LinearMathematics() {
    }

    /**
     * return the path points points for the current linear path.
     */
    public static List<Point> createLinearPath(
            final Point start,
            final Point finish,
            final int pathPoints) {

        List<Point> path = new ArrayList<>();

        final Point delta = subtraction(finish, start);
        for (int i = 0; i <= pathPoints; i++) {

            // t -> 0 to 1
            double t = (double) i / pathPoints;

            // pathPoint = start + ((finish - start) * t)
            Point pathPoint = addition(start, multiply(delta, t));
            path.add(pathPoint);
        }

        return path;
    }
}
