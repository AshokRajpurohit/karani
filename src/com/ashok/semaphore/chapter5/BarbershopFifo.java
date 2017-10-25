package com.ashok.semaphore.chapter5;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Please see the book for problem description. I am tired of writing here.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BarbershopFifo {
    private final int capacity;
    private volatile LinkedList<Semaphore> queue = new LinkedList<>(); // store thread(customer) specific semaphores.
    private volatile int customers = 0;
    private Semaphore
            mutex = new Semaphore(1), // mutex for queue and customers.
            customer = new Semaphore(0),
            customerDone = new Semaphore(0),
    //            barber = new Semaphore(0),
    barberDone = new Semaphore(0);
    private ThreadLocal<Semaphore>
            barber = getThreadLocalSemaphore(0);

    BarbershopFifo(int capacity) {
        this.capacity = capacity;
    }

    private void customer() throws InterruptedException {
        mutex.acquire();
        if (customers == capacity) {
            mutex.release();
            return;
        }

        customers++;
        queue.addLast(barber.get());
        mutex.release();

        customer.release(); // tell barber, I am here.
        barber.get().acquire(); // wait for barber to call.

        // getHariCut(); // do whatever you need to do during haircut.

        customerDone.release(); // let barbet know I am done.
        barberDone.wait(); // now wait for the barber to finish.

        mutex.acquire();
        customers--;
        mutex.release();
    }

    private void barber() throws InterruptedException {
        customer.acquire(); // wait for customer.

        mutex.acquire();
        queue.removeFirst().release(); // let next customer know I am available.
        mutex.release();
//        barber.release(); // let next customer know I am available.

        // cutHair();

        customerDone.acquire(); // wait for customer to finish.
        barberDone.release(); // let customer know I am done.
    }

    private static ThreadLocal<Semaphore> getThreadLocalSemaphore(int permits) {
        return new ThreadLocal<Semaphore>() {
            @Override
            protected Semaphore initialValue() {
                return new Semaphore(permits);
            }
        };
    }


}
