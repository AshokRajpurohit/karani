package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Laddu
 * https://www.codechef.com/MAY16/problems/LADDU
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class LADDU {

    private static PrintWriter out;
    private static InputStream in;
    private static final int INDIAN = 2822700, NON_INDIAN =
        -909915668, CONTEST_WON = 1046611848, TOP_CONTRIBUTOR =
        -224447696, BUG_FOUND = -2097012276, CONTEST_HOSTED = -1361663394;


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LADDU a = new LADDU();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int activities = in.readInt(), origin = in.readInt();
            int limit = origin == INDIAN ? 200 : 400;
            int count = 0, laddus = 0;

            for (int i = 0; i < activities; i++) {
                int activity = in.readInt();

                if (activity == CONTEST_WON) {
                    int rank = Math.min(in.readInt(), 20);
                    laddus += 320 - rank;
                } else if (activity == TOP_CONTRIBUTOR) {
                    laddus += 300;
                } else if (activity == BUG_FOUND) {
                    laddus += in.readInt();

                } else {
                    laddus += 50;
                }

                if (laddus >= limit) {
                    count++;
                    laddus -= limit;
                }
            }

            count += laddus / limit;

            sb.append(count).append('\n');
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
