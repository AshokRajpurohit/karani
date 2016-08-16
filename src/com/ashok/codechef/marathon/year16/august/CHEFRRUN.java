package com.ashok.codechef.marathon.year16.august;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Round Run
 * Link: https://www.codechef.com/AUG16/problems/CHEFRRUN
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHEFRRUN {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CHEFRRUN a = new CHEFRRUN();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            int n = in.readInt();
            sb.append(process(n, in.readIntArray(n))).append('\n');
        }

        out.print(sb);
    }

    private static int process(int n, int[] ar) {
        if (n == 1)
            return 1;

        int[] count = new int[n];
        boolean[] block = new boolean[n];
        int index = 0;
        int res = 0;

        while (index < n) {
            if (count[index] != 0) {
                index++;
                continue;
            }

            count[index] = 1;
            int j = (index + ar[index] + 1) % ar.length;

            while (count[j] != 2 && !block[j]) {
                count[j]++;
                j = (j + ar[j] + 1) % ar.length;
            }

            j = index;
            while (count[j] == 1 && !block[j]) {
                block[j] = true;
                j = (j + ar[j] + 1) % ar.length;
            }
        }

        for (int e : count)
            if (e == 2)
                res++;

        return res;
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
