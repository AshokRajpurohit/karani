package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 * problem: Utkarsh and Jumps
 * https://www.hackerearth.com/zoomcar-hiring-challenge/problems/445fd9bd832f748b029dfbfb66665e30/
 */

public class ZoomCarB {

    private static PrintWriter out;
    private static InputStream in;
    private double p, q;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        ZoomCarB a = new ZoomCarB();
        a.solve();
        out.close();
    }

    private static String trim(double n) {
        if (n == 0)
            return "0.000000";

        StringBuilder sb = new StringBuilder(String.valueOf(n));
        sb.append("000000");
        return sb.substring(0, 8);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        p = 1.0 * in.readInt() / 100;
        q = 1 - p;
        out.println(getProb(n));
    }

    private String getProb(int n) {
        if (p == 0) {
            if (n % 3 == 0)
                return "1.000000";
            return "0.000000";
        }

        if (p == 1) {
            if (n % 2 == 0)
                return "1.000000";
            return "0.000000";
        }

        if (n == 0)
            return trim(1.0);
        if (n < 0 || n == 1)
            return trim(0.0);

        if (n == 2)
            return trim(p);
        if (n == 3)
            return trim(q);

        double a = 1, b = 0, c = p;

        for (int i = 3; i <= n; i++) {
            double temp = b * p + a * q;
            a = b;
            b = c;
            c = temp;
        }
        return trim(c);
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
