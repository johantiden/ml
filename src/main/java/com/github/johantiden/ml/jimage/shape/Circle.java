package com.github.johantiden.ml.jimage.shape;

public interface Circle extends Point, Bounded {

    boolean isInside(double x, double y);

    default boolean isInside(int x, int y) {
        return isInside((double)x, (double)y);
    }

    default boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    @Override
    default double left() {
        return getX() - getRadius();
    }

    @Override
    default double right() {
        return getX() + getRadius();
    }

    @Override
    default double top() {
        return getY() - getRadius();
    }

    @Override
    default double bottom() {
        return getY() + getRadius();
    }

    double getRadius();
}
