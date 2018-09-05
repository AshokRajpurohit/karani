/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.flipkart;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Driver class to run the test cases.
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Driver {
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
        while (true) {
            out.println("what do you want? 1. Add restaurent\n2. order food\n3. stats\n4. completed-orders for restaurent\n5. add menu items to restaurent.");
            out.flush();
            int choice = in.readInt();
            switch (choice) {
                case 1:
                    System.out.println("Add restaurent information like: <name>, <address>, <capacity>");
                    Restaurent restaurent = RestaurentFactory.newRestaurent(new RestaurentModel(in.read(), in.read(), in.readInt()));
                    System.out.println("Restaurent added, id: " + restaurent.id);
                    break;
                case 2:

            }
        }
    }
}
