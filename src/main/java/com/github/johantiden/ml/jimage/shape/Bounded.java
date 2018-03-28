package com.github.johantiden.ml.jimage.shape;


public interface Bounded {
    double left();
    double right();
    double top();
    double bottom();

    default double getWidth() {
        return right() - left();
    }

    default double getHeight() {
        return bottom() - top();
    }
}
