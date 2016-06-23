package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Criminals: Little Deepu and Little Kuldeep
 * https://www.hackerearth.com/problem/algorithm/criminals-little-deepu-and-little-kuldeep/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Criminals {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Criminals a = new Criminals();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            Arrays.sort(ar);

            int max = 1, cur = 1;
            for (int i = 1; i < n; i++) {
                if (ar[i] == ar[i - 1])
                    cur++;
                else {
                    max = Math.max(cur, max);
                    cur = 1;
                }
            }

            sb.append(Math.max(max, cur)).append('\n');
        }
        out.print(sb);
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
