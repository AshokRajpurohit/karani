package com.ashok.codechef.IPC15P2B;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Shil and LIS
 * https://www.codechef.com/IPC15P2B/problems/LISBYONE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class LISBYONE {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LISBYONE a = new LISBYONE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            out.println(process(in.readIntArray(n), k));
        }
    }

    private static int process(int[] ar, int k) {
        int n = ar.length;
        int[] index = new int[n];
        index[0] = -1;
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            while (j != -1 && ar[j] >= ar[i])
                j = index[j];

            index[i] = j;
        }

        int[] len = new int[n];
        len[0] = 1;

        for (int i = 1; i < n; i++) {
            if (index[i] != -1)
                len[i] = len[index[i]] + 1;
            else
                index[i] = 1;
        }

        int max = 1;
        for (int i = 1; i < n; i++)
            max = Math.max(len[i], max);

        int res = 0;

        for (int i = 0; i < n; i++) {
            if (len[i] == max) {
                int j = i;
                if (k > ar[i])
                    res += k - ar[i];

                int p = j;

                while (j != -1 && ar[j] >= k) {
                    p = j;
                    j = index[j];
                }

                if (j != -1 && ar[j] < k) {
                    if (ar[p] == k)
                        k--;

                    res += k - ar[j];
                }

                while (j != -1) {
                    p = j;
                    j = index[j];

                    if (j != -1)
                        res += ar[p] - ar[j] - 1;
                }

                res += ar[p] - 1;
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
