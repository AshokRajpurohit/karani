/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Weissen {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Weissen a = new Weissen();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    final static class Stock {

        Map<String, Counter> map = new ConcurrentHashMap<>();

        public void putNewPrice(String symbol, double price) {
            // YOUR CODE HERE
            getCounter(symbol).addValue(price);
        }

        public double getAveragePrice(String symbol) {
            // YOUR CODE HERE
            return getCounter(symbol).getAverageValue();
        }

        public int getTickCount(String symbol) {
            // YOUR CODE HERE
            return getCounter(symbol).count;
        }

        private Counter getCounter(String symbol) {
            if (map.containsKey(symbol))
                return map.get(symbol);

            map.putIfAbsent(symbol, new Counter());
            return map.get(symbol);
        }

        private final static class Counter {
            volatile double sum = 0.0;
            volatile int count = 0;

            synchronized void addValue(double value) {
                sum += value;
                count++;
            }

            synchronized double getAverageValue() {
                if (count == 0)
                    return sum;

                return sum / count;
            }
        }

    }

    final static class WeightLog {

        private static double getMidPoint(Log log) {
            final double limit = 0.0001;
            double start = 0.0, end = log.getLength(), totalWeight = log.weightUpto(end), halfWeight = totalWeight / 2;
            while (end - start >= limit) {
                double mid = (start + end) / 2;
                double weight = log.weightUpto(mid);
                if (weight > halfWeight)
                    end = mid;
                else if (weight < halfWeight)
                    start = mid;
                else
                    return mid;
            }

            return (start + end) / 2;
        }

        interface Log {
            double getLength();

            double weightUpto(double distance);
        }

    }
}
