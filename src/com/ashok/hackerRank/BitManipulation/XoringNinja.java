package com.ashok.hackerRank.BitManipulation;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Xoring Ninja
 * https://www.hackerrank.com/challenges/xoring-ninja
 */

public class XoringNinja {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        XoringNinja a = new XoringNinja();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(process(in.readIntArray(n)));
        }
    }

    private static long process(int[] ar) {
        int[] bits = new int[30];
        int[] counts = new int[30];
        bits[0] = 1;
        for (int i = 1; i < 30; i++)
            bits[i] = bits[i - 1] << 1;

        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < 30; j++) {
                if ((bits[j] & ar[i]) != 0)
                    counts[j]++;
            }
        }

        long res = 0;
        for (int i = 0; i < 30; i++) {
            res += permutations(counts[i], ar.length) * bits[i];
            res = res % mod;
        }

        return res;
    }

    private static long permutations(int count, int total) {
        if (count == 0)
            return 0;

        int zeros = total - count; // number of zeros
        long res = pow(2, count - 1) * pow(2, zeros);
        if (res < 0)
            res += mod;
        return res % mod;
    }

    private static long pow(long a, long b) {
        if (b <= 0)
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
