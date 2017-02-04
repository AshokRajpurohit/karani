package com.ashok.lang.geometry;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Rectangle {
    public static final Rectangle INVALID;

    private Point lowerLeft, upperRight;

    private double length, width;

    static {
        INVALID = new Rectangle();
        INVALID.length = -1;
        INVALID.width = -1;
        INVALID.lowerLeft = Point.ORIGIN;
        INVALID.upperRight = Point.ORIGIN;
    }

    private Rectangle() {
        // do nothing.
    }

    public double left() {
        return lowerLeft.x;
    }

    public double right() {
        return upperRight.x;
    }

    public double bottom() {
        return lowerLeft.y;
    }

    public double top() {
        return upperRight.y;
    }

    public Rectangle(Point a, Point b) {
        lowerLeft = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
        upperRight = new Point(Math.max(a.x, b.x), Math.max(a.y, b.y));

        length = upperRight.x - lowerLeft.x;
        width = upperRight.y - lowerLeft.y;
    }

    public Rectangle(Point lowerLeft, double length, double width) {
        this.lowerLeft = lowerLeft;
        upperRight = lowerLeft.shift(length, width);
        this.length = length;
        this.width = width;
    }

    public Rectangle(Point[] points) {
        Point first = points[0];
        double minx = first.x, maxx = first.x, miny = first.y, maxy = first.y;

        for (Point point : points) {
            minx = Math.min(minx, point.x);
            miny = Math.min(miny, point.y);

            maxx = Math.max(maxx, point.x);
            maxy = Math.max(maxy, point.y);
        }

        lowerLeft = new Point(minx, miny);
        upperRight = new Point(maxx, maxy);

        length = maxx - minx;
        width = maxy - miny;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public void shift(double dx, double dy) {
        lowerLeft = lowerLeft.shift(dx, dy);
        upperRight = upperRight.shift(dx, dy);
    }

    public void shiftVertically(double distance) {
        shift(0, distance);
    }

    public void shiftHorizontally(double distance) {
        shift(distance, 0);
    }

    public boolean isValid() {
        return length >= 0 && width >= 0;
    }

    public boolean contains(Point point) {
        return betweenHorizontalLines(point) && betweenVerticalLines(point);
    }

    public boolean contains(Rectangle rectangle) {
        return contains(rectangle.lowerLeft) && rectangle.contains(rectangle.upperRight);
    }

    public boolean intersects(Rectangle rectangle) {
        double left = Math.max(lowerLeft.x, rectangle.lowerLeft.x),
                right = Math.min(upperRight.x, rectangle.upperRight.x),
                upper = Math.min(upperRight.y, rectangle.upperRight.y),
                bottom = Math.max(lowerLeft.y, rectangle.lowerLeft.y);

        return left <= right && upper >= bottom;
    }

    public boolean betweenVerticalLines(Point point) {
        return point.x >= lowerLeft.x && point.x <= upperRight.x;
    }

    public boolean betweenHorizontalLines(Point point) {
        return point.y >= lowerLeft.y && point.y <= upperRight.y;
    }

    public double distance(Point point) {
        if (contains(point))
            return 0;

        double dx = Math.max(Math.abs(point.x - lowerLeft.x), Math.abs(point.x - upperRight.x)),
                dy = Math.max(Math.abs(point.y - lowerLeft.y), Math.abs(point.y - upperRight.y));

        if (dx <= length)
            dx = 0;
        else dx -= length;

        if (dy <= width)
            dy = 0;
        else dy -= width;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public Rectangle clone() {
        Rectangle rectangle = new Rectangle();
        rectangle.lowerLeft = lowerLeft;
        rectangle.upperRight = upperRight;
        rectangle.width = width;
        rectangle.length = length;

        return rectangle;
    }

    public String toString() {
        return lowerLeft + ", " + upperRight + " length: " + length + " width: " + width;
    }

    public static Rectangle intersection(Rectangle a, Rectangle b) {
        if (a.contains(b))
            return b.clone();

        if (b.contains(a))
            return a.clone();

        double left = Math.max(a.lowerLeft.x, b.lowerLeft.x),
                right = Math.min(a.upperRight.x, b.upperRight.x),
                upper = Math.min(a.upperRight.y, b.upperRight.y),
                bottom = Math.max(a.lowerLeft.y, b.lowerLeft.y);

        if (left > right || upper < bottom)
            return INVALID;

        Rectangle rectangle = new Rectangle();

        rectangle.lowerLeft = new Point(left, bottom);
        rectangle.upperRight = new Point(right, upper);

        rectangle.length = right - left;
        rectangle.width = upper - bottom;

        return rectangle;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (!(object instanceof Rectangle))
            return false;

        Rectangle rectangle = (Rectangle)object;
        return lowerLeft.equals(rectangle.lowerLeft) && upperRight.equals(rectangle.upperRight);
    }
}
