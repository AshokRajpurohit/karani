/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Cleartrip for Ankit Soni
 * Link: Private Link
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ClearTrip {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ClearTrip a = new ClearTrip();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            String[] words = in.readStringArray(n);
            out.println(longestChain(words));
            out.flush();
        }
    }

    private static int longestChain(String[] words) {
        if (words.length == 0)
            return 0;

        int chainLength = 1;
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        TST tst = new TST(words);
        int[] lengths = new int[words.length];
        Arrays.fill(lengths, 1);
        int baseLength = words[0].length();

        if (words[words.length - 1].length() == baseLength)
            return chainLength;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() == baseLength)
                continue;

            if (word.length() > baseLength + 1)
                baseLength++;

            lengths[i] = getLength(tst, word, lengths);
            chainLength = Math.max(chainLength, lengths[i]);
        }

        return chainLength;
    }

    private static int getLength(TST tst, String word, int[] map) {
        char[] findWord = new char[word.length() - 1];
        int maxLength = 1;

        for (int i = 0; i < word.length(); i++) {
            populate(word, findWord, i);
            int id = tst.getId(findWord);
            if (id < 0)
                continue;

            maxLength = 1 + map[id];
        }

        return maxLength;
    }

    private static void populate(String s, char[] ar, int index) {
        for (int i = 0; i < index; i++)
            ar[i] = s.charAt(i);

        for (int i = index + 1; i < s.length(); i++)
            ar[i - 1] = s.charAt(i);
    }

    final static class TST {
        private TST left, right, equal;
        private boolean end = false;
        private Character ch;
        private int count = 0, id = -1;

        public TST(String s) {
            this(s, 0, 0);
        }

        /**
         * initializes the instance with first string and then add the remaining
         * strings.
         *
         * @param ar
         */
        public TST(String[] ar) {
            this(ar[0], 0, 0);
            for (int i = 1; i < ar.length; i++)
                this.add(ar[i], 0, i);
        }

        /**
         * initializes the instance.
         *
         * @param s   String s you want to initialize the instance.
         * @param pos start pos in string s to be inserted in instance.
         */
        private TST(String s, int pos, int id) {
            if (pos == s.length())
                return;

            if (ch == null) {
                ch = s.charAt(pos);
                if (pos == s.length() - 1) {
                    end = true;
                    this.id = id;
                    count++;
                } else
                    equal = new TST(s, pos + 1, id);
            } else {
                if (ch < s.charAt(pos)) {
                    if (right == null)
                        right = new TST(s, pos, id);
                    else
                        right.add(s, pos, id);
                } else if (ch == s.charAt(pos)) {
                    if (pos == s.length() - 1) {
                        end = true;
                        count++;
                    } else if (equal == null) {
                        equal = new TST(s, pos + 1, id);
                    } else {
                        equal.add(s, pos + 1, id);
                    }
                } else {
                    if (left == null)
                        left = new TST(s, pos, id);
                    else
                        left.add(s, pos, id);
                }
            }
        }

        public int getId(char[] s) {
            return getId(s, 0);
        }

        private int getId(char[] s, int pos) {
            if (pos == s.length)
                return 0;

            if (s[pos] == this.ch) {
                pos++;
                if (pos == s.length)
                    return id;
                if (equal == null)
                    return -1;
                return equal.getId(s, pos);
            }

            if (s[pos] > ch) {
                if (right == null)
                    return -1;
                return right.getId(s, pos);
            }

            if (left == null)
                return -1;

            return left.getId(s, pos);
        }

        /**
         * adds the given string to the TST structure.
         *
         * @param s
         */
        public void add(String s, int stringId) {
            this.add(s, 0, stringId);
        }

        /**
         * to add new string into existing TST instance.
         *
         * @param s   String s to be added to instance.
         * @param pos start char pos in String s.
         */
        private void add(String s, int pos, int id) {
            if (s.charAt(pos) == this.ch) {
                if (pos == s.length() - 1) {
                    this.end = true;
                    this.id = id;
                    count++;
                    return;
                } else {
                    if (equal == null)
                        equal = new TST(s, pos + 1, id);
                    else
                        equal.add(s, pos + 1, id);
                    return;
                }
            } else if (s.charAt(pos) < this.ch) {
                if (left == null) {
                    left = new TST(s, pos, id);
                    return;
                } else
                    left.add(s, pos, id);
            } else {
                if (right == null) {
                    right = new TST(s, pos, id);
                    return;
                } else {
                    right.add(s, pos, id);
                    return;
                }
            }
            return;
        }
    }

}
