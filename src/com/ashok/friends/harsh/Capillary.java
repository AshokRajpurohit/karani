package com.ashok.friends.harsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Capillary {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long[] fibonacciNumbers, twoPowers;
    private static final int LIMIT = 1000000 + 1, MODULO = 1000000000 + 7;

    static {
        fibonacciNumbers = new long[LIMIT];
        fibonacciNumbers[0] = 1;
        fibonacciNumbers[1] = 2;

        for (int i = 2; i < LIMIT; i++)
            fibonacciNumbers[i] = (fibonacciNumbers[i - 1] + fibonacciNumbers[i - 2]) % MODULO;

        twoPowers = new long[LIMIT];
        twoPowers[0] = 1;

        for (int i = 1; i < LIMIT; i++)
            twoPowers[i] = (twoPowers[i - 1] << 1) % MODULO;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            sb.append(process(in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long process(int n) {
        if (n == 1)
            return 2;

        long res = twoPowers[n];

        res -= fibonacciNumbers[n] - ((n + 1) >>> 1) - 1;
        n--;
        res -= fibonacciNumbers[n] - ((n + 1) >>> 1) - 1;
        res %= MODULO;

        return res >= 0 ? res : res + MODULO;
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
    }
}