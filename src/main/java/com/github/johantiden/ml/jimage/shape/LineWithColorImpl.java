package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

public class LineWithColorImpl implements LineWithColor {
    private final JTColor color;
    private final Point a;
    private final Point b;

    public LineWithColorImpl(double x1, double y1, double x2, double y2, JTColor color) {
        this.a = new PointImpl(x1, y1);
        this.b = new PointImpl(x2, y2);
        this.color = color;
    }

    public LineWithColorImpl(Point a, Point b, JTColor color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    @Override
    public JTColor getColor() {
        return color;
    }

    @Override
    public Point getA() {
        return a;
    }

    @Override
    public Point getB() {
        return b;
    }
}
