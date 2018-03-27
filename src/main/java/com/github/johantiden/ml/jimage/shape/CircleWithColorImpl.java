package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

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

    @Override
    public boolean isInside(double x, double y) {
        return ((x - getX()) * (x - getX()) +
                (y - getY()) * (y - getY()))
                < (getRadius() * getRadius());
    }
}
