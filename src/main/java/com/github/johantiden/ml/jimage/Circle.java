package com.github.johantiden.ml.jimage;

public interface Circle extends Point, Bounded {
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
