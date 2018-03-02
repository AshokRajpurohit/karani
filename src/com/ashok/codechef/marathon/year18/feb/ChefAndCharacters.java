/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef And His Characters
 * Link: https://www.codechef.com/FEB18/problems/CHEFCHR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndCharacters {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String LOVELY = "lovely", NORMAL = "normal";
    private static final char[] chef = "chef".toCharArray();
    private static final int CHEF_LEN = chef.length;
    private static final SlidingWindow SLIDING_WINDOW = new SlidingWindow();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int count = process(in.read().toCharArray());
            if (count != 0)
                sb.append(LOVELY).append(' ').append(count);
            else
                sb.append(NORMAL);

            sb.append('\n');
        }

        out.print(sb);
    }

    private static int process(char[] chars) {
        int len = chars.length;
        if (len < CHEF_LEN)
            return 0;

        SLIDING_WINDOW.clear();
        int count = 0;
        for (int i = 0; i < CHEF_LEN; i++)
            SLIDING_WINDOW.add(chars[i]);

        if (SLIDING_WINDOW.isComplete())
            count++;

        for (int i = CHEF_LEN, j = 0; i < len; i++, j++) {
            SLIDING_WINDOW.add(chars[i]);
            SLIDING_WINDOW.remove(chars[j]);

            if (SLIDING_WINDOW.isComplete())
                count++;
        }

        return count;
    }

    final static class SlidingWindow {
        final CharMap charMap = new CharMap(chef);
        final int[] counts = new int[256];
        final int len = CHEF_LEN;
        private int count = 0;

        private void clear() {
            count = 0;
            for (int e : chef)
                counts[e] = 0;
        }

        private void add(int e) {
            if (!charMap.isValid(e))
                return;

            counts[e]++;
            if (counts[e] == 1) count++;
        }

        private void remove(int e) {
            if (!charMap.isValid(e))
                return;

            counts[e]--;
            if (counts[e] == 0) count--;
        }

        private boolean isComplete() {
            return count == len;
        }
    }

    final static class CharMap {
        final boolean[] map = new boolean[256];

        CharMap(char[] chars) {
            for (char ch : chars)
                map[ch] = true;
        }

        boolean isValid(int ch) {
            return map[ch];
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