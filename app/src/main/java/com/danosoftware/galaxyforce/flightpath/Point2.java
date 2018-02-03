package com.danosoftware.galaxyforce.flightpath;

import com.danosoftware.galaxyforce.flightpath.dto.PointDTO;

/**
 * Represents a point object (an x-y co-ordinate).
 * Normally used to represent a position on a path.
 */
public class Point2
{

    private final int x;
    private final int y;

    public Point2(PointDTO point)
    {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Point2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
