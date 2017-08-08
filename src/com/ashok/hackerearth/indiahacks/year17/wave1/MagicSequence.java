/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.indiahacks.year17.wave1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Magic Sequence
 * Link: https://www.hackerearth.com/challenge/competitive/indiahacks-2017-programming-wave-10-eliminator-1/algorithm/sequence-6352c81a/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MagicSequence {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char OPENING_BRACKET = '(', CLOSING_BRACKET = ')';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        out.println(process(in.read(n << 1).toCharArray()));
    }

    private static int process(char[] ar) {
        return Math.min(left(ar), right(ar));
    }

    private static int left(char[] ar) {
        int swaps = 0, len = ar.length;
        LinkedList<Integer> openingIndices = new LinkedList<>(), closingIndices = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            if (ar[i] == CLOSING_BRACKET) {
                if (openingIndices.isEmpty())
                    closingIndices.addLast(i);
                else
                    openingIndices.removeFirst()    ;
            } else {
                if (closingIndices.isEmpty())
                    openingIndices.addLast(i);
                else
                    swaps += i - closingIndices.removeLast();
            }
        }

        return swaps;
    }

    private static int right(char[] ar) {
        int swaps = 0, len = ar.length;
        LinkedList<Integer> openingIndices = new LinkedList<>(), closingIndices = new LinkedList<>();
        for (int i = len - 1; i >= 0; i--) {
            if (ar[i] == OPENING_BRACKET) {
                if (openingIndices.isEmpty())
                    closingIndices.addLast(i);
                else
                    openingIndices.removeLast();
            } else {
                if (closingIndices.isEmpty())
                    openingIndices.addLast(i);
                else
                    swaps += i - closingIndices.removeLast();
            }
        }

        return swaps;
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