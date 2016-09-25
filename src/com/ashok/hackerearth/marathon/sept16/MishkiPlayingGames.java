package com.ashok.hackerearth.marathon.sept16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Mishki Playing Games
 * Link: https://www.hackerearth.com/september-circuits/algorithm/mishki-playing-games/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MishkiPlayingGames {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int limit = 1000000;
    private static int[] bits = new int[limit + 1];

    static {
        int bit = 1, value = 1;
        while (bit <= limit) {
            bits[bit] = value;
            bit = bit << 1;
            ++value;
        }

        for (int i = 2; i <= limit; i++) {
            if (bits[i] == 0)
                bits[i] = bits[i - 1];
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        int[] sum = new int[n];
        String mishki = "Mishki\n", hacker = "Hacker\n";
        StringBuilder sb = new StringBuilder(q * 7);

        for (int i = 0; i < n; i++) {
            sum[i] = bits[ar[i]];
        }

        for (int i = 1; i < n; i++)
            sum[i] += sum[i - 1];

        while (q > 0) {
            q--;
            int l = in.readInt(), r = in.readInt();
            int value = sum[r - 1];

            if (l != 1)
                value -= sum[l - 2];

            if ((value & 1) == 1)
                sb.append(mishki);
            else
                sb.append(hacker);
        }

        out.print(sb);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
