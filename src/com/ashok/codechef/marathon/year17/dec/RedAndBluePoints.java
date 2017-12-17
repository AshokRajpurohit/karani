/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Red and blue points
 * Link: https://www.codechef.com/DEC17/problems/REDBLUE
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class RedAndBluePoints {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int RED = 0, BLUE = 1;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int redPoints = in.readInt(), bluePoints = in.readInt();
            Point[] red = readPoints(redPoints, RED), blue = readPoints(bluePoints, BLUE);
            out.println(bruteForce(red, blue));
        }
    }

    private static int bruteForce(Point[] redPoints, Point[] bluePoints) {
        if (bluePoints.length < redPoints.length)
            return bruteForce(bluePoints, redPoints); // doesn't matter if we switch color.

        if (redPoints.length == 1) {
            if (bluePoints.length <= 2)
                return 0;

            return processBruteForce(bluePoints, redPoints);
        }

        return processBruteForce(redPoints, bluePoints);
    }

    private static int processBruteForce(Point point, Point[] points) {
        int[] ref = new int[2];
        for (Point p : points) {
            LineSegment segment = new LineSegment(point, p);
            countPointsOnSides(segment, points, ref);
            if (ref[0] == 1 || ref[1] == 1) return 0;
        }

        return 1;
    }

    private static int processBruteForce(Point[] redPoints, Point[] bluePoints) {
        int rl = redPoints.length, bl = bluePoints.length;
        int min = rl;
        for (int i = 0; i < rl; i++)
            for (int j = i + 1; j < rl; j++) {
                LineSegment segment = new LineSegment(redPoints[i], redPoints[j]);
                min = Math.min(min, getMin(redPoints, bluePoints, segment));
                if (min == 0) return min;
            }

        return Math.min(min, processCrossBruteForce(redPoints, bluePoints));
    }

    private static int processCrossBruteForce(Point[] redPoints, Point[] bluePoints) {
        int rl = redPoints.length, bl = bluePoints.length;
        int min = rl;
        for (Point a : redPoints)
            for (Point b : bluePoints) {
                LineSegment segment = new LineSegment(a, b);
                min = Math.min(min, getMin(redPoints, bluePoints, segment));
                if (min == 0) return min;
            }

        return min;
    }

    private static int getMin(Point[] redPoints, Point[] bluePoints, LineSegment segment) {
        int[] redRef = new int[2], blueRef = new int[2];
        countPointsOnSides(segment, redPoints, redRef);
        countPointsOnSides(segment, bluePoints, blueRef);
        return getShiftPointCount(redRef, blueRef);
    }

    private static void countPointsOnSides(LineSegment segment, Point[] points, int[] ref) {
        int leftCount = 0, rightCount = 0;
        for (Point point : points) {
            int orientation = segment.getOrientation(point);
            if (orientation < 0)
                leftCount++;
            else if (orientation > 0)
                rightCount++;
        }

        ref[0] = leftCount;
        ref[1] = rightCount;
    }

    private static int getShiftPointCount(int[] a, int[] b) {
        return Math.min(a[0] + b[1], a[1] + b[0]);
    }

    private static int process(Point[] redPoints, Point[] bluePoints) {
        if (bluePoints.length < redPoints.length)
            return process(bluePoints, redPoints); // doesn't matter if we switch color.

        LineSegment segment = getLeftMostLine(redPoints);
        return redPoints.length;
    }

    private static LineSegment getLeftMostLine(Point[] points) {
        Point a = points[0], b = points[1];
        return new LineSegment(a, b);
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
    private static int orientation(Point a, Point b, Point c) {
        long v = (c.y - a.y) * (b.x - a.x) - (c.x - a.x) * (b.y - a.y);
        return v > 0 ? 1 : -1;
    }

    private static double getSlopeAngle(Point a, Point b) {
        double angle = Math.atan2(b.y - a.y, b.x - a.x);
        return angle < 0 ? Math.PI - angle : angle;
    }

    private static double getShiftAngle(LineSegment segment, Point point) {
        double angle = getSlopeAngle(segment.from, segment.to), angle2 = getSlopeAngle(segment.to, point);
        double diff = angle2 - angle;
        return diff >= 0 ? diff : diff + 2 * Math.PI;
    }

    private static Point[] readPoints(int count, int color) throws IOException {
        Point[] points = new Point[count];
        for (int i = 0; i < count; i++)
            points[i] = new Point(in.readInt(), in.readInt(), color);

        return points;
    }

    final static class Point {
        final long x, y;
        final int color;

        Point(int x, int y, int c) {
            this.x = x;
            this.y = y;
            color = c;
        }

        public String toString() {
            return x + ", " + y;
        }
    }

    final static class LineSegment {
        final Point from, to;

        LineSegment(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        int getOrientation(Point point) {
            return contains(point) ? 0 : orientation(from, to, point);
        }

        boolean contains(Point point) {
            return from == point || to == point;
        }

        public String toString() {
            return from + " -> " + to;
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