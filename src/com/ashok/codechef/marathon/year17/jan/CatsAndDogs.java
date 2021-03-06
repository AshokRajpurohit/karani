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
 * Problem Name: Cats and Dogs
 * Link: https://www.codechef.com/JAN17/problems/CATSDOGS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CatsAndDogs {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String YES = "yes\n", NO = "no\n";

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
            sb.append(process(in.readInt(), in.readInt(), in.readInt()));
        }

        out.print(sb);
    }

    /**
     * Maximum legs are when all the cats walks with dogs.
     *
     * @param cats
     * @param dogs
     * @param legs
     * @return
     */
    private static String process(long cats, long dogs, long legs) {
        if ((legs & 3) != 0) // check legs are in multiple of four or not.
            return NO;

        long min = minimumLegs(cats, dogs), max = (dogs + cats) << 2;
        return legs >= min && legs <= max ? YES : NO;
    }

    /**
     * Dogs can carry 2 * {@code dogs} cats on their back, so remaining cats has to walk.
     *
     * @param cats
     * @param dogs
     * @return
     */
    private static long minimumLegs(long cats, long dogs) {
        return (dogs + Math.max(0, cats - (dogs << 1))) << 2;
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
    }
}
