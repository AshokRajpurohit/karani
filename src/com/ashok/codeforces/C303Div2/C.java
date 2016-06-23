package com.ashok.codeforces.C303Div2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 *
 */

public class C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] x = new int[n];
        int[] h = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            h[i] = in.readInt();
        }

        out.println(solve(x, h));
    }

    private static int solve(int[] x, int[] h) {
        int count = 0;
        boolean[] tree = new boolean[x.length];
        int[] y = new int[x.length];
        boolean[] barf = new boolean[x.length];

        for (int i = 0; i < x.length; i++)
            y[i] = x[i];

        for (int i = 1; i < x.length - 2; ) {
            if (x[i + 1] - x[i] > h[i + 1] + h[i]) {
                tree[i] = true;
                tree[i + 1] = true;
                y[i] = x[i] + h[i];
                y[i + 1] = x[i + 1] - h[i + 1];
                i += 2;
            } else
                i++;
        }

        tree[0] = true;
        tree[x.length - 1] = true;
        y[0] = x[0] - h[0];
        y[x.length - 1] = x[x.length - 1] + h[x.length - 1];

        for (int i = 1; i < x.length; i++) {
            if (tree[i - 1] && (!tree[i])) {
                if (x[i] - h[i] >
                    (x[i - 1] > y[i - 1] ? x[i - 1] : y[i - 1])) {
                    tree[i] = true;
                    y[i] = x[i] - h[i];
                }
            }
        }

        for (int i = x.length - 2; i > 0; i--) {
            if (tree[i + 1] && (!tree[i])) {
                if (x[i] + h[i] <
                    (x[i + 1] > y[i + 1] ? y[i + 1] : x[i + 1])) {
                    tree[i] = true;
                    y[i] = x[i] + h[i];
                }
            }
        }

        for (int i = 1; i < x.length - 1; i++) {
            if (!tree[i]) {
                if ((x[i] - h[i] <=
                     (x[i - 1] > y[i - 1] ? x[i - 1] : y[i - 1]))) {
                    if (x[i] + h[i] >=
                        (x[i + 1] > y[i + 1] ? y[i + 1] : x[i + 1]))
                        barf[i] = true;
                }
            }
        }

        for (int i = 0; i < x.length; i++)
            if (tree[i])
                count++;

        int bar = 0;
        for (int i = 0; i < x.length; i++)
            if (barf[i])
                bar++;

        return x.length - bar;

        //        return count;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

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
