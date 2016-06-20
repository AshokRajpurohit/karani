package com.ashok.projectEuler.problems;


import java.util.Arrays;
import java.util.LinkedList;

public class P041 {

    private static int[] map = new int[256];

    static {
        for (int i = '0'; i <= '9'; i++) {
            map[i] = i - '0';
        }
    }

    public static int solve(int n) {
        if (n > 8 || n < 4)
            return 1;

        if (n % 3 == 0 || n % 3 == 2)
            return 1;

        StringBuilder sb = new StringBuilder(n);
        for (int i = 1; i <= n; i++) {
            sb.append(i);
        }

        int p = 1;
        for (int i = 0; i < n; i++)
            p = (p << 3) + (p << 1);

        boolean[] primes = gen_prime(p);
        int[] pan_digit_numbers = permutations(sb.toString());
        Arrays.sort(pan_digit_numbers);

        for (int i = pan_digit_numbers.length - 1; i >= 0; i--)
            if (!primes[pan_digit_numbers[i]])
                return pan_digit_numbers[i];

        return 0;
    }

    private static int[] permutations(String s) {
        char[] ar = s.toCharArray();
        int[] nums = new int[fact(s.length())];
        LinkedList<Integer> list = new LinkedList<Integer>();

        for (int i = 0; i < s.length(); i++) {
            swap(ar, 0, i);
            list.add(toInteger(ar));
            permutation(ar, list, 1);
            swap(ar, 0, i);
        }

        int i = 0;
        for (Integer e : list)
            nums[i++] = e;

        return nums;
    }

    private static void permutation(char[] ar, LinkedList<Integer> list,
                                    int index) {
        if (index == ar.length - 1)
            return;

        for (int i = index + 1; i < ar.length; i++) {
            swap(ar, index, i);
            list.add(toInteger(ar));
            permutation(ar, list, index + 1);
            swap(ar, index, i);
        }
    }

    private static void swap(char[] ar, int a, int b) {
        char temp = ar[a];
        ar[a] = ar[b];
        ar[b] = temp;
    }

    private static int toInteger(char[] ar) {
        int res = map[ar[0]];
        for (int i = 1; i < ar.length; i++)
            res = (res << 3) + (res << 1) + map[ar[i]];

        return res;
    }

    private static int fact(int n) {
        int res = 1;
        for (int i = 2; i <= n; i++)
            res *= i;

        return res;
    }

    private static boolean[] gen_prime(int n) {
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

        return ar;
    }
}
