package com.danosoftware.galaxyforce.flightpath;

import com.danosoftware.galaxyforce.exceptions.GalaxyForceException;
import com.danosoftware.galaxyforce.flightpath.dto.BezierPathDTO;
import com.danosoftware.galaxyforce.flightpath.dto.PathDTO;
import com.danosoftware.galaxyforce.flightpath.dto.PathListDTO;
import com.danosoftware.galaxyforce.flightpath.generators.BezierCurveGenerator;
import com.danosoftware.galaxyforce.flightpath.generators.PathGenerator;
import com.danosoftware.galaxyforce.flightpath.new_refactor.Path2;
import com.danosoftware.galaxyforce.flightpath.translators.PointTranslatorChain;

import java.util.ArrayList;
import java.util.List;

import static com.danosoftware.galaxyforce.flightpath.utilities.PathLoader.loadPaths;

public final class PathFactory2
{

    private PathFactory2() {
    }

    public static List<Point2> createPath(Path2 path, PointTranslatorChain translators)
    {
        List<Point2> alienPath = new ArrayList<>();

        // load path data from file
        PathListDTO pathData = loadPaths(path.getPathFile());

        for (PathDTO pathDTO: pathData.getPathList()) {
            final PathGenerator generator;
            switch (pathDTO.getType()) {
                case BEZIER:
                    BezierPathDTO bezierData = (BezierPathDTO) pathDTO;
                    generator = new BezierCurveGenerator(bezierData, translators);
                    break;
                case LINEAR:
                default:
                    throw new GalaxyForceException("Unknown path type: "+ pathDTO.getType().name());
            }
            alienPath.addAll(generator.path());
        }
        return alienPath;
    }
}
