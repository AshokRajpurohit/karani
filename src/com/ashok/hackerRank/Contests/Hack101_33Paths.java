package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Intersecting Paths
 * https://www.hackerrank.com/contests/101hack33/challenges/intersecting-paths
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Hack101_33Paths {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Hack101_33Paths a = new Hack101_33Paths();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 1);
        int[] path = gen_path(ar);
        while (q > 0) {
            q--;
            int x = in.readInt() - 1, y = in.readInt() - 1;
            if (x > y) {
                int temp = x;
                x = y;
                y = temp;
            }

            int xh = path[x], yh = path[x];
            if (y <= yh)
                sb.append("1\n");
            else
                sb.append("0\n");
        }

        out.print(sb);
    }

    private static int[] gen_path(int[] ar) {
        if (ar.length == 1) {
            int[] res = new int[ar.length];
            res[0] = 0;
            return res;
        }

        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length - 1;
        if (ar[ar.length - 2] > ar[ar.length - 1])
            res[ar.length - 2] = ar.length - 1;
        else
            res[ar.length - 2] = ar.length - 2;

        for (int i = ar.length - 3; i >= 0; i--) {
            if (ar[i + 1] < ar[i])
                if (ar[i + 1] < ar[i + 2])
                    res[i] = res[i + 2];
                else
                    res[i] = i + 1;
            else
                res[i] = i;
        }

        return res;
    }

    public static int[] getLarger(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = -1;

        for (int i = ar.length - 2; i >= 0; i--) {
            for (int j = i + 1; j != -1; ) {
                if (ar[j] > ar[i]) {
                    res[i] = j;
                    break;
                } else {
                    j = res[j];
                    if (j == -1)
                        res[i] = j;
                }
            }
        }

        return res;
    }

    private static int[] getSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = -1;

        for (int i = ar.length - 2; i >= 0; i--) {
            for (int j = i + 1; j != -1; ) {
                if (ar[j] < ar[i]) {
                    res[i] = j;
                    break;
                } else {
                    j = res[j];
                    if (j == -1)
                        res[i] = j;
                }
            }
        }

        return res;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
