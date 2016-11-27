package com.ashok.hackerearth.marathon.nov16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Mike and Fraud
 * Link: https://www.hackerearth.com/november-circuits/algorithm/mike-and-fraud/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MikeAndFraud {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), k = in.readInt();
        int[] ar = in.readIntArray(n);
        out.println(countGoodArrays(ar, k));
    }

    private static long countGoodArrays(int[] ar, int k) {
        if (k == 1) {
            long res = ar.length;
            return res * (res + 1) / 2;
        }

        Pattern pattern = new Pattern(k);
        long res = 0;

        for (int i = 0, j = 0; i < ar.length; ) {
            if (pattern.match()) {
                res += ar.length - j + 1;
                pattern.remove(ar[i++]);
            } else if (j < ar.length) {
                pattern.add(ar[j++]);
            } else
                break;
        }

        return res;
    }

    private static LinkedList<Integer> getPrimeFactors(int n) {
        LinkedList<Integer> primeFactors = new LinkedList<>();
        int root = (int) Math.sqrt(n);
        int factor = 2;

        while (n != 1 && factor <= root) {
            if (n % factor == 0) {
                primeFactors.add(factor);

                while (n % factor == 0)
                    n /= factor;

                factor++;
            }
        }

        if (n != 1)
            primeFactors.add(n);

        return primeFactors;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    final static class Pattern {
        int count = 0; // numbers of prime factor completed in multiplication.
        final int k, size; // total number of prime factors
        LinkedList<Integer> primeFactors;
        LinkedList<FactorCount> factorCounts;

        Pattern(int n) {
            k = n;
            primeFactors = getPrimeFactors(n);
            factorCounts = new LinkedList<>();
            size = primeFactors.size();

            for (int primeFactor : primeFactors) {
                FactorCount primeFactorCount = new FactorCount(primeFactor);
                while (n % primeFactor == 0) {
                    primeFactorCount.decrement();
                    n /= primeFactor;
                }

                factorCounts.add(primeFactorCount);
            }
        }

        void add(int n) {
            for (FactorCount primeFactor : factorCounts) {
                if (n % primeFactor.factor != 0)
                    continue;

                while (n % primeFactor.factor == 0) {
                    n /= primeFactor.factor;
                    primeFactor.count++;

                    if (primeFactor.count == 0)
                        count++;
                }
            }
        }

        void remove(int n) {
            for (FactorCount primeFactor : factorCounts) {
                if (n % primeFactor.factor != 0)
                    continue;

                while (n % primeFactor.factor == 0) {
                    n /= primeFactor.factor;
                    primeFactor.count--;

                    if (primeFactor.count == -1)
                        count--;
                }
            }
        }

        boolean match() {
            return count == size;
        }

        public String toString() {
            return "" + (count == size);
        }
    }

    final static class FactorCount {
        final int factor;
        int count = 0;

        FactorCount(int factor) {
            this.factor = factor;
        }

        void increment() {
            count++;
        }

        void decrement() {
            count--;
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
