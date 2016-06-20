package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Array;

import java.util.LinkedList;

/**
 * Problem: Sereja and Two Lines
 * https://www.codechef.com/FEB16/problems/SEATL
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SEATL {

    private static PrintWriter out;
    private static InputStream in;
    private static LinkedList<Integer> ref = new LinkedList<Integer>();
    private static LinkedList<Integer>[] rowCount =
        (LinkedList<Integer>[])Array.newInstance(ref.getClass(),
                                                 1000001), colCount =
        (LinkedList<Integer>[])Array.newInstance(ref.getClass(), 1000001);

    private static int[] countRows = new int[1000001], countCols =
        new int[1000001], tcount = new int[1000001];

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEATL a = new SEATL();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int[][] ar = new int[n][];

            for (int i = 0; i < n; i++)
                ar[i] = in.readIntArray(m);

            sb.append(process(ar, n, m)).append('\n');
        }
        out.print(sb);
    }

    private static int process(int[][] matrix, int n, int m) {
        LinkedList<Integer>[] rows = getRowsCount(matrix, n, m, countRows);
        LinkedList<Integer>[] cols = getColCount(matrix, n, m, countCols);

        int maxCount = 1;
        int maxLength = 0;
        for (int[] ar : matrix)
            for (int v : ar)
                maxLength = Math.max(maxLength, countRows[v] + countCols[v]);

        for (int[] ar : matrix)
            for (int v : ar) {
                int length = countRows[v] + countCols[v];
                if (length == maxLength && length > maxCount) {
                    maxCount = count(rows[v], cols[v], v, length, matrix);
                }
                countRows[v] = 0;
                countCols[v] = 0;
                rows[v] = null;
                cols[v] = null;
            }
        return maxCount;
    }

    /**
     * We have the list of rows and columns having maximum count of value.
     * Some of rows and columns may not have the element 'value' as their
     * intersection, so in that case the count is sum of counts from row and
     * column else one less.
     *
     * @param rows
     * @param cols
     * @param value
     * @param length
     * @param matrix
     * @return
     */
    private static int count(LinkedList<Integer> rows,
                             LinkedList<Integer> cols, int value, int length,
                             int[][] matrix) {
        if (length == matrix.length + matrix[0].length)
            return length - 1;

        if (countCols[value] == 1) {
            if (cols.size() >= length)
                return length;

            return length - 1;
        }

        if (countRows[value] == 1) {
            if (rows.size() >= length)
                return length;

            return length - 1;
        }

        if (rows.size() < cols.size()) {
            for (Integer e : rows) {
                for (Integer f : cols) {
                    if (matrix[e][f] != value)
                        return length;
                }
            }
        } else {
            for (Integer e : cols) {
                for (Integer f : rows) {
                    if (matrix[f][e] != value)
                        return length;
                }
            }
        }

        return length - 1;
    }

    /**
     * This function stores the list of columns having maximum count of element.
     * for element e the list of rows is stored at colCount[n].
     *
     * @param matrix
     * @param n
     * @param m
     * @param count
     * @return
     */
    private static LinkedList<Integer>[] getColCount(int[][] matrix, int n,
                                                     int m, int[] count) {
        LinkedList<Integer>[] res = colCount;

        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++)
                tcount[matrix[i][j]]++;

            for (int i = 0; i < n; i++) {
                int v = matrix[i][j];
                if (count[v] < tcount[v]) {
                    count[v] = tcount[v];
                    res[v] = new LinkedList<Integer>();
                    res[v].add(j);
                } else if (tcount[v] == count[v]) {
                    if (res[v] == null)
                        res[v] = new LinkedList<Integer>();
                    res[v].add(j);
                }
            }

            for (int i = 0; i < n; i++)
                tcount[matrix[i][j]] = 0;
        }

        return res;
    }

    /**
     * This function stores the list of rows having maximum count of element.
     * for element e the list of rows is stored at rowCount[n].
     *
     * @param matrix
     * @param n
     * @param m
     * @param count
     * @return
     */
    private static LinkedList<Integer>[] getRowsCount(int[][] matrix, int n,
                                                      int m, int[] count) {
        LinkedList<Integer>[] res = rowCount;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                tcount[matrix[i][j]]++;

            for (int j = 0; j < m; j++) {
                int v = matrix[i][j];
                if (count[v] < tcount[v]) {
                    count[v] = tcount[v];
                    res[v] = new LinkedList<Integer>();
                    res[v].add(i);
                } else if (tcount[v] == count[v]) {
                    if (res[v] == null)
                        res[v] = new LinkedList<Integer>();
                    res[v].add(i);
                }
            }

            for (int j = 0; j < m; j++)
                tcount[matrix[i][j]] = 0;
        }

        return res;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30; ++offset) {
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
            return number;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
