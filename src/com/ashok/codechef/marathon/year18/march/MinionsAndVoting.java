/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.march;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Minions and Voting
 * Link: https://www.codechef.com/MARCH18A/problems/MINVOTE
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MinionsAndVoting {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            int minions = in.readInt();
            int[] influences = in.readIntArray(minions);
            int[] minionVotes = process(influences);
            append(sb, minionVotes);
        }

        out.print(sb);
    }

    /**
     * First we will store the cumulative add ons in vote array, finally add all
     * the values till index <b>i</b> to get the total vote counts for <b>i</b>'th minion.
     *
     * @param minionInfluences
     * @return
     */
    private static int[] process(int[] minionInfluences) {
        int minions = minionInfluences.length;
        int[] voteSequence = new int[minions], minionVotes = new int[minions];
        long[] sums = sumArray(minionInfluences);
        for (int i = 0; i < minions; i++) {
            if (i != 0) {
                int leftIndex = findElementLeft(sums, sums[i - 1] - minionInfluences[i], i);
                voteSequence[leftIndex]++;
                voteSequence[i]--;
            }

            if (i == minions - 1) continue;
            voteSequence[i + 1]++;
            int rightIndex = findElementRight(sums, sums[i] + minionInfluences[i], i);
            if (rightIndex < minions) voteSequence[rightIndex]--;
        }

        int votes = 0;
        for (int i = 0; i < minions; i++) {
            votes += voteSequence[i]; // same array could be used but for simplicity
            minionVotes[i] = votes; // let's keep segregated.
        }

        return minionVotes;
    }

    private static int findElementLeft(long[] ar, long value, int index) {
        int v = Arrays.binarySearch(ar, 0, index, value);
        return v < 0 ? -(v + 1) : v;
    }

    private static int findElementRight(long[] ar, long value, int index) {
        int v = Arrays.binarySearch(ar, index + 1, ar.length, value);
        return v < 0 ? -v : v + 2;
    }

    private static long[] sumArray(int[] ar) {
        int len = ar.length, index = 0;
        long[] sums = new long[len];
        long val = 0;
        for (int e : ar) {
            val += e;
            sums[index++] = val;
        }

        return sums;
    }

    private static void append(StringBuilder sb, int[] ar) {
        for (int e : ar)
            sb.append(e).append(' ');

        sb.append('\n');
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