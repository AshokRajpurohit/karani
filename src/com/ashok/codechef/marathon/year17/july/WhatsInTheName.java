/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Whats in the Name
 * Link: https://www.codechef.com/JULY17/problems/NITIKA
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class WhatsInTheName {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static char[] toUpper = new char[256],
            toLower = new char[256];

    static {
        for (char i = 0; i < 256; i++) {
            toUpper[i] = i;
            toLower[i] = i;
        }

        for (char i = 'a', j = 'A'; i <= 'z'; i++, j++)
            toUpper[i] = j;

        for (char i = 'A', j = 'a'; i <= 'Z'; i++, j++)
            toLower[i] = j;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t);

        while (t > 0) {
            t--;

            String line = in.readLine();
            String[] strings = line.split(" ");
            process(strings);
            append(sb, strings).append('\n');
        }

        out.print(sb);
    }

    private static void process(String[] strings) {
        int len = strings.length;
        for (int i = 0; i < len - 1; i++)
            strings[i] = convert(strings[i]);

        strings[len - 1] = formatLastName(strings[len - 1]);
    }

    private static String convert(String s) {
        char[] chars = new char[2];
        chars[0] = toUpper[s.charAt(0)];
        chars[1] = '.';
        return String.valueOf(chars);
    }

    private static String formatLastName(String name) {
        char[] ar = name.toCharArray();
        for (int i = 1; i < ar.length; i++)
            ar[i] = toLower[ar[i]];

        ar[0] = toUpper[ar[0]];
        return String.valueOf(ar);
    }

    private static StringBuilder append(StringBuilder sb, String[] strings) {
        for (String e : strings)
            sb.append(e).append(' ');

        return sb;
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

        public String readLine() throws IOException {
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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