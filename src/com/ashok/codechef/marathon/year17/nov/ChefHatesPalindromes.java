/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.nov;

import com.ashok.lang.algorithms.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Chef Hates Palindromes
 * Link: https://www.codechef.com/NOV17/problems/CHEFHPAL
 * <p>
 * Chef's birthday is coming soon! His friend Fehc is going to send him a string
 * of length N as a gift. Knowing that Chef doesn't like palindromes, Fehc wants
 * the longest palindromic substring to be as short as possible. The string
 * should only contain the first A latin letters(e.g. let A=2, then this string
 * only contains 'a' and 'b'). Please help Fehc and find such a string. If
 * multiple solution exists, you can print any.
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefHatesPalindromes {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private final StringBuilder sb = new StringBuilder();
    private static final String[] STRINGS = new String[9];
    private static final char[]
            ar = new char[26],
            pattern = "aababb".toCharArray();

    static {
        for (char c = 'a'; c <= 'z'; c++)
            ar[c - 'a'] = c;

        STRINGS[1] = "1 a";
        STRINGS[2] = "1 ab";
        STRINGS[3] = "2 aab";
        STRINGS[4] = "2 aabb";
        STRINGS[5] = "3 aaabb";
        STRINGS[6] = "3 aaabbb";
        STRINGS[7] = "3 aaababb";
        STRINGS[8] = "3 aaababbb";
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        playPalindroms();
//        play();
        ChefHatesPalindromes object = new ChefHatesPalindromes();
        int t = in.readInt();
        while (t > 0) {
            t--;
            object.process(in.readInt(), in.readInt());
        }

        out.print(object.sb);
    }

    private static void playPalindroms() throws IOException {
        while (true) {
            out.println(Strings.largestPalindromeSubstring(in.read()).length());
            out.flush();
        }
    }

    private static void play() throws IOException {
        while (true) {
            out.println(bruteForce(in.readInt()));
            out.flush();
        }
    }

    private static String bruteForce(int len) {
        int palinLen = len;
        List<String> strings = generateStrings(len);
        String res = "a";

        for (String s : strings) {
            int palindromLen = Strings.largestPalindromeSubstring(s).length();
            if (palindromLen < palinLen) {
                res = s;
                palinLen = palindromLen;
            }
        }

        return res;
    }

    private void process(int n, int p) {
        if (p == 1) {
            sb.append(n).append(' ');
            while (n > 0) {
                n--;
                sb.append(ar[0]);
            }
        } else if (p > 2 || n == 2) {
            sb.append(1).append(' ');
            while (n >= p) {
                n -= p;
                sb.append(ar, 0, p);
            }

            sb.append(ar, 0, n);
        } else {
            process2(n);
        }

        sb.append('\n');
    }

    private void process2(int n) {
        if (n < STRINGS.length) {
            sb.append(STRINGS[n]);
        } else {
            sb.append(4).append(' ');
            while (n >= 6) {
                n -= 6;
                sb.append(pattern);
            }

            sb.append(pattern, 0, n);
        }
    }

    private static List<String> generateStrings(int len) {
        char[] permutation = new char[len];
        permutation[0] = 'a';
        List<String> list = new LinkedList<>();
        permutate(permutation, 1, list);
        return list;
    }

    private static void permutate(char[] ar, int index, List<String> list) {
        if (index == ar.length) {
            list.add(new String(ar));
            return;
        }

        ar[index] = 'a';
        permutate(ar, index + 1, list);

        ar[index] = 'b';
        permutate(ar, index + 1, list);
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