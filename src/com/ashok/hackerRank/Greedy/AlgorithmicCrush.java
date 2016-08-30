package com.ashok.hackerRank.Greedy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Algorithmic Crush
 * Link: https://www.hackerrank.com/challenges/crush
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AlgorithmicCrush {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        AlgorithmicCrush a = new AlgorithmicCrush();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        Range[] ranges = new Range[m << 1];

        for (int i = 0, t = 0; t < m; t++) {
            int a = in.readInt(), b = in.readInt(), k = in.readInt();
            ranges[i++] = new Range(a, k);
            ranges[i++] = new Range(b + 1, -k);
        }

        Arrays.sort(ranges);
        long max = ranges[0].value, cur = ranges[0].value;
        for (int i = 1; i < ranges.length && ranges[i].index <= n; i++) {
            cur += ranges[i].value;
            max = Math.max(max, cur);
        }

        out.println(max);
    }

    final static class Range implements Comparable<Range> {
        int index = 0;
        int value = 0;

        Range(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Range range) {
            if (index == range.index)
                return value - range.value;
            return this.index - range.index;
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
    }
}
