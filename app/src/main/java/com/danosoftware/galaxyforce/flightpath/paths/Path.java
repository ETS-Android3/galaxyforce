package com.danosoftware.galaxyforce.flightpath.paths;

/**
 * enum containing all the possible flight paths for aliens.
 *
 * Useful Bezier Curve sites:
 *
 * http://cubic-bezier.com/#.06,.93,0,1.01
 * https://www.desmos.com/calculator/cahqdxeshd
 *
 */
public enum Path {

    TEST("testPathData.json"),

    /*
    * Triangular path where aliens cross-over where the top of the triangle.
    */
    TRIANGLULAR_PATH("TRIANGLULAR_PATH.json"),

    /*
     * Path where aliens move from left to right in an arc. Aliens originally
     * rise as they move to right. At top of arc at mid-way point and then move
     * downwards in arc as they continue to move right.
     */
    SINGLE_ARC("SINGLE_ARC.json"),

    /**
     * space invader style attack - gradually moving across screen and then down
     */
    SPACE_INVADER("SPACE_INVADER.json"),

    /**
     * Figure of eight attack. Starting at top right looping round in figure
     * figure of eight and finishing at top right.
     */
    FIGURE_OF_EIGHT("FIGURE_OF_EIGHT.json"),

    /**
     * Bezier curve from bottom left, crosses to right hand-side then leaves at
     * top right.
     */
    BEZIER_STEP_UP("BEZIER_STEP_UP.json"),

    /**
     * Alien starts at top left edge, moves straight down, pauses and then exit
     * on opposite right-side of screen.
     */
    EXIT_STAGE_RIGHT("EXIT_STAGE_RIGHT.json"),

    /**
     * straight line down screen from top to bottom. designed to be adjusted in
     * x-axis so any straight line path from top to bottom at any x-position can
     * be used.
     */
    STRAIGHT_DOWN("STRAIGHT_DOWN.json"),

    /**
     * straight line down screen from top to near bottom then bounce up again.
     * designed to be adjusted in x-axis so any straight line path from top to
     * bottom at any x-position can be used.
     */
    BOUNCE_DOWN_AND_UP("BOUNCE_DOWN_AND_UP.json"),

    WAVEY_HORIZONTAL("WAVEY_HORIZONTAL.json"),

    BEZIER_PATH_01("BEZIER_PATH_01.json"),

    /**
     * Drops from top-left down to valley and up again to top right.
     */
    VALLEY_DROP("VALLEY_DROP.json"),

    /*
     * Bell Curve path. Start bottom left, rises to peak in middle then drops to
     * bottom right.
     */
    BELL_CURVE("BELL_CURVE.json"),
    /*
     * Starts bottom left, twists around loop and exits top right
     */
    LOOPER("LOOPER.json"),

    /*
     * Tear drop shaped path starting from and ending at top middle.
     */
    TEAR_DROP("TEAR_DROP.json"),

    BEZIER_DEMO("BEZIER_DEMO.json"),

    STARIGHT_LINE_01("STARIGHT_LINE_01.json"),

    STARIGHT_LINE_DEMO("STARIGHT_LINE_DEMO.json"),

    ORIGINAL_ALIEN_PATH_01("ORIGINAL_ALIEN_PATH_01.json"),

    ORIGINAL_ALIEN_PATH_02("ORIGINAL_ALIEN_PATH_02.json"),

    ORIGINAL_ALIEN_PATH_03("ORIGINAL_ALIEN_PATH_03.json"),

    ORIGINAL_ALIEN_PATH_04("ORIGINAL_ALIEN_PATH_04.json"),

    ORIGINAL_ALIEN_PATH_05("ORIGINAL_ALIEN_PATH_05.json"),

    CIRCULAR_DEMO("CIRCULAR_DEMO.json");


    
    // file name holding path data
    private final String pathFile;

    Path(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getPathFile() {
        return pathFile;
    }
}
