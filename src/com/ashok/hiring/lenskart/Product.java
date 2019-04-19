package com.ashok.hiring.lenskart;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Product {
    public final String name;
    public final int id;
    private final AtomicInteger stock = new AtomicInteger(0);
    private final AtomicBoolean isAvailableForSale = new AtomicBoolean(true);
    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // should use minimuly to reduce lock contentions.
    private final AtomicLong saleCount = new AtomicLong();
    public final ProductCategory type;

    Product(int id, String name, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.type = category;
    }

    int addStock(int newStock) {
        return stock.addAndGet(newStock);
    }

    void sale(int count) throws ProductException { // this code may give problem some time but overall the performance is better.
        if (!isAvailableForSale.get()) throw new ProductNotForSaleException(name);
        if (stock.get() < count) throw new NoStockException(name);
        if (stock.addAndGet(-count) < 0) { // unlucky guy.
            stock.addAndGet(count);
            throw new NoStockException(name);
        }

        saleCount.incrementAndGet();
        new Order(this); // for history purpose.
    }

    public String toString() {
        return "[" + id + ", " + name + "]";
    }

    void updateProductAvailability(boolean newValue) {
        isAvailableForSale.set(newValue);
    }

    public long getSaleCount() {
        return saleCount.get();
    }

    void sale1(int count) throws ProductException { // this code is better compared to method above but with poor performance.
        lock.writeLock().lock();
        if (!isAvailableForSale.get()) throw new NoStockException(name);
        try {
            if (stock.addAndGet(-count) < 0) {
                stock.addAndGet(count);
                throw new NoStockException(name);
            }

            saleCount.addAndGet(count);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        return (o instanceof Product) && this.id == ((Product) o).id;
    }
}
