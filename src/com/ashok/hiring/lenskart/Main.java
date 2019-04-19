/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.lenskart;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Main {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        Catalogue catalogue = new Catalogue();
        while (true) {
            System.out.println("What do you want.\n 1. add new product, 2. add stock to product, 3. mark product enable/disable for sale,\n 4. list products in their sale count, 5. sale product, 6. anything else for order history");
            int t = in.readInt();
            try {
                switch (t) {
                    case 1:
                        out.println("use one of the categories, enter product name and category");
                        out.println(ProductCategory.listCategories());
                        out.flush();
                        Product product = catalogue.createProduct(in.read(), in.read());
                        out.println("product created, " + product.id);
                        out.flush();
                        break;
                    case 2:
                        out.println("enter product id, from one of these option and stock");
                        out.println(catalogue.getProducts());
                        out.flush();
                        out.println(catalogue.addStock(in.readInt(), in.readInt()));
                        out.flush();
                        break;
                    case 3:
                        out.println("enter product id and 0 to disable the product for sale");
                        out.println(catalogue.getProducts());
                        out.flush();
                        int id = in.readInt();
                        catalogue.getProduct(id).updateProductAvailability(in.readInt() != 0);
                        out.println("updated product: " + catalogue.getProduct(id));
                        out.flush();
                        break;
                    case 4:
                        out.println(catalogue.getProductsInOrder());
                        out.flush();
                        break;
                    case 5:
                        out.println("enter product id");
                        out.println(catalogue.getProducts());
                        out.flush();
                        catalogue.sale(in.readInt());
                        out.println("product sold successfully");
                        break;
                    default:
                        out.println(Order.getOrders().stream().map(o -> o + "\n").collect(Collectors.toList()));
                        out.flush();
                        break;
                }

                out.flush();
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }
    }
}
