package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Lighthouses
 * https://www.codechef.com/SEPT15/problems/LIGHTHSE
 */

public class LIGHTHSE {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = fip;
        //        out = new PrintWriter(fop);

        LIGHTHSE a = new LIGHTHSE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++)
                points[i] = new Point(in.readInt(), in.readInt());

            process(points, sb);
        }
        out.print(sb);
    }

    private static void process(Point[] points, StringBuilder sb) {
        if (checkOne(points, sb))
            return;

        int left = 0, right = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].x < points[left].x)
                left = i;

            if (points[i].x > points[right].x)
                right = i;
        }

        sb.append("2\n");
        if (points[left].y > points[right].y) {
            sb.append(left + 1).append(" SE\n");
            sb.append(right + 1).append(" NW\n");
            return;
        }

        sb.append(right + 1).append(" SW\n");
        sb.append(left + 1).append(" NE\n");
    }

    private static boolean checkOne(Point[] points, StringBuilder sb) {
        int minX, minY, maxX, maxY;
        minX = points[0].x;
        maxX = minX;
        minY = points[0].y;
        maxY = minY;

        for (int i = 1; i < points.length; i++) {
            minX = Math.min(minX, points[i].x);
            maxX = Math.max(maxX, points[i].x);
            minY = Math.min(minY, points[i].y);
            maxY = Math.max(maxY, points[i].y);
        }

        int index = exists(points, minX, minY);
        if (index != -1) {
            sb.append("1\n").append(index + 1).append(" NE\n");
            return true;
        }

        index = exists(points, maxX, minY);
        if (index != -1) {
            sb.append("1\n").append(index + 1).append(" NW\n");
            return true;
        }

        index = exists(points, minX, maxY);
        if (index != -1) {
            sb.append("1\n").append(index + 1).append(" SE\n");
            return true;
        }

        index = exists(points, maxX, maxY);
        if (index != -1) {
            sb.append("1\n").append(index + 1).append(" SW\n");
            return true;
        }

        return false;
    }

    private static int exists(Point[] points, int x, int y) {
        for (int i = 0; i < points.length; i++)
            if (points[i].x == x && points[i].y == y)
                return i;

        return -1;
    }

    final static class Point {
        int x, y;

        Point(int a, int b) {
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
