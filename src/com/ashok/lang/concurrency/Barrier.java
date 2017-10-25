package com.ashok.lang.concurrency;

import java.util.concurrent.Semaphore;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Barrier {
    private final int size;
    private volatile int count = 0;
    private final Semaphore mutex = new Semaphore(1);
    private final Turnstile turnstile1 = new Turnstile(), turnstile2 = new Turnstile(1);

    public Barrier(int size) {
        this.size = size;
    }

    public void phase1() throws InterruptedException {
        mutex.acquire();
        count++;

        if (count == size) {
            turnstile2.acquire(); // lock the second
            turnstile1.release(size); // unlock the first
        }

        mutex.release();
        turnstile1.passThrough();
    }

    public void phase2() throws InterruptedException {
        mutex.acquire();
        count--;

        if (count == 0) {
            turnstile1.acquire(); // lock the first
            turnstile2.release(size); // unlock the second.
        }

        mutex.release();
        turnstile2.passThrough();
    }

    public void barrier() throws InterruptedException {
        phase1();
        phase2();
    }
}
