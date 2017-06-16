/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Chef and Prime Queries
 * Link: https://www.codechef.com/JUNE17/problems/PRMQ
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndPrimeQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] numbers, primes, largestFactor; // holds largest factor for any integer using as index.
    private static LinkedList<Integer>[] multiplierIndices;
    private static Pair[][] multiplierIndexArray;
    private static SparseTable minSparseTable, maxSparseTable;
    private static Query[] queries;
    private static Indexer primeIndexer;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        numbers = in.readIntArray(n);
        populate(max(numbers));

        int q = in.readInt();
        queries = new Query[q];

        for (int i = 0; i < q; i++) {
            int left = in.readInt() - 1, right = in.readInt() - 1, x = in.readInt(), y = in.readInt();
            Query query = new Query(i, left, right, x, y);
            queries[i] = query;
        }

        process(queries);
        StringBuilder sb = new StringBuilder(q << 3);

        for (Query query : queries)
            sb.append(query.value).append('\n');

        out.print(sb);
    }

    private static int bruteForce(int L, int R, int x, int y) {
        return 0;
    }

    private static void process(Query[] queries) {
        for (Query query : queries)
            process(query);
    }

    private static void process(Query query) {
        query.value = getExponentSum(query.left, query.right, query.startFactorIndex, query.endFactorIndex);
    }

    private static int getExponentSum(int from, int to) {
        int sum = 0;
        for (int i = from; i <= to; i++)
            sum += getExponentSum(numbers[i]);

        return sum;
    }

    private static int getExponentSum(int fromIndex, int toIndex, int fromFactorIndex, int toFactorIndex) {
        int sum = 0;
        for (int i = fromFactorIndex; i <= toFactorIndex; i++) {
            int primeFactor = primes[i];
            Pair[] indexArray = multiplierIndexArray[primeFactor];

            int start = findSmaller(indexArray, fromIndex - 1), end = findSmaller(indexArray, toIndex);

            if (end <= start)
                continue;

            sum += indexArray[end].b - (start == -1 ? 0 : indexArray[start].b);

            // TODO: work on this to make it in O(1).
//            for (int j = start; j <= end; j++)
//                sum += getExponent(numbers[indexArray[j]], primeFactor);
        }

        return sum;
    }

    private static void populate(int n) {
        populatePrimes(n);
        populateLargestFactors(n);
        populateMultiplierIndices();
        populateMultiplierIndexArray();
        normalizePrimes(); // let's discard un-used primes from future calculations.
        primeIndexer = new Indexer(primes);
        minSparseTable = new SparseTable(numbers, MIN_OPERATOR);
        maxSparseTable = new SparseTable(numbers, MAX_OPERATOR);
    }

    private static void populatePrimes(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;

            for (int j = i * 2; j <= n; j += i) {
                ar[j] = true;
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        primes = new int[count];
        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                primes[j++] = i;
            }
        }
    }

    private static void populateLargestFactors(int n) {
        largestFactor = new int[n + 1];
        for (int i = 1; i <= n; i++)
            largestFactor[i] = i;

        int root = (int) Math.sqrt(n);
        for (int i = 0; primes[i] <= root; i++) {
            int e = primes[i];

            for (int j = 2 * e; j <= n; j += e) {
                int factor = removeFactor(largestFactor[j], e);
                largestFactor[j] = factor == 1 ? e : factor;
            }
        }
    }

    private static void populateMultiplierIndices() {
        int n = primes[primes.length - 1];
        multiplierIndices = new LinkedList[n + 1];
        for (int i = 0; i <= n; i++)
            multiplierIndices[i] = new LinkedList<>();

        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            int factor = largestFactor[number];
            while (factor > 1) {
                multiplierIndices[factor].addLast(i);

                number = removeFactor(number, factor);
                factor = largestFactor[number];
            }
        }
    }

    private static void populateMultiplierIndexArray() {
        multiplierIndexArray = new Pair[multiplierIndices.length][];
        for (int i = 0; i < multiplierIndices.length; i++)
            multiplierIndexArray[i] = toSumPairArray(toArray(multiplierIndices[i]), i);

    }

    private static void normalizePrimes() {
        LinkedList<Integer> primeFactors = new LinkedList<>();
        for (int i = 2; i < multiplierIndices.length; i++)
            if (!multiplierIndices[i].isEmpty())
                primeFactors.addLast(i);

        primes = toArray(primeFactors);
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    private static Pair[] toSumPairArray(int[] ar, int factor) {
        int length = ar.length;
        Pair[] pairs = new Pair[length];
        for (int i = 0; i < length; i++)
            pairs[i] = new Pair(ar[i], getExponent(numbers[ar[i]], factor));

        for (int i = 1; i < length; i++)
            pairs[i].b += pairs[i - 1].b;

        return pairs;
    }

    private static int findSmaller(int[] ar, int key) {
        if (key < ar[0])
            return -1;

        if (key >= ar[ar.length - 1])
            return ar.length - 1;

        return binarySearch(ar, key);
    }

    private static int findLarger(int[] ar, int key) {
        if (key <= ar[0])
            return 0;

        if (key > ar[ar.length - 1])
            return ar.length;

        int index = binarySearch(ar, key);
        if (ar[index] < key)
            index++;

        return index;
    }

    private static int findSmaller(Pair[] pairs, int key) {
        if (key < pairs[0].a)
            return -1;

        if (key >= pairs[pairs.length - 1].a)
            return pairs.length - 1;

        return binarySearch(pairs, key);
    }

    private static int binarySearch(int[] ar, int key) {
        if (ar.length < 10)
            return linearSearch(ar, key);

        int low = 0, high = ar.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = ar[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }

        return low;
    }

    private static int binarySearch(Pair[] pairs, int key) {
        int low = 0, high = pairs.length - 1, mid = (low + high) >>> 1;
        while (low <= high) {
            mid = (low + high) >>> 1;
            int midVal = pairs[mid].a;

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }

        return pairs[mid].a == key ? mid : mid - 1;
    }

    /**
     * Returns index of element equal or smaller than {@code key} element.
     *
     * @param ar
     * @param key
     * @return
     */
    private static int linearSearch(int[] ar, int key) {
        int index = 0, length = ar.length;

        while (index < length && ar[index] <= key)
            index++;

        return index - 1;
    }

    private static int removeFactor(int number, int factor) {
        while (number % factor == 0)
            number /= factor;

        return number;
    }

    private static int getExponent(int number, int factor) {
        int exponent = 0;
        while (number % factor == 0) {
            number /= factor;
            exponent++;
        }

        return exponent;
    }

    private static int getExponentSum(int number) {
        int exponent = 0;
        while (number > 1) {
            exponent += getExponent(number, largestFactor[number]);
            number = removeFactor(number, largestFactor[number]);
        }

        return exponent;
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(max, e);

        return max;
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

    interface Operator {
        int operation(int a, int b);
    }

    private static final Operator MIN_OPERATOR = new MinOperator(), MAX_OPERATOR = new MaxOperator();

    final static class MinOperator implements Operator {
        @Override
        public int operation(int a, int b) {
            return Math.min(a, b);
        }
    }

    final static class MaxOperator implements Operator {
        @Override
        public int operation(int a, int b) {
            return Math.max(a, b);
        }
    }

    /**
     * Implementation of Range Query Data Structure using an array of arrays.
     * Preprocessing complexity is order of n * long(n) and quering complexity is
     * order of 1 (constant time). This is useful when the range of the query can
     * be anything from 1 to n. If the min range width is fixed,
     * {@link RangeQueryBlock} is the better option for range query.
     * For two dimensional arrays use {@link Quadtree} or {@link SparseTable2D}
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class SparseTable {
        private int[][] mar;
        final Operator operator;

        public SparseTable(int[] ar, Operator operator) {
            this.operator = operator;
            format(ar);
        }

        public int query(int L, int R) {
            if (R >= mar[1].length || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

            if (L > R)
                throw new InvalidParameterException("start index can not be after end index: " + L + ", " + R);

            int half = Integer.highestOneBit(R + 1 - L);
            return operator.operation(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] ar) {
            mar = new int[ar.length + 1][];
            mar[1] = ar;
            int bit = 2;
            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];
                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = operator.operation(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
        }

        /**
         * Single operation for query types.
         *
         * @param a
         * @param b
         * @return
         */
        public int operation(int a, int b) {
            return Math.max(a, b);
        }
    }

    final static class Query implements Comparable<Query> {
        final int index, left, right;
        int startFactorIndex, endFactorIndex, value = 0; // -1 as not calculated.

        Query(int index, int left, int right, int startFactor, int endFactor) {
            this.index = index;
            this.left = left;
            this.right = right;

            normalize(startFactor, endFactor);
        }

        @Override
        public int compareTo(Query query) {
            if (startFactorIndex != query.startFactorIndex)
                return startFactorIndex - query.startFactorIndex;

            return endFactorIndex - query.endFactorIndex;
        }

        private void normalize(int startFactor, int endFactor) {
            startFactorIndex = Math.max(minSparseTable.query(left, right), startFactor);
            endFactorIndex = Math.min(maxSparseTable.query(left, right), endFactor);

            startFactorIndex = primeIndexer.getLargerElementIndex(startFactor);
            endFactorIndex = primeIndexer.getSmallerElementIndex(endFactor);
        }
    }

    final static class Indexer {
        int[] indexMap, smallerIndex, ar;
        final int length;

        Indexer(int[] ar) {
            this.ar = ar;
            length = max(ar) + 1;
            indexMap = new int[length];
            smallerIndex = new int[length];

            populate();
        }

        int getElementIndex(int value) {
            if (value < 0 || value >= length)
                return -1;

            return indexMap[value];
        }

        int getSmallerElementIndex(int value) {
            if (value < 0)
                return -1;

            if (value >= length)
                return smallerIndex[length - 1];

            return smallerIndex[value];
        }

        int getLargerElementIndex(int value) {
            int index = getSmallerElementIndex(value);
            if (getElementIndex(value) != -1)
                return index;

            index++; // next element.
            return index == length ? -1 : index;
        }

        private void populate() {
            Arrays.fill(indexMap, -1);
            Arrays.fill(smallerIndex, -1);

            for (int i = 0; i < ar.length; i++) {
                indexMap[ar[i]] = i;
                smallerIndex[ar[i]] = i;
            }

            for (int i = 1; i < length; i++)
                if (smallerIndex[i] == -1)
                    smallerIndex[i] = smallerIndex[i - 1];
        }
    }

    final static class Pair {
        int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}