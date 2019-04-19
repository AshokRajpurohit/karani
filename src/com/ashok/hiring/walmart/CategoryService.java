package com.ashok.hiring.walmart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class CategoryService {
    private final Map<String, Category> categories = new ConcurrentHashMap();

    public Category newCategory(String name) {
        Category category = new Category(name);
        categories.putIfAbsent(name, category);
        return categories.get(name);
    }

    public Category getCategoryByName(String name) {
        return categories.get(name);
    }
}
