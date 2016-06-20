package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Simple One
 * https://www.hackerrank.com/contests/infinitum14/challenges/simple-one
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class AdInfinitum14B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AdInfinitum14B a = new AdInfinitum14B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(tan(in.readInt(), in.readInt(),
                          in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long tan(int p, int q, int n) {
        if (n == 0)
            return 0;

        long res = tan(p, q, n >>> 1);
        long den = (1 - res * res) % mod;
        if (den < 0)
            den += mod;

        res = (res << 1) * pow(den, mod - 2) % mod;
        if ((n & 1) == 0)
            return res;

        long value = p * pow(q, mod - 2) % mod;
        den = (1 - value * res) % mod;
        if (den < 0)
            den += mod;

        return (res + value) * pow(den, mod - 2) % mod;
    }

    public static long pow(long a, long b) {
        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0)
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
