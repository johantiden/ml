package com.github.johantiden.ml.jimage;


public class PointImpl implements Point {
    public final double x;
    public final double y;

    public PointImpl(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Point plus(double x, double y) {
        return new PointImpl(this.x + x, this.y + y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }

        Point point = (Point) o;

        if (Double.compare(point.getX(), x) != 0) {
            return false;
        }
        if (Double.compare(point.getY(), y) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        long temp = x == 0.0d ? 0L : Double.doubleToLongBits(x);
        int result = (int) (temp ^ temp >>> 32);
        long l = y == 0.0d ? 0L : Double.doubleToLongBits(y);
        result = 31 * result + (int) (l ^ l >>> 32);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
