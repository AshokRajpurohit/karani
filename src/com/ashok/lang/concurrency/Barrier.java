package com.ashok.lang.concurrency;

import java.util.concurrent.Semaphore;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Barrier {
    private final int size;
    private volatile int count = 0;
    private final Semaphore
            mutex = new Semaphore(1),
            turnstile1 = new Semaphore(0),
            turnstile2 = new Semaphore(0);

    public Barrier(int size) {
        this.size = size;
    }

    void phase1() throws InterruptedException {
        mutex.acquire();
        count++;

        if (count == size)
            turnstile1.release(size);

        mutex.release();
        turnstile1.acquire();
    }

    void phase2() throws InterruptedException {
        mutex.acquire();
        count--;

        if (count == 0)
            turnstile2.release(size);

        mutex.release();
        turnstile2.release();
    }

    void barrier() throws InterruptedException{
        phase1();
        phase2();
    }
}
