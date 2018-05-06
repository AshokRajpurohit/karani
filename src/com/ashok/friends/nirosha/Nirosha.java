/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.nirosha;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Nirosha's problems
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Nirosha {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Nirosha a = new Nirosha();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, in.readInt(), in.readInt());
            out.print(ar);
            out.print(format(ar));
            out.flush();
        }
    }

    /**
     * Returns array with
     * @param ar
     * @return
     */
    private static int[] format(int[] ar) {
        int n = ar.length;
        int[] res = new int[n];
        Arrays.sort(ar);
        int index = 0, arStart = 0, arEnd = n - 1;
        while (index < n) {
            res[index++] = ar[arEnd--];
            if (index < n) res[index++] = ar[arStart++];
        }

        return res;
    }
}
