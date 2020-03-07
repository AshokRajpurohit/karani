/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.dec19;

import com.ashok.lang.dsa.RandomStrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Addition
 * Link: https://www.codechef.com/DEC19A/problems/BINADD
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Addition {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(bruteForce(in.read(), in.read()));
        }
    }

    private static void test() throws IOException {
        RandomStrings randomStrings = new RandomStrings();
        while (true) {
            String first = randomStrings.nextBinaryString(5), second = randomStrings.nextBinaryString(5);
            out.println(first + " " + second);
            out.flush();
            int expected = bruteForce(first, second), actual = process(first, second);
            out.println("result: " + (expected == actual));
            out.flush();
            if (expected != actual) {
                out.println("incorrect results for ");
                out.println(first);
                out.println(second);
                out.println("expected: " + expected + ", actual: " + actual);
                out.println("enter anything to continue");
                out.flush();
                in.read();
                actual = process(first, second);
            }
        }
    }

    private static int process(String a, String b) {
        if (isZero(b)) return 0;
        if (isZero(a)) return 1;

        int len = Math.max(a.length(), b.length());
        a = preAppend(a, len);
        b = preAppend(b, len);

        char[] ac = a.toCharArray(), bc = b.toCharArray();
        int i = ac.length - 1, j = bc.length - 1;
        int max01 = 0;
        while (i >= 0 && j >= 0) {
            boolean av = ac[i] == '1', bv = bc[j] == '1';
            i--;
            j--;
            if (!(av && bv)) continue;
            i++;
            j++;
            while (av && bv && (i > 0 && j > 0)) {
                av = ac[i] == '1';
                bv = bc[j] == '1';
                i--;
                j--;
            }

            if (i == 0) {
                max01 = Math.max(max01, 2);
            }

            i++;
            j++;

            int count = 1;
            while (i >= 0 && j >= 0 && (av ^ bv)) {
                count++;
                av = ac[i] == '1';
                bv = bc[j] == '1';
                i--;
                j--;
            }

            if (i < 0) count++;

            max01 = Math.max(max01, count);
        }

        return max01;
    }

    private static String preAppend(String str, int targetLength) {
        if (str.length() == targetLength) return str;
        char[] temp = new char[targetLength - str.length()];
        Arrays.fill(temp, '0');
        return String.valueOf(temp) + str;
    }

    private static boolean isZero(String binStr) {
        for (char ch : binStr.toCharArray()) {
            if (ch != '0') return false;
        }

        return true;
    }

    private static int bruteForce(String a, String b) {
        if (isZero(b)) return 0;
        if (isZero(a)) return 1;

        int len = Math.max(a.length(), b.length());
        a = preAppend(a, len);
        b = preAppend(b, len);

        boolean[] ac = toBoolArray(a), bc = toBoolArray(b);
        int count = 0;
        boolean cont = true;
        while (cont) {
            count++;
            cont = false;
            for (int i = 0; i < len; i++) {
                boolean first = ac[i] ^ bc[i], second = ac[i] && bc[i];
                ac[i] = first;
                bc[i] = second;
                cont = cont || bc[i];
            }

            leftShift(bc);
        }

        return count;
    }

    private static void leftShift(boolean[] ar) {
        for (int i = 1; i < ar.length; i++) {
            ar[i - 1] = ar[i];
        }
    }

    private static boolean[] toBoolArray(String str) {
        boolean[] ar = new boolean[str.length()];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = str.charAt(i) == '1';
        }
        return ar;
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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