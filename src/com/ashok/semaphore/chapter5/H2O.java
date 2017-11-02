package com.ashok.semaphore.chapter5;

import com.ashok.lang.concurrency.Barrier;

import java.util.concurrent.Semaphore;

/**
 * Please go through the problem statement in the e-book 'A Little Book for Semaphores'.
 * <p>
 * There are two kinds of threads, oxygen and hydrogen. In order to assemble
 * these threads into water molecules, we have to create a barrier that makes each
 * thread wait until a complete molecule is ready to proceed.
 * As each thread passes the barrier, it should invoke bond. You must guarantee
 * that all the threads from one molecule invoke bond before any of the threads
 * from the next molecule do.
 * In other words:
 * <ul>
 * <li> If an oxygen thread arrives at the barrier when no hydrogen threads are
 * present, it has to wait for two hydrogen threads.
 * <li> If a hydrogen thread arrives at the barrier when no other threads are
 * present, it has to wait for an oxygen thread and another hydrogen thread.
 * </ul>
 * <p>
 * This particular solution is tricky.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class H2O {
    private final Semaphore
            mutex = new Semaphore(1),
            oxyQueue = new Semaphore(0),
            hydroQueue = new Semaphore(0);

    private volatile int oxygenAtoms = 0, hydrogenAtoms = 0;
    private final Barrier barrier = new Barrier(3);

    public void hydrogen() throws InterruptedException {
        mutex.acquire();
        hydrogenAtoms++;

        if (hydrogenAtoms >= 2 && oxygenAtoms >= 1) {
            hydroQueue.release(2);
            hydrogenAtoms -= 2;
            oxyQueue.release();
            oxygenAtoms--;
        } else {
            mutex.release();
        }

        hydroQueue.acquire();
        bond();
        barrier.cross();
    }

    public void oxygen() throws InterruptedException {
        mutex.acquire();
        oxygenAtoms++;

        if (hydrogenAtoms >= 2) {
            hydroQueue.release(2);
            hydrogenAtoms -= 2;
            oxyQueue.release();
            oxygenAtoms--;
        } else {
            mutex.release();
        }

        oxyQueue.acquire();
        bond();

        barrier.cross();
        mutex.release();
    }

    private void bond() throws InterruptedException {
        // collect two hydrogen atoms and one oxygen atom to form one dy-hydrogen oxide molecule.
    }
}
