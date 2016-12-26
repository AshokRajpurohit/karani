package com.ashok.projecteuler.problems;

import com.ashok.lang.utils.ArrayUtils;

import java.util.Arrays;

/**
 * Problem: Coin sums
 * Link: https://projecteuler.net/problem=31
 * <p>
 * Description:
 * In England the currency is made up of pound, £, and pence, p, and there are eight coins in general circulation:
 * <p>
 * 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).
 * It is possible to make £2 in the following way:
 * <p>
 * 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
 * How many different ways can £2 be made using any number of coins?
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P031 {
    private int[] coins;
    private long[][] cache;

    public P031(int[] coinArray) {
        coins = coinArray.clone();
        Arrays.sort(coins);
        ArrayUtils.reverse(coins);
        intializeCache();
    }

    private void intializeCache() {
        int arraySize = 10000000 / coins.length;
        cache = new long[coins.length][arraySize];

        for (long[] ar : cache)
            Arrays.fill(ar, -1); // mark un-calculated.
    }

    public long waysToGet(int n) {
        return waysToGet(0, n);
    }

    private long waysToGet(int fromIndex, final int value) {
        if (value == 0)
            return 1;

        if (fromIndex == coins.length)
            return 0;

        if (isCalculated(fromIndex, value))
            return cache[fromIndex][value];

        long res = 0;
        int copyValue = value;
        while (copyValue >= 0) {
            res += waysToGet(fromIndex + 1, copyValue);
            copyValue -= coins[fromIndex];
        }

        updateCache(fromIndex, value, res);
        return res;
    }

    private void updateCache(int index, int arrayIndex, long value) {
        if (cache[index].length <= arrayIndex)
            return;

        cache[index][arrayIndex] = value;
    }

    private boolean isCalculated(int index, int value) {
        return cache[index].length > value && cache[index][value] != -1;
    }
}
