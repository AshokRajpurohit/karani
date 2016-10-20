package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Fenwick Iterations
 * Link: https://www.codechef.com/OCT16/problems/FENWITER
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FENWITER {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char ONE = '1';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(Math.max(1, process(in.read(), in.read(), in.read(), in.readInt())));
            sb.append('\n');
        }

        out.print(sb);
    }

    private static long process(String L1, String L2, String L3, int n) {
        int count = process(L3);
        if (count != 0)
            return getOneCount(L1) + 1L * n * getOneCount(L2) + count;

        count = process(L2);
        n--;

        if (count == 0)
            return process(L1);

        return getOneCount(L1) + 1L * n * getOneCount(L2) + count;
    }

    private static int process(String s) {
        int index = s.length() - 1;

        while (index != -1 && s.charAt(index) == ONE)
            index--;

        if (index == -1)
            return 0;

        int count = 1;
        while (index != -1) {
            if (s.charAt(index) == ONE)
                count++;

            index--;
        }

        return count;
    }

    private static int getOneCount(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == ONE)
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
