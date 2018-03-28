package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

public class RectangleWithColor implements ShapeWithColor {

    public final double x;
    public final double y;
    public final double width;
    public final double height;
    public final JTColor color;

    public RectangleWithColor(double x, double y, double width, double height, JTColor color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    @Override
    public boolean isInside(double x, double y) {

        return
                x >= left() &&
                x <= right() &&
                y >= top() &&
                y <= bottom();

    }

    @Override
    public double left() {
        return x;
    }

    @Override
    public double right() {
        return x + width;
    }

    @Override
    public double top() {
        return y;
    }

    @Override
    public double bottom() {
        return y + height;
    }

    @Override
    public JTColor getColor() {
        return color;
    }
}
