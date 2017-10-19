/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.semaphore.chapter3;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Solution for ReaderWriters where there can be multiple readers accessing the resource
 * at the same time, but only one writer can access it. While a writer is accessing the
 * resource, no other thread reader or writer is allowed to access it.
 * <p>
 * The solution presented here does not cause deadlock as reader or writer threads
 * continuously keep working, But this solution causes starvation.
 * <p>
 * Think of the scenario when a writer is waiting for it's turn and readers are making
 * progress of. All the time there are atleast 2 readers working on it. Before last one
 * leaves, a new reader can enter and so on. In this case, the writer will never get the
 * resource and it will starve, so other writers also.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ReadersWritersStarvation {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private Semaphore roomEmpty = new Semaphore(1),
            mutex = new Semaphore(1);
    private volatile int readers = 0;
    private LinkedList<String> queue = new LinkedList<>();
    private int writerSequence = 1, readerSequence = 1;

    public static void main(String[] args) throws IOException {
        ReadersWritersStarvation a = new ReadersWritersStarvation();
        try {
            a.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void solve() throws IOException {
        println("Enter number of readers and writers");

        int r = in.readInt(), w = in.readInt();
        runReaders(r);
        runWriters(w);
    }

    private void runWriters(int writers) {
        while (writers > 0) {
            writers--;
            new Thread(new Writer()).start();
        }
    }

    private void runReaders(int readers) {
        while (readers > 0) {
            readers--;
            new Thread(new Reader()).start();
        }
    }

    private static String read() throws IOException {
        synchronized (in) {
            return in.read();
        }
    }

    private static void println(Object object) {
        synchronized (out) {
            out.println(object);
            out.flush();
        }
    }

    private final class Writer implements Runnable {
        Random random = new Random();
        final int id = writerSequence++;

        @Override
        public void run() {
            while (true) {
                try {
                    roomEmpty.acquire();
                    task();
                    roomEmpty.release();
                    Thread.sleep(random.nextInt(1500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void task() throws IOException {
            String value = read();
            println(this + " " + value);
            queue.addLast(value);
        }

        public String toString() {
            return "[writer: " + id + "]";
        }
    }

    private final class Reader implements Runnable {
        Random random = new Random();
        final int id = readerSequence++;

        @Override
        public void run() {
            while (true) {
                try {
                    incrementReaders();
                    task();
                    decrementReaders();
                    Thread.sleep(random.nextInt(1500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void incrementReaders() throws InterruptedException {
            mutex.acquire();
            readers++;

            if (readers == 1)
                roomEmpty.acquire();

            mutex.release();
        }

        private void decrementReaders() throws InterruptedException {
            mutex.acquire();
            readers--;

            if (readers == 0)
                roomEmpty.release();

            mutex.release();
        }

        private void task() {
            println(this + " " + queue);
        }

        public String toString() {
            return "[reader: " + id + "]";
        }
    }
}
