/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.oct20;

import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;
import com.ashok.lang.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Problem Name: Adding Squares
 * Link: https://www.codechef.com/OCT20A/problems/ADDSQURE
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class AddingSquares {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        testPairFunction();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int w = in.readInt(), h = in.readInt(), n = in.readInt(), m = in.readInt();
        int[] verticalLines = in.readIntArray(n), horizontalLines = in.readIntArray(m);
        Arrays.sort(verticalLines);
        Arrays.sort(horizontalLines);
        out.println(process1(w, h, verticalLines, horizontalLines));
    }

    private static long process(int w, int h, int[] verticals, int[] horizontals) {
        if (h > 1000 || w > 1000) return -1;
        final int[] widths = getAllPairwiseDiffs(verticals, w), heights = getAllPairwiseDiffs(horizontals, h);
        int[] uniqueWidths = IntStream.range(1, w + 1).filter(i -> widths[i] != 0).toArray();

        long count = calculate(widths, heights);
        boolean[] horizontal_mapping = new boolean[h + 1];
        int[] lineWidths = new int[w + 1];
        for (int e : horizontals) horizontal_mapping[e] = true;

        IntConsumer function = pos -> {
            Arrays.fill(lineWidths, 0);
            for (int e : horizontals) {
                lineWidths[Math.abs(e - pos)] = 1;
            }
        };

        long maxSquares = 0;
        for (int i = 0; i <= h; i++) {
            if (!horizontal_mapping[i] && (heights[i] == 0)) {
                function.accept(i);
                maxSquares = Math.max(maxSquares, calculate(widths, lineWidths));
            }
        }

        return count + maxSquares;
    }

    private static long process1(int w, int h, int[] verticals, int[] horizons) {
        final int[] widths = getAllPairwiseDiffs(verticals, w), heights = getAllPairwiseDiffs(horizons, h);
        long count = calculate(widths, heights);
        boolean[] horizontal_mapping = new boolean[h + 1];
        for (int e : horizons) horizontal_mapping[e] = true;

        long max = 0;
        int[] line_diffs = new int[h + 1];
        for (int i = 0; i <= h; i++) {
            if (horizontal_mapping[i]) continue;
            long temp = 0;
            for (int e : horizons) {
                int diff = Math.abs(e - i);
                if (heights[diff] == 0) line_diffs[diff] = 1;
            }

            temp = calculate(line_diffs, widths);
            max = Math.max(max, temp);
            Arrays.fill(line_diffs, 0);
        }

        return count + max;
    }

    private static long calculate(int[] ar, int[] br) {
        long res = 0;
        int min = Math.min(ar.length, br.length);
        for (int i = 1; i < min; i++) res += ar[i] * br[i];
        return res;
    }

    private static void testPairFunction() throws IOException {
        int size = in.readInt(), max = in.readInt();
        while (true) {
            int[] values = Utils.getIndexArray(max);
            ArrayUtils.randomizeArray(values);
            values = Arrays.stream(values).limit(size).sorted().toArray();
            long time = System.currentTimeMillis();
            int[] first = getAllPairwiseDiffs(values, max);
            time = System.currentTimeMillis() - time;
            out.println("first time taken: " + time);
            time = System.currentTimeMillis();
            int[] second = getAllPairwiseDiffs1(values, max);
            time = System.currentTimeMillis() - time;
            out.println("second time taken: " + time);
            out.flush();
            boolean match = Arrays.equals(first, second);
            if (!match) {
                second = getAllPairwiseDiffs1(values, max);
            }
        }
    }

    private static int[] getAllPairwiseDiffs(int[] values, int max) {
        if (values.length > 1000) return getAllPairwiseDiffs1(values, max);
        int[] mapping = new int[max + 1];
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                mapping[values[j] - values[i]] = 1;
            }
        }
        return mapping;
    }

    // TODO: Test this method against getAllPairwiseDiffs
    private static int[] getAllPairwiseDiffs1(int[] values, int max) {
        int[] mapping = new int[max + 1];
        int minDiff = IntStream.range(1, values.length).map(i -> values[i] - values[i - 1]).min().getAsInt();
        int maxDiff = values[values.length - 1] - values[0];
        int start = 0, end = 1, diff = minDiff;
        boolean found = false;
        while (diff <= maxDiff) {
            int a = values[start], b = values[end];

            mapping[b - a] = 1;
            if (b > a + diff) {
                start++;
                if (start == end) end++;
            } else {
                end++;
            }

            found = found || (mapping[diff] == 1);

            if (end == values.length) {
                if (!found) diff++;
                found = false;
                start = 0;
                end = 1;
            }

            if (mapping[diff] == 1) {
                diff++;
            }
        }
        return mapping;
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