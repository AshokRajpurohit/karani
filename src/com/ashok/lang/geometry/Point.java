package com.ashok.lang.geometry;

public class Point {
    public static final Point ORIGIN = new Point(0, 0);
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns new point exactly with same co-ordinates.
     * @return new Point
     */
    public final Point clone() {
        return new Point(x, y);
    }

    /**
     * Compares this Point with the specified Object for equality.
     * returns true if point p is same as the point.
     * @param p
     * @return
     */
    public final boolean equals(Object x) {
        if (x == this)
            return true;

        if (!(x instanceof Point))
            return false;

        Point xPoint = (Point)x;
        return this.x == xPoint.x && this.y == xPoint.y;
    }

    public final double distance(Point p) {
        return Math.sqrt((p.y - y) * (p.y - y) + (p.x - x) * (p.x - x));
    }

    public final static double distance(Point a, Point b) {
        return Math.sqrt((a.y - b.y) * (a.y - b.y) +
                         (a.x - b.x) * (a.x - b.x));
    }

    /**
     * returns the orientation from point a to b to c.
     * if orientation is anticlockwise it returns 1.
     * if it is clockwise, returns -1.
     * if all three points are collinear, returns 0.
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

    public String toString() {
        return x + ", " + y;
    }
}
