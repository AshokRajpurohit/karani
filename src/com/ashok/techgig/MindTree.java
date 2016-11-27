package com.ashok.techgig;

import java.util.Arrays;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class MindTree {

    final static class First {

        public static int CompanyRecruitment(int[] marks, int count) {
            if (count == marks.length)
                return 1;

            if (count > marks.length || count <= 0)
                return 0;

            Arrays.sort(marks);
            reverse(marks);

            int total = 0, toBeSelected = 0;
            int value = marks[count - 1];

            for (int i = count - 1; i >= 0 && marks[i] == value; i--)
                toBeSelected++;

            total = toBeSelected;
            for (int i = count; i < marks.length && marks[i] == value; i++)
                total++;

            return ncr(total, toBeSelected);
        }

        private static int ncr(int n, int r) {
            if (r == 1)
                return n;

            return n * ncr(n - 1, r - 1) / r;
        }

        private static void reverse(int[] ar) {
            for (int i = 0, j = ar.length - 1; i < j; i++, j--)
                swap(ar, i, j);
        }

        private static void swap(int[] ar, int i, int j) {
            int t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }
    }

    final static class Second {
        private final static int MAX_HEIGHT = 100000000;
        private final static Point INVALID = new Point(-1, -1);

        /**
         * Seriously! who writes function name as first latter in caps.
         * This is bad coding. Although I do not support it but bound to use it.
         *
         * @param length      grid length
         * @param width       grid width
         * @param heightArray heights of buildings in bad way.
         * @return the water accumulated in buildings.
         */
        public static int GetWaterLevel(int length, int width, int[] heightArray) {
            if (heightArray.length != length * width)
                return -1;

            if (length < 3 || width < 3)
                return 0;

            int[][] height = getHeights(heightArray, length, width);
            Grid grid = new Grid(height, length, width);
            return grid.compute();
        }

        /**
         * Converts the one dimensional array into two dimensional array given
         * in the problem.
         *
         * @param ar    The BAD one dimensional array.
         * @param len   grid length
         * @param width grid width
         * @return 2d array.
         */
        public static int[][] getHeights(int[] ar, int len, int width) {
            int[][] res = new int[len][width];
            int index = 0;

            for (int i = 0; i < len; i++)
                for (int j = 0; j < width; j++)
                    res[i][j] = ar[index++];

            return res;
        }

        /**
         * The {@code Grid} class represents our two dimensional imaginary city.
         */
        final static class Grid {
            final int length, width;
            int[][] block, group;

            Grid(int[][] ar, int len, int wid) {
                length = len;
                width = wid;
                block = ar;
                group = new int[length][width];
                markEdges();
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();

                for (int[] e : block) {
                    for (int f : e)
                        sb.append(f).append(' ');

                    sb.append('\n');
                }

                return sb.toString();
            }

            public int compute() {
                int res = 0;
                int water = getWater();

                while (water != 0) {
                    res += water;
                    water = getWater();
                }

                return res;
            }

            private int getWater() {
                Point point = getDeepestPoint();
                if (point.isInvalid())
                    return 0;

                int water = markGroupAndFill(point);
                return water;
            }

            private int markGroupAndFill(Point point) {
                Group group = new Group();
                markGroup(point, group);
                int water = group.count * group.depth;

                fillMarkedCells(group.depth);
                unmark();

                return Math.max(0, water);
            }

            private void unmark() {
                for (int i = 0; i < length; i++)
                    for (int j = 0; j < width; j++)
                        if (group[i][j] == 1)
                            group[i][j] = 0;
            }

            private void fillMarkedCells(int level) {
                for (int i = 0; i < length; i++)
                    for (int j = 0; j < width; j++)
                        if (group[i][j] == 1)
                            block[i][j] += level;
            }

            private void markGroup(Point point, Group group) {
                if (isPointInvalid(point))
                    return;

                if (isEdgePoint(point)) {
                    group.count = -1; // invalid value.
                    return;
                }

                if (isMarked(point))
                    return;

                markPoint(point);
                group.count++;

                if (!group.isInvalid())
                    updateGroup(point, point.upPoint(), group);

                if (!group.isInvalid())
                    updateGroup(point, point.leftPoint(), group);

                if (!group.isInvalid())
                    updateGroup(point, point.rightPoint(), group);

                if (!group.isInvalid())
                    updateGroup(point, point.downPoint(), group);

                if (group.isInvalid())
                    markEdge(point);
            }

            private void markEdge(Point point) {
                group[point.row][point.col] = -1;
            }

            private boolean isEdgePoint(Point point) {
                return group[point.row][point.col] == -1;
            }

            private void updateGroup(Point groupPoint, Point nextPoint, Group group) {
                if (isPointInvalid(nextPoint))
                    return;

                int nextHeight = getHeight(nextPoint), height = getHeight(groupPoint);
                if (nextHeight > height)
                    group.depth = Math.min(group.depth, nextHeight - height);
                else if (nextHeight == height)
                    markGroup(nextPoint, group);
            }

            private int getHeight(Point point) {
                return block[point.row][point.col];
            }

            private boolean isPointInvalid(Point point) {
                return point.isInvalid() || point.row >= length || point.col >= width;
            }

            private void markPoint(Point point) {
                group[point.row][point.col] = 1;
            }

            private boolean isMarked(Point point) {
                return group[point.row][point.col] == 1;
            }

            private Point getDeepestPoint() {
                Point point = new Point(-1, -1);
                int value = MAX_HEIGHT;

                for (int i = 1; i < length - 1; i++)
                    for (int j = 1; j < width - 1; j++) {
                        if (group[i][j] != -1 && value > block[i][j]) {
                            point.row = i;
                            point.col = j;
                            value = block[i][j];
                        }
                    }

                return point;
            }

            private void markEdges() {
                for (int i = 0; i < length; i++) {
                    group[i][0] = -1; // -1 value marks edge or connected to edge
                    group[i][width - 1] = -1;
                }

                for (int j = 0; j < width; j++) {
                    group[0][j] = -1;
                    group[length - 1][j] = -1;
                }
            }
        }

        /**
         * We define a group as having the cubes (or after water has been filled),
         * with same heights and sharing atleast one edge.
         * <p>
         * Group property is defined on reference point.
         * count is the number of group members and depth is the minimum
         * boundry height.
         * <p>
         * A boundry is list of cubes encircling this group.
         */
        final static class Group {
            int count = 0, depth = MAX_HEIGHT;

            public String toString() {
                return count + ", " + depth;
            }

            boolean isInvalid() {
                return count < 0 || depth < 0;
            }
        }

        final static class Point {
            int row, col;

            Point(int r, int c) {
                row = r;
                col = c;
            }

            public String toString() {
                return row + ", " + col;
            }

            boolean isInvalid() {
                return row == -1 || col == -1;
            }

            Point upPoint() {
                return new Point(row - 1, col);
            }

            Point leftPoint() {
                return new Point(row, col - 1);
            }

            Point rightPoint() {
                return new Point(row, col + 1);
            }

            Point downPoint() {
                return new Point(row + 1, col);
            }
        }
    }
}
