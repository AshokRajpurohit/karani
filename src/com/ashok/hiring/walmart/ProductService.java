package com.ashok.hiring.walmart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ProductService {
    final Map<Product, Product> products = new ConcurrentHashMap();

    public Product newProduct(Category category, String name, double price) {
        Product product = new Product(category, price, name);
        products.putIfAbsent(product, product);
        return products.get(product);
    }
}
