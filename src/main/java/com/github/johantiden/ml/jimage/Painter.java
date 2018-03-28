package com.github.johantiden.ml.jimage;

import java.util.List;

public interface Painter {

    void paint(JTGraphics g, List<Double> t);

    int getChunkSize();
}
