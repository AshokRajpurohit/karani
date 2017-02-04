/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Micro's House
 * Link: https://www.hackerearth.com/january-circuits-17/algorithm/micros-house-30/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MicrosHouse {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private final static int size = 30;
    private static int n, m, maxNum = 0, maxBit = 0;
    private static int[] bitValue = new int[size];
    private static int[][] matrix;
    private static BitCounts[][] bitMatrix;
    private static MatrixIndex[][] bitMatrixIndices = new MatrixIndex[size][];

    static {
        bitValue[0] = 1;

        for (int i = 1; i < size; i++)
            bitValue[i] = bitValue[i - 1] << 1;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        n = in.readInt();
        m = in.readInt();
        matrix = new int[n][];

        for (int i = 0; i < n; i++)
            matrix[i] = in.readIntArray(m);

        out.println(process());
    }

    private static int process() {
        if (allEqual(matrix))
            return matrix[0][0];

        maxNum = getMax(matrix);
        if ((maxNum & (maxNum + 1)) == 0)
            return maxNum;

        maxBit = 0;
        int n = maxNum;
        while (n > 1) {
            n >>>= 1;
            maxBit++;
        }

        bitMatrix = getBitMatrix();
        processBitMatrix();
        populateBitMatrixIndices();

        return bruteForce();
        /*maxNum = getMax(matrix);
        maxBit = Integer.highestOneBit(maxNum);
        MatrixIndex from = new MatrixIndex(0, 0), to = MatrixIndex.INVALID;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (!bitMatrix[i][j].isSetBit(maxBit))
                    continue;

                to = new MatrixIndex(i, j);
            }

        process(from, to, maxBit - 1);

        for (int i =)

        return maxNum;*/
    }

    private static int bruteForce() {
        if (isMaximumPossibleValue(maxNum))
            return maxNum;

        MatrixIndex[] matrixIndices = getMatrixIndices(maxBit);
        bruteForce(matrixIndices[0]);

        if (isMaximumPossibleValue(maxNum))
            return maxNum;

        for (int i = 1; i < matrixIndices.length && !isMaximumPossibleValue(maxNum); i++) {
            if ((i & 1) == 0)
                bruteForce(matrixIndices[i], MatrixIndex.ORIGIN, true, matrixIndices[0], true);

            MatrixIndex[] matrixIndicesInRange = getMatrixIndicesInRange(matrixIndices, i);
            for (int j = 1; j < matrixIndicesInRange.length && !isMaximumPossibleValue(maxNum); j++) {
                MatrixIndex start = matrixIndicesInRange[j - 1], end = matrixIndicesInRange[j];
                boolean startInclude = ((matrixIndicesInRange.length - j) & 1) == 0;

                bruteForce(matrixIndices[i], start, startInclude, end, !startInclude);
            }
        }


        return maxNum;
    }

    private static int bruteForce(MatrixIndex currentIndex, MatrixIndex start, boolean startInclude, MatrixIndex end, boolean endInclude) {
        for (int i = start.row; i <= end.row; i++)
            for (int j = start.col; j <= end.col; j++) {
                if (i == start.row && j == start.col && !startInclude)
                    continue;

                if (i == end.row && j == end.col && !endInclude)
                    continue;

                maxNum = Math.max(maxNum, getValue(i, j, currentIndex.row, currentIndex.col));
                if (isMaximumPossibleValue(maxNum))
                    return maxNum;
            }

        return maxNum;
    }

    private static int bruteForce(MatrixIndex start, boolean include, MatrixIndex current) {
        for (int i = start.row; i <= current.row; i++)
            for (int j = start.col; j <= current.col; j++) {
                if (i == start.row && j == start.col && !include)
                    continue;

                maxNum = Math.max(maxNum, getValue(i, j, current.row, current.col));

                if (isMaximumPossibleValue(maxNum))
                    return maxNum;
            }

        return maxNum;
    }

    private static MatrixIndex[] getMatrixIndicesInRange(MatrixIndex[] matrixIndices, int index) {
        List<MatrixIndex> matrixIndexList = new LinkedList<>();
        MatrixIndex current = matrixIndices[index];

        for (int i = 0; i <= index; i++)
            if (matrixIndices[i].row <= current.row && matrixIndices[i].col <= current.col)
                matrixIndexList.add(matrixIndices[i]);

        return toArray(matrixIndexList);
    }

    private static int bruteForce(MatrixIndex currentIndex) {
        for (int i = 0; i <= currentIndex.row; i++)
            for (int j = 0; j <= currentIndex.col; j++) {
                maxNum = Math.max(maxNum, getValue(i, j, currentIndex.row, currentIndex.col));

                if (isMaximumPossibleValue(maxNum))
                    return maxNum;
            }

        return maxNum;
    }

    private static void process(MatrixIndex from, MatrixIndex to, int bitIndex) {
        for (int i = from.row; i <= to.row; i++)
            for (int j = from.col; j <= to.col; j++) {
                if (!bitMatrix[i][j].isSetBit(bitIndex))
                    continue;
            }
    }

    private static boolean isMaximumPossibleValue(int n) {
        return (n & (n + 1)) == 0;
    }

    private static int getValue(int si, int sj, int ei, int ej) {
        return getValue(ei, ej) ^ getValue(ei, sj - 1) ^ getValue(si - 1, ej) ^ getValue(si - 1, sj - 1);
    }

    private static int getValue(int row, int col) {
        if (row < 0 || col < 0)
            return 0;

        return bitMatrix[row][col].getValue();
    }

    private static void populateBitMatrixIndices() {
        LinkedList<MatrixIndex>[] listArray = new LinkedList[size];
        for (int i = 0; i < size; i++)
            listArray[i] = new LinkedList<>();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < size; k++)
                    if (bitMatrix[i][j].bits[k])
                        listArray[k].add(new MatrixIndex(i, j));
            }

        for (int i = 0; i < size; i++)
            bitMatrixIndices[i] = toArray(listArray[i]);
    }

    private static MatrixIndex[] getMatrixIndices(int bit) {
        LinkedList<MatrixIndex> list = new LinkedList<>();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (bitMatrix[i][j].bits[maxBit])
                    list.add(new MatrixIndex(i, j));
            }

        return toArray(list);
    }

    private static int getMax(int[][] ar) {
        int max = ar[0][0];

        for (int[] e : ar)
            for (int f : e)
                max = Math.max(max, f);

        return max;
    }

    private static MatrixIndex[] toArray(List<MatrixIndex> matrixIndexList) {
        MatrixIndex[] res = new MatrixIndex[matrixIndexList.size()];

        int index = 0;
        for (MatrixIndex matrixIndex : matrixIndexList)
            res[index++] = matrixIndex;

        return res;
    }

    private static boolean allEqual(int[][] ar) {
        int value = ar[0][0];
        for (int[] e : ar)
            for (int f : e)
                if (f != value)
                    return false;

        return true;
    }

    private static boolean allEqual(int[] ar) {
        int ref = ar[0];

        for (int e : ar)
            if (e != ref)
                return false;

        return true;
    }

    private static BitCounts[][] getBitMatrix() {
        BitCounts[][] bitMatrix = new BitCounts[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                bitMatrix[i][j] = new BitCounts(matrix[i][j]);

        return bitMatrix;
    }

    /**
     * Make {@code bitMatrix} like a two dimensional sum array where ar[i][j] is sum of each element
     * ar[x][y], x [0, i], y[0, j].
     *
     * @param bitMatrix, BitCount array to be processed.
     */
    private static void processBitMatrix() {
        for (int i = 0; i < n; i++)
            for (int j = 1; j < m; j++)
                bitMatrix[i][j].add(bitMatrix[i][j - 1].getValue());

        for (int i = 1; i < n; i++)
            for (int j = 0; j < m; j++)
                bitMatrix[i][j].add(bitMatrix[i - 1][j].getValue());
    }

    final static class BitCounts {
        boolean[] bits = new boolean[size]; // it should be 27 but let's take 30.

        BitCounts(int n) {
            for (int i = 0; i < size && n > 0; i++) {
                bits[i] = (n & 1) == 1;
                n >>>= 1;
            }
        }

        void add(int n) {
            for (int i = 0; i < size && n > 0; i++) {
                bits[i] ^= (n & 1) == 1;
                n >>>= 1;
            }
        }

        int getValue() {
            int bit = 1, value = 0;

            for (int i = 0; i < size; i++, bit = bit << 1)
                if (bits[i])
                    value |= bit;

            return value;
        }

        boolean isSetBit(int bitIndex) {
            return bits[bitIndex];
        }

        int getBitValue(int bitIndex) {
            if (bits[bitIndex])
                return 1 << bitIndex;

            return 0;
        }

        public String toString() {
            return String.valueOf(getValue());
        }
    }

    final static class MatrixIndex {
        final static MatrixIndex INVALID = new MatrixIndex(-1, -1), ORIGIN = new MatrixIndex(0, 0);
        final int row, col;

        MatrixIndex(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return row + ", " + col;
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
