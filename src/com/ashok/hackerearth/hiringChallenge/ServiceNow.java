/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: ServiceNow Developer Hiring Challenge
 * Link: https://www.hackerearth.com/challenge/hiring/servicenow-developer-hiring-challenge/problems
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ServiceNow {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        PopUpOrientation.solve();
//        solve();
        in.close();
        out.close();
    }

    final static class PopUpOrientation {
        private static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 4);

            while (t > 0) {
                t--;
                Point windowSize = new Point(in.readInt(), in.readInt()),
                        popUpSize = new Point(in.readInt(), in.readInt()),
                        icon = new Point(in.readInt(), in.readInt());

                sb.append(process(windowSize, popUpSize, icon)).append('\n');
            }

            out.print(sb);
        }

        private static Orientation process(Point window, Point popUp, Point icon) {
            for (Orientation orientation : Orientation.values()) {
                Point endPoint = orientation.location(icon, popUp);
                if (window.contains(endPoint)) return orientation;
            }

            throw new RuntimeException("Something is wrong. check the data.");
        }

    }

    enum Orientation {
        BOTTOM_RIGHT("bottom-right") {
            @Override
            public Point location(Point a, Point dimension) {
                return new Point(a.x + dimension.x, a.y - dimension.y);
            }
        },
        BOTTOM_LEFT("bottom-left") {
            @Override
            public Point location(Point a, Point dimension) {
                return new Point(a.x - dimension.x, a.y - dimension.y);
            }
        },
        TOP_RIGHT("top-right") {
            @Override
            public Point location(Point a, Point dimension) {
                return new Point(a.x + dimension.x, a.y + dimension.y);
            }
        },
        TOP_LEFT("top-left") {
            @Override
            public Point location(Point a, Point dimension) {
                return new Point(a.x - dimension.x, a.y + dimension.y);
            }
        };

        String value;

        Orientation(String v) {
            value = v;
        }

        public abstract Point location(Point a, Point dimension);

        public String toString() {
            return value;
        }
    }

    final static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        boolean contains(Point point) {
            return point.x >= 0 && point.x <= x && point.y >= 0 && point.y <= y;
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