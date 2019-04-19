/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jan19;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.ModularArithmatic;
import com.ashok.lang.utils.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Yet Another Problem About Sequences
 * Link: https://www.codechef.com/JAN19A/problems/EARTSEQ
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SequenceProblem {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int PRIME_COUNT = 1913, LIMIT = 50000;
    private static int[] primes;
    private static int[] primeSeries;

    static {
        populate();
    }

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void populate() {
        primes = generatePrimes(PRIME_COUNT); // magic number :)
        populatePrimeSeries();
    }

    private static void populatePrimeSeries() {
        primeSeries = new int[LIMIT];
        int index = 0, diff = 1, len = primes.length;
        while (index < LIMIT) {
            int counter = len;
            for (int j = 0; index < LIMIT && counter > 0; j += diff, counter--) {
                if (j >= len) j -= len;
                primeSeries[index++] = primes[j];
            }

            diff++;
        }
    }

    private static void play() throws IOException {
        Output out = new Output();
        while (true) {
            out.println("Enter max prime value");
            out.flush();
            int[] ar = generatePrimes(in.readInt());
            out.println(ar.length + " " + ar[ar.length - 1]);
            out.flush();
            int len = ar.length;
            while (Arrays.binarySearch(ar, len--) < 0) ;
            out.println("prime length: " + (++len));
            out.println(ar[len - 1]);
            out.println("you want to use the same series as base? enter zero for No, if yes, please enter the LIMIT value also.");
            out.flush();
            if (in.readInt() != 0) {
                LIMIT = in.readInt();
                primes = ar;
                populatePrimeSeries();
            }

            out.println("you want to run test cases? enter zero for No");
            out.flush();
            if (in.readInt() != 0) {
                int t = 3;
                while (t <= LIMIT) {
                    int n = t;
                    int[] res = process(n);
                    try {
                        validate(res);
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.println("size: " + res.length);
                        out.print(res);
                        out.print(primeSeries);
                        out.flush();
                        process(n);
                        break;
                    }
                    t++;
                }
            }
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000);
        while (t > 0) {
            t--;
            for (int e : process(in.readInt())) sb.append(e).append(' ');
            sb.append('\n');
        }

        out.print(sb);
    }

    private static void validate(int[] ar) {
        int min = ArrayUtils.min(ar), max = ArrayUtils.max(ar), len = ar.length;
        if (min <= 1 && max >= 1000000000)
            throw new RuntimeException("invalid values min and max: " + min + ", " + max);

        if (ModularArithmatic.gcd(ar[0], ar[len - 1]) == 1)
            throw new RuntimeException("two consecutive elements should not be coprime, " + ar[len - 1] + " " + ar[0]);

        for (int i = 1; i < len; i++) {
            if (ModularArithmatic.gcd(ar[i], ar[i - 1]) == 1)
                throw new RuntimeException("two consecutive elements should not be coprime, " + ar[i - 1] + " " + ar[i]);
        }

        for (int i = 2; i < len; i++) {
            if (ModularArithmatic.gcd(ar[i - 2], ModularArithmatic.gcd(ar[i - 1], ar[i])) != 1)
                throw new RuntimeException("three element triplet should be coprime, " + ar[i - 2] + " " + ar[i - 1] + " " + ar[i]);
        }

        if (ModularArithmatic.gcd(ar[0], ModularArithmatic.gcd(ar[len - 1], ar[1])) != 1)
            throw new RuntimeException("three element triplet should be coprime, " + ar[len - 1] + " " + ar[0] + " " + ar[1]);

        if (ModularArithmatic.gcd(ar[0], ModularArithmatic.gcd(ar[len - 1], ar[len - 2])) != 1)
            throw new RuntimeException("three element triplet should be coprime, " + ar[len - 2] + " " + ar[len - 1] + " " + ar[0]);

        validateForDistinct(ar.clone());
    }

    private static void validateForDistinct(int[] ar) {
        Arrays.sort(ar);
        for (int i = 1; i < ar.length; i++) if (ar[i] == ar[i - 1]) throw new RuntimeException("Duplicate elements");
    }

    private static int[] process(int n) {
        int[] ar = new int[n];
        System.arraycopy(primeSeries, 0, ar, 0, n);
        int len = primes.length;
        if (n > len) {
            int diff = 1 + (n - 1) / len;
            int secondLastIndex = Arrays.binarySearch(primes, ar[n - 2]), lastIndex = secondLastIndex + diff;
            if (lastIndex >= len) lastIndex -= len;
            if (secondLastIndex == 0) {
                secondLastIndex = 1;
                lastIndex = diff + 2;
            } else if (lastIndex < secondLastIndex || lastIndex > len - diff) lastIndex = diff + 1;
            ar[n - 2] = primes[secondLastIndex];
            ar[n - 1] = primes[lastIndex];
        }

        generateCyclicSeries(ar);
        return ar;
    }

    private static void generateCyclicSeries(int[] ar) {
        int first = ar[0], len = ar.length;
        for (int i = 0; i < len - 1; i++) ar[i] *= ar[i + 1];
        ar[len - 1] *= first;
    }

    private static int[] generatePrimes(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }

    private static void normalize(int[] ar) {
        int[] br = ar.clone();
        int len = ar.length, index = 0;
        for (int s = 0, e = len - 1; s < e; s++, e--) {
            ar[index++] = br[s];
            ar[index++] = br[e];
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
    }
}