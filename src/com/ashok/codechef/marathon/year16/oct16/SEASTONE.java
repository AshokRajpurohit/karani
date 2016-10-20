package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Sereja and Stones
 * Link: https://www.codechef.com/OCT16/problems/SEASTONE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SEASTONE {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int[] energies = in.readIntArray(n);

            out.println(process(energies, m));
        }
    }

    private static long process(int[] energies, int stones) {
        int boxes = energies.length;
        if (stones % boxes == 0)
            return 0;

        Arrays.sort(energies);
        if (stones < boxes) {
            long res = 0;

            for (int i = 0; i < boxes - stones; i++)
                res += energies[i];

            return res;
        }

        long[] sums = new long[boxes];
        sums[0] = energies[0];

        for (int i = 1; i < boxes; i++)
            sums[i] = sums[i - 1] + energies[i];

        long res = sums[boxes - 1];
        for (int i = 1, j = boxes - 1; i < boxes; i++, j--) {
            if (sums[i - 1] * j >= res)
                continue;

            int boxCount = stones / j, remaining = stones % j;
            if (remaining == 0)
                res = Math.min(res, sums[i - 1] * j);

            if (divisionPossible(i, energies.length - i, stones)) {
                res = Math.min(res, sums[i - 1] * j);
            }

            while (remaining / i < boxCount) {

                if (sums[i - 1] * j >= res)
                    break;

                long temp = process(sums, i - 1, remaining);
                temp += sums[i - 1] * j;

                res = Math.min(res, temp);

                boxCount--;
                remaining += j;
            }
        }

        return res;
    }

    private static long process(long[] sum, int index, int stones) {
        int length = index + 1;

        if (stones % length == 0)
            return 0;

        if (index == 1)
            return sum[0];

        long res = sum[index - 1];

        for (int i = 1, j = length - 1; i < length; i++, j--) {
            if (sum[i - 1] * j >= res)
                continue;

            int boxCount = stones / j, remaining = stones % j;
            if (remaining == 0)
                res = Math.min(res, sum[i - 1] * j);

            if (divisionPossible(i, length - i, stones)) {
                res = Math.min(res, sum[i - 1] * j);
            }

            while (remaining / i < boxCount) {
                if (sum[i - 1] * j >= res)
                    break;

                long temp = process(sum, i - 1, remaining);
                temp += sum[i - 1] * j;

                res = Math.min(res, temp);
                boxCount--;
                remaining += j;
            }
        }

        return res;
    }

    private static boolean divisionPossible(int first, int second, int stones) {
        if (stones % (gcd(first, second)) != 0)
            return false;


        for (int i = 0, k = 0; i < stones; i += first, k++) {
            int j = (stones - i) / second;
            if (j < k)
                return false;

            if ((stones - i) % second == 0)
                return true;
        }

        return true;
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
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
