package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem: Duff and Weight Lifting
 * problem Link: http://codeforces.com/contest/588/problem/C
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class C326D2C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C326D2C a = new C326D2C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        Arrays.sort(ar);
        int i = 0, count = 0;
        //        while (i < n && ar[i] == 0)
        //            i++;
        //
        //        if (i == n) {
        //            out.println(1);
        //            return;
        //        }
        //
        //        int count = i > 0 ? 1 : 0;
        int p = ar[i], pi = i;
        int j = pi + 1;

        while (i < n) {
            while (j < n && ar[j] == p)
                j++;

            int dist = j - pi;
            if (j == n) {
                while (dist > 0) {
                    if ((dist & 1) == 1)
                        count++;
                    dist = dist >>> 1;
                }

                if (ar[j - 1] > ar[i])
                    count++;

                out.println(count);
                return;
            }

            if (ar[j] == p + 1) {
                if (p == ar[i] && dist < 2) {
                    p = ar[j];
                    pi = j;
                    count++;
                    i = j;
                } else {
                    dist--;
                    if (p == ar[i])
                        dist--;
                    while (dist > 0) {
                        if ((dist & 1) == 1)
                            count++;
                        dist = dist >>> 1;
                    }
                    pi = j;
                    p = ar[j];
                }
            } else {
                while (dist > 0) {
                    if ((dist & 1) == 1)
                        count++;
                    dist = dist >>> 1;
                }

                p = ar[j];
                i = j;
                pi = i;
            }
        }
        out.println(count);
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
