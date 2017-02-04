/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017.round1;

import com.ashok.lang.geometry.Point;
import com.ashok.lang.geometry.Rectangle;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name: Fighting the Zombies
 * Link: https://www.facebook.com/hackercup/problem/235709883547573/
 * <p>
 * For full implementation please see {@link github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FightingZombies {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final String CASE = "Case #";
    private static Set<Pair> cache;

    public static void main(String[] args) throws IOException {
//        play();
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\round1\\";
        in = new InputReader(path + "FightingZombies.in");
        out = new Output(path + "FightingZombies.out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int n = in.readInt(), r = in.readInt();
            Point[] points = new Point[n];

            for (int j = 0; j < n; j++)
                points[j] = new Point(in.readDouble(), in.readDouble());

            append(sb, i, process(points, r));
        }

        out.print(sb);
    }

    private static void play() throws IOException {
        int limit = 1000000000;
        while (true) {
            int n = in.readInt(), r = in.readInt(), t = in.readInt();
            limit = in.readInt();
            int[] x = Generators.generateRandomIntegerArray(n, limit), y = Generators.generateRandomIntegerArray(n, limit);
            Point[] points = new Point[n];

            for (int i = 0; i < n; i++)
                points[i] = new Point(x[i], y[i]);

            long time = System.currentTimeMillis();
            while (t > 0) {
                t--;
                out.println(process(points, r));
            }
            out.println("total time: " + (System.currentTimeMillis() - time));
            out.flush();
        }
    }

    private static int process(Point[] points, int side) {
        if (points.length < 3)
            return points.length;

        Arrays.sort(points);
        Rectangle plane = new Rectangle(points);
        double min = Math.min(plane.getLength(), plane.getWidth()), max = Math.max(plane.getLength(), plane.getWidth());

        if (side >= min && side * 2 >= max) // rectangle can be covered by two squares
            return points.length;

        return calculate(points, side);
    }

    private static int calculate(Point[] points, int side) {
        int max = 2;
        cache = new HashSet<>();

        for (int i = 0; i < points.length; i++) {
            Rectangle reference = new Rectangle(points[i], side, side);

            for (int j = i + 1; j < points.length; j++) {
                Rectangle movingSquare = new Rectangle(points[j], side, side);
                max = Math.max(max, calculate(reference, movingSquare, points));
            }
        }

        return max;
    }

    private static int calculate(Rectangle reference, Rectangle movableSquare, Point[] points) {
        if (cache.contains(new Pair(reference.left(), movableSquare.left())))
            return 0;

//        cache.add(new Pair(reference.left(), movableSquare.left()));
        double highestBoundary = points[points.length - 1].y;
        List<Rectangle> verticalSpaceRectangles = rectanglesInVerticalSpace(movableSquare, points);

        setVerticallyLowest(reference, points);
        int max = countPoints(reference, verticalSpaceRectangles, points);

        while (reference.top() < highestBoundary) {
            shiftUpward(reference, points);

            max = Math.max(max, countPoints(reference, verticalSpaceRectangles, points));
        }

        return max;
    }

    private static int countPoints(Rectangle reference, List<Rectangle> movingRectangles, Point[] points) {
        int max = 0;

        for (Rectangle var : movingRectangles)
            max = Math.max(max, countPoints(reference, var, points));

        return max;
    }

    private static List<Rectangle> rectanglesInVerticalSpace(Rectangle rectangle, Point[] points) {
        List<Rectangle> rectangles = new LinkedList<>();
        setVerticallyLowest(rectangle, points);
        double highestBoundary = points[points.length - 1].y;
        rectangles.add(rectangle);

        while (rectangle.top() < highestBoundary) {
            shiftUpward(rectangle, points);
            rectangles.add(rectangle);
        }

        return rectangles;
    }

    private static void shiftUpward(Rectangle rectangle, Point[] points) {
        double top = rectangle.top();

        int start = 0, end = points.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            if (points[mid].y > top)
                end = mid;
            else start = mid;

            mid = (start + end) >>> 1;
        }

        while (end < points.length) {
            if (rectangle.betweenVerticalLines(points[start]))
                break;

            end++;
        }

        end = Math.min(end, points.length - 1);
        double distance = points[end].y - top;
        rectangle.shift(0, distance);
    }

    private static int countPoints(Rectangle a, Rectangle b, Point[] points) {
        int count = 0;

        for (Point point : points) {
            if (a.contains(point) || b.contains(point))
                count++;
        }

        return count;
    }

    private static void setVerticallyLowest(Rectangle rectangle, Point[] points) {
        double distance = rectangle.bottom() - points[0].y;
        rectangle.shift(0, -distance);
    }

    private static int count(Rectangle rectangle, Point[] points) {
        int count = 0;

        for (Point point : points)
            if (rectangle.contains(point))
                count++;

        return count;
    }

    private static void append(StringBuilder sb, int test, int trips) {
        sb.append(CASE).append(test).append(": ").append(trips).append('\n');
    }

    final static class Pair {
        double first, second;

        Pair(double a, double b) {
            first = a;
            second = b;
        }

        public int hashCode() {
            return Double.hashCode(first + second);
        }

        public boolean equals(Object object) {
            if (this == object)
                return true;

            if (!(object instanceof Pair))
                return false;

            Pair pair = (Pair) object;

            return (first == pair.first && second == pair.second) || (first == pair.second && second == pair.first);
        }
    }
}
