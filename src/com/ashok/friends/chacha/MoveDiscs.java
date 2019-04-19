/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.chacha;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MoveDiscs {
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
            out.println(countSteps(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static int countSteps(int[] discs) {
        final int len = discs.length;
        int[] indexMap = new int[len + 1];
        IntStream.range(0, len).forEach(i -> indexMap[discs[i]] = i);
        boolean[] shifted = new boolean[len + 1];

        return IntStream.range(2, discs.length + 1).map(i -> {
            int index = indexMap[i];
            int smaller = indexMap[i - 1];
            if (index > smaller || shifted[i - 1]) {
                shifted[i] = true;
                return 1;
            }
            return 0;
        }).sum();
    }
}
