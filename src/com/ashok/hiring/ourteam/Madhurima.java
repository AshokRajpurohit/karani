/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.ourteam;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Madhurima Webex Interview
 * Date: 11th June 2019
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Madhurima {
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
        ArraySum arraySum = new ArraySum(in.readIntArray(in.readInt()));
        while (true) {
            out.println(arraySum.query(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    final static class ArraySum {
        private final int[] ar;

        ArraySum(int[] ar) {
            this.ar = ar.clone();
        }

        // query(i, j);
        public int query(int from, int to) {
            return -1;
        }
    }
}
