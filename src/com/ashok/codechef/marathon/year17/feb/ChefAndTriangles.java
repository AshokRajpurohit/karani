/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem Name: Chef and Triangles
 * Link: https://www.codechef.com/FEB17/problems/MAKETRI
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndTriangles {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final Range GLOBAL = new Range(0, 0);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        long L = in.readLong(), R = in.readLong();
        GLOBAL.start = L;
        GLOBAL.end = R;

        long[] strands = in.readLongArray(n);
        out.println(process(strands));
    }

    private static long process(long[] ar) {
        LinkedList<Range> rangeList = new LinkedList<>();
        Arrays.sort(ar);

        Range range = new Range(ar[0], ar[1]);
        rangeList.add(range);

        for (int i = 2; i < ar.length; i++) {
            Range temp = new Range(ar[i - 1], ar[i]);

            if (range.intersects(temp)) {
                range.merge(temp);
            } else {
                rangeList.add(temp);
                range = temp;
            }
        }

        normalize(rangeList);
        return valueOf(rangeList);
    }

    private static void normalize(LinkedList<Range> ranges) {
        Collections.sort(ranges);

        LinkedList<Range> temp = new LinkedList<>();
        temp.add(ranges.removeFirst());

        while (!ranges.isEmpty()) {
            Range lastRange = temp.getLast();
            Range range = ranges.removeFirst();

            if (lastRange.intersects(range)) {
                lastRange.merge(range);
            } else
                temp.addLast(range);
        }

        ranges.addAll(temp);


        for (Range range : ranges)
            range.normalize();
    }

    private static long valueOf(LinkedList<Range> ranges) {
        long res = 0;

        for (Range range : ranges)
            if (range.valid())
                res += range.length();

        return res;
    }

    private static boolean validRange(Range range) {
        return GLOBAL.contains(range);
    }

    final static class Range implements Comparable<Range> {
        long start, end;

        public Range(long a, long b) {
            start = Math.abs(b - a) + 1; // minimum length of a side (integer value)
            end = a + b - 1; // maximum length of a side in triangle (integer value)
        }

        public boolean equals(Range range) {
            return start == range.start && end == range.end;
        }

        public boolean contains(Range range) {
            return start <= range.start && end >= range.end;
        }

        public boolean intersects(Range range) {
            return !(start > range.end || end < range.start);
        }

        public void merge(Range range) {
            start = Math.min(start, range.start);
            end = Math.max(end, range.end);
        }

        public void normalize() {
            end = Math.min(end, GLOBAL.end);
            start = Math.max(start, GLOBAL.start);
        }

        public boolean valid() {
            return end >= start;
        }

        public long length() {
            return end + 1 - start;
        }

        @Override
        public int compareTo(Range range) {
            return Long.compare(start, range.start);
        }

        public String toString() {
            return start + ", " + end;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
