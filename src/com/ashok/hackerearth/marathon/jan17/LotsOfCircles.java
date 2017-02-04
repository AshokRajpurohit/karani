/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Lots of Circles
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-17/approximate/lots-of-circles/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LotsOfCircles {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Circle[] circles;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        circles = new Circle[n];

        for (int i = 0; i < n; i++)
            circles[i] = new Circle(i, in.readInt(), in.readInt());

        process();
        sortById();

        StringBuilder sb = new StringBuilder(n << 2);

        for (Circle circle : circles)
            sb.append(circle.x).append(' ').append(circle.y).append('\n');

        out.print(sb);
    }

    private static void process() {
        sortByRadius();
        Circle centre = circles[0];
        centre.setCentre(0, 0);

        if (circles.length == 1)
            return;

        Point left = new Point(-centre.radius, 0), right = new Point(centre.radius, 0),
                up = new Point(0, centre.radius), down = new Point(0, -centre.radius);

        int safeDistance = centre.radius;

        for (int i = 1; i < circles.length; ) {
            Circle circle = circles[i];

            circle.setCentre(left.x - circle.radius, 0);
            left.set(circle.x - circle.radius, 0);

            i++;
            if (i < circles.length) {
                circle = circles[i];
                circle.setCentre(right.x + circle.radius, 0);
                right.set(circle.x + circle.radius, 0);
            }

            safeDistance = Math.max(-left.x, right.x);
            up.y = safeDistance;
            down.y = -safeDistance;

            i++;
            if (i < circles.length) {
                circle = circles[i];
                circle.setCentre(0, up.y + circle.radius);
                up.set(0, circle.y + circle.radius);
            }

            i++;
            if (i < circles.length) {
                circle = circles[i];
                circle.setCentre(0, down.y - circle.radius);
                down.set(0, circle.y - circle.radius);
            }

            safeDistance = Math.max(up.y, -down.y);
            left.x = -safeDistance;
            right.x = safeDistance;
        }

//        LinkedList<Circle> innerLoop = new LinkedList<>(), currentLoop = new LinkedList<>();
//        innerLoop.add(centre);
//        circles[1].setCentre(circles[0].radius, 0);
//
//        currentLoop.add(circles[1]);
//
//        for (int i = 2; i < circles.length; i++)
    }

    private static void sortById() {
        Circle[] copy = new Circle[circles.length];

        for (Circle circle : circles)
            copy[circle.id] = circle;

        for (Circle circle : copy)
            circles[circle.id] = circle;
    }

    private static void sortByWeight() {
        Arrays.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle o1, Circle o2) {
                if (o1.weight == o2.weight)
                    return o1.radius - o2.radius;

                return o2.weight - o1.weight;
            }
        });
    }

    private static void sortByRadius() {
        Arrays.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle o1, Circle o2) {
                if (o1.radius == o2.radius)
                    return o2.weight - o1.weight;

                return o1.radius - o2.radius;
            }
        });
    }

    private static void sortByRadiusWeight() {
        Arrays.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle o1, Circle o2) {
                return o1.radius * o1.weight - o2.radius * o2.weight;
            }
        });
    }

    private static double distance(int x, int y, int a, int b) {
        int dx = x - a, dy = y - b;

        return Math.sqrt(dx * dx + dy * dy);
    }

    final static class Circle {
        int x, y;
        final int id, radius, weight;

        Circle(int id, int r, int w) {
            this.id = id;
            radius = r;
            weight = w;
        }

        private void setCentre(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private boolean intersects(Circle circle) {
            return distance(x, y, circle.x, circle.y) < radius + circle.radius;
        }

        public String toString() {
            return x + ", " + y + ": " + radius + ", " + weight;
        }
    }

    final static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void set(int x, int y) {
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
