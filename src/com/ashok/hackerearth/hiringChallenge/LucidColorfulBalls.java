package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Colorful balls
 * Challenge: Lucid Technologies Java Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class LucidColorfulBalls {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] fact = new long[41];
    private static int mod = 1000000007;

    static {
        fact[0] = 1;

        for (int i = 1; i < 41; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        LucidColorfulBalls a = new LucidColorfulBalls();
        a.solve();
        out.close();
    }

    private static long get(int s, int a, int b, int c, int d) {
        if (a < 2)
            return 0;

        return fact[s - 1] / (fact[a - 1] * fact[b] * fact[c] * fact[d]);
    }

    private static long get(int s, int a, int b) {
        if (a < 2 || b < 2)
            return 0;

        return fact[s - 2] / (fact[a - 1] * fact[b - 1]);
    }

    public static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int a = in.readInt(), b = in.readInt(), c = in.readInt(), d =
                in.readInt();

        int s = a + b + c + d;
        long res = fact[s] / fact[a];
        res /= fact[b];
        res /= fact[c];
        res /= fact[d];

        res -= get(s, a, b, c, d);
        res -= get(s, b, a, c, d);
        res -= get(s, c, b, a, d);
        res -= get(s, d, b, c, a);

        res += get(s, a, b);
        res += get(s, a, c);
        res += get(s, a, d);
        res += get(s, b, d);
        res += get(s, b, c);
        res += get(s, c, d);

        out.println(res);
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
