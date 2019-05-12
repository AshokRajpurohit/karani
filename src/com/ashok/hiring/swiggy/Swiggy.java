/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.swiggy;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem Name: Swiggy HackerRank Test
 * Link: email
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Swiggy {
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
            int n = in.readInt(), g = in.readInt(), k = in.readInt();
            int[] source = in.readIntArray(k), target = in.readIntArray(k);
            List<Integer> sourceList = Arrays.stream(source).mapToObj(i -> i).collect(Collectors.toList());
            List<Integer> targetList = Arrays.stream(target).mapToObj(i -> i).collect(Collectors.toList());
            out.println(TravelingIsFun.connectedCities(n, g, sourceList, targetList));
            out.flush();
        }
    }

    final static class MostFrequentSubstring {
        public static int getMaxOccurrences(String s, int minLength, int maxLength, int maxUnique) {
            SlidingWindowValidator validator = new SlidingWindowValidator(minLength, maxLength, maxUnique);
            return getMaxOccurrences(s, validator);
        }

        private static int getMaxOccurrences(String s, SlidingWindowValidator validator) {
            SlidingWindow window = new SlidingWindow();
            char[] chars = s.toCharArray();
            Set<String> validSubStrings = new HashSet<>();
            int from = 0, to = 0, length = s.length();
            while (to < length) {
                while (to < length && !validator.checkMinCriterieMet(window)) {
                    window.add(chars[to++]);
                }

                while (from <= to && validator.checkLimitExceed(window)) {
                    window.remove(chars[from++]);
                }

                boolean subStringFound = validator.validateWindow(window); // atleast one substring.
                while (from <= to && validator.validateWindow(window)) {
                    window.remove(chars[from++]);
                }

                if (subStringFound)
                    validSubStrings.add(new String(chars, from - 1, window.size + 1)); // let's use the last substring, the smallest one
            }

            return validSubStrings.stream().mapToInt(vss -> stringCountIntersect(s, vss)).max().getAsInt();
        }

        /**
         * This method returns the number of times search appears in s while
         * occurances can be intersecting.
         * This method is based on KMP algorithm for string matching.
         * if the search string and s string both are of same length then it checks
         * for equality and returns 1 or 0 accordingly.
         *
         * @param s
         * @param search
         * @return number of times search appears in String s
         */
        public static int stringCountIntersect(String s, String search) {
            if (s.length() < search.length())
                return 0;

            if (s.length() == search.length()) {
                for (int i = 0; i < s.length(); i++)
                    if (s.charAt(i) != search.charAt(i))
                        return 0;

                return 1;
            }

            int count = 0;
            int[] failTable = failFunctionKMP(search);
            int i = 0, j = 0;

            while (i < s.length()) {
                if (s.charAt(i) == search.charAt(j)) {
                    if (j == search.length() - 1) {
                        count++;
                        i++;
                        j = failTable[j];
                    } else {
                        i++;
                        j++;
                    }
                } else {
                    if (j > 0) {
                        j = failTable[j - 1];
                    } else
                        i++;
                }
            }

            return count;
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

        final static class SlidingWindowValidator {
            final int minLength, maxLength, maxUnique;

            SlidingWindowValidator(int minLength, int maxLength, int maxUnique) {
                this.minLength = minLength;
                this.maxLength = maxLength;
                this.maxUnique = maxUnique;
            }

            boolean validateWindow(SlidingWindow window) {
                return minLength <= window.size && maxLength >= window.size && maxUnique >= window.uniqueCharCount;
            }

            boolean checkLimitExceed(SlidingWindow window) {
                return maxLength < window.size || maxUnique < window.uniqueCharCount;
            }

            boolean checkMinCriterieMet(SlidingWindow window) {
                return minLength <= window.size;
            }
        }

        final static class SlidingWindow {
            final int[] counts = new int[256];
            private int uniqueCharCount = 0, size = 0;

            private void add(int e) {
                size++;
                counts[e]++;
                if (counts[e] == 0) uniqueCharCount++;
            }

            private void remove(int e) {
                size--;
                counts[e]--;
                if (counts[e] == 0) uniqueCharCount--;
            }
        }
    }

    final static class TravelingIsFun {
        public static List<Integer> connectedCities(int n, int g, List<Integer> originCities, List<Integer> destinationCities) {
            int[] groups = calculateGroups(n, g);
            Iterator<Integer> originIter = originCities.iterator(), destinationIter = destinationCities.iterator();
            List<Integer> result = new LinkedList<>();
            while (originIter.hasNext()) {
                int sourceCity = originIter.next(), targetCity = destinationIter.next();
                boolean reachable = sourceCity == targetCity || (groups[sourceCity] != 0 && groups[sourceCity] == groups[targetCity]);
                result.add(reachable ? 1 : 0);
            }

            return result;
        }

        private static int[] calculateGroups(int n, int g) {
            int[] groups = new int[n + 1];
            if (g == 0) {
                Arrays.fill(groups, 1);
                return groups;
            }

            g++;
            int groupId = 1, sqrt = (int) (Math.sqrt(n));
            int r = g;
            while (r <= n) {
                groups[r] = groupId;
                r += g;
            }
            g++;

            while (g <= sqrt) {
                r = g;
                while (r <= n) {
                    groups[r] = groupId;
                    r += g;
                }

                g++;
            }

            while (g <= n) {
                if (groups[g] != 0) {
                    g++;
                    continue;
                }

                int gid = groupId;
                r = g;
                boolean merge = false;
                while (r <= n) {
                    if (groups[r] != 0) {
                        gid = groups[r];
                        merge = true;
                        break;
                    }

                    groups[r] = gid;
                    r += g;
                }

                if (merge) {
                    r = g;
                    while (r <= n) {
                        groups[r] = gid;
                        r += g;
                    }
                }

                g++;
                groupId++;
            }

            return groups;
        }
    }
}
