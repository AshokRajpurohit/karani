/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.may20;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name: Buying a New String
 * Link: https://www.codechef.com/MAY20A/problems/TWOSTRS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BuyingANewString {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            String a = in.read(), b = in.read();
            int n = in.readInt();
            FavoriteString[] fvs = new FavoriteString[n];
            for (int i = 0; i < n; i++) fvs[i] = new FavoriteString(i, in.read(), in.readInt());
            out.println(bruteForce(a, b, fvs));
        }
    }

    private static void test() throws IOException {
        int n = in.readInt(), a = in.readInt();
        RandomStrings rs = new RandomStrings();
        while (true) {
            String s = rs.nextStringabc(n), s1 = rs.nextStringabc(n);
            String[] ars = Arrays.stream(Generators.generateRandomStringArray(a, 1, n))
                    .map(v -> v.toLowerCase()).toArray(t -> new String[t]);
            int[] nums = Generators.generateRandomIntegerArray(a, 1, 10000);
            FavoriteString[] fvs = IntStream.range(0, a)
                    .mapToObj(i -> new FavoriteString(i, ars[i], nums[i]))
                    .toArray(t -> new FavoriteString[t]);

            bruteForce(s, s1, fvs);
        }
    }

    private static long bruteForce(String a, String b, FavoriteString[] fvs) {
        int alen = a.length(), blen = b.length();
        int minLen = Arrays.stream(fvs).mapToInt(fv -> fv.str.length()).min().getAsInt();
        StringMatcher[] matchers = new StringMatcher[fvs.length];
        Arrays.stream(fvs).forEach(fv -> matchers[fv.id] = getStringMatcher(fv.str));

        long max = 0;
        for (String apart : prefixes(a)) {
            for (String bpart : suffixes(b)) {
                if (apart.length() + bpart.length() < minLen) continue;
                max = Math.max(max, calculate(apart + bpart, fvs, matchers));
            }
        }

        return max;
    }

    private static Collection<String> substrings(String s) {
        int n = s.length();
        Set<String> subStrings = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                subStrings.add(s.substring(i, j + 1));
            }
        }

        return subStrings;
    }

    private static Collection<String> prefixes(String s) {
        return IntStream.range(1, s.length() + 1)
                .mapToObj(i -> s.substring(0, i))
                .collect(Collectors.toSet());
    }

    private static Collection<String> suffixes(String s) {
        return IntStream.range(0, s.length())
                .mapToObj(i -> s.substring(i))
                .collect(Collectors.toSet());
    }

    private static long calculate(String s, FavoriteString[] fvs, StringMatcher[] matchers) {
        return Arrays.stream(fvs)
                .mapToLong(fv -> 1L * fv.beauty * matchIndexes(s, matchers[fv.id]).length)
                .sum();
    }

    private static int count(String s, String pattern) {
        int count = -1;
        int index = -1;
        do {
            count++;
            index = s.indexOf(pattern, index + 1);
        } while (index >= 0);

        return count;
    }

    private static long process(String a, String b, FavoriteString[] fvs) {
        int minLen = 0, maxLen = 0;
        long[] sumA = new long[a.length()], sumB = new long[b.length()];
        int[][] mapA = new int[fvs.length][], mapB = new int[fvs.length][];
        StringMatcher[] matchers = new StringMatcher[fvs.length];

        maxLen = Arrays.stream(fvs).mapToInt(fv -> fv.str.length()).max().getAsInt();
        minLen = Arrays.stream(fvs).mapToInt(fv -> fv.str.length()).min().getAsInt();
        Arrays.stream(fvs).forEach(fv -> matchers[fv.id] = getStringMatcher(fv.str));
        Arrays.stream(fvs).forEach(fv -> mapA[fv.id] = matchIndexes(a, matchers[fv.id]));
        Arrays.stream(fvs).forEach(fv -> mapB[fv.id] = matchIndexes(b, matchers[fv.id]));
//        try {
//        } catch (Exception e) {
//            System.exit(0);
//        }

        Arrays.stream(fvs)
                .forEach(fv -> Arrays.stream(mapA[fv.id])
                        .filter(index -> index >= 0)
                        .map(index -> index + fv.str.length() - 1) // taking the last index of every match.
                        .filter(matchId -> matchId >= 0 && matchId < a.length())
                        .forEach(matchId -> sumA[matchId] += fv.beauty));

        Arrays.stream(fvs)
                .forEach(fv -> Arrays.stream(mapB[fv.id])
                        .filter(matchId -> matchId >= 0 && matchId < b.length())
                        .forEach(matchId -> sumB[matchId] += fv.beauty)); // no parallelization since common resource.

        for (int i = 1; i < sumA.length; i++) sumA[i] += sumA[i - 1];
        for (int i = sumB.length - 2; i >= 0; i--) sumB[i] += sumB[i + 1];

        int aie = a.length() - 1, bis = 0; // aie -> end index of substring of a, bis is start index for b's substring.
        int biLimit = b.length() - minLen, aiLimit = Math.max(minLen - 1, 0); // max value for bis and min value for aie.
        long max = sumA[sumA.length - 1] + sumB[0];
        while (aie >= aiLimit) {
            bis = 0;
            int astart = Math.max(0, aie - maxLen + 1);
            while (bis < biLimit) {
                int bend = Math.min(bis + maxLen - 1, b.length() - 1);
                bend = Math.max(bis, bend);
                bend = Math.min(bend, b.length() - 1);
                long sum = sumA[aie] + sumB[bis];
                String s = a.substring(astart, aie + 1) + b.substring(bis, bend + 1);
                int mustInclude = aie + 1 - astart;
                sum += Arrays.stream(fvs)
                        .filter(fv -> fv.str.length() != 1)
                        .filter(fv -> fv.str.length() < s.length())
                        .mapToLong(fv -> {
                            if (s.length() == fv.str.length()) {
                                return s.equals(fv.str) ? fv.beauty : 0;
                            }
                            int[] indices = matchIndexes(s, matchers[fv.id]);
                            long res = 0;
                            for (int e : indices) {
                                if (e >= mustInclude) continue;
                                if (e + fv.str.length() > mustInclude) res += fv.beauty;
                            }
                            return res;
                        })
                        .sum();

                max = Math.max(sum, max);
                bis++;
            }
            aie--;
        }
        return max;
    }

    /**
     * Returns mapping for <code>ref</code> string about the beauty score, starting from each index.
     *
     * @param ref
     * @param matcher
     * @return
     */
    private static int[] matchIndexes(String ref, StringMatcher matcher) {
        List<Integer> list = new ArrayList();
        matcher.finder(ref).forEachRemaining(v -> list.add(v));
        return list.stream().mapToInt(v -> v).toArray();
    }

    final static class FavoriteString {
        final int id;
        final String str;
        final int beauty;

        FavoriteString(final int id, final String str, final int beauty) {
            this.id = id;
            this.str = str;
            this.beauty = beauty;
        }

        public String toString() {
            return str + " " + beauty;
        }

        public int hashCode() {
            return id;
        }

        public boolean equals(Object o) {
            return (o != null) && (o instanceof FavoriteString) && ((FavoriteString) o).id == id;
        }
    }

    interface StringMatcher {
        Iterator<Integer> finder(String text);
    }

    private static StringMatcher getStringMatcher(String pattern) {
        if (pattern.length() == 1) return new SingleCharMatcher(pattern.charAt(0));
        if (pattern.length() < 5) return new SimpleStringMatcher(pattern);
        return new KMPStringMatcher(pattern);
    }

    final static class KMPStringMatcher implements StringMatcher {
        final String ref;
        private final char[] searchChars;
        private final int[] failTable;

        KMPStringMatcher(final String ref) {
            this.ref = ref;
            searchChars = ref.toCharArray();
            failTable = failFunctionKMP(ref);
        }

        @Override
        public Iterator<Integer> finder(String text) {
            return new MatchIterator(text);
        }

        private class MatchIterator implements Iterator<Integer> {
            final String text;
            private int ti = 0, ri = 0;
            private final int limit;

            private MatchIterator(final String text) {
                this.text = text;
                limit = text.length() - ref.length();
                if (text.length() == ref.length()) {
                    ti = text.equals(ref) ? 0 : -1;
                } else if (text.length() < ref.length()) {
                    ti = -1;
                } else {
                    shift();
                }
            }

            private void shift() {
                if (ti >= text.length()) {
                    ti = -1;
                    return;
                }
                while (ti < text.length()) {
                    if (text.charAt(ti) == searchChars[ri]) {
                        if (ri == searchChars.length - 1) { // match found.
                            ti++;
                            ri = failTable[ri];
                            return;
                        } else {
                            ti++;
                            ri++;
                        }
                    } else {
                        if (ri > 0) {
                            ri = failTable[ri - 1];
                        } else
                            ti++;
                    }
                }

                ti = -1;
            }

            @Override
            public boolean hasNext() {
                return ti != -1;
            }

            @Override
            public Integer next() {
                int v = ti;
                if (v != -1) {
                    shift();
                }
                return v != -1 ? v - ref.length() : v;
            }

            public String toString() {
                return ref + " " + text;
            }
        }

        public String toString() {
            return ref;
        }
    }

    final static class SimpleStringMatcher implements StringMatcher {
        final String ref;
        final char[] chars;

        SimpleStringMatcher(final String ref) {
            this.ref = ref;
            chars = ref.toCharArray();
        }

        @Override
        public Iterator<Integer> finder(String text) {
            return new MatchIterator(text);
        }

        private class MatchIterator implements Iterator<Integer> {
            final String text;
            int index = 0;

            private MatchIterator(final String text) {
                this.text = text;
                index = text.indexOf(ref);
            }

            @Override
            public boolean hasNext() {
                return index != -1;
            }

            @Override
            public Integer next() {
                int v = index;
                index = text.indexOf(ref, index + 1);
                return v;
            }
        }
    }

    final static class SingleCharMatcher implements StringMatcher {
        final char ch;

        SingleCharMatcher(final char ch) {
            this.ch = ch;
        }

        @Override
        public Iterator<Integer> finder(String text) {
            return new MatchIterator(text);
        }

        private class MatchIterator implements Iterator<Integer> {
            final String text;
            int index;

            private MatchIterator(final String text) {
                this.text = text;
                shift();
            }

            @Override
            public boolean hasNext() {
                return index != -1;
            }

            private void shift() {
                while (index < text.length() && text.charAt(index) != ch) index++;
                if (index == text.length()) index = -1;
            }

            @Override
            public Integer next() {
                int v = index;
                index++;
                shift();
                return v;
            }
        }
    }

    /**
     * returns fail function (Partial Matching Table) used in KMP string search
     * algorithm.
     *
     * @param s String for which the fail function to be generated.
     * @return
     */
    private static int[] failFunctionKMP(String s) {
        int[] ar = new int[s.length()];
        int i = 1, j = 0;

        while (i < s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                ar[i] = j + 1;
                i++;
                j++;
            } else if (j > 0)
                j = ar[j - 1];
            else {
                ar[i] = 0;
                i++;
            }
        }
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