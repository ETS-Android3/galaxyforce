package com.danosoftware.galaxyforce.flightpath.utilities;

import com.danosoftware.galaxyforce.flightpath.Point2;

import org.junit.Test;

import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.addition;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.multiply;
import static com.danosoftware.galaxyforce.flightpath.utilities.PointMathematics.subtraction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test Point Mathematics Utility Methods
 */
public class PointMathematicsTest {

    @Test
    public void multiplyTest() {
        final Point2 point = new Point2(10,20);
        final Point2 multipliedPoint = multiply(point, 1.5);
        assertThat(multipliedPoint.getX(), is (15));
        assertThat(multipliedPoint.getY(), is (30));
    }

    @Test
    public void additionTest() {
        final Point2 point1 = new Point2(10,20);
        final Point2 point2 = new Point2(90,30);
        final Point2 addedPoint = addition(point1, point2);
        assertThat(addedPoint.getX(), is (100));
        assertThat(addedPoint.getY(), is (50));
    }

    @Test
    public void subtractionTest() {
        final Point2 point1 = new Point2(100,50);
        final Point2 point2 = new Point2(90,30);
        final Point2 subtractedPoint = subtraction(point1, point2);
        assertThat(subtractedPoint.getX(), is (10));
        assertThat(subtractedPoint.getY(), is (20));
    }
}
