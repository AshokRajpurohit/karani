package com.ashok.codechef.marathon.year16.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chefland and Electricity
 * Link: https://www.codechef.com/JULY16/problems/CHEFELEC
 * <p>
 * We are not going to connect two farthest adjacent villages between two
 * electrified villages.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHEFELEC {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private final static char con = '1', disCon = '0';

    public static void main(String[] args) throws IOException {
        CHEFELEC a = new CHEFELEC();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            String connection = in.read(n);
            int[] position = in.readIntArray(n);
            out.println(process(n, connection, position));
        }
    }

    private static int process(int n, String connections, int[] position) {
        int total = position[n - 1] - position[0], start = 0, end = 0;
        char[] ar = connections.toCharArray();

        while (start < n && ar[start] == disCon)
            start++;

        if (total == 0)
            return total;

        while (start < n - 1) {
            end = start + 1;
            int localMax = position[end] - position[start];

            while (end < n && ar[end] == disCon) {
                localMax = Math.max(localMax, position[end] - position[end - 1]);
                end++;
            }

            if (end == n)
                return total;

            localMax = Math.max(localMax, position[end] - position[end - 1]);

            total -= localMax;
            start = end;
        }

        return total;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
            return s == -1 ? -number : number;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (; buffer[offset] == '\n'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }

            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == '\n')
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
