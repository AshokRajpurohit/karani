package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Shootout!
 * Challenge: Altimetrik Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class AltimetricShootout {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AltimetricShootout a = new AltimetricShootout();
        a.solve();
        out.close();
    }

    private static boolean intersect(Point a, Point b, Point p, Point q) {
        if (equal(a, b) || equal(a, p) || equal(a, q) || equal(b, p) ||
                equal(b, q) || equal(p, q))
            return false;

        int o1 = orientation(a, b, p), o2 = orientation(a, b, q), o3 =
                orientation(p, q, a), o4 = orientation(p, q, b);

        if (o1 == 0)
            if (onSegment(a, b, p))
                return true;

        if (o2 == 0)
            if (onSegment(a, b, q))
                return true;

        if (o3 == 0)
            if (onSegment(p, q, a))
                return true;

        if (o4 == 0)
            if (onSegment(p, q, b))
                return true;

        //        if (o3 == 0 || o4 == 0) {
        //            if (o1 != o2)
        //                return true;
        //
        //            return false;
        //        }

        if (o1 != o2 && o3 != o4)
            return true;

        return false;
    }

    private static boolean onSegment(Point a, Point b, Point c) {
        long dx = c.x - a.x, dy = c.y - a.y;
        long dis = dx * dx + dy * dy;

        dx = c.x - b.x;
        dy = c.y - b.y;

        return dis == dx * dx + dy * dy;
    }

    private static boolean equal(Point a, Point b) {
        return a.x == b.x && a.y == b.y;
    }

    private static int orientation(Point a, Point b, Point c) {
        long v = (b.y - a.y) * (c.x - a.x) - (c.y - a.y) * (b.x - a.x);

        if (v > 0)
            return 1;
        else if (v < 0)
            return -1;

        return 0;
    }

    public static double getSlope(Point a, Point b) {
        if (a.x == b.x) {
            if (b.y > a.y)
                return Double.POSITIVE_INFINITY;

            return Double.NEGATIVE_INFINITY;
        }
        return (b.y - a.y) / (b.x - a.x);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "Yes\n", no = "No\n";
        while (t > 0) {
            t--;
            Point a = new Point(in.readInt(), in.readInt()), b =
                    new Point(in.readInt(), in.readInt()), c =
                    new Point(in.readInt(), in.readInt()), d =
                    new Point(in.readInt(), in.readInt());
            if (intersect(a, c, b, d))
                sb.append(yes);
            else
                sb.append(no);
        }

        out.print(sb);
    }

    final static class Point {
        long x, y;

        Point(long a, long b) {
            x = a;
            y = b;
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
