package com.github.johantiden.ml.awt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.johantiden.ml.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Colors {

    private static final Logger log = LoggerFactory.getLogger(Colors.class);

    public static Color hsvShift(Color color, double hueShift, double saturationShift, double brightnessShift) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        hsb[0] = clamp((float) (hsb[0]*hueShift));
        hsb[1] = clamp((float) (hsb[1]*saturationShift));
        hsb[2] = clamp((float) (hsb[2]*brightnessShift));

        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        if (rgb == -1) {
            throw new IllegalArgumentException("Could not hsvShift");
        }
        Color colorFromRGB = getColorFromRGB(rgb);
        return colorFromRGB;
    }

    private static float clamp(float v) {
        return Maths.minmaxF(v, 0, 1);
    }

    public static Color getColor(BufferedImage image, int x, int y) {
        int clr=  image.getRGB(x, y);
        return getColorFromRGB(clr);
    }

    private static Color getColorFromRGB(int clr) {
        int  red   = (clr & 0x00ff0000) >> 16;
        int  green = (clr & 0x0000ff00) >> 8;
        int  blue  =  clr & 0x000000ff;

        return new Color(red, green, blue);
    }

    public static Color avg(Color a, Color b) {
        return new Color(
                Maths.avgI(a.getRed(), b.getRed()),
                Maths.avgI(a.getGreen(), b.getGreen()),
                Maths.avgI(a.getBlue(), b.getBlue())
        );
    }
}
