package com.danosoftware.galaxyforce.utilities;

import com.danosoftware.galaxyforce.view.Vector2;

public class OverlapTester {

    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        return r1.lowerLeft.x < r2.lowerLeft.x + r2.width && r1.lowerLeft.x + r1.width > r2.lowerLeft.x
                && r1.lowerLeft.y < r2.lowerLeft.y + r2.height && r1.lowerLeft.y + r1.height > r2.lowerLeft.y;
    }

    public static boolean pointInRectangle(Rectangle r, Vector2 p) {
        return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x && r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
    }
}
