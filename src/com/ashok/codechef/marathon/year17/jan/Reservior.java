/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Reservior
 * Link: https://www.codechef.com/JAN17/problems/RESERVOI
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Reservior {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String YES = "yes\n", NO = "no\n";
    private static final int WATER = 'W', AIR = 'A', BRICK = 'B';
    private static boolean[] map = new boolean[256];
    private static final Block BOTTOM_AIR_BLOCK = new Block(1, AIR); // this block represents everything outside reservoir

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
            int n = in.readInt(), w = in.readInt();
            String[] reservoir = new String[n];

            for (int i = 0; i < n; i++)
                reservoir[i] = in.read(w);

            sb.append(validateReservoir(reservoir, n, w) ? YES : NO);
        }

        out.print(sb);
    }

    private static boolean validateReservoir(String[] reservoir, int height, int width) {
        if (!validateColumns(reservoir, height, width))
            return false;

        Block[] topBlocks = getTopBlocks(reservoir, width);
        int brickHeight = 0, blockIndex = 0;
        Block left = BOTTOM_AIR_BLOCK, right = BOTTOM_AIR_BLOCK;

        while (blockIndex < width) {
            Block current = topBlocks[blockIndex];
            if (current.type != WATER) {
                left = current;
                blockIndex++;
                continue;
            }

            if (blockIndex + 1 < width)
                right = topBlocks[blockIndex + 1];
            else
                right = BOTTOM_AIR_BLOCK;

            if (overflow(left, current) || overflow(right, current))
                return false;

            blockIndex++;
            left = current;
        }

        return true;
    }

    private static boolean overflow(Block boundry, Block current) {
        if (current.type != WATER) // only water makes any block unstable. air and bricks are always stable.
            return false;

        if (boundry.type == BRICK) // for overflow, brick should be at lower height.
            return boundry.height < current.height;

        if (boundry.type == WATER) // water in two blocks at same heights don't overflow.
            return boundry.height != current.height;

        return true;
    }

    private static Block[] getTopBlocks(String[] reservoir, int width) {
        Block[] topBlocks = new Block[width];

        for (int col = 0; col < width; col++)
            topBlocks[col] = getTopBlockForColumn(reservoir, col);

        return topBlocks;
    }

    /**
     * Returns the top block (Non Air) for the specified column in reservoir, If no such block exists, it returns
     * a default air block. For air block, height doesn't matter.
     *
     * @param reservoir
     * @param column
     * @return
     */
    private static Block getTopBlockForColumn(String[] reservoir, int column) {
        for (int row = 0; row < reservoir.length; row++)
            if (reservoir[row].charAt(column) != AIR)
                return new Block(reservoir.length - row, reservoir[row].charAt(column));

        return BOTTOM_AIR_BLOCK;
    }

    private static boolean validateColumns(String[] reservoir, int height, int width) {
        for (int i = 0; i < width; i++)
            if (!validateColumn(reservoir, i))
                return false;

        return true;
    }

    /**
     * Validates block in specific reservoir column. in Top-Down manner, any heavier material (Brick > Water > Air) over
     * lighter one makes block unstable.
     * A boolean map is used to check which blocks are present above the current block.
     *
     * @param reservoir
     * @param column
     * @return
     */
    private static boolean validateColumn(String[] reservoir, int column) {
        clearMap();

        for (int row = 0; row < reservoir.length; row++) {
            int block = reservoir[row].charAt(column);
            if (!validateBlock(block))
                return false;

            map[block] = true;
        }

        return true;
    }

    /**
     * Validates for specific block if it is stable or not.
     * If any heavier block above to specific block exists, the current block is unstable.
     *
     * @param block
     * @return
     */
    private static boolean validateBlock(int block) {
        if (block == BRICK)
            return true;

        if (block == WATER)
            return !map[BRICK]; // water can't be below a brick.

        return !(map[BRICK] || map[WATER]); // air can't be below to water or brick.
    }

    private static void clearMap() {
        map[WATER] = false;
        map[AIR] = false;
        map[BRICK] = false;
    }

    final static class Block {
        final int height, type;

        Block(int h, int t) {
            height = h;
            type = t;
        }

        public String toString() {
            return height + " : " + type;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
