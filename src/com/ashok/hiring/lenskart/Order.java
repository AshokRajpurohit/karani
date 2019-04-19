package com.ashok.hiring.lenskart;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Order {
    private static final Collection<Order> orders = new ConcurrentSkipListSet<>((o1, o2) -> o1.date.compareTo(o2.date));
    final Product product;
    final Date date = new Date();

    Order(Product product) {
        this.product = product;
        orders.add(this);
    }

    public int hashCode() {
        return product.hashCode();
    }

    public String toString() {
        return date + " -> " + product;
    }

    public static Collection<Order> getOrders() {
        return Collections.unmodifiableCollection(orders);
    }
}
