package com.ashok.hiring.flipkart;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Processor {
    static Map<Restaurent, Set<Pair>> ordersInProcess = new ConcurrentHashMap<>();
    private static ExecutorService executor = Executors.newCachedThreadPool();

    static void addOrderInProcess(Restaurent restaurent, Order order, MenuItem item) {
        ordersInProcess.putIfAbsent(restaurent, new HashSet<>());
        ordersInProcess.get(restaurent).add(new Pair(order, item));
        executor.submit(() -> {
                    try {
                        restaurent.process(order, item);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ordersInProcess.get(restaurent).remove(new Pair(order, item));
                }
        );
    }

    final static class Pair {
        Order order;
        MenuItem item;

        Pair(Order order, MenuItem item) {
            this.order = order;
            this.item = item;
        }

        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return order == pair.order && item == pair.item;
        }
    }
}

