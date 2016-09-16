package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem Name: Chef and Friends
 * Link: https://www.codechef.com/SEPT16/problems/CHFNFRN
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHFNFRN_Generator {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String yes = "YES\n", no = "NO\n";

    public static void main(String[] args) throws IOException {
        play();
        in.close();
        out.close();
    }

    private static void play() throws IOException {
        while (true) {
            test(in.readInt(), in.readInt());
        }
    }

    private static void generateInputs(int n, boolean[][] map) {
        int edges = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (map[i][j])
                    edges++;
            }
        }

        out.println(n + " " + edges);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (map[i][j])
                    out.println((i + 1) + " " + (j + 1));
            }
        }

        out.flush();
    }

    private static void test(int t, int n) {
        out.println(t);
        for (int i = 1; i <= t; i++) {
            Random random = new Random();
            boolean[][] map = new boolean[n][n];
            int m = n >>> 1;

            int edges = n * n;
            int edgeCount = 0;

            for (int j = 0; j < edges; j++) {
                int a = random.nextInt(n), b = random.nextInt(n);

                if (a != b) {
                    if (map[a][b])
                        continue;

                    edgeCount++;
                    map[a][b] = true;
                    map[b][a] = true;
                }
            }

            generateInputs(n, map);
        }
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
