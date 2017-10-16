/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.oct;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and a great voluntary Program
 * Link: https://www.codechef.com/OCT17/problems/CHEFGP
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndVoluntaryProgram {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char APPLE = 'a', BANANA = 'b', KIWI = '*';
    private static final char XOR = APPLE ^ BANANA;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.read(), in.readInt(), in.readInt()));
        }
    }

    private static String process(String fruits, int appleGroupSize, int bananaGroupSize) {
        int appleCount = getCount(fruits, APPLE), bananaCount = fruits.length() - appleCount;
        Basket basket = new Basket(appleCount, bananaCount, appleGroupSize, bananaGroupSize);
        return process(basket);
    }

    private static String process(Basket basket) {
        StringBuilder sb = new StringBuilder(basket.ca + basket.cb + Math.min(basket.ca, basket.cb));
        if (basket.possible())
            makePossible(sb, basket);
        else
            useKiwis(sb, basket);

        return sb.toString();
    }

    private static void makePossible(StringBuilder sb, Basket basket) {
        int ka = basket.ca / (basket.cb + 1), rem = basket.ca - ka * (basket.cb + 1);
        for (int i = 0; i < rem; i++) {
            append(sb, basket.cha, ka + 1);
            sb.append(basket.chb);
        }

        for (int i = rem; i < basket.cb; i++) {
            append(sb, basket.cha, ka);
            sb.append(basket.chb);
        }

        append(sb, basket.cha, ka);
    }

    private static void useKiwis(StringBuilder sb, Basket basket) {
        int ka = basket.ga, rem = basket.ca - ka * basket.cb;
        for (int i = 0; i < basket.cb; i++) {
            append(sb, basket.cha, ka);
            sb.append(basket.chb);
        }

        while (true) {
            append(sb, basket.cha, Math.min(rem, basket.ga));
            rem -= basket.ga;
            if (rem <= 0)
                return;

            sb.append(KIWI);
        }
    }

    private static void appendWithKiwis(StringBuilder sb, int count, int size, char ch) {
        append(sb, ch, Math.min(count, size));
        count -= size;
        while (count > 0) {
            sb.append(KIWI);
            append(sb, ch, Math.min(count, size));
            count -= size;
        }
    }

    private static int getCount(String s, char ch) {
        int len = s.length(), count = 0;
        for (int i = 0; i < len; i++)
            if (s.charAt(i) == ch)
                count++;

        return count;
    }

    private static void append(StringBuilder sb, char ch, int count) {
        while (count > 0) {
            count--;
            sb.append(ch);
        }
    }

    final static class Basket {
        final int ca, cb; // counts for a and b
        final int ga, gb; // group size for a and b.
        final char cha, chb; // characters for a and b.

        Basket(int ca, int cb, int ga, int gb) {
            if (ca >= cb) {
                this.ca = ca;
                this.cb = cb;
                this.cha = APPLE;
                this.chb = BANANA;
                this.ga = ga;
                this.gb = gb;
            } else {
                this.ca = cb;
                this.cb = ca;
                this.cha = BANANA;
                this.chb = APPLE;
                this.ga = gb;
                this.gb = ga;
            }
        }

        private boolean possible() {
            return 1L * ga * (cb + 1) >= ca;
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