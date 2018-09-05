package com.ashok.hiring.flipkart;

import com.ashok.hiring.swiggy.Coordinates;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Order {
    private static AtomicLong idSequence = new AtomicLong(1);
    final long id = idSequence.getAndIncrement();
    public String[] items;
    public String strategy;
    public Coordinates location;

    public Order(OrderModel model) {
        items = model.items;
        strategy = model.strategy;
        location = model.location;
    }
}
