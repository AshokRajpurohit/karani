package com.ashok.hackerRank.Greedy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Accessory Collection
 * Link: https://www.hackerrank.com/challenges/accessory-collection
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AccessoryCollection {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String sad = "SAD\n";

        while (t > 0) {
            t--;

            long result = process(in.readInt(), in.readInt(), in.readInt(), in.readInt());

            if (result == 0)
                sb.append(sad);
            else
                sb.append(result).append('\n');
        }

        out.print(sb);
    }

    private static long bruteForce(int L, int A, int N, int D) {
        return 0;
    }

    private static long process(int L, int A, int N, int D) {
        if (A < D || L > 1L * N * D)
            return 0;

        if (D == 1) {
            return 1L * A * L;
        }

        int n = (N - 1) / (D - 1);
        int start = A - (L / n) + 1;
        long res = n * sum(start, A);
        int count = n * (L / n);
        count = L - count;
        int topAgain = count > D - 1 ? D - 1 : count;
        res += sum(A - topAgain + 1, A);

        count -= topAgain;

        return res + 1L * count * (start - 1);
    }

    private static long sum(int start, int end) {
        long res = 1L * (end - start + 1) * (0L + start + end);
        return res >>> 1;
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
    }
}
