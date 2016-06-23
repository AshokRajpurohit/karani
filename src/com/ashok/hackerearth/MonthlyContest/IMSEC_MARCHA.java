package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:  https://www.hackerearth.com/imsec-march-challenge/algorithm/find-me/
 */

public class IMSEC_MARCHA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        IMSEC_MARCHA a = new IMSEC_MARCHA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        String yes = "YES ", no = "NO 0\n";

        while (t > 0) {
            t--;
            int count = 0;
            boolean pres = false;
            int n = in.readInt();
            int p = in.readInt();
            int temp = 0;
            for (int i = 0; i < n; i++) {
                temp = in.readInt();
                if (!pres && temp == p)
                    pres = true;
                if (temp >= p)
                    count++;
            }
            if (pres) {
                sb.append(yes).append(count - 1).append('\n');
            } else
                sb.append(no);
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
