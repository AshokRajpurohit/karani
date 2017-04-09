/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.qualification;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Problem Name: Problem C. Bathroom Stalls
 * Link: https://code.google.com/codejam/contest/3264486/dashboard#s=p2
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BathroomStalls {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path
            = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\qualification\\BathroomStallsLarge";

    public static void main(String[] args) throws IOException {
//        in = new InputReader();
//        out = new Output();
        in = new InputReader(path + ".in");
        out = new Output(path + ".out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            sb.append(CASE).append(i).append(": ");
            long n = in.readLong(), k = in.readLong();
            long time = System.currentTimeMillis();
            Pair result = process(n, k);
            sb.append(result).append('\n');
        }

        out.print(sb);
    }

    private static void test() throws IOException {
        while (true) {
            int t = in.readInt();
            long[] first = Generators.generateRandomLongArray(t, 1000000000000000000L),
                    second = Generators.generateRandomLongArray(t, 1000000000000000000L);

            for (int i = 0; i < t; i++) {
                if (first[i] >= second[i])
                    continue;

                long temp = first[i];
                first[i] = second[i];
                second[i] = temp;
            }

            long time = System.currentTimeMillis();
            for (int i = 0; i < t; i++)
                process(first[i], second[i]);

            out.println("time taken is: " + (System.currentTimeMillis() - time));
            out.flush();
        }
    }

    private static Pair process(long n, long k) {
        if (k == 1)
            return new Pair(n);

        TreeMap<Pair, Pair> map = new TreeMap<>();
        PriorityQueue<Pair> queue = new PriorityQueue<>();

        Pair temp = new Pair(n);
        map.put(temp, temp);
        queue.add(temp);

        while (true) {
            Pair top = queue.poll();
            k -= top.count;
            if (k <= 0)
                return top;

            Pair leftPairs = new Pair(top.left), rightPairs = new Pair(top.right);
            leftPairs.count = top.count;
            rightPairs.count = top.count;

            if (map.containsKey(leftPairs))
                map.get(leftPairs).count += top.count;
            else {
                map.put(leftPairs, leftPairs);
                queue.add(leftPairs);
            }

            if (map.containsKey(rightPairs))
                map.get(rightPairs).count += top.count;
            else {
                map.put(rightPairs, rightPairs);
                queue.add(rightPairs);
            }
        }
    }

    final static class Pair implements Comparable<Pair> {
        long left, right;
        long min, max, count = 1;

        Pair() {
            this(1);
        }

        Pair(long n) {
            this((n - 1) >> 1, n >> 1);
        }

        Pair(long left, long right) {
            this.left = left;
            this.right = right;
            min = Math.min(left, right);
            max = Math.max(left, right);
        }

        public void incrementCount(long c) {
            count += c;
        }

        public int hashCode() {
            return Long.hashCode(left + right);
        }

        public boolean equals(Object o) {
            if (o == null)
                return false;

            if (o == this)
                return true;

            if (!(o instanceof Pair))
                return false;

            Pair pair = (Pair) o;
            return left == pair.left && right == pair.right;
        }

        @Override
        public int compareTo(Pair pair) {
            if (min != pair.min)
                return Long.compare(pair.min, min);

            return Long.compare(pair.max, max);
        }

        public String toString() {
            return max + " " + min;
        }
    }
}
