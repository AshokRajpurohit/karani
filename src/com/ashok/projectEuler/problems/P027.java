package com.ashok.projectEuler.problems;


/**
 * Problem 27 : Quadratic primes
 * <p>
 * Euler discovered the remarkable quadratic formula:
 * <p>
 * nﾲ + n + 41
 * <p>
 * It turns out that the formula will produce 40 primes for the consecutive
 * values n = 0 to 39. However, when n = 40, 402 + 40 + 41 = 40(40 + 1) + 41
 * is divisible by 41, and certainly when n = 41, 41ﾲ + 41 + 41 is clearly
 * divisible by 41.
 * <p>
 * The incredible formula  nﾲ ? 79n + 1601 was discovered, which produces 80
 * primes for the consecutive values n = 0 to 79. The product of the
 * coefficients, ?79 and 1601, is ?126479.
 * <p>
 * Considering quadratics of the form:
 * <p>
 * nﾲ + an + b, where |a| < 1000 and |b| < 1000
 * <p>
 * where |n| is the modulus/absolute value of n
 * e.g. |11| = 11 and |?4| = 4
 * Find the product of the coefficients, a and b, for the quadratic expression
 * that produces the maximum number of primes for consecutive values of n,
 * starting with n = 0.
 */
public class P027 {
    private static boolean[] primes = new boolean[1000001];

    static {
        for (int i = 2; i < primes.length; i++)
            primes[i] = true;

        for (int i = 2; i <= 1000; i++) {
            while (!primes[i])
                i++;

            for (int j = i << 1; j < primes.length; j += i)
                primes[j] = false;
        }
    }

    public static int solve() {
        int multi = 0, max = 0;
        int a = 0, b = 0;
        for (int i = -1000; i <= 1000; i++) {
            for (int j = -1000; j <= 1000; j++) {
                if (!primes[Math.abs(j)])
                    continue;

                int count = getCount(i, j);
                if (max < count) {
                    a = i;
                    b = j;
                    multi = i * j;
                    max = count;
                }
            }
        }

        System.out.println(a + ", " + b + " | " + max + " | " + multi);

        return multi;
    }

    private static int getCount(int a, int b) {
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            int value = i * i + a * i + b;
            value = Math.abs(value);
            if (value >= primes.length || !primes[value])
                return count;

            count++;
        }

        return count;
    }
}
