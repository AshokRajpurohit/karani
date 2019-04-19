/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.template;

import com.ashok.lang.algorithms.Strings;
import com.ashok.lang.concurrency.Turnstile;
import com.ashok.lang.dsa.GroupOperator;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Power;
import com.ashok.lang.math.Prime;
import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;
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
    private static String start = "this is ";

    public static void main(String[] args) throws IOException,
            InterruptedException {
        try {
            ConcurrentSkipListMap map = new ConcurrentSkipListMap();
            TreeMap treeMap = new TreeMap();
            play();
        } finally {
            out.close();
        }
    }

    private static void play() throws IOException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.tryAcquire();
        SingletonFactory factory = new SingletonFactory();
        Callable<Singleton> tasks = () -> factory.get();
        while (true) {
            break;
        }
    }

    private static class Singleton {
        private static volatile Singleton object;
        private static final Semaphore lock = new Semaphore(1);
        private static final Turnstile TURNSTILE = new Turnstile(0);

        private Singleton() {
            out.println("creating Singleton");
        }

        private static Singleton get() throws InterruptedException {
            if (lock.tryAcquire()) {
                object = new Singleton();
                TURNSTILE.release(1);
            }

            TURNSTILE.passThrough();
            return object;
        }
    }

    private static class SingletonFactory {
        private volatile Singleton object;
        private final Semaphore lock = new Semaphore(1);
        private final Turnstile turnstile = new Turnstile(0);

        private Singleton get() {
            if (lock.tryAcquire()) {
                object = new Singleton();
                turnstile.release(1);
            }

            try {
                turnstile.passThrough();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return object;
        }
    }

    /**
     * Moves all the files from {@code source} directory to {@code target} directory.
     *
     * @param source
     * @param target
     */
    static Path moveFiles(String source, String target) throws IOException {
        Path tar = Files.createDirectories(Paths.get(target).resolve(System.currentTimeMillis() + ""));
//        Path tar = Files.createDirectory(Paths.get(target).resolve(System.currentTimeMillis() + ""));
        File file = new File(source);
        out.println(file.list());
        for (File it : file.listFiles()) {
            boolean res = it.renameTo(new File(tar.toString(), it.getName()));
            out.println("transferred file: " + it.getAbsolutePath() + ": " + res);
        }

        return tar;
    }

    static void moveAllFiles(String source, String target) throws IOException {
        if (Files.notExists(Paths.get(target)))
            Files.copy(Paths.get(source), Paths.get(target));

        File file = new File(source);
        if (!file.isDirectory())
            return;

        for (File it : file.listFiles())
            moveAllFiles(it.toString(), Paths.get(target, it.getName()).toString());
    }

    static boolean delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
        }

        return file.delete();
    }

    private static boolean primeDigits(long n) {
        while (n > 1) {
            long d = n % 10;
            n /= 10;
            if (!isPrimeDigit(d))
                return false;
        }

        return true;
    }

    private static boolean increasingDigits(long n) {
        long d = 10;
        while (n > 0) {
            long digit = n % 10;
            n /= 10;
            if (digit >= d)
                return false;

            d = digit;
        }

        return true;
    }

    private static boolean isPrimeDigit(long n) {
        return n == 2 || n == 3 || n == 5 || n == 7;
    }

    final static class ThreadExt extends Thread {
        ThreadExt(Object lock) {
            function(lock);
            out.println("khatam");
            out.flush();
        }
    }

    private static void function(Object lock) {
        synchronized (lock) {
            out.println("ye lock hai" + lock);
            out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    final static class Pair {
        long clientId, sdsId;

        Pair(int a, int b) {
            clientId = a;
            sdsId = b;
        }

        public int hashCode() {
            return Long.hashCode(1L * Long.hashCode(clientId) * Long.hashCode(sdsId));
        }

        public boolean equals(Object object) {
            if (this == object)
                return true;

            if (!(object instanceof Pair))
                return false;

            Pair pair = (Pair) object;
            return clientId == pair.clientId && sdsId == pair.sdsId;
        }
    }

    private static boolean validate(int[] ar, int[] nextEqualIndex) {
        for (int i = 0; i < ar.length; i++) {
            int j = i + 1;
            while (j < ar.length && ar[j] != ar[i]) j++;
            if (j != nextEqualIndex[i])
                return false;
        }

        return true;
    }

    private static void dbQueries() throws IOException {
        while (true) {
            String query = in.readLine(), pattern = in.readLine();
            int count = in.readInt();
            String[] list = in.readLineArray(count);
            out.println(Strings.replace(query, pattern, list));
            out.flush();
        }
    }

    final static Comparator<LinkedListNode> LIST_NODE_COMPARATOR = (a, b) -> a.val - b.val;

    static LinkedListNode sort(int k, LinkedListNode list) {
        // Write your code here.
        LinkedListNode[] nodes = toArray(list);
        k = Math.min(k, nodes.length);
        int start = 0, end = k, len = nodes.length;
        while (start <= len) {
            end = Math.min(end, len);
            Arrays.sort(nodes, start, end, LIST_NODE_COMPARATOR);
            start += k;
            end += k;
        }

        for (int i = 0; i < len - 1; i++)
            nodes[i].next = nodes[i + 1];

        nodes[len - 1] = null;
        return nodes[0];
    }

    private static LinkedListNode[] toArray(LinkedListNode list) {
        int size = getSize(list);
        LinkedListNode[] ar = new LinkedListNode[size];
        for (int i = 0; i < size; i++, list = list.next)
            ar[i] = list;

        return ar;
    }

    private static int getSize(LinkedListNode list) {
        int count = 0;
        LinkedListNode iterator = list;
        while (iterator != null) {
            iterator = iterator.next;
            count++;
        }

        return count;
    }

    private static class LinkedListNode {
        int val;
        LinkedListNode next;
    }

    private static LinkedListNode _insert_node_into_singlylinkedlist(LinkedListNode list, LinkedListNode listTail, int val) {
        LinkedListNode node = new LinkedListNode();
        node.val = val;

        if (list == null)
            return node;

        listTail.next = node;
        return node;
    }

    static long calculateCombinations(int n, int r) {
        // recursive way;
        if (r == 0)
            return 1;

        return calculateCombinations(n - 1, r - 1) * n / r;
    }

    /**
     * Iterative way
     */
    private static long ncr(int n, int r) {
        r = Math.min(r, n - r);
        long result = 1L;
        n = n - r + 1;

        for (int i = 1; i <= r; i++, n++)
            result = result * n / i;

        return result;
    }

    private static long bruteForce(int[] ar) {
        Arrays.sort(ar);
        long val = 0;
        int len = ar.length;
        for (int i = 0; i < len; i++)
            for (int j = i + 1; j < len; j++)
                for (int k = j + 1; k < len; k++) {
                    if (ar[k] >= ar[i] + ar[j])
                        break;

                    val = Math.max(val, ar[i] + ar[j] + ar[k]);
                }

        return val;
    }

    private static long optimized(int[] ar) {
        Arrays.sort(ar);
        ArrayUtils.reverse(ar);
        for (int i = 0, j = 1, k = 2; k < ar.length; i++, j++, k++)
            if (ar[i] < ar[j] + ar[k])
                return ar[i] + ar[j] + ar[k];

        return 0;
    }

    private static abstract class Aclass {
        public int get() {
            return get2();
        }

        public abstract int get1();

        public int get2() {
            return 4;
        }
    }

    private static class Bclass extends Aclass {

        @Override
        public int get1() {
            return -1;
        }

        @Override
        public int get2() {
            return 17;
        }
    }

    private static void synchronizedMethod() {
        synchronized (out) {
            out.println(Thread.currentThread().getName());
            out.println("just nothing");
            out.flush();
            out.println("another print");
            out.flush();
        }
    }

    public static void solveThis(String s) {
        char[] chars = s.toCharArray();
        int len = s.length();
        int j = 1;
        int i = 0;
        for (i = 0; j < len; ) {
            if (i == -1) {
                chars[i + 1] = chars[j];
                i++;
                j++;
                continue;
            }
            if (i < 0) {
                i++;
                continue;
            }
            if (chars[i] != chars[j]) {
                chars[i + 1] = chars[j];
                i++;
                j++;
            } else {
                while (j < len && chars[i] == chars[j]) {
                    j++;
                }
                i--;
            }
        }
//        System.out.println(Arrays.toString(chars));
        s = new String(chars);
        out.println(s.substring(0, i + 1));
    }

    private static void encryptionTesting() throws IOException, InterruptedException {
        String a = null;
//        System.out.println(a.equals(b));
        int[] ar = new int[256];
        for (int i = 'a'; i <= 'z'; i++)
            ar[i] = i + 1 - 'a';

        for (int i = 'A'; i <= 'Z'; i++)
            ar[i] = i + 1 - 'A';

        long mod = 10000000000L;
        while (true) {
            out.println("Enter max number and max power for testing");
            out.flush();
            int n = in.readInt(), p = in.readInt();
            for (int i = 2; i <= n; i++)
                for (int j = 2; j <= p; j++) {
                    long actual = calculateModPower(i, j, mod), expected = Power.pow(i, j, mod);
                    if (actual != expected)
                        out.println("Incorrect value for " + i + "->" + j + ", actual: " + actual + ", expected: " + expected);
                }

            out.flush();
//            out.println(Power.pow(in.readInt(), in.readInt(), mod));
//            out.print(Prime.primesInRange(in.readInt(), in.readInt()));
//            out.flush();
            /*int n = in.readInt(), k = in.readInt(), m = in.readInt();
            int v = (int)Power.pow(n, k, m);
            out.println(v);
            out.flush();
            out.println(ModularArithmatic.moduloRoot(v, k, m));
            out.flush();*/
            /*String s = in.read();
            char[] chars = s.toCharArray();
            StringBuilder sb = new StringBuilder(), sb1 = new StringBuilder();
            for (char ch : chars) {
                long num = ar[ch];
                if (num < 10)
                    sb.append('0');

                sb.append(num);
                num = Power.pow(num, 27, 29);
                if (num < 10)
                    sb1.append('0');

                sb1.append(num);
            }

            out.println(sb);
            out.println(sb1);*/

            out.flush();

            /*int p = in.readInt(), q = in.readInt(), k = in.readInt();
            Encryption encryption = new Encryption(p, q, k);
            while (true) {
                String s = in.read();
                String en = encryption.encrypt(s);
                out.println(en);
                out.println(encryption.decrypt(en));
                out.flush();
            }*/
        }
    }

    static private long calculateModPower(long a, long b, long mod) {
        long res = 1;
        while (b != 0) {
            if ((b & 1) == 1)
                res = (res * a) % mod;
            // if (res < 0) {
            // res = res + mod;
            // }
            a = (a * a) % mod;
            b = b >> 1;
        }
        return res;
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
