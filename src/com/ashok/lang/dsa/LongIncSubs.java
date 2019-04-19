package com.ashok.lang.dsa;

public class LongIncSubs {
    private int[] seq_ar;

    public LongIncSubs(int[] array) {
        longestIncreasingSubSequence(array);
    }

    public static int[] longestIncreasingSubSequence(int[] ar) {
        int len = ar.length;
        int[] map = new int[len];
        map[0] = 1;
        for (int i = 1; i < len; i++) {
            int max = 0;
            for (int j = 0; j < i - 1; j++) {
                if (ar[j] < ar[i]) {
                    max = Math.max(max, map[j]);
                }
            }
            map[i] = max + 1;
        }

        return map;
    }

    final static class Pair implements Comparable<Pair> {
        int length, last;

        Pair(int a) {
            length = 1;
            last = a;
        }

        void add(Pair pair) {
            last = pair.last;
            length++;
        }

        public int compareTo(Pair pair) {
            if (last == pair.last)
                return length - pair.length;

            return this.last - pair.last;
        }
    }


}
