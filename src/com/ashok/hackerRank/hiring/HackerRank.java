/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: HackerRank SDE Hiring Challenge - Java
 * Link: Email Invite
 * Candidate: Ashok Rajpurohit
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackerRank {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        HackerRank a = new HackerRank();
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
            int[] ar = in.readIntArray(n);
            int p = in.readInt();
            out.println(BuyingShowTickets.waitingTime(ar, p));
            out.flush();
        }
    }

    final static class ArrangeWords {
        static String arrange(String sentence) {
            String[] words = sentence.split("[\\s\\.]");
            words[0] = words[0].toLowerCase();
            String[] formattedWords = Arrays.stream(words)
                    .filter(s -> s.length() > 0) // remove empty strings.
                    .sorted((a, b) -> a.length() - b.length())
                    .toArray(t -> new String[t]);

            char[] firstWordAr = formattedWords[0].toCharArray();
            firstWordAr[0] = Character.toUpperCase(firstWordAr[0]);
            formattedWords[0] = new String(firstWordAr);
            return String.join(" ", formattedWords) + ".";
        }
    }

    final static class BuyingShowTickets {
        static long waitingTime(int[] tickets, int p) {
            int jessiTickets = tickets[p];
            long time = jessiTickets;
            for (int i = 0; i < p; i++)
                time += Math.min(jessiTickets, tickets[i]);

            if (jessiTickets == 1)
                return time;

            for (int i = p + 1; i < tickets.length; i++)
                time += Math.min(jessiTickets - 1, tickets[i]);

            return time;
        }
    }
}
