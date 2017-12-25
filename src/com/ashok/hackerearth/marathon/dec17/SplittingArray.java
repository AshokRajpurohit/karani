/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.dec17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: HP and Splitting of Array
 * Link: https://www.hackerearth.com/challenge/competitive/december-circuits-17/algorithm/hp-and-splitting-of-array-b8a54f7a/
 * <p>
 * <p> let's store for each element (index), the number of smaller and larger elements before and after it.
 * Now in the ith rotation, what we are missing from i-1th rotation?
 * <p> We loose the swaps with smaller elements after it, which is smaller elements in original
 * array before and after this array as this element is at index 0, so all the smaller elements.
 * <p>
 * Now what we are adding to i-1th rotation?
 * We get extra swaps with larger elements before and after this element in original array as all the
 * elements are now before it.
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SplittingArray {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            out.println(process(ar));
        }
    }

    private static String process(int[] ar) {
        int n = ar.length;
        StringBuilder sb = new StringBuilder(n);
        FenwickTree tree = new FenwickTree(max(ar) + 1);
        Element[] elements = toElements(ar);
        long res = 0;
        for (Element e : elements) {
            res += tree.query(e.value);
            tree.update(e.value, 1);
        }

        for (Element e : elements) {
            // add number of new inversive pair and remove existing inversive pair.
            // this element is at the top of the array, so all the smaller elements are after it.
            // now after moving this element to end of the array, all the larger elements will
            // have an inversion with it.
            res += (n - 1 - e.curIndex) - (e.curIndex);
            sb.append(res).append(' ');
        }
        return sb.toString();
    }

    private static void format(Element[] elements) {
        sort(elements);
        int index;

        index = 0;
        for (Element e : elements)
            e.curIndex = index++;
    }

    private static void sort(Element[] elements) {
        int max = max(elements), len = elements.length;
        if (max > len * Integer.numberOfTrailingZeros(Integer.highestOneBit(len))) {
            Arrays.sort(elements);
            return;
        }
        Element[] ar = new Element[max + 1];
        for (Element e : elements)
            ar[e.value] = e;

        int index = 0;
        for (Element e : ar)
            if (e != null)
                elements[index++] = e;
    }

    private static Element[] toElements(int[] ar) {
        int index = 0;
        Element[] elements = new Element[ar.length];
        for (int e : ar) {
            elements[index] = new Element(index, e);
            index++;
        }

        format(elements.clone()); // updates new index after sorting.
        return elements;
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    private static int max(Element[] elements) {
        int max = elements[0].value;
        for (Element e : elements)
            max = Math.max(max, e.value);

        return max;
    }

    final static class Element implements Comparable<Element> {
        final int oldIndex, value;
        int curIndex;

        Element(int i, int v) {
            oldIndex = i;
            value = v;
        }

        @Override
        public int compareTo(Element element) {
            return value - element.value;
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


    /**
     * @author Ashok Rajpurohit (ashok1113@gmail.com).
     */
    final static class FenwickTree {
        private final int[] array;
        private int size = 0;
        private final int capacity;

        public FenwickTree(int capacity) {
            this.capacity = capacity;
            array = new int[capacity];
        }

        public FenwickTree(int[] ar) {
            this(ar.length);

            for (int value : ar)
                add(value);
        }

        public void add(int value) {
            array[size++] = value;

            if (size == capacity)
                populate();
        }

        /**
         * Populates the tree for the very first and only time.
         * Every update operation is called once. This method runs in order of
         * N (array length).
         */
        private void populate() {
            for (int i = 0; i < capacity; i++)
                updateTree(i);
        }

        public void update(int index, final int value) {
            updateTree(index, value);
            array[index] += value;
        }

        public void replace(int index, int value) {
            int oldValue = query(index, index);
            update(index, value - oldValue);
        }

        /**
         * Returns the sum of elements from start to end index, both inclusive.
         *
         * @param start
         * @param end
         * @return
         */
        public int query(int start, int end) {
            int result = 0;
            while (end >= start) {
                result += array[end];
                end = end & (end + 1);
                end--;
            }

            start--;
            while (start > end) {
                result -= array[start];
                start = start & (start + 1);
                start--;
            }

            return result;
        }

        public int query(int start) {
            return query(start, capacity - 1);
        }

        /**
         * This method finds the parent and update the same with this node value.
         *
         * @param index
         */
        private void updateTree(int index) {
            int nextIndex = index | (index + 1);

            if (nextIndex >= capacity)
                return;

            array[nextIndex] += array[index];
        }

        /**
         * This is an addition operation for the specified node.
         * The update value is added to the same node and recursively
         * the parent node is also updated.
         *
         * @param index current node index to be updated.
         * @param value value to be updated.
         */
        private void updateTree(int index, int value) {
            int nextIndex = index | (index + 1);

            if (nextIndex >= capacity)
                return;

            array[nextIndex] += value;
            updateTree(nextIndex, value);
        }
    }

}