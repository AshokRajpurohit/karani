/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Problem Name: Priyanka
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class OnsiteTest {
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
            out.println(in.read());
            out.flush();
        }
    }

    private static String athens(int n) {
        char[] chars = "ATHENS".toCharArray();
        int[] baseDigits = convert(n, 6);
        StringBuilder sb = new StringBuilder();
        for (int d : baseDigits) {
            sb.append(chars[d]);
        }

        return sb.toString();
    }

    private static int[] convert(int n, int base) {
        Deque<Integer> stack = new LinkedList<>();
        while (n >= base) {
            stack.push(n % base);
            n /= base;
        }

        stack.push(n);
        return stack.stream().mapToInt(i -> i).toArray();
    }
}
