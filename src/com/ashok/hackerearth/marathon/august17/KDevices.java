/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.august17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: K Devices
 * Link: https://www.hackerearth.com/challenge/competitive/august-circuits-17/algorithm/k-devices-96ab1c02/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class KDevices {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), k = in.readInt();
        int[] valueX = in.readIntArray(n), valueY = in.readIntArray(n);
        Point[] points = getPoints(valueX, valueY);
        Arrays.sort(points);
        out.println(points[k - 1].getIntegralRadius());
    }

    private static Point[] getPoints(int[] x, int[] y) {
        int n = x.length;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++)
            points[i] = new Point(x[i], y[i]);

        return points;
    }

    final static class Point implements Comparable<Point> {
        final int x, y;
        final long distance2;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            distance2 = 1L * x * x + 1L * y * y;
        }

        long getIntegralRadius() {
            long radius = (long) Math.sqrt(distance2);

            return radius * radius == distance2 ? radius : radius + 1;
        }

        @Override
        public int compareTo(Point p) {
            return Long.compare(distance2, p.distance2);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}