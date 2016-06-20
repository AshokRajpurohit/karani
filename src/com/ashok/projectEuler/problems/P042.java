package com.ashok.projecteuler.problems;


import java.util.List;

public class P042 {
    private static int[] map = new int[256];

    static {
        for (int i = 'A'; i <= 'Z'; i++)
            map[i] = i + 1 - 'A';
    }

    public static int solve(List<String> words) {
        int count = 0;

        for (String word : words)
            if (isTriangleNumber(wordToNumber(word)))
                count++;

        return count;
    }

    public static int wordToNumber(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++)
            res += map[s.charAt(i)];

        return res;
    }

    public static boolean isTriangleNumber(int n) {
        n = (n << 1);
        int m = (int) Math.sqrt(n);

        if (m * (m + 1) == n || (m + 1) * (m + 2) == n)
            return true;

        return false;
    }
}
