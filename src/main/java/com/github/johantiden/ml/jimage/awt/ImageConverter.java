package com.github.johantiden.ml.jimage.awt;

import com.github.johantiden.ml.jimage.JTImage;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class ImageConverter {

    private ImageConverter() {
    }

    public static JTImage loadImage(String pathAndFileName, double scaleDown) {
        return toJTImage(getRealImage(pathAndFileName), scaleDown);
    }

    private static BufferedImage getRealImage(String pathAndFileName) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage toAwtImage(JTImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.getGraphics();

        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                Color c = image.getColorAt(x, y).asAwtColor();
                g.setColor(c);
                g.fillRect(x, y, 1, 1);
            }
        }

        g.dispose();

        return bufferedImage;
    }

    public static JTImage toJTImage(BufferedImage image, double downscale) {
        JTImage jtImage = new JTImage(
                (int) (image.getWidth(null) / downscale),
                (int) (image.getHeight(null) / downscale));

        for (int y = 0; y < jtImage.getHeight(); ++y) {
            for (int x = 0; x < jtImage.getWidth(); ++x) {
                int argb = image.getRGB((int) (x * downscale), (int) (y * downscale));

                int r = argb >> 16 & 0xff;
                int g = argb >> 8 & 0xff;
                int b = argb & 0xff;

                jtImage.setPixel(x, y,
                        r / 255.0,
                        g / 255.0,
                        b / 255.0);
            }
        }
        return jtImage;
    }

}
