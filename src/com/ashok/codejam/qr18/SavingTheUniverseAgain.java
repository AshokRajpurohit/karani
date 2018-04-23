/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.qr18;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Saving The Universe Again
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SavingTheUniverseAgain {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char SHOOT = 'S', CHARGE = 'C';
    private static final String IMPOSSIBLE = "IMPOSSIBLE", CASE = "CASE #";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            int res = process(in.readInt(), in.read().toCharArray());
            out.println(CASE + i + ": " + (res == -1 ? IMPOSSIBLE : res));
        }
    }

    private static int process(int d, char[] chars) {
        if (!possible(d, chars))
            return -1;

        int swaps = 0;
        while (damage(chars) > d) {
            swaps++;
            makeSwap(chars);
        }

        return swaps;
    }

    private static void makeSwap(char[] chars) {
        int len = chars.length - 1;
        for (int i = chars.length - 2; i >= 0; i--) {
            if (chars[i] != chars[i + 1] && chars[i] != SHOOT) {
                swap(chars, i, i + 1);
                return;
            }
        }
    }

    private static void swap(char[] chars, int a, int b) {
        char temp = chars[a];
        chars[a] = chars[b];
        chars[b] = temp;
    }

    private static long damage(char[] chars) {
        long damage = 0, r = 1;
        for (char ch : chars) {
            if (ch == SHOOT) {
                damage += r;
            } else
                r <<= 1;
        }

        return damage;
    }

    private static boolean possible(int d, char[] chars) {
        return count(chars, SHOOT) <= d;
    }

    private static int count(char[] ar, char c) {
        int count = 0;
        for (char ch : ar)
            if (ch == c)
                count++;

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