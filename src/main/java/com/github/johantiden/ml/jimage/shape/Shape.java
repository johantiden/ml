package com.github.johantiden.ml.jimage.shape;

public interface Shape {
    boolean isInside(double x, double y);

    default boolean isInside(int x, int y) {
        return isInside((double)x, (double)y);
    }

    default boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }
}
