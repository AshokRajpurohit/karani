package com.ashok.lang.dsa;

/**
 * The {@code SparseTable2D} class is extension of {@link SparseTable}
 * for two dimensional matrix.
 * The preprosessing complexity for M x N matrix is M x N x log(M) x log(N) and
 * the query time is O(1).
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * @see SparseTable
 */
public class SparseTable2D {
    private int[][][][] matrix;
    int m, n;

    public SparseTable2D(int[][] ar) {
        format(ar);
    }

    public int query(int sr, int sc, int er, int ec) {
        int halfRow = Integer.highestOneBit(er + 1 - sr), halfCol =
            Integer.highestOneBit(ec + 1 - sc);

        return operation(matrix[halfRow][halfCol][sr][sc],
                         matrix[halfRow][halfCol][sr + halfRow][sc],
                         matrix[halfRow][halfCol][sr][sc + halfCol],
                         matrix[halfRow][halfCol][sr + halfRow][sc + halfCol]);
    }

    /**
     * Populates the four dimensional array {@link SparseTable2D#matrix} for
     * fast quering.
     *
     * @param ar
     */
    private void format(int[][] ar) {
        m = ar.length;
        n = ar[0].length;

        matrix = new int[m + 1][n + 1][][];
        matrix[1][1] = ar;

        for (int bitj = 2; bitj <= m; bitj <<= 1) {
            matrix[1][bitj] = new int[m][n + 1 - bitj];
            int halfj = bitj >>> 1;
            for (int i = 0; i < m; i++)
                for (int j = 0; j <= n - bitj; j++) {
                    matrix[1][bitj][i][j] =
                            operation(matrix[1][halfj][i][j], matrix[1][halfj][i][j +
                                      halfj]);
                }
        }

        for (int biti = 2; biti <= m; biti <<= 1) {
            int halfi = biti >>> 1;

            matrix[biti][1] = new int[m + 1 - biti][n];
            for (int i = 0; i <= m - biti; i++) {
                for (int j = 0; j <= n - 1; j++) {
                    matrix[biti][1][i][j] =
                            operation(matrix[halfi][1][i][j], matrix[halfi][1][i +
                                      halfi][j]);
                }
            }

            for (int bitj = 2; bitj <= n; bitj <<= 1) {
                matrix[biti][bitj] = new int[m + 1 - biti][n + 1 - bitj];
                int halfj = bitj >>> 1;

                for (int i = 0; i <= m - biti; i++)
                    for (int j = 0; j <= n - bitj; j++) {
                        matrix[biti][bitj][i][j] =
                                operation(matrix[halfi][halfj][i][j],
                                          matrix[halfi][halfj][i + halfi][j],
                                          matrix[halfi][halfj][i][j + halfj],
                                          matrix[halfi][halfj][i + halfi][j +
                                          halfj]);
                    }
            }
        }
    }

    private int operation(int a, int b, int c, int d) {
        return operation(operation(a, b), operation(c, d));
    }

    private int operation(int a, int b) {
        return a > b ? a : b;
    }
}
