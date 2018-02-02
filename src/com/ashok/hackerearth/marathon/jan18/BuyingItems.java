/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan18;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Buying Items
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/algorithm/buying-items-d552af6f/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BuyingItems {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        List<Seller> sellers = new LinkedList<>();
        for (int i = 0; i < m; i++)
            sellers.add(new Seller(in.readIntArray(n), in.readInt()));

        out.println(process(sellers, n));
    }

    private static long process(List<Seller> sellers, int items) {
        Process p = new Process(toArray(sellers), new boolean[items]);
        return p.minCost;
        /* As it turned out that the brute force with early discarding is working, I
        had to abandon this beautifull code as you can see the below commented code.

        List<Seller> mustHaveSellers = getMandatorySeller(sellers, items);
        long res = cost(mustHaveSellers);
        int size = sellers.size();
        sellers.removeAll(mustHaveSellers);
        boolean[] valueSet = getValueSet(mustHaveSellers, items);
        if (!mustHaveSellers.isEmpty())
            removeItems(sellers, valueSet);

        int itemCount = mustHaveSellers.isEmpty() ? 0 : count(valueSet, true);
        Set<Seller> remainingSeller = new HashSet<>(sellers);
        normalize(remainingSeller);
        Collection<Seller> remainingSellerBackup = clone(remainingSeller);
        long approxAns = solveByApproximation(remainingSeller);
        Process process = new Process(toArray(remainingSellerBackup), valueSet, approxAns);

        return res + process.minCost;*/
    }

    private static long solveByApproximation(Collection<Seller> sellers) {
        int itemCount = 0, size = sellers.size();
        long res = 0;

        while (itemCount < size && !sellers.isEmpty()) {
            Seller minCostSeller = Collections.min(sellers);
            sellers.remove(minCostSeller);
            res += minCostSeller.price;
            removeItems(sellers, minCostSeller.items.map);
            itemCount += minCostSeller.items.count;
        }

        return res;
    }

    private static Collection<Seller> clone(Collection<Seller> sellers) {
        Set<Seller> sellerSet = new HashSet<>();
        for (Seller seller : sellers)
            sellerSet.add(seller.clone());

        return sellerSet;
    }

    private static void normalize(Collection<Seller> sellers) {
        Set<Seller> normalizedSellers = new HashSet<>(sellers);
        for (Seller seller : sellers) {
            boolean add = true;
            for (Seller next : sellers) {
                if (seller != next && seller.contains(next) && seller.price <= next.price)
                    normalizedSellers.remove(next);
            }
        }

        sellers.clear();
        sellers.addAll(normalizedSellers);
    }

    private static void removeItems(Collection<Seller> sellers, Collection<Seller> removeItems, int items) {
        if (removeItems.isEmpty())
            return;

        removeItems(sellers, getValueSet(removeItems, items));
    }

    private static void removeItems(Collection<Seller> sellers, boolean[] itemMap) {
        for (Seller seller : sellers)
            removeItems(seller, itemMap);
    }

    private static void removeItems(Seller seller, boolean[] itemMap) {
        int index = 0;
        for (boolean itemValue : itemMap) {
            if (itemValue) seller.items.remove(index);
            index++;
        }
    }

    private static boolean[] getValueSet(Collection<Seller> sellers, int items) {
        boolean[] set = new boolean[items];
        for (Seller seller : sellers)
            for (int item : seller.items)
                set[item] = true;

        return set;
    }

    private static boolean[] getComplimentarySet(boolean[] set) {
        int len = set.length;
        boolean[] res = new boolean[len];
        int index = 0;

        for (boolean val : set)
            res[index++] = !val;

        return res;
    }

    private static long cost(List<Seller> sellers) {
        long cost = 0;
        for (Seller seller : sellers)
            cost += seller.price;

        return cost;
    }

    private static List<Seller> subtract(List<Seller> a, List<Seller> b) {
        IdMap map = getSellerIdMap(b, sellerSequence);
        List<Seller> res = new LinkedList<>();
        for (Seller seller : a)
            if (!map.hasValue(seller.id)) res.add(seller);

        return res;
    }

    private static LinkedList<Seller> getMandatorySeller(List<Seller> sellers, final int items) {
        int sellerCount = sellers.size();
        LinkedList<Seller>[] itemSellerMap = new LinkedList[items];
        for (int i = 0; i < items; i++)
            itemSellerMap[i] = new LinkedList<>();

        for (Seller seller : sellers)
            updateSellerMap(itemSellerMap, seller);

        LinkedList<Seller> mandatorySellers = new LinkedList<>();
        for (LinkedList<Seller> itemSellers : itemSellerMap) {
            if (itemSellers.size() == 1) {
                Seller s = itemSellers.remove();
                mandatorySellers.addLast(s);
            }
        }

        return mandatorySellers;
    }

    private static IdMap getSellerIdMap(List<Seller> sellers, int size) {
        IdMap sellerMap = new IdMap(size);
        for (Seller seller : sellers)
            sellerMap.add(seller.id);

        return sellerMap;
    }

    private static void updateSellerMap(LinkedList<Seller>[] map, Seller seller) {
        for (int item : seller.items)
            map[item].addLast(seller);
    }

    private static int count(int[] ar, int value) {
        int count = 0;
        for (int e : ar)
            if (e == value) count++;

        return count;
    }

    private static int count(boolean[] ar, boolean value) {
        int count = 0;

        for (boolean e : ar)
            if (e == value)
                count++;

        return count;
    }

    private static boolean completes(boolean[] ar, boolean[] br) {
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] || br[i])
                continue;
            else
                return false;
        }

        return true;
    }

    /**
     * This method is now used only for inverse modulo calculation.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    private static void merge(boolean[] ar, boolean[] br) {
        for (int i = 0; i < ar.length; i++)
            ar[i] = ar[i] || br[i];
    }

    private static Seller[] toArray(Collection<Seller> c) {
        Seller[] sellers = new Seller[c.size()];
        int index = 0;
        for (Seller seller : c)
            sellers[index++] = seller;

        return sellers;
    }

    private static Seller[] toArray(List<Seller> list) {
        Seller[] sellers = new Seller[list.size()];
        int index = 0;
        for (Seller seller : list)
            sellers[index++] = seller;

        return sellers;
    }

    private static int sellerSequence = 0;

    final static class Seller implements Comparable<Seller> {
        final Bucket items;
        final int price, id = sellerSequence++;

        Seller(int[] ar, int price) {
            this.price = price;
            items = new Bucket(ar);
        }

        Seller(Bucket bucket, int price) {
            items = bucket.clone();
            this.price = price;
        }

        public int hashCode() {
            return id;
        }

        public Fraction getFraction() {
            return new Fraction(price, items.count);
        }

        boolean hasItem(int index) {
            return items.map[index];
        }

        @Override
        public int compareTo(Seller o) {
            int res = getFraction().compareTo(o.getFraction());
            return res == 0 ? o.items.size - items.size : res;
        }

        public String toString() {
            return id + ": " + items.toString() + " " + price;
        }

        public boolean contains(Seller seller) {
            return items.contains(seller.items);
        }

        public Seller clone() {
            return new Seller(items, price);
        }
    }

    final static class IdMap {
        final int size;
        final boolean[] map;

        IdMap(int size) {
            this.size = size;
            map = new boolean[size];
        }

        private void add(int id) {
            map[id] = true;
        }

        private void remove(int id) {
            map[id] = false;
        }

        private boolean hasValue(int id) {
            return map[id];
        }
    }

    final static class Bucket implements Iterable<Integer> {
        final int size;
        final boolean[] map;
        private int count = 0;

        private Bucket(boolean[] map, int count) {
            size = map.length;
            this.map = map.clone();
            this.count = count;
        }

        Bucket(int[] ar) {
            size = ar.length;
            int index = 0;
            map = new boolean[size];

            for (int e : ar)
                map[index++] = e != 0;

            count = count(ar, 1);
        }

        private void add(int index) {
            if (map[index])
                return;

            map[index] = true;
            count++;
        }

        private void remove(int index) {
            if (!map[index])
                return;

            map[index] = false;
            count--;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                int iterIndex = firstIndex();

                private int firstIndex() {
                    if (count == 0) return size;
                    for (int i = 0; i < size; i++)
                        if (map[i]) return i;

                    return size;
                }

                @Override
                public boolean hasNext() {
                    return iterIndex < size;
                }

                @Override
                public Integer next() {
                    int value = iterIndex++;
                    while (iterIndex < size && !map[iterIndex])
                        iterIndex++;

                    return value;
                }
            };
        }

        @Override
        public Bucket clone() {
            return new Bucket(map, count);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size << 1);
            for (boolean val : map)
                sb.append(val ? 1 : 0).append(' ');

            return sb.toString();
        }

        public boolean contains(Bucket bucket) {
            if (size < bucket.size) return false;

            for (int item : bucket)
                if (!map[item]) return false;

            return true;
        }
    }

    final static class Fraction implements Comparable<Fraction> {
        long numerator, denominator;

        Fraction(long n, long d) {
            long gcd = gcd(n, d);
            numerator = n / gcd;
            denominator = d / gcd;
        }

        @Override
        public int compareTo(Fraction o) {
            return Long.compare(numerator * o.denominator, denominator * o.numerator);
        }
    }

    final static class Process {
        long minCost = Long.MAX_VALUE;
        private Seller[] sellers;
        private boolean[] purchasedItemMap;
        private boolean[][] backup, unions;
        final int size, sellerCount;

        Process(Seller[] sellers, boolean[] purchasedItemMap, long minCost) {
            this.sellers = sellers;
            sellerCount = sellers.length;
            size = purchasedItemMap.length;
            this.purchasedItemMap = purchasedItemMap.clone();
            backup = new boolean[sellerCount][size];
            unions = new boolean[sellerCount][size];
            this.minCost = minCost;
            populate();
            process();
        }

        void populate() {
            boolean[] map = new boolean[size];
            for (int i = sellerCount - 1; i >= 0; i--) {
                merge(map, sellers[i].items.map);
                System.arraycopy(map, 0, unions[i], 0, size);
            }
        }

        Process(Seller[] sellers, boolean[] purchasedItemMap) {
            this(sellers, purchasedItemMap, Long.MAX_VALUE);
        }

        Process(Collection<Seller> sellers, boolean[] purchasedItemMap) {
            this(toArray(sellers), purchasedItemMap);
        }

        void process() {
            process(0, 0);
        }

        void process(long costSoFar, int index) {
            if (index >= sellerCount || costSoFar >= minCost || !completes(purchasedItemMap, unions[index]))
                return;

            process(costSoFar, index + 1);
            if (!applicable(sellers[index]))
                return;

            backup(index);
            apply(sellers[index]);
            costSoFar += sellers[index].price;

            if (count(purchasedItemMap, true) == size) {
                minCost = Math.min(minCost, costSoFar);
                // no need to process further as it will increase the cost only.
            } else {
                process(costSoFar, index + 1);
            }

            restore(index);
        }

        boolean applicable(Seller seller) {
            boolean[] map = seller.items.map;
            for (int i = 0; i < size; i++) {
                if (map[i] && !purchasedItemMap[i])
                    return true;
            }

            return false;
        }

        void apply(Seller seller) {
            merge(purchasedItemMap, seller.items.map);
        }

        void backup(int index) {
            System.arraycopy(purchasedItemMap, 0, backup[index], 0, size);
        }

        void restore(int index) {
            System.arraycopy(backup[index], 0, purchasedItemMap, 0, size);
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