package com.ashok.lang.geometry;

public class Point implements Comparable<Point> {
    public static final Point ORIGIN = new Point(0, 0);
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        int x_value = (int) (x % 10000) + (int) (10000 * (x - (int) x));
        int y_value = (int) (y % 10000) + (int) (10000 * (y - (int) y));

        return x_value ^ y_value;
    }

    /**
     * Returns new point exactly with same co-ordinates.
     *
     * @return new Point
     */
    public final Point clone() {
        return new Point(x, y);
    }

    /**
     * Compares this Point with the specified Object for equality.
     * returns true if point p is same as the point.
     *
     * @param object
     * @return
     */
    public final boolean equals(Object object) {
        if (object == this)
            return true;

        if (!(object instanceof Point))
            return false;

        Point point = (Point) object;
        return x == point.x && y == point.y;
    }

    public final double distance(Point point) {
        double dx = x - point.x, dy = y - point.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public final static double distance(Point a, Point b) {
        return a.distance(b);
    }

    /**
     * returns the orientation from point a to b to c.
     * if orientation is anticlockwise it returns 1.
     * if it is clockwise, returns -1.
     * if all three points are collinear, returns 0.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public final static int orientation(Point a, Point b, Point c) {
        double m1 = (b.y - a.y) / (b.x - a.x);
        double m2 = (c.y - b.y) / (c.x - b.x);

        if (m1 < m2)
            return 1;

        if (m1 > m2)
            return -1;

        return 0;
    }

    /**
     * returns true if three points a, b and c are collinear.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public final static boolean collinear(Point a, Point b, Point c) {
        return (b.y - a.y) / (b.x - a.x) == (c.y - b.y) / (c.x - b.x);
    }

    public final static boolean collinear(Point[] points) {
        if (points.length <= 2)
            return true;

        double m = Line.getSlope(points[0], points[1]);
        for (int i = 2; i < points.length; i++)
            if (m != Line.getSlope(points[0], points[i]))
                return false;

        return true;
    }

    public Point shift(double dx, double dy) {
        return new Point(x + dx, y + dy);
    }

    public String toString() {
        return x + ", " + y;
    }

    @Override
    public int compareTo(Point point) {
        if (y == point.y)
            return Double.compare(x, point.x);

        return Double.compare(y, point.y);
    }
}
