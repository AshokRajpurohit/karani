package com.ashok.codejam.R1C2016;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem:
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class A {

    private static PrintWriter out;
    private static InputStream in;
    private static String format = "Case #";
    private static char[] map = new char[26];

    static {
        for (int i = 0; i < 26; i++)
            map[i] = (char)(i + 'A');
    }

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String path =
            "D:\\GitHub\\Competetions\\CodeJam\\Code\\src\\R1c2016\\";
        String input = "A-large.in", output = "A-large.out";
        FileInputStream fip = new FileInputStream(path + input);
        FileOutputStream fop = new FileOutputStream(path + output);
        in = fip;
        out = new PrintWriter(fop);
        A a = new A();
        try {
            a.solve();
        } catch (IOException ioe) {
            // TODO: Add catch code
            out.close();
        }
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= t; i++) {
            sb.append(format).append(i).append(": ");
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            sb.append(process(n, ar)).append('\n');
        }

        out.print(sb);
    }

    private static String process(int n, int[] ar) {
        Pair[] pairs = pairs(ar);
        Arrays.sort(pairs);

        StringBuilder sb = new StringBuilder(ar.length);
        for (int i = 1; i < ar.length; i++) {
            while (pairs[i - 1].count != 0) {
                sb.append(map[pairs[i -
                          1].index]).append(map[pairs[i].index]).append(' ');
                pairs[i - 1].count--;
                pairs[i].count--;
            }
        }

        while (pairs[n - 1].count > 1) {
            sb.append(map[pairs[n - 1].index]).append(map[pairs[n -
                                                      1].index]).append(' ');

            pairs[n - 1].count -= 2;
        }

        if (pairs[n - 1].count == 1)
            sb.append(map[pairs[n - 1].index]).append(' ');

        sb.deleteCharAt(sb.length() - 1);
        return sb.reverse().toString();
    }

    private static Pair[] pairs(int[] ar) {
        Pair[] res = new Pair[ar.length];

        for (int i = 0; i < ar.length; i++)
            res[i] = new Pair(ar[i], i);

        return res;
    }

    final static class Pair implements Comparable<Pair> {
        int count, index;

        Pair(int c, int i) {
            count = c;
            index = i;
        }

        public int compareTo(Pair o) {
            return this.count - o.count;
        }
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
