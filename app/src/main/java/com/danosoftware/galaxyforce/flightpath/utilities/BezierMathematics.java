package com.danosoftware.galaxyforce.flightpath.utilities;

import com.danosoftware.galaxyforce.flightpath.generators.BezierCurveGenerator;
import com.danosoftware.galaxyforce.flightpath.Point2;

import java.util.ArrayList;
import java.util.List;

import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.addition;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.multiply;

/**
 * Bezier Mathematics Utilities
 */
public final class BezierMathematics {

    private BezierMathematics() {
    }

    /**
     * return the bezier curve points for the current bezier curve path.
     *
     * @param start
     * @param startControl
     * @param finish
     * @param finishControl
     * @param pathPoints
     * @return array of points representing Bezier curve
     */
    public static List<Point2> createBezierPath(
            final Point2 start,
            final Point2 startControl,
            final Point2 finish,
            final Point2 finishControl,
            final int pathPoints)
    {
        List<Point2> path = new ArrayList<>();

        /* calculate point for each segment and add to path */
        for (int i = 0; i <= pathPoints; i++)
        {
            double t = (double) i / pathPoints;
            path.add(
                    calculateBezierPoint(
                            start,
                            startControl,
                            finish,
                            finishControl,
                            t)
            );
        }

        return path;
    }

    /**
     * calculate current point on bezier curve based control points and on parameter t
     *
     * @param start
     * @param startControl
     * @param finish
     * @param finishControl
     * @param t
     * @return current point on bezier curve
     */
    private static Point2 calculateBezierPoint(final Point2 start,
                                               final Point2 startControl,
                                               final Point2 finish,
                                               final Point2 finishControl,
                                               double t)
    {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttt = tt * t;

        /* stage 1 - uuu * start */
        Point2 stage1 = multiply(start, uuu);

        /* stage 2 - stage 1 + (3 * uu * t * startControl) */
        Point2 stage2 = addition(stage1, multiply(startControl, (3 * uu * t)));

        /* stage 3 - stage 2 + ( 3 * u * tt * finishControl) */
        Point2 stage3 = addition(stage2, multiply(finishControl, (3 * u * tt)));

        /* stage 4 - stage 3 + (ttt * finish) */
        return addition(stage3, multiply(finish, ttt));
    }
}
