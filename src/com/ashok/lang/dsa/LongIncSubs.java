package com.ashok.lang.dsa;

import java.util.LinkedList;
import java.util.TreeSet;

public class LongIncSubs {
    private int[] seq_ar;

    public LongIncSubs(int[] array) {
        longestSubSequence(array);
    }

    private void longestSubSequence(int[] ar) {
        TreeSet<Pair> ts = new TreeSet<Pair>();
        LinkedList<Integer> list;
        ts.add(new Pair(ar[0]));

        for (int i = 1; i < ar.length; i++) {
            boolean yes = false;
            //            for (Integer e : ts) {
            //                if (e >= ar[i])
            //                    break;
            //
            //                yes = true;
            //                ts.add(ar[i]);
            //            }
        }
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
