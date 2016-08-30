package com.ashok.hackerRank.Greedy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Sherlock and MiniMax
 * Link: https://www.hackerrank.com/challenges/sherlock-and-minimax
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SherlockAndMiniMax {
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
        int p = in.readInt(), q = in.readInt();
        out.println(process(n, ar, p, q));
    }

    private static int process(int n, int[] ar, int p, int q) {
        Arrays.sort(ar);
        int m = 0, max = 0;
        int start = 0, end = 0;
        if (p < ar[0]) {
            start = p;
            end = Math.min(ar[0], q);

            m = start;
            max = end - start;
        }

        start = ar[0];
        for (int i = 1; i < n && ar[i - 1] < q; i++) {
            start = ar[i - 1];
            end = ar[i];
            int mid = (start + end) >>> 1;
            int diff = (end - start) >>> 1;

            if (mid < p) {
                mid = p;
            }

            if (mid > q)
                mid = q;

            diff = Math.min(mid - start, end - mid);

            if (diff > max) {
                max = diff;
                m = mid;
            }
        }

        if (q > ar[n - 1]) {
            int diff = q - ar[n - 1];
            if (diff > max) {
                m = q;
                max = diff;
            }
        }

        return m;
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
