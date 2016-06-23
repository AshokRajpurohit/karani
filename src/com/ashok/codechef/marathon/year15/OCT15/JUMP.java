package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * Problem: Jump mission
 * https://www.codechef.com/OCT15/problems/JUMP
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class JUMP {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] P, A, H, E;
    private static int N;
    private static long def = Long.MAX_VALUE >>> 4;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        JUMP a = new JUMP();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();


        N = in.readInt();
        P = in.readLongArray(N);
        A = in.readLongArray(N);
        H = in.readLongArray(N);

        E = new long[N];
        for (int i = 0; i < N; i++)
            E[i] = def;

        E[N - 1] = A[N - 1];
        process(0);
        out.println(E[0]);
    }

    private static void process() {
        E[0] = A[0];
        for (int i = 1; i < N; i++) {
            long temp = def;
            for (int j = 0; j < i; j++) {
                if (P[i] > P[j])
                    temp = Math.min(temp, E[j] + jumpEnergy(i, j));
            }
            E[i] = temp + A[i];
        }
    }

    private static long process(int index) {
        if (E[index] != def)
            return E[index];

        if (index == N - 1)
            return def;

        long res = def;
        for (int j = index + 1; j < N; j++) {
            if (P[j] > P[index])
                res = Math.min(res, jumpEnergy(index, j) + process(j));
        }


        E[index] = res + A[index];
        return E[index];
    }

    private static long jumpEnergy(int i, int j) {
        return (H[j] - H[i]) * (H[j] - H[i]);
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


        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
