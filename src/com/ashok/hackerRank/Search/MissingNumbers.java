package com.ashok.hackerRank.Search;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Missing Numbers
 * https://www.hackerrank.com/challenges/missing-numbers
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class MissingNumbers {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MissingNumbers a = new MissingNumbers();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] a = in.readIntArray(n);

        int m = in.readInt();
        int[] b = in.readIntArray(m);

        int min = Integer.MAX_VALUE, max = 0;
        for (int e : b) {
            min = Math.min(min, e);
            max = Math.max(max, e);
        }

        int length = max - min + 1;
        int[] hashB = new int[length];
        for (int e : b) {
            hashB[e - min]++;
        }

        int[] hashA = new int[length];
        for (int e : a)
            hashA[e - min]++;

        StringBuilder sb = new StringBuilder((m - n) << 2);
        for (int i = 0; i < length; i++)
            if (hashB[i] != hashA[i])
                sb.append(i + min).append(' ');

        out.println(sb);
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
