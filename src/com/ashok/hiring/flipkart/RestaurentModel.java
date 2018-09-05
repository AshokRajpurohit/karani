package com.ashok.hiring.flipkart;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class RestaurentModel {
    long id;
    String name;
    String address = "";
    Set<MenuItem> items = new HashSet<>();
    int capacity;

    RestaurentModel(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }
}
