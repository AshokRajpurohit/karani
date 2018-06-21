package com.ashok.hackerRank.mathematics;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sherlock and Permutations
 * https://www.hackerrank.com/challenges/sherlock-and-permutations
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SherlockAndPermutations {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] fact = new long[2001];

    static {
        fact[0] = 1;

        for (int i = 1; i < fact.length; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SherlockAndPermutations a = new SherlockAndPermutations();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            sb.append(count(in.readInt(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long count(int n, int m) {
        if (m == 0)
            return 0;

        if (m == 1)
            return 1;

        m--;

        return (fact[n + m] * inverse(fact[n]) % mod) * inverse(fact[m]) % mod;
    }

    private static long inverse(long a) {
        a = a % mod;
        if (a == 1 || a == 0)
            return a;

        long b = mod - 2;
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
