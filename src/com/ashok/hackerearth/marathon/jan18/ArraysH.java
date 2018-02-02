/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan18;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Arrays
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/algorithm/theatre-830bdbff/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ArraysH {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), k = in.readInt();
        Triplet[] triplets = new Triplet[n];
        for (int i = 0; i < n; i++)
            triplets[i] = new Triplet(in.readInt(), in.readInt(), in.readInt());

        out.println(process(triplets, k));
    }

    private static int process(Triplet[] triplets, int k) {
        int res = (k << 1) + k; // k * 3
        int len = triplets.length;
        int[] tValues = getTValues(triplets, k);
        int tIndex = 0;
        GenericHeap tripletHeap = new GenericHeap(TRIPLET_COMPARATOR, len, new ReferenceProviderSetter() {
            @Override
            public int getValue(Triplet triplet) {
                return triplet.ref1;
            }

            @Override
            public int setValue(Triplet triplet, int newValue) {
                return triplet.ref1 = newValue;
            }
        });
        GenericHeap tripletSumHeap = new GenericHeap(TRIPLET_SUM_COMPARATOR, len, new ReferenceProviderSetter() {
            @Override
            public int getValue(Triplet triplet) {
                return triplet.ref2;
            }

            @Override
            public int setValue(Triplet triplet, int newValue) {
                return triplet.ref2 = newValue;
            }
        });
        tripletHeap.addAll(triplets);
        tripletSumHeap.addAll(triplets);
        res = tripletSumHeap.peek().sum;

        for (int tValue : tValues) {
            Triplet maxValueTriplet = tripletHeap.peek();
            while (maxValueTriplet.max + tValue == k) {
                maxValueTriplet.subtractFromMax(k);
                tripletHeap.restructure(maxValueTriplet);
                tripletSumHeap.restructure(maxValueTriplet);
                res = Math.min(res, tripletSumHeap.peek().sum + 3 * tValue);
                maxValueTriplet = tripletHeap.peek();
            }
        }

        return res;
    }

    private static int[] getTValues(Triplet[] triplets, int k) {
        int len = triplets.length * 3, index = 0;
        int[] ar = new int[len];

        for (Triplet triplet : triplets) {
            ar[index++] = k - triplet.a;
            ar[index++] = k - triplet.b;
            ar[index++] = k - triplet.c;
        }

        return uniqueElements(ar);
    }

    private static int[] uniqueElements(int[] ar) {
        Arrays.sort(ar);
        int uniqueuCount = 1;
        int prev = ar[0];
        for (int e : ar) {
            if (e != prev) uniqueuCount++;
            prev = e;
        }

        int[] res = new int[uniqueuCount];
        int ref = ar[0], index = 1;
        res[0] = ref;

        for (int e : ar) {
            if (e != ref) res[index++] = e;
            ref = e;
        }

        return res;
    }

    private final static Comparator<Triplet> TRIPLET_COMPARATOR = new Comparator<Triplet>() {
        @Override
        public int compare(Triplet o1, Triplet o2) {
            return o2.max - o1.max;
        }
    };

    private static final Comparator<Triplet> TRIPLET_SUM_COMPARATOR = new Comparator<Triplet>() {
        @Override
        public int compare(Triplet o1, Triplet o2) {
            return o2.sum - o1.sum;
        }
    };

    private static int tripletSequence = 0;

    final static class Triplet {
        final int id;
        int a, b, c, max, sum;
        int ref1 = 0, ref2 = 0;

        Triplet(int a, int b, int c) {
            id = tripletSequence++;
            this.a = a;
            this.b = b;
            this.c = c;
            reset();
        }

        void reset() {
            max = Math.max(a, Math.max(b, c));
            sum = a + b + c;
        }

        void subtractFromMax(int k) {
            if (max == a) a -= k;
            else if (max == b) b -= k;
            else c -= k;

            reset();
        }

        public int hashCode() {
            return id;
        }

        public boolean equals(Object object) {
            Triplet triplet = (Triplet) object;
            return a == triplet.a && b == triplet.b && c == triplet.c;
        }

        public String toString() {
            return a + ", " + b + ", " + c;
        }
    }

    interface ReferenceProviderSetter {
        int getValue(Triplet triplet);

        int setValue(Triplet triplet, int newValue);
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
    }

    /**
     * This Heap (aka Priority Queue) implementation is for fix size.
     * It is assumed that in most situation the max size is already known and
     * we have to know the highest priority element or highest k priority elements
     * or kth priority element. In all these case the k (max size) is fixed.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com).
     */
    final static class GenericHeap {
        private final Comparator<? super Triplet> comparator;
        public final int capacity;
        private int count = 0;
        private final Triplet[] heap;
        private final ReferenceProviderSetter referenceProvider;

        public GenericHeap(Comparator<Triplet> comparator, int capacity, ReferenceProviderSetter referenceProvider) {
            this.comparator = comparator;
            this.capacity = capacity;
            heap = new Triplet[capacity];
            this.referenceProvider = referenceProvider;
        }

        public Triplet poll() {
            Triplet res = heap[0];
            count--;
            heap[0] = heap[count];
            reformatDown(0);

            return res;
        }

        public Triplet peek() {
            return heap[0];
        }

        public boolean isFull() {
            return count == capacity;
        }

        /**
         * If the heap is already full then this will update the already existing
         * top element if necessary.
         *
         * @param e
         * @return
         */
        public boolean offer(Triplet e) {
            if (count == capacity)
                return update(e);
            else
                add(e);

            return true;
        }

        public void addAll(Triplet[] ar) {
            for (Triplet e : ar)
                add(e);
        }

        private void add(Triplet e) {
            set(count, e);
            reformatUp(count);
            count++;
        }

        private boolean update(Triplet e) {
            if (comparator.compare(e, heap[0]) <= 0)
                return false;

            set(0, e);
            reformatDown(0);
            return true;
        }

        private void reformatUp(int index) {
            Triplet val = heap[index];
            while (index != 0) {
                int parent = (index - 1) >>> 1;
                Triplet p = heap[parent];
                if (comparator.compare(p, val) <= 0)
                    break;

                set(index, p);
                index = parent;
            }

            set(index, val);
        }

        private void set(int index, Triplet triplet) {
            heap[index] = triplet;
            referenceProvider.setValue(triplet, index);
        }

        private void reformatDown(int index) {
            Triplet val = heap[index];
            while (((index << 1) + 1) < count) {
                int c = getSmallerChild(index);
                Triplet e = heap[c];
                if (comparator.compare(val, e) <= 0)
                    break;

                set(index, e);
                index = c;
            }

            set(index, val);
        }

        private int getParent(int index) {
            return (index - 1) >>> 1;
        }

        private int getSmallerChild(int index) {
            int c1 = (index << 1) + 1, c2 = c1 + 1;

            if (c2 == count)
                return c1;

            return comparator.compare(heap[c1], heap[c2]) <= 0 ? c1 : c2;
        }

        public void restructure(Triplet e) {
            int index = referenceProvider.getValue(e);
            reformatDown(index);
            reformatUp(index);
        }
    }
}