package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Count Subarrays
 * https://www.codechef.com/OCT15/problems/SUBINC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class SUBINC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SUBINC a = new SUBINC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt();
            sb.append(process(in.readIntArray(n))).append('\n');
        }

        out.print(sb);
    }

    private static long process(int[] ar) {
        int temp = ar[0];
        ar[0] = 1;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] >= temp) {
                temp = ar[i];
                ar[i] = ar[i - 1] + 1;
            } else {
                temp = ar[i];
                ar[i] = 1;
            }
        }

        long res = 0;
        for (int i = 0; i < ar.length; i++)
            res += ar[i];

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
