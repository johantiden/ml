package com.github.johantiden.ml.jimage;

import com.github.johantiden.ml.jimage.color.JTColor;
import com.github.johantiden.ml.jimage.color.JTColorImpl;
import com.github.johantiden.ml.jimage.shape.CircleWithColor;
import com.github.johantiden.ml.jimage.shape.Ellipse;
import com.github.johantiden.ml.jimage.shape.EllipseWithColor;
import com.github.johantiden.ml.jimage.shape.LineWithColor;
import com.github.johantiden.ml.jimage.shape.Point;
import com.github.johantiden.ml.jimage.shape.PointImpl;
import com.github.johantiden.ml.jimage.shape.PolygonWithColor;
import com.github.johantiden.ml.jimage.shape.PolygonWithColorImpl;
import com.google.common.collect.Lists;
import com.github.johantiden.ml.util.Maths;

public class FastJTImageGraphics implements JTGraphics {
    private final FastJTImage fastJTImage;

    public FastJTImageGraphics(FastJTImage fastJTImage) {
        this.fastJTImage = fastJTImage;
    }

    @Override
    public void fillCircle(CircleWithColor circle) {
        int left = Math.max(circle.leftInt(), 0);
        int top = Math.max(circle.topInt(), 0);
        int right = Math.min(circle.rightInt(), fastJTImage.getWidth() - 1);
        int bottom = Math.min(circle.bottomInt(), fastJTImage.getHeight() - 1);

        JTColor color = circle.getColor();

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = fastJTImage.getIndex(x, y);

                if (isInsideCircle(circle, x, y)) {
                    //char alpha = (char) ((Math.max(circle.color.a - Math.sqrt((x - circle.x) * (x - circle.x) +
                    //                                                                (y - circle.y) * (y - circle.y)) / circle.radius*200, 0)));

                    mixPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
                }
            }
        }
    }

    @Override
    public void drawCircle(CircleWithColor circle, double penWidth) {
        int left = (int) Math.max(circle.left() - penWidth, 0);
        int top = (int) Math.max(circle.top() - penWidth, 0);
        int right = (int) Math.min(left + circle.getRadius()*2 + 1 + penWidth*2, fastJTImage.getWidth() - 1);
        int bottom = (int) Math.min(top + circle.getRadius()*2 + 1 + penWidth*2, fastJTImage.getHeight() - 1);

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = fastJTImage.getIndex(x, y);

                double alphaPercentage = Math.min(1, penWidth - Math.abs(circle.getRadius() - circle.distanceFrom(x, y)));
                if (alphaPercentage > 0) {
                    mixPixel(index, circle.getColor().getR(), circle.getColor().getG(), circle.getColor().getB(), (int) Math.round(circle.getColor().getA()*alphaPercentage));
                }
            }
        }
    }

    @Override
    public void fillCircleRadial(CircleWithColor circle) {
        int left = Math.max(circle.leftInt(), 0);
        int top = Math.max(circle.topInt(), 0);
        int right = (int) Math.min(left + circle.getRadius()*2 + 1, fastJTImage.getWidth() - 1);
        int bottom = (int) Math.min(top + circle.getRadius()*2 + 1, fastJTImage.getHeight() - 1);

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {

                if (isInsideCircle(circle, x, y)) {
                    JTColor color = circle.getColor();
                    char alpha = (char) Math.max(color.getA() * Math.pow((circle.getRadius() - Math.sqrt((x - circle.getX()) * (x - circle.getX()) +
                            (y - circle.getY()) * (y - circle.getY()))) / circle.getRadius(), 2), 0);

                    int index = fastJTImage.getIndex(x, y);
                    mixPixel(index, color.getR(), color.getG(), color.getB(), alpha);
                }
            }
        }
    }

    @Override
    public void fillAll(JTColor color) {
        final int arraySize = fastJTImage.getWidth() * fastJTImage.getHeight();
        for (int i = 0; i < arraySize; ++i) {
            fastJTImage.setPixel(i, color.getR(), color.getG(), color.getB());
        }
    }

    @Override
    public void drawLine(LineWithColor line, double strokeWidth) {

        // quick and dirty :)
        Point SW = line.getA().plus(-strokeWidth, 0);
        Point SE = line.getA().plus(strokeWidth, 0);
        Point NW = line.getB().plus(-strokeWidth, 0);
        Point NE = line.getB().plus(strokeWidth, 0);

        PolygonWithColorImpl polygon = new PolygonWithColorImpl(Lists.newArrayList(SW, SE, NE, NW, SW), line.getColor());
        fillPolygon(polygon);
    }

    @Override
    public void paint(int baseX, int baseY, JTImage image) {

        for (int i = 0; i < image.getHeight(); i++) {
            int y = baseY + i;
            for (int j = 0; j < image.getWidth(); j++) {
                int x = baseX + j;

                int index = fastJTImage.getIndex(x, y);
                JTColor color = image.getColorAt(j, i);
                mixPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
            }
        }

    }

    @Override
    public void paint(int baseX, int baseY, JTImage image, double alpha) {

        for (int i = 0; i < image.getHeight(); i++) {
            int y = baseY + i;
            for (int j = 0; j < image.getWidth(); j++) {
                int x = baseX + j;

                int index = fastJTImage.getIndex(x, y);
                JTColor color = image.getColorAt(j, i);
                mixPixel(index, color.getR(), color.getG(), color.getB(), alpha);
            }
        }
    }

    @Override
    public void fillRectangle(int baseX, int baseY, int width, int height, JTColor color) {
        int left = Math.max(baseX, 0);
        int top = Math.max(baseY, 0);
        int right = Math.min(baseX+width, fastJTImage.getWidth());
        int bottom = Math.min(baseY+height, fastJTImage.getHeight());

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                int index = fastJTImage.getIndex(x, y);
                mixPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
            }
        }
    }

    @Override
    public void fillEllipse(EllipseWithColor ellipseWithColor) {

        int left = Math.max(ellipseWithColor.leftInt(), 0);
        int top = Math.max(ellipseWithColor.topInt(), 0);
        int right = Math.min(ellipseWithColor.rightInt(), fastJTImage.getWidth() - 1);
        int bottom = Math.min(ellipseWithColor.bottomInt(), fastJTImage.getHeight() - 1);

        JTColor color = ellipseWithColor.getColor();

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = fastJTImage.getIndex(x, y);

                if (isInsideEllipse(ellipseWithColor, x, y)) {
                    mixPixel(index, color.getR(), color.getG(), color.getB(), color.getA());
                }
            }
        }


    }

    @Override
    public void fillEllipseRadial(EllipseWithColor ellipseWithColor) {
        int left = Math.max(ellipseWithColor.leftInt(), 0);
        int top = Math.max(ellipseWithColor.topInt(), 0);
        int right = Math.min(ellipseWithColor.rightInt(), fastJTImage.getWidth() - 1);
        int bottom = Math.min(ellipseWithColor.bottomInt(), fastJTImage.getHeight() - 1);

        JTColor color = ellipseWithColor.getColor();

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                final int index = fastJTImage.getIndex(x, y);
                double ellipseBoundRatio = getEllipseBoundRatio(ellipseWithColor, x, y);
                if (ellipseBoundRatio < 1) {
                    int alpha = Maths.roundI(color.getA() * (1-ellipseBoundRatio));
                    mixPixel(index, color.getR(), color.getG(), color.getB(), alpha);
                }
            }
        }
    }

    private boolean isInsideEllipse(Ellipse ellipse, int x, int y) {
        double rad_cc = getEllipseBoundRatio(ellipse, x, y);
        return rad_cc < 1;
    }

    private double getEllipseBoundRatio(Ellipse ellipse, int x, int y) {
        double cos = Math.cos(ellipse.getAngle());
        double sin = Math.sin(ellipse.getAngle());

        double xc = x - ellipse.getX();
        double yc = y - ellipse.getY();


        double xct = xc * cos - yc * sin;
        double yct = xc * sin + yc * cos;

        return xct*xct/Math.pow(ellipse.getWidth()/2, 2) + yct*yct/Math.pow(ellipse.getHeight()/2.,2);
    }

    public void fillPolygon(PolygonWithColor polygon) {
        JTColor color = polygon.getColor();

        int left = Math.max(polygon.leftInt(), 0);
        int top = Math.max(polygon.topInt(), 0);
        int right = Math.min(polygon.rightInt(), fastJTImage.getWidth());
        int bottom = Math.min(polygon.bottomInt(), fastJTImage.getHeight());

        for (int y = top; y < bottom; ++y) {
            for (int x = left; x < right; ++x) {
                if (polygon.isPointInside(new PointImpl(x, y))) {
                    int index = fastJTImage.getIndex(x, y);
                    double a = color.getA();
                    mixPixel(index, color.getR(), color.getG(), color.getB(), a);
                }
            }
        }
    }


    private void mixPixel(int index, double r, double g, double b, double a) {
        JTColorImpl.verify(r, g, b, a);
        if (a > 0.999999) {
            fastJTImage.setPixel(index, r, g, b);
        } else {
            double rFromOld = (1 - a) * fastJTImage.getR(index);
            double gFromOld = (1 - a) * fastJTImage.getG(index);
            double bFromOld = (1 - a) * fastJTImage.getB(index);

            double rFromNew = a * r;
            double gFromNew = a * g;
            double bFromNew = a * b;

            double mixedR = rFromOld + rFromNew;
            double mixedG = gFromOld + gFromNew;
            double mixedB = bFromOld + bFromNew;


            fastJTImage.setPixel(index, mixedR, mixedG, mixedB);
        }
    }


    private boolean isInsideCircle(CircleWithColor circle, int x, int y) {
        return ((x - circle.getX()) * (x - circle.getX()) +
                (y - circle.getY()) * (y - circle.getY()))
                < (circle.getRadius() * circle.getRadius());
    }
}
