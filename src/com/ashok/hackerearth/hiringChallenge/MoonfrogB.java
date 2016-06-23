package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: FitThePaintings
 * Link: Moonfrog Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class MoonfrogB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MoonfrogB a = new MoonfrogB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        int a = in.readInt(), b = in.readInt();
        int c = in.readInt(), d = in.readInt();

        if (a + c <= n && Math.max(b, d) <= m) {
            out.print("Yes\n");
            return;
        }

        if (a + c <= m && Math.max(b, d) <= n) {
            out.print("Yes\n");
            return;
        }

        if (a + d <= n && Math.max(b, c) <= m) {
            out.print("Yes\n");
            return;
        }

        if (a + d <= m && Math.max(b, c) <= n) {
            out.print("Yes\n");
            return;
        }

        if (b + c <= n && Math.max(a, d) <= m) {
            out.print("Yes\n");
            return;
        }

        if (b + c <= m && Math.max(a, d) <= n) {
            out.print("Yes\n");
            return;
        }

        if (b + d <= n && Math.max(a, c) <= m) {
            out.print("Yes\n");
            return;
        }

        if (b + d <= m && Math.max(a, c) <= n) {
            out.print("Yes\n");
            return;
        }

        out.print("No\n");

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
