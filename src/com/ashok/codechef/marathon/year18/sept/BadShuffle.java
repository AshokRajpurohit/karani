/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem Name: Bad Shuffle
 * Link: https://www.codechef.com/SEPT18A/problems/BSHUFFLE#
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BadShuffle {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;
    private static Map<CustomArray, AtomicInteger> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        List<CustomArray> customArrays = process(in.readInt());
        customArrays.forEach(out::println);
    }

    private static void play() throws IOException {
        while (true) {
            map.clear();
            List<CustomArray> customArrays = bruteForce(in.readInt());
            map.entrySet().stream().sorted((a, b) -> a.getKey().compareTo(b.getKey())).forEach(t -> {
                out.println(toString(t.getKey().array) + ": " + t.getValue());
            });
            out.println("Answer");
            customArrays.forEach(t -> out.println(t + ": " + map.get(t)));
            out.flush();
        }
    }

    private static String toString(int[] ar) {
        StringBuilder sb = new StringBuilder();
        for (int e : ar) sb.append(e).append(' ');
        return sb.toString();
    }

    private static List<CustomArray> bruteForce(int n) {
        CustomArray ref = new CustomArray(getDefaultArray(n));
        Permute permute = new Permute(ref, 0);
        permute.invoke();

        List<CustomArray> list = new LinkedList<>();
        CustomArray max = map.entrySet().stream().max((a, b) -> a.getValue().intValue() - b.getValue().intValue()).get().getKey();
        CustomArray min = map.entrySet().stream().min((a, b) -> a.getValue().intValue() - b.getValue().intValue()).get().getKey();
        list.add(max);
        list.add(min);
        return list;
    }

    private static List<CustomArray> process(int n) {
        List<CustomArray> list = new LinkedList<>();
        list.add(prepareMostProbableArray(n));
        list.add(prepareMinProbableArray(n));
        return list;
    }

    private static CustomArray prepareMostProbableArray(int n) {
        int[] ar = new int[n];
        int half = n / 2;
        for (int i = 0; i < half; i++)
            ar[i] = i + 2;

        ar[half - 1] = 1;
        int ref = half + 2;
        for (int i = half; i < n; i++)
            ar[i] = ref++;

        ar[n - 1] = half + 1;
        return new CustomArray(ar);
    }

    private static CustomArray prepareMinProbableArray(int n) {
        int[] ar = new int[n];
        for (int i = 1; i < n; i++)
            ar[i] = i;

        ar[0] = n;
        return new CustomArray(ar);
    }

    private static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    private static int[] getDefaultArray(int n) {
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = i + 1;

        return ar;
    }

    private static int getHashValue(int[] ar) {
        long value = 0;
        int multiplier = 1;
        for (int e : ar) {
            value += multiplier * e;
            multiplier++;
        }

        return (int) (value % MOD);
    }

    final static class Permute extends RecursiveAction {
        final CustomArray array;
        final int index;

        Permute(CustomArray array, int index) {
            this.array = array;
            this.index = index;
        }

        @Override
        protected void compute() {
            if (index == array.length) {
                map.putIfAbsent(array, new AtomicInteger(0));
                map.get(array).incrementAndGet();
                return;
            }

            List<Permute> permutes = new LinkedList<>();
            for (int i = 0; i < array.length; i++) {
                Permute permute = new Permute(array.clone(), index + 1);
                swap(permute.array.array, i, index);
                permutes.add(permute);
            }

            invokeAll(permutes);
        }
    }

    final static class CustomArray implements Comparable<CustomArray> {
        final int[] array;
        final int length;
        private int hashValue = -1;

        CustomArray(int[] ar) {
            array = ar;
            length = ar.length;
        }

        @Override
        public int compareTo(CustomArray customArray) {
            if (length != customArray.length)
                return length - customArray.length;

            for (int i = 0; i < length; i++)
                if (array[i] != customArray.array[i])
                    return array[i] - customArray.array[i];

            return 0;
        }

        @Override
        public int hashCode() {
            if (hashValue == -1)
                hashValue = getHashValue(array);

            return hashValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof CustomArray))
                return false;

            CustomArray customArray = (CustomArray) o;
            return equals(customArray);
        }

        public boolean equals(CustomArray customArray) {
            if (length != customArray.length || hashValue != customArray.hashValue)
                return false;

            return Arrays.equals(array, customArray.array);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int e : array) sb.append(e).append(' ');
            return sb.toString();
        }

        @Override
        public CustomArray clone() {
            return new CustomArray(array.clone());
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
    }
}