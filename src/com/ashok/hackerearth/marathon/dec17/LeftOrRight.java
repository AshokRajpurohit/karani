/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.dec17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Left or Right
 * Link: https://www.hackerearth.com/challenge/competitive/december-circuits-17/algorithm/left-or-right-92c0b54c/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LeftOrRight {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int RIGHT = 'R' - '0';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] types = in.readIntArray(n);
        Country country = new Country(types);
        StringBuilder sb = new StringBuilder(q << 2);

        while (q > 0) {
            q--;
            int index = in.readInt(), type = in.readInt(), direction = in.readInt();
            sb.append(country.query(index, type, direction == RIGHT ? 1 : -1)).append('\n');
        }

        out.print(sb);
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;
        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    private static int binarySearch(int[] ar, int value) {
        if (ar.length < 10)
            return iterativeSearch(ar, value);

        return Arrays.binarySearch(ar, value);
    }

    private static int iterativeSearch(int[] ar, int value) {
        for (int i = 0; i < ar.length; i++)
            if (ar[i] > value)
                return -i - 1;

        return -ar.length - 1;
    }

    final static class Country {
        final int[] types;
        final int[][] map;
        final int maxType;

        Country(int[] types) {
            this.types = types;
            maxType = max(types);
            map = new int[maxType + 1][];
            populate();
        }

        private int query(int index, int type, int move) {
            if (type > maxType || map[type] == null)
                return -1;

            if (types[index] == type)
                return 0;

            int[] ar = map[type];
            int nextIndex = ar.length == 1 ? 0 : -binarySearch(ar, index) - 1;
            if (move == -1)
                nextIndex--;

            if (nextIndex < 0)
                nextIndex += ar.length;

            if (nextIndex == ar.length)
                nextIndex = 0;

            int next = ar[nextIndex];
            if (move == -1) {
                return next < index ? index - next : types.length - next + index;
            }

            return next > index ? next - index : types.length + next - index;
        }

        private void populate() {
            LinkedList<Integer>[] mapList = new LinkedList[maxType + 1];
            int len = types.length;
            for (int i = 0; i < len; i++) {
                ensureInitialized(mapList, types[i]);
                mapList[types[i]].addLast(i);
            }

            for (int i = 1; i <= maxType; i++)
                if (mapList[i] != null)
                    map[i] = toArray(mapList[i]);
        }

        private void ensureInitialized(LinkedList<Integer>[] listMap, int index) {
            if (listMap[index] != null)
                return;

            listMap[index] = new LinkedList<>();
        }
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