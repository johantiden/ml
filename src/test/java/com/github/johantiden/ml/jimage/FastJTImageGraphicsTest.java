package com.github.johantiden.ml.jimage;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.color.JTColorImpl;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class FastJTImageGraphicsTest {

    @Test
    public void testFillRectangleFullAlpha() throws Exception {

        FastJTImage i = new FastJTImage(2, 2);
        JTGraphics g = i.getGraphics();

        g.fillRectangle(0, 0, 1, 1, JTColor.RED);

        assertThat(i.getColorAt(0, 0), is(JTColor.RED));
        assertThat(i.getColorAt(1, 0), is(JTColor.BLACK));
        assertThat(i.getColorAt(0, 1), is(JTColor.BLACK));
        assertThat(i.getColorAt(1, 1), is(JTColor.BLACK));

    }

    @Test
    public void testFillRectangleHalfAlpha() throws Exception {

        FastJTImage i = new FastJTImage(2, 2);
        JTGraphics g = i.getGraphics();

        g.fillRectangle(0, 0, 1, 1, new JTColorImpl(JTColor.RED, 0.5));

        double e = 0.0001;
        assertThat(i.getColorAt(0, 0).getR(), greaterThan(0.5-e));
        assertThat(i.getColorAt(0, 0).getR(), lessThan(0.5+e));
        assertThat(i.getColorAt(0, 0).getG(), is(0.0));
        assertThat(i.getColorAt(0, 0).getB(), is(0.0));
        assertThat(i.getColorAt(1, 0), is(JTColor.BLACK));
        assertThat(i.getColorAt(0, 1), is(JTColor.BLACK));
        assertThat(i.getColorAt(1, 1), is(JTColor.BLACK));

    }




}