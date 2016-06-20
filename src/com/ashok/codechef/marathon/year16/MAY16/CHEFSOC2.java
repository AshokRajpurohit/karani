package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Big Soccer
 * https://www.codechef.com/MAY16/problems/CHEFSOC2
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFSOC2 {

    private static PrintWriter out;
    private static InputStream in;
    private static final int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CHEFSOC2 a = new CHEFSOC2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 13);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt(), s = in.readInt();

            long[] ar = process(n, s, in.readIntArray(m));
            println(sb, ar);
        }

        out.print(sb);
    }

    private static long[] process(int n, int s, int[] mar) {
        long[] orig = new long[n], copy = new long[n];
        orig[s - 1] = 1;

        for (int pass : mar) {
            pass(orig, copy, pass);
        }

        return orig;
    }

    private static void pass(long[] source, long[] to, int pass) {
        for (int i = 0; i < source.length; i++) {
            if (i + pass < source.length)
                to[i + pass] += source[i];

            if (i - pass >= 0)
                to[i - pass] += source[i];
        }

        for (int i = 0; i < source.length; i++)
            to[i] %= mod;

        copy(to, source);
        clean(to);
    }

    private static void clean(long[] ar) {
        for (int i = 0; i < ar.length; i++)
            ar[i] = 0;
    }

    private static void copy(long[] source, long[] to) {
        for (int i = 0; i < to.length; i++)
            to[i] = source[i];
    }

    private static void print(StringBuilder sb, long[] ar) {
        for (long e : ar)
            sb.append(e).append(' ');
    }

    private static void println(StringBuilder sb, long[] ar) {
        print(sb, ar);

        sb.append('\n');
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
    }
}
