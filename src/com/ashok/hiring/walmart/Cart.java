package com.ashok.hiring.walmart;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Cart {
    private static final AtomicInteger sequence = new AtomicInteger(0);
    final Map<Category, Map<Product, Integer>> categoryProductsMap = new HashMap<>();
    private AtomicInteger itemCount = new AtomicInteger();
    volatile CartStatus status = CartStatus.ACTIVE;
    public final int id = sequence.incrementAndGet();
    private volatile double totalPrice = 0;
    public final Date date = new Date();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void addProduct(Product product) {
        lock.writeLock().lock();
        try {
            int count = 0;
            Map<Product, Integer> productCount;
            if (!categoryProductsMap.containsKey(product.category)) {
                productCount = new HashMap<>();
                categoryProductsMap.putIfAbsent(product.category, productCount);
            }

            productCount = categoryProductsMap.get(product.category);
            count = productCount.containsKey(product) ? productCount.get(product) + 1 : 1;
            productCount.put(product, count);
            itemCount.incrementAndGet();
            totalPrice += product.getPrice();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void putOrder() {
        lock.writeLock().lock();
        try {
            status.putOrder(this);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void cancleOrder() {
        lock.writeLock().lock();
        try {
            status.putOrder(this);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        return (o instanceof Cart) && id == ((Cart) o).id;
    }

    public int getItemCount() {
        return itemCount.get();
    }

    public double getTotalPrice() {
        lock.readLock().lock();
        double price = categoryProductsMap.entrySet().stream()
                .flatMap(categoryMapEntry -> categoryMapEntry.getValue().entrySet().stream())
                .mapToDouble(value -> value.getKey().getPrice() * value.getValue()).sum();
        lock.readLock().unlock();
        return price;
    }

    public CartStatus getStatus() {
        return status;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(id).append(", status ").append(status).append(" total price: ").append(totalPrice).append("\n");
        categoryProductsMap.entrySet().stream().forEach(categoryMapEntry -> {
            sb.append(categoryMapEntry.getKey()).append("=>").append(categoryMapEntry.getValue());
        });

        return sb.toString();
    }

    public boolean containsCategory(Category category) {
        lock.readLock().lock();
        boolean value = categoryProductsMap.containsKey(category);
        lock.readLock().unlock();
        return value;
    }

    public boolean containsProduct(Product product) {
        lock.readLock().lock();
        boolean value = categoryProductsMap.containsKey(product.category) && categoryProductsMap.get(product.category).containsKey(product);
        lock.readLock().unlock();
        return value;
    }
}
