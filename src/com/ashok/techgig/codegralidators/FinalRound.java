/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.codegralidators;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Problem Name: Code Gladiators 2017, Final Round.
 * Link: Go and check on TechGig website.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FinalRound {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        FinalRound a = new FinalRound();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            solveFirst();
            out.flush();
        }
    }

    private static void solveFirst() throws IOException {
        int length = in.readInt(), steps = in.readInt();
        String[] ar = generateGrid(length);
        out.print(ar);
        out.flush();
//        String[] ar = in.readStringArray(length, length);
        long time = System.currentTimeMillis();
        out.println(One.maxPossibleWait(length, steps, ar));
        out.println("total time: ", (System.currentTimeMillis() - time));
        out.flush();
    }

    private static String[] generateGrid(int length) {
        int[][] ar = new int[length][length];

//        for (int i = 0; i < length; i++)
//            ar[i] = Generators.generateRandomIntegerArray(length, 2);

        Random random = new Random();
        int r = 0, c = 0;
        int trucks = random.nextInt(length >>> 1);
        for (int i = 0; i < trucks; i++) {
            r = random.nextInt(length);
            c = random.nextInt(length);
            ar[r][c] = 1;
        }

        for (int i = 0; i < length; i++) {
            r = random.nextInt(length);
            c = random.nextInt(length);
            ar[r][c] = 2;
        }

        r = random.nextInt(length);
        c = random.nextInt(length);
        ar[r][c] = 3;

        r = random.nextInt(length);
        c = random.nextInt(length);
        ar[r][c] = 4;

        char[] map = new char[]{'O', 'L', 'H', 'M', 'S'};

        String[] grid = new String[length];
        for (int i = 0; i < length; i++) {
            char[] chars = new char[length];
            for (int j = 0; j < length; j++)
                chars[j] = map[ar[i][j]];

            grid[i] = String.valueOf(chars);
        }

        return grid;
    }

    final static class One {
        private static final int HURDLE = 'H', OPEN = 'O', NICK = 'M', SAFE = 'S', TRUCK = 'L';

        public static int maxPossibleWait(int gridSize, int steps, String[] gridRows) {
            Grid grid = new Grid(gridSize, gridRows);
            return grid.calculateMinimumWaitingTime(steps);
        }

        final static class Grid {
            final int length;
            Cell[][] grid;
            Cell startCell, endCell;
            List<Cell> trucks = new LinkedList<>();
            private int minWaitTime = -1, stepSize;
            private Cell[] neighbours = new Cell[4];

            Grid(int length) {
                this.length = length;
                grid = new Cell[length][length];
            }

            Grid(int length, String[] grid) {
                this(length);
                populate(grid);
                startCell = getStartCell();
                endCell = getEndCell();
                populateTrucks();
                spreadCommandos();
                updateReachability();
                endCell.leaveTime = 0;
            }

            private int calculateMinimumWaitingTime(int stepSize) {
                out.println("start calculation");
                out.flush();
                this.stepSize = stepSize;
//                setBenchMark(stepSize);
                populateWaitingTime();
                return minWaitTime;
            }

            /**
             * We will use DFS to calculate the min waiting time.
             */
            private void populateWaitingTime() {
                boolean[][] visited = new boolean[length][length];// PadChinha, i.e. foot-mark
                int distance = 0;
                Cell cell = startCell;
                minWaitTime = applyBFS(cell, distance, visited);
            }

            /**
             * Applying BFS to get max wait time for nick.
             *
             * @param cell
             * @param distance
             * @param visited
             * @return
             */
            private int applyBFS(Cell cell, int distance, boolean[][] visited) {
                if (visited[cell.row][cell.col] || !cell.reachable) // already visited.
                    return -1;

                int cellWaitTime = getWaitTime(cell, distance, stepSize);

                if (cellWaitTime <= minWaitTime) // no need to check further cells as we are sure, this path
                    return cellWaitTime; // is not good for Nick.

                visited[cell.row][cell.col] = true; // mark
                distance++;
                int time = -1;
                List<Cell> nextCells = getAdjacentCells(cell);
                boolean endCellCheck = false;
                for (Cell nextCell : nextCells) {
                    if (nextCell == endCell) {
                        endCellCheck = true;
                        time = Math.max(time, 1);
                        continue;
                    }
                    if (!nextCell.hurdle())
                        time = Math.max(time, applyBFS(nextCell, distance, visited));
                }

                visited[cell.row][cell.col] = false; // unmark
                cell.leaveTime = Math.min(cellWaitTime, time);

                if (endCellCheck)
                    minWaitTime = cell.leaveTime;

                return cell.leaveTime;
            }

            /**
             * Finds shortest path from initial position to target position and use that time as benchmark for
             * further calculations to remove redundant calcuations.
             * <p>
             * Algo used is BFS.
             */
            private void setBenchMark(int stepSize) {
                LinkedList<Cell> queue = new LinkedList<>();
                queue.add(startCell);
                queue.add(INVALID_CELL); // marker cell.

                int time = startCell.commandoTime; // he should leave at this moment, as we know.
                int distance = 0;
                boolean[][] visited = new boolean[length][length];
                visited[startCell.row][startCell.col] = true;


                while (queue.size() > 1) {
                    distance++;
                    while (queue.getFirst().isValid()) {
                        for (Cell cell : getAdjacentCells(queue.removeFirst())) {
                            if (!visited[cell.row][cell.col]) {
                                visited[cell.row][cell.col] = true;
                                queue.addLast(cell);
                                time = Math.min(time, getWaitTime(cell, distance, stepSize));
                            }
                        }
                    }

                    queue.removeFirst();
                    queue.addLast(INVALID_CELL);
                }

                minWaitTime = time;
            }

            private int getWaitTime(Cell cell, int distance, int stepSize) {
                return cell.commandoTime - (distance + stepSize - 1) / stepSize;
            }

            /**
             * What time Nick should start in order to reach this cell before commandos arrive.
             *
             * @param stepSize
             * @param reachTime
             * @return
             */
            private int getWaitTime(int stepSize, int reachTime, int distance) {
                return reachTime - (distance + stepSize - 1) / stepSize;
            }

            private int getWaitTime(int distance, int stepSize) {
                return (distance + stepSize - 1) / stepSize;
            }

            private void spreadCommandos() {
                for (Cell truck : trucks)
                    truck.commandoTime = 0;

                int time = 1;
                LinkedList<Cell> queue = new LinkedList<>(trucks);
                queue.addLast(INVALID_CELL); // marker cell.

                while (queue.size() > 1) {
                    while (queue.getFirst().isValid()) {
                        List<Cell> nextCells = getAdjacentCells(queue.removeFirst());
                        for (Cell cell : nextCells)
                            if (!cell.hasCommando() && !cell.hurdle()) {
                                queue.addLast(cell);
                                cell.commandoTime = time;
                            }
                    }

                    queue.removeFirst();
                    queue.addLast(INVALID_CELL);
                    time++;
                }
            }

            private void updateReachability() {
                boolean[][] visited = new boolean[length][length];
                LinkedList<Cell> queue = new LinkedList<>();
                queue.add(endCell);
                queue.add(INVALID_CELL);
                visited[endCell.row][endCell.col] = true;
                endCell.reachable = true;

                while (queue.size() > 1) {
                    while (queue.getFirst().isValid()) {
                        Cell top = queue.removeFirst();
                        for (Cell cell : getAdjacentCells(top)) {
                            cell.reachable = true;
                            if (visited[cell.row][cell.col])
                                continue;

                            visited[cell.row][cell.col] = true;
                            queue.add(cell);
                        }
                    }

                    queue.removeFirst();
                    queue.add(INVALID_CELL);
                }
            }

            private void populateTrucks() {
                for (Cell[] row : grid)
                    for (Cell cell : row)
                        if (cell.type == TRUCK)
                            trucks.add(cell);
            }

            private Cell getStartCell() {
                for (Cell[] row : grid)
                    for (Cell cell : row)
                        if (cell.type == NICK)
                            return cell;

                return INVALID_CELL;
            }

            private Cell getEndCell() {
                for (Cell[] row : grid)
                    for (Cell cell : row)
                        if (cell.type == SAFE)
                            return cell;

                return INVALID_CELL;
            }

            private void populate(String[] ar) {
                for (int i = 0; i < length; i++) {
                    char[] row = ar[i].toCharArray();

                    for (int j = 0; j < length; j++)
                        grid[i][j] = new Cell(row[j], i, j);
                }
            }

            List<Cell> getAdjacentCells(Cell cell) {
                LinkedList<Cell> cells = new LinkedList<>();
                if (cell == INVALID_CELL)
                    return cells;

                Cell next = getCell(cell.row - 1, cell.col);
                if (next.isValid())
                    cells.add(next);

                next = getCell(cell.row, cell.col - 1);
                if (next.isValid())
                    cells.add(next);

                next = getCell(cell.row + 1, cell.col);
                if (next.isValid())
                    cells.add(next);

                next = getCell(cell.row, cell.col + 1);
                if (next.isValid())
                    cells.add(next);

                return cells;
            }

            Cell getCell(int row, int col) {
                if (!isPositionValid(row, col))
                    return INVALID_CELL;

                return grid[row][col];
            }

            boolean isPositionValid(int row, int col) {
                return row >= 0 && row < length && col >= 0 && col < length;
            }
        }

        private static final Cell INVALID_CELL = new Cell('I', -1, -1); // always invalid.

        final static class Cell {
            final int type, row, col;
            int commandoTime = -1, leaveTime = -1;
            boolean reachable = false;

            Cell(int type, int row, int col) {
                this.type = type;
                this.row = row;
                this.col = col;
            }

            boolean isValid() {
                return this != INVALID_CELL;
            }

            boolean processed() {
                return leaveTime > -1;
            }

            boolean hurdle() {
                return type == HURDLE;
            }

            boolean hasCommando() {
                return commandoTime != -1;
            }

            public String toString() {
                return "(" + row + "," + col + ") time: " + commandoTime + ", type: " + type;
            }
        }
    }
}
