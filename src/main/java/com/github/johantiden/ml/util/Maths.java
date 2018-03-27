package com.github.johantiden.ml.util;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.color.JTColor;

import java.util.Random;

public class Maths {

    public static final Random RANDOM = new Random();

    public static int roundI(double d) {
        return (int)Math.round(d);
    }

    public static float roundF(double d) {
        return (float)Math.round(d);
    }

    public static double minmax(double v, double min, double max) {
        if (v > max) {
            return max;
        }
        if (v < min) {
            return min;
        }

        return v;
    }

    public static long minmax(long v, long min, long max) {
        if (v > max) {
            return max;
        }
        if (v < min) {
            return min;
        }

        return v;
    }

    public static float minmaxF(float v, float min, float max) {
        if (v > max) {
            return max;
        }
        if (v < min) {
            return min;
        }

        return v;
    }

    public static double avg(double a, double b) {
        return (a+b)/2;
    }

    public static float avgF(double a, double b) {
        return (float) avg(a,b);
    }

    public static int avgI(int a, int b) {
        return roundI((a + b) / 2.0);
    }

    public static int randomInt(int bound) {
        int i = RANDOM.nextInt(bound);
        return i;
    }

    public static int maxI(double a, double b) {
        return roundI(Math.max(a, b));
    }

    public static double randomize(double maxJump, double d, double min, double max) {

        double change = Math.random()*2*maxJump - maxJump;
        double v = d + change;
        return minmax(v, min, max);
    }

    public static double randomize(double maxJump, double d) {
        return randomize(maxJump, d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public static JTColor randomizeColor(int maxJump, int maxJumpAlpha, JTColor color) {
        return new JTColor(
                roundI(randomize(maxJump, color.getR(), 0, 1)),
                roundI(randomize(maxJump, color.getG(), 0, 1)),
                roundI(randomize(maxJump, color.getB(), 0, 1)),
                roundI(randomize(maxJumpAlpha, color.getA(), 0, 1))
                        );
    }
}
