package com.ashok.hackerRank.Implementation;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: The Grid Search
 * https://www.hackerrank.com/challenges/the-grid-search
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class GridSearch {

    private static PrintWriter out;
    private static InputStream in;
    private static String[] grid, pattern;
    private static int R, C, r, c;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        GridSearch a = new GridSearch();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            R = in.readInt();
            C = in.readInt();
            grid = new String[R];

            for (int i = 0; i < R; i++)
                grid[i] = in.read(C);

            r = in.readInt();
            c = in.readInt();
            pattern = new String[r];

            for (int i = 0; i < r; i++)
                pattern[i] = in.read(c);

            if (process())
                out.print(yes);
            else
                out.print(no);
        }
    }

    private static boolean process() {
        for (int i = r - 1; i < R; i++) {
            for (int j = c - 1; j < C; j++) {
                if (match(i, j))
                    return true;
            }
        }

        return false;
    }

    private static boolean match(int row, int col) {
        for (int i = row - r + 1, si = 0; i <= row; i++, si++) {
            for (int j = col - c + 1, sj = 0; j <= col; j++, sj++) {
                if (grid[i].charAt(j) != pattern[si].charAt(sj))
                    return false;
            }
        }

        return true;
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
