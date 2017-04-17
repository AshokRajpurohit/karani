/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Bear and Row 01
 * Link: https://www.codechef.com/APRIL17/problems/ROWSOLD
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BearAndRow01 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int EMPTY = '0';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            out.println(process(in.read().toCharArray()));
        }
    }

    /**
     * let's say for any soldier at index i, row[i], the final position is fi, so the
     * distance traveled by this soldier is fi - i.
     * Additionally time can be spent by selecting this soldier multiple times.
     * <p>
     * How do we maximize the selection count?
     * Let's say the next soldier is at position j. Then selection count for i is
     * S(i) = 1 + S(j), if there is a gap between i and j, i.e. j - i >= 2 or
     * S(i) = S(j), if i and j are adjacent.
     * <p>
     * We will always select ith soldier to move if there is space available to move.
     * If there is no space, select jth soldier, and make space.
     * <p>
     * Shifting all the 1's to right side is same as shifting all the 1's to left in
     * reversed array. It will make the processing a bit easier.
     *
     * @param row, cell array.
     * @return number of seconds Bear can play the game.
     */
    private static long process(char[] row) {
        reverse(row);
        if (noMovement(row))
            return 0;

        long time = getDistanceSum(row);
        time += getSelectionSum(row);

        return time;
    }

    /**
     * Movement can only be possible if there is a 0 before any 1.
     *
     * @param row
     * @return true if movement of any one soldier is possible.
     */
    private static boolean noMovement(char[] row) {
        for (int i = 1; i < row.length; i++)
            if (row[i] != EMPTY && row[i - 1] == EMPTY)
                return false;

        return true;
    }

    /**
     * Calculates the sum of moves for each soldier before reaching final position.
     * It is same as number of zeroes before it.
     * As this array is reversed, so all 1's will be in left and 0's on right side.
     *
     * @param row
     * @return
     */
    private static long getDistanceSum(char[] row) {
        long totalDistance = 0, distance = 0;
        int index = 0;

        while (index < row.length) {
            if (row[index] == EMPTY)
                distance++;
            else
                totalDistance += distance;

            index++;
        }

        return totalDistance;
    }

    /**
     * Calculates selection counts for each soldier and returns the sum of the same.
     * <p>
     * For a soldier at index i, the selection count can be expressed as:
     * <p>
     * S(i) = S(j) + 1, if i - j > 1, i.e. at least one empty cell is there between i and j.
     * S(i) = S(j), if i = j + 1.
     * here j is the previous soldier's index.
     *
     * @param row, soldier row as explained in question but reversed.
     * @return sum of selection counts for soldiers.
     */
    private static long getSelectionSum(char[] row) {
        long selectionSum = 0, selection = 0;

        for (int i = 1; i < row.length; i++) {
            if (row[i] == EMPTY)
                continue;

            if (row[i - 1] == EMPTY)
                selection++;

            selectionSum += selection;
        }

        return selectionSum;
    }

    private static void reverse(char[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            char temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
