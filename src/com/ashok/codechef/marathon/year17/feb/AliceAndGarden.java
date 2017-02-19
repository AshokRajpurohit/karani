/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Alice and her garden
 * Link: https://www.codechef.com/FEB17/problems/ALICGARD
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class AliceAndGarden {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static StringBuilder response;
    private static final String MOVE = "MOVE ", EXIT = "EXIT";
    private static final Cell INVALID_CELL = new Cell(-1, -1);
    private static int timer = 0; // it is basically a move counter for alice.
    private static final int thinkAheadMoves = 4; // how many steps ahead alice thinks before making a move.
    private static int penalty;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        response = new StringBuilder();

        int n = in.readInt(), f = in.readInt(), x = in.readInt(), y = in.readInt();
        penalty = in.readInt();
        Garden garden = new Garden(penalty, n);

        int[][] flowers = in.readIntTable(n, n), limits = in.readIntTable(n, n), regrowTimes = in.readIntTable(n, n);
        garden.addFlowers(flowers, limits, regrowTimes);

        for (int i = 1; i <= f; i++)
            garden.addFence(in.readInt() - 1, in.readInt() - 1, in.readInt() - 1, in.readInt() - 1, i);

        garden.setAlicePosition(x - 1, y - 1);
        startMoving(garden);

        response.append(EXIT);
        out.print(response);
    }

    /**
     * Let's allowe Alice to roam around and pluck flowers in garden.
     *
     * @param garden
     */
    private static void startMoving(Garden garden) {
        while (true) {
            Cell next = thinkNextCell(garden);
            if (!movable(next))
                return;

            garden.moveAliceToCell(next);
            response.append(MOVE).append(next.row + 1).append(' ').append(next.col + 1).append('\n');
            timer++;
        }
    }

    /**
     * Calculates what should be the next move for Alice.
     *
     * @param garden Alice's garden, she is roaming around, plucking flowers without any reason.
     * @return Next cell, Alice should visite in order to get maximum score.
     */
    private static Cell thinkNextCell(Garden garden) {
        int maxDepth = thinkAheadMoves;
        long max = 0;
        Cell next = INVALID_CELL;

        for (Cell cell : getNeighbourCells(garden.alicePosition)) {
            if (!movable(cell))
                continue;

            long value = thinkNextCellValue(cell, 1);
            if (max < value) {
                max = value;
                next = cell;
            }
        }

        return next;
    }

    /**
     * Just figure out the next move for the {@code cell}, without updating anything.
     *
     * @param cell     cell for which next move has to be figure out.
     * @param maxDepth thinking level this cell exists at.
     * @return maximum flowers which can be picked up starting from this cell.
     */
    private static long thinkNextCellValue(Cell cell, int maxDepth) {
        if (maxDepth > thinkAheadMoves)
            return 0;

        cell.limit--; // dummy visit.
        int cellTime = cell.flowerPot.lastPluckTime; // let's save last plucking time before we loose it.
        long value = cell.pluckIfAvailable(timer + maxDepth), max = 0;

        if (maxDepth < thinkAheadMoves) {
            for (Cell neighbour : getNeighbourCells(cell))
                max = Math.max(max, thinkNextCellValue(neighbour, maxDepth + 1) - getMovingCost(cell, neighbour));
        }

        cell.flowerPot.lastPluckTime = cellTime; // resotore the plucking time
        cell.limit++; // undoing dummy visit.
        return value + max; // this is the max value, what this program can think of.
    }

    /**
     * Can we move to this cell? This depends on
     * 1. Cell should be a valid cell.
     * 2. Cell's visiting counter is not full.
     *
     * @param cell
     * @return
     */
    private static boolean movable(Cell cell) {
        return cell != INVALID_CELL && cell.limit > 0;
    }

    /**
     * What is the cost if we move from cell {@code from} to cell {@code to}?
     *
     * @param from
     * @param to
     * @return
     */
    private static int getMovingCost(Cell from, Cell to) {
        if (from.fenceId != to.fenceId)
            return penalty;

        return 0;
    }

    /**
     * Returns the list of neighbouring cells for {@code cell}, which are visitable.
     * Preferred Cells are within same fence.
     *
     * @param cell
     * @return
     */
    private static LinkedList<Cell> getNeighbourCells(Cell cell) {
        LinkedList<Cell> allNeighbours = new LinkedList<>();

        addCellToList(allNeighbours, cell);
        addCellToList(allNeighbours, cell.left());
        addCellToList(allNeighbours, cell.left().up());
        addCellToList(allNeighbours, cell.up());
        addCellToList(allNeighbours, cell.up().right());
        addCellToList(allNeighbours, cell.right());
        addCellToList(allNeighbours, cell.right().down());
        addCellToList(allNeighbours, cell.down());
        addCellToList(allNeighbours, cell.down().left());

        LinkedList<Cell> neighboursWithinFence = getNeighboursWithinFence(allNeighbours, cell.fenceId);

        if (neighboursWithinFence.size() > 0)
            return neighboursWithinFence;

        return allNeighbours;
    }

    private static void addCellToList(LinkedList<Cell> cells, Cell cell) {
        if (movable(cell))
            cells.add(cell);
    }

    private static LinkedList<Cell> getNeighboursWithinFence(List<Cell> cellList, int fenceId) {
        LinkedList<Cell> cells = new LinkedList<>();

        for (Cell cell : cellList)
            if (cell.fenceId == fenceId)
                cells.add(cell);

        return cells;
    }

    final static class Garden {
        final int penalty;
        Cell alicePosition;
        Cell[][] cells;

        Garden(int penalty, int size) {
            Cell.initialize(size);
            this.penalty = penalty;
            cells = new Cell[size][size];

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    cells[i][j] = Cell.getInstance(i, j);
        }

        void addFlowers(int[][] flowerValues, int[][] limit, int[][] regrowTimes) {
            for (int i = 0; i < limit.length; i++)
                for (int j = 0; j < limit.length; j++) {
                    FlowerPot flowerPot = new FlowerPot(flowerValues[i][j], regrowTimes[i][j]);
                    cells[i][j].flowerPot = flowerPot;
                    cells[i][j].limit = limit[i][j];
                }
        }

        void setAlicePosition(int x, int y) {
            alicePosition = cells[x][y];
            alicePosition.pluck(timer);
            alicePosition.limit--; // 1st visit to this cell.
        }

        /**
         * Adds fence starting frowm (sr, sc) as top left corner to (er, ec) as bottom right corner to
         * the garden.
         *
         * @param sr      start row
         * @param sc      start collumn
         * @param er      end row
         * @param ec      end column
         * @param fenceId fence id to be added.
         */
        void addFence(int sr, int sc, int er, int ec, int fenceId) {
            for (int i = sr; i <= er; i++)
                for (int j = sc; j <= ec; j++)
                    cells[i][j].fenceId = fenceId;
        }

        void moveAliceToCell(Cell cell) {
            alicePosition = cell;
            cell.limit--;

            cell.pluckIfAvailable(timer);
        }

        public String toString() {
            return "penalty: " + penalty + " alice position: " + alicePosition;
        }
    }

    final static class Cell {
        static int size;
        static Cell[][] baseCells;

        final int row, col;
        int fenceId = 0; // default id, it means like, without fence.
        int limit; // how many times, Alice can visit this cell.
        FlowerPot flowerPot;

        static void initialize(int len) {
            size = len;
            baseCells = new Cell[len][len];
        }

        static Cell getInstance(int x, int y) {
            if (x < 0 || y < 0 || x >= size || y >= size)
                return INVALID_CELL;

            if (baseCells[x][y] == null)
                baseCells[x][y] = new Cell(x, y);

            return baseCells[x][y];
        }

        private long pluck(int time) {
            return flowerPot.pluck(time);
        }

        private long pluckIfAvailable(int time) {
            return flowerPot.pluckIfAvailable(time);
        }

        private boolean valid() {
            return this != INVALID_CELL;
        }

        private Cell(int x, int y) {
            row = x;
            col = y;
        }

        public Cell left() {
            if (this == INVALID_CELL)
                return this;

            return getInstance(row, col - 1);
        }

        public Cell right() {
            if (this == INVALID_CELL)
                return this;

            return getInstance(row, col + 1);
        }

        public Cell up() {
            if (this == INVALID_CELL)
                return this;

            return getInstance(row - 1, col);
        }

        public Cell down() {
            if (this == INVALID_CELL)
                return this;

            return getInstance(row + 1, col);
        }

        public String toString() {
            return "cell location is (" + row + ", " + col + "), can be visited " + limit + " time more";
        }
    }

    final static class FlowerPot {
        final int flowers, regrowTime; // these values are not going to change, even after Big Collapse.
        private int lastPluckTime = Integer.MIN_VALUE; // we don't know when the flowers were plucked last time.
        // I think in 1500 BCE, so currently all the plants are full of flowers.

        FlowerPot(int f, int r) {
            flowers = f;
            regrowTime = r;
            lastPluckTime = -r;
        }

        /**
         * Are flowers regrown at {@code time}, so that we can pluck?
         *
         * @param time
         * @return
         */
        private boolean readyToPluck(int time) {
            return time >= lastPluckTime + regrowTime;
        }

        /**
         * Go ahead and pluck, you will get exception if you try to pluck, already plucked flowers.
         * This plant did hard Tapasya (penace) in raining season for days and Indra dev gave him one boon.
         * <p>
         * Multiple Sharp Prickles. Beware of these.
         *
         * @param time
         * @return all the flowers it has, else expect some bad behaviour
         */
        private long pluck(int time) {
            if (readyToPluck(time)) {
                lastPluckTime = time;
                return flowers;
            }

            throw new RuntimeException("Can not pluck flowers, when they are not ready");
        }

        private long pluckIfAvailable(int time) {
            if (readyToPluck(time)) {
                lastPluckTime = time;
                return flowers;
            }

            return 0;
        }

        public String toString() {
            return "flowers: " + flowers + " regrows in " + regrowTime + " time. last pluck time: " + lastPluckTime;
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
