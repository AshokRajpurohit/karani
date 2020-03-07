/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.test;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Test {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            test();
//            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        CompletableFuture<Integer> completableFuture = new CompletableFuture();
        List[] lists = new LinkedList[10];
        IntStream.range(0, 10).forEach(i -> lists[i] = new LinkedList());
        out.println(lists);
        while (true) {
            testSet();
            testStack();
        }
    }

    private static void test() {
        Base b = new Derived();
        b.show();
        System.out.println(b.getClass().getSimpleName());
        ((Derived)b).show();
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2);
        list.add(8);
        list.add(5);
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        out.println(iterator.next());
        list.add(11);
//        Collections.reverse(list);
        while (iterator.hasNext()) {
            out.println(iterator.next());
        }
    }

    private static class Base {
        public static void show() {
            System.out.println("Base");
        }
    }

    private static class Derived extends Base {
        public static void show() {
            System.out.println("Derived");
        }
    }

    private static void testSet() throws IOException {
        while (true) {
            int n = in.readInt(), m = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, m);
            int[] ops = Generators.generateRandomIntegerArray(n, 3);
            testSet(ar, ops);
            testSet(ar, ops);
            out.println("done");
            out.flush();
        }
    }

    private static void testStack() throws IOException {
        while (true) {
            int n = in.readInt(), m = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, m);
            int[] ops = Generators.generateRandomIntegerArray(n, 4);
            testStack(ar, ops);
            testStack(ar, ops); // for debugging
            out.println("done");
            out.flush();
        }
    }

    private static void testSet(int[] ar, int[] ops) {
        int n = ar.length;
        Set<Integer> intSet = new HashSet<>();
        SetImplementation set = new SetImplementation(n);

        for (int i = 0; i < n; i++) {
            switch (ops[i]) {
                case 0: // add element
                    intSet.add(ar[i]);
                    set.add(ar[i]);
                    break;
                case 1: // contains element
                    checkEquals(intSet.contains(ar[i]), set.contains(ar[i]));
                    break;
                case 2: // remove element
                    checkEquals(intSet.remove(ar[i]), set.remove(ar[i]));
                    break;
            }
        }
    }

    private static void checkEquals(boolean expected, boolean actual) {
        if (expected != actual)
            print(expected, actual);
    }

    private static void testStack(int[] ar, int[] ops) {
        Deque<Integer> stack = new LinkedList<>();
        StackByLinkedList sbl = new StackByLinkedList();
        int n = ar.length;

        for (int i = 0; i < n; i++) {
            switch (ops[i]) {
                case 0: // push operation
                    stack.push(ar[i]);
                    sbl.push(ar[i]);
                    break;
                case 1: // peek operation
                    if (stack.isEmpty()) {
                        checkEquals(-1, sbl.peekOrTop());
                    } else
                        checkEquals(stack.peek(), sbl.peekOrTop());
                    break;
                case 2: // pop operation
                    if (stack.isEmpty()) {
                        checkEquals(-1, sbl.pop());
                    } else
                        checkEquals(stack.pop(), sbl.pop());
                    break;
                case 3: // iterate through the stack
                    break; // iterator is not available for sbl
            }
        }
    }

    private static void checkEquals(int expected, int actual) {
        if (expected != actual)
            print(expected, actual);
    }

    private static void print(Object expected, Object actual) {
        out.println("values are not matching, expected: " + expected + ", actual: " + actual);
        out.flush();
    }
}
