/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Art of Balance
 * Link: https://www.codechef.com/FEB19A/problems/ARTBALAN
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ArtOfBalance {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int[] CHAR_INDEX_MAP = new int[256];

    static {
        for (int i = 'A'; i <= 'Z'; i++) CHAR_INDEX_MAP[i] = i - 'A';
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            sb.append(process(in.read().toCharArray())).append('\n');
        }

        out.print(sb);
    }

    private static int process(char[] chars) {
        int[] counts = getCounts(chars);
        return process(counts);
    }

    private static int process(int[] counts) {
        Arrays.sort(counts);
        int sum = sum(counts), len = counts.length, min = sum - counts[len - 1];
        int moved = 0;
        for (int size = len, index = 0; size > 0; size--, index++) {
            if (sum % size == 0) {
                int ops = (moved + diff(counts, index, sum / size));// >>> 1;
                min = Math.min(min, ops);
            }
            moved += counts[index];
        }

        int size = len + 1;
        while (size <= 26) {
            if (sum % size == 0) {
                int ops = diff(counts, 0, sum / size);
                if (min <= ops) break;
                min = ops;
            }

            size++;
        }

        return min;
    }

    private static int diff(int[] ar, int index, int val) {
        int sum = 0;
        for (int i = index; i < ar.length; i++) if (ar[i] > val) sum += ar[i] - val; //sum += Math.abs(ar[i] - val);
        return sum;
    }

    private static int sum(int[] ar) {
        int sum = 0;
        for (int e : ar) sum += e;
        return sum;
    }

    private static int[] getCounts(char[] chars) {
        int[] counts = new int[26];
        for (char ch : chars) counts[CHAR_INDEX_MAP[ch]]++;
        return normalize(counts);
    }

    private static int[] normalize(int[] ar) {
        int size = ar.length - count(ar, 0);
        int[] nar = new int[size];
        for (int i = 0, index = 0; i < ar.length; i++) {
            if (ar[i] != 0) nar[index++] = ar[i];
        }

        return nar;
    }

    private static int count(int[] ar, int val) {
        int count = 0;
        for (int e : ar) if (e == val) count++;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
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
            for (; offset < bufferSize; ++offset) {
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