package com.ashok.semaphore.chapter4;

import java.util.concurrent.Semaphore;

/**
 * This class is analogy with, where first person (thread here) into the room turns on the
 * light (locks the mutex) and the last one out turns it off (unlocks the mutex).
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class LightSwitch {
    int counter = 0;
    final Semaphore mutex = new Semaphore(1); // guards counter.

    /**
     * lock for a reader thread. writer thread can directly use {@code lightSwitch} for
     * mutual exclusion without worrying about reader thread.
     * <p>
     * Reader thread locks the {@code lightSwitch} before getting it's hands on the
     * critical part (whatever it is, specific to readers only).
     *
     * @param lightSwitch This is analogous to {@link ReadersWritersStarvation#roomEmpty} semaphore.
     * @throws InterruptedException
     */
    public void lock(Semaphore lightSwitch) throws InterruptedException {
        mutex.acquire();
        counter++;
        if (counter == 1)
            lightSwitch.acquire();

        mutex.release();
    }

    /**
     * releases any reader specific locks (or semaphores) and unlocks {@code lightSwitch} if
     * this thread is the last one, to signal writer threads that they can proceed now.
     *
     * @param lightSwitch This is analogous to {@link ReadersWritersStarvation#roomEmpty} semaphore.
     * @throws InterruptedException
     */
    public void unlock(Semaphore lightSwitch) throws InterruptedException {
        mutex.acquire();
        counter--;
        if (counter == 0)
            lightSwitch.release();

        mutex.release();
    }
}
