package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Matrix Division
 * Link: https://www.codechef.com/SEPT16/problems/CHMTDV
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHMTDV {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[][] matrix;
    private static long[][] sum;
    private static int N, P;
    private static int[] h, v, h_back, v_back;
    private static long matrixSum = 0, rowColAvg = 0;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        N = in.readInt();
        P = in.readInt();
        h = new int[P];
        v = new int[P];
        h_back = new int[P];
        v_back = new int[P];

        matrix = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                matrix[i][j] = in.readInt();

        process();
        StringBuilder sb = new StringBuilder(P << 3);

        for (int i = 0; i < P - 1; i++)
            sb.append(h[i] + 1).append(' ');

        sb.append('\n');
        for (int i = 0; i < P - 1; i++)
            sb.append(v[i] + 1).append(' ');

        out.print(sb);
    }

    private static void process() {
        populateSum();
        long[] rowSum = new long[N], colSum = new long[N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                rowSum[i] += matrix[i][j];
                colSum[i] += matrix[j][i];
            }

        long grandSum = sum[N - 1][N - 1];
        long avg = grandSum / N;
        matrixSum = grandSum;
        rowColAvg = avg;

        for (int i = 1; i <= N - P; i++) {

            if (getColSum(i) > avg)
                break;

            v[0] = i;
        }

        for (int i = 1; i < P; i++) {
            v[i] = v[i - 1] + 1;
            for (int j = v[i - 1] + 1; j <= N - P + i; j++) {
                if (getColSum(j) > avg * (i + 1))
                    break;

                v[i] = j;
            }
        }

        v[P - 1] = N - 1;

        // arranging horizontal lines
        for (int i = 1; i <= N - P; i++) {

            if (getRowSum(i) > avg)
                break;

            h[0] = i;
        }

        for (int i = 1; i < P; i++) {
            h[i] = h[i - 1] + 1;
            for (int j = h[i - 1] + 1; j <= N - P + i; j++) {
                if (getRowSum(j) > avg * (i + 1))
                    break;

                h[i] = j;
            }
        }

        h[P - 1] = N - 1;

        boolean optimize = true;
        long time = System.currentTimeMillis();
        while (optimize) {
            optimize = optimize();

            if (System.currentTimeMillis() - time >= 3500)
                break;
        }
    }

    private static boolean optimize() {
        long max = 0;
        int r = 0, c = 0;
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < P; j++) {
                long value = getBlockSum(i, j);

                if (value > max) {
                    r = i;
                    c = j;
                    max = value;
                }
            }
        }

        return optimize(r, c);
    }

    private static long getMaxBlockSum() {
        long max = 0;
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < P; j++) {
                max = Math.max(max, getBlockSum(i, j));
            }
        }

        return max;
    }

    private static long getMaxBlockSumInCol(int col) {
        long max = 0;
        for (int i = 0; i < P; i++)
            max = Math.max(max, getBlockSum(i, col));

        return max;
    }

    private static long getMaxBlockSumInRow(int row) {
        long max = 0;
        for (int i = 0; i < P; i++)
            max = Math.max(max, getBlockSum(row, i));

        return max;
    }

    private static int leftBoundry(int col) {
        if (col == 0)
            return 0;

        return v[col - 1] + 1;
    }

    private static int upperBoundry(int row) {
        if (row == 0)
            return 0;

        return h[row - 1] + 1;
    }

    private static boolean optimize(int row, int col) {
        copy(h, h_back);
        copy(v, v_back);
        boolean optimized = false;
        long max = getBlockSum(row, col);
        if (shiftLeft(row, col, max)) {
            optimized = max > getMaxBlockSum();

            if (optimized)
                return optimized;

            copy(h_back, h);
            copy(v_back, v);
        }

        if (shiftRight(row, col, max)) {
            optimized = max > getMaxBlockSum();

            if (optimized)
                return optimized;

            copy(h_back, h);
            copy(v_back, v);
        }

        if (shiftUp(row, col, max)) {
            optimized = max > getMaxBlockSum();

            if (optimized)
                return optimized;

            copy(h_back, h);
            copy(v_back, v);
        }

        shiftDown(row, col, max);
        optimized = max > getMaxBlockSum();

        if (optimized)
            return optimized;

        copy(h_back, h);
        copy(v_back, v);

        return optimized;
    }

    private static boolean shiftLeft(int r, int c, long max) {
        if (c == 0 || c == P - 1 || leftBoundry(c) == v[c])
            return false;

        long blockValue = getBlockSum(r, c);
        v[c]--;

        long nextMax = getMaxBlockSumInCol(c + 1);
        if (nextMax >= max)
            return shiftLeft(r, c + 1, max);

        return true;
    }

    private static boolean shiftRight(int r, int c, long max) {
        if (c == 0 || leftBoundry(c) == v[c])
            return false;

        long blockValue = getBlockSum(r, c);
        v[c - 1]++;

        long nextMax = getMaxBlockSumInCol(c - 1);
        if (nextMax >= max)
            return shiftRight(r, c - 1, max);

        return true;
    }

    private static boolean shiftUp(int r, int c, long max) {
        if (r == 0 || r == P - 1 || upperBoundry(r) == h[r])
            return false;

        long blockValue = getBlockSum(r, c);
        h[r]--;

        long nextMax = getMaxBlockSumInRow(r + 1);
        if (nextMax >= max)
            return shiftUp(r + 1, c, max);

        return true;
    }

    private static boolean shiftDown(int r, int c, long max) {
        if (r == 0 || upperBoundry(r) == h[r])
            return false;

        long blockValue = getBlockSum(r, c);
        h[r - 1]++;

        long nextMax = getMaxBlockSumInRow(r - 1);
        if (nextMax >= max)
            return shiftDown(r - 1, c, max);

        return true;
    }

    private static void copy(int[] source, int[] target) {
        for (int i = 0; i < source.length; i++)
            target[i] = source[i];
    }

    private static long getBlockSum(int hIndex, int vIndex) {
        int sr = hIndex == 0 ? 0 : h[hIndex - 1] + 1,
                sc = vIndex == 0 ? 0 : v[vIndex - 1] + 1;

        return getSum(sr, sc, h[hIndex], v[vIndex]);
    }

    private static long getColSum(int start, int end) {
        return getSum(0, start, N - 1, end);
    }

    private static long getColSum(int col) {
        return getColSum(0, col);
    }

    private static long getRowSum(int row) {
        return getRowSum(0, row);
    }

    private static long getRowSum(int start, int end) {
        return getSum(start, 0, end, N - 1);
    }

    private static void populateSum() {
        sum = new long[N][N];

        for (int i = 0; i < N; i++)
            sum[i][0] = matrix[i][0];

        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++)
                sum[i][j] = sum[i][j - 1] + matrix[i][j];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++)
                sum[i][j] += sum[i - 1][j];
        }
    }

    private static long getSum(int r, int c) {
        if (r < 0 || c < 0 || r >= N || c >= N)
            return 0;

        return sum[r][c];
    }

    private static long getSum(int sr, int sc, int er, int ec) {
        return getSum(er, ec) - getSum(er, sc - 1) - getSum(sr - 1, ec)
                + getSum(sr - 1, sc - 1);
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
