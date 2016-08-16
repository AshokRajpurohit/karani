package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and His Garden
 * Link: https://www.codechef.com/AUG16/problems/CHAHG
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHAHG {
    private static final long max = Long.MAX_VALUE;
    private static final String infinite = "Inf";
    private static final TimeRange a = new TimeRange(), b = new TimeRange();
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] heights = new int[n], rates = new int[n];

            for (int i = 0; i < n; i++) {
                heights[i] = in.readInt();
                rates[i] = in.readInt();
            }

            if (n == 1) {
                sb.append("1\n0 ").append(infinite).append('\n');
                continue;
            }

            process(heights, rates);
            append(sb);
        }

        out.print(sb);
    }

    private static void process(int[] h, int[] r) {
        a.clear();
        b.clear();

        boolean high = true;
        for (int i = 1; i < h.length; i++) {
            setTime(h[i - 1], r[i - 1], h[i], r[i], a, high);
            high = !high;
        }

        high = false;
        for (int i = 1; i < h.length; i++) {
            setTime(h[i - 1], r[i - 1], h[i], r[i], b, high);
            high = !high;
        }

        merge();
    }

    private static void merge() {
        if (!valid(a) || !valid(b))
            return;

        if (a.start > b.start) {
            long temp = a.start;
            a.start = b.start;
            b.start = temp;

            temp = a.end;
            a.end = b.end;
            b.end = temp;
        }

        if (a.end >= b.start - 1) {
            a.end = Math.max(a.end, b.end);
            b.end = -1;
        }
    }

    private static void setTime(int h1, int r1, int h2, int r2, TimeRange tr,
                                boolean high) {
        if (!valid(tr))
            return;

        if (high)
            setTime(h1, r1, h2, r2, tr);
        else
            setTime(h2, r2, h1, r1, tr);
    }

    private static void setTime(int h1, int r1, int h2, int r2, TimeRange tr) {
        if (h1 == h2) {
            if (r2 > r1) {
                tr.start = Math.max(1, tr.start);
                return;
            }

            tr.end = -1;
            return;
        }

        if (r1 == r2) {
            if (h2 > h1)
                return;

            tr.end = -1;
            return;
        }

        if (h2 > h1) {
            if (r2 > r1)
                return;

            long t = (h2 - h1 - 1) / (r1 - r2);
            tr.end = Math.min(tr.end, t);
            return;
        }

        if (r1 > r2) {
            tr.end = -1;
            return;
        }

        long t = 1 + (h1 - h2) / (r2 - r1);
        tr.start = Math.max(tr.start, t);
    }

    private static void invalid(TimeRange timeRange) {
        timeRange.end = -1;
    }

    private static void append(StringBuilder sb) {
        int count = 0;

        if (valid(a))
            count++;

        if (valid(b))
            count++;

        sb.append(count).append('\n');

        if (count != 0 && valid(a)) {
            count--;

            sb.append(a.start).append(' ').append(a.end == max ? infinite : a
                    .end).append('\n');
        }

        if (count != 0) {
            sb.append(b.start).append(' ')
                    .append(b.end == max ? infinite : b.end).append('\n');
        }
    }

    private static boolean valid(TimeRange timeRange) {
        return timeRange.end >= timeRange.start;
    }

    final static class TimeRange {
        long start = 0, end = max;

        void clear() {
            start = 0;
            end = max;
        }
    }

    final static class InputReader {
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
    }
}
