package com.danosoftware.galaxyforce.flightpath;

import java.util.List;

public interface FlightPath
{

    /* returns an array of points to append to existing path */
    public List<Point> addPath();

}
