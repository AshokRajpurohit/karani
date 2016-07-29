package com.ashok.hackerearth.marathon.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Little Shino and Coins
 * Link: https://www.hackerearth.com/july-circuits/algorithm/little-shino-and-coins-3/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ShinoAndCoins {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] charIndex = new int[256];

    static {
        for (int i = 'a'; i <= 'z'; i++)
            charIndex[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        ShinoAndCoins a = new ShinoAndCoins();
        while (true) {
            a.solve();
            out.flush();
        }
//        in.close();
//        out.close();
    }

    private void solve() throws IOException {
        int k = in.readInt();
        char[] ar = in.read().toCharArray();
        int n = ar.length;

        if (k > n) {
            out.println(0);
            return;
        }

        int[][] map = new int[26][n];
        map[charIndex[ar[0]]][0] = 1;

        for (int i = 1; i < n; i++) {
            map[charIndex[ar[i]]][i] = 1;

            for (int j = 0; j < 26; j++)
                map[j][i] += map[j][i - 1];
        }

        int res = 0;

        for (int i = 0; i <= n - k; i++) {
            for (int j = i + k - 1; j < n; j++) {
                int uniques = 0;

                for (int[] e : map) {
                    int count = e[j] - (i > 0 ? e[i - 1] : 0);

                    if (count > 0)
                        uniques++;
                }

                if (uniques == k)
                    res++;
            }
        }

        out.println(res);
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
