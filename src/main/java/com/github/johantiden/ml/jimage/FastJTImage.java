package com.github.johantiden.ml.jimage;

import java.util.Arrays;

public class FastJTImage implements JTImage {

    private final int width;
    private final int height;
    private final double[] r;
    private final double[] g;
    private final double[] b;

    public FastJTImage(int width, int height) {
        this(
                width, height,
                new double[width * height],
                new double[width * height],
                new double[width * height]);
    }

    public FastJTImage(int width, int height, double[] r, double[] g, double[] b) {
        this.width = width;
        this.height = height;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    int getIndex(int x, int y) {
        verify(x, y);
        return y * width + x;
    }

    private void verify(int x, int y) {
        if (y >= height) {
            throw new IllegalArgumentException("Trying to access out of bounds. height: " + height + " y:" + y);
        }

        if (x >= width) {
            throw new IllegalArgumentException("Trying to access out of bounds. width: " + width + " x:" + x);
        }
    }


    @Override
    public JTColor getColorAt(int x, int y) {
        int index = getIndex(x, y);
        if (index >= r.length) {
            throw new IllegalArgumentException("Index out of bounds! x:" + x + " y:" + y + " size: " + r.length + " index:" + index + '.');
        }
        return new JTColorImpl(
                r[index],
                g[index],
                b[index]);
    }

    @Override
    public void setPixel(int x, int y, JTColor color) {
        setPixel(
                x,
                y,
                color.getR(),
                color.getG(),
                color.getB());
    }

    @Override
    public void setPixel(int x, int y, double r, double g, double b) {
        int index = getIndex(x, y);
        setPixel(index, r, g, b);
    }

    void setPixel(int index, double r, double g, double b) {
        JTColorImpl.verify(r, g, b, 1);
        this.r[index] = r;
        this.g[index] = g;
        this.b[index] = b;
    }

    @Override
    public JTGraphics getGraphics() {
        return new FastJTImageGraphics(this);
    }

    @Override
    public JTImage copy() {
        return new FastJTImage(width, height, copy(r), copy(g), copy(b));
    }

    private static double[] copy(double[] array) {
        return Arrays.copyOf(array, array.length);
    }

    double getR(int index) {
        return r[index];
    }

    double getG(int index) {
        return g[index];
    }

    double getB(int index) {
        return b[index];
    }
}
