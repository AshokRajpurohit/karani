/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.nov;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Periodic Palindrome Construction
 * Link: https://www.codechef.com/NOV17/problems/PERPALIN
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PeriodicPalindromeConstruction {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String IMPOSSIBLE = "impossible";
    private static final int XOR = 'a' ^ 'b';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.readInt(), in.readInt()));
        }
    }

    private static String process(int n, int p) {
        if (!possible(n, p))
            return IMPOSSIBLE;

        char[] ar = new char[n];
        fill(ar, p);
        return new String(ar);
    }

    private static boolean possible(int n, int p) {
        return n > 2 && p > 1 && !(p == 2 && isEven(n));
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static void fill(char[] ar, int p) {
        int len = ar.length;
        int left = (len - 1) >>> 1, right = len >>> 1; // left might be equal to right.
        char ch = 'a';
        ar[left] = ar[right] = ch;
        int count = right - left + 1; // 1 or 2.

        while (count < p) {
            count++;
            ch ^= XOR; // switch characters.
            ar[--left] = ch;

            if (p == count)
                break;

            count++;
            ar[++right] = ch;
        }

        int index = right + 1;
        while (index < len) {
            copy(ar, index, left, right);
            index += p;
        }

        index = left - p;
        while (index > -p) {
            copy(ar, index, left, right);
            index -= p;
        }
    }

    private static void copy(char[] ar, int index, int from, int to) {
        if (index < 0) {
            copy(ar, 0, from - index, to);
            return;
        }
        int len = ar.length;
        for (int i = from; i <= to && index < len; i++)
            ar[index++] = ar[i];
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