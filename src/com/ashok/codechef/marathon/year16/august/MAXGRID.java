package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Maximum Grid
 * Link: https://www.codechef.com/AUG16/problems/MAXGRID
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MAXGRID {
    private static final Cell EMPTY_CELL = new Cell(0, 0, 0);
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Cell[] grid;
    private static int minx = 100000, miny = 100000, maxx = 1, maxy = 1;
    private static int N, L;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        N = in.readInt();
        L = in.readInt();

        grid = new Cell[N];
        for (int i = 0; i < N; i++)
            grid[i] = new Cell(in.readInt(), in.readInt(), in.readInt());

        for (Cell cell : grid) {
            minx = Math.min(minx, cell.x);
            miny = Math.min(miny, cell.y);
            maxx = Math.max(maxx, cell.x);
            maxy = Math.max(maxy, cell.y);
        }

        if (1L * (maxx - minx) * (maxy - miny) <= 10000000) {
            matrixWay();
            return;
        }

        Arrays.sort(grid);
        process();
    }

    private static void matrixWay() {
        int length = maxx + 1 - minx, width = maxy + 1 - miny;
        long[][] matrix = new long[length][width];

        for (Cell cell : grid)
            matrix[cell.x - minx][cell.y - miny] = cell.c;

        for (int j = 1; j < width; j++)
            matrix[0][j] += matrix[0][j - 1];

        for (int i = 1; i < length; i++) {
            long rowSum = matrix[i][0];

            matrix[i][0] += matrix[i - 1][0];
            for (int j = 1; j < width; j++) {
                rowSum += matrix[i][j];
                matrix[i][j] = rowSum + matrix[i - 1][j];
            }
        }

        long maxSum = 0;
        int len = Math.min(length, L);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                maxSum = Math.max(maxSum, sum(matrix, i, j, i + len - 1, j +
                        len - 1));
            }
        }

        int end = len, start = 1, minLen = 0;
        int mid = (start + len) >>> 1;
        while (mid != start) {
            if (sumPossible(matrix, mid, maxSum))
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        if (sumPossible(matrix, start, maxSum))
            minLen = start;
        else
            minLen = start + 1;

        out.println(maxSum + " " + minLen);
    }

    private static boolean sumPossible(long[][] matrix, int len, long sum) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (sum(matrix, i, j, i + len - 1, j + len - 1) == sum)
                    return true;
            }
        }

        return false;
    }

    private static long sum(long[][] matrix, int sr, int sc, int er, int ec) {
        return sum(matrix, er, ec) - sum(matrix, sr - 1, ec) - sum(matrix,
                er, sc - 1) + sum(matrix, sr - 1, sc - 1);
    }

    private static long sum(long[][] matrix, int r, int c) {
        if (r < 0 || c < 0)
            return 0;

        if (r >= matrix.length)
            r = matrix.length - 1;

        if (c >= matrix[0].length)
            c = matrix[0].length - 1;

        return matrix[r][c];
    }

    private static void process() {
        long rowSum = 0;
        grid[0].sum = grid[0].c;

        for (int i = 1; i < grid.length; i++) {
            if (grid[i].x != grid[i - 1].x)
                rowSum = 0;

            rowSum += grid[i].c;
            grid[i].sum = rowSum + findSum(grid[i].x - 1, grid[i].y);
        }
    }

    private static long findSum(int x, int y) {
        return findCell(0, grid.length - 1, x, y).sum;
    }

    private static Cell findCell(int start, int end, int x, int y) {
        int mid = (start + end) >>> 1;

        while (start != mid) {
            if (grid[mid].x > x) {
                end = mid;
            } else if (grid[mid].x == x) {
                if (grid[mid].y > y)
                    end = mid;
                else
                    return grid[mid];
            } else
                return grid[mid];

            mid = (start + end) >>> 1;
        }

        return grid[mid];
    }

    final static class Cell implements Comparable<Cell> {
        int x, y, c;
        long sum = 0;

        Cell(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        @Override
        public int compareTo(Cell o) {
            if (x != o.x)
                return x - o.x;

            return y - o.y;
        }
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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
