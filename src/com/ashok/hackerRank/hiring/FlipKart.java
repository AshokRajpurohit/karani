/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Flipkart SDE 2 online test for UAGC
 * Link: check outlook mail
 * Candidate: Ashok Rajpurohit
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FlipKart {
    private static Output out = new Output(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(LazyEscalations.getLazyEscalations(in.readInt(), in.readIntArray(in.readInt())));
            out.println(ChromosomeInsights.getLongestBalancedChromosome(in.read()));
            out.flush();
        }
    }

    final static class LazyEscalations {

        private static int getLazyEscalations(int n, int[] escalations) {
            Arrays.sort(escalations);
            ArrayUtils.reverse(escalations);
            int days = 0, count = 0;
            for (int e : escalations) {
                if (count >= n)
                    break;

                count += e;
                days++;

            }

            return count >= n ? days : -1;
        }
    }

    final static class ChromosomeInsights {
        final static int ZERO = '0', ONE = '1';

        static int getLongestBalancedChromosome(String chromosome) {
            int len = chromosome.length();
            char[] ar = chromosome.toCharArray();
            IntMap map = new IntMap(-len, len);
            int totalSoFar = 0, maxLen = 0;
            for (int i = 0; i < len; i++) {
                totalSoFar += getNormalizedValue(ar[i]);

                if (totalSoFar == 0)
                    maxLen = i + 1;
                else if (map.contains(totalSoFar))
                    maxLen = Math.max(maxLen, i - map.get(totalSoFar));

                map.put(totalSoFar, i);
            }

            return maxLen;
        }

        private static int getNormalizedValue(int ch) {
            return ch == ZERO ? -1 : 1;
        }

        final static class IntMap {
            final int[] map;
            final int offset, size;

            IntMap(int start, int end) {
                offset = -start;
                size = end + 1 - start;
                map = new int[size];
                Arrays.fill(map, -1);
            }

            private void put(int key, int val) {
                if (!contains(key))
                    map[key + offset] = val;
            }

            private int get(int key) {
                return map[key + offset];
            }

            private boolean contains(int key) {
                return map[key + offset] != -1;
            }
        }
    }
}