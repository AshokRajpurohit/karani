/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.simility;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Problem Name: Shuffle array in zig-zag order
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Simility {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            play();
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
//        int[] ar = in.readIntArray(in.readInt());
//        QuickSort.kthMinElement(ar, ar.length >> 1);
        int n = in.readInt();
        while (true) {
            int[] ar = Generators.generateRandomIntegerArray(n, 1000);
            if (!uniques(ar.clone()))
                continue; // the simplest approach also not consistent when there are consecutive duplicates.
            partitionAtMid(ar);
            int[] copy = prepareWaveForm(ar);
            try {
                validateWaveForm(copy); // strict waveform.
            } catch (Exception e) {
//                e.printStackTrace();
                out.print(ar);
                out.print(copy);
                out.flush();
                in.read();
            }
        }
    }

    private static void play() throws IOException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        while (true) {
            NotLessThanHundred t = new NotLessThanHundred(in.readInt(), in.readInt());
            out.println(t.setA(in.readInt()));
            out.println(t.setB(in.readInt()));
            out.println(t.set(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static int function(int r) {
        return function(r >>> 1) + function(1 + (r >>> 1));
    }

    private static boolean uniques(int[] ar) {
        Arrays.sort(ar);
        for (int i = 1; i < ar.length; i++) if (ar[i] == ar[i - 1]) return false;
        return true;
    }

    private static void validateWaveForm(int[] ar) {
        for (int i = 1; i < ar.length; i += 2)
            if (ar[i] <= ar[i - 1]) throw new RuntimeException("something is wrong with this array");

        for (int i = 2; i < ar.length; i += 2)
            if (ar[i] >= ar[i - 1]) throw new RuntimeException("something is wrong with the array");
    }

    private static int[] prepareWaveForm(int[] ar) {
        int start = 0, end = ar.length - 1, index = 0;
        int[] copy = new int[ar.length];
        while (start < end) {
            copy[index++] = ar[start++];
            copy[index++] = ar[end--];
        }

        if (start == end) copy[index++] = ar[start];
        return copy;
    }

    private static void partitionAtMid(int[] ar) {
        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;
        int pivote = -1;
        while (pivote != mid) {
            pivote = pivoteM3(ar, start, end);
            if (pivote > mid)
                end = pivote - 1;
            else if (pivote < mid)
                start = pivote + 1;
            else
                return;
        }
    }

    private static int pivoteM3(int[] ar, int start, int end) {
        if (start == end)
            return start;

        int l = start;
        int n = end;
        int m = (l + n) >>> 1;
        int len = end + 1 - start;
        m = medianOfThree(ar, l, m, n); // Mid-size, med of 3

        //        int mid = medianOfThree(ar, start, end);
        int pivot = ar[m];
        swap(ar, end, m);
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        ArrayUtils.swap(ar, i, end);
        return i;
    }

    public static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    private static int medianOfThree(int[] ar, int a, int b, int c) {
        return (ar[a] < ar[b] ? (ar[b] < ar[c] ? b : ar[a] < ar[c] ? c : a) :
                (ar[b] > ar[c] ? b : ar[a] > ar[c] ? c : a));
    }

    final static class NotLessThanHundred {
        private volatile int a, b;
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // let's not make it fair for performance purpose.

        NotLessThanHundred(int a, int b) {
            if (a + b < 100) throw new IllegalArgumentException("sum of a and b should not be less than 100" + (a + b));
            this.a = a;
            this.b = b;
        }

        public boolean setA(int value) {
            boolean updated;
            lock.writeLock().lock();
            updated = value + b >= 100;
            if (updated) a = value;
            lock.writeLock().unlock();
            return updated;
        }

        public boolean setB(int value) {
            boolean updated;
            lock.writeLock().lock();
            updated = value + a >= 100;
            if (updated) b = value;
            lock.writeLock().unlock();
            return updated;
        }

        public int getA() {
            int val;
            lock.readLock().lock();
            val = a;
            lock.readLock().unlock();
            return val;
        }

        public int getB() {
            int val;
            lock.readLock().lock();
            val = a;
            lock.readLock().unlock();
            return val;
        }

        public int getSum() {
            int val;
            lock.readLock().lock();
            val = a + b;
            lock.readLock().unlock();
            return val;
        }

        public boolean set(int a, int b) {
            if (a + b < 100) return false;
            lock.writeLock().lock();
            this.a = a;
            this.b = b;
            lock.writeLock().unlock();
            return true;
        }
    }
}
