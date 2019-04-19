/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.infoworks;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class InfoWorksIO {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            String[] strings = in.readStringArray(n);
            out.print(strings);
            out.flush();
            out.println(new MergeStrings(Arrays.stream(strings).collect(Collectors.toList())).getMergeString());
            out.flush();
        }
    }

    private static Collection<String> normalize(Collection<String> strings) {
        String[] ar = strings.stream().sorted((a, b) -> a.length() - b.length()).toArray(t -> new String[t]);
        int len = ar.length;
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i + 1; j < len && ar[i] != null; j++) {
                if (ar[i] != null && ar[j] != null && ar[j].contains(ar[i])) {
                    ar[i] = null;
                    break;
                }
            }
        }

        return Arrays.stream(ar).filter(t -> t != null).collect(Collectors.toSet());
    }

    private static Map<String, Map<String, Integer>> prepareMatchingMap(Collection<String> strings) {
        Map<String, Map<String, Integer>> pairMatchMap = new HashMap<>();
        for (String first : strings) {
            Map<String, Integer> matchMap = new HashMap<>();
            for (String second : strings)
                if (first != second) matchMap.put(second, calculateMergeLength(first, second));

            pairMatchMap.put(first, matchMap);
        }

        return pairMatchMap;
    }

    private static int calculateMergeLength(String a, String b) {
        char[] ar = a.toCharArray(), br = b.toCharArray();
        int len = Math.min(a.length(), b.length());
        for (int i = a.length() - len; i < a.length() && !matches(ar, br, i); i++, len--) ;
        return len;
    }

    private static boolean matches(char[] ar, char[] br, int startIndex) {
        int bi = 0;
        while (startIndex < ar.length) {
            if (ar[startIndex] != br[bi])
                return false;

            startIndex++;
            bi++;
        }

        return true;
    }

    final static class MergeStrings {
        private Collection<String> strings;
        private final Deque<String> orderedStrings = new LinkedList<>(); // for permutation and calculation
        private Collection<String> minLengthPermutation;
        private final Map<String, Map<String, Integer>> stringPairMatchMap;
        private int minLength = Integer.MAX_VALUE;

        MergeStrings(Collection<String> strings) {
            this.strings = normalize(strings); // remove strings which are substrings of other string
            minLength = this.strings.stream().mapToInt(t -> t.length()).sum();
            stringPairMatchMap = prepareMatchingMap(this.strings); // for every ordered pair, save merge length
            minLengthPermutation = new LinkedList<>(this.strings);
        }

        private void permuteAndCalculate(int lengthSoFar) {
            if (lengthSoFar >= minLength) return;
            if (strings.isEmpty()) {
                minLengthPermutation = new LinkedList<>(orderedStrings);
                minLength = lengthSoFar;
            }
            Collection<String> copy = new LinkedList<>(strings);
            String lastString = orderedStrings.isEmpty() ? null : orderedStrings.getLast();
            for (String nextString : copy) {
                strings.remove(nextString);
                orderedStrings.addLast(nextString);
                permuteAndCalculate(lengthSoFar + nextString.length() - getMatchLength(lastString, nextString));
                orderedStrings.removeLast();
                strings.add(nextString);
            }
        }

        private int getMatchLength(String a, String b) {
            return a != null ? stringPairMatchMap.get(a).get(b) : 0;
        }

        public String getMergeString() {
            permuteAndCalculate(0);
            StringBuilder sb = new StringBuilder(minLength);
            String last = null;
            for (String s : minLengthPermutation) {
                if (last == null)
                    sb.append(s);
                else
                    sb.append(s.substring(stringPairMatchMap.get(last).get(s)));

                last = s;
            }

            return sb.toString();
        }
    }
}
