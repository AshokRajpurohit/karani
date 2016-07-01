package com.ashok.edrepublic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Number of Right Triangles
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class RightTriangles {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        RightTriangles a = new RightTriangles();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int[] x = parseIntArray(in.read());
        int[] y = parseIntArray(in.read());
        out.println(numberOfRightTriangle(x, y));
        //        out.println(numberOfSamakonTribhuja(x, y));
    }

    private static int numberOfSamakonTribhuja(int[] x, int[] y) {
        int count = 0;
        for (int i = 0; i < x.length - 2; i++) {
            for (int j = i + 1; j < x.length - 1; j++) {
                double slope = getSlope(x[i], y[i], x[j], y[j]);
                for (int k = 0; k < x.length; k++) {
                    if (slope * getSlope(x[i], y[i], x[k], y[k]) == -1)
                        count++;
                }
            }
        }

        return count;
    }

    private static double getSlope(int x1, int y1, int x2, int y2) {
        if (x1 == x2)
            return Double.POSITIVE_INFINITY;

        return (0.0 + y2 - y1) / (0.0 + x2 - x1);
    }

    private static int[] parseIntArray(String s) {
        int count = 1;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == ',')
                count++;

        int[] ar = new int[count];
        for (int i = 0, j = 0; i < count; i++) {
            int temp = 0;
            int signum = s.charAt(j) == '-' ? -1 : 1;
            if (s.charAt(j) == '-')
                j++;

            while (j < s.length() && s.charAt(j) != ',') {
                temp = (temp << 3) + (temp << 1) + s.charAt(j) - '0';
                j++;
            }
            ar[i] = temp * signum;
            j++;
        }

        return ar;
    }

    /**
     * let's say the sides of a triangle are a, b and c in increasing order.
     * the sufficient condition for a triangle to be right angled is
     * a^2 + b^2 = c^2
     *
     * @param x
     * @param y
     * @return
     */
    private static int numberOfRightTriangle(int[] x, int[] y) {
        int count = 0;
        for (int i = 0; i < x.length - 2; i++) {
            for (int j = i + 1; j < x.length - 1; j++) {
                long lenOne = lenSquare(x[i], y[i], x[j], y[j]);
                for (int k = j + 1; k < x.length; k++) {
                    long lenTwo = lenSquare(x[i], y[i], x[k], y[k]);
                    long lenThre = lenSquare(x[j], y[j], x[k], y[k]);
                    long max = Math.max(lenThre, lenTwo);
                    max = Math.max(max, lenOne);
                    max = max << 1;
                    if (max == lenOne + lenTwo + lenThre) {
                        max = max >>> 2;
                        if (lenThre == max || lenTwo == max)
                            count++;
                    }
                    //                    if (lenOne == lenTwo + lenThre ||
                    //                        lenOne == Math.abs(lenTwo - lenThre)) {
                    //                        count++;
                    //                    }
                }
            }
        }
        return count;
    }

    private static long lenSquare(int x1, int y1, int x2, int y2) {
        long dis = 1L * (x1 - x2) * (x1 - x2) + 1L * (y1 - y2) * (y1 - y2);
        if (dis < 0)
            System.out.println("le kamine");
        return dis;
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
