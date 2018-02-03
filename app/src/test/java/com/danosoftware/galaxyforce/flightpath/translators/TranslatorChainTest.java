package com.danosoftware.galaxyforce.flightpath.translators;

import com.danosoftware.galaxyforce.flightpath.Point2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Checks that a chain of point translators correctly converts an example point
 */
public class TranslatorChainTest {

    @Test
    public void translateTest() {

        Point2 point = new Point2(10, 20);

        // build up a chain of translators
        PointTranslatorChain translatorChain = new PointTranslatorChain()
                .add(new FlipXPointTranslator(100))
                .add(new FlipYPointTranslator(200))
                .add(new OffsetXPointTranslator(50))
                .add(new OffsetYPointTranslator(75));

        // converts the point using the build chain
        // x = 10 -> flip:(100-10) = 90 -> offset:(90+50) = 140
        // y = 20 -> flip:(200-20) = 180 -> offset:(180+75) = 255
        Point2 convertedPoint = translatorChain.translate(point);

        assertThat(convertedPoint.getX(), is(140));
        assertThat(convertedPoint.getY(), is(255));
    }
}
