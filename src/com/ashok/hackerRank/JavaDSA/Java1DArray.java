package com.ashok.hackerRank.JavaDSA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Java | Data Structures | Java 1D Array
 * https://www.hackerrank.com/challenges/java-1d-array
 */

public class Java1DArray {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Java1DArray a = new Java1DArray();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            if (solve(m, in.readIntArray(n)))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean solve(int m, int[] ar) {
        if (ar[0] == 1)
            return false;

        if (m >= ar.length)
            return true;

        int index = 0;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] == 0) {
                if (i - index > m)
                    return false;
                index = i;
            }
        }
        boolean[] possible = new boolean[ar.length];
        process(m, ar, possible);
        return possible[0];
    }

    private static void process(int m, int[] ref, boolean[] ar) {
        for (int i = ar.length - m; i < ar.length; i++)
            ar[i] = ref[i] == 0;

        for (int i = ar.length - m; i < ar.length; i++)
            if (ar[i])
                process(m, ref, ar, i);
    }

    private static void process(int m, int[] ref, boolean[] ar, int index) {
        if (index == 0)
            return;

        if (ref[index - 1] == 0 && !ar[index - 1]) {
            ar[index - 1] = true;
            process(m, ref, ar, index - 1);
        }

        if (index < ar.length - 1 && ref[index + 1] == 0 && !ar[index + 1]) {
            ar[index + 1] = true;
            process(m, ref, ar, index + 1);
        }

        if (index - m >= 0 && ref[index - m] == 0 && !ar[index - m]) {
            ar[index - m] = true;
            process(m, ref, ar, index - m);
        }
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
