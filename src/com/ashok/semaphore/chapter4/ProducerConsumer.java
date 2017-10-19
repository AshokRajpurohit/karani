/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.semaphore.chapter4;

import com.ashok.lang.dsa.Queue;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ProducerConsumer {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static Queue<String> queue = new Queue<>();
    private static int producer_sequence = 0, consumer_sequence = 0;


    private static final Semaphore
            mutex = new Semaphore(1),
            items = new Semaphore(0),
            space = new Semaphore(20);

    public static void main(String[] args) throws IOException {
        ProducerConsumer a = new ProducerConsumer();
        try {
            a.solve();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            in.close();
//            out.close();
//            System.exit(0);
        }
    }

    private void solve() throws IOException {
        out.println("Enter number of producers and consumers");
        out.flush();
        int p = in.readInt(), c = in.readInt();
        runProducers(p);
        runConsumers(c);
    }

    private void runProducers(int producers) {
        while (producers > 0) {
            producers--;
            new Thread(new Producer()).start();
        }
    }

    private static synchronized String read() throws IOException {
        return in.read();
    }

    private void runConsumers(int consumers) {
        while (consumers > 0) {
            consumers--;
            new Thread(new Consumer()).start();
        }
    }

    final class Producer implements Runnable {
        final int id = producer_sequence++;
        Random random = new Random();

        @Override
        public void run() {
            while (true) {
                try {
                    String value = read();
                    space.acquire();
                    mutex.acquire();
                    queue.push(value);
                    mutex.release();
                    items.release();
                    out.println("Producer: " + id + " got this value: => " + value);
                    out.flush();
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    final class Consumer implements Runnable {
        final int id = consumer_sequence++;
        Random random = new Random();

        public void run() {
            while (true) {
                try {
                    items.acquire();
                    mutex.acquire();
                    mutex.getQueueLength();
                    String value = queue.pop();
                    mutex.release();
                    space.release();
                    out.println("Consumer: " + id + " value => " + value);
                    out.flush();
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
