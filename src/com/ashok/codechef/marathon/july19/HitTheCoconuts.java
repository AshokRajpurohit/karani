/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.july19;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Problem Name: Hit the Coconuts
 * Link: https://www.codechef.com/JULY19A/problems/CCC
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HitTheCoconuts {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    static final ForkJoinPool pool = new ForkJoinPool();


    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        Query[] queries = IntStream.range(0, t)
                .mapToObj(i -> {
                    try {
                        int n = in.readInt(), z = in.readInt();
                        return new Query(i, z, in.readIntArray(n));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    throw new RuntimeException("");
                })
                .toArray(s -> new Query[s]);

        Arrays.stream(queries)
//                .parallel()
                .map(query -> pool.submit(() -> query.calculate(), 1))
                .forEach(task -> {
                    try {
                        task.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        Arrays.stream(queries)
                .forEach(query -> sb
                        .append(query.result)
                        .append('\n')
                );
        out.println(sb);
    }

    private static void test() throws IOException {
        Output output = new Output();
        while (true) {
            int n = in.readInt(), z = in.readInt();
            int start = in.readInt(), end = in.readInt();
            while (true) {
                int[] ar = Generators.generateRandomIntegerArray(n, start, end);
//                process(ar, z);
                CoconutHitCalculator hit1 = new CoconutHitCalculatorPartitionImpl(ar, z);
                CoconutHitCalculator hit2 = new CoconutHitCalculatorMultiPartitionImpl(ar.clone(), z);
                try {
                    int actual = hit2.calculate(), expected = hit1.calculate();
                    out.println(expected + ", " + actual + " => " + (actual == expected));
//                    Assert.check(actual <= expected, "not matching, expected: " + expected + ", actual: " + actual);
                } catch (Throwable e) {
                    e.printStackTrace();
                    output.print(ar);
                    output.print(z);
                    output.flush();
                    hit1 = new CoconutHitCalculatorMultiPartitionImpl(ar, z);
                    hit1.calculate();
                    break;
                }
                out.flush();
            }
        }
    }

    private static int process(int[] ar, int z) {
        CoconutHitCalculator coconutHitCalculator = getCoconutHitCalculator(ar, z);
        return coconutHitCalculator.calculate();
//        return Math.min(new CoconutHitCalculatorPartitionImpl(ar, z).calculate(), new CoconutHitCalculatorImpl(ar, z).calculate());
    }

    private static boolean allEquals(int[] ar) {
        int min = Arrays.stream(ar).min().getAsInt();
        int max = Arrays.stream(ar).max().getAsInt();
        return min == max;
    }

    private static int targetSingleCoconut(int[] ar) {
        Arrays.sort(ar);
        int result = Integer.MAX_VALUE;
        for (int i = ar.length - 1, j = 1; i >= 0; i--, j++)
            result = Math.min(result, ar[i] * j);

        return result;
    }

    private static int targetAllCoconuts(int[] ar) {
        return Arrays.stream(ar).sum();
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    private static CoconutHitCalculator getCoconutHitCalculator(int[] ar, int z) {
        if (z == 1) return CoconutHitCalculators.newSingleCoconutCalculator(ar);
        if (z == ar.length) return CoconutHitCalculators.newAllCoconutsHitCalculator(ar);
        if (allEquals(ar)) return CoconutHitCalculators.newAllEqualCoconutHitCalculator(ar, z);
        return CoconutHitCalculators.newMultiPartCoconutHitCalculator(ar, z);
    }

    final static class SumArray {
        private final int[] array, sums;
        private final int size;

        SumArray(int[] ar) {
            array = ar;
            size = ar.length;
            sums = new int[size];
            initialize();
        }

        private void initialize() {
            sums[0] = array[0];
            for (int i = 1; i < size; i++) {
                sums[i] = sums[i - 1] + array[i];
            }
        }

        public int getSum(int from, int to) {
            return getValueAtIndex(to) - getValueAtIndex(from - 1);
        }

        private int getValueAtIndex(int index) {
            return validateIndex(index) ? sums[index] : 0;
        }

        private boolean validateIndex(int index) {
            return index >= 0;
        }
    }

    final static class CoconutHitCalculators {
        static CoconutHitCalculator newSingleCoconutCalculator(int[] ar) {
            return () -> targetSingleCoconut(ar);
        }

        static CoconutHitCalculator newAllCoconutsHitCalculator(int[] ar) {
            return () -> Arrays.stream(ar).sum();
        }

        static CoconutHitCalculator newTwoPartitionCoconutHitCalculator(int[] ar, int z) {
            return new CoconutHitCalculatorPartitionImpl(ar, z);
        }

        static CoconutHitCalculator newCoconutHitCalculator(int[] ar, int z) {
            return new CoconutHitCalculatorImpl(ar, z);
        }

        static CoconutHitCalculator newMultiPartCoconutHitCalculator(int[] ar, int z) {
            return new CoconutHitCalculatorMultiPartitionImpl(ar, z);
        }

        static CoconutHitCalculator newAllEqualCoconutHitCalculator(int[] ar, int z) {
            return () -> IntStream.range(0, z).map(i -> ar[i]).sum();
        }

        static CoconutHitCalculator bestCoconutHitCalculator(CoconutHitCalculator c1, CoconutHitCalculator c2) {
            return () -> Math.min(c1.calculate(), c2.calculate());
        }
    }

    @FunctionalInterface
    interface CoconutHitCalculator {
        int calculate();
    }

    final static class CoconutHitCalculatorPartitionImpl implements CoconutHitCalculator {
        final int coconuts, required;
        private final int[] coconutStrengths;
        private final SumArray sumArray;

        CoconutHitCalculatorPartitionImpl(final int[] coconutStrengths, final int required) {
            this.required = required;
            this.coconuts = coconutStrengths.length;
            this.coconutStrengths = coconutStrengths;
            Arrays.sort(this.coconutStrengths);
            sumArray = new SumArray(this.coconutStrengths);
        }

        public int calculate() {
            int result = calculateMinHitsByGroup(required);
            for (int i = 1; i <= required; i++) {
                result = Math.min(result, sumArray.getSum(coconuts - i, coconuts - 1) + calculateMinHitsByGroup(required - i));
            }

            return result;
        }

        private int calculateMinHitsByGroup(int size) {
            if (size == 0) return 0;
            int used = required - size, fromIndex = coconuts - required, lastIndex = fromIndex + size - 1, buffer = 0;
            int hits = Integer.MAX_VALUE;
            while (fromIndex >= 0) {
                int coconutStrength = coconutStrengths[lastIndex];
                int hitCount = coconutStrength * buffer + sumArray.getSum(fromIndex, lastIndex);
                hits = Math.min(hitCount, hits);
                fromIndex--;
                lastIndex--;
                buffer++;
            }

            return hits;
        }
    }

    /**
     * for each element, let's consider it ith element from left, check for all (i - 1)th element in left side
     * and calculate the cost of choosing it as ith element and the other element as previous element. store the
     * minimum value found as choosing ith element for this one.
     */
    final static class CoconutHitCalculatorMultiPartitionImpl implements CoconutHitCalculator {
        final int coconuts, required, totalSum;
        private final int[] array;
        private int[][] matrix;
        private final SumArray sumArray;

        CoconutHitCalculatorMultiPartitionImpl(final int[] array, int z) {
            Arrays.sort(array);
            reverse(array);
            this.array = array;
            coconuts = array.length;
            required = z;
            matrix = new int[coconuts + 1][z + 1];
            sumArray = new SumArray(array);
            totalSum = sumArray.getSum(0, coconuts - 1);
            initialize();
        }


        private void initialize() {
            int sum = sumArray.getSum(0, required - 1);
            Arrays.stream(matrix).forEach(ar -> Arrays.fill(ar, sum));
        }

        /**
         * index is from 0 to {@link #coconuts} - 1 both inclusive.
         *
         * @param index
         * @return
         */
        private void calculate(final int index) {
            matrix[index][1] = array[index] * (index + 1);//sumArray.getSum(0, index); // if element is the first element from left.
            int maxPos = Math.min(coconuts - index, required);
            IntStream.range(2, maxPos + 1)
//                    .parallel()
                    .mapToObj(i -> pool.submit(() -> {
                        matrix[index][i] = getCostForIndexAndPosition(index, i);
                    }, 1))
                    .forEach(task -> {
                        try {
                            task.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
        }

        private int getCostForIndexAndPosition(final int index, final int pos) {
            return array[index] * (index + 1)
                    + IntStream.range(index + 1, coconuts)
                    .map(i -> matrix[i][pos - 1] - array[i] * (index + 1))
                    .min()
                    .getAsInt();
        }

        public int calculate() {
            for (int i = coconuts - 1; i >= 0; i--) calculate(i);
            return IntStream.range(0, coconuts).map(i -> matrix[i][required]).min().getAsInt();
        }
    }

    final static class CoconutHitCalculatorImpl implements CoconutHitCalculator {
        final int coconuts, required, totalSum;
        private final int[] array;
        private Pair[][] matrix;
        private final SumArray sumArray;

        CoconutHitCalculatorImpl(final int[] array, int z) {
            Arrays.sort(array);
            this.array = array;
            coconuts = array.length;
            required = z;
            matrix = new Pair[coconuts + 1][z + 1];
            sumArray = new SumArray(array);
            totalSum = sumArray.getSum(0, coconuts - 1);
            initialize();
        }

        private void initialize() {
            int sum = sumArray.getSum(0, required - 1);
            Arrays.stream(matrix).forEach(ar -> Arrays.fill(ar, INVALID_PAIR));
        }

        private Pair calculate(final int n, final int z) {
            Pair pair = matrix[n][z];
            if (pair.isValid()) return pair;
            if (z == 0) return EMPTY_PAIR;
            if (n < z) return INVALID_PAIR;
            if (n == z) return new Pair(sumArray.getSum(0, z - 1), array[n - 1]);
            int result = totalSum;
            Pair p1 = calculate(n - 1, z - 1), p2 = calculate(n - 1, z);
            result = Math.min(p1.first + array[n - 1], p2.first + p2.second);
            int compare = Integer.compare(p1.first + array[n - 1], p2.first + p2.second);
            if (compare < 0)
                pair = new Pair(p1.first + array[n - 1], array[n - 1]);
            else if (compare > 0)
                pair = new Pair(p2.first + p2.second, p2.second);
            else
                pair = new Pair(p2.first + p2.second, Math.min(array[n - 1], p2.second));

            matrix[n][z] = pair;

            return pair;
        }

        public int calculate() {
            return calculate(coconuts, required).first;
        }
    }

    private static final Pair INVALID_PAIR = new Pair(-1, -1);
    private static final Pair EMPTY_PAIR = new Pair(0, 0);

    final static class Pair {
        final int first, second;

        Pair(final int first, final int second) {
            this.first = first;
            this.second = second;
        }

        boolean isValid() {
            return this != INVALID_PAIR;
        }

        public String toString() {
            return first + ", " + second;
        }
    }

    final static class Query {
        final int required, index;
        final int[] strengths;
        private int result = -1;

        Query(final int index, final int required, final int[] strengths) {
            this.index = index;
            this.required = required;
            this.strengths = strengths;
        }

        void calculate() {
            result = process(strengths, required);
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}