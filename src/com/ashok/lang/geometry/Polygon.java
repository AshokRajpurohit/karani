package com.ashok.lang.geometry;

public class Polygon {
    private final Point[] points;

    public Polygon(Point[] points) {
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            this.points[i] = points[i].clone();
    }

    public boolean pointInPolygon(Point p) {
        boolean result = false;
        for (int i = 0, j = points.length - 1; i < points.length; j = i++) {
            if (((points[i].y > p.y) != (points[j].y > p.y)) &&
                p.x < points[i].x +
                (p.y - points[i].y) * (points[j].x - points[i].x) /
                (points[j].y - points[i].y))
                result = !result;
        }
        return result;
    }

    public Polygon clone() {
        return new Polygon(this.points);
    }

    public Point[] getPoints() {
        Point[] p = new Point[points.length];
        for (int i = 0; i < p.length; i++)
            p[i] = points[i].clone();
        return p;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(points.length << 2);
        for (Point e : points)
            sb.append(e).append('\n');
        return sb.toString();
    }
}
