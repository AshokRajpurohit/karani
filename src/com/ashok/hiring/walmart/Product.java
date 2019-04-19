package com.ashok.hiring.walmart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Product {
    public final Category category;
    private double price;
    public final String name;


    Product(Category category, double price, String name) {
        this.category = category;
        this.price = price;
        this.name = name;
    }

    public int hashCode() {
        return Long.hashCode(1L * category.hashCode() * name.hashCode());
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return category.equals(product.category) && name.equals(product.name);
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "[" + category + "-" + name + ": " + price + "]";
    }
}
