/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.mmt;

import com.ashok.hiring.mmt.model.BookModel;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MakeMyTrip {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final int addCategory = 1;
    private static final int addBook = 2;
    private static final int searchBooks = 3;
    private static final int byCategroy = 1;
    private static final int byAuthorName = 2;
    private static final int byBookName = 3;
    private static final int BookIssueRecords = 4;
    private static final int issueBook = 1;
    private static final int returnBook = 2;
    private static final int bookIssueDetails = 3;
    private static final int topIssuedBooks = 4;
    private static final int defaulters = 5;

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        Catalogue catalogue = new Catalogue(10);
        while (true) {
            out.println("1. Add category.\n2. Add book to category.\n3. Search books.\n4. Book Issue Records");
            out.flush();
            int queryType = in.readInt();
            switch (queryType) {
                case addCategory:
                    catalogue.addCategory(getName("category"));
                    break;
                case addBook:
                    try {
                        catalogue.addBook(getBookInfo());
                    } catch (DuplicateName duplicateName) {
                        duplicateName.printStackTrace();
                    }
                    break;
                case searchBooks:
                    searchBooks(catalogue);
                    break;
                case BookIssueRecords:
                    issueRecords(catalogue);
                    break;
                default:
                    invalidEntry();
                    break;
            }
        }
    }

    private static void issueRecords(Catalogue catalogue) throws IOException {
        out.println("select the action: 1. issue book, 2. return book. 3. book issue details. 4 search issued books. 5. list of defaulters.");
        out.flush();
        int type = in.readInt();
        String name, issuerName;
        switch (type) {
            case issueBook:
                name = ("book");
                issuerName = getName("your");
                catalogue.issueBook(name, issuerName);
                break;
            case returnBook:
                name = ("book");
                issuerName = getName("your");
                catalogue.returnBook(name, issuerName);
        }
    }

    private static int getId(String name) throws IOException {
        out.println("Enter " + name + " id");
        out.flush();
        return in.readInt();
    }

    private static void searchBooks(Catalogue cat) throws IOException {
        out.println("select the criteria for search: 1. category, 2. author, 3. book-name");
        out.flush();
        int type = in.readInt();
        List<Book> books = new LinkedList<>();
        switch (type) {
            case byAuthorName:
                cat.authors.find(getName("author")).stream().forEach(author -> books.addAll(author.books));
                break;
            case byBookName:
                cat.searchBooks(getName("book"));
                break;
            case byCategroy:
                cat.categories.find(getName("category")).stream().forEach(category -> books.addAll(category.books));
                break;
            default:
                invalidEntry();
                break;
        }

        if (books.isEmpty()) out.println("No books found");
        out.println(books);
        out.flush();
    }

    private static void invalidEntry() {
        out.println("Invalid Value");
        out.flush();
    }

    private static BookModel getBookInfo() throws IOException {
        return new BookModel(getName("book-name"), getName("author-name"), getName("category"));
    }

    private static String getName(String name) throws IOException {
        out.println("Enter the " + name + " name:");
        out.flush();
        return in.read();
    }
}
