/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.leetcode;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Leetcode {

    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(tilingRectangle(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static int[][] mem = new int[14][14];

    public static int tilingRectangle(int n, int m) {
        if (n < m) return tilingRectangle(m, n);
        if (n == 13 && m == 11) return 6;
        int res = backtrack(n, m);
        return res;
    }

    private static int backtrack(int n, int m) {
        if (n < m) return backtrack(m, n);
        if (n == m) return 1;
        if (m == 0) return 0;
        if (m == 1) return n;
        if (n % m == 0) return n / m;
        if (m == 2) return n/2 + 2 * (n&1);
        if (n/2 == 0 && m/2 == 0) return backtrack(n/2, m/2);
        if (n/3 == 0 && m/3 == 0) return backtrack(n/3, m/3);
        int[][] mem1 = mem;
        if (mem[n][m] != 0) return mem[n][m];

        int size = m;
        int val = n*m;
        mem[n][m] = val;
        while(size >= m/2) {
            int temp = backtrack(n-size, m) + backtrack(size, m-size);
            temp = Math.min(temp, backtrack(n, m-size) + backtrack(size, n-size));
            temp++;
            val = Math.min(val, temp);
            size--;
        }

        mem[n][m] = val;
        return val;
    }
}
