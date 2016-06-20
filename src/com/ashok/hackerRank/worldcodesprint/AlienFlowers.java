package com.ashok.hackerRank.worldcodesprint;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Alien Flowers
 * https://www.hackerrank.com/contests/worldcodesprint/challenges/colorful-ornaments
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class AlienFlowers {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] fact;
    private static int mod = 1000000007;

    static {
        fact = new long[1000000];
        fact[0] = 1;

        for (int i = 1; i < fact.length; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AlienFlowers a = new AlienFlowers();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.readInt(), in.readInt(), in.readInt(),
                            in.readInt()));
    }

    private static long process(int rr, int rb, int bb, int br) {
        if (Math.abs(rb - br) > 1)
            return 0;

        long res = 0;

        if (rb > br) {
            int cr = rb, cb = rb; // cr is count of R and cb is count of B
            return ncr(cr + rr - 1, rr) * ncr(cb + bb - 1, bb) % mod;
        }

        if (br > rb) {
            int cr = rb, cb = rb; // cr is count of R and cb is count of B
            return ncr(cr + rr - 1, rr) * ncr(cb + bb - 1, bb) % mod;
        }

        // rbr way
        int cr = rb + 1, cb = rb;
        res = ncr(cr + rr - 1, rr) * ncr(cb + bb - 1, bb) % mod;

        // brb way
        cb = rb + 1;
        cr = rb;
        return (res + ncr(cr + rr - 1, rr) * ncr(cb + bb - 1, bb) % mod) % mod;
    }

    public static long ncr(int n, int r) {
        if (n == r || r == 0)
            return 1;

        if (n < r || r <= 0 || n < 0)
            return 0;

        long res = fact[n] * pow(fact[r], mod - 2) % mod;

        return res * pow(fact[n - r], mod - 2) % mod;
    }

    private static long pow(long a, long b) {
        a = a % mod;

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
