/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankit;

import com.ashok.lang.dsa.QuickSort;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LSE {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            play();
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            int[] x = in.readIntArray(in.readInt());
            int[] y = in.readIntArray(in.readInt());
            out.println(closestSquaredDistance(x, y));
            out.flush();
        }
    }

    private static void play() throws IOException {
        while (true) {
            out.println("Enter size");
            out.flush();
            int n = in.readInt();
            while (true) {
                int[] x = Generators.generateRandomIntegerArray(n, 1000000000);
                int[] y = Generators.generateRandomIntegerArray(n, 1000000000);
                Point[] points = IntStream.range(0, n).mapToObj(i -> new Point(x[i], y[i])).toArray(t -> new Point[t]);
                Arrays.sort(points, (a, b) -> a.x - b.x);
                try {
                    long time = System.currentTimeMillis();
                    long actual = concurrentCalculation(points);
                    out.println("optimal solution time for " + n + " points: " + (System.currentTimeMillis() - time));
                    time = System.currentTimeMillis();
                    long expected = closestSquareDistance(points);
                    out.println("bruteforce time for " + n + " points: " + (System.currentTimeMillis() - time));
                    out.println("matching the results: " + (actual == expected));
                    /*if (actual != expected) {
                        out.println("Not matching, actual: " + actual + ", expected: " + expected);
                        out.println(points);
                        out.flush();
                        concurrentCalculation(points);
                        break;
                    }*/
                } catch (Throwable e) {
                    out.println("Error " + e.getMessage());
                    e.printStackTrace(out);
                    out.println(points);
                    out.flush();
                    concurrentCalculation(points);
                    break;
                }
                out.flush();
            }
        }
    }

    private static long closestSquaredDistance(int[] x, int[] y) {
        int len = x.length;
        Point[] points = IntStream.range(0, len).mapToObj(i -> new Point(x[i], y[i])).toArray(t -> new Point[t]);
        Arrays.sort(points, (a, b) -> a.x - b.x);
        return closestSquareDistance(points);
    }

    private static long square(long n) {
        return n * n;
    }

    private static long concurrentCalculation(Point[] points) {
        ClosestDistanceTask task = new ClosestDistanceTask(points);
        return task.compute();
    }

    private static long closestSquareDistance(Point[] points) {
//        out.println("closestSquareDistance: " + points.length);
//        out.flush();
        if (points.length < 30) return bruteForce(points);
        PointSplit pointSplits = split(points);
        if (pointSplits.left.length == points.length || pointSplits.right.length == points.length)
            return bruteForce(points);
        long distance = Math.min(closestSquareDistance(pointSplits.left), closestSquareDistance(pointSplits.right));
        int sqrtDistance = (int) Math.sqrt(distance);
        Predicate<Point> predicate = p -> Math.abs((pointSplits.horizontalPartition ? p.y : p.x) - pointSplits.partitionLine) <= sqrtDistance;
        Comparator<Point> comparator = (a, b) -> pointSplits.horizontalPartition ? a.x - b.x : a.y - b.y;
        Point[] leftCloseStrip = Arrays.stream(pointSplits.left).filter(predicate).sorted(comparator).toArray(t -> new Point[t]);
        Point[] rightCloseStrip = Arrays.stream(pointSplits.right).filter(predicate).sorted(comparator).toArray(t -> new Point[t]);

        if (leftCloseStrip.length * rightCloseStrip.length <= 100)
            return Math.min(distance, closestSquareDistance(pointSplits.left, pointSplits.right));

        if (!pointSplits.horizontalPartition) {
            leftCloseStrip = Arrays.stream(leftCloseStrip).map(p -> p.transpose()).toArray(t -> new Point[t]);
            rightCloseStrip = Arrays.stream(rightCloseStrip).map(p -> p.transpose()).toArray(t -> new Point[t]);
        }
        int ref = 0;
        for (Point left : leftCloseStrip) {
            long lineDistanceSquare = Math.abs(left.y - pointSplits.partitionLine);
            lineDistanceSquare = lineDistanceSquare * lineDistanceSquare;
            if (lineDistanceSquare >= distance) continue;
            long squareDiffPending = distance - lineDistanceSquare;
            while (ref < rightCloseStrip.length && square(rightCloseStrip[ref].y - pointSplits.partitionLine) >= squareDiffPending)
                ref++;

            for (int j = ref; j < rightCloseStrip.length && square(rightCloseStrip[j].y - left.y) < squareDiffPending; j++) {
                long temp = distance(left, rightCloseStrip[j]);
                if (temp < distance) {
                    distance = temp;
                    squareDiffPending = distance - lineDistanceSquare;
                }
            }
        }

        return distance;
    }

    private static long closestSquareDistance(Point[] left, Point[] right) {
        return Arrays.stream(left)
                .mapToLong(p ->
                        Arrays.stream(right)
                                .mapToLong(q -> distance(p, q))
                                .min().getAsLong())
                .min().getAsLong();
    }

    private static Point[] collectCloseVerticalPoints(Point[] points, int x, long distance) {
        return Arrays.stream(points).filter(p -> 1L * (p.x - x) * (p.x - x) < distance).sorted((a, b) -> a.y - b.y).toArray(t -> new Point[t]);
    }

    private static long bruteForce(Point[] points) {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++)
                min = Math.min(min, distance(points[i], points[j]));
        }

        return min;
    }

    private static PointSplit split(Point[] points) {
        int len = points.length;
        int mid = len >>> 1;
        int midX = median(Arrays.stream(points).mapToInt(p -> p.x).toArray()),
                midY = median(Arrays.stream(points).mapToInt(p -> p.y).toArray());
        Point[] leftX = Arrays.stream(points).filter(p -> p.x <= midX).toArray(t -> new Point[t]);
        Point[] rightX = Arrays.stream(points).filter(p -> p.x > midX).toArray(t -> new Point[t]);
        Point[] leftY = Arrays.stream(points).filter(p -> p.y <= midY).toArray(t -> new Point[t]);
        Point[] rightY = Arrays.stream(points).filter(p -> p.y > midY).toArray(t -> new Point[t]);
        if (Math.abs(leftX.length - rightX.length) > Math.abs(leftY.length - rightY.length)) {
            return new PointSplit(leftY, rightY, midY, true);
        }
        return new PointSplit(leftX, rightX, midX, false);
    }

    private static int median(int[] ar) {
        return QuickSort.kthMaxElement(ar, ar.length >>> 1);
    }

    private static long distance(Point a, Point b) {
        long dx = a.x - b.x, dy = a.y - b.y;
        return dx * dx + dy * dy;
    }

    final static class PointSplit {
        final Point[] left, right;
        final boolean horizontalPartition;
        final int partitionLine;

        PointSplit(Point[] left, Point[] right, int partitionLine, boolean horizontalPartition) {
            this.left = left;
            this.right = right;
            this.partitionLine = partitionLine;
            this.horizontalPartition = horizontalPartition;
        }
    }

    final static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point transpose() {
            return new Point(y, x);
        }

        public Point clone() {
            return this;
        }

        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

    final static class ClosestDistanceTask extends RecursiveTask<Long> {
        Point[] points;

        ClosestDistanceTask(Point[] points) {
            this.points = points;
        }

        @Override
        protected Long compute() {
            if (points.length < 30) return bruteForce(points);
            PointSplit pointSplits = split(points);
            if (pointSplits.left.length == points.length || pointSplits.right.length == points.length)
                return bruteForce(points);

            ClosestDistanceTask leftTask = new ClosestDistanceTask(pointSplits.left);
            ClosestDistanceTask rightTask = new ClosestDistanceTask(pointSplits.right);

            leftTask.fork();
            long distance = Math.min(rightTask.compute(), leftTask.join());
            int sqrtDistance = (int) Math.sqrt(distance);
            Predicate<Point> predicate = p -> Math.abs((pointSplits.horizontalPartition ? p.y : p.x) - pointSplits.partitionLine) <= sqrtDistance;
            Comparator<Point> comparator = (a, b) -> pointSplits.horizontalPartition ? a.x - b.x : a.y - b.y;
            Point[] leftCloseStrip = Arrays.stream(pointSplits.left).filter(predicate).sorted(comparator).toArray(t -> new Point[t]);
            Point[] rightCloseStrip = Arrays.stream(pointSplits.right).filter(predicate).sorted(comparator).toArray(t -> new Point[t]);

            if (leftCloseStrip.length * rightCloseStrip.length <= 100)
                return Math.min(distance, closestSquareDistance(pointSplits.left, pointSplits.right));

            if (!pointSplits.horizontalPartition) {
                leftCloseStrip = Arrays.stream(leftCloseStrip).map(p -> p.transpose()).toArray(t -> new Point[t]);
                rightCloseStrip = Arrays.stream(rightCloseStrip).map(p -> p.transpose()).toArray(t -> new Point[t]);
            }
            int ref = 0;
            for (Point left : leftCloseStrip) {
                long lineDistanceSquare = Math.abs(left.y - pointSplits.partitionLine);
                lineDistanceSquare = lineDistanceSquare * lineDistanceSquare;
                if (lineDistanceSquare >= distance) continue;
                long squareDiffPending = distance - lineDistanceSquare;
                while (ref < rightCloseStrip.length && square(rightCloseStrip[ref].y - pointSplits.partitionLine) >= squareDiffPending)
                    ref++;

                for (int j = ref; j < rightCloseStrip.length && square(rightCloseStrip[j].y - left.y) < squareDiffPending; j++) {
                    long temp = distance(left, rightCloseStrip[j]);
                    if (temp < distance) {
                        distance = temp;
                        squareDiffPending = distance - lineDistanceSquare;
                    }
                }
            }

            return distance;
        }
    }

    private static void finalPrice(int[] ar) {
        int[] nextNonHigher = nextNonHigher(ar);
        long sum = 0;
        for (int i = 0; i < ar.length; i++) {
            int next = nextNonHigher[i];
            int discount = next != ar.length ? ar[next] : 0;
            sum += ar[i] - discount;
        }

        System.out.println(sum);
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, ar.length).filter(i -> nextNonHigher[i] == ar.length).forEach(i -> sb.append(i).append(' '));
        System.out.println(sb);
    }

    public static int[] nextNonHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] > ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }
}
