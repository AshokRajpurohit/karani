/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.amazon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Name: Amazon hiring test on AmCat
 * Link: email
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Amazon {
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
        int capacity = in.readInt(), n = in.readInt(), m = in.readInt();
        List<List<Integer>> forg = new LinkedList<>(), back = new LinkedList<>();
        for (int i = 0; i < n; i++) forg.add(toList(in.readIntArray(2)));
        for (int i = 0; i < m; i++) back.add(toList(in.readIntArray(2)));
        out.println(optimalUtilization(capacity, forg, back));
    }

    private static List<Integer> toList(int[] ar) {
        List<Integer> list = new LinkedList<>();
        for (int e : ar) list.add(e);
        return list;
    }

    int removeObstacle(int numRows, int numColumns, List<List<Integer>> lot) {
        int[][] grid = lot.stream().map((row) -> row.stream().mapToInt((n) -> n).toArray()).toArray((t) -> new int[t][]);
        return new Grid(grid).removeObstacle();
    }

    final static class Grid {
        Point[][] points;
        final static Point OUT = new Point(-1, -1);
        final static int OBSTACLE = 9, FLAT = 1, TRENCH = 0;
        final int rows, columns;

        Grid(int[][] grid) {
            rows = grid.length;
            columns = grid[0].length;
            points = new Point[rows][columns];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++) {
                    points[i][j] = new Point(i, j);
                    points[i][j].value = grid[i][j];
                }
        }

        private Point find(int value) {
            for (Point[] ps : points) {
                for (Point point : ps) if (point.value == value) return point;
            }

            throw new RuntimeException("No Obstacle found");
        }

        int removeObstacle() {
            Point obstacle = find(OBSTACLE);
            Point start = points[0][0];
            Point mark = new Point(-2, -2);
            int level = 1;
            LinkedList<Point> queue = new LinkedList<>();
            queue.add(start);
            queue.add(mark);
            while (queue.size() > 1 && level < obstacle.distance) {
                Point point = queue.removeFirst();
                if (point == mark) {
                    queue.addLast(point);
                    level++;
                    continue;
                }

                if (point == obstacle) {
                    return level;
                } else if (point.value == FLAT) {
                    queue.addLast(left(point));
                    queue.addLast(right(point));
                    queue.addLast(up(point));
                    queue.addLast(down(point));
                }

                point.value = TRENCH;
            }

            return obstacle.value;
        }

        int distance() {
            Point obstacle = find(OBSTACLE);
            Point start = points[0][0];
            Point mark = new Point(-2, -2);
            int level = 1;
            LinkedList<Point> queue = new LinkedList<>();
            queue.add(start);
            queue.add(mark);
            while (queue.size() > 1 && level < obstacle.distance) {
                Point point = queue.removeFirst();
                if (point == mark) {
                    queue.addLast(point);
                    level++;
                    continue;
                }

                if (point == obstacle) {
                    obstacle.distance = Math.min(obstacle.distance, level);
                } else if (point.value == FLAT) {
                    queue.addLast(left(point));
                    queue.addLast(right(point));
                    queue.addLast(up(point));
                    queue.addLast(down(point));
                }
            }

            return obstacle.value;
        }

        Point get(int r, int c) {
            return r < 0 || r >= rows || c < 0 || c >= columns ? OUT : points[r][c];
        }

        Point left(Point p) {
            return get(p.x, p.y - 1);
        }

        Point right(Point p) {
            return get(p.x, p.y + 1);
        }

        Point up(Point p) {
            return get(p.x - 1, p.y);
        }

        Point down(Point p) {
            return get(p.x + 1, p.y);
        }
    }

    private static class Point {
        final int x, y;
        private int value, distance = Integer.MAX_VALUE;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    List<List<Integer>> nearestVegetarianRestaurant(int totalRestaurants,
                                                    List<List<Integer>> allLocations,
                                                    int numRestaurants) {
        Location[] locations = allLocations.stream().map(a -> new Location(a)).toArray((t) -> new Location[t]);
        Arrays.sort(locations, (a, b) -> Long.compare(a.distanceSquare, b.distanceSquare));
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < numRestaurants; i++) result.add(locations[i].list);
        return result;
    }

    List<List<Integer>> ClosestXdestinations(int numDestinations,
                                             List<List<Integer>> allLocations,
                                             int numDeliveries) {
        return allLocations.stream()
                .map(a -> new Location(a))
                .sorted((a, b) -> Long.compare(a.distanceSquare, b.distanceSquare))
                .limit(numDeliveries).map(a -> a.list)
                .collect(Collectors.toList());
    }

    static List<List<Integer>> optimalUtilization(
            int deviceCapacity,
            List<List<Integer>> foregroundAppList,
            List<List<Integer>> backgroundAppList) {
        App[] forgroundApps = toAppArray(foregroundAppList), backgroundApps = toAppArray(backgroundAppList);
        int flen = foregroundAppList.size(), blen = backgroundAppList.size();
        int fi = flen - 1, bi = 0, max = forgroundApps[0].y + backgroundApps[0].y;
        if (max > deviceCapacity) return new LinkedList<>();
        List<Pair> appPairs = new LinkedList<>();

        for (Pair p : forgroundApps) {
            for (Pair q : backgroundApps) {
                int val = p.y + q.y;
                if (val > deviceCapacity)
                    break;

                if (val > max) {
                    max = val;
                    appPairs.clear();
                }

                if (val == max)
                    appPairs.add(new Pair(p.x, q.x));
            }
        }

        return appPairs.stream()
                .map(p -> {
                    List<Integer> list = new LinkedList<Integer>();
                    list.add(p.x);
                    list.add(p.y);
                    return list;
                }).collect(Collectors.toList());
    }

    private static int prevSmallerIndex(App[] apps, int index) {
        int val = apps[index].y;
        while (index >= 0 && apps[index].y == val) index--;
        return index;
    }

    private static int nextHigherElementIndex(App[] apps, int index) {
        int val = apps[index].y;
        while (index < apps.length && apps[index].y == val) index++;
        return index;
    }

    static App[] toAppArray(List<List<Integer>> appList) {
        return appList.stream()
                .map(fa -> new App(fa.get(0), fa.get(1)))
                .sorted((a, b) -> a.y - b.y)
                .toArray(t -> new App[t]);
    }

    private static class Pair {
        final int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    final static class App extends Pair {
        App(int x, int y) {
            super(x, y);
        }
    }

    private static class Location extends Point {
        final long distanceSquare;
        final List<Integer> list;

        public Location(List<Integer> list) {
            super(list.get(0), list.get(1));
            this.list = list;
            distanceSquare = 1L * x * x + 1L * y * y;
        }
    }
}