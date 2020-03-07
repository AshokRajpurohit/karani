/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.semifinal19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
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
public class Roadies {
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
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            out.println(process(ar));
        }
    }

    private static int process(int[] ar) {
        NumberSetBucket bucket = new NumberSetBucket();
        Arrays.sort(ar);
        int num = -1;
        for (int e : ar) {
            if (e == num) continue;
            num = e;
            populateSets(bucket, e);
        }

        return bucket.values().stream().mapToInt(s -> s.sum).max().getAsInt();
    }

    private static void populateSets(NumberSetBucket bucket, int n) {
        Set<NumberSet> clones = new HashSet<>();
        bucket.values().stream().filter(s -> s.validate(n)).map(s -> s.clone()).forEach(s -> {
            s.put(n);
            clones.add(s);
        });

        clones.forEach(set -> bucket.add(set));
        bucket.add(new NumberSet(n));
    }

    final static class NumberSetBucket extends TreeMap<NumberSet, NumberSet> {
        public NumberSetBucket() {
            super((a, b) -> a.hash - b.hash);
        }

        void add(NumberSet set) {
            NumberSet oldSet = get(set);
            if (oldSet == null || oldSet.sum < set.sum) {
                put(set, set);
            }
        }
    }

    final static class NumberSet {
        private final static int[] twoPowers = IntStream.range(0, 10).map(i -> 1 << i).toArray();
        final boolean[] digitMarks = new boolean[10];
        List<Integer> numbers = new LinkedList<>();
        int sum = 0, hash = 0;

        public NumberSet(int n) {
            put(n);
        }

        private NumberSet() {

        }

        boolean validate(int n) {
            while (n > 9) {
                int digit = n % 10;
                n /= 10;
                if (digitMarks[digit]) return false;
            }

            return !digitMarks[n];
        }

        void put(int n) {
            numbers.add(n);
            sum += n;
            while (n > 9) {
                int digit = n % 10;
                n /= 10;
                if (!digitMarks[digit]) hash += (1 << digit);
                digitMarks[digit] = true;
            }

            if (!digitMarks[n]) hash += (1 << n);
            digitMarks[n] = true;
        }

        @Override
        public NumberSet clone() {
            NumberSet numberSet = new NumberSet();
            numberSet.sum = sum;
            IntStream.range(0, 10).forEach(i -> numberSet.digitMarks[i] = digitMarks[i]);
            numberSet.numbers.addAll(numbers);
            numberSet.hash = hash;
            return numberSet;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof NumberSet)) return false;
            return hash != ((NumberSet) o).hash;
        }

        @Override
        public String toString() {
            return numbers.toString();
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