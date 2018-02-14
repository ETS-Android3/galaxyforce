package com.danosoftware.galaxyforce.flightpath.generators;

import com.danosoftware.galaxyforce.flightpath.dto.BezierPathDTO;
import com.danosoftware.galaxyforce.flightpath.dto.LinearPathDTO;
import com.danosoftware.galaxyforce.flightpath.paths.Point;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;

import java.util.ArrayList;
import java.util.List;

import static com.danosoftware.galaxyforce.flightpath.utilities.BezierMathematics.createBezierPath;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.convertAndTranslatePoint;

/**
 * Create line from provided control points
 */
public class LinearGenerator implements PathGenerator {

    private final Point start;
    private final Point finish;
    private final int pathPoints;

    /**
     * Instantiate generator by extracting and converting the linear data points
     * and then translating them to their new positions based on the provided
     * translators (e.g. x-axis flip).
     *
     * @param linearData
     * @param translators
     */
    public LinearGenerator(LinearPathDTO linearData, PointTranslatorChain translators)
    {
        this.start = convertAndTranslatePoint(linearData.getStart(), translators);
        this.finish = convertAndTranslatePoint(linearData.getFinish(), translators);
        this.pathPoints = linearData.getPathPoints();
    }

    /**
     * return the linear points for the current linear object.
     *
     * @return array of points representing line
     */
    @Override
    public List<Point> path()
    {
        List<Point> path = new ArrayList<Point>();

        /* calculate x, y delta and distance to be travelled */
        double xDelta = finish.getX() - start.getX();
        double yDelta = finish.getY() - start.getY();
        double distance = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));

        /* number of points on line */
        double delta = (double) pathPoints / distance;

        for (double t = 0; t < 1; t = t + delta)
        {
            /* calculate current position from parametric equation */
            double xPos = start.getX() + (xDelta * t);
            double yPos = start.getY() + (yDelta * t);

            /* xPos, yPos refers to centre of alien sprite */
            Point position = new Point((int) Math.round(xPos), (int) Math.round(yPos));
            path.add(position);
        }

        return path;
    }
}
