package com.ashok.projecteuler.problems;


/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class P035 {
    private static boolean[] prime;

    static {
        prime = new boolean[1000000];
        for (int i = 2; i < 1000000; i++)
            prime[i] = true;

        for (int i = 2; i < 1000; i++)
            for (int j = i << 1; j < 1000000; j += i)
                if (prime[j])
                    prime[j] = false;
    }

    private P035() {
        super();
    }

    public static int solve() {
        int count = 0, pow = 10;
        for (int i = 2; i < 9; i++)
            if (prime[i])
                count++;

        for (int i = 2; i < 7; i++) {
            count += getCount(i, pow);
            pow = 10 * pow;
        }
        return count;
    }

    private static int getCount(int digits, int pow) {
        int count = 0, lim = pow * 10;
        for (int i = pow + 1; i < lim; i++)
            if (possible(digits, i, pow))
                count++;

        return count;
    }

    private static boolean possible(int digits, int n, int pow) {
        if (!prime[n])
            return false;

        for (int i = 1; i < digits && prime[n]; i++) {
            n = pow * (n % 10) + (n / 10);
        }

        if (!prime[n]) {
            for (int i = 1; i < digits; i++) {
                n = pow * (n % 10) + (n / 10);
                prime[n] = false;
            }
        }

        return prime[n];
    }
}
