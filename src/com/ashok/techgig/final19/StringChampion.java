/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.final19;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class StringChampion {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int len = in.readInt(), q = in.readInt();
        String str = in.read(len);
        SubStringProcessor stringProcessor = getSubStringProcessor(str);
        StringBuilder sb = new StringBuilder(q << 3);
        while (q > 0) {
            q--;
            sb.append(stringProcessor.calculate(in.readLong())).append('\n');
        }

        out.print(sb);
    }

    private static void play() throws IOException {
        int len = in.readInt(), q = in.readInt();
        long[] queries = null;
        String s = "";
        while (true) {
            s = new RandomStrings().nextStringabc(len);
            queries = Generators.generateRandomLongArray(q, 1L * len * len);
            SubStringProcessor processor = getSubStringProcessor(s);
            for (long query : queries)
                try {
                    processor.calculate(query);
                } catch (Exception e) {
                    out.println(s);
                    out.println(query);
                    out.flush();
                    processor = getSubStringProcessor(s);
                    processor.calculate(query);
                }
        }
    }

    private static boolean allCharSame(String str) {
        char ref = str.charAt(0);
        for (char ch : str.toCharArray()) if (ref != ch) return false;
        return true;
    }

    private static SubStringProcessor getSubStringProcessor(String str) {
        return allCharSame(str) ? SUB_STRING_PROCESSOR : new SubStringManager(str);
    }

    @FunctionalInterface
    interface SubStringProcessor {
        int calculate(long index);
    }

    final static SubStringProcessor SUB_STRING_PROCESSOR = (t) -> 1;

    final static class SubStringManager implements SubStringProcessor {
        final String str;
        final long limit;
        final long[] countSums;
        final Pair[] subStringInfo;
        final char[] chars;


        SubStringManager(final String str) {
            this.str = str;
            int len = str.length();
            limit = (1L * len * (len + 1)) >>> 1;
            countSums = new long[len];
            Arrays.fill(countSums, -1);
            subStringInfo = IntStream.range(0, len)
                    .mapToObj(i -> new Pair(i, 0))
                    .toArray(t -> new Pair[t]);

            chars = str.toCharArray();
            Arrays.sort(subStringInfo, (a, b) -> {
                int depth = getDepth(a.start, b.start);
                int compare = 0;
                if (a.start + depth < len && b.start + depth < len) {
                    compare = chars[a.start + depth] - chars[b.start + depth];
                    if (compare < 0)
                        b.offset = depth;
                    else
                        a.offset = depth;
                } else if (depth > 0) {
                    compare = b.start - a.start;
                    if (a.start < b.start) a.offset = depth;
                }

                return compare;
            });

            IntStream.range(1, len).forEach(i -> subStringInfo[i].offset = getDepth(subStringInfo[i - 1].start, subStringInfo[i].start));

            IntStream.range(0, len).forEach(i -> countSums[i] = len - subStringInfo[i].start - subStringInfo[i].offset);
            IntStream.range(1, len).forEach(i -> countSums[i] += countSums[i - 1]);
        }

        private int getDepth(int i, int j) {
            int depth = 0;
            while (i < chars.length && j < chars.length && chars[i] == chars[j]) {
                i++;
                j++;
                depth++;
            }

            return depth;
        }

        public int calculate(long index) {
            if (index > countSums[str.length() - 1]) return -1;
            int pos = Arrays.binarySearch(countSums, index);
            if (pos >= 0) {
                return countUnique(subStringInfo[pos].start, chars.length);
            }

            pos = -(pos + 1);
            if (pos == 0) return countUnique(subStringInfo[pos].start + subStringInfo[pos].offset, pos + (int) index);
            return countUnique(subStringInfo[pos].start, subStringInfo[pos].offset + subStringInfo[pos].start + (int) (index - countSums[pos - 1]));
        }

        private int countUnique(int index, int end) {
            boolean[] map = new boolean[256];
            for (int i = index; i < end; i++)
                map[chars[i]] = true;

            return (int) IntStream.range('a', 'z' + 1).filter(i -> map[i]).count();
        }
    }

    final static class Pair {
        int start, offset;

        Pair(final int a, final int b) {
            this.start = a;
            this.offset = b;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
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