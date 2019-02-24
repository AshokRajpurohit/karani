/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem Name: Manhattan Rectangle
 * Link: https://www.codechef.com/FEB19A/problems/MANRECT
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ManhattanRectangle {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static SystemInteractor interactor;

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            interact(new DefaultSystemInteractor());
        }
    }

    /**
     * Uses mock system interactor for testing. can be used to test your code.
     *
     * @throws IOException
     */
    private static void test() throws IOException {
        Random random = new Random();
        int limit = in.readInt();
        while (true) {
            int x1 = random.nextInt(limit), x2 = random.nextInt(limit);
            int y1 = random.nextInt(limit), y2 = random.nextInt(limit);
            Point a = new Point(Math.min(x1, x2), Math.min(y1, y2));
            Point b = new Point(Math.max(x1, x2), Math.max(y1, y2));

            SystemInteractor interactor = new MockSystemInteractor(new Rectangle(a, b));
            try {
                if (interact(interactor) < 0) {
                    out.println(new Rectangle(a, b));
                    out.flush();
                    interact(new MockSystemInteractor(new Rectangle(a, b)));
                }
            } catch (Throwable e) {
                out.println(new Rectangle(a, b));
                out.flush();
                interact(new MockSystemInteractor(new Rectangle(a, b)));
            }
        }
    }

    private static int interact(SystemInteractor interactor) throws IOException {
        Point lowerLeft = getLowerLeftPoint(interactor), upperRight = getUpperRightPoint(interactor, lowerLeft);
        interactor.submit(new Rectangle(lowerLeft, upperRight));
        return interactor.result();
    }

    private static Point getLowerLeftPoint(SystemInteractor interactor) throws IOException {
        int x = 0, y = 0, da = 0, db = 0;
        int originDistance = interactor.queryManhattonDistance(ORIGIN), half = originDistance >>> 1;
        if (originDistance == 0) return ORIGIN;
        Point a, b;
        if ((originDistance & 1) == 0) {
            a = new Point(half - 1, half + 1);
            da = interactor.queryManhattonDistance(a);
            if (da == 0) return a;
            b = new Point(half + 1, half - 1);
        } else {
            a = new Point(half, half + 1);
            da = interactor.queryManhattonDistance(a);
            if (da == 0) return a;
            b = new Point(half + 1, half);
        }

        db = interactor.queryManhattonDistance(b);
        if (db == 0) return b;
        if (da == db) return new Point(half, half);
        int dab = manhattonDistance(a, b); // possible values 2 and 4 in case of odd and even origin distance.
        int dx = Math.abs(a.x - b.x), dy = Math.abs(a.y - b.y);
        if (da < db) {
            int diff = db - da;
            if (diff == dab) { // topRight corner is left to a.
                a = new Point(a.x - (da >>> 1), a.y + (da >>> 1));
                da = interactor.queryManhattonDistance(a);
            }

            if (diff == 1 && da == 1 && dy != diff) return new Point(half, half);
            return new Point(originDistance - a.y - da, a.y + da);
        }

        int diff = da - db;
        if (diff == dab) { // topLeft corner is below b.
            b = new Point(b.x + (db >>> 1), b.y - (db >>> 1));
            db = interactor.queryManhattonDistance(b);
        }

        if (diff == 1 && db == 1 && dx != diff) return new Point(half, half);
        return new Point(b.x + db, originDistance - b.x - db);
    }

    private static Point getUpperRightPoint(SystemInteractor interactor, Point lowerLeft) throws IOException {
        int x = 0, y = 0;
        int cornerDistance = interactor.queryManhattonDistance(TOP_RIGHT), half = cornerDistance >>> 1;
        if (cornerDistance == 0) return TOP_RIGHT;
        Point a, b;
        if (lowerLeft.x >= TOP_RIGHT.x - cornerDistance) {
            Point p = new Point(lowerLeft.x, TOP_RIGHT.x + TOP_RIGHT.y - cornerDistance - lowerLeft.x);
            int dy = interactor.queryManhattonDistance(p);
            y = p.y - dy;
            x = TOP_RIGHT.x + TOP_RIGHT.y - cornerDistance - y;
            return new Point(x, y);
        }

        if (lowerLeft.y >= TOP_RIGHT.x - cornerDistance) {
            Point p = new Point(TOP_RIGHT.x + TOP_RIGHT.y - cornerDistance - lowerLeft.y, lowerLeft.y);
            int dx = interactor.queryManhattonDistance(p);
            x = p.x - dx;
            y = TOP_RIGHT.x + TOP_RIGHT.y - cornerDistance - x;
            return new Point(x, y);
        }

        Point p = new Point(TOP_RIGHT.x, TOP_RIGHT.y - cornerDistance);
        int dx = interactor.queryManhattonDistance(p);
        x = p.x - dx;
        y = TOP_RIGHT.x + TOP_RIGHT.y - cornerDistance - x;
        return new Point(x, y);
    }

    private static Point fromTop(Point point) {
        return new Point(TOP_RIGHT.x - point.x, TOP_RIGHT.y - point.y);
    }

    private static int queryManhattonDistance(Point point) throws IOException {
        out.println("Q " + point);
        out.flush();
        return in.readInt();
    }

    private static int manhattonDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static int getDistance(int from, int to, int val) {
        return Math.max(0, Math.max(from - val, val - to));
    }

    private final static Point ORIGIN = new Point(0, 0), TOP_RIGHT = new Point(1000000000, 1000000000);

    static abstract class SystemInteractor {
        int count = 7;

        abstract int queryManhattonDistance(Point point) throws IOException;

        abstract int result() throws IOException;

        abstract int submit(Rectangle rectangle) throws IOException;

        void validate(Point point) {
            if (point.x < 0 || point.y < 0 || point.x > TOP_RIGHT.x || point.y > TOP_RIGHT.y)
                throw new RuntimeException("invalid point");
        }
    }

    final static class DefaultSystemInteractor extends SystemInteractor {
        int result = 0;

        @Override
        public int queryManhattonDistance(Point point) throws IOException {
            out.println("Q " + point);
            out.flush();
            return in.readInt();
        }

        @Override
        public int result() throws IOException {
            return result == 0 ? (result = in.readInt()) : result;
        }

        @Override
        public int submit(Rectangle rectangle) throws IOException {
            out.println("A " + rectangle);
            out.flush();
            return result;
        }
    }

    /**
     * MockSystemInteractor is to mimick the codechef system. It verifies the queries and return appropriate
     * response.
     */
    final static class MockSystemInteractor extends SystemInteractor {
        final Interactive interactor;
        private boolean found = false;

        MockSystemInteractor(Rectangle rectangle) {
            interactor = new Interactive(rectangle);
        }

        @Override
        public int queryManhattonDistance(Point point) throws IOException {
            validate(point);
            if (count == 0) throw new RuntimeException("limit reached");
            count--;
            return interactor.getManhattonDistance(point);
        }

        @Override
        public int result() throws IOException {
            return found ? 1 : -1;
        }

        @Override
        public int submit(Rectangle rectangle) throws IOException {
            found = found || rectangle.equals(interactor.rectangle);
            return result();
        }
    }

    private final static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point clone() {
            return new Point(x, y);
        }

        public String toString() {
            return x + " " + y;
        }

        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    final static class Interactive {
        final Rectangle rectangle;

        Interactive(Rectangle rectangle) {
            this.rectangle = rectangle;
        }

        int getManhattonDistance(Point point) {
            return getDistance(rectangle.lowerLeft.x, rectangle.upperRight.x, point.x) + getDistance(rectangle.lowerLeft.y, rectangle.upperRight.y, point.y);
        }
    }

    final static class Rectangle {
        final Point lowerLeft, upperRight;

        Rectangle(Point lowerLeft, Point upperRight) {
            this.lowerLeft = lowerLeft;
            this.upperRight = upperRight;
        }

        public String toString() {
            return lowerLeft + " " + upperRight;
        }

        public boolean equals(Object object) {
            Rectangle r = (Rectangle) object;
            return lowerLeft.equals(r.lowerLeft) && upperRight.equals(r.upperRight);
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