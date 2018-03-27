package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.EllipseWithColor;
import com.github.johantiden.ml.jimage.JTColor;
import com.github.johantiden.ml.jimage.Point;

public class WormBlob implements EllipseWithColor {
    public double x;
    public double y;
    public JTColor color;
    public double width;
    public double height;
    public double angle;

    public WormBlob(double x, double y, JTColor color, double width, double height, double angle) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    public WormBlob copy() {
        return new WormBlob(x, y, color, width, height, angle);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Point plus(double x, double y) {
        return new WormBlob(getX()+x, getY()+y, color, width, height, angle);
    }

    @Override
    public JTColor getColor() {
        return color;
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
