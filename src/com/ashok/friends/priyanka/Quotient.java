/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name: Quotient for Priyanka
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Quotient {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static char opening = '[', closing = ']';

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
            int[] ar = in.readIntArray(in.readInt());
            int[] br = in.readIntArray(in.readInt());
            List<Integer> list = Arrays.stream(ar).mapToObj(n -> n).collect(Collectors.toList());
            List<Integer> list1 = Arrays.stream(br).mapToObj(n -> n).collect(Collectors.toList());
            out.println(ReductorArray.comparatorValue(list, list1, in.readInt()));
            out.flush();
        }
    }

    public static List<Integer> circuitsOutput(List<String> circuitsExpression) {
        return circuitsExpression.stream()
                .map(expression -> evaluate(expression))
                .collect(Collectors.toList());
    }

    private static int evaluate(String expression) {
        char[] chars = expression.toCharArray();
        Deque<Integer> stack = new LinkedList<>();
        for (int i = expression.length() - 2; i > 0; i--) {
            if (chars[i] == opening || chars[i] == closing || chars[i] == ',')
                continue;

            if (chars[i] == '1' || chars[i] == 0) {
                stack.push(chars[i] - '0');
            } else {
                char ops = chars[i];
                if (ops == '!') {
                    stack.push(toInt(!toBool(stack.pop())));
                } else {
                    stack.push(evaluate(ops, stack.pop(), stack.pop()));
                }
            }
        }
        return stack.pop();
    }


    private static int evaluate(char ops, int v1, int v2) {
        if (ops == '|') {
            return v1 | v2;
        } else if (ops == '&') {
            return v1 & v2;
        }

        throw new RuntimeException("invalid ops " + ops);
    }

    private static boolean toBool(int v) {
        return v != 0;
    }

    private static int toInt(boolean v) {
        return v ? 1 : 0;
    }

    final static class TripletProblem {
        public static long totalTriplets(List<Integer> capacity, long desiredCapacity) {
            int limit = 1000000;
            if (desiredCapacity > limit) return 0;
            int[] ar = capacity.stream().mapToInt(n -> n).toArray();
            int min = Arrays.stream(ar).min().getAsInt(), max = Arrays.stream(ar).max().getAsInt();
            int range = max - min + 1;
            int[] pairCounts = new int[limit], counts = new int[limit], triplets = new int[limit];

            IntStream.range(2, ar.length)
                    .forEach(i -> {
                        triplets[ar[i] + ar[i - 1] + ar[i - 2] - min]++;
                    });

            counts[ar[0]]++;
            IntStream.range(1, ar.length)
                    .forEach(i -> {
                        counts[ar[i] - min]++;
                        pairCounts[ar[i] + ar[i - 1] - min]++;
                    });

            if (desiredCapacity < min * 3) return 0;
            long result = 0;
            for (int i = 0; i < ar.length; i++) {
                long rem = desiredCapacity - ar[i];
                int count = pairCounts[(int) rem - min];
                if (i > 0 && (ar[i - 1] + ar[i] == rem)) count--;
                if (i < ar.length - 1 && (ar[i] + ar[i + 1] == rem)) count--;
                if (i > 0 && i < ar.length - 1 && (ar[i] + ar[i - 1] + ar[i + 1] == desiredCapacity)) count--;
                result += count;
            }

            return result;
        }
    }

    final static class ReductorArray {
        public static int comparatorValue(List<Integer> a, List<Integer> b, int d) {
            b.sort(Integer::compareTo);
            a.sort(Integer::compareTo);

            Iterator<Integer> bIter = b.iterator(), aIter = a.iterator();
            int rangeEnd = bIter.next(), rangeStart = Integer.MIN_VALUE;
            int comparatorValue = 0, num = aIter.next();

            while (true) {
                if (num > rangeEnd) {
                    rangeStart = rangeEnd;
                    rangeEnd = bIter.hasNext() ? bIter.next() : Integer.MAX_VALUE;
                    continue;
                } else if ((num > rangeStart + d) && (num < rangeEnd - d)) {
                    comparatorValue++;
                }
                if (!aIter.hasNext()) break;
                num = aIter.next();
            }

            return comparatorValue;
        }
    }

    final static class SillyStocks {
        public static long maximumProfit(List<Integer> price) {
            if (price.size() == 1) return 0;
            int[] ar = price.stream().mapToInt(n -> n).toArray();
            int len = ar.length;
            long profit = 0;
            int max = ar[len - 1];
            for (int i = len - 2; i >= 0; i--) {
                if (ar[i] < max)
                    profit += max - ar[i];
                else
                    max = ar[i];
            }

            return profit;
        }
    }
}
