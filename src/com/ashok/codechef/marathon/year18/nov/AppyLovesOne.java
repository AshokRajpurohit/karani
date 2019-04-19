/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.nov;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Problem Name: Appy Loves One
 * Link: https://www.codechef.com/NOV18A/problems/HMAPPY1
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AppyLovesOne {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt(), k = in.readInt();
        int[] ar = in.readIntArray(n);
        ArrayProcessor processor = new ArrayProcessor(ar, k);
        char[] queries = in.read(q).toCharArray();
        StringBuilder sb = new StringBuilder(q << 1);
        for (char ch : queries) {
            if (ch == '?')
                sb.append(processor.query()).append('\n');
            else
                processor.rightShift();
        }

        out.print(sb);
    }

    private static int count(int[] ar, int value, int from, int to) {
        int count = 0;
        for (int i = from; i <= to; i++) if (ar[i] == value) count++;
        return count;
    }

    final static class ArrayProcessor {
        final int[] ar, pr;
        final int k, size;
        private int shift;

        ArrayProcessor(int[] ar, int k) {
            this.ar = ar;
            this.k = k;
            size = ar.length;
            pr = new int[size];
            process();
        }

        public int query() {
            return Math.min(k, pr[shift]);
        }

        private void process() {
            PriorityQueue<Pair> queue = new PriorityQueue<>((a, b) -> a.length == b.length ? b.index - a.index : b.length - a.length);
            queue.offer(new Pair(0, 0));
            int[] tar = new int[(size << 1) - 1];
            System.arraycopy(ar, 1, tar, 0, size - 1);
            System.arraycopy(ar, 0, tar, size - 1, size);
            int len = 0, index = 0;
            for (int i = tar.length - 1; i >= size - 1; i--) {
                if (tar[i] == 0) continue;
                len = 0;
                while (i >= size - 1 && tar[i] == 1) {
                    len++;
                    i--;
                }

                Pair pair = new Pair(i + 1, len);
                queue.offer(pair);
            }

            pr[index++] = queue.peek().length;
            int limit = tar.length - 1;
            for (int i = size - 2; i >= 0; i--, limit--) {
                if (tar[i] == 1) {
                    len++;
                    queue.offer(new Pair(i, len));
                } else
                    len = 0;

                while (true) {
                    Pair top = queue.poll();
                    if (top.index >= limit) ;
                    else if (top.index + top.length > limit) {
                        int cut = top.index + top.length - limit;
                        top.length -= cut;
                        queue.offer(top);
                    } else {
                        queue.offer(top);
                        break;
                    }
                }

                pr[index++] = queue.peek().length;
            }
        }

        public void rightShift() {
            shift++;
            if (shift == size) shift = 0;
        }

        public void leftShift() {
            shift--;
            if (shift == -1) shift = size - 1;
        }
    }

    final static Comparator<Pair> PAIR_COMPARATOR = (a, b) -> b.length != a.length ? b.length - a.length : b.index - a.index;

    final static class Pair {
        private int index, length;

        Pair(int index, int length) {
            this.index = index;
            this.length = length;
        }

        public void copyFrom(Pair pair) {
            index = pair.index;
            length = pair.length;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
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