package com.github.johantiden.ml.jimage.shape;

import com.github.johantiden.ml.jimage.color.JTColor;

import java.util.List;

public class PolygonWithColorImpl implements PolygonWithColor {
    private final JTColor color;

    private final List<Point> points;

    public PolygonWithColorImpl(List<Point> points, JTColor color) {
        this.points = points;
        this.color = color;
    }

    @Override
    public JTColor getColor() {
        return color;
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }
}
