/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ashok.lang.concurrency;

import com.ashok.lang.annotation.ThreadSafe;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem Name: Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
@ThreadSafe
public class TestConcurrency {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static InputUtils inputUtils = new InputUtils();
    private static OutputUtils outputUtils = new OutputUtils();
    private static final CountDownLatch endGate = new CountDownLatch(2);

    private static Thread inputThread = new Thread(new Runnable() {
        public void run() {
            try {
                inputLoader();
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                endGate.countDown();
            }
        }
    });

    private static Thread outputThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                outputWriter();
            } finally {
                out.println("output-thread is complete");
                out.flush();
                endGate.countDown();
            }
        }
    });

    public static void main(String[] args) throws IOException,
            InterruptedException {
        TestConcurrency a = new TestConcurrency();
        long time = System.currentTimeMillis();
        inputThread.setName("input-thread");
        outputThread.setName("output-thread");
        inputThread.start();
        outputThread.start();
        a.solve();
        endGate.await();

        out.println(System.currentTimeMillis() - time);

        out.close();
    }

    private void solve() throws InterruptedException {
        while (true) {
            int n = inputUtils.nextInt();
            if (n == -1)
                outputUtils.outputComplete = true;

            outputUtils.println(n);
        }
    }

    private static void outputWriter() {
        while (!outputUtils.outputComplete) {
            outputUtils.flush();
        }
    }

    final static class OutputUtils {
        private LinkedBlockingQueue<String> outputBuffer = new LinkedBlockingQueue();
        private LinkedList<String> outputList = new LinkedList<>();
        volatile boolean outputComplete = false;
        private final static String newLine = "\n";
        AtomicInteger size = new AtomicInteger();

        final static class Lock {
        }

        private final Lock notEmpty = new Lock(), flushLock = new Lock();

        void outputComplete() {
            outputComplete = true;
            flush();
        }

        void println(Object obj) throws InterruptedException {
            synchronized (flushLock) {
                outputList.addLast(String.valueOf(obj) + newLine);
            }
        }

        void flush() {
            synchronized (flushLock) {
                while (outputList.size() > 0) {
                    out.print(outputList.removeFirst());
                }

                out.flush();
            }
        }
    }

    private static void inputLoader() throws IOException, InterruptedException {
        int t = in.readInt();
        inputUtils.loadInt(t);

        while (t > 0) {
            t--;

            int n = in.readInt();
            inputUtils.loadInt(n);

            for (int i = 0; i < n; i++)
                inputUtils.loadLong(in.readLong());

            int q = in.readInt();
            inputUtils.loadInt(q);

            while (q > 0) {
                q--;
                inputUtils.loadLong(in.readLong());
            }
        }
    }

    final static class InputUtils {
        private LinkedList<Integer> intList = new LinkedList<>();
        private LinkedList<Long> longList = new LinkedList<>();

        final static class Lock {
        }

        ;

        private final Lock lockInt = new Lock(), lockLong = new Lock();

        int nextInt() throws InterruptedException {
            synchronized (lockInt) {
                while (intList.isEmpty())
                    lockInt.wait();

                return intList.removeFirst();
            }
        }

        long nextLong() throws InterruptedException {
            synchronized (lockLong) {
                while (longList.isEmpty())
                    lockLong.wait();

                return longList.removeFirst();
            }
        }

        void loadInt(int n) throws IOException, InterruptedException {
            synchronized (lockInt) {
                intList.addLast(n);

                if (intList.size() == 1)
                    lockInt.notify();
            }
        }

        void loadLong(long n) throws IOException, InterruptedException {
            synchronized (lockLong) {
                longList.addLast(n);

                if (longList.size() == 1)
                    lockLong.notify();
            }
        }

    }

}
