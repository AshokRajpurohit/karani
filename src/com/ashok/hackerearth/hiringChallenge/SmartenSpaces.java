/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Smarten Spaces Software Developer
 * Link: Personal Mail
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SmartenSpaces {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ArrayCost.solve();
        Walls.solve();
        EscapeFromGrid.solve();
        GoldMine.solve();
        in.close();
        out.close();
    }

    private static void reverse(int[] ar) {
        int n = ar.length;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    final static class ArrayCost {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt(), p = in.readInt();
                out.println(process(in.readIntArray(n), p));
            }
        }

        private static long process(int[] ar, long p) {
            int len = ar.length;
            long res = ar[0] * p, cur = res;
            for (int i = 1; i < len; i++) {
                cur += p * (ar[i] - ar[i - 1]);
                res = Math.max(cur, res);
            }

            return res;
        }
    }

    final static class Walls {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt();
                int[] ar = in.readIntArray(n);
                out.println(process(ar));
            }
        }

        private static long process(int[] ar) {
            int n = ar.length;
            if (n < 3)
                return 0;

            long max = 1L * (n - 3) * Math.min(ar[n - 1], ar[0]);
            for (int i = 0; i < ar.length; i++) {
                long limit = (max - 1) / ar[i];
                for (int j = n - 1; j > i + limit; j--) {
                    if (ar[j] >= ar[i]) {
                        max = Math.max(max, 1L * (j - i - 1) * ar[i]);
                        break;
                    }
                }
            }

            reverse(ar);
            for (int i = 0; i < ar.length; i++) {
                long limit = (max - 1) / ar[i];
                for (int j = n - 1; j > i + limit; j--) {
                    if (ar[j] >= ar[i]) {
                        max = Math.max(max, 1L * (j - i - 1) * ar[i]);
                        break;
                    }
                }
            }

            return max;
        }
    }

    final static class EscapeFromGrid {
        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[][] ar = in.readIntTable(n, m);
            Grid grid = new Grid(ar);
            out.println(grid.getShortestPathLength());
        }
    }

    final static class GoldMine {
        private final int[][] sumMatrix;
        private final int n, m;

        GoldMine(int[][] matrix) {
            n = matrix.length;
            m = matrix[0].length;
            sumMatrix = sumMatrix(matrix);
        }

        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt(), q = in.readInt();
            int[][] grid = in.readIntTable(n, m);
            GoldMine mine = new GoldMine(grid);
            StringBuilder sb = new StringBuilder(q << 2);
            while (q > 0) {
                q--;
                sb.append(mine.query(in.readInt() - 1, in.readInt() - 1, in.readInt() - 1, in.readInt() - 1))
                        .append('\n');
            }

            out.print(sb);
        }

        private int query(int a, int b, int x, int y) {
            int minX = Math.min(a, x), minY = Math.min(b, y), maxX = Math.max(a, x), maxY = Math.max(b, y);

            return query(maxX, maxY) + query(minX - 1, minY - 1) - query(minX - 1, maxY) - query(maxX, minY - 1);
        }

        private int query(int x, int y) {
            if (x < 0 || y < 0)
                return 0;

            return sumMatrix[x][y];
        }

        private static int[][] sumMatrix(int[][] matrix) {
            int length = matrix.length, width = matrix[0].length;
            int[][] sumMatrix = new int[length][width];
            for (int i = 0; i < length; i++) {
                int[] row = matrix[i], sumRow = sumMatrix[i];
                sumRow[0] = row[0];

                for (int j = 1; j < width; j++) {
                    sumRow[j] = sumRow[j - 1] + row[j];
                }
            }

            for (int i = 1; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    sumMatrix[i][j] += sumMatrix[i - 1][j];
                }
            }

            return sumMatrix;
        }
    }

    final static class Grid {
        final Cell[][] grid;
        final int n, m;

        Grid(int[][] ar) {
            n = ar.length;
            m = ar[0].length;
            grid = new Cell[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    grid[i][j] = new Cell(i, j, ar[i][j]);
        }

        public int getShortestPathLength() {
            boolean[][] visited = new boolean[n][m];
            int level = 0;
            LinkedList<Cell> queue = new LinkedList<>();
            queue.addLast(getStartPoint());
            queue.addLast(INVALID_CELL);
            while (queue.size() > 1) {
                Cell top = queue.removeFirst();
                if (top == INVALID_CELL) {
                    queue.addLast(top);
                    level++;
                    continue;
                }

                if (isEdge(top))
                    return level;

                Cell next = left(top);
                if (next != INVALID_CELL && !visited[next.row][next.col] && next.isEmpty()) {
                    visited[next.row][next.col] = true;
                    queue.addLast(next);
                }

                next = right(top);
                if (next != INVALID_CELL && !visited[next.row][next.col] && next.isEmpty()) {
                    visited[next.row][next.col] = true;
                    queue.addLast(next);
                }

                next = up(top);
                if (next != INVALID_CELL && !visited[next.row][next.col] && next.isEmpty()) {
                    visited[next.row][next.col] = true;
                    queue.addLast(next);
                }

                next = down(top);
                if (next != INVALID_CELL && !visited[next.row][next.col] && next.isEmpty()) {
                    visited[next.row][next.col] = true;
                    queue.addLast(next);
                }
            }

            return -1;
        }

        private Cell left(Cell cell) {
            return cell.col > 0 ? grid[cell.row][cell.col - 1] : INVALID_CELL;
        }

        private Cell right(Cell cell) {
            return cell.col < m - 1 ? grid[cell.row][cell.col + 1] : INVALID_CELL;
        }

        private Cell up(Cell cell) {
            return cell.row > 0 ? grid[cell.row - 1][cell.col] : INVALID_CELL;
        }

        private Cell down(Cell cell) {
            return cell.row < n - 1 ? grid[cell.row + 1][cell.col] : INVALID_CELL;
        }

        private boolean isValidCell(Cell cell) {
            return cell.row >= 0 && cell.row < n && cell.col >= 0 && cell.col < 0;
        }

        private Cell getStartPoint() {
            for (Cell[] row : grid)
                for (Cell cell : row)
                    if (cell.isStartPoint())
                        return cell;

            return INVALID_CELL;
        }

        private boolean isEdge(Cell cell) {
            return cell.row == 0 || cell.col == 0 || cell.row == n - 1 || cell.col == m - 1;
        }
    }

    final static Cell INVALID_CELL = new Cell(-1, -1, -1);

    final static class Cell {
        final int row, col, value;

        public Cell(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        private boolean isStartPoint() {
            return value == 2;
        }

        private boolean isEmpty() {
            return value == 0;
        }

        public String toString() {
            return row + ", " + col + " : " + value;
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

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
        }
    }
}