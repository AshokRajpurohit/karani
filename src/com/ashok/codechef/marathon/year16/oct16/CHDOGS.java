package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Problem Name: Chef and Three Dogs
 * Link: https://www.codechef.com/OCT16/problems/CHDOGS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CHDOGS {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static NumberFormat formatter = new DecimalFormat("#0.000000000");
    private static final double epsilon = 0.00000001;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int s = in.readInt(), v = in.readInt();
            double ans = 1.0 * (s << 1) / (3.0 * v);
            out.println(formatter.format(epsilon + ans));
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
