/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Rebel rescue
 * Link: https://www.codechef.com/FEB17/problems/JAKKU
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class RebelRescue {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Point[] satellites;

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
            satellites = new Point[n];

            for (int i = 0; i < n; i++)
                satellites[i] = new Point(in.readInt(), in.readInt());

            out.println(process());
        }
    }

    private static int process() {
        if (satellites.length == 1)
            return 0;

        if (satellites.length == 2) {
            if (satellites[0].isOpposite(satellites[1]))
                return 1;

            return 0;
        }
        int minCount = satellites.length;

        for (int t = 0; t < 360; t++) {
            for (Point satellite : satellites)
                minCount = Math.min(minCount, countSatellitesInRange(minCount, satellite.oppositePoint()));

            if (minCount == 0)
                return 0;

            moveSatellites();
        }

        return minCount;
    }

    private static int countSatellitesInRange(int minSoFar, Point point) {
        int count = 0;

        for (Point satellite : satellites) {
            if (satellite.isVisible(point))
                count++;

            if (count == minSoFar)
                return minSoFar;
        }

        return count;
    }

    private static void moveSatellites() {
        for (Point satellite : satellites)
            satellite.move();
    }

    private static int angleDistance(int theta, int phi) {
        if (theta > phi)
            return angleDistance(phi, theta);

        return Math.min(phi - theta, 360 + theta - phi);
    }

    final static class Point {
        int phase, longitude;

        Point(int p, int l) {
            phase = p;
            longitude = l;
        }

        /**
         * Returns true if a satellite at this point can see the {@code point}.
         *
         * @param point
         * @return
         */
        boolean isVisible(Point point) {
            int phaseDiff = angleDistance(normalizedPhase(), point.normalizedPhase()),
                    longitudeDiff = angleDistance(normalizedLongtitude(), point.normalizedLongtitude());

            return 90 >= Math.min(phaseDiff, longitudeDiff);
        }

        int normalizedPhase() {
            return Math.min(phase, 360 - phase);
        }

        int normalizedLongtitude() {
            int normalizedLongitude = phase > 180 ? longitude + 180 : longitude;

            return normalizedLongitude >= 360 ? 0 : normalizedLongitude;
        }

        /**
         * Move the point to the position for next second.
         */
        public void move() {
            phase++;

            if (phase == 360)
                phase -= 360;
        }

        public Point oppositePoint() {
            return new Point(180 - normalizedPhase(), (180 + normalizedLongtitude()) % 360);
        }

        public boolean isOpposite(Point point) {
            return phase == 180 - normalizedPhase() && Math.abs(normalizedLongtitude() - point.normalizedLongtitude()) == 180;
        }

        public String toString() {
            return phase + ", " + longitude;
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
