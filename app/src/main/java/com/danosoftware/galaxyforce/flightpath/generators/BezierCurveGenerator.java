package com.danosoftware.galaxyforce.flightpath.generators;

import com.danosoftware.galaxyforce.flightpath.Point;
import com.danosoftware.galaxyforce.flightpath.Point2;
import com.danosoftware.galaxyforce.flightpath.dto.BezierPathDTO;

import java.util.List;

import static com.danosoftware.galaxyforce.flightpath.utilities.BezierMathematics.createBezierPath;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.convertPoint;

/**
 * Create bezier curve from provided control points
 */
public class BezierCurveGenerator implements PathGenerator
{

    private final Point2 start;
    private final Point2 startControl;
    private final Point2 finish;
    private final Point2 finishControl;
    private final int pathPoints;

    public BezierCurveGenerator(BezierPathDTO bezierData)
    {
        this.start = convertPoint(bezierData.getStart());
        this.startControl = convertPoint(bezierData.getStartControl());
        this.finish = convertPoint(bezierData.getFinish());
        this.finishControl = convertPoint(bezierData.getFinishControl());
        this.pathPoints = bezierData.getPathPoints();
    }

    /**
     * return the bezier curve points for the current bezier curve object.
     * 
     * @return array of points representing Bezier curve
     */
    @Override
    public List<Point2> path()
    {
        return createBezierPath(
                start,
                startControl,
                finish,
                finishControl,
                pathPoints);
    }
}
