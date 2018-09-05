/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Problem Name: Pizza Delivery
 * Link: https://www.codechef.com/JULY18A/problems/PDELIV
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PizzaDelivery {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        PizzariaSet pizzariaSet = new PizzariaSet(n);
        Customer[] customers = new Customer[m];
        for (int i = 0; i < n; i++)
            pizzariaSet.newPizzaria(in.readInt(), in.readInt());

        for (int i = 0; i < m; i++) {
            int location = in.readInt(), k = in.readInt();
            int[] ar = in.readIntArray(k);
            Pizzaria[] pizzarias = new Pizzaria[k];
            for (int j = 0; j < k; j++)
                pizzarias[j] = pizzariaSet.get(ar[j] - 1);
            customers[i] = new Customer(i, location, pizzarias);
        }

        long[] result = process(customers, pizzariaSet);
        StringBuilder sb = new StringBuilder(m << 2);
        for (long price : result) sb.append(price).append('\n');
        out.print(sb);
    }

//    private static void play() throws IOException {
//        Output out = new Output();
//        Random random = new Random();
//        int start = in.readInt(), end = in.readInt();
//        int len = end + 1 - start;
//        while (true) {
//            int n = random.nextInt(len) + start, m = random.nextInt(len) + start;
//            int[] locations = Generators.generateRandomIntegerArray(n, 0, 20),
//                    prices = Generators.generateRandomIntegerArray(n, 1, 10);
//
//            PizzariaSet set = new PizzariaSet(n);
//            for (int i = 0; i < n; i++)
//                set.newPizzaria(locations[i], prices[i]);
//
//            locations = Generators.generateRandomIntegerArray(m, 0, 20);
//            int[] unwanted = Generators.generateRandomIntegerArray(m, 0, n - 2);
//            Customer[] customers = new Customer[m];
//            for (int i = 0; i < m; i++)
//                customers[i] = new Customer(i, locations[i], Generators.generateRandomIntegerArray(unwanted[i], 0, n - 1));
//
////            out.println("Starting the process");
////            out.flush();
//            long[] naive = naiveWay(customers.clone(), set);
////            out.println("naive done");
////            out.flush();
//            long[] process = process(customers.clone(), set);
////            out.println("process done");
////            out.println("Starting the naive");
////            out.flush();
//            boolean res = Arrays.equals(process, naive);
//            out.println(res);
//            out.flush();
//            if (!res) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(n).append(' ').append(m).append('\n');
//                for (Pizzaria p : set.pizzarias) {
//                    sb.append(p.location).append(' ').append(p.price).append('\n');
//                }
//
//                for (Customer c : customers) {
//                    sb.append(c.location).append(' ').append(c.unwantedPizzariaIds.length).append('\n');
//                    for (int e : c.unwantedPizzariaIds)
//                        sb.append(e + 1).append(' ');
//
//                    sb.append('\n');
//                }
//
//                out.println(sb);
//                out.print(process);
//                out.print(naive);
//                out.flush();
//                break;
//            }
//        }
//    }

    private static long[] naiveWay(Customer[] customers, PizzariaSet pizzariaSet) {
        Pizzaria[] pizzarias = pizzariaSet.pizzarias;
        long[] res = new long[customers.length];
        Arrays.stream(customers).forEach((c) -> {
            Pizzaria pizzaria = Arrays.stream(pizzariaSet.pizzarias).min((a, b) -> Long.compare(c.calculatePrice(a), c.calculatePrice(b))).get();
            res[c.id] = c.calculatePrice(pizzaria);
        });

        return res;
    }

    private static long[] process(Customer[] customers, PizzariaSet ps) {
        int pzs = ps.size;
        long[] res = new long[customers.length];
        Arrays.fill(res, Long.MAX_VALUE);
        Pizzaria[] pizzarias = ps.pizzarias.clone();
        Arrays.sort(pizzarias, (a, b) -> a.location - b.location);
        Arrays.sort(customers, (a, b) -> a.location - b.location);
        int[] nextSmallerPrices = nextSmaller(pizzarias, (a, b) -> a.price - b.price);
        int[] prevSmallerPrices = previousSmaller(pizzarias, (a, b) -> a.price - b.price);
        int pi = 0;
        for (Customer customer : customers) {
            while (pi < pzs && pizzarias[pi].location <= customer.location)
                pi++;

            pi--;
            int index = pi < 0 || pizzarias[pi].location < customer.location ? pi + 1 : pi;
            Pizzaria before = findBefore(customer, pizzarias, pi, prevSmallerPrices);
            customer.price = customer.calculatePrice(before);
            Pizzaria after = findAfter(customer, pizzarias, index, nextSmallerPrices);
            customer.price = Math.min(customer.price, customer.calculatePrice(after));
            res[customer.id] = customer.price;
            if (pi < 0) pi = 0;
        }

        return res;
    }

    private static Pizzaria findBefore(Customer c, Pizzaria[] pizzarias, int index, int[] prevSmallerIndices) {
        Pizzaria res = INVALID_PIZZARIA;
        while (index >= 0 && c.isUnwanted(pizzarias[index]))
            index--;

        if (index < 0) return res;
        res = pizzarias[index];
        long limit = (long) (Math.sqrt(c.calculatePrice(res)));
        while (index >= 0) {
            Pizzaria pizzaria = pizzarias[index];
            if (c.distance(pizzaria) > limit) break;
            index = prevSmallerIndices[index];
            while (index >= 0 && c.isUnwanted(pizzarias[index]) && c.distance(pizzarias[index]) <= limit)
                index--;

            if (c.isUnwanted(pizzaria) || c.calculatePrice(res) <= c.calculatePrice(pizzaria)) continue;
            res = pizzaria;
            limit = (long) (Math.sqrt(c.calculatePrice(res)));
        }
        return res;
    }

    private static Pizzaria findAfter(Customer c, Pizzaria[] pizzarias, int index, int[] nextSmallerIndices) {
        Pizzaria res = INVALID_PIZZARIA;
        long limit = (long) Math.sqrt(c.price);
        while (index < pizzarias.length && c.isUnwanted(pizzarias[index]) && c.distance(pizzarias[index]) < limit)
            index++;

        if (index >= pizzarias.length || c.isUnwanted(pizzarias[index])) return res;
        res = pizzarias[index];
        limit = (long) (Math.sqrt(c.calculatePrice(res)));
        while (index < pizzarias.length) {
            Pizzaria pizzaria = pizzarias[index];
            if (c.distance(pizzaria) > limit) break;
            index = nextSmallerIndices[index];
            while (index < pizzarias.length && c.isUnwanted(pizzarias[index]) && c.distance(pizzarias[index]) <= limit)
                index++;

            if (c.isUnwanted(pizzaria) || c.calculatePrice(res) <= c.calculatePrice(pizzaria)) continue;
            res = pizzaria;
            limit = (long) (Math.sqrt(c.calculatePrice(res)));
        }
        return res;
    }

    private static <T> int[] nextSmaller(T[] ar, Comparator<T> c) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && c.compare(ar[i], ar[j]) <= 0)
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static <T> int[] previousSmaller(T[] ar, Comparator<T> c) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && c.compare(ar[i], ar[j]) <= 0)
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static IntPredicate getForwardValidator(int[] ar) {
        return new IntPredicate() {
            int index = 0, size = ar.length;

            @Override
            public boolean test(int value) {
                while (index < size && ar[index] < value)
                    index++;

                return index < size && ar[index] == value;
            }
        };
    }

    private static IntPredicate getBackwardValidator(int[] ar) {
        return new IntPredicate() {
            int index = ar.length - 1;

            @Override
            public boolean test(int value) {
                while (index >= 0 && ar[index] > value)
                    index--;

                return index >= 0 && ar[index] == value;
            }
        };
    }

    private static final Customer GHOST_CUSTOMER = new Customer(-1, -1, new Pizzaria[0]);

    final static class Customer {
        final int id, location;
        private final int[] unwantedPizzariaIds;
        private final Pizzaria[] unwantedPizzarias;
        private long price = Long.MAX_VALUE;

        Customer(int id, int location, Pizzaria[] unwantedPizzarias) {
            this.id = id;
            this.location = location;
            unwantedPizzariaIds = Arrays.stream(unwantedPizzarias).mapToInt((t) -> t.id).toArray();
            this.unwantedPizzarias = unwantedPizzarias;
            Arrays.sort(unwantedPizzariaIds);
            Arrays.sort(unwantedPizzarias, (a, b) -> a.location - b.location);
        }

        boolean isUnwanted(Pizzaria p) {
            return Arrays.binarySearch(unwantedPizzariaIds, p.id) >= 0;
        }

        private long calculatePrice(Pizzaria p) {
            if (isUnwanted(p) || p == INVALID_PIZZARIA) return Long.MAX_VALUE;
            return p.price + 1L * (p.location - location) * (p.location - location);
        }

        int distance(Pizzaria p) {
            return Math.abs(location - p.location);
        }

        int distance(Customer c) {
            return Math.abs(location - c.location);
        }

        boolean sharesSameLocation(Customer c) {
            return location == c.location;
        }

        private Predicate<Pizzaria> getForwardValidator() {
            return new Predicate<Pizzaria>() {
                int size = unwantedPizzarias.length, index = 0;

                @Override
                public boolean test(Pizzaria pizzaria) {
                    if (index >= size) return false;
                    while (index < size && unwantedPizzarias[index].location < pizzaria.location) index++;
                    return index < size && unwantedPizzarias[index].location == pizzaria.location;
                }
            };
        }

        private Predicate<Pizzaria> getBackwardValidator() {
            return new Predicate<Pizzaria>() {
                int index = unwantedPizzarias.length - 1;

                @Override
                public boolean test(Pizzaria pizzaria) {
                    if (index < 0) return false;
                    while (index >= 0 && unwantedPizzarias[index].location > pizzaria.location) index--;
                    return index >= 0 && unwantedPizzarias[index].location == pizzaria.location;
                }
            };
        }
    }

    final static class PizzariaSet {
        private int sequence = 0;
        final int size;
        Pizzaria[] pizzarias;

        PizzariaSet(int size) {
            this.size = size;
            pizzarias = new Pizzaria[size];
        }

        Pizzaria newPizzaria(int location, int price) {
            Pizzaria pizzaria = new Pizzaria(sequence, location, price);
            pizzarias[sequence++] = pizzaria;
            return pizzaria;

        }

        Pizzaria get(int id) {
            return pizzarias[id];
        }
    }

    private static final Pizzaria INVALID_PIZZARIA = new Pizzaria(-1, -1, -1);

    final static class Pizzaria implements Comparable<Pizzaria> {
        final int id, location, price;

        Pizzaria(int id, int location, int price) {
            this.id = id;
            this.location = location;
            this.price = price;
        }

        @Override
        public int compareTo(Pizzaria o) {
            return location - o.location;
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