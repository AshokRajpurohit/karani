package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and His Garden
 * Link: https://www.codechef.com/AUG16/problems/CHAHG
 * <p>
 * For any pair of trees let's say t1 and t2, with heights (initial)
 * h1 and h2 respectevly, grow at the rate of r1 and r2, the
 * comparision changes once only. if h1 < h2 and r1 > r2, once in
 * future h1 will become larger than h2 and will remain forever.
 * <p>
 * So at max there can be two time ranges for h1 < h2 and h1 > h2.
 * We will represent these time ranges in a and b respectively.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHAHG {
    private static final long max = Long.MAX_VALUE; // infinite time
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

    /**
     * Populates TimeRange objects a and b for the specified height
     * and rate arrays.
     * <p>
     * TimeRange a will be updated for the time range for first case:
     * h1 < h2 > h3 < h4 > h5 ....
     * <p>
     * Similarly TimeRange b will be updated when
     * h1 > h2 < h3 > h4 ...
     *
     * @param h height array
     * @param r rate of change array
     */
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

    /**
     * Merges time ranges a and b if these are mergeable.
     * First it sorts these ranges based on startTime.
     * <p>
     * Two time ranges (t1, t2) and (t3, t4) are mergeable
     * if t2 >= t3 - 1. This example will make it clear:
     * <p>
     * (1, 4) and (5, 10) are mergeable and the new range is
     * (1, 10)
     */
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

    /**
     * Updates TimeRange tr for two trees t1 and t2 with heights h1 and h2
     * when h1 is smaller than h2.
     * <p>
     * let's say the time range for h1 to be lesser than h2 is (t1, t2) both
     * inclusive and the values in tr is (tr1, tr2).
     * <p>
     * Then the updated values in tr would be common time range in (t1, t2)
     * and (tr1, tr2) or it would be (max(t1, tr1), min(t2, tr2)).
     * <p>
     * All the conditions in the method are representation of above statement.
     *
     * @param h1 height for first tree
     * @param r1 growth rate for first tree
     * @param h2 height for second tree
     * @param r2 growth rate for second tree
     * @param tr TimeRange to be updated when height of t1 is less than of t2
     */
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

    /**
     * Prints or appends the time ranges to the StringBuilder sb.
     *
     * @param sb
     */
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

    /**
     * Returns true if the TimeRange object is valid.
     *
     * @param timeRange TimeRange to be validated.
     * @return true if timeRange is valid
     */
    private static boolean valid(TimeRange timeRange) {
        return timeRange.end >= timeRange.start;
    }

    /**
     * TimeRange class to represent time range.
     * {@code max} represents infinite time.
     */
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
