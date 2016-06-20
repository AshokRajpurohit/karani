package com.ashok.projecteuler.problems;


import java.lang.reflect.Array;
import java.util.LinkedList;

public class P060 {
    private static boolean[] checkPrime;
    private static int[] primes = gen_prime(10000);
    private static LinkedList<Pair>[] twinPrimeList;

    public static int solve(int n) {
        LinkedList<Pair> temp = new LinkedList<Pair>();
        LinkedList<Pair>[] list =
                (LinkedList<Pair>[]) Array.newInstance(temp.getClass(),
                        primes.length);

        LinkedList<Four> fourList = new LinkedList<Four>();

        long t = System.currentTimeMillis();
        populate(list);
        System.out.println(System.currentTimeMillis() - t);
        printListSize(list);
        twinPrimeList = list;

        t = System.currentTimeMillis();
        populate(fourList);
        System.out.println(System.currentTimeMillis() - t);

        int sum = Integer.MAX_VALUE;
        System.out.println("Pair 4 counts " + fourList.size());
        System.out.println("max value " + Integer.MAX_VALUE);
        Four f = fourList.getFirst();
        int last = 0;

        for (Four four : fourList) {
            for (int i = four.index + 1; i < primes.length; i++) {
                if (check(four.a, four.b, four.c, four.d, primes[i]))
                    if (sum > four.a + four.b + four.c + four.d + primes[i]) {
                        sum = four.a + four.b + four.c + four.d + primes[i];
                        f = four;
                        last = primes[i];
                    }
            }
        }

        System.out.println(f + ", " + last);

        return sum;
    }

    private static void printListSize(LinkedList<Pair>[] list) {
        int sum = 0;
        for (LinkedList<Pair> e : list)
            if (e != null)
                sum += e.size();

        System.out.println(sum);
    }

    private static void populate(LinkedList<Pair>[] list) {
        for (int i = 1; i < primes.length; i++) {
            if (primes[i] != 5) {
                list[i] = new LinkedList<Pair>();

                for (int j = i + 1; j < primes.length; j++) {
                    if (primes[j] == 5)
                        continue;

                    if (check(primes[i], primes[j]))
                        list[i].add(new Pair(j, primes[j]));
                }
            }
        }
    }

    private static void populate(LinkedList<Four> listFour) {
        LinkedList<Pair>[] list = twinPrimeList;

        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                for (Pair p : list[i]) {
                    int index = p.index;

                    for (int j = p.index + 1; j < list.length; j++) {
                        if (check(primes[i], p.value, primes[j])) {
                            if (list[j] != null) {
                                for (Pair s : list[j]) {
                                    if (check(primes[i], primes[index],
                                            s.value))

                                        listFour.add(new Four(primes[i],
                                                p.value,
                                                primes[j],
                                                s.value,
                                                s.index));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean check(int a, int b, int c) {
        return check(a, c) && check(b, c);
    }

    private static boolean check(int a, int b, int c, int d) {
        return check(a, d) && check(b, d) && check(c, d);
    }

    private static boolean check(int a, int b, int c, int d, int e) {
        return check(a, e) && check(b, e) && check(c, e) && check(d, e);
    }

    private static boolean check(int a, int b) {
        return isPrime(add(a, b)) && isPrime(add(b, a));
    }

    private static long add(long a, long b) {
        long temp = b;

        while (temp > 0) {
            temp /= 10;
            a = (a << 3) + (a << 1);
        }

        return a + b;
    }

    private static boolean isPrime(long n) {
        if (n < checkPrime.length)
            return !checkPrime[(int) n];

        int root = (int) Math.sqrt(n);
        if (root > primes[primes.length - 1]) {
            return false;
        }

        for (int i = 0; i < primes.length && primes[i] <= root; i++)
            if (n % primes[i] == 0)
                return false;

        return true;
    }

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }

        checkPrime = ar;
        return ret;
    }

    final static class Four {
        final int a, b, c, d, index;

        Four(int a, int b, int c, int d, int index) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.index = index;
        }

        public String toString() {
            return a + ", " + b + ", " + c + ", " + d;
        }
    }

    final static class Pair {
        final int value, index;

        Pair(int i, int v) {
            value = v;
            index = i;
        }
    }
}
