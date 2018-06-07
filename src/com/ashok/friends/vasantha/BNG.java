/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vasantha;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: BNG QA SDET Test
 * Link: Mail link
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BNG {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CountUniqueDigitNumbers.solve();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

        }
    }

    final static class CountUniqueDigitNumbers {
        private static void solve() throws IOException {
            boolean[] map = new boolean[10];
            int start = in.readInt(), end = in.readInt();
            for (int r = start; r <= end; r++) {
                if (isUniqueDigits(map, r))
                    out.println(r);
            }
        }

        private static boolean isUniqueDigits(boolean[] map, int n) {
            Arrays.fill(map, false);
            while (n > 0) {
                int digit = n % 10;
                n /= 10;
                if (map[digit])
                    return false;

                map[digit] = true;
            }

            return true;
        }
    }

    final static class ElementsPresentInTree {
        static int isPresent(Node root, int value) {
            while (root != null) {
                if (root.data > value)
                    root = root.left;
                else if (root.data < value)
                    root = root.right;
                else
                    return 1;
            }

            return 0;
        }
    }

    final static class Playlist {
        static int playlist(String[] songs, int currentIndex, String query) {
            int n = songs.length;
            int next = -1, prev = -1;
            for (int i = currentIndex + 1; i != currentIndex && next == -1; i++) {
                if (i == n)
                    i = 0;

                if (query.equals(songs[i]))
                    next = i;
            }

            for (int i = currentIndex - 1; i != currentIndex && prev == -1; i--) {
                if (i == -1)
                    i = n - 1;

                if (query.equals(songs[i]))
                    prev = i;
            }

            if (next == -1 && prev == -1)
                return n;
            else if (next == -1)
                return Math.abs(prev - currentIndex);
            else if (prev == -1)
                return Math.abs(next - currentIndex);
            return Math.min(Math.abs(prev - currentIndex), Math.abs(next - currentIndex));
        }
    }

    final static class Frequency {
        static int[] frequency(String s) {
            int[] map = new int[26];
            for (int i = 1; i <= 26; i++) {
                String num = String.valueOf(i);
                if (i > 9) num = num + "#";
                map[i - 1] = getCount(s, num);
            }

            return map;
        }

        private static int getCount(String s, String pattern) {
            if (pattern.length() == 1) {
                char ch = pattern.charAt(0);
                return getCount(s, ch);
            }

            int index = s.indexOf(pattern), len = s.length(), plen = pattern.length();
            int count = 0;
            while (index != -1) {
                if (index < len - plen && s.charAt(index + plen) == '(') {
                    int bracketClose = s.indexOf(')');
                    int num = Integer.valueOf(s.substring(index + 2, bracketClose));
                    index = s.indexOf(bracketClose + 1);
                    count += num;
                } else {
                    index = s.indexOf(index + plen + 1);
                    count++;
                }
            }

            return count;
        }

        private static int getCount(String s, char ch) {
            int index = s.indexOf(ch), len = s.length();
            int count = 0;
            while (index != -1) {
                if (index < len - 2 && s.charAt(index + 2) == '#') {
                    index = s.indexOf(ch, index + 1);
                } else if (index < len - 1 && s.charAt(index + 1) == '(') {
                    int bracketClose = s.indexOf(')');
                    int num = Integer.valueOf(s.substring(index + 2, bracketClose));
                    index = s.indexOf(bracketClose + 1);
                    count += num;
                } else {
                    index = s.indexOf(index + 2);
                    count++;
                }
            }

            return count;
        }
    }

    final static class Node {
        Node left, right;
        int data;
    }
}