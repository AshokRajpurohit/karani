/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: String Merging
 * Link: https://www.codechef.com/JAN18/problems/STRMRG
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class StringMerging {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

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
            int n = in.readInt(), m = in.readInt();
            sb.append(process(in.read(n), in.read(m))).append('\n');
        }
        out.print(sb);
    }

    private static int process(String a, String b) {
        Merge merge = new Merge(a.toCharArray(), b.toCharArray());
        return merge.process();
    }

    final static class Merge {
        final int[][] map1, map2;
        final int[] sum1, sum2;
        final char[] ar, br;
        final int alen, blen;
        static final int INVALID_VALUE = Integer.MAX_VALUE >>> 1;

        Merge(char[] ar, char[] br) {
            this.ar = ar;
            this.br = br;
            alen = ar.length;
            blen = br.length;
            map1 = new int[alen][blen];
            map2 = new int[blen][alen];
            sum1 = new int[alen];
            sum2 = new int[blen];
        }

        private int process() {
            populate(sum1, ar);
            populate(sum2, br);
            int count = ar[alen - 1] == br[blen - 1] ? 1 : 2;
            map1[alen - 1][blen - 1] = map2[blen - 1][alen - 1] = count;
            process(0, 0);
            return select(getValue(map1, 0, 0), getValue(map2, 0, 0));
        }

        private static void populate(int[] sum, char[] ar) {
            int len = ar.length;
            sum[len - 1] = 1;

            len -= 2;
            while (len >= 0) {
                sum[len] = sum[len + 1];
                if (ar[len] != ar[len + 1]) sum[len]++;
                len--;
            }
        }

        private void process(int ai, int bi) {
            if (ai >= alen || bi >= blen || map1[ai][bi] != 0) return;
            if (ar[ai] == br[bi]) {
                processSame(ai, bi);
                return;
            }

            process(ai, bi + 1);
            process(ai + 1, bi);
            int v1 = getValue1(ai + 1, bi), v2 = getValue2(ai + 1, bi);

            if (ai + 1 < alen && ar[ai] != ar[ai + 1]) v1++;
            if (ar[ai] != br[bi]) v2++;

            map1[ai][bi] = select(v1, v2);

            v1 = getValue1(ai, bi + 1);
            v2 = getValue2(ai, bi + 1);

            if (bi + 1 < blen && br[bi] != br[bi + 1]) v2++;
            if (br[bi] != ar[ai]) v1++;

            map2[ai][bi] = select(v1, v2);
        }

        private void processSame(int ai, int bi) {
            process(ai + 1, bi + 1);
            int v1 = getValue1(ai + 1, bi + 1), v2 = getValue2(ai + 1, bi + 1);

            if (ai + 1 < alen && ar[ai] != ar[ai + 1]) v1++;
            if (bi + 1 < blen && br[bi] != br[bi + 1]) v2++;

            int value = select(v1, v2);
            map1[ai][bi] = map2[ai][bi] = value;
        }

        private void putValue(int[][] map, int ai, int bi, int value) {
            if (ai >= alen || bi >= blen)
                return;

            map[ai][bi] = value;
        }

        private int getValue(int[][] map, int ai, int bi) {
            return ai < alen && bi < blen ? map[ai][bi] : ai < alen ? 1 : INVALID_VALUE;
        }

        private int getValue1(int ai, int bi) {
            return ai >= alen ? INVALID_VALUE : bi >= blen ? sum1[ai] : map1[ai][bi];
        }

        private int getValue2(int ai, int bi) {
            return bi >= blen ? INVALID_VALUE : ai >= alen ? sum2[bi] : map2[ai][bi];
        }

        private int select(int v1, int v2) {
            return Math.min(v1, v2);
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