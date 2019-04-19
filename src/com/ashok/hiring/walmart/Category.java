package com.ashok.hiring.walmart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Category {
    public final String name;

    public Category(String name) {
        this.name = name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        return (o instanceof Category) && name.equals(((Category) o).name);
    }

    public String toString() {
        return name;
    }
}
