package com.ashok.hackerearth.marathon.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Velma and Triplets
 * Link: https://www.hackerearth.com/october-circuits/algorithm/velma-and-triplets/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class VelmaAndTriplets {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);

        int countOne = getCount(ar, 1), countTwo = getCount(ar, 2),
                countThree = getCount(ar, 3);

        long res = ncr(countOne, 3) * 3;
        res += ncr(countTwo, 2) * countOne;
        res += 1L * countOne * countTwo * countThree;
        res += ncr(countOne, 2) * (ar.length - countOne - countTwo);
        res += ncr(countOne, 2) * countTwo * 2;

        out.println(res);
    }

    private static long ncr(int n, int r) {
        if (r > n)
            return 0;

        if (r == n)
            return 1;

        if (r == 1)
            return n;

        if (r == 2)
            return 1L * n * (n - 1) / 2;

        return 1L * n * (n - 1) * (n - 2) / 6;
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    private static int getCount(int[] ar, int n) {
        int count = 0;

        for (int e : ar)
            if (e == n)
                count++;

        return count;
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
