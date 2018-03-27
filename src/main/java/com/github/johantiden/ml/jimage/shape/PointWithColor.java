package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.Colorful;
import com.github.johantiden.ml.jimage.color.JTColor;

public class PointWithColor extends PointImpl implements Colorful {
    public final JTColor color;

    public PointWithColor(double x, double y, JTColor color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public JTColor getColor() {
        return color;
    }

}
