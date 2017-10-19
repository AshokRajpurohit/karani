/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.semaphore.chapter4;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.semaphore.chapter3.Turnstile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * This is an improvement over {@link ReadersWritersStarvation}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ReadersWriters {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private final Semaphore
            roomEmpty = new Semaphore(1);

    private final Turnstile turnstile = new Turnstile(1);
    private final LightSwitch lightSwitch = new LightSwitch();

    private volatile int readers = 0;
    private LinkedList<String> queue = new LinkedList<>();
    private int writerSequence = 1, readerSequence = 1;

    public static void main(String[] args) throws IOException {
        ReadersWriters a = new ReadersWriters();
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
                    turnstile.acquire();
                    roomEmpty.acquire();
                    task();
                    turnstile.release();
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
                    turnstile.jump();
                    lightSwitch.lock(roomEmpty);
                    task();
                    lightSwitch.unlock(roomEmpty);
                    Thread.sleep(random.nextInt(1500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void task() {
            println(this + " " + queue);
        }

        public String toString() {
            return "[reader: " + id + "]";
        }
    }
}
