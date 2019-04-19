/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.paypal;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Paypal {
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
            int n = in.readInt(), m = in.readInt();
            String[] ar = in.readLineArray(n), br = in.readLineArray(m);
            List<String> al = new LinkedList<>(), bl = new LinkedList<>();
            for (String e : ar) al.add(e);
            for (String e : br) bl.add(e);
            testQueries(al, bl);
            out.println(in.read());
            out.flush();
        }
    }

    public static void testQueries(List<String> sentences, List<String> queries) {
        StringBuilder sb = new StringBuilder();
        String[][] sentenceWords = sentences.stream()
                .map(t -> {
                    String[] ar = t.split(" ");
                    Arrays.sort(ar);
                    return ar;
                }).toArray(k -> new String[k][]);

        Object[] maps = Arrays.stream(sentenceWords).map(strings -> {
            Map<String, Integer> map = new HashMap<>();
            for (String e : strings) {
                if (map.containsKey(e))
                    map.put(e, map.get(e) + 1);
                else
                    map.put(e, 1);
            }
            return map;
        }).toArray();
        queries.forEach(q -> {
            int index = 0;
            boolean found = false;
            for (Object map : maps) {
                int count = count((Map<String, Integer>) map, q.split(" "));
                found = found || (count != 0);
                while (count > 0) {
                    count--;
                    sb.append(index).append(' ');
                }
                index++;
            }

            if (!found) sb.append("-1");
            sb.append('\n');
        });

        System.out.println(sb);
    }

    private static int count(Map<String, Integer> sentenceWords, String[] phraseWords) {
        int count = Integer.MAX_VALUE;
        int pi = 0;
        for (String phraseWord : phraseWords) {
            if (sentenceWords.containsKey(phraseWord))
                count = Math.min(count, sentenceWords.get(phraseWord));
            else return 0;
        }

        return count;
    }

    private static int count(String[] ar, String val) {
        int count = 0;
        for (String e : ar)
            if (e.equals(val)) count++;

        return count;
    }
}
