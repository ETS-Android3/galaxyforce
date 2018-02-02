package com.danosoftware.galaxyforce.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the Data Transfer Object of Bezier Paths.
 */
public class BezierPathDTO implements PathDTO {

    private final Integer pathPoints;
    private final PointDTO start;
    private final PointDTO startControl;
    private final PointDTO finish;
    private final PointDTO finishControl;
    private final PathType pathType;

    @JsonCreator
    public BezierPathDTO(
            @JsonProperty("pathPoints") Integer pathPoints,
            @JsonProperty("start") PointDTO start,
            @JsonProperty("startControl") PointDTO startControl,
            @JsonProperty("finish") PointDTO finish,
            @JsonProperty("finishControl") PointDTO finishControl) {

        this.pathType = PathType.BEZIER;
        this.pathPoints = pathPoints;
        this.start = start;
        this.startControl = startControl;
        this.finish = finish;
        this.finishControl = finishControl;
    }

    @Override
    public PathType getType() {
        return pathType;
    }

    public Integer getPathPoints() {
        return pathPoints;
    }

    public PointDTO getStart() {
        return start;
    }

    public PointDTO getStartControl() {
        return startControl;
    }

    public PointDTO getFinish() {
        return finish;
    }

    public PointDTO getFinishControl() {
        return finishControl;
    }
}
