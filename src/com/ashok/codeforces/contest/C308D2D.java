package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Ashok Rajpurohit
 * problem: Vanya and Triangles
 * Solution is incorrect.
 * http://codeforces.com/contest/552/problem/D
 */

public class C308D2D {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C308D2D a = new C308D2D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        System.out.println("lemao");
        int n = in.readInt();
        int[] x = new int[n];
        int[] y = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            y[i] = in.readInt();
        }

        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                Line line = new Line(x[i], y[i], x[j], y[j]);
                if (hm.containsKey(line.toString()))
                    hm.put(line.toString(), hm.get(line.toString()) + 1);
                else
                    hm.put(line.toString(), 2);
            }
        }

        Set set = hm.entrySet();

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            System.out.println(entry.getValue());
        }
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println(set);
        /*
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                double slope = getSlope(x[i], y[i], x[j], y[j]);
                for (int k = j + 1; k < n; k++) {
                    if (getSlope(x[i], y[i], x[k], y[k]) != slope)
                        count++;
                }
            }
        }
        out.println(count);
         */
    }

    private static int nc(int n) {
        if (n < 3)
            return 0;
        return n * (n - 1) * (n - 3) / 6;
    }

    private static double getSlope(int p, int q, int x, int y) {
        if (p == x)
            return Double.POSITIVE_INFINITY;
        return (0.0 + y - q) / (0.0 + x - p);
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

    class Line {
        public final double c;
        public final double slope;

        /**
         * creates a line connecting two points a and b.
         * if both the points are same, it will instantiate a new horizontal line
         * passing through these points.
         * @param a
         * @param b
         */
        public Line(int a, int b, int x, int y) {
            if (a == x) {
                slope = Double.POSITIVE_INFINITY;
                c = x;
                return;
            }

            slope = (y - b) / (x - a);
            c = y - slope * x;
        }

        public String toString() {
            return c + "" + slope;
        }

        /**
         * Compares this line with the specified Object for equality.
         * returns true if both the straight lines are equivalent.
         * @param line
         * @return
         */
        public final boolean equals(Object line) {
            if (this == line)
                return true;

            if (!(line instanceof Line))
                return false;

            Line L = (Line)line;
            return c == L.c && slope == L.slope;
        }
    }
}
