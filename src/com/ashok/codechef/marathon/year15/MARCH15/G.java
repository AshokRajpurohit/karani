package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link:
 * http://www.codechef.com/MARCH15/problems/SIGNWAVE
 */
public class G {

    private static PrintWriter out;
    private static InputStream in;
    private static long unit = 1;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        G a = new G();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int s = in.readInt();
            int c = in.readInt();
            int k = in.readInt();
            sb.append(solve(s, c, k)).append('\n');
        }
        out.print(sb);
    }

    private static long solve(int s, int c, int k) {

        if (k == 1) {
            if (s > c)
                return (unit << s) | 1;

            if (s == 0)
                return (unit << (c + 1)) - 2;

            return 1 | (unit << (c + 1));
        }

        if (c == 0) {
            if (s < k)
                return 0;
            return (unit << (s - k + 1)) | 1;
        }

        if (s < k)
            return 0;

        long res = 0;
        //        int m = s - k;
        res = (unit << (s - k + 1));

        if (s - k <= c - 1) {
            //            res = 1 | (unit << (s - k + 2));
            return 1 | (res << 1);
        }
        return res | 1;
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
