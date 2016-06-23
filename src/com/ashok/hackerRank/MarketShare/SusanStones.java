package com.ashok.hackerRank.MarketShare;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: MarketShare | Susan and Stones
 */

public class SusanStones {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SusanStones a = new SusanStones();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.readInt(), in.readInt(), in.readInt()));
        }
    }

    private static String process(int n, int a, int b) {
        if (n == 1)
            return "0";

        if (a == b)
            return "" + (n - 1) * a;

        StringBuilder sb = new StringBuilder(n << 2);
        if (a > b) {
            int c = a;
            a = b;
            b = c;
        }

        int total = (n - 1) * a;
        sb.append(total).append(' ');
        int d = b - a;
        for (int i = 0; i < n - 1; i++) {
            total += d;
            sb.append(total).append(' ');
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
