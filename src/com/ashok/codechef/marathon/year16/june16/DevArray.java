package com.ashok.codechef.marathon.year16.june16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Devu and an Array
 * https://www.codechef.com/JUNE16/problems/DEVARRAY
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class DevArray {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        DevArray a = new DevArray();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);
        String yes = "Yes\n", no = "No\n";

        int min = in.readInt(), max = min;
        for (int i = 1; i < n; i++) {
            int temp = in.readInt();

            if (temp < min)
                min = temp;
            else if (temp > max)
                max = temp;
        }

        while (q > 0) {
            q--;
            int temp = in.readInt();

            if (temp >= min && temp <= max)
                sb.append(yes);
            else
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
