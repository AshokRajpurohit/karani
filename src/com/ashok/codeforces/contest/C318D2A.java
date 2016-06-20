package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * @author: Ashok Rajpurohit
 * problem: Bear and Elections
 * http://codeforces.com/contest/574/problem/0
 */

public class C318D2A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C318D2A a = new C318D2A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        out.println(process(ar));
    }

    private static int process(int[] ar) {
        int max = 0, smax = 0, bribe = 0, vote = ar[0];
        int[] newar = new int[ar.length - 1];
        for (int i = 1; i < ar.length; i++)
            newar[i - 1] = ar[i];
        Arrays.sort(newar);
        for (int i = newar.length - 1; i >= 0; i--) {
            if (vote > newar[i])
                return bribe;

            //            bribe += ((newar[i] - vote) >>> 1) + 1;

            int count = 1;
            while (i >= 1 && newar[i] == newar[i - 1]) {
                i--;
                count++;
            }
            int temp = Integer.MAX_VALUE;
            if (i > 0)
                temp = newar[i] - vote + 1;

            bribe += Math.min(temp, count * ((newar[i] - vote) / count));

            //            bribe +=
            //                    Math.min(newar[i] - vote + 1, ((newar[i] * count - vote + count +
            //                                                    1) / (count + 1)));
            vote = ar[0] + bribe;
        }

        return bribe;
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
