package com.ashok.hiring.mmt;

import java.util.TreeSet;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Author {
    final String name;
    TreeSet<Book> books = new TreeSet<Book>((a, b) -> b.issueCount - a.issueCount);

    Author(String name) {
        this.name = name;
    }
}
