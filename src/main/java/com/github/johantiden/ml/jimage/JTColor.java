package com.github.johantiden.ml.jimage;

import java.awt.Color;

public interface JTColor {

    JTColor YELLOW = new JTColorImpl(1, 1, 0);
    JTColor RED = new JTColorImpl(1, 0, 0);
    JTColor GREEN = new JTColorImpl(0, 1, 0);
    JTColor BLUE = new JTColorImpl(0, 0, 1);

    JTColor WHITE = new JTColorImpl(1, 1, 1);
    JTColor GRAY = new JTColorImpl(0.5, 0.5, 0.5);
    JTColor BLACK = new JTColorImpl(0, 0, 0);

    double getR();
    double getG();
    double getB();
    double getA();

    Color asAwtColor();
}
