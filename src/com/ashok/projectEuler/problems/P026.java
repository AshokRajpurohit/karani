package com.ashok.projecteuler.problems;


import java.util.HashSet;

public class P026 {
    private P026() {
        super();
    }

    public static int countRecurringDigits(int n) {
        HashSet<Integer> hs = new HashSet<Integer>();
        int count = 0, temp = 1;
        while (!hs.contains(temp)) {
            hs.add(temp);
            count++;
            temp = (temp * 10) % n;
        }

        return count;
    }

    public static String recurringDigits(int n) {
        StringBuilder sb = new StringBuilder();
        HashSet<Integer> hs = new HashSet<Integer>();
        int temp = 1;
        while (!hs.contains(temp)) {
            hs.add(temp);
            sb.append((temp * 10) / n);
            temp = (temp * 10) % n;
        }

        return sb.toString();
    }
}
