package com.github.johantiden.ml.jimage.shape;

public interface Point {

    default int xInt() {
        return (int) Math.round(getX());
    }

    default int yInt() {
        return (int) Math.round(getY());
    }

    default double distanceSquaredFrom(double x, double y) {
        return distanceSquared(getX(), getY(), x, y);
    }

    default double distanceFrom(double x, double y) {
        return distance(getX(), getY(), x, y);
    }

    static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(distanceSquared(x1, y1, x2, y2));
    }

    static double distanceSquared(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    @Override
    String toString();

    double getX();

    double getY();

    Point plus(double x, double y);
}
