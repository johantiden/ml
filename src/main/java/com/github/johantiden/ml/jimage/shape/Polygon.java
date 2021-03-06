package com.github.johantiden.ml.jimage.shape;

import com.google.common.collect.Lists;

import java.util.List;

public interface Polygon extends Bounded, Shape {

    List<Point> getPoints();

    // https://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
    @Override
    default boolean isInside(double x, double y) {
        int i;
        int j;
        boolean result = false;
        List<Point> points = getPoints();
        for (i = 0, j = points.size() - 1; i < points.size() ; j = i++) {
            Point pointI = points.get(i);
            Point pointJ = points.get(j);
            if ((pointI.getY() > y) != (pointJ.getY() > y) &&
                    (x < (pointJ.getX() - pointI.getX()) * (y - pointI.getY()) /
                            (pointJ.getY()- pointI.getY()) + pointI.getX())) {
                result = !result;
            }
        }
        return result;
    }

    @Override
    default double left() {
        return getPoints().stream()
                .mapToDouble(Point::getX)
                .min()
                .getAsDouble();
    }

    @Override
    default double right() {
        return getPoints().stream()
                .mapToDouble(Point::getX)
                .max()
                .getAsDouble();
    }

    @Override
    default double top() {
        return getPoints().stream()
                .mapToDouble(Point::getY)
                .min()
                .getAsDouble();
    }

    @Override
    default double bottom() {
        return getPoints().stream()
                .mapToDouble(Point::getY)
                .max()
                .getAsDouble();
    }
}
