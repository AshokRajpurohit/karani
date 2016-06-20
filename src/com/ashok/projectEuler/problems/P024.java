package com.ashok.projectEuler.problems;


public class P024 {
    private static int[] fact = new int[10];

    static {
        fact[0] = 1;

        for (int i = 1; i < 10; i++)
            fact[i] = fact[i - 1] * i;
    }

    private P024() {
        super();
    }

    public static String solve(int digitCount, int n) {
        StringBuilder sb = new StringBuilder(digitCount);

        char[] ar = new char[digitCount];
        if (ar.length <= 10 && ar.length > 0) {
            for (int i = 0; i < ar.length; i++)
                ar[i] = (char) (i + '0');
        } else {
            throw new RuntimeException("Bhad mein ja");
        }

        permutation(sb, ar, n);
        return sb.toString();
    }

    public static String solve(String characters, int n) {
        StringBuilder sb = new StringBuilder(characters.length());

        char[] ar = new char[characters.length()];

        for (int i = 0; i < ar.length; i++)
            ar[i] = characters.charAt(i);

        permutation(sb, ar, n);
        return sb.toString();
    }

    private static void permutation(StringBuilder sb, char[] ar, int n) {

        boolean[] digits = new boolean[ar.length];
        for (int i = 0; i < ar.length; i++)
            digits[i] = true;

        int digi = ar.length - 1;

        for (int i = 0; i < digits.length; i++) {
            if (n > fact[digi - i]) {
                int shift = n / fact[digi - i];
                n = n % fact[digi - i];

                if (n == 0) {
                    n = fact[digi - i];
                    shift--;
                }
                char temp = ar[i];
                ar[i] = ar[i + shift];
                for (int j = i + shift; j > i; j--)
                    ar[j] = ar[j - 1];

                ar[i + 1] = temp;
            }
        }

        for (int i = 0; i < digits.length; i++)
            sb.append(ar[i]);
    }

    private static void process(StringBuilder sb, boolean[] digits, int n) {
        System.out.println(n + "\t" + sb);
        if (n == 0)
            return;

        int i = 10;
        while (i > 0 && fact[i] >= n)
            i--;

        int dig = (n - 1) / fact[i];
        n = n % fact[i];
        n++;

        int j = 9, count = 1;
        while (count <= i && j >= 0) {
            if (digits[j])
                count++;
            j--;
        }

        System.out.println(j + " :");

        while (j >= 0 && !digits[j])
            j--;

        System.out.println(i + "\t le \t" + j);

        for (i = 0; i < j; i++)
            if (digits[i]) {
                sb.append(i);
                digits[i] = false;
            }

        count = 0;
        while (count < dig && j <= 9) {
            if (digits[j])
                count++;
            j++;
        }

        if (j >= 0 && j < 10 && digits[j]) {
            digits[j] = false;
            sb.append(j);
        }

        process(sb, digits, n);
    }
}
