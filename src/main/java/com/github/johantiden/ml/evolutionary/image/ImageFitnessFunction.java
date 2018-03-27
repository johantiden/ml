package com.github.johantiden.ml.evolutionary.image;

import com.github.johantiden.ml.awt.Painter;
import com.github.johantiden.ml.jimage.FastJTImage;
import com.github.johantiden.ml.jimage.JTColor;
import com.github.johantiden.ml.jimage.JTImage;

import java.util.function.Function;

public abstract class ImageFitnessFunction<T> implements Function<T, Double> {
    private final JTImage targetImage;
    private final Painter<T> painter;
    private final int windowSize;

    protected ImageFitnessFunction(JTImage targetImage, Painter<T> painter, int windowSize) {
        this.targetImage = targetImage;
        this.painter = painter;
        this.windowSize = windowSize;
    }

    protected abstract double getTopologyLoss(T t);

    @Override
    public Double apply(T t) {

        JTImage buffer = new FastJTImage(getWidth(), getHeight());
        buffer.getGraphics().fillAll(JTColor.WHITE);
        painter.paint(buffer.getGraphics(), t);

        double loss = getRGBDiff(buffer, targetImage);

        double fitnessFromImage = -loss;
        double topologyLoss =  getTopologyLoss(t);
        double fitness = fitnessFromImage - topologyLoss;
        return fitness;
    }

    private int getHeight() {
        return targetImage.getHeight();
    }

    private int getWidth() {
        return targetImage.getWidth();
    }

    private double getRGBDiff(JTImage actual, JTImage target) {
        double sum = 0;
        for (int y = 0; y < getHeight(); y+= windowSize) {
            for (int x = 0; x < getWidth(); x+= windowSize) {

                JTColor targetColor = target.getColorAt(x, y);
                JTColor actualColor = actual.getColorAt(x, y);

                double loss = getLoss(actualColor, targetColor);

                sum += loss;

            }
        }

        return sum;
    }

    private static double getLoss(JTColor actualColor, JTColor targetColor) {
        double r1 = targetColor.getR();
        double g1 = targetColor.getG();
        double b1 = targetColor.getB();

        double r2 = actualColor.getR();
        double g2 = actualColor.getG();
        double b2 = actualColor.getB();

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}
