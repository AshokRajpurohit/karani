package com.ashok.codechef.marathon.year16.dec16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * Problem Name: Kirito in labyrinth
 * Link: https://www.codechef.com/DEC16/problems/KIRLAB
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class KIRLAB {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int limit = 10000000;
    private static int[] largestFactor, factorCount;
    private static boolean[] primeCheck;
    private static CountDownLatch startGate = new CountDownLatch(1);

    /**
     * Populates {@link #primeCheck}, {@link #largestFactor} and
     * {@link #factorCount} arrays for computation.
     */
    private static void populate() {
        largestFactor = new int[limit + 1];
        factorCount = new int[limit + 1];
        primeCheck = new boolean[limit + 1];
        Arrays.fill(primeCheck, true);
        int root = limit >>> 1;

        for (int i = 2; i <= root; i++) {
            if (primeCheck[i]) {
                for (int j = i; j <= limit; j += i) {
                    primeCheck[j] = false;
                    largestFactor[j] = i;
                    factorCount[j]++;
                }

                primeCheck[i] = true;
            }
        }

        for (int i = root + 1; i <= limit; i++) {
            if (primeCheck[i]) {
                largestFactor[i] = i;
                factorCount[i]++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Thread populateThread = new Thread(new Populator());
        populateThread.start(); // start preprocess (populate) in seperate thread.
        try {
            solve();
        } catch (InterruptedException e) {
            populateThread.interrupt();
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        in.close();
        out.close();
    }

    private static void solve() throws IOException, InterruptedException {
        int t = in.readInt();
        int[][] testData = new int[t][];
        for (int i = 0; i < t; i++) {
            int n = in.readInt();
            testData[i] = in.readIntArray(n);
        }

        process(testData);
    }

    private static void process(int[][] testData) throws InterruptedException {
        startGate.await(); // let's wait for populate to complete.

        for (int[] ar : testData)
            out.println(process(ar));
    }

    /**
     * Returns the max length of any sequence where gcd for any two consecutive
     * integers is more than 1.
     *
     * @param ar
     * @return
     */
    private static int process(int[] ar) {
        if (ar.length == 1)
            return 1;

        int max = maxInArray(ar);
        if (max == 1)
            return 1;

        int[] map = new int[max + 1];

        for (int e : ar)
            populateMap(e, map);

        return maxInArray(map);
    }

    /**
     * updates the map for n's prime factors. For each prime factor,
     * the path length is max for any prime factor plus one.
     * consider this value as a node in path which provides, a set
     * of prime factors for gcd to cross it.
     *
     * @param n
     * @param map
     */
    private static void populateMap(int n, int[] map) {
        if (n == 1)
            return;

        if (factorCount[n] == 1) {
            map[largestFactor[n]]++;
            return;
        }

        int[] factors = getFactors(n);
        int maxCountForFactors = 0;

        for (int e : factors)
            maxCountForFactors = Math.max(maxCountForFactors, map[e]);

        maxCountForFactors++; // new value for all factors via this node.
        for (int e : factors)
            map[e] = maxCountForFactors;
    }

    /**
     * Returns array of prime factors for this number in decresing order.
     *
     * @param n
     * @return array of prime factors.
     */
    private static int[] getFactors(int n) {
        int[] facotrs = new int[factorCount[n]];
        int index = 0;

        while (n > 1) {
            int factor = largestFactor[n];
            facotrs[index++] = factor;

            while (n % factor == 0)
                n /= factor;
        }

        return facotrs;
    }

    private static int maxInArray(int[] ar) {
        int max = ar[0];

        for (int e : ar)
            max = max < e ? e : max;

        return max;
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

    /**
     * Runs {@link #populate()} in seperate thread while we read input data.
     */
    final static class Populator implements Runnable {
        @Override
        public void run() {
            populate();
            startGate.countDown();
        }
    }
}
