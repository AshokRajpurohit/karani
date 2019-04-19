package com.ashok.hiring.lenskart;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ProductCategory {
    private static Map<String, ProductCategory> categories = new ConcurrentHashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    final String name;

    static {
        String[] values = new String[]{"EYEGLASS", "SUNGLASS", "EYEWARE", "CONTACT_LENS", "OTHERS"};
        for (String value : values) {
            new ProductCategory(value);
        }
    }

    ProductCategory(String name) {
        name = name.toUpperCase();
        this.name = name;
        categories.put(name, this);
    }

    public static Collection<String> listCategories() {
        return categories.keySet();
    }

    public static ProductCategory getByName(String name) {
        return categories.get(name);
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        return (o instanceof ProductCategory) && name.equals(((ProductCategory) o).name);
    }
}
