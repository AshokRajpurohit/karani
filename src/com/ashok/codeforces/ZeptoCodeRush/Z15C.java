package com.ashok.codeforces.ZeptoCodeRush;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class Z15C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Z15C a = new Z15C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int c = in.readInt();
        int hr = in.readInt();
        int hb = in.readInt();
        int wr = in.readInt();
        int wb = in.readInt();
        long jr = hr * wb;
        long jb = hb * wr;
        if (jr > jb) {
            int resr = c / wr;
            int resb = c - resr * wr;
            long max = 0;
            long temp = resr * hr + resb * hb / wb;
            long rem = resb % wb;
            while (temp >= max) {
                max = temp;
                rem = rem + wr;
                temp = temp - hr + hb * (rem / wb);
                rem = rem % wb;
            }
            out.println(max);
            return;
        }

        int resb = c / wb;
        int resr = c - resb * wb;
        long max = 0;
        long temp = resb * hb + hr * (resr / wr);
        long rem = resr % wr;
        while (temp >= max) {
            max = temp;
            rem = rem + wb;
            temp = temp - hb + hr * (rem / wr);
            rem = rem % wr;
        }
        out.println(max);
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
