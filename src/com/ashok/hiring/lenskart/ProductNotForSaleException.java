package com.ashok.hiring.lenskart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ProductNotForSaleException extends ProductException {
    ProductNotForSaleException(String message) {
        super("Product is disabled for sale, " + message);
    }
}
