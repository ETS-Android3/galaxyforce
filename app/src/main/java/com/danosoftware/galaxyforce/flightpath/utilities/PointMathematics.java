package com.danosoftware.galaxyforce.flightpath.utilities;

import com.danosoftware.galaxyforce.flightpath.Point;
import com.danosoftware.galaxyforce.flightpath.Point2;
import com.danosoftware.galaxyforce.flightpath.dto.PointDTO;


/**
 * Point Mathematics Utilities
 */
public final class PointMathematics {

    private PointMathematics() {
    }

    /**
     * Convert from point DTO (used for JSON deserialization)
     * to point object used across app
     *
     * @param pointData
     * @return point
     */
    public static Point2 convertPoint(PointDTO pointData) {
        return new Point2(pointData.getX(), pointData.getY());
    }

    /**
     * Return a point position scaled by multiplier.
     *
     * @param point
     * @param multiplier
     * @return scaled point
     */
    public static Point2 multiply(Point2 point, double multiplier)
    {
        return new Point2(
                (int) (point.getX() * multiplier),
                (int) (point.getY() * multiplier));
    }

    /**
     * Return a point position representing the sum of two supplied points
     *
     * @param point1
     * @param point2
     * @return summed point
     */
    public static Point2 addition(Point2 point1, Point2 point2)
    {
        return new Point2(
                point1.getX() + point2.getX(),
                point1.getY() + point2.getY());
    }

    /**
     * Return a point position representing the subtraction of two supplied points
     *
     * @param point1
     * @param point2
     * @return subtracted point
     */
    public static Point2 subtraction(Point2 point1, Point2 point2)
    {
        return new Point2(
                point1.getX() - point2.getX(),
                point1.getY() - point2.getY());
    }
}