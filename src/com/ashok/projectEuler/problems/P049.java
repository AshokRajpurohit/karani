package com.ashok.projectEuler.problems;

import com.ashok.lang.math.Prime;
import com.ashok.lang.utils.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P049 {
    private static boolean[] primeCheck = new boolean[10000], check = new
            boolean[10000];
    private static int[] primes = Prime.primesInRange(1001, 9999);

    static {
        for (int e : primes)
            primeCheck[e] = true;
    }

    public static void solve() {
        LinkedList<Integer> list = new LinkedList<>();

        for (int prime : primes) {
            process(prime, list);
            if (list.size() != 0)
                break;
        }

        for (int e : list)
            System.out.print(e);

        System.out.println();
    }

    private static void process(int n, List<Integer> list) {
        if (check[n])
            return;

        int[] ar = permutations(n);
        for (int i = 0; i < ar.length - 2; i++) {
            for (int j = i + 1; j < ar.length - 1; j++) {
                int key = (ar[j] << 1) - ar[i];
                int k = Arrays.binarySearch(ar, j + 1, ar.length, key);

                if (k >= 0 && k < ar.length && ar[k] == key) {
                    System.out.println(ar[i] + "" + ar[j] + "" + ar[k]);
                    return;
                }
            }
        }

        return;
    }

    private static int[] permutations(int n) {
        int[] ar = new int[4];
        int r = n;
        int index = 0;
        while (r > 0) {
            ar[index++] = r % 10;
            r /= 10;
        }

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            int a = ar[i];
            ar[i] = -1;
            for (int j = 0; j < 4; j++) {
                if (ar[j] == -1)
                    continue;

                int b = ar[j];
                ar[j] = -1;

                for (int k = 0; k < 4; k++) {
                    if (ar[k] == -1)
                        continue;

                    int c = ar[k];
                    ar[k] = -1;
                    for (int l = 0; l < 4; l++) {
                        if (ar[l] == -1)
                            continue;

                        int num = 1000 * a + 100 * b + 10 * c + ar[l];
                        if (primeCheck[num])
                            list.add(num);

                        check[num] = true;
                    }

                    ar[k] = c;
                }

                ar[j] = b;
            }

            ar[i] = a;
        }

        int[] res = ArrayUtils.toArray(list);
        Arrays.sort(res);
        List<Integer> uniques = new LinkedList<>();
        uniques.add(res[0]);

        for (int i = 1; i < res.length; i++)
            if (res[i] != res[i - 1])
                uniques.add(res[i]);

        return ArrayUtils.toArray(uniques);
    }
}
