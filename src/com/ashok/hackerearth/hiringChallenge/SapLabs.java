package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: SAP Labs Java Hiring Challenge
 * Link: https://www.hackerearth.com/sap-labs-java-hiring-challenge-1/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SapLabs {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String yes = "YES\n", no = "NO\n";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();

        int n = in.readInt(), m = in.readInt();
        int[][] matrix = new int[n][];
        Point.n = n;
        Point.m = m;
        Point.points = new Point[n][m];

        for (int i = 0; i < n; i++)
            matrix[i] = in.readIntArray(m);

        int dx = in.readInt(), dy = in.readInt(), diff = in.readInt();
        Grid grid = new Grid(matrix, diff);
        grid.checkEscape(dx - 1, dy - 1);

        sb.append(grid.possible ? yes : no);

        if (grid.possible) {
            for (Point point : grid.path)
                sb.append(point.x + 1).append(' ').append(point.y + 1).append('\n');
        }

        out.print(sb);
    }

    final static class Grid {
        int n, m, diff;
        boolean possible = false;
        int[][] grid;
        boolean[][] computed;

        LinkedList<Point> path;

        Grid(int[][] matrix, int diff) {
            n = matrix.length;
            m = matrix[0].length;
            grid = matrix;
            this.diff = diff;
        }

        void checkEscape(int dx, int dy) {
            computed = new boolean[n][m];
            path = new LinkedList<>();
            Point current = Point.getPoint(dx, dy);

            compute(current);
        }

        void compute(Point current) {
            if (computed[current.x][current.y])
                return;

            markChecked(current);
            path.addLast(current);

            if (current.isAtBoundary()) {
                possible = true;
                return;
            }

            if (feasible(current, current.leftPoint()))
                compute(current.leftPoint());

            if (!possible && feasible(current, current.rightPoint()))
                compute(current.rightPoint());

            if (!possible && feasible(current, current.upPoint()))
                compute(current.upPoint());

            if (!possible && feasible(current, current.downPoint()))
                compute(current.downPoint());

            if (!possible)
                path.removeLast();
        }

        boolean feasible(Point from, Point to) {
            int h1 = getHeight(from), h2 = getHeight(to);
            return h1 == h2 || (h2 <= h1 - diff);
        }

        private int getHeight(Point point) {
            return grid[point.x][point.y];
        }

        void markChecked(Point point) {
            computed[point.x][point.y] = true;
        }
    }

    final static class Point {
        int x, y;
        static int n, m;
        static Point[][] points;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static Point getPoint(int x, int y) {
            if (points[x][y] == null)
                points[x][y] = new Point(x, y);

            return points[x][y];
        }

        boolean isAtBoundary() {
            return x == 0 || y == 0 || x == n - 1 || y == m - 1;
        }

        Point leftPoint() {
            return Point.getPoint(x, y - 1);
        }

        Point rightPoint() {
            return Point.getPoint(x, y + 1);
        }

        Point upPoint() {
            return Point.getPoint(x - 1, y);
        }

        Point downPoint() {
            return Point.getPoint(x + 1, y);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
