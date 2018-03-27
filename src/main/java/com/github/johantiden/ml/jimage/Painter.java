package com.github.johantiden.ml.jimage;

@FunctionalInterface
public interface Painter<T> {

    void paint(JTGraphics g, T t);

}
