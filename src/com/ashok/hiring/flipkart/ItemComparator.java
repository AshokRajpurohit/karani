package com.ashok.hiring.flipkart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface ItemComparator {
    int compareAgainstOrder(Order order, MenuItem item1, MenuItem item2);
}
