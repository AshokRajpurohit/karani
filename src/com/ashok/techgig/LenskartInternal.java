/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Multiple Problems
 * Link: ashok Ankit
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LenskartInternal {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        LenskartInternal a = new LenskartInternal();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    final static class CardCombination {
        private static void solve() throws IOException {
            while (true) {
                out.println(in.read());
                out.flush();
            }
        }
    }

    final static class Cards implements Comparable<Cards> {
        final boolean[] cards;

        Cards(boolean[] ar) {
            cards = ar.clone();
        }

        public int hashCode() {
            int hashCode = 1;

            for (boolean b: cards) {
                hashCode = (hashCode << 1) + (b ? 1 : 0);
                hashCode %= 1000000;
            }

            return hashCode;
        }

        @Override
        public int compareTo(Cards c) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != c.cards[i])
                    return cards[i] ? 1 : -1;
            }

            return 0;
        }

        public boolean equals(Object o) {
            Cards c = (Cards) o;
            return compareTo(c) == 0;
        }

    }
}
