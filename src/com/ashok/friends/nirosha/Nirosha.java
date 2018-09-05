/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.nirosha;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("Sleeping thread: " + Thread.currentThread());
        };

        while (true) {
            int n = in.readInt();
            playWithMultiThreading(n, runnable);
            out.println("Finish running");
            out.flush();
        }
    }

    private static void playWithMultiThreading(int threads, Runnable run) {
        CyclicBarrier barrier = new CyclicBarrier(threads, () -> out.println("Barrier tripping thread: " + Thread.currentThread()));

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    // log something
                    out.println("Executing thread: " + Thread.currentThread());
                    run.run(); // the method.
                    //log something
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread number: " + (i + 1)).start();
        }
    }

    /**
     * Returns array with
     *
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
