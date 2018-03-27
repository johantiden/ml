package com.github.johantiden.ml.jimage;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.shape.CircleWithColor;
import com.github.johantiden.ml.jimage.shape.EllipseWithColor;
import com.github.johantiden.ml.jimage.shape.LineWithColor;

public interface JTGraphics {
    void drawCircle(CircleWithColor circleWithColor, double penWidth);
    void fillCircle(CircleWithColor circle);
    void fillCircleRadial(CircleWithColor circleWithColor);

    void fillAll(JTColor color);


    void drawLine(LineWithColor lineWithColor, double strokeWidth);

    void paint(int x, int y, JTImage image);
    void paint(int x, int y, JTImage image, double alpha);

    void fillRectangle(int x, int y, int width, int height, JTColor color);

    void fillEllipse(EllipseWithColor ellipseWithColor);
    void fillEllipseRadial(EllipseWithColor ellipseWithColor);

}
