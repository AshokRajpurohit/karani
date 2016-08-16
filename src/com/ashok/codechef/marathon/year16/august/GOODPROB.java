package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: A Good Problem
 * Link: https://www.codechef.com/AUG16/problems/GOODPROB
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class GOODPROB {
    private static final int limit = 1 << 14;
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[][] map;

    static {
        map = new int[limit][];
        LinkedList<Integer>[] mapList = new LinkedList[limit];
    }

    public static void main(String[] args) throws IOException {
        play();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);

        if (n <= 3000)
            out.println(bruteForce(n, ar));
        else
            out.println(process(n, ar));
    }

    private static void play() throws IOException {
        while (true) {
            int n = in.readInt();
            StringBuilder sb = new StringBuilder(n << 2);
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (function(i, n)) {
                    sb.append(i).append(' ');
                    count++;
                }
            }

            out.println(count);
//            out.println(sb);
            out.flush();
        }
    }

    private static int[] next(int[] ar) {
        int n = ar.length;
        int[] res = new int[n];
        res[n - 1] = n;

        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;

            while (j != n && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static int[] prev(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j != -1 && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static long bruteForce(int n, int[] ar) {
        long res = 0;

        for (int i = 0; i < n; i++) {
            int max = ar[i];
            for (int j = i + 1; j < n; j++) {
                max = ar[j] > max ? ar[j] : max;

                if (function(ar[i], ar[j]))
                    res += max;
            }
        }

        return res;
    }

    private static boolean function(int a, int b) {
        int c = a & b;
        return c == a || c == b;
    }

    private static long process(int n, int[] ar) {
        int[] next = next(ar), prev = prev(ar);
        long res = 0;

        for (int i = 0; i < n; i++)
            res += ar[i] * process(ar, prev[i], next[i], i);

        return res;
    }

    private static int process(int[] ar, int start, int end, int index) {
        if (end - start == 2)
            return 0;

        return 4;
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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
