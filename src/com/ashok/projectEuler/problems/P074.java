package com.ashok.projecteuler.problems;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P074 {
    private static LinkedList<Integer> temp = new LinkedList<Integer>();
    private static LinkedList<Integer>[] listArray =
            (LinkedList<Integer>[]) Array.newInstance(temp.getClass(), 2177281); // = 6 * 9!
    private static boolean[] processing = new boolean[2177281];

    public static void solve(int n) {
    }

    public static long maximumAmount(int[] a, long k) {
        Arrays.sort(a);
        int count = 0;
        long sum = 0;
        int i = 0;

        for (i = a.length - 2; i >= 0; i--) {
            int soFar = a.length - i - 1;
            long n = soFar * (a[i + 1] - a[i]);

            if (n > k - count)
                n = k - count;

            if (k - count < soFar)
                return sum + (k - count) * a[i + 1];

            long tempCount = n / soFar;

            count += tempCount * soFar;
            sum += soFar * sum(a[i + 1] - tempCount + 1, a[i + 1]);

            if (count == k)
                return sum;

        }

        return sum + (k - count) * a[0];
    }

    static long sum(long start, long end) {
        long sum = (end - start + 1) * (start + end);
        return sum >>> 1;
    }


}
