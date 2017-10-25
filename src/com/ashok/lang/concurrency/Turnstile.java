package com.ashok.lang.concurrency;

import java.util.concurrent.Semaphore;

/**
 * {@code Turnstile} class is to represent turnstile. A turnstile forces threads to go sequencially
 * and can be locked to block all the threads.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Turnstile extends Semaphore {
    public Turnstile(int permits) {
        super(permits);
    }

    public Turnstile(int permits, boolean fair) {
        super(permits, fair);
    }

    public Turnstile() {
        super(1);
    }

    /**
     * This function ensures that the threads are going sequencially. However threads can access
     * resources in any order, after this.
     *
     * @throws InterruptedException
     */
    public void passThrough() throws InterruptedException {
        acquire();
        release();
    }
}
