/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.dec20;

import com.ashok.lang.dsa.RandomStrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Problem Name: String Operations
 * Link: https://www.codechef.com/DEC20A/problems/STROPERS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class StringOperations {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char ZERO = '0', ONE = '1';

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt();
            String s = new RandomStrings().nextBinaryString(n);
            out.println(s);
            out.flush();
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            String s = in.read();
            out.println(process(s));
        }
    }

    private static int process(String binaryString) {
        Set<BinaryStringProps> set = new TreeSet<>();
        char[] chars = binaryString.toCharArray();
        int len = chars.length;
        for (int i = 0; i < len; i++) {
            char ch = chars[i];
            BinaryStringProps props = ch == ZERO ? ZERO_PROPS : ONE_PROPS;
            set.add(props);
            for (int j = i + 1; j < len; j++) {
                props = props.addChar(chars[j] == ZERO);
                set.add(props);
            }
        }
        return set.size();
    }

    private static BinaryStringProps getBinaryStringProps(String binaryString) {
        char[] chars = binaryString.toCharArray();
        int even0 = 0, odd0 = 0, ones = 0;
        boolean isEvenOnes = true;
        for (char ch : chars) {
            if (ch == ONE) {
                ones++;
                isEvenOnes = !isEvenOnes;
            } else { // ZERO
                if (isEvenOnes) even0++;
                else odd0++;
            }
        }

        return new BinaryStringProps(even0, odd0, ones);
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static final BinaryStringProps ZERO_PROPS = new BinaryStringProps(1, 0, 0);
    private static final BinaryStringProps ONE_PROPS = new BinaryStringProps(0, 0, 1);

    private static final class BinaryStringProps implements Comparable<BinaryStringProps> {
        // format of final string (smallest) is
        // even '0's followed by '1' followed by odd0 '0's and then (ones-1) '1's
        // 000100001111
        final int even0, odd0, ones;
        final int size;

        private BinaryStringProps(final int even0, final int odd0, final int ones) {
            this.even0 = even0;
            this.odd0 = odd0;
            this.ones = ones;
            size = even0 + odd0 + ones;
        }

        private BinaryStringProps addChar(boolean isZero) {
            if (!isZero) {
                return new BinaryStringProps(even0, odd0, ones + 1);
            }

            if (isEven(ones)) {
                return new BinaryStringProps(even0 + 1, odd0, ones);
            } else {
                return new BinaryStringProps(even0, odd0 + 1, ones);
            }
        }

        @Override
        public int compareTo(BinaryStringProps bsp) {
            int cmp = size - bsp.size;
            if (cmp == 0) cmp = bsp.even0 - even0;
            if (cmp == 0) cmp = bsp.odd0 - odd0;

            return cmp;
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