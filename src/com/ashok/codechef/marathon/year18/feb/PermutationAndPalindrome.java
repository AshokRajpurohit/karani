/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Permutation and Palindrome
 * Link: https://www.codechef.com/FEB18/problems/PERMPAL
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PermutationAndPalindrome {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int[] permutation = process(in.read().toCharArray());
            out.println(toSting(permutation));
        }
    }

    private static int[] process(char[] chars) {
        int ref = 'a', len = chars.length;
        LinkedList<Integer>[] indexMap = new LinkedList[26];
        for (int i = 0; i < 26; i++)
            indexMap[i] = new LinkedList<>();

        int naturalIndex = 1;
        for (char ch : chars)
            indexMap[ch - ref].add(naturalIndex++);

        int oddCount = 0;
        for (List<Integer> list : indexMap)
            if (odd(list.size()))
                oddCount++;

        if (oddCount != (len & 1)) {
            return new int[]{-1};
        }

        int left = 0, right = len - 1;
        int[] indices = new int[len];

        for (LinkedList<Integer> list : indexMap) {
            if (odd(list.size()))
                indices[len >>> 1] = list.removeFirst();

            while (!list.isEmpty()) {
                indices[left++] = list.removeFirst();
                indices[right--] = list.removeLast();
            }
        }

        return indices;
    }

    private static boolean odd(long n) {
        return (n & 1) == 1;
    }

    private static String toSting(int[] list) {
        StringBuilder sb = new StringBuilder(list.length << 2);
        for (int e : list)
            sb.append(e).append(' ');

        return sb.toString();
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