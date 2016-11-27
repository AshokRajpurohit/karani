package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Accolite Java Developer Hiring Challenge
 * Link: https://www.hackerearth.com/accolite-java-developer-hiring-challenge/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AccoliteNov16 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        SimpleGame.solve();
//        AliceAndSequence.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    final static class AliceAndSequence {
        private static final int mod = 1000000007;

        private static void solve() throws IOException {
            int t = in.readInt();

            while (t > 0) {
                t--;

                out.println(process(in.readInt()));
            }
        }

        private static long process(int n) {
            if (n < 3)
                return 0;

            if (n == 3)
                return 6;

            long res = pow(3, n) - 3 * pow(2, n) + 3;
            res %= mod;

            return res < 0 ? res + mod : res;
        }

        public static long pow(long a, long b) {
            if (b == 0)
                return 1;

            a = a % mod;
            if (a < 0)
                a += mod;

            if (a == 1 || a == 0 || b == 1)
                return a;

            long r = Long.highestOneBit(b), res = a;

            while (r > 1) {
                r = r >> 1;
                res = (res * res) % mod;
                if ((b & r) != 0) {
                    res = (res * a) % mod;
                }
            }
            return res;
        }
    }

    final static class SimpleGame {
        private static final String monk = "Monk", notMonk = "!Monk", tie = "Tie";

        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[] a = in.readIntArray(n), b = in.readIntArray(m);

            Arrays.sort(a);
            Arrays.sort(b);

            long monkScore = score(a, b), notMonkScore = score(b, a);
            if (monkScore > notMonkScore) {
                out.println(monk + " " + (monkScore - notMonkScore));
            } else if (monkScore < notMonkScore) {
                out.println(notMonk + " " + (notMonkScore - monkScore));
            } else
                out.println(tie);
        }

        private static long score(int[] a, int[] b) {
            int smallerElementIndex = 0, largerElementIndex = 0;

            long score = 0;
            for (int e : a) {
                if (largerElementIndex == b.length)
                    return score;

                while (smallerElementIndex < b.length && b[smallerElementIndex] < e)
                    smallerElementIndex++;

                largerElementIndex = Math.max(largerElementIndex, smallerElementIndex);
                while (largerElementIndex < b.length && b[largerElementIndex] <= e)
                    largerElementIndex++;

                score += 1L * smallerElementIndex * (b.length - largerElementIndex);
            }

            return score;
        }
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
