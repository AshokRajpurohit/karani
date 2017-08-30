/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.august17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Straightest path
 * Link: https://www.hackerearth.com/challenge/competitive/august-circuits-17/algorithm/vizard-and-turns-a8c61c7e/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class StraightestPath {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        Grid grid = new Grid(n, m);
        grid.populate(in.readStringArray(n));
        grid.process();

        out.println(grid.end.state == State.PROCESSED ? grid.end.turns : -1);
    }

    final static class Grid {
        final int rows, cols;
        Cell start, end;
        Cell[][] cells;

        Grid(int r, int c) {
            rows = r;
            cols = c;
            cells = new Cell[rows][cols];
        }

        void populate(String[] rows) {
            for (int i = 0; i < this.rows; i++)
                for (int j = 0; j < this.cols; j++)
                    cells[i][j] = new Cell(i, j, rows[i].charAt(j));
        }

        void process() {
            start = getCell(CellProperty.ORIGIN);
            end = getCell(CellProperty.DESTINATION);

            int turns = -1;
            LinkedList<Cell> queue = new LinkedList<>();
            queue.addLast(start);

            while (queue.size() > 0) {
                int count = queue.size();
                while (count > 0) {
                    count--;
                    Cell cell = queue.removeFirst();
                    queue.addAll(getCells(cell));
                    cell.state = State.PROCESSED;
                    cell.turns = turns;

                    if (cell == end)
                        return;
                }

                turns++;
            }
        }

        LinkedList<Cell> getCells(Cell cell) {
            LinkedList<Cell> nextCells = new LinkedList<>();
            getCells(cell, Move.LEFT, nextCells);
            getCells(cell, Move.RIGHT, nextCells);
            getCells(cell, Move.UP, nextCells);
            getCells(cell, Move.DOWN, nextCells);

            for (Cell c : nextCells)
                c.state = State.PROCESSING;

            return nextCells;
        }

        void getCells(Cell cell, Move direction, LinkedList<Cell> list) {
            if (cell.checkIfMoveRestricted(direction))
                return;

            int row = cell.row, col = cell.col;
            row += direction.dr;
            col += direction.dc;

            while (validIndex(row, col) && cells[row][col].property != CellProperty.IMMOVABLE) {
                Cell next = cells[row][col];
                if (next.state == State.UNPROCESSED) {
                    list.addLast(next);
                    next.restrictMoves(direction);
                }

                row += direction.dr;
                col += direction.dc;
            }
        }

        private boolean validIndex(int r, int c) {
            return r >= 0 && r < rows && c >= 0 && c < cols;
        }

        private Cell getCell(CellProperty property) {
            for (Cell[] row : cells)
                for (Cell cell : row)
                    if (cell.property == property)
                        return cell;

            return new Cell(-1, -1, 'i');
        }
    }

    final static class Cell {
        final int row, col;
        State state = State.UNPROCESSED; // initial cell state.
        int turns = 0;
        final CellProperty property;
        boolean allowedHorizontal = true, allowedVertical = true;

        Cell(int i, int j, char ch) {
            row = i;
            col = j;
            property = CellProperty.getCellProperty(ch);
        }

        void restrictMoves(Move move) {
            if (move.isHorizontal())
                allowedHorizontal = false;
            else
                allowedVertical = false;
        }

        boolean checkIfMoveRestricted(Move move) {
            return move.isHorizontal() ? !allowedHorizontal : !allowedVertical;
        }
    }

    enum State {
        UNPROCESSED, PROCESSING, PROCESSED;
    }

    enum CellProperty {
        MOVABLE('.'), IMMOVABLE('*'), ORIGIN('V'), DESTINATION('H');
        char property;

        CellProperty(char ch) {
            property = ch;
        }

        static CellProperty getCellProperty(char ch) {
            switch (ch) {
                case '.':
                    return MOVABLE;
                case '*':
                    return IMMOVABLE;
                case 'V':
                    return ORIGIN;
                default:
                    return DESTINATION;
            }
        }
    }

    enum Move {
        LEFT(0, -1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0), LU(LEFT, UP), LD(LEFT, DOWN), RU(RIGHT, UP), RD(RIGHT, DOWN),
        INVALID(0, 0);
        int dr, dc;
        Move reverse;

        static {
            LEFT.reverse = RIGHT;
            RIGHT.reverse = LEFT;
            UP.reverse = DOWN;
            DOWN.reverse = UP;
        }

        Move(int r, int c) {
            dr = r;
            dc = c;
        }

        Move(Move a, Move b) {
            dr = a.dr + b.dr;
            dc = a.dc + b.dc;
        }

        boolean isHorizontal() {
            return this == LEFT || this == RIGHT;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String[] readStringArray(int size) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read();

            return res;
        }
    }
}