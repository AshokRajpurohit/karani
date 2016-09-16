package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: The New Restaurant
 * Link: https://www.codechef.com/SEPT16/problems/CIRCLEQ
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CIRCLEQ {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final double epsilon = 0.000001;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        Circle[] circles = new Circle[n];

        for (int i = 0; i < n; i++)
            circles[i] = new Circle(in.readInt(), in.readInt(), in.readInt());

        while (q > 0) {
            Rectangle rectangle = new Rectangle(in.readInt(), in.readInt(),
                    in.readInt(), in.readInt());

            process(rectangle, circles);
            sb.append(rectangle.circleArea).append('\n');
        }

        out.print(sb);
    }

    private static void process(Rectangle rectangle, Circle[] circles) {
        for (Circle circle : circles)
            process(rectangle, circle);
    }

    private static void process(Rectangle rectangle, Circle circle) {
        if (rectangleInCircle(rectangle, circle)) {
            rectangle.circleArea = rectangle.maxArea;
            rectangle.covered = true;
            return;
        }

        if (circleInRectangle(rectangle, circle)) {
            rectangle.circleArea += circle.area;
            formChilds(rectangle);
        }

        if (pointInRectangle(rectangle, circle.centre)) {
            rectangle.getClass().getDeclaredFields();
        }
    }

    private static boolean pointInRectangle(Rectangle rectangle, Point point) {
        return rectangle.right.x >= point.x && rectangle.left.x <= point.x &&
                rectangle.right.y >= point.y && rectangle.left.y <= point.y;
    }

    private static void formChilds(Rectangle rectangle) {
        Point left = rectangle.left, right = rectangle.right;

        if (right.y - left.y == 1 && right.x - left.x == 1)
            return;

        int midx = (rectangle.left.x + rectangle.right.x) >>> 1,
                midy = (rectangle.left.y + rectangle.right.y) >>> 1;

        rectangle.q1 = new Rectangle(rectangle.left, new Point(midx, midy));

        if (midy + 1 <= rectangle.right.y)
            rectangle.q2 = new Rectangle(left.x, midy + 1, midx, right.y);

        if (midx + 1 <= right.x && midy + 1 <= right.y)
            rectangle.q3 = new Rectangle(midx + 1, midy + 1, right.x, right.y);

        if (midx + 1 <= right.x)
            rectangle.q4 = new Rectangle(midx + 1, left.y, right.x, midy);
    }

    private static boolean circleInRectangle(Rectangle rectangle, Circle circle) {
        Point centre = circle.centre, right = rectangle.right, left = rectangle.left;
        int r = circle.radius;

        return centre.x + r <= right.x && centre.y + r <= right.y &&
                centre.x - r >= left.x && centre.y - r >= left.y;
    }

    private static boolean rectangleInCircle(Rectangle rectangle, Circle circle) {
        return Math.max(distance(circle.centre, rectangle.left),
                distance(circle.centre, rectangle.right)) + epsilon >= circle.radius;
    }

    private static double distance(Point a, Point b) {
        int dx = b.x - a.x, dy = b.y - a.y;
        return Math.sqrt(1.0 * dx * dx + 1.0 * dy * dy);
    }

    final static class Rectangle {
        double circleArea = 0.0;
        long maxArea = 0;
        Point left, right;
        Rectangle q1, q2, q3, q4;
        boolean covered = false;

        Rectangle(int x1, int y1, int x2, int y2) {
            left = new Point(x1, y1);
            right = new Point(x2, y2);
            maxArea = 1L * (x2 - x1) * (y2 - y1);
        }

        Rectangle(Point a, Point b) {
            left = a;
            right = b;
            maxArea = 1L * (b.x - a.x) * (b.y - a.y);
        }
    }

    final static class Circle {
        Point centre;
        int radius;
        double area;

        Circle(int x, int y, int r) {
            centre = new Point(x, y);
            this.radius = r;
            area = Math.PI * r * r;
        }
    }

    final static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
