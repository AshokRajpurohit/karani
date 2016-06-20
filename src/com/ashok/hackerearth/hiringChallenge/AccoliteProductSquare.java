package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Product Square
 * Challenge: Accolite Java Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class AccoliteProductSquare {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AccoliteProductSquare a = new AccoliteProductSquare();
        a.solve();
        out.close();
    }

    private static String format(double n) {
        String ans = n + "00000";
        return ans.substring(0, 8);
    }

    private static boolean square(int n) {
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] hash = new int[5001];

        for (int i = 0; i < n; i++)
            hash[in.readInt()]++;

        if (n == 1) {
            out.println("0.000000");
            return;
        }

        long total = 1L * n * (n - 1) / 2;
        long square = 0;

        for (int i = 1; i < hash.length; i++) {
            if (hash[i] == 0)
                continue;

            long temp = hash[i];
            square += temp * (temp - 1) / 2;

            for (int j = i + 1; j < hash.length; j++) {
                if (hash[j] == 0)
                    continue;

                if (square(i * j)) {
                    square += temp * hash[j];
                }
            }
        }

        double p = 1.0 * square / total;
        out.println(format(p));
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
