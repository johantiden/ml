package com.github.johantiden.ml.jimage.shape;

public interface Line extends Bounded {

    Point getA();

    Point getB();

    @Override
    default double left() {
        return Math.min(getA().getX(), getB().getX());
    }

    @Override
    default double top() {
        return Math.min(getA().getY(), getB().getY());
    }

    @Override
    default double right() {
        return Math.max(getA().getX(), getB().getX());
    }

    @Override
    default double bottom() {
        return Math.max(getA().getY(), getB().getY());
    }
}
