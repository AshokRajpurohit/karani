package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Find Number
 * https://www.hackerrank.com/contests/worldcupsemifinals/challenges/find-number
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class WorldCupSemiFinalA {

    private static PrintWriter out;
    private static InputStream in;
    private static int sum, a, b, c;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        WorldCupSemiFinalA a = new WorldCupSemiFinalA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            long n = in.readLong();
            a = in.readInt();
            b = in.readInt();
            c = in.readInt();
            int temp = Math.max(a, Math.max(b, c));
            int mini = Math.min(a, Math.min(b, c));
            b = a + b + c - temp - mini;
            a = temp;
            c = mini;
            sum = a * b + b * c + c * a;
            sb.append(process(n, a, b, c)).append('\n');
        }
        out.print(sb);
    }

    private static long process(long n, int a, int b, int c) {
        if (n == 1)
            return c;

        if (n == 2)
            return b;

        if (n == 3)
            return a;

        if (n <= 0)
            return 0;

        long p = (long)(1.0 * b * c * n / sum);

        long q = (long)(1.0 * c * (n - p) / (b + c));

        long r = n - p - q;
        //        long q = c * a * (n / sum);
        //        long r = n - p - q;

        if (n % sum == 0)
            return a + process(p, a, b, c);

        if (p == 0 && q == 0)
            return c * (r - 1);

        long p1 = p > 0 ? process(p - 1, a, b, c) : 0;
        long q1 = q > 0 ? process(q - 1, a, b, c) : 0;

        long one =
            Math.max(a + process(p, a, b, c), Math.max(b + process(q, a, b, c),
                                                       c +
                                                       process(r, a, b, c)));

        long two =
            Math.max(a + p > 0 ? process(p - 1, a, b, c) : 0, Math.max(b +
                                                                       process(q,
                                                                               a,
                                                                               b,
                                                                               c),
                                                                       c +
                                                                       process(r +
                                                                               1,
                                                                               a,
                                                                               b,
                                                                               c)));

        long three =
            Math.max(a + process(p - 1, a, b, c), Math.max(b + process(q - 1,
                                                                       a, b,
                                                                       c),
                                                           c + process(r + 2,
                                                                       a, b,
                                                                       c)));

        long four =
            Math.max(a + process(p, a, b, c), Math.max(b + process(q - 1, a, b,
                                                                   c),
                                                       c + process(r + 2, a, b,
                                                                   c)));

        return Math.min(Math.min(one, two), Math.min(three, four));

        //        return Math.max(a + process(p, a, b, c),
        //                        Math.max(b + process(q, a, b, c),
        //                                 c + process(r, a, b, c)));
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
