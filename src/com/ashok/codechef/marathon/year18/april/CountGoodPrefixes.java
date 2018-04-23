/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.april;

import com.ashok.lang.dsa.RandomStrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Count Good Prefixes
 * Link: https://www.codechef.com/APRIL18A/problems/GOODPREF
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CountGoodPrefixes {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.read().toCharArray(), in.readInt()));
        }
    }

    private static void play() throws IOException {
        while (true) {
            out.println("Enter new numbers: ");
            out.flush();
            int n = in.readInt(), k = in.readInt();
            RandomStrings strings = new RandomStrings();
            while (true) {
                int len = strings.nextInt(n) + 1, num = strings.nextInt(k) + 1;
                char[] ar = strings.nextBinaryString(len).toCharArray();
                for (int i = 0; i < len; i++)
                    ar[i] = (char) (ar[i] - '0' + 'a');

                long actual = process(ar, num), expected = bruteForce(ar, num);
                if (actual == expected) continue;
                out.println(new String(ar) + " " + num);
                out.println("Actual: " + actual + ", Expected: " + expected);
//                process(ar, num);
//                bruteForce(ar, num);
                out.flush();
                break;
            }
        }
    }

    private static long process(char[] chars, int n) {
        long res = 0;
        int delta = 0, cycleDelta = count(chars, 'a') * 2 - chars.length; // acount - bcount.
        for (char ch : chars) {
            if (ch == 'a')
                delta++;
            else
                delta--;

            res += countPrefixes(delta, cycleDelta, n);
        }

        return res;
    }

    private static long bruteForce(char[] chars, int n) {
        char[] ar = concatenate(chars, n);
        int res = 0, delta = 0;
        for (char ch : ar) {
            if (ch == 'a')
                delta++;
            else
                delta--;

            if (delta > 0)
                res++;
        }

        return res;
    }

    private static char[] concatenate(char[] chars, int n) {
        if (n == 1)
            return chars;

        int len = chars.length, index = 0;
        char[] ar = new char[len * n];
        for (int i = 0; i < n; i++) {
            for (char ch : chars)
                ar[index++] = ch;
        }

        return ar;
    }

    private static int countPrefixes(int delta, int cycleDelta, int n) {
        if (delta > 0 && cycleDelta >= 0)
            return n;

        if (delta <= 0 && cycleDelta <= 0)
            return 0;

        if (delta == 0 && cycleDelta > 0)
            return n - 1;

        if (delta > 0)
            return Math.min(n, -(delta - cycleDelta - 1) / cycleDelta);

        return Math.max(0, n - 1 + delta / cycleDelta);
    }

    private static int count(char[] ar, char val) {
        int count = 0;
        for (char ch : ar)
            if (ch == val) count++;

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