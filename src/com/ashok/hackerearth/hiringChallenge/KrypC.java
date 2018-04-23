/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class KrypC {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        GauravAndSubArray.solve();
//        OrSum.solve();
        ArrayConversion.solve();
//        play();
        in.close();
        out.close();
    }

    private static void play() throws IOException {
        while (true) {
            int n = in.readInt(), k = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(0, 100);
            out.println(ArrayConversion.process(ar, k));
            out.flush();
        }
    }

    /**
     * Given an integer array and an integer K, find the subarray length whose sum of elements is atleast K.
     * If there is no such subarray, return -1.
     */
    final static class GauravAndSubArray {
        private static void solve() throws IOException {
            int n = in.readInt(), q = in.readInt();
            int[] ar = in.readIntArray(n);
            int[] bitCounts = generateBitCounts(ar);
            int[] bitSums = generateSumArray(bitCounts);
            while (q > 0) {
                q--;
                out.println(process(bitSums, in.readInt()));
            }
        }

        private static int process(int[] sumArray, int k) {
            if (k == 0) return 1;
            int len = sumArray.length;
            if (k > sumArray[len - 1]) return -1;
            int start = 1, end = len, mid = len >>> 1; // let's use Binary Search for length.
            while (start < end) {
                if (exists(sumArray, k, mid))
                    end = mid;
                else
                    start = mid + 1;

                mid = (start + end) >>> 1;
            }

            return end;
        }

        private static boolean exists(int[] sumArray, int k, int size) {
            if (sumArray[size - 1] >= k)
                return true;

            int len = sumArray.length;
            for (int i = 0, j = size; j < len; i++, j++)
                if (sumArray[j] - sumArray[i] >= k)
                    return true;

            return false;
        }

        private static int[] generateBitCounts(int[] ar) {
            int len = ar.length;
            int[] bitCounts = new int[len];
            for (int i = 0; i < len; i++)
                bitCounts[i] = Integer.bitCount(ar[i]);

            return bitCounts;
        }

        private static int[] generateSumArray(int[] ar) {
            int len = ar.length, sum = 0, index = 0;
            int[] sumArray = new int[len];
            for (int e : ar)
                sumArray[index++] = (sum += e);

            return sumArray;
        }
    }

    /**
     * Given an integer array, you have to give the sum of bitwise OR of all the subsets.
     * In Simple terms, for each subset, calculate the bitwise OR, and then sum it up.
     * The answer may be very large, print it modulo of 1000000007.
     */
    final static class OrSum {
        private static final int MOD = 1000000007, LIMIT = 100000;
        private static int[] bitValues = new int[32];
        private static long[] twoPowers = new long[LIMIT + 1];

        static {
            bitValues[0] = 1;
            for (int i = 1; i < 32; i++)
                bitValues[i] = bitValues[i - 1] << 1;

            twoPowers[0] = 1;
            for (int i = 1; i <= LIMIT; i++)
                twoPowers[i] = twoPowers[i - 1] * 2 % MOD;
        }

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt();
                out.println(process(in.readIntArray(n)));
            }
        }

        private static long process(int[] ar) {
            int len = ar.length;
            if (len == 1)
                return ar[0];

            int[] bitCounts = generateBitCounts(ar);
            long res = 0;
            for (int i = 0; i < 32; i++) {
                if (bitCounts[i] == 0)
                    continue;

                int bitCount = bitCounts[i];
                res = res + bitValues[i] * getBitSum(bitCount, len) % MOD;
            }

            return res % MOD;
        }

        private static long getBitSum(int bits, int total) {
            return (twoPowers[bits] - 1) * twoPowers[total - bits] % MOD;
        }

        private static int[] generateBitCounts(int[] ar) {
            int[] bitCounts = new int[32];
            for (int e : ar)
                populateBitCounts(bitCounts, e);

            return bitCounts;
        }

        private static void populateBitCounts(int[] bitCounts, int num) {
            int index = 0;
            while (num != 0) {
                if ((num & 1) == 1)
                    bitCounts[index]++;

                index++;
                num >>>= 1;
            }
        }
    }

    /**
     * Given an array and an integer K. You can perform one of three operation on
     * array elements
     * <p>
     * 1. increment it. (i.e. e = e + 1)
     * <p>
     * 2. decrement it. (i.e. e = e - 1)
     * <p>
     * 3. make it negative. (i.e. e = -e)
     * <p>
     * You can perform more than one operation on any element.
     * Now you need to convert the given array into different array such that the
     * the sum of all elements is equal to K.
     */
    final static class ArrayConversion {
        private static final String YES = "YES", NO = "NO";
        private int[] ar, minArray, maxArray, sumArray;
        private int[][] map;// -1 as No, +1 as Yes and 0 as un-processed.
        private final int offset;

        private ArrayConversion(int[] ar) {
            this.ar = ar;
            this.map = map;
            minArray = getMinPossibleArray(ar);
            maxArray = getMaxPossibleArray(ar);
            sumArray = getSumArray(ar);
            int len = ar.length, min = minArray[len - 1], max = maxArray[len - 1];
            map = new int[len][max + 1 - min];
            offset = -min;
        }

        private static int[] getMinPossibleArray(int[] ar) {
            int index = 0, sum = 0, len = ar.length;
            int[] minArray = new int[len];
            for (int e : ar)
                minArray[index++] = sum += Math.min(-e, e - 1 - index);

            return minArray;
        }

        private static int[] getMaxPossibleArray(int[] ar) {
            int index = 0, sum = 0, len = ar.length;
            int[] maxArray = new int[len];
            for (int e : ar)
                maxArray[index++] = sum += e + 1 + index;

            return maxArray;
        }

        private static int[] getSumArray(int[] ar) {
            int len = ar.length, index = 0, sum = 0;
            int[] sumArray = new int[len];
            for (int e : ar)
                sumArray[index++] = sum += e;

            return sumArray;
        }

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt(), k = in.readInt();
                int[] ar = in.readIntArray(n);
                out.println(process(ar, k) ? YES : NO);
            }
        }

        private static boolean process(int[] ar, int sum) {
            int len = ar.length, min = minimumPossible(ar), total = sum(ar), max = total + len;
            if (sum == min || sum == total || sum == max)
                return true;

            if (sum < min || sum > max)
                return false;

            ArrayConversion conversion = new ArrayConversion(ar);
            return conversion.process(sum);
        }

        private boolean process(int sum) {
            return process(ar.length - 1, sum);
        }

        private boolean process(int index, int sum) {
            if (index < 0)
                return sum == 0;

            if (sum < minArray[index] || maxArray[index] < sum) // no need to check further.
                return false;

            if (minArray[index] == sum || maxArray[index] == sum || sumArray[index] == sum)
                return true;

            if (map[index][sum + offset] != 0)
                return map[index][sum + offset] == 1;

            boolean result = process(index - 1, sum + ar[index]) // if we make this element negative.
                    || process(index - 1, sum - 1 - ar[index]) // if we increment this element.
                    || process(index - 1, sum + 1 - ar[index]) // if we decrement this element.
                    || process(index - 1, sum - ar[index]); // if we don't change the element at all.

            map[index][sum + offset] = result ? 1 : -1;
            return result;
        }

        private static int minimumPossible(int[] ar) {
            int sum = 0;
            for (int e : ar)
                sum += Math.min(-e, e - 1);

            return sum;
        }

        private static int sum(int[] ar) {
            int sum = 0;
            for (int e : ar)
                sum += e;

            return sum;
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