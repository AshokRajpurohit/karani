/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.august17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: String Matching
 * Link: https://www.hackerearth.com/challenge/competitive/august-circuits-17/algorithm/string-matching-google-3dc355a5/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class StringMatching {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int[] DIGIT_MAP = new int[256];

    static {
        for (int i = 0; i <= 9; i++)
            DIGIT_MAP[i] = i + '0';
    }


    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        String numberString = in.read();
        int n = in.readInt();
        int[] digits = in.readIntArray(n);

        out.println(process(numberString.toCharArray(), digits));
    }

    private static long process(char[] numChars, int[] digits) {
        CharBag bag = new CharBag(digits);
        int len = numChars.length;
        long total = 0, current = 0;
        for (int i = 0, j = 0; i < len && j < len; j++) {
            bag.putChar(numChars[j]);
            while (bag.containsAll()) {
                bag.removeChar(numChars[i]);
                i++;
            }

            current = i;
            total += current;
        }

        return total;
    }

    private static boolean[] toMap(int[] digits) {
        boolean[] map = new boolean[256];
        for (int digit : digits)
            map[DIGIT_MAP[digit]] = true;

        return map;
    }

    private static int count(boolean[] ar, boolean value) {
        int count = 0;

        for (boolean b : ar)
            if (value == b)
                count++;

        return count;
    }

    final static class CharBag {
        boolean[] map;
        final int size;
        final int[] countMap = new int[256];
        int count = 0;

        CharBag(int[] digits) {
            map = toMap(digits);
            size = digits.length;
        }

        void putChar(char ch) {
            if (map[ch]) {
                countMap[ch]++;

                if (countMap[ch] == 1)
                    count++;
            }
        }

        void removeChar(char ch) {
            if (map[ch]) {
                countMap[ch]--;

                if (countMap[ch] == 0)
                    count--;
            }
        }

        boolean containsAll() {
            return count == size;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
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