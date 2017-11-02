package com.ashok.lang.concurrency;

import java.util.concurrent.Semaphore;

/**
 * The {@code Barrier} class implements re-usable barrier. This Barrier blocks untill {@code predefined} number of
 * threads arrive at barrier and then open it for those threads only and keeps blocking new threads untill all the
 * existing threads exit the barrier. It allows a batch of threads to proceed.
 *
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

    /**
     * Enter the code blocked by this barrier.
     *
     * @throws InterruptedException
     */
    public void enter() throws InterruptedException {
        mutex.acquire();
        count++;

        if (count == size) {
            turnstile2.acquire(); // lock the second
            turnstile1.release(size); // unlock the first
        }

        mutex.release();
        turnstile1.passThrough();
    }

    /**
     * Exit the code blocked by this barrier.
     *
     * @throws InterruptedException
     */
    public void exit() throws InterruptedException {
        mutex.acquire();
        count--;

        if (count == 0) {
            turnstile1.acquire(); // lock the first
            turnstile2.release(size); // unlock the second.
        }

        mutex.release();
        turnstile2.passThrough();
    }

    /**
     * Just cross over it. It is just like barrier which allows size number of threads to cross at a time.
     *
     * @throws InterruptedException
     */
    public void cross() throws InterruptedException {
        enter();
        exit();
    }
}
