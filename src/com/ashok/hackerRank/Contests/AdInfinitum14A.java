package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Most Distant
 * https://www.hackerrank.com/contests/infinitum14/challenges/most-distant
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class AdInfinitum14A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AdInfinitum14A a = new AdInfinitum14A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int minx = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, miny =
            Integer.MAX_VALUE, maxy = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int x = in.readInt(), y = in.readInt();
            minx = Math.min(minx, x);
            maxx = Math.max(maxx, x);
            miny = Math.min(miny, y);
            maxy = Math.max(maxy, y);
        }

        double d = Math.max(maxx - minx, maxy - miny);
        long absMaxX = Math.max(maxx, Math.abs(minx)), absMaxY =
            Math.max(maxy, Math.abs(miny));
        d = Math.max(d, Math.sqrt(absMaxX * absMaxX + absMaxY * absMaxY));
        out.println(d);
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
    }
}
