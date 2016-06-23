package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Inflation
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHINFL {

    private static PrintWriter out;
    private static InputStream in;
    private static double epsilon = 0.000001, limit = 1000000000000000000.0;
    private static Pair[][] ar;
    private static int n, m;
    private static String ans = "Quintillionnaire";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHINFL a = new CHINFL();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        n = in.readInt();
        m = in.readInt();
        double d = Double.valueOf(in.read());
        ar = new Pair[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                ar[i][j] = new Pair(in.readInt(), in.readInt());
        }

        iterative(d);
    }

    private static void iterative(double d) {
        double[] dollar = new double[n], peppe = new double[n];
        double[] dollart = new double[n], peppet = new double[n];
        boolean breaker = false;

        for (int i = 0; i < n; i++) {
            peppe[i] = d;
            dollar[i] = d / ar[i][0].sell;
        }

        for (int i = 1; i < m && !breaker; i++) {
            copy(peppe, peppet);
            copy(dollar, dollart);

            for (int j = 0; j < n - 1; j++) {
                dollar[j] = Math.max(dollart[j], dollart[j + 1]);
                peppe[j] = Math.max(peppet[j], peppet[j + 1]);
            }

            for (int j = 1; j < n; j++) {
                dollar[j] = Math.max(dollar[j], dollart[j - 1]);
                peppe[j] = Math.max(peppe[j], peppet[j - 1]);
            }

            for (int j = 0; j < n && !breaker; j++) {
                double temp = limit / ar[j][i].buy;
                if (temp - dollart[j] < epsilon) {
                    breaker = true;
                    break;
                }
                dollar[j] = Math.max(dollar[j], peppet[j] / ar[j][i].sell);
                peppe[j] = Math.max(peppe[j], dollart[j] * ar[j][i].buy);
            }
        }

        if (breaker) {
            out.println(ans);
            return;
        }

        double res = 0;
        for (int i = 0; i < n; i++)
            res = Math.max(res, peppe[i]);

        out.println(res);
        return;
    }

    private static void copy(double[] from, double[] to) {
        for (int i = 0; i < n; i++)
            to[i] = from[i];
    }

    final static class Pair {
        int sell, buy;

        Pair(int x, int y) {
            sell = x;
            buy = y;
        }
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
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
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
