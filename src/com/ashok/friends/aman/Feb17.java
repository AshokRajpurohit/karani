package com.ashok.friends.aman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem: For Aman.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Feb17 {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);

            out.println(remote(ar, 0, 99));
            Arrays.sort(ar);
            out.print(ar);
            formatArrayInZigZag(ar);
            out.print(ar);
            out.print(ar);
            out.flush();

            boolean[][] matrix = new boolean[3][];

            matrix[0] = new boolean[]{true, true, false};
            matrix[1] = new boolean[]{false, true, true};
            matrix[2] = new boolean[]{false, false, true};

            out.println(minSteps(matrix, 0, 0, 2, 2));
            out.flush();
        }
    }

    /**
     * Count minimum number of steps person needs to go from cell (si, sj) to (ei, ej).
     * Blocked cells (not to be visited) are marked as false in matrix.
     * <p>
     * in one step person can go to right, left, up or down cell adjacent to it.
     *
     * @param matrix Cell Grid.
     * @param si     source cell row number
     * @param sj     source cell col number
     * @param ei     target cell row number
     * @param ej     target cell col number
     * @return minimum number of steps to go from source to target.
     */
    private static int minSteps(boolean[][] matrix, int si, int sj, int ei, int ej) {
        int n = matrix.length, m = matrix[0].length;
        Cell source = new Cell(si, sj), target = new Cell(ei, ej);

        if (source.equals(target))
            return 0;

        if (!matrix[si][sj] || !matrix[ei][ej]) // if anyone of source and target cell is un-reachable, why waste time.
            return -1; // let's return invalid value, indicating no such path exists.

        boolean[][] check = new boolean[n][m]; // visiter's log, keeps record of which cell is visited.
        LinkedList<Cell> queue = new LinkedList<>();

        queue.add(source);
        check[source.row][source.col] = true; // mark it visited.
        int distance = 0;

        // we are using BFS or level order traversal.
        while (!queue.isEmpty()) {
            distance++;

            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Cell cell = queue.removeFirst();

                for (Cell c : getNeighbours(cell)) {
                    if (c.isValid(n - 1, m - 1) && !check[c.row][c.col] && matrix[c.row][c.col]) {
                        if (c.equals(target)) // we reached our target, let's return the distance.
                            return distance;

                        queue.addLast(c);
                        check[c.row][c.col] = true;
                    }
                }
            }
        }

        return -1; // when we are here, means we could not reach target.
    }

    private static LinkedList<Cell> getNeighbours(Cell cell) {
        LinkedList<Cell> neighbours = new LinkedList<>();
        neighbours.add(new Cell(cell.row - 1, cell.col)); // up
        neighbours.add(new Cell(cell.row + 1, cell.col)); // down
        neighbours.add(new Cell(cell.row, cell.col - 1)); // left
        neighbours.add(new Cell(cell.row, cell.col + 1)); // right

        return neighbours;
    }

    private static void formatArrayInZigZag(int[] ar) {
        Arrays.sort(ar);
        int count = ar.length;
        int index = 0, value = ar[0];
        int mid = count / 2;

        for (int i = 0; i < ar.length; i++) {
            index = i;
            value = ar[index];

            while (value > 0) {
                count--;
                int target = index < mid ? 2 * index + 1 : (ar.length - 1 - index) * 2;// new index after shuffling.

                if (ar[target] < 0) // checking if already corrected, if so break.
                    break;

                int temp = ar[target];
                ar[target] = -value; // marking target as already correct value.
                value = temp;
                index = target;
            }
        }

        for (int i = 0; i < ar.length; i++)
            ar[i] = -ar[i]; // remove markings.
    }

    public static int remote(int[] ar, int start, int end) {
        int count = 2;
        int prev = start;
        if (Math.abs(start - ar[0]) < 2)
            count = Math.abs(start - ar[0]);

        for (int i = 1; i < ar.length; i++) {
            if (Math.abs(ar[i] - ar[i - 1]) == 1 || prev == ar[i])
                count++;
            else count += 2;

            prev = ar[i - 1];
        }

        if (prev == end)
            return count + 1;

        return count + 2;
    }

    final static class Cell {
        final int row, col;

        Cell(int r, int c) {
            this.row = r;
            this.col = c;
        }

        public boolean equals(Object object) {
            if (this == object)
                return true;

            if (!(object instanceof Cell))
                return false;

            Cell cell = (Cell) object;
            return row == cell.row && col == cell.col;
        }

        public boolean isValid(int maxRow, int maxCol) {
            return row >= 0 && row <= maxRow && col >= 0 && col <= maxCol;
        }

        public String toString() {
            return row + ", " + col;
        }
    }
}
