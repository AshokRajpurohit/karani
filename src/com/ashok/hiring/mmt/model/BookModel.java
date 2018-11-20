package com.ashok.hiring.mmt.model;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BookModel {
    final public String bookName, authorName, category;

    public BookModel(String bookName, String authorName, String category) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
    }
}
