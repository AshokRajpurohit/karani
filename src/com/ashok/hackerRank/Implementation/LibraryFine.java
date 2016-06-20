package com.ashok.hackerRank.Implementation;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Library Fine
 * https://www.hackerrank.com/challenges/library-fine
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class LibraryFine {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LibraryFine a = new LibraryFine();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.readInt(), in.readInt(), in.readInt(),
                            in.readInt(), in.readInt(), in.readInt()));
    }

    private static int process(int d1, int m1, int y1, int d2, int m2,
                               int y2) {
        if (y1 > y2)
            return 10000;

        if (y1 < y2)
            return 0;

        if (m1 > m2)
            return 500 * (m1 - m2);

        if (m1 < m2)
            return 0;

        if (d1 > d2)
            return 15 * (d1 - d2);

        return 0;
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
