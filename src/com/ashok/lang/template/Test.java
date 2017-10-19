/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.template;

import com.ashok.lang.dsa.GroupOperator;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Prime;
import com.ashok.lang.utils.Generators;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Problem Name: Link:
 * <p>
 * For full implementation please see {@link https
 * ://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Test {
    private static final String path = "C:/Projects/karani/src/com/ashok/lang/template/";
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException,
            InterruptedException {
        try {
            play();
        } finally {
            out.close();
        }
    }

    private static void play() throws IOException, InterruptedException {
        out.println(Integer.MAX_VALUE);
        out.println(1L << 32);
        out.flush();
        while (true) {
            int n = in.readInt();
            testSync(new Semaphore(1), n);
            out.flush();
            long l = 1;
            for (int i = 1; i <= n; i++) {
                out.println("" + i + "\t" + l);
                l <<= 1;
            }

            out.flush();
        }
    }

    private static void testSync(Semaphore semaphore, int count) throws InterruptedException {
        if (count <= 0)
            return;

        semaphore.acquire();
        semaphore.getQueueLength();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        lock.writeLock().lock();
        lock.writeLock().unlock();
        out.println("queue length: " + semaphore.getQueueLength());
        testSync(semaphore, count - 1);
        semaphore.release();
    }

    private static long sum(int[] ar) {
        long sum = 0;
        for (int e : ar)
            sum += e;

        return sum;
    }

    private static void printFileNames(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Arrays.sort(files);
            for (File f : file.listFiles())
                printFileNames(f);
        } else
            out.println(file.getAbsolutePath());
    }

    private static int findLarger(int[] ar, int key) {
        int index = Arrays.binarySearch(ar, key);
        if (index >= 0)
            return index;

        index = -index;
        return index - 1;
    }

    private static int findSmaller(int[] ar, int key) {
        int index = Arrays.binarySearch(ar, key);
        if (index >= 0)
            return index;

        index = -index;
        index--; // insertion point
        return index - 1; // smaller element position.
    }

    private static void caller(Object o) {
        callMethod(o);
    }

    private static void callMethod(Object o) {
        out.println("callMethod for object object");
    }

    private static void callMethod(Parent p) {
        out.println(p instanceof Parent);
        out.println(p instanceof Child);
        out.println("callMethod for parent object");
        out.println(p.getClass());
    }

    private static void callMethod(Child c) {
        out.println(c instanceof Parent);
        out.println(c instanceof Child);
        out.println("callMethod for child object");
    }

    private static <K, V> Map<K, V> getMap(int size) {
        return new TreeMap<K, V>();
    }

    static class Parent {
        Parent(String name) {
            // do nothing
        }
    }

    static class Child extends Parent {
        Child() {
            super("ashok");
        }
    }

    private static void methodThrowingError(int n) throws Error {
        if ((n & 1) == 1) {
            throw new Error("error thrown");
        }

        out.println("nothing happend");
        out.flush();
    }

    private static void methodThrowingRuntimeException(int n)
            throws RuntimeException {
        if ((n & 1) == 1) {
            throw new RuntimeException("error thrown");
        }

        out.println("nothing happend in RuntimeException");
        out.flush();
    }

    private static void methodThrowingException(int n) throws Exception {
        if ((n & 1) == 1) {
            throw new Exception("error thrown");
        }

        out.println("nothing happend in Exception");
        out.flush();
    }

    private static void methodThrowingThrowable(int n) throws Throwable {
        if ((n & 1) == 1) {
            throw new Throwable("error thrown");
        }

        out.println("nothing happend in Exception");
        out.flush();
    }

    private static int[][] generateInput(int n, int m) {
        int[][] res = new int[n][];

        for (int i = 0; i < n; i++)
            res[i] = Generators.generateRandomIntegerArray(m, -2, 10);

        return res;
    }

    private static String convert(int[][] ar) {
        StringBuilder sb = new StringBuilder();
        for (int[] a : ar) {
            for (int e : a)
                sb.append(e).append('@');

            sb.deleteCharAt(sb.length() - 1);
            sb.append('#');
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static void solve() throws IOException {
        while (true) {
            out.print(Prime.primesInRange(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static void updateInt(Integer n) {
        n++;
    }

    static class Custom {
        int value = 0;
    }

    static class CustomExtend extends Custom {
        int value = 10;
    }

    public static void puzzle(int totalNumbersCount) {

        List<Integer> numberList = new ArrayList<Integer>();
        for (int i = 0; i < totalNumbersCount; i++) {
            numberList.add(i + 1);
        }

        while (true) {
            numberList = copyNumbers(numberList);
            numberList.notify();
            if (numberList.size() == 1) {
                System.out.println("Number Remaining is " + numberList.get(0));
                break;
            }
        }
    }

    private static List<Integer> copyNumbers(List<Integer> numberList) {

        List<Integer> copiedNumberList = new ArrayList<Integer>();
        for (int i = 1; i < numberList.size(); i = i + 2) {
            copiedNumberList.add(numberList.get(i));
        }
        return copiedNumberList;
    }

    final static class Operator implements GroupOperator<Long> {

        @Override
        public Long operation(Long first, Long second) {
            return first + second;
        }

        @Override
        public Long inverseOperation(Long first, Long second) {
            return first - second;
        }

        @Override
        public Long newInstance() {
            return 0L;
        }
    }

    private static void normalizeIndex(int[] start, int[] end) {
        for (int i = 0; i < start.length; i++)
            if (start[i] > end[i]) {
                int t = start[i];
                start[i] = end[i];
                end[i] = t;
            }
    }

    final static class Question4 {

        public static void solve() throws IOException {
            int testCases = in.readInt();
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while (i < testCases) {
                String nextLine = in.next();
                long numOperations = in.readLong();
                long position = in.readLong();
                // String nextLine = "ab";
                // position = position - 1;
                StringBuilder sb1 = new StringBuilder(nextLine);
                StringBuilder sb2 = new StringBuilder(nextLine);
                sb1.append(sb2.reverse());
                StringBuilder sb3 = new StringBuilder(" ").append(sb1);
                sb.append(findKthCharacter(sb3, position));
                if (i != testCases - 1) {
                    sb.append("\n");
                }
                i++;
            }
            System.out.println(sb.toString());
        }

        public static void solve(long numOperations, long position, String s) {
            StringBuilder sb1 = new StringBuilder(s);
            StringBuilder sb2 = new StringBuilder(s);
            sb1.append(sb2.reverse());
            StringBuilder sb3 = new StringBuilder(" ").append(sb1);
            out.println(findKthCharacter(sb1, position));
        }

        private static char findKthCharacter(StringBuilder sb1, long position) {
            int size = sb1.length() - 1;
            if (position % size == 0) {
                return sb1.charAt(1);
            }
            int index = (int) (position % size);
            // System.out.println(sb1);
            // System.out.println(index);
            char charAt = sb1.charAt(index);
            // System.out.println(charAt);
            return charAt;
        }

    }

}
