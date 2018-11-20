package com.ashok.hiring.mmt;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BookIssueRecord {
    private static final AtomicInteger SEQUENCE = new AtomicInteger();
    final Book book;
    final int id = SEQUENCE.incrementAndGet();
    final String issuerName;
    final LocalDate issueDate, dueDate;

    BookIssueRecord(Book book, String name, int days) {
        this.book = book;
        this.issuerName = name;
        issueDate = LocalDate.now();
        dueDate = issueDate.plusDays(15);
        book.bookIssueRecords.add(this);
        book.issueCount++;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BookIssueRecord)) return false;
        return id == ((BookIssueRecord) o).id;
    }
}
