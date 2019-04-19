/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.visa;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class VisaVDP {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    public static long minArea(List<Integer> x, List<Integer> y, int k) {
        if (k == 1) return 4;
        Iterator<Integer> xIter = x.iterator(), yIter = y.iterator();
        List<Point> points = new LinkedList<>();
        while (xIter.hasNext()) {
            points.add(new Point(xIter.next(), yIter.next()));
        }

        Point[] xSorted = points.stream().toArray(t -> new Point[t]), ySorted = xSorted.clone();
        Arrays.sort(xSorted, (a, b) -> a.x - b.x);
        Arrays.sort(ySorted, (a, b) -> a.y - b.y);
        return -1;
    }

    private static long minArea(Point[] points, int count) {
        int len = points.length;
        if (count == len) {
            int maxX = Arrays.stream(points).mapToInt(p -> p.x).max().orElse(0);
            int maxY = Arrays.stream(points).mapToInt(p -> p.y).max().orElse(0);
            int minX = Arrays.stream(points).mapToInt(p -> p.x).min().orElse(0);
            int minY = Arrays.stream(points).mapToInt(p -> p.y).min().orElse(0);
            int size = Math.max(maxX + 1 - minX, maxY + 1 - minY);
            return 1L * size * size;
        }

        Point[] xSorted = points.clone();
        Point[] ySorted = points.clone();
        Arrays.sort(xSorted, (a, b) -> a.x - b.x);
        Arrays.sort(ySorted, (a, b) -> a.y - b.y);
        return Arrays.stream(points).mapToLong(p -> minArea(p, xSorted, ySorted, count)).min().orElse(4);
    }

    private static long minArea(Point leftBottom, Point[] xSorted, Point[] ySorted, int count) {
        Point squareLeftPoint = new Point(leftBottom.x - 1, leftBottom.y - 1);
        int len = xSorted.length;
        int maxSize = Math.max(xSorted[len - 1].x + 1 - squareLeftPoint.x, ySorted[len - 1].y + 1 - squareLeftPoint.y);
        int low = 0, high = maxSize;//, mid = (low + high) >>> 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int c = count(new Square(squareLeftPoint, new Point(squareLeftPoint.x + mid, squareLeftPoint.y + 1)), xSorted);
        }

        return maxSize;
    }

    private static int count(Square square, Point[] points) {
        return (int) Arrays.stream(points).filter(p -> square.contains(p)).count();
    }

    final static class Square {
        final Point leftBottom, topRight;
        final long area;

        Square(Point a, Point b) {
            leftBottom = a;
            topRight = b;
            area = 1L * (b.x - a.x) * (b.y - a.y);
        }

        boolean contains(Point point) {
            return leftBottom.x < point.x && leftBottom.y < point.y && topRight.x > point.x && topRight.y > point.y;
        }
    }

    final static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Problem
     *
     * @param n
     * @return
     */
    public static int drawingEdge(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        long nc2 = (1L * n * (n - 1)) >>> 1;
        return (int) pow(2, nc2, 1000000007);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    public static long pow(long a, long b, long mod) {
        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        if (res < 0) res += mod;
        return res;
    }

    /**
     * Problem
     *
     * @param prices
     * @return
     */
    static long maxInversions(List<Integer> prices) {
        int[] ar = prices.stream().mapToInt(t -> t).toArray();
        int[] prevLargeElementsCount = calculateLargerElementCount(ar);
        int[] nextSmallerElementCount = calculateSmallerElementCount(ar);
        return IntStream.range(0, ar.length).mapToLong(i -> 1L * prevLargeElementsCount[i] * nextSmallerElementCount[i]).sum();
    }

    private static int[] calculateLargerElementCount(int[] ar) {
        int len = ar.length;
        int[] counts = new int[len];
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++)
                if (ar[j] > ar[i]) counts[i]++;
        }

        return counts;
    }

    private static int[] calculateSmallerElementCount(int[] ar) {
        int len = ar.length;
        int[] counts = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++)
                if (ar[j] < ar[i]) counts[i]++;
        }

        return counts;
    }

    /**
     * Problem
     *
     * @param products
     * @param discounts
     * @return
     */
    public static int findLowestPrice(List<List<String>> products, List<List<String>> discounts) {
        Map<String, Discount> tagDiscountsMap = new HashMap<>();
        discounts.stream().forEach(t -> tagDiscountsMap.put(t.get(0), new Discount(Integer.valueOf(t.get(1)), Integer.valueOf(t.get(2)))));
        return products.stream().mapToInt(t -> {
            int price = Integer.valueOf(t.get(0));
            return t.stream().skip(1).map(v -> tagDiscountsMap.get(v)).filter(v -> v != null).mapToInt(d -> d.applyDiscount(price)).min().orElse(price);
        }).sum();
    }

    final static class Discount {
        final int type, value;

        Discount(int type, int value) {
            this.type = type;
            this.value = value;
        }

        private int applyDiscount(int price) {
            switch (type) {
                case 0:
                    return Math.min(price, value);
                case 1:
                    return price * (100 - value) / 100;
                default:
                    return Math.max(0, price - value);
            }
        }
    }
}
