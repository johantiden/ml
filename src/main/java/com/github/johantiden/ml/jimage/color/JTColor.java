package com.github.johantiden.ml.jimage.color;

import com.github.johantiden.ml.util.Maths;

import java.awt.Color;

public class JTColor {

    public static final JTColor YELLOW = new JTColor(1, 1, 0);
    public static final JTColor RED = new JTColor(1, 0, 0);
    public static final JTColor GREEN = new JTColor(0, 1, 0);
    public static final JTColor BLUE = new JTColor(0, 0, 1);
    public static final JTColor WHITE = new JTColor(1, 1, 1);
    public static final JTColor GRAY = new JTColor(0.5, 0.5, 0.5);
    public static final JTColor BLACK = new JTColor(0, 0, 0);

    private final double r;
    private final double g;
    private final double b;
    private final double a;

    public JTColor(double r, double g, double b, double a) {
        verify(r, g, b, a);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public JTColor(JTColor baseColor, double a) {
        this(baseColor.getR(), baseColor.getG(), baseColor.getB(), a);
    }

    public JTColor(double r, double g, double b) {
        this(r, g, b, 1);
    }

    public static void verify(double r, double g, double b, double a) {
        verify(a, 'a');
        verify(r, 'r');
        verify(g, 'g');
        verify(b, 'b');
    }

    public static void verify(double c, char channel) {
        if (c < 0 || c > 1) {
            throw new IllegalStateException("Color '"+channel+"' value out of bounds: " + c);
        }
    }

    public Color asAwtColor() {
        return new Color(
                Maths.roundI(r*255),
                Maths.roundI(g*255),
                Maths.roundI(b*255),
                Maths.roundI(a*255));
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public double getA() {
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        JTColor jtColor = (JTColor) o;

        if (Double.compare(jtColor.r, r) != 0) { return false; }
        if (Double.compare(jtColor.g, g) != 0) { return false; }
        if (Double.compare(jtColor.b, b) != 0) { return false; }
        return Double.compare(jtColor.a, a) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(r);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(g);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(a);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
