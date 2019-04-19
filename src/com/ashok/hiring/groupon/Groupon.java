/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.groupon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Groupon {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    private static boolean[] charMap = new boolean[256];

    static {
        Arrays.fill(charMap, 'a', 'z' + 1, true);
    }

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
            out.println(missingWords(in.readLine(), in.readLine()));
            out.flush();
        }
    }

    public static String betterCompression(String s) {
        StringBuilder sb = new StringBuilder();
        long[] charCounts = new long[256];
        int ch;
        long count = 0;
        for (int i = 0; i < s.length(); ) {
            ch = s.charAt(i);
            if (charMap[ch]) {
                count = 0;
                i++;
                while (i < s.length() && !charMap[s.charAt(i)]) {
                    count = count * 10 + s.charAt(i) - '0';
                    i++;
                }
                charCounts[ch] += count;
            } else {
                throw new RuntimeException("invalid char string: " + s);
            }
        }

        IntStream.range('a', 'z' + 1).filter(i -> charCounts[i] != 0).forEach(i -> sb.append((char) i).append(charCounts[i]));
        return sb.toString();
    }

    public static List<String> missingWords(String s, String t) {
        String[] mainSentence = s.split("\\s"), subsequenceWords = t.split("\\s");
        int mi = 0, si = 0;
        List<String> missingWords = new LinkedList<>();
        while (mi < mainSentence.length && si < subsequenceWords.length) {
            String word = subsequenceWords[si++];
            while (mi < mainSentence.length && !mainSentence[mi].equals(word)) {
                missingWords.add(mainSentence[mi++]);
            }
            mi++;
        }

        while (mi < mainSentence.length) {
            missingWords.add(mainSentence[mi++]);
        }
        return missingWords;
    }
}
