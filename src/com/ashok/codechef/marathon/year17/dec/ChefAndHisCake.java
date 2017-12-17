/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef And his Cake
 * Link: https://www.codechef.com/DEC17/problems/GIT01
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndHisCake {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char RED = 'R', GREEN = 'G', GRED = RED ^ GREEN; // xor of green and red.
    private static final int GREEN_RED_COST = 3, RED_GREEN_COST = 5;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

            int n = in.readInt(), m = in.readInt();
            out.println(process(in.read(n, m)));
        }
    }

    private static int process(String[] cake) {
        return Math.min(process(cake, RED), process(cake, GREEN));
    }

    private static int process(String[] cake, char firstChar) {
        int res = 0, len = cake.length;
        for (int i = 0; i < len; i += 2)
            res += getCost(cake[i].toCharArray(), firstChar);

        firstChar ^= GRED;
        for (int i = 1; i < len; i += 2)
            res += getCost(cake[i].toCharArray(), firstChar);

        return res;
    }

    private static int getCost(char[] chars, char firstChar) {
        int len = chars.length, res = 0;
        for (int i = 0; i < len; i += 2)
            res += getCost(chars[i], firstChar);

        firstChar ^= GRED;
        for (int i = 1; i < len; i += 2)
            res += getCost(chars[i], firstChar);

        return res;
    }

    private static int getCost(char source, char target) {
        if (source == target)
            return 0;

        return target == RED ? GREEN_RED_COST : RED_GREEN_COST;
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

        public String[] read(int n, int len) throws IOException {
            String[] strings = new String[n];
            for (int i = 0; i < n; i++)
                strings[i] = read(len);

            return strings;
        }
    }
}