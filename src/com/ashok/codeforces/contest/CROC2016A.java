package com.ashok.codeforces.contest;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Parliament of Berland
 * problem Link: http://codeforces.com/contest/644/problem/A
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CROC2016A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CROC2016A a = new CROC2016A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), a = in.readInt(), b = in.readInt();

        out.println(process(n, a, b));
    }

    private static String process(int n, int a, int b) {
        if (n > a * b)
            return "-1";

        if ((n > 1) && (a == 1 || b == 1))
            return "-1";

        StringBuilder sb = new StringBuilder(a * b * 4);

        int[][] parliament = new int[a][b];
        boolean toggle = true;

        for (int i = 0; i < a && n > 0; i++) {
            if (!toggle) {
                for (int j = b - 1; j >= 0 && n > 0; j--)
                    parliament[i][j] = n--;
            } else {
                for (int j = 0; j < b && n > 0; j++)
                    parliament[i][j] = n--;
            }

            toggle = !toggle;
        }

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++)
                sb.append(parliament[i][j]).append(' ');

            sb.append('\n');
        }

        return sb.toString();
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
