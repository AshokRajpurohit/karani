package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 *      https://www.hackerearth.com/march-clash-15/approximate/putting-matrix-1/
 * Example galat h ismein.
 */

public class Mar153 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] matrix, matsum; // the BIG MATRIX
    private static int[] war, har; // width array, height array
    private static boolean[] visit, put; // visited matrix array and finally put
    private static int xor = 2047, msum = 0, sum = 0;
    private static int[] px, py, tx, ty;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Mar153 a = new Mar153();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        matrix = new int[n][m];
        matsum = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                matrix[i][j] = in.readInt();
        }
        int k = in.readInt();
        war = new int[k];
        har = new int[k];
        visit = new boolean[k];
        put = new boolean[k];
        px = new int[k];
        py = new int[k];
        tx = new int[k];
        ty = new int[k];

        fillmat();

        for (int i = 0; i < k; i++) {
            har[i] = in.readInt();
            war[i] = in.readInt();
        }
        solve(0, 0, n, m);
        int count = 0;
        for (int i = 0; i < put.length; i++)
            if (put[i])
                count++;

        StringBuilder sb = new StringBuilder(count << 4);
        sb.append(msum).append('\n');
        sb.append(count).append('\n');

        for (int i = 0; i < put.length; i++) {
            if (put[i]) {
                sb.append(i + 1).append(' ').append(px[i] + 1).append(' ');
                sb.append(py[i] + 1).append('\n');
            }
        }
        out.print(sb);
    }

    private static void fillmat() {
        int tsum = 0;
        matsum[0][0] = matrix[0][0];

        for (int i = 1; i < matsum[0].length; i++) {
            matsum[0][i] = matsum[0][i - 1] + matrix[0][i];
        }

        for (int i = 1; i < matsum.length; i++) {
            tsum = 0;
            for (int j = 0; j < matsum[i].length; j++) {
                tsum = tsum + matrix[i][j];
                matsum[i][j] = matsum[i - 1][j] + tsum;
            }
        }
    }

    private static void solve(int x, int y, int xlim, int ylim) {
        boolean end = true;
        int tsum = 0;
        for (int i = 0; i < visit.length; i++) {
            if (!visit[i]) {
                for (int p = x; p <= matrix.length - har[i] && p < xlim; p++) {
                    for (int q = y; q <= matrix[p].length - war[i] && q < ylim;
                         q++) {
                        visit[i] = true;
                        tx[i] = p;
                        ty[i] = q;
                        end = false;
                        tsum =
getVal(p - 1, q - 1) - getVal(p - 1, q + war[i] - 1) -
 getVal(p + har[i] - 1, q - 1) + getVal(p + har[i] - 1, q + war[i] - 1);
                        sum = sum + tsum;
                        solve(p + har[i], q, xlim, q + war[i]);
                        solve(p, q + war[i], xlim, ylim);
                        sum = sum - tsum;
                    }
                }
                visit[i] = false;
            }
        }
        if (end && sum > msum) {
            reset();
            msum = sum;
        }
    }

    private static void reset() {
        for (int i = 0; i < visit.length; i++) {
            put[i] = visit[i];
            px[i] = tx[i];
            py[i] = ty[i];
        }
    }

    private static int getVal(int p, int q) {
        if (p < 0 || q < 0)
            return 0;
        return matsum[p][q];
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
