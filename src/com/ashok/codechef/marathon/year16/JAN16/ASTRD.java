package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Asteroids
 * https://www.codechef.com/JAN16/problems/ASTRD
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class ASTRD {

    private static PrintWriter out;
    private static InputStream in;
    private static double epsilon = 0.001, PI2 = Math.PI * 2;
    private static Point[] ar;
    private static int radius;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ASTRD a = new ASTRD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            radius = in.readInt();
            ar = new Point[n];

            for (int i = 0; i < n; i++)
                ar[i] = new Point(in.readInt(), in.readInt(), in.readInt());

            out.println(process());
        }
    }

    private static int process() {
        if (ar.length < 4)
            return 0;

        double x = 0, y = 0, z = 0;

        for (int i = 0; i < ar.length; i++) {
            PointS ps = x2theta(ar[i]);
            ps.theta = Math.PI - ps.theta;
            ps.phi += Math.PI;

            if (ps.phi > PI2)
                ps.phi -= PI2;

            Point px = theta2x(ps);
            x += px.x;
            y += px.y;
            z += px.z;
        }

        x /= ar.length;
        y /= ar.length;
        z /= ar.length;

        Point px = new Point(x, y, z);
        PointS ps = x2theta(px);
        ps.r = radius;
        px = theta2x(ps);

        x = px.x;
        y = px.y;
        z = px.z;

        Plan tplan = new Plan();
        tplan.a = x;
        tplan.b = y;
        tplan.c = z;
        tplan.d = x * x + y * y + z * z;

        int count = 0;
        for (int i = 0; i < ar.length; i++) {
            if (distance(tplan, ar[i]) >= epsilon)
                count++;
        }

        return count;
    }

    private static PointS x2theta(Point p) {
        PointS res = new PointS();
        res.r = Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
        res.theta = Math.acos(p.z / res.r);
        double phi = Math.atan2(p.y, p.x);

        if (phi < 0)
            phi = Math.PI * 2 - phi;

        res.phi = phi;
        return res;
    }

    private static Point theta2x(PointS p) {
        Point res = new Point();
        res.z = p.r * Math.cos(p.theta);
        double sin = Math.sin(p.theta);

        res.y = p.r * sin * Math.sin(p.phi);
        res.x = p.r * sin * Math.cos(p.phi);

        return res;
    }

    private static double distance(Plan pl, Point p) {
        double res = pl.a * p.x + pl.b * p.y + pl.c * p.z - pl.d;
        return res / Math.sqrt(pl.d);
    }

    final static class Plan {
        double a, b, c, d;
    }

    final static class PointS {
        double r, theta, phi;
    }

    final static class Point {
        double x, y, z;

        Point(double a, double b, double c) {
            x = a;
            y = b;
            z = c;
        }

        Point() {

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
