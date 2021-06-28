/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.nov20;

import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Problem Name: Magical Candy Store
 * Link: https://www.codechef.com/NOV20A/problems/CNDYGAME
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MagicalCandyStore {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int q = in.readInt();
            long[] br = in.readLongArray(q);

            out.println(process(ar, br));
        }
    }

    private static void test() throws IOException {
        while (true) {
            out.println("enter n");
            out.flush();
            int n = in.readInt(), size = n << 4;
            int[] ar = IntStream.range(1, size + 1).toArray();
            ArrayUtils.randomizeArray(ar);
            ar = Arrays.copyOfRange(ar, 0, n);
            int[] qar = Generators.generateRandomIntegerArray(size, 1, n << 6);
            long iterationValue = loopCount(ar);
            long[] map = mapToIndex(ar);
            for (int r : qar) {
                long expected = process(n, iterationValue, map, r);
                long actual = bruteForce(ar, r);
//                out.println("expected: " + expected + ", actual: " + actual);
//                out.flush();
                if (expected != actual) {
                    actual = bruteForce(ar, r);
                    expected = process(n, iterationValue, map, r);
                    map = mapToIndex(ar);
                }
            }
        }
    }

    private static String bruteForce(int[] nums, long[] queries) {
        int n = nums.length, q = queries.length;
        StringBuilder sb = new StringBuilder();
        for (long r : queries) {
            sb.append(bruteForce(nums, (int) r)).append('\n');
        }

        return sb.toString();
    }

    private static String process(int[] nums, long[] queries) {
        int n = nums.length, q = queries.length;
        if (n == 1) return process_1(nums[0], queries);
        int onePos = findIndex(nums, 1);
        if (onePos == 0) return process_0(nums, queries);
        long iterationValue = loopCount(nums);
        StringBuilder sb = new StringBuilder(q << 3);
        long[] map = mapToIndex(nums);

        for (long r : queries) {
            Long res = process(n, iterationValue, map, r);
            sb.append(res % MOD).append('\n');
        }

        return sb.toString();
    }

    private static long process(int n, long iterationValue, long[] map, long r) {
        r--;
        if (r < n) {
            return map[(int) r];
        }
        long loops = r / n;
        loops %= MOD;
        int lastIndex = (int) (r % n);
        long res = loops * iterationValue % MOD + map[lastIndex];
        return res;
    }

    private static long bruteForce(int[] nums, int r) {
        int n = nums.length;
        Pair c = new Pair();
        r--;
        int index = r % n;
        while (r >= 0) {
            int v = nums[index];
            Pair pc = c.clone();
            if (v == 1) {
                long x = c.b + 1;
                c.b = c.a;
                c.a = x;
            } else {
                int e = getEven(v), o = getOdd(v);
                if (e + c.a > o + c.b) {
                    c.a += e;
                } else if (e + c.a < o + c.b) {
                    long x = o + c.b;
                    c.b = c.a;
                    c.a = x;
                } else {
                    if (c.a > c.b) {
                        c.a += e;
                    } else {
                        long x = o + c.b;
                        c.b = c.a;
                        c.a = x;
                    }
                }
            }

            r--;
            index--;
            if (index < 0) {
                c.flip();
                index = n - 1;
            }
        }

        return c.b;
    }

    final static class Pair {
        long a, b;

        public Pair clone() {
            Pair pair = new Pair();
            pair.a = this.a;
            pair.b = this.b;

            return pair;
        }

        private void flip() {
            long temp = a;
            a = b;
            b = temp;
        }

        public String toString() {
            return a + ", " + b;
        }
    }

    private static long process(int[] nums, long r) {
        int n = nums.length;
        long iterationValue = loopCount(nums);
        long[] map = mapToIndex(nums);
        long loops = (r - 1) / n;
        loops %= MOD;
        int lastIndex = (int) (r % n);
        lastIndex = lastIndex == 0 ? n - 1 : lastIndex - 1;
        long res = loops * iterationValue % MOD + map[lastIndex];
        return res % MOD;
    }

    private static String process_1(int value, long[] queries) {
        int oddValue = getOdd(value);
        StringBuilder sb = new StringBuilder(queries.length << 3);
        for (long r : queries) {
            long loops = (r - 1) % MOD;
            long count = loops * oddValue % MOD + value;
            sb.append(count % MOD).append('\n');
        }

        return sb.toString();
    }

    private static String process_0(int[] nums, long[] queries) {
        int q = queries.length, n = nums.length;
        StringBuilder sb = new StringBuilder(q << 3);
        for (long r : queries) {
            long loops = process_0(n, r);
            sb.append(loops).append('\n');
        }

        return sb.toString();
    }

    private static long process_0(int n, long r) {
        r--;
        long loops = r / n;
        loops %= MOD;
        if (r % n != 0) loops++;
        return loops;
    }

    private static long[] mapToIndex(int[] nums) {
        int n = nums.length, onePos = findIndex(nums, 1);
        onePos = onePos == -1 || onePos == n - 1 ? n : onePos;
        long[] map = new long[n];
        map[0] = nums[0];
        if (onePos == 0) {
            Arrays.fill(map, 1);
            return map;
        }
        long sumSoFar = 0;
        for (int i = 0; i < onePos; i++) {
            map[i] = sumSoFar + nums[i];
            sumSoFar += getEven(nums[i]);
        }

        if (onePos < n) map[onePos] = Math.max(map[onePos - 1], sumSoFar + 1);
        sumSoFar += getOdd(nums[onePos - 1]) - getEven(nums[onePos - 1]);
        for (int i = onePos + 1; i < n; i++) {
            map[i] = sumSoFar + nums[i];
            sumSoFar += getEven(nums[i]);
        }

        return map;
    }

    private static long loopCount(int[] nums) {
        int n = nums.length;
        if (n == 1) return getOdd(nums[0]);
        int onePos = findIndex(nums, 1);
        if (onePos == 0) return 1;
        long res = 0;
        if (onePos == -1 || onePos == n - 1) {
            for (int i = 0; i < n - 1; i++) res += getEven(nums[i]);
            return (res + getOdd(nums[n - 1])) % MOD;
        }

        for (int i = 0; i < onePos - 1; i++) res += getEven(nums[i]);
        res += getOdd(nums[onePos - 1]);
        for (int i = onePos + 1; i < n - 1; i++) res += getEven(nums[i]);
        return (res + getOdd(nums[n - 1])) % MOD;
    }

    private static int getEven(int v) {
        return isEven(v) ? v : v - 1;
    }

    private static int getOdd(int v) {
        return isEven(v) ? v - 1 : v;
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static int findIndex(int[] ar, int v) {
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == v) return i;
        }

        return -1;
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