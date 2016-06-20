package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  https://www.hackerearth.com/april-easy-challenge-15/algorithm/we-are-on-fire/
 */

public class April15EasyC {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] ar;
    private static int total = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        April15EasyC a = new April15EasyC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt(), q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        ar = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ar[i][j] = in.readInt();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (ar[i][j] == 1)
                    total++;
            }
        }

        for (int i = 0; i < q; i++) {
            int l = in.readInt() - 1;
            int k = in.readInt() - 1;
            bomb(l, k);
            sb.append(total).append('\n');
        }

        out.print(sb);
    }

    private static void bomb(int l, int r) {
        if (l < 0 || r < 0 || l == ar.length || r == ar[0].length ||
            ar[l][r] == 0)
            return;

        total--;
        ar[l][r] = 0;
        bomb(l - 1, r);
        bomb(l + 1, r);
        bomb(l, r - 1);
        bomb(l, r + 1);
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
