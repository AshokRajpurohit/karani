package com.ashok.hackerRank.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Bear and Steady Gene
 * Link: https://www.hackerrank.com/challenges/bear-and-steady-gene
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BearAndSteadyGene {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] charToIndex = new int[256];
    private static char[] genes = {'A', 'C', 'G', 'T'};

    static {
        for (int i = 1; i < 4; i++)
            charToIndex[genes[i]] = i;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        String gene = in.read(n);
        out.print(process(gene.toCharArray()));
    }

    private static int process(char[] gene) {
        int[][] map = new int[4][gene.length];
        map[charToIndex[gene[0]]][0] = 1;

        for (int i = 1; i < gene.length; i++) {
            for (int j = 0; j < 4; j++)
                map[j][i] = map[j][i - 1];

            map[charToIndex[gene[i]]][i]++;
        }

        int[] max = new int[gene.length];
        int maxCount = gene.length >>> 2;
        int minLength = gene.length - 1;

        int[] left = new int[4], right = new int[4];
        for (int i = 0; i < gene.length; i++) {
            for (int j = 0; j < 4; j++) {
                left[j] = map[j][i];
                if (left[j] > maxCount)
                    continue;
            }

            boolean cont = true;

            int j = Math.min(gene.length - 1, i + minLength + 1);
            for (int k = 0; k < 4; k++) {
                right[k] = map[k][gene.length - 1] - map[k][j - 1];
                if (left[k] + right[k] > maxCount)
                    cont = false;
            }

            if (!cont)
                continue;

            j--;
            for (; j > i; j--) {
                int dnaIndex = charToIndex[gene[j]];
                right[dnaIndex]++;
                if (left[dnaIndex] + right[dnaIndex] > maxCount)
                    break;

                minLength = Math.min(minLength, j - i - 1);
            }
        }

        return minLength;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
