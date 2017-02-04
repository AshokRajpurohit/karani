/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: FreshDesk Hiring Challenge.
 * Link: Private Link
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FreshDesk {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            RandomStrings random = new RandomStrings();
            out.println(RearrangeWords.rearrangeWord(random.nextStringabc(in.readInt())));
            out.flush();
        }
    }

    final static class RearrangeWords {
        private final static String NA = "no answer";
        static String rearrangeWord(String word) {
            char[] ar = word.toCharArray();

            if (impossible(ar))
                return NA;

            char max = ar[ar.length - 1];

            for (int i = ar.length - 2; i >= 0; i--) {
                if (ar[i] < ar[i + 1]) {
                    int index = getMin(ar, ar[i], i + 1);
                    swap(ar, i, index);

                    Arrays.sort(ar, i + 1, ar.length);
                    return String.valueOf(ar);
                }
            }

            return NA;
        }

        private static int getMin(char[] ar, char ch, int start) {
            char min = ar[start];
            int index = start;

            for (int i = start; i < ar.length; i++)
                if (ar[i] > ch && ar[i] < min) {
                    min = ar[i];
                    index = i;
                }

            return index;
        }

        private static void swap(char[] ar, int i, int j) {
            char t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }

        private static boolean impossible(char[] ar) {
            char ref = ar[0];

            for (char ch : ar) {
                if (ref < ch)
                    return false;

                ref = ch;
            }

            return true;
        }
    }

    final static class SplitPixels {
        private static Distance[] distances = new Distance[5];
        private static final String AMBIGUOUS = "Ambiguous";

        static {
            for (Pixel pixel : Pixel.values())
                distances[pixel.ordinal()] = new Distance(0, pixel);
        }

        static String[] ClosestColor(String[] hexcodes) {
            String[] res = new String[hexcodes.length];

            for (int i = 0; i < hexcodes.length; i++)
                res[i] = getColor(hexcodes[i]);

            return res;
        }

        private static String getColor(String hexaCode) {
            ColorPoint colorPoint = new ColorPoint(Long.valueOf(hexaCode, 2));

            for (Distance distance : distances)
                distance.updateDistance(colorPoint);

            return process();
        }

        private static String process() {
            Arrays.sort(distances);

            if (distances[0].d2 == distances[1].d2)
                return AMBIGUOUS;

            return distances[0].color.name();
        }

        enum Pixel {
            Red(255, 0, 0), Green(0, 255, 0), Blue(0, 0, 255), Black(0, 0, 0), White(255, 255, 255);
            final int r, g, b;

            Pixel(int r, int g, int b) {
                this.r = r;
                this.g = g;
                this.b = b;
            }
        }

        final static class ColorPoint {
            int r, g, b;

            ColorPoint(long num) {
                b = (int) (num & 255);

                num >>>= 8;
                g = (int) (num & 255);

                num >>>= 8;
                r = (int) (num);
            }
        }

        final static class Distance implements Comparable<Distance> {
            int d2;
            final Pixel color;

            Distance(int distanceSquare, Pixel color) {
                d2 = distanceSquare;
                this.color = color;
            }

            public void updateDistance(ColorPoint colorPoint) {
                int dr = Math.abs(colorPoint.r - color.r),
                        dg = Math.abs(colorPoint.g - color.g),
                        db = Math.abs(colorPoint.b - color.b);

                d2 = dr * dr + dg * dg + db * db;
            }

            @Override
            public int compareTo(Distance distance) {
                return d2 - distance.d2;
            }
        }
    }

    /**
     * Problem: HackLand Election
     * <p>
     * No need to use TST or Trie for this problem as the constraints are sufficient to use HashMap.
     */
    final static class HackLandElection {
        public static String electionWinner(String[] votes) {
            HashMap<String, Integer> voteMap = new HashMap<>();

            for (String candidate : votes)
                addVote(voteMap, candidate);

            int max = getMaxValue(voteMap);
            String winner = getWinner(voteMap, max);

            return winner;
        }

        private static void addVote(Map<String, Integer> map, String key) {
            int votes = 1;
            if (map.containsKey(key))
                votes = map.get(key) + 1;

            map.put(key, votes);
        }

        private static int getMaxValue(Map<String, Integer> map) {
            int max = 0;

            for (int vote : map.values())
                max = Math.max(max, vote);

            return max;
        }

        private static String getWinner(Map<String, Integer> voteMap, int votes) {
            String winner = "";

            for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
                if (entry.getValue() == votes && winner.compareTo(entry.getKey()) < 0)
                    winner = entry.getKey();
            }

            return winner;
        }
    }
}
