package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

public class EllipseWithColorImpl extends PointWithColor implements EllipseWithColor {
    private final double width;
    private final double height;
    private final double angle;

    public EllipseWithColorImpl(double centerX, double centerY, double width, double height, double angle, JTColor color) {
        super(centerX, centerY, color);
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getAngle() {
        return angle;
    }
}
