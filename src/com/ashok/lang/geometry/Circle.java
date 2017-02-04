package com.ashok.lang.geometry;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Circle {
    private Point centre = Point.ORIGIN;
    private double radius = 0;

    Circle(Point c, double r) {
        update(c, r);
    }

    void update(Point c, double r) {
        centre = c;
        radius = r;
    }

    boolean pointInCircle(Point point) {
        return centre.distance(point) <= radius;
    }

    boolean pointOnCircle(Point point) {
        return centre.distance(point) == radius;
    }

    int countPointsInCircle(Point[] points) {
        int count = 0;

        for (Point point : points)
            if (pointInCircle(point))
                count++;

        return count;
    }

    int[] pointsOnCircle(Point[] points) {
        List<Integer> list = new LinkedList<>();

        for (int i = 0; i < points.length; i++)
            if (pointOnCircle(points[i]))
                list.add(i);

        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    public String toString() {
        return "[" + centre + ", " + radius + "]";
    }
}
