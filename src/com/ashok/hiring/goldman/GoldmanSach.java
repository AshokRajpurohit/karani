/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.goldman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link: Email HackerRank Test
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GoldmanSach {
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
            int max = in.readInt(), n = in.readInt(), m = in.readInt();
            List<List<Integer>> list = new LinkedList<>();
            for (int i = 0; i < n; i++) list.add(toList(in.readIntArray(3)));
            out.println(findTruckCargo(max, list));
//            out.println(findQualifiedNumbers(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static List<Integer> toList(int[] ar) {
        List<Integer> list = new LinkedList<>();
        for (int e : ar) list.add(e);
        return list;
    }

    static List<Integer> findTruckCargo(int maxCargoWeight, List<List<Integer>> cargoList) {
        ProfitCalculator calculator = new ProfitCalculator(toCargoArray(cargoList), maxCargoWeight);
        calculator.calculate();
        return calculator.getProfit();
    }

    private static Cargo[] toCargoArray(List<List<Integer>> cargoList) {
        Counter counter = new Counter();
        return cargoList.stream()
                .map(t -> new Cargo(t.get(0), t.get(1), t.get(2), counter.get()))
                .sorted((a, b) -> a.weight - b.weight)
                .toArray(t -> new Cargo[t]);
    }

    private static final Cargo INVALID_CARGO = new Cargo(-1, -1, -1, -1);

    final static class Cargo {
        final int id, weight, profit, order;

        Cargo(int id, int weight, int profit, int order) {
            this.id = id;
            this.weight = weight;
            this.profit = profit;
            this.order = order;
        }
    }

    final static class ProfitCalculator {
        final Cargo[] cargos;
        final int[] weightSum;
        int maxProfit = -1;
        final int cargoCapacity, size;
        Node[][] nodes;
        Node result = INVALID_NODE;

        ProfitCalculator(Cargo[] cargos, int capacity) {
            size = cargos.length;
            this.cargos = cargos;
            cargoCapacity = capacity;
            weightSum = new int[size];
            initialize();
        }

        private void initialize() {
            weightSum[0] = cargos[0].weight;
            for (int i = 1; i < size; i++)
                weightSum[i] = weightSum[i - 1] + cargos[i].weight;
        }

        private void calculate() {
            if (cargoCapacity < cargos[0].weight) return;
            if (cargoCapacity >= weightSum[size - 1]) {
                result = useAll();
                return;
            }
            nodes = new Node[size][cargoCapacity + 1];
            result = calculate(size - 1, cargoCapacity);
        }

        private Node calculate(int index, int weight) {
            if (index < 0 || weight < cargos[0].weight) return INVALID_NODE;
            if (nodes[index][weight] != null) return nodes[index][weight];
            if (weight > weightSum[index]) return calculate(index, weightSum[index]);
            if (weight == weightSum[index]) {
                Node node = new Node(cargos[index], calculate(index - 1, weight - cargos[index].weight));
                nodes[index][weight] = node;
                return node;
            }

            Node n1 = calculate(index - 1, weight), n2 = calculate(index - 1, weight - cargos[index].weight);
            Node node = n1.profit > n2.profit ? n1 : new Node(cargos[index], n2);
            nodes[index][weight] = node;
            return node;
        }


        private Node useAll() {
            Node node = new Node(cargos[0]);
            for (int i = 1; i < size; i++)
                node = new Node(cargos[i], node);

            maxProfit = node.profit;
            return node;
        }

        private List<Integer> getProfit() {
            Node node = result;
            List<Cargo> list = new LinkedList<>();
            while (node != INVALID_NODE) {
                list.add(node.cargo);
                node = node.next;
            }

            List<Integer> res = list.stream().sorted((a, b) -> a.order - b.order).map(t -> new Integer(t.id)).collect(Collectors.toList());
            res.add(result.profit);
            return res;
        }
    }

    private static final Node INVALID_NODE = new Node(INVALID_CARGO);

    final static class Node {
        Node next = INVALID_NODE;
        final Cargo cargo;
        int profit;

        Node(Cargo cargo) {
            this.cargo = cargo;
            profit = cargo.profit;
        }

        Node(Cargo cargo, Node next) {
            this(cargo);
            this.next = next;
            if (next != INVALID_NODE)
                profit += next.profit;
        }
    }

    final static class Counter {
        int index = 0;

        int get() {
            return index++;
        }
    }

    static String findQualifiedNumbers(int[] numberArray) {
        StringBuilder sb = new StringBuilder();
        int[] res = Arrays.stream(numberArray)
                .filter(n -> validate(n))
                .sorted()
                .toArray();

        if (res.length == 0) return "-1";
        sb.append(res[0]);
        for (int i = 1; i < res.length; i++) sb.append(',').append(res[i]);
        return sb.toString();
    }

    private static boolean validate(int v) {
        return validate(v, "1") && validate(v, "2") && validate(v, "3");
    }

    private static boolean validate(int n, String d) {
        return String.valueOf(n).contains(d);
    }
}
