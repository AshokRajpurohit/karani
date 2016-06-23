package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and math
 * https://www.codechef.com/MAY16/problems/CHEFMATH
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFMATH {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] chefonacci;
    private static final int mod = 1000000007, LEN = 44;

    static {
        chefonacci = new long[LEN];

        chefonacci[0] = 1;
        chefonacci[1] = 2;

        for (int i = 2; i < LEN; i++)
            chefonacci[i] = chefonacci[i - 1] + chefonacci[i - 2];
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFMATH a = new CHEFMATH();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(solve(in.readInt(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long solve(final int x, final int k) {
        if (k == 1 && contains(x))
            return 1;

        if (x == 1)
            return 0;

        int index = LEN - 1;

        while (chefonacci[index] > x)
            index--;

        return solve(x, k, index);
    }

    private static long solve(long x, int k, int index) {
        if (x == 0 || k == 0 || index < 0 || x < k ||
            x > chefonacci[index] * k)
            return 0;

        if (x == chefonacci[index] * k || x == k ||
            (k == 1 && contains(x, index)))
            return 1;

        while (index >= 0 && chefonacci[index] > x)
            index--;

        long res = solve(x, k, index - 1);
        x -= chefonacci[index];

        while (x > 0) {
            k--;
            res += solve(x, k, index - 1);
            x -= chefonacci[index];
        }

        return res % mod;
    }

    private static boolean contains(long x, int index) {
        while (index >= 0 && chefonacci[index] > x)
            index--;

        if (index < 0)
            return false;

        return chefonacci[index] == x;
    }

    private static boolean contains(long x) {
        int i = 0;
        while (chefonacci[i] < x)
            i++;

        return chefonacci[i] == x;
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
