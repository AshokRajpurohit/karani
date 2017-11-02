package com.ashok.semaphore.chapter7;

import java.util.concurrent.Semaphore;

/**
 * Imagine a sushi bar with 5 seats. If you arrive while there is an empty seat,
 * you can take a seat immediately. But if you arrive when all 5 seats are full, that
 * means that all of them are dining together, and you will have to wait for the
 * entire party to leave before you sit down.
 * <p>
 * This problem can be found from "Design Patterns for Semaphores, Kenneth".
 * This pattern is called "Pass the baton" as we pass the mutex holding to next waiting thread.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SushiBar {
    final int tableSize;
    private volatile int eating = 0, waiting;
    private Semaphore mutex = new Semaphore(1), block = new Semaphore(0);
    private volatile boolean mustWait = false;


    SushiBar(int tableSize) {
        this.tableSize = tableSize;
    }

    public void eat() throws InterruptedException {
        enter();
        // eat sushi and enjoy.
        exit();
    }

    public void enter() throws InterruptedException {
        mutex.acquire();
        if (mustWait) {
            waiting++;
            mutex.release();
            block.acquire(); // when we resume, we have the mutex
            waiting--;
        }

        eating++;
        mustWait = mustWait || eating == tableSize;
        if (waiting != 0 && !mustWait)
            block.release(); // and pass the mutex to next thread
        else
            mutex.release();
    }

    public void exit() throws InterruptedException {
        mutex.acquire();
        eating--;
        mustWait = mustWait && eating != 0;

        if (waiting != 0 && !mustWait)
            block.release(); // and pass the mutex to next thread
        else
            mutex.release();
    }
}
