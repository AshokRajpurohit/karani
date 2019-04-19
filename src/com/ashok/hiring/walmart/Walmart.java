/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.walmart;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Walmart {
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
        CategoryService categoryService = new CategoryService();
        String[] categories = new String[]{"clothing", "electronics"};
        Arrays.stream(categories).forEach(c -> categoryService.newCategory(c));

        String[] cloths = new String[]{"t-shirt", "shirt", "pant", "jeans", "sari"},
                electronics = new String[]{"tv", "mobile", "fridge"};

        int[] clothPrices = Generators.generateRandomIntegerArray(cloths.length, 1000, 10000),
                electronicPrices = Generators.generateRandomIntegerArray(electronics.length, 10000, 1000000);

        ProductService productService = new ProductService();
        Category electCate = categoryService.getCategoryByName(categories[1]);
        List<Product> productList = IntStream.range(0, electronicPrices.length)
                .mapToObj(i -> productService.newProduct(electCate, electronics[i], electronicPrices[i]))
                .collect(Collectors.toList());

        Category clothCate = categoryService.getCategoryByName(categories[0]);
        productList.addAll(
                IntStream.range(0, clothPrices.length)
                        .mapToObj(i -> productService.newProduct(clothCate, cloths[i], clothPrices[i]))
                        .collect(Collectors.toList()));

        CartService cartService = new CartService();
        Product[] products = productList.stream().toArray(t -> new Product[t]);
        ArrayUtils.randomizeArray(products);
        int[] sizes = Generators.generateRandomIntegerArray(products.length >>> 1, 2, products.length);

        Cart[] carts = Arrays.stream(sizes).mapToObj(i -> {
            Cart cart = cartService.newCart();
            IntStream.range(0, i).forEach(j -> cart.addProduct(products[j]));
            return cart;
        }).toArray(t -> new Cart[t]);

        ArrayUtils.randomizeArray(carts);
        int len = carts.length, half = len >>> 1;
        IntStream.range(0, half + 1).mapToObj(i -> carts[i]).forEach(c -> {
            try {
                cartService.putOrder(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        IntStream.range(half - 1, len).mapToObj(i -> carts[i]).forEach(c -> {
            try {
                cartService.cancleOrder(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Arrays.stream(CartStatus.values()).forEach(cartStatus -> {
            out.println(cartService.cartsInStatus(cartStatus, CartService.SORT_BY_COUNT));
            out.println("-----------------------------------------------");
            out.println(cartService.cartsInStatus(cartStatus, CartService.SORT_BY_TOTAL_PRICE));
        });
    }
}
