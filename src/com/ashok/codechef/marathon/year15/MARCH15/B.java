package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link http://www.codechef.com/MARCH15/problems/CNOTE
 */
public class B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();
        String happy = "LuckyChef\n";
        String unhappy = "UnluckyChef\n";

        while (t > 0) {
            t--;
            int x = in.readInt();
            int y = in.readInt();
            int k = in.readInt();
            int n = in.readInt();
            int page = x - y; // pages needed
            boolean hp = false;
            int p, c; // p price and c cost of ith notebook

            /**
             * checking whether any notebook with lesser price and having more
             * pages is available.
             */

            for (int i = 0; i < n; i++) {
                p = in.readInt();
                c = in.readInt();
                if (p >= page && c <= k)
                    hp = true;
            }

            if (hp)
                sb.append(happy);
            else
                sb.append(unhappy);
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
