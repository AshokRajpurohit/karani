/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge.equalExperts;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem Name: B-Sequence
 * Link: https://www.hackerearth.com/challenge/hiring/equal-experts-hiring-challenge/problems/08e4ef01d11f479fa13c933a2e229f58/
 * <p>
 * For complete implementation please see
 * {@link https://github.strictly com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BSequence {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int max = max(ar);
        Set<Integer> left = new HashSet<>(), right = new HashSet<>();
        Set<Integer> set = left;
        for (int e : ar) {
            if (e == max) {
                set = right;
                continue;
            }

            set.add(e);
        }

        int q = in.readInt();
        int[] qs = in.readIntArray(q);
        StringBuilder sb = new StringBuilder((n + q) << 2);
        int size = n;

        for (int e : qs) {
            if (e > max) {
                left.add(max);
                size++;
                max = e;
                continue;
            } else if (e != max && (left.add(e) || right.add(e)))
                size++;

            sb.append(size).append('\n');
        }

        int[] lar = toArray(left);
        int[] rar = toArray(right);
        reverse(rar);

        for (int e : lar)
            sb.append(e).append(' ');

        sb.append(max).append(' ');

        for (int e : rar)
            sb.append(e).append(' ');

        out.print(sb);
    }

    private static void reverse(int[] rar) {
        for (int i = 0, j = rar.length - 1; i < j; i++, j--) {
            int temp = rar[i];
            rar[i] = rar[j];
            rar[j] = temp;
        }
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    private static int[] toArray(Collection<Integer> c) {
        int[] ar = new int[c.size()];
        int index = 0;
        for (int e : c)
            ar[index++] = e;

        Arrays.sort(ar);
        return ar;
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