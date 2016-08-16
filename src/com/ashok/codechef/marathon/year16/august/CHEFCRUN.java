package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Circle Run
 * Link: https://www.codechef.com/AUG16/problems/CHEFCRUN
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHEFCRUN {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CHEFCRUN a = new CHEFCRUN();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            int n = in.readInt();
            sb.append(calculate(n, in.readIntArray(n), in.readInt() - 1,
                    in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private static long calculate(int n, int[] ar, int start, int end) {
        long[] clockWise = populateClockWise(n, ar, start);
        long[] minClockWiseStart = new long[n],
                minAntiClockWiseStart = new long[n],
                minClockWiseEnd = new long[n],
                minAntiClockWiseEnd = new long[n];

        for (int i = start, j = start + 1; ; i++, j++) {
            if (i == n)
                i = 0;

            if (j == n)
                j = 0;

            if (i == end)
                break;

            minClockWiseStart[j] = Math.min(clockWise[j], minClockWiseStart[i]);
        }

        for (int i = start, j = start - 1; ; i--, j--) {
            if (i == -1)
                i = n - 1;

            if (j == -1)
                j = n - 1;

            if (i == end)
                break;

            minAntiClockWiseStart[j] = Math.min(clockWise[start] - clockWise[j],
                    minAntiClockWiseStart[i]);
        }

        for (int i = end, j = end + 1; ; i++, j++) {
            if (i == n)
                i = 0;

            if (j == n)
                j = 0;

            if (i == start)
                break;

            minClockWiseEnd[j] = Math.min(minClockWiseEnd[i], clockWise[j] - clockWise[end]);
        }

        for (int i = end, j = end - 1; ; i--, j--) {
            if (i == -1)
                i = n - 1;

            if (j == -1)
                j = n - 1;

            if (i == start)
                break;

            minAntiClockWiseEnd[j] = Math.min(minAntiClockWiseEnd[i], clockWise[end] - (j == start ? 0 : clockWise[j]));
        }

        long res = Math.min(clockWise[start], 0) + Math.min(clockWise[end], clockWise[start] - clockWise[end]);
        long clock = clockWise[start] - clockWise[end];
        for (int i = end; ; i++) {
            if (i == n)
                i = 0;

            if (i == start)
                break;

            clock = Math.min(clock, minClockWiseEnd[i] + minAntiClockWiseStart[i]);
        }

        res = Math.min(res, clock * 2 + clockWise[end]);
        long antiClock = clockWise[end];
        for (int i = start; ; i++) {
            if (i == n)
                i = 0;

            if (i == end)
                break;

            antiClock = Math.min(antiClock, minClockWiseStart[i] + minAntiClockWiseEnd[i]);
        }

        return Math.min(res, antiClock * 2 + clockWise[start] - clockWise[end]);
    }

    private static long[] populateClockWise(int n, int[] ar, int start) {
        long[] res = new long[n];

        for (int i = start, j = start + 1, k = 0; k < n; i++, j++, k++) {
            if (i == n)
                i = 0;

            if (j == n)
                j = 0;

            res[j] = res[i] + ar[i];
        }

        return res;
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
