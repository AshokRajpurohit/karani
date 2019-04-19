package com.ashok.hiring.lenskart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class NoStockException extends ProductException {
    NoStockException(String message) {
        super("No stock available for product, " + message);
    }
}
