/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Problem Name: Partition the numbers
 * Link: https://www.codechef.com/JAN18/problems/PRTITION
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PartitionNumbers {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String IMPOSSIBLE = "impossible";
    private static final char FIRST = '0', SECOND = '1', EXCLUDE = '2';
    private static final int LIMIT = 1000000;
    private static final long[] sum = new long[LIMIT + 1];

    static {
        for (int i = 1; i <= LIMIT; i++)
            sum[i] = i + sum[i - 1];
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private static String process(int exclude, int n) {
        if (n < 4 || !isEven(sum[n] - exclude)) return IMPOSSIBLE;

        long s = sum[n];
        long half = (s + 1) >>> 1;
        Bucket bucket = new Bucket(n);
        int index = 1;

        for (int i = 1; i <= n; i++)
            if (i != exclude) bucket.exclude(i);

        while (index <= n && bucket.includeSum() <= bucket.excludeSum()) {
            if (index != exclude) bucket.include(index);
            index++;
        }

        index--;
        bucket.exclude(index);
/*
        if (bucket.includeSum() != half) {
            bucket.exclude(index);
        }

        while (index < n) {
            bucket.exclude(++index);
        }

        bucket.remove(exclude);*/
        long diff = bucket.excludeSum() - bucket.includeSum();

        int num = (int) (diff / 2);
        num = Math.abs(num);

        if (diff < 0 && bucket.include.contains(num))
            bucket.exclude(num);
        else if (diff > 0 && bucket.exclude.contains(num))
            bucket.include(num);
        else {
            num = (int) (diff / 2);
            LinkedList<Integer> list = findDiffNumsList(bucket.include, bucket.exclude, num);
            for (int e : list)
                bucket.exclude(e);

            if (!list.isEmpty())
                bucket.include(list.getLast());
        }

        diff = bucket.excludeSum() - bucket.includeSum();
        if (diff != 0)
            return IMPOSSIBLE;

        char[] result = new char[n];
        populate(result, bucket.include, FIRST);
        populate(result, bucket.exclude, SECOND);
        result[exclude - 1] = EXCLUDE;
        return new String(result);
    }

    private static LinkedList<Integer> findDiffNumsList(Set<Integer> nums1, Set<Integer> nums2, int diff) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int e : nums1) {
            int f = e + diff;
            if (nums2.contains(f)) {
                list.add(e);
                list.add(f);
                break;
            }
        }

        if (list.isEmpty())
            list = findDiffNumsList2(nums1, nums2, diff);

        return list;
    }

    private static LinkedList<Integer> findDiffNumsList2(Set<Integer> nums1, Set<Integer> nums2, int diff) {
        LinkedList<Integer> list = new LinkedList<>();
        int d = isEven(Math.abs(diff)) ? 2 : 1;
        for (int e : nums1) {
            int e1 = e + d;
            if (nums1.contains(e1)) {
                int f = e + e1 + diff;
                if (nums2.contains(f)) {
                    list.add(e);
                    list.add(e1);
                    list.add(f);
                    break;
                }
            }
        }

        if (list.isEmpty())
            list = findDiffNumsList3(nums1, nums2, diff);

        return list;
    }

    private static LinkedList<Integer> findDiffNumsList3(Set<Integer> nums1, Set<Integer> nums2, int diff) {
        LinkedList<Integer> list = new LinkedList<>();
        int da = 1, db = 2;
        int m = diff % 3;
        if (m < 0) m += 3;

        if (m != 1) db++;
        if (m == 2) da++;

        for (int e : nums1) {
            int e1 = e + da, e2 = e + db;
            if (nums1.contains(e1) && nums1.contains(e2)) {
                int f = e + e1 + e2;
                if (nums2.contains(f)) {
                    list.add(e);
                    list.add(e1);
                    list.add(e2);
                    list.add(f);
                    break;
                }
            }
        }

        return list;
    }

    private static void populate(char[] map, Set<Integer> nums, char value) {
        for (int e : nums)
            map[e - 1] = value;
    }

    private static long sum(long n) {
        return (n * (n + 1)) >>> 1;
    }

    private static boolean isEven(long n) {
        return (n & 1) == 0;
    }

    final static class Bucket {
        final int n;
        CustomSet include = new CustomSet(), exclude = new CustomSet();

        Bucket(int n) {
            this.n = n;
        }

        private void remove(int n) {
            include.remove(n);
            exclude.remove(n);
        }

        private void include(int n) {
            include.add(n);
            exclude.remove(n);
        }

        private void exclude(int n) {
            include.remove(n);
            exclude.add(n);
        }

        private long includeSum() {
            return include.sum;
        }

        private long excludeSum() {
            return exclude.sum;
        }
    }

    final static class CustomSet extends HashSet<Integer> {
        private long sum = 0;

        void add(int n) {
            if (contains(n)) return;

            sum += n;
            super.add(n);
        }

        void remove(int n) {
            if (!contains(n)) return;

            sum -= n;
            super.remove(n);
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