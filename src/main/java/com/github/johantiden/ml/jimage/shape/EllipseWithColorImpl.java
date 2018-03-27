package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

/***
 * Gardener's Ellipse
 */
public class EllipseWithColorImpl implements EllipseWithColor {
    public final double x1;
    public final double y1;
    public final double x2;
    public final double y2;
    public final double length;
    public final JTColor color;

    public EllipseWithColorImpl(double x1, double y1, double x2, double y2, double length, JTColor color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.length = length;
        this.color = color;
    }

    @Override
    public JTColor getColor() {
        return color;
    }

    @Override
    public double getCentreRatio(double x, double y) {
//
//        double dx1 = x-x1;
//        double dy1 = y-y1;
//        double dx2 = x-x2;
//        double dy2 = y-y2;

        double z1 = Point.distance(x1, y1, x, y);
        double z2 = Point.distance(x2, y2, x, y);
        return (z1+z2)/length;
    }

    @Override
    public double left() {
        return Math.min(x1, x2)-length;
    }

    @Override
    public double right() {
        return Math.max(x1, x2)+length;
    }

    @Override
    public double top() {
        return Math.min(y1, y2)-length;
    }

    @Override
    public double bottom() {
        return Math.max(y1, y2)+length;
    }

    @Override
    public boolean isInside(double x, double y) {
        return getCentreRatio(x, y) < 1;
    }
}
