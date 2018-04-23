package com.ashok.friends.harsh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Bankbazar {
    private static PrintWriter out = new PrintWriter(System.out);
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int num = 1048576 - 1; //i.e. 2 ^ 20 - 1
    private static int[] specialNumberCount;

    public static void main(String[] args) throws IOException {
        int t = Integer.valueOf(br.readLine());
        StringBuilder sb = new StringBuilder(t << 2);
        int[] first = new int[t], second = new int[t];
        for (int i = 0; i < t; i++) {
            String[] ar = br.readLine().split(" ");
            first[i] = new Integer(ar[0]);
            second[i] = new Integer(ar[1]);
        }

        populate(max(second));
        for (int i = 0; i < t; i++)
            sb.append(query(first[i], second[i])).append('\n');

        out.print(sb);

        br.close();
        out.close();
    }

    static int query(int start, int end) {
        return query(end) - query(start - 1);
    }

    static int query(int n) {
        return n < 0 ? 0 : specialNumberCount[n];
    }

    static int isSpecial(int n) {
        if (n == 0)
            return 0;
//        n = removeTrailingZeroes(n); // we don't need to check this.
        int trailingOnes = numberOfTrailingOnes(n);
        n = removeTrailingOnes(n);
        n = removeTrailingZeroes(n);

        int lastTrailingZeroes = isSpecial(n);
        return lastTrailingZeroes != -1 && trailingOnes > lastTrailingZeroes ? trailingOnes : -1;
    }

    static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    static void populate(int limit) {
        specialNumberCount = new int[limit + 1];
        Arrays.fill(specialNumberCount, -1);
        specialNumberCount[0] = 0;
        for (int i = 1; i <= limit; i++) {
            if (specialNumberCount[i] == -1) { // unmarked.
                int val = 0;
                if (isSpecial(i) != -1)
                    val = 1;

                for (int j = i; j <= limit; j = j << 1)
                    specialNumberCount[j] = val; // marking as visited or processed.
            }
        }

        // populate actual count sum.
        for (int i = 2; i <= limit; i++)
            specialNumberCount[i] += specialNumberCount[i - 1];
    }

    static int removeTrailingOnes(int n) {
        while ((n & 1) == 1)
            n >>>= 1;

        return n;
    }

    static int removeTrailingZeroes(int n) {
        if (n == 0)
            return n;

        while ((n & 1) == 0)
            n >>>= 1;

        return n;
    }

    static int numberOfTrailingOnes(int n) {
        return Integer.numberOfTrailingZeros(n ^ num);
    }
}