/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.silverman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.*;
import com.ashok.lang.problems.NumberInWords;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Silverman {
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
        c12p4();
    }

    private static void c12p4() throws IOException {
        int n = in.readInt();
        Fraction[] fractions = new Fraction[n + 1];
        IntStream.range(2, n + 1)
                .parallel()
                .forEach(i -> {
                    Fraction fraction = IntStream.range(1, i)
                            .filter(j -> ModularArithmatic.gcd(i, j) == 1)
                            .mapToObj(j -> new Fraction(1, j))
                            .reduce((a, b) -> a.add(b)).get();

                    fractions[i] = fraction.toReducedFraction();
                });

        out.println(fractions);
    }

    private static void c12p3() throws IOException {
        int[] primes = Prime.generatePrimes(in.readInt());
        Fraction[] fractions = new Fraction[primes.length];
        fractions[1] = new Fraction(3, 2);
        IntStream.range(2, primes.length)
                .forEach(i -> {
                    int val = primes[i];
                    Fraction fraction = fractions[i - 1];
                    for (int j = primes[i - 1]; j < val; j++) {
                        fraction = fraction.add(new Fraction(1, j));
                    }

                    fractions[i] = fraction.toReducedFraction();
                });

        IntStream.range(2, primes.length)
                .forEach(i -> out.println(primes[i] + "\t" + fractions[i].toReducedFraction().toString()));
    }

    private static void c11p13() {
        for (int i = 2; i <= 10; i++) {
            long res = Power.pow(i, 1000, 10000);
            out.println(i + "\t" +  res);
        }

        out.flush();
    }

    private static void prob3n1() {
        Map<Integer, Integer> steps = new TreeMap<>();
        IntStream.range(2, 1000).forEach(n -> prob3n1(steps, n));
        steps.entrySet().forEach(e -> out.println(e.getKey() + "\t" + e.getValue()));
        Map<Integer, Integer> vals = new TreeMap<>();
        int prev = 0, count = 1;
        for (Map.Entry<Integer, Integer> e : steps.entrySet()) {
            if (e.getValue() == prev) count++;
            else {
                vals.put(e.getKey() - count, count);
                count = 1;
            }
            prev = e.getValue();
        }
        vals.remove(0);
        Map<Integer, Integer> filtered = new TreeMap<>();
        vals.entrySet().stream().filter(e -> e.getValue() >= 3).filter(e -> e.getKey() < 1000).forEach(e -> filtered.put(e.getKey(), e.getValue()));
        vals.entrySet().forEach(e -> out.println(e.getKey() + "\t" + e.getValue()));
        out.flush();
    }

    private static void prob3n1(Map<Integer, Integer> steps, final int num) {
        int n = num;
        if (steps.containsKey(n)) return;
        Set<Integer> values = new TreeSet<>();
        Deque<Integer> stack = new LinkedList<>();
        int count = 1;
        while (n > 1) {
            if (values.contains(n)) break;
            stack.push(n);
            n = next3n1(n);
            count++;
        }

        if (n != 1) { // it's loop
            out.println("it's loop for " + num);
            out.flush();
            for (int v : stack) {
                steps.put(v, count);
            }

            return;
        }

        count = 1;
        for (int v : stack) {
            steps.put(v, count++);
        }
    }

    private static int next3n1(int n) {
        if ((n & 1) == 0) return n >> 1;
        return (n << 1) + n + 1;
    }
}
