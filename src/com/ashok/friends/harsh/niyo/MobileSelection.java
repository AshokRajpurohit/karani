/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh.niyo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Mobile Selection
 * Link: HackerEarth | NiYO Java Developer Hiring Challenge
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MobileSelection {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int WINDOWS = 0, ANDROID = 1, IOS = 2;
    private static final int RAM_2 = 0, RAM_4 = 1, RAM_8 = 2;
    private static final int MEMORY_32 = 0, MEMORY_64 = 1;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        Shop mobileShop = new Shop();
        for (int i = 0; i < n; i++) {
            int os = getOs(in.read()), ram = getRam(in.readInt()), memory = getMemory(in.readInt()),
                    price = in.readInt(), rating = in.readInt();

            mobileShop.addMobile(new Mobile(os, ram, memory, price, rating));
        }

        mobileShop.process();
        int q = in.readInt();
        while (q > 0) {
            q--;
            int os = getOs(in.read()), ram = getRam(in.readInt()),
                    memory = getMemory(in.readInt()), price = in.readInt();

            out.println(mobileShop.search(os, ram, memory, price));
        }
    }

    private static int getOs(String os) {
        char c = os.charAt(0);
        switch (c) {
            case 'w':
                return WINDOWS;
            case 'a':
                return ANDROID;
            default:
                return IOS;
        }
    }

    private static int getRam(int ram) {
        return ram == 2 ? RAM_2 : ram == 4 ? RAM_4 : RAM_8;
    }

    private static int getMemory(int memory) {
        return memory == 32 ? MEMORY_32 : MEMORY_64;
    }

    final static class Shop {
        MobileBucket[][][] buckets = new MobileBucket[3][3][2]; // os, ram, memory

        Shop() {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 2; k++)
                        buckets[i][j][k] = new MobileBucket();
        }

        private void process() {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 2; k++)
                        buckets[i][j][k].process();
        }

        void addMobile(Mobile mobile) {
            buckets[mobile.os][mobile.ram][mobile.memory].add(mobile);
        }

        int search(int os, int ram, int memory, int budget) {
            return buckets[os][ram][memory].search(budget);
        }
    }

    final static class Mobile implements Comparable<Mobile> {
        final int rating, price, os, ram, memory;

        Mobile(int os, int ram, int memory, int price, int rating) {
            this.price = price;
            this.rating = rating;
            this.os = os;
            this.ram = ram;
            this.memory = memory;
        }

        @Override
        public int compareTo(Mobile mobile) {
            if (price == mobile.price)
                return mobile.rating - rating;

            return price - mobile.price;
        }
    }

    final static class MobileBucket {
        final List<Mobile> list = new LinkedList<>();
        private Mobile[] mobiles, bestMobiles;

        private void add(Mobile mobile) {
            list.add(mobile);
        }

        private void process() {
            if (list.isEmpty())
                return;

            mobiles = new Mobile[list.size()];
            int index = 0;
            for (Mobile mobile : list)
                mobiles[index++] = mobile;

            Arrays.sort(mobiles);
            bestMobiles = new Mobile[mobiles.length];
            Mobile best = mobiles[0];
            bestMobiles[0] = best;

            for (int i = 1; i < mobiles.length; i++) {
                if (mobiles[i].rating > best.rating)
                    best = mobiles[i];

                bestMobiles[i] = best;
            }
        }

        private int search(int price) {
            if (list.isEmpty() || bestMobiles[0].price > price)
                return -1;

            int low = 0;
            int high = list.size() - 1;

            while (low <= high) {
                int mid = (low + high) >>> 1;
                Mobile mobile = bestMobiles[mid];
                int midVal = mobile.price;

                if (midVal < price)
                    low = mid + 1;
                else if (midVal > price)
                    high = mid - 1;
                else
                    return mobile.rating; // key found
            }

            return bestMobiles[low - 1].rating;
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