/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Share Market
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-17/algorithm/share-market/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ShareMarket {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] worth, counts, costs, worthSum;
    private static final String yes = "Yes\n", no = "No\n";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            int n = in.readInt();
            worth = in.readIntArray(n);
            int k = in.readInt();
            counts = in.readIntArray(k);
            costs = in.readIntArray(k);
            populate();

            for (int i = 0; i < k; i++)
                sb.append(possible(costs[i], counts[i], 0) ? yes : no);
        }

        out.print(sb);
    }

    private static void populate() {
        Arrays.sort(worth);
        worthSum = new int[worth.length];
        worthSum[0] = worth[0];

        for (int i = 1; i < worth.length; i++)
            worthSum[i] = worthSum[i - 1] + worth[i];
    }

    /**
     * Checks whether {@code count} number of items exists whose worth sum is equal to {@code cost}, in the
     * subarray starting from index {@code index}.
     * <p>
     * If smallest {@code count} items worth sum is more than {@code cost}, then there is no need to checking further
     * as there no selection can be smaller than minimum value.
     * <p>
     * If largest {@code count} items worth sum is smaller than {@code cost}, then again, there is no need to check
     * as any selection can not be larger than maximum value.
     * <p>
     * If number of remaining items are less than required (count), then we can't have a selection of {@code count}
     * items.
     *
     * @param cost
     * @param count
     * @param index
     * @return
     */
    private static boolean possible(int cost, int count, int index) {
        if (cost < 0 || count < 0 || index + count > worth.length)
            return false;

        if (count == 0 || cost == 0)
            return cost == 0 && count == 0;

        int minPossible = getMinimumWorth(index, count), maxPossible = getMaximumWorth(count);
        if (minPossible == maxPossible)
            return minPossible == cost;

        if (cost == minPossible || cost == maxPossible)
            return true;

        if (cost < minPossible || cost > maxPossible)
            return false;

        for (int i = index; i < worth.length; i++)
            if (possible(cost - worth[i], count - 1, i + 1))
                return true;

        return false;
    }

    /**
     * Returns the maximum value for any selection of {@code count} items.
     *
     * @param count selection size.
     * @return maximum worth for a selection of {@code count} items.
     */
    private static int getMaximumWorth(int count) {
        return getWorthSum(worth.length - 1) - getWorthSum(worth.length - count - 1);
    }

    /**
     * Returns the minimum value for any selection with items not before {@code index}.
     *
     * @param index start index for the selection.
     * @param count number of items in selection.
     * @return minimum value for any selection.
     */
    private static int getMinimumWorth(int index, int count) {
        return getWorthSum(index + count - 1) - getWorthSum(index - 1);
    }

    private static int getWorthSum(int index) {
        if (index < 0 || index >= worth.length)
            return 0;

        return worthSum[index];
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
