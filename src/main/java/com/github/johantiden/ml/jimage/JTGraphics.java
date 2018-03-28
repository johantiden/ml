package com.github.johantiden.ml.jimage;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.shape.CircleWithColor;
import com.github.johantiden.ml.jimage.shape.EllipseWithColor;
import com.github.johantiden.ml.jimage.shape.LineWithColor;
import com.github.johantiden.ml.jimage.shape.Point;
import com.github.johantiden.ml.jimage.shape.PolygonWithColor;
import com.github.johantiden.ml.jimage.shape.PolygonWithColorImpl;
import com.github.johantiden.ml.jimage.shape.RectangleWithColor;
import com.github.johantiden.ml.jimage.shape.ShapeWithColor;
import com.github.johantiden.ml.util.Maths;
import com.google.common.collect.Lists;

public class JTGraphics {
    private final JTImage image;

    public JTGraphics(JTImage image) {
        this.image = image;
    }


    public void fillShape(ShapeWithColor shape) {
        fillShape(image, shape);
    }

    public static void fillShape(JTImage image, ShapeWithColor shape) {
        int left = Maths.maxFloor(shape.left(), 0);
        int top = Maths.maxFloor(shape.top(), 0);
        int right = Maths.minCeil(shape.right(), image.getWidth() - 1);
        int bottom = Maths.minCeil(shape.bottom(), image.getHeight() - 1);

        JTColor color = shape.getColor();

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = image.getIndex(x, y);

                if (shape.isInside(x, y)) {
                    paintPixel(image, index, color.getR(), color.getG(), color.getB(), color.getA());
                }
            }
        }
    }


    public void fillCircle(CircleWithColor circle) {
        fillShape(circle);
    }

    public void drawCircle(CircleWithColor circle, double penWidth) {
        int left = (int) Math.max(circle.left() - penWidth, 0);
        int top = (int) Math.max(circle.top() - penWidth, 0);
        int right = (int) Math.min(left + circle.getRadius()*2 + 1 + penWidth*2, image.getWidth() - 1);
        int bottom = (int) Math.min(top + circle.getRadius()*2 + 1 + penWidth*2, image.getHeight() - 1);

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = image.getIndex(x, y);

                double alphaPercentage = Math.min(1, penWidth - Math.abs(circle.getRadius() - circle.distanceFrom(x, y)));
                if (alphaPercentage > 0) {
                    paintPixel(index, circle.getColor().getR(), circle.getColor().getG(), circle.getColor().getB(), circle.getColor().getA() * alphaPercentage);
                }
            }
        }
    }

    public void fillCircleRadial(CircleWithColor circle) {
        int left = Maths.maxFloor(circle.left(), 0);
        int top = Maths.maxFloor(circle.top(), 0);
        int right = Maths.minCeil(circle.right(), image.getWidth() - 1);
        int bottom = Maths.minCeil(circle.bottom(), image.getHeight() - 1);

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {

                if (circle.isInside(x, y)) {
                    JTColor color = circle.getColor();
                    char alpha = (char) Math.max(color.getA() * Math.pow((circle.getRadius() - Math.sqrt((x - circle.getX()) * (x - circle.getX()) +
                            (y - circle.getY()) * (y - circle.getY()))) / circle.getRadius(), 2), 0);

                    int index = image.getIndex(x, y);
                    paintPixel(index, color.getR(), color.getG(), color.getB(), alpha);
                }
            }
        }
    }

    public void fillAll(JTColor color) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int index = this.image.getIndex(x, y);
                paintPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
            }
        }
    }

    public void drawLine(LineWithColor line, double strokeWidth) {

        // quick and dirty :)
        Point SW = line.getA().plus(-strokeWidth, 0);
        Point SE = line.getA().plus(strokeWidth, 0);
        Point NW = line.getB().plus(-strokeWidth, 0);
        Point NE = line.getB().plus(strokeWidth, 0);

        PolygonWithColorImpl polygon = new PolygonWithColorImpl(Lists.newArrayList(SW, SE, NE, NW, SW), line.getColor());
        fillPolygon(polygon);
    }

    public void paint(int baseX, int baseY, JTImage image) {

        for (int i = 0; i < image.getHeight(); i++) {
            int y = baseY + i;
            for (int j = 0; j < image.getWidth(); j++) {
                int x = baseX + j;

                int index = this.image.getIndex(x, y);
                JTColor color = image.getColorAt(j, i);
                paintPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
            }
        }

    }

    public void paint(int baseX, int baseY, JTImage image, double alpha) {

        for (int i = 0; i < image.getHeight(); i++) {
            int y = baseY + i;
            for (int j = 0; j < image.getWidth(); j++) {
                int x = baseX + j;

                int index = this.image.getIndex(x, y);
                JTColor color = image.getColorAt(j, i);
                paintPixel(index, color.getR(), color.getG(), color.getB(), alpha);
            }
        }
    }

    public void fillRectangle(RectangleWithColor rectangle) {
        fillShape(rectangle);
    }
    public void fillRectangle(double x, double y, double width, int height, JTColor color) {
        fillShape(new RectangleWithColor(x, y, width, height, color));
    }

    public void fillEllipse(EllipseWithColor ellipse) {
        fillShape(ellipse);
    }

    public void fillEllipseRadial(EllipseWithColor ellipse) {
        int left = Maths.maxFloor(ellipse.left(), 0);
        int top = Maths.maxFloor(ellipse.top(), 0);
        int right = Maths.minCeil(ellipse.right(), image.getWidth() - 1);
        int bottom = Maths.minCeil(ellipse.bottom(), image.getHeight() - 1);

        JTColor color = ellipse.getColor();

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = image.getIndex(x, y);
                double ellipseBoundRatio = ellipse.getCentreRatio(x, y);
                if (ellipseBoundRatio < 1) {
                    double alpha = color.getA() * (1 - ellipseBoundRatio);
                    paintPixel(index, color.getR(), color.getG(), color.getB(), alpha);
                }
            }
        }
    }


    public void fillPolygon(PolygonWithColor polygon) {
        fillShape(polygon);
    }


    public void paintPixel(int index, JTColor color) {
        paintPixel(image, index, color.getR(), color.getG(), color.getB(), color.getA());
    }

    public void paintPixel(int index, double r, double g, double b, double a) {
        paintPixel(image, index, r, g, b, a);
    }

    public static void paintPixel(JTImage image, int index, double r, double g, double b, double a) {
        JTColor.verify(r, g, b, a);
        if (a > 0.999999) {
            image.setPixel(index, r, g, b);
        } else {
            double rFromOld = (1 - a) * image.getR(index);
            double gFromOld = (1 - a) * image.getG(index);
            double bFromOld = (1 - a) * image.getB(index);

            double rFromNew = a * r;
            double gFromNew = a * g;
            double bFromNew = a * b;

            double mixedR = rFromOld + rFromNew;
            double mixedG = gFromOld + gFromNew;
            double mixedB = bFromOld + bFromNew;


            image.setPixel(index, mixedR, mixedG, mixedB);
        }
    }
}
