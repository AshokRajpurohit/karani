/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.skillenza.moveinsync;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Obstacles for the Queen
 * Link: https://skillenza.com/challenge/moveinsync-recruitmentdrive-java-backend-mar/checkpoint/submit/1211
 * <p>
 * A queen in the game of chess is the most powerful piece as it can move horizontally, vertically and diagonally
 * with no limit on the number of squares. You are given an _nXn_ chess board and a position where
 * the queen is placed as (r,c) where r represents the row number of the square and c represents the column number
 * of the square. Rows are numbers from 1 to n starting from bottom and columns are numbered from 1 to n starting
 * from left. So the bottom left square is (1,1). Find the number of places where an obstacle can be placed such
 * that the number of moves of the queen remain the same as before.
 * <p>
 * For full implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class QueenObstacles {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            String input = in.readLine();
            input = input.substring(1, input.length() - 1);
            String[] inputs = input.split(",");
            out.println(calculate(n, Integer.valueOf(inputs[0]), Integer.valueOf(inputs[1])));
        }
    }

    private static int calculate(int n, int r, int c) {
        int dr = n - r, dc = n - c;
        int straightMoves = (n << 1) - 2; // horizontal and vertical moves
        int diagonal1Moves = Math.min(r - 1, dc) + Math.min(dr, c - 1);
        int diagonal2Moves = Math.min(r - 1, c - 1) + Math.min(dr, dc);
        return n * n - straightMoves - diagonal1Moves - diagonal2Moves;
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

        public String readLine() throws IOException {
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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