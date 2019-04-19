package com.ashok.hiring.lenskart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Catalogue {
    private final AtomicInteger sequence = new AtomicInteger();
    private Map<Integer, Product> products = new ConcurrentHashMap<>();

    public Product createProduct(String name, String productType) {
        ProductCategory category = ProductCategory.getByName(productType.toUpperCase());
        if (category == null) throw new RuntimeException("category: " + productType + ", does not exist");
        Product product = new Product(sequence.getAndIncrement(), name, category);
        products.put(product.id, product);
        return product;
    }

    public List<Product> getProductsInOrder() {
        return getProductsInOrder((a, b) -> Long.compare(b.getSaleCount(), a.getSaleCount()));
    }

    public List<Product> getProductsInOrder(Comparator<Product>... comparators) {
        Stream<Product> productStream = products.values().stream();
        int len = comparators.length;
        for (int i = len - 1; i >= 0; i--)
            productStream = productStream.sorted(comparators[i]);

        return productStream.collect(Collectors.toList());
    }

    Collection<Product> getProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public int addStock(int productId, int stock) throws ProductException {
        return getProduct(productId).addStock(stock);
    }

    public void sale(int productId) throws ProductException {
        getProduct(productId).sale(1);
    }

    Product getProduct(int id) throws ProductException {
        Product product = products.get(id);
        if (product == null) throw new ProductException("no product exists for id: " + id);
        return product;
    }
}
