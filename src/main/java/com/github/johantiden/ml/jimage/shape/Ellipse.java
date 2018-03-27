package com.github.johantiden.ml.jimage.shape;

public interface Ellipse extends Point, Bounded {
    @Override
    default double left() {
        return getX() - getWidth()*2;
    }

    @Override
    default double right() {
        return getX() + getWidth()*2;
    }

    @Override
    default double top() {
        return getY() - getHeight()*2;
    }

    @Override
    default double bottom() {
        return getY() + getHeight()*2;
    }

    double getWidth();
    double getHeight();
    double getAngle();
}
