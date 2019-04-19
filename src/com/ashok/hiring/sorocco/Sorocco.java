/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.sorocco;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Sorocco {
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
            out.println(k_sum_set_count(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    public static int find_student(int n, List<List<Integer>> blood) {
        Set<Integer> suppliers = blood.stream().map(t -> t.get(0)).collect(Collectors.toSet());
        Set<Integer> allCollectors = blood.stream().map(t -> t.get(1)).collect(Collectors.toSet());
        List<Integer> collectors = blood.stream().map(t -> t.get(1)).filter(i -> !suppliers.contains(i)).distinct().collect(Collectors.toList());
        if (collectors.size() != 1) return -1;
        int ab = collectors.get(0);
        List<Integer> directSuppliers = blood.stream().filter(t -> ab == t.get(1)).map(t -> t.get(0)).collect(Collectors.toList());
        return directSuppliers.stream().filter(t -> allCollectors.contains(t)).count() == 0 ? 1 : -1;
    }

    static String sort_tickets(List<String> tickets) {
        Deque<String> travelPlan = new LinkedList<>();
        Map<String, String> cityMap = new HashMap<>(), reverseMap = new HashMap<>();
        tickets.forEach(t -> {
            String[] cities = t.split(" ");
            cityMap.put(cities[0], cities[1]);
            reverseMap.put(cities[1], cities[0]);
        });

        String city = tickets.stream().map(t -> t.split(" ")[0]).filter(t -> !reverseMap.containsKey(t)).findFirst().get();
        travelPlan.add(city);
        while (cityMap.containsKey(city)) {
            city = cityMap.get(city);
            travelPlan.addLast(city);
        }

        return String.join(" ", travelPlan);
    }

    private static int[][][] matrix = new int[51][51][11];

    private static void initializeMatrix() {
        Arrays.stream(matrix).forEach(t -> Arrays.stream(t).forEach(s -> Arrays.fill(s, -1)));
    }

    public static int k_sum_set_count(int target, int k) {
        initializeMatrix();
        return kSumSetCount(target, k, target);
    }

    private static int kSumSetCount(int target, int k, final int max) {
        if (k == 0 && target == 0) return 1;
        if (k == 0 || target == 0 || k > target || max == 0) return 0;
        if (k == target || k == 1) return 1;
        if (matrix[target][max][k] != -1) return matrix[target][max][k];
        int maxPossible = (target + k - 1) / k;
        int sum = 0;
        for (int v = max; v >= maxPossible; v--) {
            sum += kSumSetCount(target - v, k - 1, v);
        }

        matrix[target][max][k] = sum;
        return sum;
    }

    private static final long PUSH_QUERY = 1, DELETE_QUERY = 2, APPEND_QUERY = 3;

    public static List<Long> find_maximum_element(List<List<Long>> queries) {
        List<Long> maxValues = new LinkedList<>();
        MaxStack stack = new MaxStack();
        queries.forEach(query -> {
            long type = query.get(0), value = query.get(1);
            if (type == PUSH_QUERY)
                stack.push(value);
            else if (type == DELETE_QUERY)
                stack.pop();
            else if (type == APPEND_QUERY)
                maxValues.add(stack.max);
        });

        return maxValues;
    }

    final static class MaxStack {
        private Deque<Long> stack = new LinkedList<>(), maxStack = new LinkedList<>();
        private long max = Long.MIN_VALUE;

        public void push(long value) {
            max = Math.max(max, value);
            stack.push(value);
            maxStack.push(max);
        }

        private long pop() {
            maxStack.pop();
            max = maxStack.isEmpty() ? Long.MIN_VALUE : maxStack.peek();
            return stack.pop();
        }

        private long max() {
            return max;
        }
    }
}
