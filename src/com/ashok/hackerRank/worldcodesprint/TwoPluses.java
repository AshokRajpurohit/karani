package com.ashok.hackerRank.worldcodesprint;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Ema's Supercomputer
 * https://www.hackerrank.com/contests/worldcodesprint/challenges/two-pluses
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class TwoPluses {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] plus;
    private static char good = 'G', bad = 'B';
    private static String[] grid;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TwoPluses a = new TwoPluses();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        grid = new String[n];

        for (int i = 0; i < n; i++)
            grid[i] = in.read(m);

        format();
        out.println(process());
    }

    private static int process() {
        int n = grid.length, m = grid[0].length(), area = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) == bad)
                    continue;

                for (int ki = i; ki < n; ki++) {
                    for (int kj = 0; kj < m; kj++) {
                        if (grid[ki].charAt(kj) == bad)
                            continue;

                        area = Math.max(area, getArea(i, j, ki, kj));
                    }
                }
            }
        }

        return area;
    }

    private static int getArea(int fi, int fj, int si, int sj) {
        if (!isOverlap(fi, fj, si, sj))
            return ((plus[fi][fj] << 2) + 1) * ((plus[si][sj] << 2) + 1);

        int far = Math.max(Math.abs(fi - si), Math.abs(fj - sj));
        int near = Math.min(Math.abs(fi - si), Math.abs(fj - sj));

        if (far == 0)
            return 0;

        int large = Math.max(plus[fi][fj], plus[si][sj]);
        int small = Math.max(plus[fi][fj], plus[si][sj]);

        if (near == 0) {
            int len = far - 1 - small;
            if (small > len) {
                len = (far - 1) >>> 1;
            }
            int area1 = (len << 2) + 1;

            return area1 * (((far - 1 - len) << 2) + 1);
        }

        int newLarge = near - 1;

        int area = ((newLarge << 2) + 1) * ((small << 2) + 1);
        int newSmall = near - 1;
        int temp = ((newSmall << 2) + 1) * ((large << 2) + 1);

        return Math.max(area, temp);
    }

    private static boolean isOverlap(int fi, int fj, int si, int sj) {
        int far = Math.max(Math.abs(fi - si), Math.abs(fj - sj));
        int near = Math.min(Math.abs(fi - si), Math.abs(fj - sj));

        if (far == 0)
            return true;

        if (fi == si || fj == sj) {
            return far < plus[fi][fj] + plus[si][sj] + 1;
        }

        if (far > Math.max(plus[fi][fj], plus[si][sj]))
            return false;

        return near <= Math.min(plus[fi][fj], plus[si][sj]);
    }

    private static void format() {
        int n = grid.length, m = grid[0].length();

        int[][] up = new int[n][m], down = new int[n][m], left =
            new int[n][m], right = new int[n][m];

        for (int i = 1; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) == grid[i - 1].charAt(j))
                    up[i][j] = up[i - 1][j] + 1;
            }

        for (int i = n - 2; i >= 0; i--)
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) == grid[i + 1].charAt(j))
                    down[i][j] = down[i + 1][j] + 1;
            }

        for (int i = 0; i < n; i++)
            for (int j = 1; j < m; j++) {
                if (grid[i].charAt(j) == grid[i].charAt(j - 1))
                    left[i][j] = left[i][j - 1] + 1;
            }

        for (int i = 0; i < n; i++)
            for (int j = m - 2; j >= 0; j--) {
                if (grid[i].charAt(j) == grid[i].charAt(j + 1))
                    right[i][j] = right[i][j + 1] + 1;
            }

        plus = new int[n][m];

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (grid[i].charAt(j) == bad)
                    continue;

                plus[i][j] =
                        Math.min(Math.min(left[i][j], right[i][j]), Math.min(up[i][j],
                                                                             down[i][j]));
            }
        }
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
