package com.ashok.semaphore.chapter5;

import com.ashok.lang.concurrency.Turnstile;

import java.util.concurrent.Semaphore;

/**
 * Problem: A tribe of savages eats communal dinners from a large pot that
 * can hold M servings of stewed missionary. When a savage wants to eat, he
 * helps himself from the pot, unless it is empty. If the pot is empty, the
 * savage wakes up the cook and then waits until the cook has refilled the
 * pot.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class DiningSavages {
    private final int capacity;
    private final Semaphore
            mutex = new Semaphore(1), // semaphore for servings parameter.
            emptyPot = new Semaphore(0);

    private final Turnstile fullPot = new Turnstile(0);

    private final Thread cookThread = new Thread(new Cook());
    private volatile int servings = 0;

    public DiningSavages(int capacity) {
        this.capacity = capacity;
        emptyPot.release(); // initially the pot is empty.
        cookThread.start();
    }

    public void savage(Savage savage) throws InterruptedException {
        while (true) {
            getServingFromPot();
            savage.eat();
        }
    }

    private void cook() throws InterruptedException {
        while (true) {
            emptyPot.acquire();
            putServingsInPot();
            fullPot.release();
            Thread.sleep(100);
        }
    }

    private void putServingsInPot() {
        // whatever code you want to write for cook.
    }

    private void getServingFromPot() throws InterruptedException {
        mutex.acquire();
        if (servings == 0) {
            emptyPot.release();
            servings = capacity;
            fullPot.passThrough(); // wait till cook is finished his work and then grab serving.
        }

        servings--;
        mutex.release();
    }

    private void eat() {
        // whatever you want savage to do while eating.
    }

    final class Cook implements Runnable {
        @Override
        public void run() {
            try {
                cook();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int savageIdSequence = 0;

    public Savage getSavage() {
        return new Savage();
    }

    public final class Savage implements Runnable {
        final int id = ++savageIdSequence;

        @Override
        public void run() {
            try {
                savage(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void eat() throws InterruptedException {
            System.out.println(this + " is eating, please don't disturb");
            Thread.sleep(100);
        }

        public String toString() {
            return "Savage: " + id;
        }
    }
}
