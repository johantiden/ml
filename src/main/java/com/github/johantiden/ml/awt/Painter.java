package com.github.johantiden.ml.awt;

import com.github.johantiden.ml.jimage.JTGraphics;

public interface Painter<T> {

    void paint(JTGraphics g, T t);

}
