package com.ashok.hackerearth.alkhwarizm;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class D {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        D a = new D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int x = in.readInt();
        int y = in.readInt();
        String r1 = in.read();
        String r2 = in.read();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 4);
        int[] lar1, lar2, rar1, rar2;
        int h1;

        for (int i = 1; i < n; i++) {

        }

        for (int i = 0; i < q; i++) {
            long res = 0;
            int rn = in.readInt();
            if (rn > n) {
                rn = rn - n - 1;
                if (r2.charAt(rn) == 'B') {
                    res = r1.charAt(rn) == 'B' ? y : 0;
                    res =
rn < n - 1 && r2.charAt(rn + 1) == 'B' ? res + y : res;
                    res = rn != 0 && r2.charAt(rn - 1) == 'B' ? res + y : res;
                }
            } else {
                rn = rn - 1;
                if (r1.charAt(rn) == 'B') {
                    res = r2.charAt(rn) == 'B' ? y : 0;
                    res =
rn < n - 1 && r1.charAt(rn + 1) == 'B' ? res + y : res;
                    res = rn != 0 && r1.charAt(rn - 1) == 'B' ? res + y : res;
                }
            }
            sb.append(res).append('\n');
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
