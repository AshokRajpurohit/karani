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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Problem Name: Practice Concurrency
 * Reference: Java Concurrency in Practice by Brian Goetz et al.
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
        process(new ReentrantLock(), in.readInt());
        TestConcurrency a = new TestConcurrency();
        a.solve();
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

    private static void process(Lock lock, int recusion) {
        if (recusion == 0)
            return;

        lock.lock();
        out.println("lock time: " + recusion);
        process(lock, recusion - 1);
        lock.unlock();
        out.println("unlock time: " + recusion);
        out.flush();
    }

    private void solve() throws InterruptedException, IOException {
        while (true) {
            Dummy dummy = new Dummy(in.read());
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dummy.task1();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    dummy.task2();
                }
            });

            thread1.start();
            thread2.start();
//            int n = inputUtils.nextInt();
//            if (n == -1)
//                outputUtils.outputComplete = true;
//
//            outputUtils.println(n);
        }
    }

    private static Lock lock = new LockImpl();

    final static class LockImpl implements Lock {

        @Override
        public void lock() {
            int n = 10;
            out.println("doing nothing", n);
            out.flush();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            int n = 73;
            out.println("unlocking", n);
            out.flush();
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    final static class Dummy {
        final String name;

        public Dummy(String name) {
            this.name = name;
        }

        void task1() throws InterruptedException {
            synchronized (lock) {
                int n = 10;
                int m = n * n;
                out.println(m + ", " + n);
                out.flush();
                Thread.sleep(6000);
            }
        }

        void task2() {
            synchronized (lock) {
                int n = 15;
                int m = n * n;
                out.println(m + ", " + n);
                out.flush();
            }
        }

        @Override
        public String toString() {
            return name;
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
