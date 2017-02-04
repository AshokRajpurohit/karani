/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Problem Name: Chef and Circle
 * Link: https://www.codechef.com/JAN17/problems/CHEFCIRC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndCircle {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final Point ORIGIN = new Point(0, 0);
    private static final double EPSILON = 0.009;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++)
            points[i] = new Point(in.readDouble(), in.readDouble());

        out.println(process(points, m));
    }

    private static double process(Point[] points, int minPoints) {
        if (minPoints == 1)
            return 0;

        if (points.length == 2)
            return getCircle(points[0], points[1]).radius;

//        randomizeArray(points);
        Circle centre = getCircleWithDiameterPoints(points, minPoints);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].distance(points[j]) >= centre.radius)
                    continue;

                double[] orientationRadiusMap = new double[3]; // -1, 0, 1 or 0, 1, 2
                for (int k = j + 1; k < points.length; k++) {
                    if (pointsIntStraightLine(points[i], points[j], points[k]))
                        continue;

                    Circle circle = getCircle(points[i], points[j], points[k]);
                    if (circle.radius >= centre.radius)
                        continue;

                    int orientation = 1 + orientation(points[i], points[j], circle.centre);

                    if (orientationRadiusMap[orientation] >= circle.radius || centre.radius + EPSILON <= circle.radius)
                        continue;

                    int pointsInCircle = circle.countPointsInCircle(points, minPoints);
                    if (pointsInCircle >= minPoints) {
                        centre = circle;

                        if (minPoints == points.length)
                            return centre.radius;
                    }
                }
            }
        }

        return centre.radius;
    }

    private static Circle getCircleWithDiameterPoints(Point[] points, int minPoints) {
        Circle centre = new Circle(ORIGIN, Math.sqrt(2) * 1000); // radius of circle enclosing a square of side 2000 unit length.

        for (int i = 0; i < points.length; i++)
            for (int j = 0; j < points.length; j++) {
                Circle circle = getCircle(points[i], points[j]);

                if (circle.radius >= centre.radius)
                    continue;

                int pointsInCircle = circle.countPointsInCircle(points, minPoints);
                if (pointsInCircle == minPoints) {
                    centre = circle;

                    if (minPoints == points.length)
                        return centre;
                }
            }

        return centre;
    }

    private static void randomizeArray(Object[] objects) {
        Random random = new Random();

        for (int i = 0; i < objects.length; i++) {
            int j = random.nextInt(objects.length);
            swap(objects, i, j);
        }
    }

    private static void swap(Object[] objects, int first, int second) {
        Object t = objects[first];
        objects[first] = objects[second];
        objects[second] = t;
    }

    private static Circle getCircle(Point a, Point b) {
        Point centre = getMidPoint(a, b);
        double radius = centre.distance(a);

        return new Circle(centre, radius);
    }

    private static Circle getCircle(Point a, Point b, Point c) {
        Line ab = new Line(a, b), bc = new Line(b, c);
        Point abMid = getMidPoint(a, b), bcMid = getMidPoint(b, c);
        Line abPerpendicular = ab.getPerpendicularLine(abMid), bcPerpendicular = bc.getPerpendicularLine(bcMid);

        Point centre = getIntersectionPoint(abPerpendicular, bcPerpendicular);
        double radius = centre.distance(a);

        return new Circle(centre, radius);
    }

    private static Point getIntersectionPoint(Line a, Line b) {
        if (a.slope == b.slope)
            throw new RuntimeException("Parallel lines can not intersect");

        if (a.isVertial()) {
            double x = a.c;
            double y = b.getY(x);
            return new Point(x, y);
        }

        if (b.isVertial()) {
            double x = b.c;
            double y = a.getY(x);
            return new Point(x, y);
        }

        double dm = a.slope - b.slope, dc = a.c - b.c;
        double x = -dc / dm;
        double y = a.getY(x);

        return new Point(x, y);
    }

    private static boolean pointsIntStraightLine(Point a, Point b, Point c) {
        if (a.equals(b) || b.equals(c) || c.equals(a))
            return true;

        double bax = b.x - a.x, bay = b.y - a.y, cax = c.x - a.x, cay = c.y - a.y;

        return bay * cax == cay * bax;
    }

    private static Point getMidPoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /**
     * returns the orientation from point a to b to c.
     * if orientation is anticlockwise it returns 1.
     * if it is clockwise, returns -1.
     * if all three points are collinear, returns 0.
     *
     * @param p
     * @param q
     * @param r
     * @return
     */
    private static int orientation(Point p, Point q, Point r) {
        double value = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return Double.compare(0, value);
    }

    final static class Circle {
        Point centre = ORIGIN;
        double radius = 0;

        Circle(Point c, double r) {
            update(c, r);
        }

        void update(Point c, double r) {
            centre = c;
            radius = r;
        }

        boolean pointInCircle(Point point) {
            return centre.distance(point) <= radius + EPSILON;
        }

        boolean pointInCircle(double x, double y) {
            return centre.distance(x, y) <= radius + EPSILON;
        }

        boolean pointOnCircle(Point point) {
            return Math.abs(centre.distance(point) - radius) <= EPSILON;
        }

        int countPointsInCircle(Point[] points, int target) {
            int count = 0;

            for (Point point : points) {
                if (pointInCircle(point))
                    count++;

                if (target == count)
                    return target;
            }

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

    /**
     * Line equation is of the form: y = mx + c.
     * For verticle lines, slope is m is {@link Double#POSITIVE_INFINITY} and equation is x = c.
     */
    final static class Line {
        public final double c;
        public final double slope;

        Line(double m, double c) {
            slope = m;
            this.c = c;
        }

        Line(Point a, Point b) {
            if (a.x == b.x) {
                slope = Double.POSITIVE_INFINITY;
                c = a.x;
                return;
            }

            slope = (b.y - a.y) / (b.x - a.x);
            c = b.y - slope * b.x;
        }

        Line getPerpendicularLine(Point point) {
            double m, c1;

            if (slope != 0)
                m = -1 / slope;
            else
                m = Double.POSITIVE_INFINITY;

            if (m == Double.POSITIVE_INFINITY)
                c1 = point.x;
            else
                c1 = point.y - m * point.x;

            return new Line(m, c1);
        }

        boolean isVertial() {
            return slope == Double.POSITIVE_INFINITY;
        }

        boolean isHorizontal() {
            return slope == 0;
        }

        double getY(double x) {
            if (isVertial())
                throw new RuntimeException("For vertical line, there is no y value.");

            return slope * x + c;
        }

        double getX(double y) {
            if (isHorizontal())
                throw new RuntimeException("For horizontal line, there is no x value.");

            return (y - c) / slope;
        }

        public String toString() {
            return slope + ", " + c;
        }
    }

    final static class Point {
        final double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double distance(Point point) {
            double dx = x - point.x, dy = y - point.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        double distance(double x, double y) {
            double dx = x - this.x, dy = y - this.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        static double distance(Point a, Point b) {
            return a.distance(b);
        }

        boolean equals(Point point) {
            return x == point.x && y == point.y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public double readDouble() throws IOException {
            return Double.parseDouble(read());
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
