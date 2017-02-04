/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Lazy Loading
 * Link: https://www.facebook.com/hackercup/problem/169401886867367/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LazyLoading {
    private static Output out;
    private static InputReader in;
    private static final String CASE = "Case #";
    private static final int MIN_WEIGHT = 50;

    public static void main(String[] args) throws IOException {
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\";
        in = new InputReader(path + "LazyLoading.in");
        out = new Output(path + "LazyLoading.out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);

            append(sb, i, process(ar));
        }

        out.print(sb);
    }

    private static int process(int[] weights) {
        Arrays.sort(weights);

        int trips = 0, start = 0, end = weights.length - 1;
        int total = sum(weights);
        int count = weights.length;

        while (end >= 0 && count * weights[end] >= MIN_WEIGHT) {
            trips++;
            count--;

            int top = weights[end--], weight = top;
            while (weight < MIN_WEIGHT) {
                count--;
                weight += top;
                start++;
            }

            total -= weight;
        }

        return trips;
    }

    private static int sum(int[] ar) {
        int sum = 0;

        for (int e : ar)
            sum += e;

        return sum;
    }

    private static void append(StringBuilder sb, int test, int trips) {
        sb.append(CASE).append(test).append(": ").append(trips).append('\n');
    }
}
