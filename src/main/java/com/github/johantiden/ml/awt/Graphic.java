package com.github.johantiden.ml.awt;

import com.github.johantiden.ml.util.Maths;

import java.awt.*;

public class Graphic {
    public static void fillCircle(Graphics2D g, double x, double y, double radius) {
        g.fillOval(
                Maths.roundI(x - radius),
                Maths.roundI(y - radius),
                Maths.roundI(radius * 2),
                Maths.roundI(radius * 2));
    }

    public static void drawLine(Graphics2D g, double x, double y, double x1, double y1) {

        g.drawLine(
                Maths.roundI(x),
                Maths.roundI(y),
                Maths.roundI(x1),
                Maths.roundI(y1)
        );

    }

    public static void fillLine(Graphics2D g, double x1, double y1, double x2, double y2, double size) {


        g.setStroke(new BasicStroke((float) size));
        g.drawLine(
                Maths.roundI(x1),
                Maths.roundI(y1),
                Maths.roundI(x2),
                Maths.roundI(y2)
        );



    }
}
