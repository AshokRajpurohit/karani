/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Problem Name: Transaction
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-17/algorithm/transaction/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Transaction {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] transactions, nextHigher, higherCount, largerElementIndexMap, nextLower;
    private static final int limit = 100002, notExists = -1;
    private static Query[] queries;

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt(), q = in.readInt();
        transactions = in.readIntArray(t);
        queries = new Query[q];

        for (int i = 0; i < q; i++)
            queries[i] = new Query(i, in.readInt(), in.readInt());

        process();
    }

    private static void play() throws IOException {
        while (true) {
            int t = in.readInt(), q = in.readInt();
            transactions = Generators.generateRandomIntegerArray(t, 1, limit - 1);
            queries = new Query[q];

            Random random = new Random();
            for (int i = 0; i < q; i++)
                queries[i] = new Query(i, random.nextInt(limit - 1) + 1, random.nextInt(limit - 1) + 1);

            process();
            out.flush();
        }
    }

    private static void process() {
        StringBuilder sb = new StringBuilder(queries.length * 6);

        if (allEqual()) {
            int value = transactions[0];

            for (Query query : queries) {
                if (query.value > value || query.pos > transactions.length)
                    sb.append(notExists).append('\n');
                else
                    sb.append(value).append('\n');
            }

            out.print(sb);
            return;
        }

        populate();

        if (isNonDecreasing()) {
            for (Query query : queries) {
                int index = largerElementIndexMap[query.value] + query.pos - 1;

                if (index >= transactions.length)
                    sb.append(notExists).append('\n');
                else
                    sb.append(transactions[index]).append('\n');
            }

            out.print(sb);
            return;
        }

        if (isNonIncreasing()) {
            for (Query query : queries) {
                int index = query.pos - 1;

                if (index >= transactions.length || transactions[index] < query.value)
                    sb.append(notExists).append('\n');
                else
                    sb.append(transactions[index]).append('\n');
            }

            out.print(sb);
            return;
        }

        normalizeQueries();
        int[] res = new int[queries.length];
        int index = 0, value = 0, lastCount = 0;
        for (Query query : queries) {
            int largerElementIndex = largerElementIndexMap[query.value];
            if (largerElementIndex == -1 || higherCount[query.value] < query.pos)
                res[query.index] = notExists;
            else {
                if (value == query.value)
                    index = getTransactionCostIndex(index, query.pos - lastCount + 1, query.value);
                else
                    index = getTransactionCostIndex(largerElementIndex, query.pos, query.value);

                res[query.index] = transactions[index];
            }

            value = query.value;
            lastCount = query.pos;
        }

        for (int e : res)
            sb.append(e).append('\n');

        out.print(sb);
    }

    private static void normalizeQueries() {
        int[] map = new int[limit];

        for (int e : transactions)
            map[e] = e;

        for (int i = limit - 2; i >= 0; i--)
            if (map[i] == 0)
                map[i] = map[i + 1];

        for (int i = limit - 1; i >= 0 && map[i] == 0; i--)
            map[i] = limit - 1;

        for (Query query : queries)
            query.value = map[query.value];

        Arrays.sort(queries, new Comparator<Query>() {
            @Override
            public int compare(Query o1, Query o2) {
                if (o1.value == o2.value)
                    return o1.pos - o2.pos;

                return o1.value - o2.value;
            }
        });
    }

    private static int getAvailableValue(int value) {
        int index = largerElementIndexMap[value];

        if (index >= transactions.length || index < 0)
            return value;

        return transactions[index];
    }

    private static int getTransactionCostIndex(int index, int count, int value) {
        int var = transactions[index];

        while (count > 1) {
            count--;

            if (var > value)
                index = Math.min(nextLower[index], nextHigher[index]);
            else
                index = nextHigher[index];

            while (transactions[index] < value)
                index = nextHigher[index];

            var = transactions[index];
        }

        return index;
    }

    private static void populateIndexMap() {
        largerElementIndexMap = new int[limit];
        Arrays.fill(largerElementIndexMap, -1);

        for (int i = 0; i < transactions.length; i++)
            if (largerElementIndexMap[transactions[i]] == -1)
                largerElementIndexMap[transactions[i]] = i;

        for (int i = limit - 2; i >= 0; i--)
            if (largerElementIndexMap[i] == -1 ||
                    (largerElementIndexMap[i] >= largerElementIndexMap[i + 1] && largerElementIndexMap[i + 1] != -1))
                largerElementIndexMap[i] = largerElementIndexMap[i + 1];
    }

    private static void populate() {
        higherCount = new int[limit];

        for (int e : transactions)
            higherCount[e]++;

        for (int i = limit - 2; i >= 0; i--)
            higherCount[i] += higherCount[i + 1];

        nextHigher = nextHigher(transactions);
        nextLower = nextSmaller(transactions);
        populateIndexMap();
    }

    private static boolean allEqual() {
        int value = transactions[0];

        for (int e : transactions)
            if (e != value)
                return false;

        return true;
    }

    private static boolean isNonDecreasing() {
        int value = transactions[0];

        for (int e : transactions) {
            if (e < value)
                return false;

            value = e;
        }

        return false;
    }

    private static boolean isNonIncreasing() {
        int value = transactions[0];

        for (int e : transactions) {
            if (e > value)
                return false;

            value = e;
        }

        return true;
    }

    /**
     * Returns array of next higher element index.
     * At index i the array contains the index of next higher element.
     * If there is no higher element right to it, than the value is array size.
     *
     * @param ar
     * @return
     */
    private static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static int[] nextSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] >= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    final static class Query {
        final int index;
        int value, pos;

        Query(int id, int v, int n) {
            index = id;
            value = v;
            pos = n;
        }

        public String toString() {
            return "index: " + index + ", value: " + value + ", count: " + pos;
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
