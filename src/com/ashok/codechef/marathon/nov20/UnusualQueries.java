/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.nov20;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Problem Name: Unusual Queries
 * Link: https://www.codechef.com/NOV20A/problems/UNSQUERS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class UnusualQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt(), s = in.readInt();
        int[] heights = in.readIntArray(n);
        SparseTable table = new SparseTable(heights);
        int last = 0;
        StringBuilder sb = new StringBuilder(q << 3);
        int[] br = nextEffectiveSmaller(heights);
        int[] cr = nextHigher(heights);
        while (q > 0) {
            q--;
            int x = in.readInt(), y = in.readInt();
            int tl = x + last * s - 1;
            int tr = y + last * s - 1;
            tl = tl >= n ? tl - n : tl;
            tr = tr >= n ? tr - n : tr;

            if (tl > tr) {
                int temp = tl;
                tl = tr;
                tr = temp;
            }

            int res = 1;
            if (tl != tr) {
                while (tl + res <= tr) {
                    res = Math.max(res, table.query(tl, tr));
//                    tl = tl == br[tl] ? br[tl + 1] : br[tl];
                    if (tl < n) tl = cr[tl];
                    if (tl < n) tl = br[tl];
                }
            }

            last = res;

            sb.append(last).append('\n');
        }

        out.println(sb);
    }

    private static int query(SparseTable table, int left, int right) {
        if (left > right) return query(table, right, left);
        if (left == right) return 1;
        if (left + 1 == right) {
            return table.ar[left] < table.ar[right] ? 2 : 1;
        }

        int res = 1;
        while (left + res <= right) {
            res = Math.max(res, table.query(left, right));
            left++;
        }

        return res;
    }

    private static void test() throws IOException {
        Random random = new Random();
        while (true) {
            out.println("Enter n, q, s, and max value");
            out.flush();
            int n = in.readInt(), q = in.readInt(), s = in.readInt(), max = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, 1, max);
            int[] br = nextEffectiveSmaller(ar);
            int last = 0;
            SparseTable table = null;
            try {
                table = new SparseTable(ar);
            } catch (Exception e) {
                table = new SparseTable(ar);
            }

            long time = System.currentTimeMillis();
            while (q > 0) {
                q--;
                int x = 1 + random.nextInt(n), y = 1 + random.nextInt(n);
                int tl = x + last * s - 1;
                int tr = y + last * s - 1;
                tl = tl >= n ? tl - n : tl;
                tr = tr >= n ? tr - n : tr;

                if (tl > tr) {
                    int temp = tl;
                    tl = tr;
                    tr = temp;
                }

                int res = 1;
                if (tl != tr) {
                    while (tl + res <= tr) {
                        res = Math.max(res, table.query(tl, tr));
                        tl = tl == br[tl] ? br[tl + 1] : br[tl];
                    }
                }

                last = res;
            }
            time = System.currentTimeMillis() - time;
            out.println("total time taken: " + time + " ms");
            /*for (int left = 0; left < n; left++) {
                for (int right = left; right < n; right++) {
                    try {
                        last = table.query(left, right);
                    } catch (Exception e) {
                        e.printStackTrace();
                        last = table.query(left, right);
                    }
                }
            }*/
        }
    }

    final static class SparseTable {
        private int[][] map;
        private final int[] ar;

        SparseTable(int[] ar) {
            this.ar = ar;
            int[] br = nextHigher(ar);
            format(br);
        }

        public int query(int left, int right) {
            if (left > right) return query(right, left);
            int value = 1;
            int half = Integer.highestOneBit(right + 1 - left);
            while (left < right) {
                while (half > 0 && (left >= map[half].length || map[half][left] > right)) {
                    half >>>= 1;
                }
                if (half == 0) return value;
                value += half;
                left = map[half][left];
            }

            return value;
        }

        private void format(int[] ar) {
            int n = ar.length;
            map = new int[n + 1][];
            map[1] = ar;
            int bit = 2;
            while (bit < map.length) {
                int half = bit >>> 1;
                map[bit] = new int[n - bit + 1];
                Arrays.fill(map[bit], n);
                for (int i = 0; i <= n - bit; i++) {
                    int id = map[half][i];
                    id = id < map[half].length ? map[half][id] : n;
                    map[bit][i] = id;
                }
                bit <<= 1;
            }
        }
    }

    /**
     * Returns array of next higher element index.
     * At index i the array contains the index of next higher element.
     * If there is no higher element right to it, than the value is array size.
     *
     * @param ar
     * @return
     */
    private static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static int[] nextEffectiveSmaller(int[] ar) {
        int n = ar.length;
        int[] res = new int[n];
        res[n - 1] = n;

        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;

            if (j < n - 1 && ar[i] > ar[j] && ar[j] < ar[j + 1]) {
                res[i] = j;
                continue;
            }

            res[i] = res[j];
        }

        return res;
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