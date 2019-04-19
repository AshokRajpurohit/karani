/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.reflektion;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ReflektionFirstRound {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ReflektionFirstRound a = new ReflektionFirstRound();
        try {
            a.solve();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException, InterruptedException {
        while (true) {
            String n1 = in.read(), n2 = in.read();
            Thread thread = new Thread(() -> out.println(n1));
            Thread t1 = new Thread(() -> out.println(n2));
            thread.start();
            t1.start();
            thread.join();
            t1.join();
            out.println("Finish");
            out.flush();
        }
    }

    private static int function(int[] ar) {
        int len = ar.length;
        int[] map = new int[len];
        map[0] = 1;
        for (int i = len - 1; i >= 0; i--)
            function(ar, map, i);

        int max = 0;
        for (int e : map)
            max = Math.max(e, max);

        return max;
    }

    private static int function(int[] ar, int[] map, int n) {
        if (n == 0)
            return 1;

        if (map[n] != 0) // processed already.
            return map[n];

        int value = ar[n], max = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (ar[i] < value) {
                max = Math.max(max, function(ar, map, i));
            }
        }

        map[n] = max + 1; // mark processed
        return map[n];
    }

    final static class Dummy {
        private final static Dummy dummy = new Dummy();
        int value;

        private Dummy() {
            value = 0;
        }

        public static Dummy getDummy() {
            return dummy;
        }
    }

}
