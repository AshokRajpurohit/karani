package com.ashok.hackerearth.CodeMonk;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Guess the triangle
 */

public class ComputGeometryA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ComputGeometryA a = new ComputGeometryA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        double x1 = Double.parseDouble(in.read()), y1 =
            Double.parseDouble(in.read());
        double x2 = Double.parseDouble(in.read()), y2 =
            Double.parseDouble(in.read());
        double x3 = Double.parseDouble(in.read()), y3 =
            Double.parseDouble(in.read());

        double sumx = (x1 + x2 + x3), sumy = (y1 + y2 + y3);
        Pair[] ar = new Pair[3];
        ar[0] = new Pair(sumx - 2 * x1, sumy - 2 * y1);
        ar[1] = new Pair(sumx - 2 * x2, sumy - 2 * y2);
        ar[2] = new Pair(sumx - 2 * x3, sumy - 2 * y3);

        Arrays.sort(ar, new pointComparator());
        for (Pair e : ar)
            System.out.println(e.x + " " + e.y);
        for (int i = 0; i < 3; i++) {
            out.println(normalize(ar[i].x) + " " + normalize(ar[i].y));
        }
    }

    private static String normalize(double d) {
        StringBuilder sb = new StringBuilder(10);
        if (d < 0)
            sb.append('-');

        d = Math.abs(d);
        sb.append((int)d);
        sb.append(((d - (int)d) + "0000").substring(1, 6));
        return sb.toString();
    }

    final static class Pair {
        double x;
        double y;

        Pair(double a, double b) {
            x = a;
            y = b;
        }
    }

    final static class pointComparator implements Comparator<Pair> {

        public int compare(ComputGeometryA.Pair o1, ComputGeometryA.Pair o2) {
            if (o1.x < o2.x)
                return -1;

            if (o1.x > o2.x)
                return 1;

            if (o1.y < o2.y)
                return -1;

            return 1;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
