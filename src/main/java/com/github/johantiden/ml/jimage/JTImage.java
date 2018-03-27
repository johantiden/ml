package com.github.johantiden.ml.jimage;

import java.io.Serializable;

public interface JTImage extends Serializable {
    int getWidth();

    int getHeight();

    JTColor getColorAt(int x, int y);

    JTGraphics getGraphics();

    JTImage copy();

    void setPixel(int x, int y, JTColor pixel);

    void setPixel(int x, int y, double r, double g, double b);
}
