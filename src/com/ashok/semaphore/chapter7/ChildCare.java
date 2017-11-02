package com.ashok.semaphore.chapter7;

import java.util.concurrent.Semaphore;

/**
 * {@code Max Hailperin} wrote this problem for his textbook <i>Operating Systems
 * and Middleware</i>. At a child care center, state regulations require that
 * there is always one adult present for every three children.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ChildCare {
    public final int adultCapacity;
    private final Semaphore
            mutex = new Semaphore(1),
            adultQueue = new Semaphore(1),
            childQueue = new Semaphore(0);

    // waiting: children waiting to enter, leaving: adults waiting to leave.
    private int children = 0, adults = 0, waiting = 0, leaving = 0;

    ChildCare(int adultCapacity) {
        this.adultCapacity = adultCapacity;
    }

    public void enterAdult() throws InterruptedException {
        mutex.acquire();
        adults++;
        pickKids();
        mutex.release();
    }

    public void exitAdult() throws InterruptedException {
        mutex.acquire();
        if (children <= adultCapacity * (adults - 1)) {
            adults--;
            mutex.release();
        } else {
            leaving++;
            mutex.release();
            adultQueue.acquire();
        }
    }

    public void enterChild() throws InterruptedException {
        mutex.acquire();
        if (checkIfAdultAvailable()) {
            children++;
            mutex.release();
        } else {
            waiting++;
            mutex.release();
            childQueue.acquire(); // wait here for your turn kid.
        }
    }

    public void exitChild() throws InterruptedException {
        mutex.acquire();
        children--;
        if (leaving + children <= adultCapacity * (adults - 1)) {
            leaving--;
            children--;
            adultQueue.release();
        }

        mutex.release();
    }

    private void pickKids() {
        if (waiting == 0)
            return;

        int kids = Math.min(adultCapacity, waiting);
        childQueue.release(kids);
        waiting -= kids;
        children += kids;
    }

    public void getChildCare() throws InterruptedException {
        enterChild();
        // critical child care code
        exitChild();
    }

    private boolean checkIfAdultAvailable() {
        return adults * adultCapacity > children;
    }
}
