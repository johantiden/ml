package com.github.johantiden.ml.evolutionary.worm;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.shape.EllipseWithColorImpl;

public class WormBlob extends EllipseWithColorImpl {


    public WormBlob(double x1, double y1, double x2, double y2, double length, JTColor color) {
        super(x1, y1, x2, y2, length, color);
    }

    public WormBlob copy() {
        return new WormBlob(x1, y1, x2, y2, length, color);
    }

}
