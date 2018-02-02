/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan18;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Array Construction
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/approximate/array-construction-410b758b/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ArrayConstruction {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        Matrix matrix = new Matrix(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix.setCell(i, j, in.readInt());

        int[] ar = process(matrix, m);
        out.println(toString(ar));
        out.println(matrix.getValue(ar));
    }

    private static int[] process(Matrix matrix, int arraySum) {
        int n = matrix.size;
        int[] ar = new int[n];
        Arrays.fill(ar, -1);
        Cell[] cells = matrix.getCells();
        Arrays.sort(cells, new CellComparator(matrix));
        reverse(cells);
        Bucket bucket = getBucket(arraySum, n);
        for (Cell cell : cells) {
            addValue(ar, bucket, cell);
        }

        return ar;
    }

    private static void addValue(int[] ar, Bucket bucket, Cell cell) {
        if (ar[cell.row] != -1) {
            if (ar[cell.col] == -1) {
                ar[cell.col] = bucket.getValue(ar[cell.row]);
            }
        } else {
            if (ar[cell.col] == -1) {
                int v1 = bucket.getValue();
                int v2 = bucket.getValue(v1);
                ar[cell.row] = v1;
                ar[cell.col] = v2;
            } else {
                ar[cell.row] = bucket.getValue(ar[cell.col]);
            }
        }
    }

    private static String toString(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length);
        for (int e : ar)
            sb.append(e).append(' ');

        return sb.toString();
    }

    private static Bucket getBucket(int sum, int count) {
        int val1 = sum / count;
        int val2 = val1 + 1, count2 = sum % count;
        int count1 = count - count2;
        Values v1 = new Values(val1, count1), v2 = new Values(val2, count2);
        return new Bucket(v1, v2);
    }

    private static void reverse(Object[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--)
            swap(ar, i, j);
    }

    private static void swap(Object[] objects, int i, int j) {
        Object temp = objects[i];
        objects[i] = objects[j];
        objects[j] = temp;
    }

    final static class Bucket {
        final Values a, b;

        Bucket(Values a, Values b) {
            this.a = a;
            this.b = b;
        }

        int getValue(int desiredValue) {
            if (a.value == desiredValue)
                return a.isEmpty() ? b.getValue() : a.getValue();
            else
                return b.isEmpty() ? a.getValue() : b.getValue();
        }

        int getValue() {
            return a.count > b.count ? a.getValue() : b.getValue();
        }
    }

    final static class Values {
        final int value;
        int count;

        Values(int v, int c) {
            value = v;
            count = c;
        }

        boolean isEmpty() {
            return count <= 0;
        }

        int getValue() {
            if (isEmpty()) throw new NullPointerException("No Values available to get.");

            count--;
            return value;
        }
    }

    final static class Matrix {
        private final Cell[][] matrix;
        private final int size;

        Matrix(int size) {
            this.size = size;
            matrix = new Cell[size][size];
        }

        void setCell(int row, int col, int value) {
            matrix[row][col] = new Cell(row, col, value);
        }

        Cell[] getCells() {
            Cell[] cells = new Cell[size * size];
            int index = 0;
            for (Cell[] c : matrix) {
                System.arraycopy(c, 0, cells, index, size);
                index += size;
            }

            return cells;
        }

        private long getValue(int[] ar) {
            long res = 0;
            for (Cell[] row : matrix)
                for (Cell cell : row)
                    if (ar[cell.row] != ar[cell.col])
                        res += cell.value;

            return res;
        }

        private Cell getComplementaryCell(Cell cell) {
            return matrix[cell.col][cell.row];
        }
    }

    final static class CellComparator implements Comparator<Cell> {
        final Matrix matrix;

        CellComparator(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        public int compare(Cell o1, Cell o2) {
            int v1 = o1.value + matrix.getComplementaryCell(o1).value;
            int v2 = o2.value + matrix.getComplementaryCell(o2).value;
            return v1 - v2;
        }
    }

    private static int cellSequence = 0;

    final static class Cell implements Comparable<Cell> {
        final int row, col, value, id = cellSequence++;

        Cell(int r, int c, int v) {
            row = r;
            col = c;
            value = v;
        }

        @Override
        public int compareTo(Cell cell) {
            return value - cell.value;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return "" + row + ", " + col + ": " + value;
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
    }
}