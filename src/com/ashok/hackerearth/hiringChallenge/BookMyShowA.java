package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Valentine Shopping
 */

public class BookMyShowA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        BookMyShowA a = new BookMyShowA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(10000);
        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            //StringBuilder sb = new StringBuilder(n << 2);
            for (int i = 0; i < n; i++) {
                int minPrice = Integer.MAX_VALUE;
                int index = 0;
                int current = 0;
                for (int j = 0; j < m; j++) {
                    current = 100 - in.readInt();
                    current = current * (100 - in.readInt());
                    current = current * (100 - in.readInt());
                    if (current < minPrice) {
                        minPrice = current;
                        index = j;
                    }
                }
                sb.append((index + 1)).append(' ');
            }
            sb.append('\n');
            //            out.print(sb);
        }
        out.print(sb);
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
