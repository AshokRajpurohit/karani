/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Problem Name: Points Inside A Polygon
 * Link: https://www.codechef.com/FEB18/problems/POINPOLY
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PointsInsidePolygon {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String NO_POINTS = "-1";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++)
                points[i] = new Point(in.readInt(), in.readInt());

//            PointSet pointSet = process(points);
            PointSet pointSet = play(points);
            out.print(pointSet);
        }
    }

    private static PointSet process(Point[] points) {
        Polygon polygon = new Polygon(points);
        int n = points.length;
        PointSet pointSet = new PointSet(n / 10);
        Vertex downLeft = Collections.min(polygon, POINT_Y_COMPARATOR);
        Vertex downRight = findAnother(polygon, downLeft, POINT_Y_COMPARATOR);
        int y = downLeft.y + 1, yMax = Collections.max(polygon, POINT_Y_COMPARATOR).y;

        while (!pointSet.complete() && y < yMax) {
            LineSegment left = new LineSegment(downLeft, downLeft.prev);
            LineSegment right = new LineSegment(downRight, downRight.next);

            double xL = left.getXCoordinates(y), xR = right.getXCoordinates(y);
            int minX = (int) xL, maxX = (int) xR;
            if (xL == minX) minX++;
            if (xR == maxX) maxX--;

            for (int x = minX; x <= maxX && !pointSet.complete(); x++)
                pointSet.add(new Point(x, y));

            y++;
            if (y > downLeft.prev.y)
                downLeft = downLeft.prev;

            if (y > downRight.next.y)
                downRight = downRight.next;
        }

        return pointSet;
    }

    private static PointSet play(Point[] points) {
        int n = points.length;
        PointSet pointSet = new PointSet(n / 10);

        for (int j = n / 2; j > 1 && !pointSet.complete(); j--) {
            for (int i = 0, k = i + j; i < n && !pointSet.complete(); i++, k++) {
                if (k == n) k = 0;

                int dx = points[k].x - points[i].x, dy = points[k].y - points[i].y;
                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;

                for (int x = points[i].x + dx, y = points[i].y + dy; g > 1 && !pointSet.complete(); g--, x += dx, y += dy)
                    pointSet.add(new Point(x, y));
            }
        }

        return pointSet;
    }

    private static Vertex findAnother(Polygon polygon, Vertex vertex, Comparator<Point> comparator) {
        for (Vertex v : polygon) {
            if (v != vertex && comparator.compare(vertex, v) == 0)
                return v;
        }

        return vertex;
    }

    /**
     * Euclid's Greatest Common Divisor algorithm implementation.
     * For more information refer Wikipedia and Alan Baker's Number Theory.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    private static final Comparator<Point> POINT_Y_COMPARATOR = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.y - o2.y;
        }
    };

    private static final Comparator<Point> POINT_X_COMPARATOR = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.x - o2.x;
        }
    };

    private static class Point {
        final int x, y, hashCode;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            hashCode = Long.hashCode(1L * x * 1000000007 + y);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return x + " " + y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point))
                return false;

            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    final static class Vertex extends Point implements Comparable<Vertex> {
        Vertex next, prev;

        Vertex(Point point) {
            super(point.x, point.y);
        }

        @Override
        public int compareTo(Vertex vertex) {
            if (POINT_Y_COMPARATOR.compare(this, vertex) == 0)
                return POINT_X_COMPARATOR.compare(this, vertex);

            return POINT_Y_COMPARATOR.compare(this, vertex);
        }
    }

    final static class Polygon extends HashSet<Vertex> {
        Polygon(Point[] points) {
            int n = points.length;
            Vertex[] vertices = new Vertex[n];
            for (int i = 0; i < n; i++)
                vertices[i] = new Vertex(points[i]);

            for (int i = 0, j = n - 1, k = 1; i < n; i++, j++, k++) {
                if (j == n) j = 0;
                if (k == n) k = 0;
                vertices[i].prev = vertices[j];
                vertices[i].next = vertices[k];
            }

            for (Vertex vertex : vertices)
                add(vertex);
        }
    }

    private final static class PointSet extends HashSet<Point> {
        final int capacity;

        PointSet(int capacity) {
            this.capacity = capacity;
        }

        boolean complete() {
            return size() >= capacity;
        }

        public String toString() {
            if (!complete()) return NO_POINTS;

            StringBuilder sb = new StringBuilder(size() << 2);
            for (Point point : this)
                sb.append(point.x).append(' ').append(point.y).append('\n');

            return sb.toString();
        }
    }

    final static class LineSegment {
        final Point a, b;

        LineSegment(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        double getYCoordinates(int x) {
            return a.y + 1.0 * (b.y - a.y) * (x - a.x) / (b.x - a.x);
        }

        double getXCoordinates(int y) {
            return a.x + 1.0 * (b.x - a.x) * (y - a.y) / (b.y - a.y);
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
    }
}