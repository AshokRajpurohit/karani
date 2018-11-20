package com.ashok.hiring.mmt;

import com.ashok.hiring.mmt.model.BookModel;
import com.ashok.lang.dsa.GenericTST;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Catalogue {
    public final int defaultIssueDuration;
    GenericTrie<BookCategory> categoryTrie = new GenericTrie<>();
    GenericTrie<Book> bookGenericTrie = new GenericTrie<>();
    GenericTrie<BookIssueRecord> recordGenericTrie = new GenericTrie<>();
    GenericTST<BookIssueRecord> records = new GenericTST<>("a", null);
    GenericTST<BookCategory> categories = new GenericTST<>("a", null);
    GenericTST<Book> books = new GenericTST<Book>("a", null);
    GenericTST<Author> authors = new GenericTST<>("a", null);
    Map<Integer, Book> bookIdMap = new HashMap<>();

    Catalogue(int duration) {
        defaultIssueDuration = duration;
    }

    public void addCategory(String name) {
        if (categories.contains(name))
            return;

        BookCategory category = new BookCategory(name);
        categories.add(name, category);
    }

    public void addBook(BookModel model) throws DuplicateName {
        validateBook(model);
        BookCategory bookCategory = getOrAddCategory(model.category);
        Author author = getOrAddAuthor(model.authorName);
        addBook(model.bookName, author, bookCategory);
    }

    public BookCategory getOrAddCategory(String name) {
        if (categories.contains(name))
            return categories.find(name).get(0);

        BookCategory category = new BookCategory(name);
        categories.add(name, category);
        return category;
    }

    public BookIssueRecord issueBook(String bookName, String issuerName) {
        if (!books.contains(bookName)) throw new NullPointerException();
        Book book = books.find(bookName).get(0);
        return new BookIssueRecord(book, issuerName, defaultIssueDuration);
    }

    public BookIssueRecord returnBook(String bookName, String issuerName) {
        Book book = searchBooks(bookName).get(0);
        Optional<BookIssueRecord> record = book.bookIssueRecords.stream().filter(a -> a.issuerName.equals(issuerName)).findFirst();
        if (record.isPresent()) {
            book.bookIssueRecords.remove(record.get());
        }

        return record.get();
    }

    void addBook(String bookName, Author author, BookCategory category) {
        Book book = new Book(bookName, author, category);
        books.add(bookName, book);
        bookIdMap.put(book.id, book);
    }

    Author getOrAddAuthor(String name) {
        if (authors.contains(name)) return authors.find(name).get(0);
        Author author = new Author(name);
        authors.add(name, author);
        return author;
    }

    public List<Book> searchBooks(String name) {
        return books.find(name);
    }

    private void validateBook(BookModel model) throws DuplicateName {
        if (books.find(model.bookName)
                .stream()
                .anyMatch(a -> a.author.name.equals(model.authorName)))
            throw new DuplicateName("Duplicate book");
    }
}
