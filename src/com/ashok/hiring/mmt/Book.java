package com.ashok.hiring.mmt;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Book {
    private static final AtomicInteger SEQUENCE = new AtomicInteger();
    final int id = SEQUENCE.incrementAndGet();
    final String name;
    final Author author;
    final BookCategory category;
    final TreeSet<BookIssueRecord> bookIssueRecords = new TreeSet<>((a, b) -> a.dueDate.compareTo(b.dueDate));
    int issueCount = 0; // let's not use it at this time. We can assume there are infinite copies.
    int totalCopies = 1;

    Book(String name, Author author, BookCategory category, int copies) {
        this.name = name;
        this.author = author;
        this.category = category;
        totalCopies = copies;
    }

    Book(String name, Author author, BookCategory category) {
        this(name, author, category, 1);
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BookIssueRecord)) return false;
        return id == ((BookIssueRecord) o).id;
    }

    public String toString() {
        String s = "id: " + id + ", name: " + name + ", category: " + category.name + ", author: " + author.name;
        return s;
    }
}
