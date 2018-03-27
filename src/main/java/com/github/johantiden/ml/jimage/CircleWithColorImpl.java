package com.github.johantiden.ml.jimage;

public class CircleWithColorImpl extends PointWithColor implements CircleWithColor {
    public final double radius;

    public CircleWithColorImpl(double x, double y, JTColor color, double radius) {
        super(x, y, color);
        this.radius = radius;
    }

    public int diameterInt() {
        return (int) (radius * 2);
    }

    @Override
    public double getRadius() {
        return radius;
    }
}
