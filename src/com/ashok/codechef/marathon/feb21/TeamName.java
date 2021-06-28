/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * Problem Name: Team Name
 * Link: https://www.codechef.com/FEB21B/problems/TEAMNAME
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class TeamName {
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
            String[] words = in.readStringArray(n);
            out.println(process1(words));
        }
    }

    private static long process1(String[] words) {
        Map<String, boolean[]> stringCharMap = new HashMap<>();
        int[] firstChars = new int[26]; // how many strings have the same first character.
        for (String w : words) {
            firstChars[w.charAt(0) - 'a']++;
            String suffix = w.substring(1);
            boolean[] counts = stringCharMap.computeIfAbsent(suffix, x -> new boolean[26]);
            counts[w.charAt(0) - 'a'] = true;
        }

        int count = 0;
        for (Entry<String, boolean[]> entry : stringCharMap.entrySet()) {
            for (Entry<String, boolean[]> e : stringCharMap.entrySet()) {
                if (e == entry) continue;
                boolean[] first = entry.getValue(), second = e.getValue();
                int fc = 0, sc = 0;
                for (int i = 0; i < 26; i++) {
                    if (first[i] == second[i]) continue;
                    if (first[i]) fc++;
                    else if (second[i]) sc++;
                }

                count += fc * sc;
            }
        }

        return count;
    }

    private static long process(String[] words) {
        final Trie root = new Trie(' ');
        Function<String, String> reverse = a -> {
            char[] chars = a.toCharArray();
            for (int i = 0, j = a.length() - 1; i < j; i++, j--) {
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
            }
            return new String(chars);
        };

        ToIntFunction<String> calc = a -> {
            Trie node = root;
            for (int i = 0; i < a.length(); i++) {
                if (i == a.length() - 2) return node.count;
                int ci = a.charAt(i) - 'a';
                node = node.childs[ci];
            }

            return 0;
        };

        boolean[] firstChars = new boolean[26];
        for (String word : words) {
            firstChars[word.charAt(0) - 'a'] = true;
        }

        words = Arrays.stream(words).map(w -> reverse.apply(w)).toArray(t -> new String[t]);
        for (String word : words) root.add(word, 0);
        long count = 0;
        for (String word : words) {
            count += calc.applyAsInt(word) - 1;
        }

        return count >>> 1;
    }

    final static class Trie {
        int count;
        final char ch;
        Trie[] childs = new Trie[26];

        Trie(final char ch) {
            this.ch = ch;
        }

        void add(String s, int index) {
            if (index == s.length()) return;
            count++;
            char c = s.charAt(index);
            int ci = c - 'a';
            if (childs[ci] == null) childs[ci] = new Trie(c);
            childs[ci].add(s, index + 1);
        }

        public String toString() {
            return ch + ": " + count;
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

        public String[] readStringArray(int size) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read();

            return res;
        }
    }
}