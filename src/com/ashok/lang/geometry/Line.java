package com.ashok.lang.geometry;

/**
 * there are two parameters used for straight line.
 * one is slope and the other one is constant parameter.
 * This is from basic straight line equation y = mx + c
 * where m is slope, c is constant.
 * for vertical lines the equation is x = c and slope is positive infinite.
 *
 * @author Ashok Rajpurohit
 */

public class Line {
    public final double c;
    public final double slope;

    /**
     * creates a line connecting two points a and b.
     * if both the points are same, it will instantiate a new horizontal line
     * passing through these points.
     *
     * @param a
     * @param b
     */
    public Line(Point a, Point b) {
        if (a.equals(b)) {
            slope = 0;
            c = b.y;
            return;
        }

        if (a.x == b.x) {
            slope = Double.POSITIVE_INFINITY;
            c = a.x;
            return;
        }

        slope = (b.y - a.y) / (b.x - a.x);
        c = b.y - slope * b.x;
    }

    /**
     * Compares this line with the specified Object for equality.
     * returns true if both the straight lines are equivalent.
     * @param line
     * @return
     */
    public final boolean equals(Object line) {
        if (this == line)
            return true;

        if (!(line instanceof Line))
            return false;

        Line L = (Line)line;
        return c == L.c && slope == L.slope;
    }

    public final Line clone() {
        if (this.slope == Double.POSITIVE_INFINITY)
            return new Line(new Point(c, 0), new Point(c, 1));
        return new Line(new Point(0, c), new Point(1, c + slope));
    }

    public final boolean pointOnLine(Point point) {
        if (slope == Double.POSITIVE_INFINITY)
            return c == point.x;

        return point.y == slope * point.x + c;
    }

    public final static Line horizontalLine(Point p) {
        return new Line(p, p);
    }

    public final static Line verticalLine(Point p) {
        return new Line(p, new Point(p.x, p.y + 1));
    }

    /**
     * returns true if the parameter line is parallel to this line.
     * @param line
     * @return
     */
    public final boolean isParallel(Line line) {
        return this.slope == line.slope;
    }

    public final boolean isPerpendicular(Line line) {
        if (this.slope == 0 && line.slope == Double.POSITIVE_INFINITY)
            return true;

        if (this.slope == Double.POSITIVE_INFINITY && line.slope == 0)
            return true;

        return this.slope * line.slope == -1;
    }

    /**
     * returns true if this line intersects the parameter line.
     * @param line
     * @return
     */
    public final boolean isIntersect(Line line) {
        return !((this.slope == line.slope) && (this.c != line.c));
    }

    /**
     * returns slope of a line connecting two points a and b
     * @param a
     * @param b
     * @return
     */
    public final static double getSlope(Point a, Point b) {
        if (a.x == b.x) {
            if (b.y > a.y)
                return Double.POSITIVE_INFINITY;
            
            return Double.NEGATIVE_INFINITY;
        }
        return (b.y - a.y) / (b.x - a.x);
    }
}
