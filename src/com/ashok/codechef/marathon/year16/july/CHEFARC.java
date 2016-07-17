package com.ashok.codechef.marathon.year16.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Chef and Robots Competition
 * Link: https://www.codechef.com/JULY16/problems/CHEFARC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CHEFARC {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char MOVABLE = '0';
    private static boolean[][] grid;
    private static int[][] robotFirst, robotSecond;
    private static Coordinate[][] coordinates;
    private static final Coordinate INVALID = new Coordinate(-1, -1);

    static {
        coordinates = new Coordinate[100][100];

        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100; j++)
                coordinates[i][j] = new Coordinate(i, j);
    }

    public static void main(String[] args) throws IOException {
        CHEFARC a = new CHEFARC();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt(), k1 = in.readInt(), k2 = in.readInt();
            grid = new boolean[n][m];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    grid[i][j] = in.readInt() == 0;

            out.println(process(n, m, k1, k2));
            out.flush();
        }
    }

    private static int process(int n, int m, int k1, int k2) {
        if (m == 1)
            return 0;

        if (m == 2 || k1 >= m - 1 || k2 >= m - 1)
            return 1;

        populate();
        populate(robotFirst, k1, 0, 0);
        populate(robotSecond, k2, 0, m - 1);

        int minDistance = 100000;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (robotFirst[i][j] != -1 && robotSecond[i][j] != -1)
                    minDistance = Math.min(Math.max(robotFirst[i][j],
                            robotSecond[i][j]), minDistance);

        return minDistance == 100000 ? -1 : minDistance;
    }

    private static void populate() {
        int n = grid.length, m = grid[0].length;
        robotFirst = new int[n][m];
        robotSecond = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                robotFirst[i][j] = -1;
                robotSecond[i][j] = -1;
            }
    }

    private static void populate(int[][] robot, int k, int row, int col) {
        int n = robot.length, m = robot[0].length;
        LinkedList<Coordinate> queue = new LinkedList<>();
        Coordinate start = coordinates[row][col];
        queue.add(start);
        queue.add(INVALID);
        boolean[][] visited = new boolean[n][m];
        visited[row][col] = true;

        robot[row][col] = 0;
        int move = 0;

        while (queue.size() > 1) {
            move++;

            while (true) {
                Coordinate current = queue.pollFirst();

                if (current == INVALID) {
                    queue.addLast(INVALID);
                    break;
                }

                int sr = Math.max(0, current.row - k),
                        sc = Math.max(0, current.col - k),
                        er = Math.min(n - 1, current.row + k),
                        ec = Math.min(m - 1, current.col + k);

                for (int i = sr; i <= er; i++) {
                    for (int j = sc; j <= ec; j++) {
                        if (!grid[i][j] || visited[i][j] || k < Math.abs(i -
                                current.row) + Math.abs(j - current.col))
                            continue;

                        visited[i][j] = true;
                        robot[i][j] = move;
                        queue.addLast(coordinates[i][j]);
                    }
                }
            }
        }
    }

    final static class Coordinate {
        int row, col;

        Coordinate(int r, int c) {
            row = r;
            col = c;
        }

        public boolean equals(Coordinate coordinate) {
            if (this == coordinate)
                return true;

            return this.row == coordinate.row && this.col == coordinate.col;
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
    }
}
