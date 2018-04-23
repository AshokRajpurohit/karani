/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.nikhil;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Nikhil {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Nikhil a = new Nikhil();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            String dna = in.read();
            String rule = in.read();
            int depth = in.readInt();
            out.println(Class5.synthesize(dna, rule, depth));
            out.flush();
        }
    }

    static class RecursionCallCheck {
        static int j = 0;

        public static void add(int i) {
            if (i > 0) {
                System.out.println(--i);
                add(i);

                System.out.println("after add" + i);
            }
        }

        public static void main(String[] args) {
            RecursionCallCheck.add(3);
        }

    }
}
