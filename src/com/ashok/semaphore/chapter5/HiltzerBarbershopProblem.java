package com.ashok.semaphore.chapter5;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Problem Statement: Please go through the e-book. I am tired of writing.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class HiltzerBarbershopProblem {
    private final int shopCapacity, chairs, sofaCapacity; // actually we don't need sofa capacity.
    private volatile int customers = 0;
    private Semaphore
            mutex = new Semaphore(1), // enter shop one by one.
            paymentMutex = new Semaphore(1);

    private ThreadLocal<Semaphore> sofaSemaphore = getThreadLocalSemaphore(0), // semaphore for sofa-waiting customer.
            barberSemaphore = getThreadLocalSemaphore(0),
            paymentSemaphore = getThreadLocalSemaphore(0);

    private LinkedList<Semaphore>
            sofaQueue = new LinkedList<>(),
            customerQueue = new LinkedList<>(),
            sofaCustomers = new LinkedList<>(),
            paymentQueue = new LinkedList<>();


    public HiltzerBarbershopProblem(int shopCapacity, int chairs, int sofaCapacity) {
        this.shopCapacity = shopCapacity;
        this.chairs = chairs;
        this.sofaCapacity = sofaCapacity;

        // let barber start their work.
        for (int i = 0; i < chairs; i++)
            new Thread(new Barber()).start();
    }

    public void customer(Customer customer) throws InterruptedException {
        mutex.acquire();
        System.out.println(customer + " is entering the shop");
        enterShop(customer);
        sitOnSofa(customer);
        mutex.release();

//        barberSemaphore.get().acquire(); // wait for the barber to call.
        sofaSemaphore.get().acquire(); // wait for the barber to call.
        signalSofaSeat();

        getHairCut(customer);
        pay(customer);

        exitShop();
    }

    private void signalSofaSeat() throws InterruptedException {
        mutex.acquire();
        if (sofaQueue.size() != 0)
            sofaQueue.removeFirst().release(); // let next sofa queue person know, seat is available.

        mutex.release();
    }

    private void enterShop(Customer customer) {
        if (customers == shopCapacity) {
            mutex.release();
            throw new CustomerNotAllowed("Shop is already full, please come later. Thank You " + customer);
        }

        customers++;
        customerQueue.addLast(barberSemaphore.get()); // and join the waiting customers queue.
    }

    private void sitOnSofa(Customer customer) throws InterruptedException {
        if (sofaCustomers.size() < sofaCapacity) { // sofa is available, what are you waiting for then!
            sofaCustomers.addLast(sofaSemaphore.get()); // sit and wait for the barber to signal him.
        } else {
            sofaQueue.addLast(sofaSemaphore.get()); // join the sofa waiting list.
            mutex.release();
            sofaSemaphore.get().acquire(); // wait for sofa seat to be available.

            mutex.acquire();
            sofaCustomers.addLast(sofaSemaphore.get());
        }
        System.out.println(customer + " is seating on sofa");
    }

    private void getHairCut(Customer customer) {
        System.out.println(customer + " getting his hair cut");
        // whatever you do during haircut, check emails, whatsapp message or something meaningful.
    }

    private void exitShop() throws InterruptedException {
        mutex.acquire();
        customers--;
        mutex.release();
    }

    private void pay(Customer customer) throws InterruptedException {
        paymentMutex.acquire();
        // now after haircut join the payment queue.
        paymentQueue.addLast(paymentSemaphore.get());
        paymentMutex.release();

        paymentSemaphore.get().acquire(); // wait for a barber to accept payment.
        System.out.println("payment completed for " + customer);
    }

    private void acceptPayment(Barber barber) throws InterruptedException {
        paymentMutex.acquire();

        if (paymentQueue.size() != 0) {
            paymentQueue.removeFirst().release(); // let customer know his payment is accepted.
            System.out.println("payment accepted by " + barber);
        }

        paymentMutex.release();
    }

    private void cutHair(Barber barber) throws InterruptedException {
        mutex.acquire();

        if (sofaCustomers.size() != 0) {
            sofaCustomers.removeFirst().release();
            System.out.println(barber + " is cutting hair");
        }

        mutex.release();
    }

    private static ThreadLocal<Semaphore> getThreadLocalSemaphore(int permits) {
        return new ThreadLocal<Semaphore>() {
            @Override
            protected Semaphore initialValue() {
                return new Semaphore(permits);
            }
        };
    }

    final static class CustomerNotAllowed extends RuntimeException {
        public CustomerNotAllowed(String message) {
            super(message);
        }
    }

    private int barberIdSequence = 0;

    private final class Barber implements Runnable {
        final int id = barberIdSequence++;

        @Override
        public void run() {
            while (true) {
                try {
                    acceptPayment(this);
                    cutHair(this);
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Barber: " + id;
        }
    }

    public Runnable getCustomer() {
        return new Customer();
    }

    private int customerIdSequence = 0;

    private final class Customer implements Runnable {
        final int id = customerIdSequence++;

        @Override
        public void run() {
            while (true) {
                try {
                    customer(this);
                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e1) {
                    }
                }
            }
        }

        public String toString() {
            return "Customer: " + id;
        }
    }
}
