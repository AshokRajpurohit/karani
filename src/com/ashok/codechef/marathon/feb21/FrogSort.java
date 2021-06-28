/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb21;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.stream.IntStream;

/**
 * Problem Name: Frog Sort
 * Link: https://www.codechef.com/FEB21B/problems/FROGS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FrogSort {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] weights = in.readIntArray(n), lengths = in.readIntArray(n);
            Frog[] frogs = IntStream.range(0, n)
                    .mapToObj(i -> new Frog(weights[i], lengths[i], i))
                    .toArray(v -> new Frog[v]);

            sb.append(jumps(frogs)).append('\n');
        }

        out.println(sb);
    }

    private static int jumps(Frog[] frogs) {
        Deque<Frog> fd = new ArrayDeque();
        Deque<Frog> copy = new ArrayDeque();

        int count = 0;
        for (Frog frog: frogs) fd.addLast(frog);

        while(fd.size() > 1) {
            Frog min = fd.stream().min(Comparator.comparingInt(a -> a.weight)).get();
            while(fd.getFirst().position <= min.position) {
                Frog frog = fd.removeFirst();
                if (frog == min) continue;
                int diff = min.position - frog.position;
                int jumps = 1 + (diff / frog.length);
                count += jumps;
                frog.position += frog.length * jumps;
                fd.addLast(frog);
            }
            copy.clear();
            fd.stream().sorted(Comparator.comparingInt(a -> a.position)).forEach(frog -> copy.addLast(frog));
            fd.clear();
            copy.stream().forEach(frog -> fd.addLast(frog));
        }

        return  count;
    }

    final static class Frog {
        final int weight;
        final int length;
        int position = 0;

        Frog(final int weight, final int length, int position) {
            this.weight = weight;
            this.length = length;
            this.position = position;
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
    }
}