/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.sept2020;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem Name: Find XOR
 * Link: https://www.codechef.com/SEPT20A/problems/FINXOR
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FindXOR {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 1 << 20;

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        Grader grader = new CodeChefGrader();
        while (t > 0) {
            t--;
            int n = in.readInt();
            if (!process(n, grader)) break;
        }
    }

    private static void test() throws IOException {
        Random random = new Random();
        while (true) {
            int n = random.nextInt(10) + 5;
            int[] nums = Generators.generateRandomIntegerArray(n, 1, 1000000);
            Grader grader = new TestingGrader(nums);
            boolean result = process(n, grader);
            if (!result) {
                result = process(n, grader);
            }

            out.println(result);
            out.flush();
        }
    }

    private static boolean process(int n, Grader grader) {
        int r = 2, xorSum = 0, sum = getSum(n, grader);
        if ((sum & 1) == 1) xorSum = 1;
        for (int i = 0; i < 19 && r <= sum; i++) {
            int diff = sum - grader.query(r);
            int x2 = diff / r + n;
            int count = x2 >>> 1; // set bits count at the same level as r
            if ((count & 1) == 1) xorSum |= r;
            r <<= 1;
        }

        return grader.submit(xorSum);
    }

    private static int getSum(int n, Grader grader) {
        return grader.query(LIMIT) - n * LIMIT;
    }

    interface Grader {
        int query(int k);

        boolean submit(int ans);
    }

    final static class CodeChefGrader implements Grader {

        @Override
        public int query(int k) {
            out.println("1 " + k);
            out.flush();
            try {
                return in.readInt();
            } catch (IOException e) {
                return -1;
            }
        }

        @Override
        public boolean submit(int ans) {
            out.println("2 " + ans);
            out.flush();
            try {
                return in.readInt() == 1;
            } catch (IOException e) {
                return false;
            }
        }
    }

    final static class TestingGrader implements Grader {
        private int[] nums;
        private final int xorSum;
        private int count = 0;

        TestingGrader(final int[] nums) {
            this.nums = nums;
            int res = 0;
            for (int e : nums) res = res ^ e;
            xorSum = res;
        }

        @Override
        public int query(int k) {
            count++;
            if (count > 20) throw new RuntimeException("count exceeded");
            int v = 0;
            for (int e : nums) v += k ^ e;
            return v;
        }

        @Override
        public boolean submit(int ans) {
            out.println("expected: " + xorSum + ", actual: " + ans);
            out.flush();
            return xorSum == ans;
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