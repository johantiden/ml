package com.github.johantiden.ml.jimage;

public interface Point {

    default int xInt() {
        return (int) Math.round(getX());
    }

    default int yInt() {
        return (int) Math.round(getY());
    }

    default double distanceSquaredFrom(double x, double y) {
        double dx = getX() - x;
        double dy = getY() - y;
        return dx * dx + dy * dy;
    }

    default double distanceFrom(double x, double y) {
        return Math.sqrt(distanceSquaredFrom(x, y));
    }

    @Override
    String toString();

    double getX();

    double getY();

    Point plus(double x, double y);
}
