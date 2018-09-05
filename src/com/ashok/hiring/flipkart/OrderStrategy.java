package com.ashok.hiring.flipkart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum OrderStrategy {
    LOWEST_PRICE((order, a, b) -> a.getPrice() - b.getPrice());

    ItemComparator strategy;

    OrderStrategy(ItemComparator strategy) {
        this.strategy = strategy;
    }
}
