package com.ashok.hackerRank.stack;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Waiter
 * Link: https://www.hackerrank.com/challenges/waiter
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Waiter {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static boolean[] primes = new boolean[10000];

    static {
        Arrays.fill(primes, true);
        for (int i = 2; i < primes.length; i++) {
            if (!primes[i])
                continue;

            for (int j = i + i; j < primes.length; j += i)
                primes[j] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] plates = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(n << 2);
        LinkedList<Integer> list = new LinkedList<>(), rem = new LinkedList<>();
        for (int e : plates)
            rem.add(e);

        int prime = 2;
        while (q > 0 && rem.size() > 0) {
            while (!primes[prime])
                prime++;

            LinkedList<Integer> temp = new LinkedList<>();
            for (int e : rem) {
                if (e % prime == 0)
                    list.add(e);
                else
                    temp.addFirst(e);
            }

            q--;
            prime++;
            rem = temp;
        }

        for (int e : list)
            sb.append(e).append('\n');

        while (rem.size() != 0)
            sb.append(rem.removeLast()).append('\n');

        out.print(sb);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
