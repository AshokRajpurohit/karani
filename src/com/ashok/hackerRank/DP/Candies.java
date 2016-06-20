package com.ashok.hackerRank.DP;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Candies
 * https://www.hackerrank.com/challenges/candies
 *
 * Alice is a kindergarden teacher.
 * She wants to give some candies to the children in her class.
 * All the children sit in a line ( their positions are fixed),
 * and each  of them has a rating score according to his or her
 * performance in the class. Alice wants to give at least 1 candy to each
 * child. If two children sit next to each other, then the one with the
 * higher rating must get more candies. Alice wants to save money, so she
 * needs to minimize the total number of candies given to the children.
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */


public class Candies {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Candies a = new Candies();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ratings = in.readIntArray(n);

        out.println(calculate(ratings, n));
    }

    private static long calculate(int[] ratings, int n) {
        long[] left = new long[n], right = new long[n];

        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1])
                left[i] = left[i - 1] + 1;
            else
                left[i] = 1;
        }

        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1])
                right[i] = right[i + 1] + 1;
            else
                right[i] = 1;
        }

        long res = 0;

        for (int i = 0; i < n; i++)
            res += Math.max(left[i], right[i]);

        return res;
    }

    private static void println(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 2);

        for (long e : ar)
            sb.append(e).append(' ');

        out.println(sb);
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
