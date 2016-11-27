package com.ashok.hackerearth.marathon.oct16;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Shil and Lab Assignment
 * Link: https://www.hackerearth.com/october-circuits/algorithm/shil-and-lab-assignment-14/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ShilAndLabAssignment {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] factors; // stores smallest factor for n at index n

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void populate(int n) {
        factors = new int[n + 1];

        factors[1] = 1;

        for (int i = 2; i <= n; i++) {
            if (factors[i] != 0)
                continue;

            int j = i;
            while (j <= n) {
                if (factors[j] == 0)
                    factors[j] = i;

                j += i;
            }

            factors[i] = 1;
        }
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        int[] ar = Generators.generateRandomIntegerArray(n, 100000);//in.readIntArray(n);
        Arrays.sort(ar);
        reverse(ar);
        populate(Math.max(m, ar[0]));

        int count = 0;
        boolean[] check = new boolean[m + 1];
        for (int e : ar) {
            if (possible(e, check))
                count++;
        }

        out.println(count);
        out.println(bruteForce(ar, m));
    }

    private static int bruteForce(int[] ar, int m) {
        Arrays.sort(ar);
        reverse(ar);
        boolean[] check = new boolean[Math.min(ar[ar.length - 1], m) + 1];
        int count = 0;

        for (int e : ar) {
            for (int i = m; i > 0; i--) {
                if (!check[i] && e % i == 0) {
                    count++;
                    check[i] = true;
                    break;
                }
            }
        }

        return count;
    }

    private static boolean possible(int n, boolean[] check) {
        if (n == 0)
            return false;

        if (n == 1) {
            if (check[1])
                return false;

            check[1] = true;
            return true;
        }

        if (n < check.length && !check[n]) {
            check[n] = true;
            return true;
        }

        int factor = factors[n];
        if (factor == 1)
            return possible(factor, check);

        return possible(factor, check) || possible(n / factor, check);
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }
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
