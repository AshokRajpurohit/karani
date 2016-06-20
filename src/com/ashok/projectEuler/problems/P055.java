package com.ashok.projectEuler.problems;


import java.math.BigInteger;

public class P055 {
    private static final long limit = Long.MAX_VALUE / 10;

    public static int solve(int n, int iter) {
        int count = 0;

        for (int i = 1; i < n; i++)
            if (isLychrelNumber(i, iter))
                count++;

        return count;
    }

    private static boolean isLychrelNumber(int n, int iter) {
        int count = 1;
        long num = n;
        num += reverse(num);
        long rev;

        while (num < limit && count < iter) {
            count++;
            rev = reverse(num);

            if (num == rev)
                return false;

            num += rev;
        }

        BigInteger bi = new BigInteger(String.valueOf(num));

        while (count < iter) {
            BigInteger revb = reverse(bi);

            if (bi.equals(revb))
                return false;

            bi = bi.add(revb);
            count++;
        }

        return true;
    }

    private static BigInteger reverse(BigInteger bi) {
        StringBuilder revString = new StringBuilder();
        char[] ar = bi.toString().toCharArray();

        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            char temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }

        return new BigInteger(String.valueOf(ar));
    }

    private static long reverse(long n) {
        long res = 0;
        while (n > 0) {
            res = (res << 3) + (res << 1) + n % 10;
            n /= 10;
        }

        return res;
    }
}
