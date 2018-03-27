package com.github.johantiden.ml.jimage;

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
