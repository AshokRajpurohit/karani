/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.r1A;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name: Pascal Walk
 * Link: Round 1A, Code Jam 2020
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class PascalWalk {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";
    private static final int LIMIT = 1000000000;
    private static final Cell INVALID = new Cell(-1, -1);

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            print(i, process());
        }
    }

    private static void test() throws IOException {
        int n = 2;
        while (true) {
            PascalTriangl pt = new PascalTriangl();
            try {
                pt.getWalk(n);
            } catch (Exception e) {
                e.printStackTrace();
                out.println("failed for " + n);
                out.flush();
                pt = new PascalTriangl();
                pt.getWalk(n);
            }
            n++;
        }
    }

    private static String process() throws IOException {
        int n = in.readInt();
        PascalTriangl pt = new PascalTriangl();
        List<Cell> cells = pt.getWalk(n);
        List<String> c = cells.stream().map(cell -> cell.toString()).collect(Collectors.toList());
        return String.join("\n", c);
    }

    private static void print(int testNo, String result) {
        out.println(CASE + testNo + ":\n" + result);
    }

    private static long ncr(int n, int r) {
        if (r == 0 || r == n) return 1;
        if (r == 1) return n;

        return ncr0(n, Math.min(r, n - r));
    }

    private static long ncr0(long n, int r) {
        if (r == 0 || r == n) return 1;
        if (r == 1) return n;

        long prev = ncr0(n - 1, r - 1);
        if (prev > LIMIT) return prev;
        long ncr = prev * n / r;

        return ncr > LIMIT ? LIMIT + 1 : ncr;
    }

    final static class PascalTriangl {
        private static final int SIZE = 500;
        private static final long[][] CELLS;

        static {
            CELLS = IntStream.range(0, SIZE)
                    .mapToObj(i -> {
                        return IntStream.range(0, i + 1).mapToLong(j -> ncr(i, j)).toArray();
                    }).toArray(t -> new long[t][]);
        }

        private final boolean[][] visitMap;

        PascalTriangl() {
            visitMap = new boolean[SIZE][SIZE];
        }

        public List<Cell> getWalk(long n) {
            Deque<Cell> cells = new LinkedList();
            Cell first = new Cell(0, 0);
            markVisited(first);
            cells.push(first);
            walk(cells, n - value(first));
            List<Cell> path = new LinkedList<>();
            cells.descendingIterator().forEachRemaining(cell -> path.add(cell));
            return path;
        }

        private long walk(Deque<Cell> stack, long n) {
            if (n == 0) return 0;
            Cell cell = stack.peek();
            List<Cell> neighbours = neighbours(cell);
            long value;
            for (Cell nbh : neighbours) {
                if (nbh == INVALID || n < value(nbh) || isVisited(nbh)) {
                    continue;
                }

                stack.push(nbh);
                markVisited(nbh);
                value = walk(stack, n - value(nbh));
                if (value == 0) return value;
                stack.pop();
                markUnVisited(nbh);
            }

            return -1;
        }

        private List<Cell> neighbours(Cell cell) {
            Cell left = left(cell), right = right(cell), ld = getLeftDown(cell),
                    lu = getLeftUp(cell), rd = getRightDown(cell), ru = getRightUp(cell);

            return Arrays.asList(left, right, ld, lu, rd, ru);
        }

        private long value(Cell cell) {
            return CELLS[cell.row][cell.col];
        }

        private void markVisited(Cell cell) {
            visitMap[cell.row][cell.col] = true;
        }

        private void markUnVisited(Cell cell) {
            visitMap[cell.row][cell.col] = false;
        }

        private boolean isVisited(Cell cell) {
            return visitMap[cell.row][cell.col];
        }

        private Cell left(Cell cell) {
            if (cell.col == 0) return INVALID;
            return new Cell(cell.row, cell.col - 1);
        }

        private Cell right(Cell cell) {
            if (cell.col == cell.row) return INVALID;
            return new Cell(cell.row, cell.col + 1);
        }

        private Cell getLeftDown(Cell cell) {
            if (cell.row == SIZE - 1) return INVALID;
            return new Cell(cell.row + 1, cell.col);
        }

        private Cell getRightDown(Cell cell) {
            if (cell.row == SIZE - 1) return INVALID;
            return new Cell(cell.row + 1, cell.col + 1);
        }


        private Cell getLeftUp(Cell cell) {
            if (cell.row == 0 || cell.col == 0) return INVALID;
            return new Cell(cell.row - 1, cell.col - 1);
        }

        private Cell getRightUp(Cell cell) {
            if (cell.row == 0 || cell.col == cell.row) return INVALID;
            return new Cell(cell.row - 1, cell.col);
        }
    }

    final static class Cell {
        final int row, col;
        final String str;

        Cell(final int row, final int col) {
            this.row = row;
            this.col = col;
            str = this == INVALID ? "INVALID" : (row + 1) + " " + (col + 1);
        }

        public String toString() {
            return str;
        }
    }

    private static class InputReader {
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
