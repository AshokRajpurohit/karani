package com.ashok.hiring.mmt;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BookCategory {
    private static final AtomicInteger SEQUENCE = new AtomicInteger();
    final int id = SEQUENCE.incrementAndGet();
    final String name;
    Set<Book> books = new TreeSet<>((a, b) -> b.bookIssueRecords.size() - a.bookIssueRecords.size());

    BookCategory(String name) {
        this.name = name;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BookIssueRecord)) return false;
        return id == ((BookIssueRecord) o).id;
    }
}
