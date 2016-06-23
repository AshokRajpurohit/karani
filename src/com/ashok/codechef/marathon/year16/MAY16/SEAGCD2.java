package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.LinkedList;

/**
 * Problem: Sereja and GCD 2
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SEAGCD2 {

    private static PrintWriter out;
    private static InputStream in;
    private static final int mod = 1000000007;
    private static final boolean[][] map;
    private static final ListMapper[] group = new ListMapper[100];

    static {
        map = new boolean[100][100];

        for (int i = 0; i < 100; i++) {
            map[0][i] = true;
            map[i][0] = true;
        }

        for (int i = 1; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                boolean value = gcd(i + 1, j + 1) == 1;

                map[i][j] = value;
                map[j][i] = value;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEAGCD2 a = new SEAGCD2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.readInt(), in.readInt()));
        }
    }

    private static long process(int n, int m) {

        return mod;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
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
    }

    final static class ListMapper extends LinkedList<Integer> {

    }
}
