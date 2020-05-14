/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.r1B;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BlindFold {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final double TWO_SQRT = Math.sqrt(2);
    private static final int MIN = -1000000000, MAX = -MIN;
    private static final String CASE = "Case #";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt(), a = in.readInt(), b = in.readInt();
        for (int i = 1; i <= t; i++) {
            process(a, b);
        }
    }

    private static void process(int a, int b) throws IOException {
        int side = (int) (TWO_SQRT * a);
        Status status = Status.WRONG;
        Point ref = new Point(0, 0);
        outer:
        for (int x = MIN; x <= MAX; x += side) {
            for (int y = MIN; y <= MAX; y += side) {
                ref = new Point(x, y);
                status = validate(ref);
                if (status != Status.MISS) break outer;
            }
        }

        if (status.equals(Status.CENTER)) return;
        if (status == Status.WRONG) {
            abort();
            return;
        }
        Point left = findLeftBoundry(ref, side),
                right = findRightBoundry(ref, side),
                leftUp = findUpBoundry(left, side),
                leftDown = findDwonBoundry(left, side);

        Point mid = mid(left, right);
        Point center = mid(findUpBoundry(mid, side), findDwonBoundry(mid, side));
        status = validate(center);
        if (status != Status.CENTER) abort();
    }

    private static void abort() {
        out.println("incorrect");
        out.flush();
    }

    private static Point mid(Point a, Point b) {
        long x = (a.x + b.x) / 2, y = (a.y + b.y) / 2;
        return new Point(x, y);
    }

    private static Point findDwonBoundry(Point ref, int side) throws IOException {
        if (validate(new Point(ref.x, ref.y - 1)) == Status.MISS) return ref;
        long a = ref.y - 2 * side, b = ref.y, m = (a + b) / 2;
        long x = ref.x;
        a = Math.max(MIN, a);
        while (b > a - 1) {
            Status status = validate(new Point(x, m));
            if (status == Status.WRONG) return null;
            if (status == Status.MISS) a = m + 1;
            else b = m;
            m = (a + b) / 2;
        }

        return new Point(a, x);
    }

    private static Point findUpBoundry(Point ref, int side) throws IOException {
        if (validate(new Point(ref.x, ref.y + 1)) == Status.MISS) return ref;
        long a = ref.y, b = a + 2 * side, m = (a + b) / 2;
        long x = ref.x;
        a = Math.min(MAX, a);
        while (b > a - 1) {
            Status status = validate(new Point(x, m));
            if (status == Status.MISS) b = m - 1;
            else a = m;
            m = (a + b) / 2;
        }

        return new Point(a, x);
    }

    private static Point findRightBoundry(Point ref, int side) throws IOException {
        long a = ref.x, b = ref.x + 2 * side, m = (a + b) / 2;
        b = Math.min(MAX, b);
        long y = ref.y;
        while (b > a - 1) {
            Status status = validate(new Point(m, y));
            if (status == Status.MISS) b = m - 1;
            else a = m;
            m = (a + b) / 2;
        }

        return new Point(a, y);
    }

    private static Point findLeftBoundry(Point ref, int side) throws IOException {
        long a = ref.x - 2 * side, b = ref.x, m = (a + b) / 2;
        a = Math.max(a, MIN);
        long y = ref.y;
        while (b > a - 1) {
            Status status = validate(new Point(m, y));
            if (status == Status.MISS) a = m + 1;
            else b = m;
            m = (a + b) / 2;
        }

        return new Point(a, y);
    }

    private static Status validate(Point point) throws IOException {
        out.println(point);
        out.flush();
        Status status = Status.valueOf(in.read());
        return status;
    }

    enum Status {
        CENTER, MISS, HIT, WRONG;
    }

    final static class Point {
        final long x, y;

        Point(final long x, final long y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + " " + y;
        }
    }

    private static double distance(Point a, Point b) {
        long dx = 0L + a.x - b.x, dy = 0L + a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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
