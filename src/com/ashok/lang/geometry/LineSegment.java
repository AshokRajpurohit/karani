package com.ashok.lang.geometry;

import java.util.Comparator;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */
public class LineSegment implements Comparable<LineSegment> {
    public final Point a, b;
    public final double length, angle;
    private static final double RadianToDegree = 180 / Math.PI;

    public LineSegment(Point x, Point y) {
        a = x;
        b = y;
        length =
                Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
        angle = RadianToDegree * getAngle(x, y);
    }

    public final double length() {
        return length;
    }

    public static final double getAngle(Point p, Point q) {
        if (p.x == q.x) {
            if (p.y < q.y)
                return Math.PI / 2;
            else if (p.y == q.y)
                return 0;
            else
                return 1.5 * Math.PI;
        }

        double theta = Math.atan(Line.getSlope(p, q));
        if (theta >= 0) {
            if (p.x > q.x)
                return Math.PI + theta;
            return theta;
        }

        if (p.x > q.x)
            return Math.PI + theta;
        return 2 * Math.PI + theta;
    }

    public final double slope() {
        return Line.getSlope(a, b);
    }

    /**
     * Returns true if the point p is on the segment (not to be confused with
     * straigh line).
     * @param p
     * @return
     * @see Line
     * @see Point
     */
    public final boolean pointOnSegment(Point p) {
        if (slope() != Line.getSlope(a, p))
            return false;

        if (p.x < Math.min(a.x, b.x) || p.x > Math.max(a.x, b.x))
            return false;

        return true;
    }

    /**
     * reurns true if a horizontal line extending to +ive infinite is drawn
     * from point p cuts the segment.
     * @param p point from where a horizontal line is drwan.
     * @return
     */
    public final boolean isHorizonLineIntersect(Point p) {
        if ((b.y - p.y > 0) == (a.y - p.y > 0))
            return false;
        return p.x < a.x + (p.y - a.y) * (b.x - a.x) / (b.y - a.y);
    }

    /**
     * reurns true if a vertical line extending to +ive infinite is drawn
     * from point p cuts the segment.
     * @param p point from where a vertical line is drwan.
     * @return
     */
    public final boolean isVerticalLineIntersect(Point p) {
        if ((b.x - p.x > 0) == (a.x - p.x > 0))
            return false;
        return p.y < a.y + (p.x - a.x) * (b.y - a.y) / (b.x - a.x);
    }

    public final boolean isIntersect(LineSegment ls) {
        if (slope() == ls.slope()) {
            return pointOnSegment(ls.a) || pointOnSegment(ls.b) ||
                ls.pointOnSegment(a) || ls.pointOnSegment(b);
        }

        if (Math.max(a.y, b.y) < Math.min(ls.a.y, ls.b.y) ||
            Math.min(a.y, b.y) > Math.max(ls.a.y, ls.b.y) ||
            Math.max(a.x, b.x) < Math.min(ls.a.x, ls.b.x) ||
            Math.min(a.x, b.x) > Math.max(ls.a.x, ls.b.x))
            return false;
        // work in progress
        return true;
    }

    public LineSegment clone() {
        return new LineSegment(this.a, this.b);
    }

    public String toString() {
        return (long)this.length() + "";
    }

    public final static LENGTH_ORDER LengthComparator = new LENGTH_ORDER();

    public int compareTo(LineSegment ls) {
        return LengthComparator.compare(this, ls);
    }

    private static final class LENGTH_ORDER implements Comparator<LineSegment> {

        public int compare(LineSegment o1, LineSegment o2) {
            if (o1.length < o2.length)
                return -1;

            if (o1.length == o2.length)
                return 0;

            return 1;
        }
    }

    public final static ANGLE_ORDER ANGLE_COMPARATOR = new ANGLE_ORDER();

    private static final class ANGLE_ORDER implements Comparator<LineSegment> {

        public int compare(LineSegment a, LineSegment b) {
            if (a.angle < b.angle)
                return -1;

            if (a.angle == b.angle)
                return 0;

            return 1;
        }
    }
}
