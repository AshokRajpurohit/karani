package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Forest Gathering
 * https://www.codechef.com/MAY16/problems/FORESTGA
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class FORESTGA {

    private static PrintWriter out;
    private static InputStream in;
    private int[] height, rate;
    private long W, L;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        FORESTGA a = new FORESTGA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        W = in.readLong();
        L = in.readLong();

        height = new int[n];
        rate = new int[n];

        for (int i = 0; i < n; i++) {
            height[i] = in.readInt();
            rate[i] = in.readInt();
        }

        out.println(process());
    }

    private long process() {
        if (get(0) == 0)
            return 0;

        long start = 0, end = getMaxTime();
        long mid = (start + end) >>> 1;

        while (mid > start) {
            int temp = get(mid);

            if (temp == 1)
                end = mid;
            else if (temp == -1)
                start = mid;
            else
                return mid;

            mid = (start + end) >>> 1;
        }

        if (get(mid) >= W)
            return mid;

        return end;
    }

    private int get(long time) {
        long res = 0;

        for (int i = 0; i < rate.length; i++) {
            res += getHeight(time, height[i], rate[i]);

            if (res > W)
                return 1;
        }

        if (res == W)
            return 0;

        return -1;
    }

    private long getHeight(long time, int h, int r) {
        long res = h + time * r;

        if (res < L && res >= 0)
            return 0;

        if (res < 0)
            return W + 1;

        return res;
    }

    private long getMaxTime() {
        long averageHeight = W / rate.length;

        if (averageHeight < L)
            averageHeight = L;

        long maxTime = 0;
        for (int i = 0; i < rate.length; i++)
            maxTime = Math.max(maxTime, (averageHeight - height[i]) / rate[i]);

        return maxTime;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
    }
}
