/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.april;

import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Problem Name: Bear and Random Grid
 * Link: https://www.codechef.com/APRIL17/problems/RNDGRID
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BearAndRandomGrid {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int EMPTY = '.', BLOCKED = '#', BROWN = -2, BLACK = -1, SKIPLENGTH = 20;
    private static Move[] movesMap = new Move[256];
    private static char[][] grid;
    private static Position[] lowestPositions, highestPositions, positionSum;
    private static int[][] scoreMatrix;
    private static char[] moves;
    private static int[] matchMap;
    private static int stackSize = 0;

    static {
        movesMap['R'] = Move.RIGHT;
        movesMap['L'] = Move.LEFT;
        movesMap['U'] = Move.UP;
        movesMap['D'] = Move.DOWN;
    }

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int L = in.readInt(), N = in.readInt();
            moves = in.read(L).toCharArray();
            grid = readMatrix(N);

            out.println(process());
        }
    }

    private static void test() throws IOException {
        Output output = new Output();
        while (true) {
            output.println("Enter moves size, grid size and probability factor (1 to infinity)");
            output.flush();
            int length = in.readInt(), gridSize = in.readInt(), probabilityFactor = in.readInt();
            moves = generateMoves(length);
            grid = generateGrid(gridSize, probabilityFactor);
            try {
                process();

                output.println(String.valueOf(moves));
                output.println();
                output.print(grid);
                output.println();
                output.print(scoreMatrix);
                output.flush();
            } catch (Exception e) {
                output.println("Error");
                e.printStackTrace();
            }
        }
    }

    private static int process() {
        matchMap = getMatchMap(moves);
        lowestPositions = getLowestPositionMap(moves);
        highestPositions = getHighestPositionMap(moves);
        scoreMatrix = getDefaultScoreMatrix();
        positionSum = getPositionSums(moves);
        stackSize = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (scoreMatrix[i][j] >= 0)
                    continue;

//                scoreMatrix[i][j] = calculateScoreBruteForce(i, j);
                calculateScore(i, j);
            }
        }

        return calculateScore(scoreMatrix);
    }

    private static int calculateScoreBruteForce(int row, int col) {
        if (grid[row][col] == BLOCKED)
            return 0;

        int x = row, y = col, count = 0;

        for (char ch : moves) {
            Move move = movesMap[ch];

            x += move.dx;
            y += move.dy;

            if (!isIndexValid(x, y) || grid[x][y] == BLOCKED)
                break;

            count++;
        }

        return count;
    }

    private static void calculateScore(int row, int col) {
        if (!isIndexValid(row, col) || grid[row][col] == BLOCKED || !isUnprocessed(row, col))
            return;

        stackSize++;
        markUnderProcess(row, col);
        int x = row, y = col;
        int count = 0;

        for (int index = 0; index < moves.length; ) {
            char ch = moves[index];
            Move move = movesMap[ch];
            Position diff = positionSum[index];

            x = row + diff.x;
            y = col + diff.y;

            if (!isIndexValid(x, y) || grid[x][y] == BLOCKED)
                break;

            count++;
            index++;
            if (count < moves.length && !isUnderProcess(x, y) && stackSize < 500) {
                calculateScore(x, y);
            }

            if (count < moves.length && scoreMatrix[x][y] <= matchMap[count]) {
                int length = Math.min(scoreMatrix[x][y], moves.length - count);

                if (length > 0) {
                    index += length;
                    count += length;
                    continue;
                }
//                scoreMatrix[row][col] = count + length;
//                return;
            } else if (count < moves.length && matchMap[count] >= SKIPLENGTH) {
                int length = Math.min(matchMap[count], moves.length - count);
                length = findLength(new Position(row, col), count - 1, length);

                if (length < SKIPLENGTH)
                    continue;

                index += length;
                count += length;
            }
        }

        scoreMatrix[row][col] = count;
        stackSize--;
    }

    private static void calculateScore(int row, int col, int index) {
        if (index == moves.length) {
            scoreMatrix[row][col] = index;
            return;
        }

        Position diff = positionSum[index];
        if (!isIndexValid(row + diff.x, col + diff.y) || grid[row + diff.x][col + diff.y] == BLOCKED) {
            scoreMatrix[row][col] = index;
            return;
        }

        while (index < moves.length) {
            if (matchMap[index] >= SKIPLENGTH) {
                int length = Math.min(matchMap[index], moves.length - index);
                length = findLength(new Position(row, col), index - 1, length);
                calculateScore(row, col, index + length);
                return;
            }

            diff = positionSum[index];
            int x = row + diff.x, y = col + diff.y;
            if (!isIndexValid(x, y) || grid[x][y] == BLOCKED)
                break;

            index++;

            if (index < moves.length && isUnprocessed(x, y))
                calculateScore(x, y);
        }

        scoreMatrix[row][col] = index;
    }

    private static int findLength(Position position, int offset, int length) {
        if (length < 10)
            return linearSearch(position, offset, length);

        int x = position.x, y = position.y;
        int start = 0, end = length, mid = length >>> 1;

        while (start != end) {
            Position diffLow = lowestPositions[offset + mid], diffHigh = highestPositions[offset + mid];

            if (isIndexValid(x + diffLow.x, y + diffLow.y) && isIndexValid(x + diffHigh.x, y + diffHigh.y))
                start = mid;
            else end = mid;

            mid = (start + end) >>> 1;
        }

        Position diff = positionSum[offset + end];
        if (isIndexValid(x + diff.x, y + diff.y))
            return end;

        return start;
    }

    private static int linearSearch(Position position, int offset, int length) {
        int x = position.x, y = position.y;
        for (int i = 1; i <= length; i++) {
            Position diffLow = lowestPositions[offset + i], diffHigh = highestPositions[offset + i];

            if (isIndexValid(x + diffLow.x, y + diffLow.y) && isIndexValid(x + diffHigh.x, y + diffHigh.y))
                return i - 1;
        }

        return length;
    }

    private static Position[] getLowestPositionMap(char[] moves) {
        int minX = 1, minY = 1, currentX = 0, currentY = 0;
        Position[] lowestPositions = new Position[moves.length];

        for (int i = 0; i < moves.length; i++) {
            Move move = movesMap[moves[i]];
            currentX += move.dx;
            currentY += move.dy;

            minX = Math.min(minX, currentX);
            minY = Math.min(minY, currentY);
            lowestPositions[i] = new Position(minX, minY);
        }

        return lowestPositions;
    }

    private static Position[] getHighestPositionMap(char[] moves) {
        int minX = 1, minY = 1, currentX = 0, currentY = 0;
        Position[] highestPositions = new Position[moves.length];

        for (int i = 0; i < moves.length; i++) {
            Move move = movesMap[moves[i]];
            currentX += move.dx;
            currentY += move.dy;

            minX = Math.max(minX, currentX);
            minY = Math.max(minY, currentY);
            highestPositions[i] = new Position(minX, minY);
        }

        return highestPositions;
    }

    private static Position[] getPositionSums(char[] moves) {
        Position[] positions = new Position[moves.length];
        int x = 0, y = 0, index = 0;

        for (char ch : moves) {
            Move move = movesMap[ch];
            x += move.dx;
            y += move.dy;

            positions[index++] = new Position(x, y);
        }

        return positions;
    }

    private static int calculateScore(int[][] matrix) {
        int score = 0;

        for (int[] row : matrix)
            for (int e : row)
                score = score ^ e;

        return score;
    }

    private static boolean isIndexValid(int x, int y) {
        int n = grid.length;
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    private static int[] getMatchMap(char[] ar) {
        int[] res = new int[ar.length];
        Arrays.fill(res, -1);
        res[0] = ar.length;

        for (int i = 1; i < ar.length; i++) {
            int count = 0;
            for (int j = 0, k = i; k < ar.length; j++, k++) {
                if (ar[j] != ar[k])
                    break;

                count++;
            }

            res[i] = count;
        }

        return res;
    }

    private static char[][] readMatrix(int n) throws IOException {
        char[][] matrix = new char[n][];

        for (int i = 0; i < n; i++)
            matrix[i] = in.read(n).toCharArray();

        return matrix;
    }

    private static int[][] getDefaultScoreMatrix() {
        int[][] matrix = new int[grid.length][grid.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = grid[i][j] == EMPTY ? BLACK : 0;

        return matrix;
    }

    private static void markUnderProcess(int row, int col) {
        scoreMatrix[row][col] = BROWN;
    }

    private static void markUnderProcess(Position position) {
        markUnderProcess(position.x, position.y);
    }

    private static boolean isUnderProcess(int row, int col) {
        return scoreMatrix[row][col] == BROWN;
    }

    private static boolean isUnderProcess(Position position) {
        return isUnderProcess(position.x, position.y);
    }

    private static boolean processed(int row, int col) {
        return scoreMatrix[row][col] > BLACK;
    }

    private static boolean processed(Position position) {
        return processed(position.x, position.y);
    }

    private static boolean isUnprocessed(int row, int col) {
        return scoreMatrix[row][col] == BLACK;
    }

    private static boolean isUnprocessed(Position position) {
        return isUnprocessed(position.x, position.y);
    }

    enum Move {
        RIGHT(0, 1), LEFT(0, -1), UP(-1, 0), DOWN(1, 0);

        final int dx, dy;

        Move(int x, int y) {
            dx = x;
            dy = y;
        }
    }

    final static class Position {
        final int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position move(Move move) {
            return new Position(x + move.dx, y + move.dy);
        }

        Position backMove(Move move) {
            return new Position(x - move.dx, y - move.dy);
        }

        public String toString() {
            return x + ", " + y;
        }
    }

    private static char[][] generateGrid(int length, int probabilityFactor) {
        char[][] grid = new char[length][];

        for (int i = 0; i < length; i++)
            grid[i] = generateRow(length, probabilityFactor);

        return grid;
    }

    private static char[] generateRow(int length, int probabilityFactor) {
        char empty = '.', blocker = '#';
        char[] ar = new char[length];

        if (probabilityFactor < 2) {
            Arrays.fill(ar, empty);
            return ar;
        }

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int next = random.nextInt(probabilityFactor);

            if (next == 0)
                ar[i] = blocker;
            else ar[i] = empty;
        }

        return ar;
    }

    private static char[] generateMoves(int length) {
        char[] moveMap = new char[]{'R', 'U', 'L', 'D'};
        char[] moves = new char[length];
        Random random = new Random();

        for (int i = 0; i < length; i++)
            moves[i] = moveMap[random.nextInt(4)];

        return moves;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
    }
}