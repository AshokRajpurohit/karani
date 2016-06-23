package com.ashok.hackerRank.Strings;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Common Child
 * https://www.hackerrank.com/challenges/common-child
 */

public class CommonChild {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CommonChild a = new CommonChild();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.read(), in.read()));
    }

    private static int process(String a, String b) {
        int[][] ar = new int[a.length()][b.length()];
        boolean[][] var = new boolean[a.length()][b.length()];

        var[0][0] = true;
        if (a.charAt(0) == b.charAt(0))
            ar[0][0] = 1;

        return LCS(ar, var, a, b, a.length() - 1, b.length() - 1);
    }

    private static int LCS(int[][] ar, boolean[][] var, String a, String b,
                           int ai, int bi) {
        if (ai < 0 || bi < 0)
            return 0;

        if (var[ai][bi])
            return ar[ai][bi];

        var[ai][bi] = true;
        ar[ai][bi] = LCS(ar, var, a, b, ai - 1, bi - 1);
        if (a.charAt(ai) == b.charAt(bi))
            ar[ai][bi]++;

        ar[ai][bi] = Math.max(ar[ai][bi], LCS(ar, var, a, b, ai - 1, bi));
        ar[ai][bi] = Math.max(ar[ai][bi], LCS(ar, var, a, b, ai, bi - 1));
        return ar[ai][bi];
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
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
            for (; offset < bufferSize; ++offset) {
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
