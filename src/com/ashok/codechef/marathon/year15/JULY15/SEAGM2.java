package com.ashok.codechef.marathon.year15.JULY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Sereja and Game 2
 * http://www.codechef.com/JULY15/problems/SEAGM2
 */

public class SEAGM2 {

    private static PrintWriter out;
    private static InputStream in;
    private static double pow;

    static {
        pow = Math.pow(10, 10);
        pow = Math.pow(10, pow);
        pow = Math.pow(10, pow);
        pow = Math.pow(10, pow);
        pow = Math.pow(10, pow);
        pow = pow - 1;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEAGM2 a = new SEAGM2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            double[][] prob = new double[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    prob[i][j] = Double.valueOf(in.read(10));

            out.println(process(prob));
        }
    }

    private static double process(double[][] prob) {
        double[] win = new double[prob.length];
        for (int i = 0; i < win.length; i++) {
            win[i] = 1.0;

            for (int j = 0; j < prob[i].length; j++)
                win[i] *= prob[i][j];
        }

        if (win[0] == 0 || win[0] == 1)
            return win[0];

        double noWin = 1.0, oneWin = 0.0;
        for (int i = 0; i < win.length; i++)
            oneWin += win[i];

        if (win[0] == 0 || win[0] == 1)
            return win[0];

        return win[0] * (1.0 - Math.pow(1.0 - oneWin, pow)) / (oneWin);
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
