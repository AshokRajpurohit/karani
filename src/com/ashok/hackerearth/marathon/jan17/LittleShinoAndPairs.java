/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Little Shino and Pairs
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-17/algorithm/little-shino-and-pairs/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 * <p>
 * We are traversing the array for max element and then we count number of different elements smaller than the current
 * element but larger than all other elements in subarray. Let's say we are at index i (max element) and for any
 * index j, the element ar[j] is second largest if and only if ar[j] < ar[i] and ar[j] > ar[k] for k in [i, j].
 * j can be either to left side (< i) or right side (> i).
 * <p>
 * Let's consider for right side only for simplicity.
 * j can start from i + 1 till ar[j] < ar[i] as after any value of j, ar[i] is not going to be largest element.
 * <p>
 * Now moving to j, if ar[j + 1] < ar[j], there is no point of considering j + 1 for j as this element can't be
 * second largest (as two larger elements already exists).
 * <p>
 * So we will move to next higher element ar[k], ar[k] > ar[j] and ar[k] < ar[i].
 * <p>
 * Similarly for left side, we can count second largest elements for ar[i].
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LittleShinoAndPairs {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        out.println(process(ar));
    }

    /**
     * Generates test data randomly and prints output.
     *
     * @throws IOException
     */
    private static void play() throws IOException {
        Output sysOut = new Output();
        while (true) {
            int n = in.readInt();
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = i + 1;

            ArrayUtils.randomizeArray(ar);
            sysOut.print(ar);
            sysOut.println(process(ar));
            sysOut.flush();
        }
    }

    private static long process(int[] ar) {
        int[] indexMap = getIndexMap(ar);
        int[] right = nextHigher(ar), left = previousHigher(ar);

        long count = 0;
        for (int num = ar.length; num > 0; num--) {
            int index = indexMap[num];
            int next = index + 1;

            while (next < ar.length && ar[next] < num) {
                count++;
                next = right[next];
            }

            int prev = index - 1;
            while (prev >= 0 && ar[prev] < num) {
                count++;
                prev = left[prev];
            }
        }

        return count;
    }

    private static int[] getIndexMap(int[] ar) {
        int[] map = new int[ar.length + 1];
        map[0] = -1;

        for (int i = 0; i < ar.length; i++)
            map[ar[i]] = i;

        return map;
    }

    /**
     * Returns array of next higher element index.
     * At index i the array contains the index of next higher element.
     * If there is no higher element right to it, than the value is array size.
     * For actual implementation, see {@link com.ashok.lang.utils.Utils}
     *
     * @param ar
     * @return
     */
    private static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    /**
     * For actual implementation see {@link com.ashok.lang.utils.Utils}
     *
     * @param ar
     * @return
     */
    public static int[] previousHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
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
