package com.ashok.codechef.marathon.year16.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Tetris
 * Link: https://www.codechef.com/JULY16/problems/CHEFTET
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHEFTET {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CHEFTET a = new CHEFTET();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(process(n, in.readIntArray(n), in.readIntArray(n)));
        }
    }

    private static long process(int n, int[] fall, int[] base) {
        if (n == 1)
            return base[0] + fall[0];

        long total = 0;
        for (int e : base)
            total += e;

        for (int e : fall)
            total += e;

        if (total % n != 0)
            return -1;

        int avg = (int) (total / n);
        int bi = 1;

        for (int e : base)
            if (e > avg)
                return -1;

        for (int e : fall)
            if (e >= avg)
                return -1;


        if (!validate(0, 1, fall, avg - base[0]))
            return -1;

        while (bi < n - 1) {
            if (validate(bi, bi + 1, fall, avg - base[bi] - fall[bi - 1]))
                bi++;
            else
                return -1;
        }

        return base[n - 1] + fall[n - 1] + fall[n - 2] == avg ? avg : -1;
    }

    private static boolean validate(int i, int j, int[] ar, int target) {
        return target == 0 || check(i, ar, target) || check(j, ar, target) ||
                check(i, j, ar, target);
    }

    private static boolean validate(int i, int j, int k, int[] ar, int target) {
        if (target > ar[i] + ar[j] + ar[k] || target < Math.min(ar[i], ar[j]
                > ar[k] ? ar[k] : ar[j]))
            return false;

        return target == 0 || check(i, ar, target) || check(j, ar, target) ||
                check(k, ar, target) || check(i, j, k, ar, target);
    }

    private static boolean check(int i, int j, int[] ar, int target) {
        if (ar[i] + ar[j] == target) {
            ar[i] = 0;
            ar[j] = 0;
            return true;
        }

        return false;
    }

    private static boolean check(int i, int[] ar, int target) {
        if (ar[i] == target) {
            ar[i] = 0;
            return true;
        }

        return false;
    }

    private static boolean check(int i, int j, int k, int[] ar, int target) {
        return check(i, j, ar, target) || check(j, k, ar, target) ||
                check(i, k, ar, target);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
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