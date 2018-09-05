package com.ashok.hiring.flipkart;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Restaurent {
    final long id;
    final String name;
    final String address;
    final int capacity;
    private AtomicInteger processing = new AtomicInteger(0);
    private AtomicBoolean shutdown = new AtomicBoolean(false);
    Semaphore lock;
    Set<MenuItem> items = new HashSet<>();

    Restaurent(long id, String name, String address, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        lock = new Semaphore(capacity);
    }

    public void process(Order order, MenuItem item) throws InterruptedException {
        lock.acquire();
        Thread.sleep(1000);
        System.out.println("Processed item: " + item.name + ", by restaurent: " + name);
        lock.release();
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Restaurent))
            return false;

        return id == ((Restaurent) o).id;
    }

    public void close() {
        shutdown.set(true);
    }
}
