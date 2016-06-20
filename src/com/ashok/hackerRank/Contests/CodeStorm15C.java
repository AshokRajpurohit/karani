package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Counting Triangles
 * https://www.hackerrank.com/contests/codestorm/challenges/ilia
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CodeStorm15C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CodeStorm15C a = new CodeStorm15C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        Arrays.sort(ar);
        int[] count = new int[ar[n - 1] + 1];
        int nyoon = 0, samkon = 0, adhik = 0;

        for (int i = 0; i < n; i++)
            count[ar[i]] = 1;

        for (int i = 1; i < count.length; i++)
            count[i] += count[i - 1];

        //        print(ar);
        //        print(count);

        for (int i = 0; i < n - 2; i++)
            for (int j = i + 1; j < n - 1; j++) {
                int karna2 = ar[i] * ar[i] + ar[j] * ar[j];
                int karna = (int)Math.sqrt(karna2);
                if (karna * karna == karna2) {
                    nyoon +=
                            getCount(count, karna - 1) - getCount(count, ar[j]);
                    samkon +=
                            getCount(count, karna) - getCount(count, karna - 1);

                    adhik +=
                            getCount(count, ar[i] + ar[j] - 1) - getCount(count,
                                                                          karna);
                } else {
                    nyoon += getCount(count, karna) - getCount(count, ar[j]);
                    adhik +=
                            getCount(count, ar[i] + ar[j] - 1) - getCount(count,
                                                                          karna);
                }
            }

        out.print(nyoon + " " + samkon + " " + adhik + "\n");
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (int e : ar)
            sb.append(e).append(' ');

        System.out.println(sb);
    }

    private static int getCount(int[] count, int value) {
        if (value >= count.length)
            return count[count.length - 1];

        return count[value];
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
