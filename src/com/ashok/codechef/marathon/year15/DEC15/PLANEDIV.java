package com.ashok.codechef.marathon.year15.DEC15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem: Plane Division
 * https://www.codechef.com/DEC15/problems/PLANEDIV
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class PLANEDIV {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        PLANEDIV a = new PLANEDIV();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt();
            Line[] lines = new Line[n];
            for (int i = 0; i < n; i++) {
                int a = in.readInt(), b = in.readInt(), c = in.readInt();
                int g = gcd(a, gcd(b, c));
                lines[i] = new Line(a / g, b / g, c / g);
            }
            Arrays.sort(lines);
            sb.append(process(lines, n)).append('\n');
        }
        out.print(sb);
    }

    private static int process(Line[] lines, int n) {
        int count = 1, max = 1;

        for (int i = 1; i < n; i++) {
            if (lines[i].parallel(lines[i - 1])) {
                if (!lines[i].equals(lines[i - 1]))
                    count++;
            } else {
                max = Math.max(max, count);
                count = 1;
            }
        }

        return Math.max(max, count);
    }

    private static int gcd(int a, int b, int c) {
        return gcd(a, gcd(b, c));
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    final static class Line implements Comparable<Line> {
        int a, b, c;

        Line(int x, int y, int z) {
            a = x;
            b = y;
            c = z;
        }

        public int compareTo(Line o) {
            if (this.a != o.a)
                return this.a - o.a;

            if (this.b != o.b)
                return this.b - o.b;

            return this.c - o.c;
        }

        public boolean parallel(Line line) {
            return (a == line.a) && (b == line.b);
        }

        public boolean equals(Line line) {
            return (a == line.a) && (b == line.b) && (c == line.c);
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
    }
}
