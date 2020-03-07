/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.lambda;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TestLambda {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private Predicate<Integer> div3 = getPredicate(3), div2 = getPredicate(2), div6 = div2.and(div3);
    private static Consumer<Object> print = out::print;
    private static Consumer<Object> println = out::println;

    private static Predicate<Integer> getPredicate(int d) {
        return (t) -> t % d == 0;
    }

    public static void main(String[] args) throws IOException {
        TestLambda a = new TestLambda();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            Thread thread = new Thread(() -> out.println("Ashok Thread Functional"));
            thread.start();
            int n = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n);
            Comparator<String> comparator = (a, b) -> -1;
            comparator.reversed();
            Comparator.nullsLast((a, b) -> -1);
            out.flush();
        }
    }
}
