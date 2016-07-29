package com.ashok.hackerearth.marathon.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Little Shino and the tournament
 * Link: https://www.hackerearth.com/july-circuits/algorithm/little-shino-and-the-tournament/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ShinoAndTournament {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ShinoAndTournament a = new ShinoAndTournament();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] fighters = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(q << 2);
        int[] fights = new int[n];
        boolean[] check = new boolean[n]; // active in tournament
        Arrays.fill(check, true);

        boolean active = true;
        while (active) {
            active = false;

            for (int i = 0; i < n; ) {
                while (i < n && !check[i])
                    i++;

                if (i == n)
                    break;

                int j = i + 1;
                while (j < n && !check[j])
                    j++;

                if (j == n)
                    break;

                active = true;
                fights[i]++;
                fights[j]++;
                if (fighters[i] > fighters[j]) {
                    check[j] = false;
                } else {
                    check[i] = false;
                }

                i = j + 1;
            }
        }

        while (q > 0) {
            q--;

            sb.append(fights[in.readInt() - 1]).append('\n');
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
