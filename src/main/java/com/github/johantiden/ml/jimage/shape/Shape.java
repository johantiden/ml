package com.github.johantiden.ml.jimage.shape;

import com.google.common.collect.Lists;

import java.util.List;

public interface Shape extends Bounded {
    boolean isInside(double x, double y);

    default boolean isInside(int x, int y) {
        return isInside((double)x, (double)y);
    }

    default boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    default boolean isAlmostInside(double x, double y) {

        List<Point> points = Lists.newArrayList(
                new PointImpl(x - 1, y - 1),
                new PointImpl(x - 1, y),
                new PointImpl(x - 1, y + 1),
                new PointImpl(x, y - 1),
                new PointImpl(x, y),
                new PointImpl(x, y + 1),
                new PointImpl(x + 1, y - 1),
                new PointImpl(x + 1, y),
                new PointImpl(x + 1, y + 1)
        );

        return points.stream().anyMatch(this::isInside);

    }
    default boolean isAlmostInside(int x, int y) {
        return isAlmostInside((double) x, (double) y);
    }

    default boolean isAlmostInside(Point point) {
        return isAlmostInside(point.getX(), point.getY());
    }

}
