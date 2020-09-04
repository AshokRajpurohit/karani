/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.silverman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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
//        ContinuedFraction fraction = new SquareRootContinuedFraction(5);
//        List<ContinuedFraction> fractions = IntStream.range(0, 10).mapToObj(i -> fraction.nextFraction()).collect(Collectors.toList());
//        fractions.add(0, fraction);
//        out.println(ContinuedFraction.evaluate(fractions.stream().map(f -> f.toFraction()).collect(Collectors.toList())));
//        c37p1();
//        c37p14();
        c37p16();
//        c33p4();
//        c33p2();
//        c12p4();
    }

    private static void c37p14() {
        IntFunction<long[]> fibFunc = mod -> {
            int n = mod * mod;
            long[] fibs = new long[n];
            fibs[1] = fibs[0] = 1;
            for (int i = 2; i < n; i++) fibs[i] = (fibs[i - 1] + fibs[i - 2]) % mod;
            return fibs;
        };

        ToIntFunction<long[]> periodFunc = fibs -> {
            for (int i = 3; i < fibs.length; i++) {
                if (fibs[i] == 1 && fibs[i - 1] == 0) return i;
            }

            return fibs.length;
        };

        IntUnaryOperator calc = n -> periodFunc.applyAsInt(fibFunc.apply(n));

        int n = 100;
        String result = IntStream.range(2, n)
                .mapToObj(i -> i + "\t" + calc.applyAsInt(i))
                .collect(Collectors.joining("\n"));

        out.println(result);
    }

    private static void c37p16() {
        IntFunction<long[]> fibFunc = mod -> {
            int n = mod * mod;
            long[] fibs = new long[n];
            fibs[1] = fibs[0] = 1;
            for (int i = 2; i < n; i++) fibs[i] = (fibs[i - 1] + fibs[i - 2]) % mod;
            return fibs;
        };

        ToIntFunction<long[]> periodFunc = fibs -> {
            for (int i = 3; i < fibs.length; i++) {
                if (fibs[i] == 1 && fibs[i - 1] == 0) return i;
            }

            return fibs.length;
        };

        IntUnaryOperator calc = n -> periodFunc.applyAsInt(fibFunc.apply(n));

        int n = 1000;
        int[] primes = Prime.generatePrimes(n);
        IntPredicate check = p -> calc.applyAsInt(p) == 2 * p + 2;
        String result = Arrays.stream(primes)
                .filter(p -> p % 5 == 3)
//                .filter(p -> p % 3 == 2)
                .filter(check.negate())
                .mapToObj(p -> p + "\t" + calc.applyAsInt(p))
                .collect(Collectors.joining("\n"));

        out.println(result);
    }

    private static void c37p1() {
        int n = 92, bn = 1000;
        long[] fibs = new long[n];
        fibs[0] = fibs[1] = 1;
        for (int i = 2; i < n; i++) fibs[i] = fibs[i - 1] + fibs[i - 2];
        LongPredicate primeCheck = p -> {
            if (Numbers.isEven(p)) return false;
            int limit = (int) Math.sqrt(p);
            for (int i = 3; i <= limit; i+=2) if (p % i == 0) return false;
            return true;
        };
        BigInteger[] bigFibs = new BigInteger[bn];
        bigFibs[0] = bigFibs[1] = BigInteger.ONE;
        for (int i = 2; i < bigFibs.length; i++) bigFibs[i] = bigFibs[i - 1].add(bigFibs[i - 2]);
        for (BigInteger bi : bigFibs) bi.isProbablePrime(30);
//        String result = IntStream.range(1, n + 1)
//                .mapToObj(i -> i + "\t" + fibs[i - 1])
//                .collect(Collectors.joining("\n"));
//        out.print(result);
        String result = IntStream.range(3, 92)
//                .filter(i -> primeCheck.test(fibs[i - 1]))
                .mapToObj(i -> i + "\t" + fibs[i - 1])
                .collect(Collectors.joining("\n"));

        out.println(result);

        result = IntStream.range(3, 92)
                .mapToObj(i -> "\t" + (fibs[i - 1] * fibs[i - 1] - fibs[i - 3] * fibs[i - 3]))
                .collect(Collectors.joining("\n"));

        out.println(result);

        result = IntStream.range(3, bigFibs.length + 1)
                .filter(i -> bigFibs[i - 1].isProbablePrime(30))
                .mapToObj(i -> i + "\t" + bigFibs[i - 1])
                .collect(Collectors.joining("\n"));

//        result = IntStream.range(3, 45)
//                .mapToObj(i -> {
//                    StringJoiner joiner = new StringJoiner("\n\t");
//                    joiner.add(i + "\t" + fibs[i - 1]);
//                    for (int k = 2; k < n; k++) {
//                        if (i * k >= n) break;
//                        if (!ModularArithmatic.checkCoprimes(i, k)) continue;
//                        int j = i * k;
//                        long fibi = fibs[i - 1], fibk = fibs[k - 1], fibj = fibs[j - 1], multi = fibi * fibk;
//                        joiner.add(k + "\t" + fibk + "\t" + j + "\t" + fibj + "\t" + fibj / multi);
////                        joiner.add(j + "\t" + fibk + "\t" + fibj + "\t" + multi + "\t" + (fibj % (multi * multi)) / multi);
//                    }
//
//                    return joiner.toString();
//                }).collect(Collectors.joining("\n"));
        out.println(result);
    }

    private static void c34p2() {
        int[] var = new int[]{433, 3};
        double sqrt11 = Math.sqrt(11);
        IntFunction<BiFunction<Long, Long, Boolean>> function = m -> (x, y) -> x * x - 11 * y * y == m;
        int limit = 10000;
        for (int e : var) {
            BiFunction<Long, Long, Boolean> bf = function.apply(e);
            for (long y = 1; y < limit; y++) {
                long x = (long) (y * sqrt11 + e);
                if (bf.apply(x, y)) {
                    out.println(x, y, e);
                    break;
                }
            }
        }

        out.flush();
    }

    private static void c33p4() throws IOException {
        long[] ys = new long[]{12, 17, 29, 41, 70, 99, 169, 239, 408, 577, 985, 1393, 2378, 3363};
        double irrational = Math.sqrt(2);
        ToDoubleBiFunction<Long, Long> equation = (x, y) -> Math.abs(x - irrational * y);
        LongFunction<Predicate<Long>> xChecker = y -> x -> equation.applyAsDouble(x, y) < 1.0 / y;
        long[] xar = Arrays.stream(ys)
                .map(y -> {
                    LongPredicate xCheck = x -> xChecker.apply(y).test(x);
                    return LongStream.range(y, 2 * y)
                            .filter(xCheck)
                            .findAny()
                            .orElse(-1);
                }).filter(x -> x != -1)
                .toArray();

        out.print(xar);
        out.flush();
    }

    private static void c33p2() throws IOException {
        double gamma = (Math.sqrt(5) + 1) / 2;
        ToDoubleBiFunction<Long, Long> equation = (x, y) -> Math.abs(x - gamma * y);
        ToDoubleFunction<Fraction> eq = f -> equation.applyAsDouble(f.numerator, f.denominator);
        Fraction result = Fraction.UNIT_FRACTION;
        double min = equation.applyAsDouble(result.numerator, result.denominator);
        for (int y = 1; y <= 1000; y++) {
            int x = (int) (gamma * y);
            double temp = equation.applyAsDouble(1L * x, 1L * y);
            if (temp < min) {
                min = temp;
                result = new Fraction(x, y);
            }

            x++;
            temp = equation.applyAsDouble(1L * x, 1L * y);
            if (temp < min) {
                min = temp;
                result = new Fraction(x, y);
            }
        }

        out.println(result);
        out.flush();
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
            out.println(i + "\t" + res);
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
