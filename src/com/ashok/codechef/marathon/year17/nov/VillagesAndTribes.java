/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.nov;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Villages and Tribes
 * <p>
 * There are n villages in a line in an area. There are two kinds of tribes A and B
 * that reside there. A village can be either empty or occupied by one of the tribes.
 * An empty village is said to be controlled by a tribe of village A if it is
 * surrounded by villages of tribes A from the left and from the right.
 * Same goes for the tribe B.
 * <p>
 * Find out the number of villages that are controlled by tribes A and B, respectively.
 * <p>
 * <p>
 * Link: https://www.codechef.com/NOV17/problems/VILTRIBE
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class VillagesAndTribes {
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
            out.println(process(in.read().toCharArray()));
        }
    }

    private static String process(char[] villages) {
        return process(toList(villages));
    }

    private static String process(LinkedList<VillageTribe> villageTribes) {
        int countA = 0, countB = 0;
        VillageTribe currentVillageTribe = new VillageTribe(Tribe.EMPTY, -1);

        for (VillageTribe villageTribe : villageTribes) {
            if (villageTribe.tribe == Tribe.EMPTY)
                continue;

            int diff = 1;
            if (currentVillageTribe.tribe == villageTribe.tribe)
                diff = villageTribe.index - currentVillageTribe.index;

            if (villageTribe.tribe == Tribe.A)
                countA += diff;
            else
                countB += diff;

            currentVillageTribe = villageTribe;
        }

        return countA + " " + countB;
    }

    private static LinkedList<VillageTribe> toList(char[] villages) {
        LinkedList<VillageTribe> villageTribes = new LinkedList<>();
        int len = villages.length;

        for (int i = 0; i < len; i++)
            if (villages[i] != Tribe.EMPTY.tribe)
                villageTribes.addLast(new VillageTribe(Tribe.getTribe(villages[i]), i));

        return villageTribes;
    }

    final static class VillageTribe {
        final Tribe tribe;
        final int index;

        VillageTribe(Tribe tribe, int index) {
            this.tribe = tribe;
            this.index = index;
        }
    }

    enum Tribe {
        A('A'), B('B'), EMPTY('.');

        char tribe;

        Tribe(char ch) {
            tribe = ch;
        }

        static Tribe getTribe(char ch) {
            return A.tribe == ch ? A : B.tribe == ch ? B : EMPTY;
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