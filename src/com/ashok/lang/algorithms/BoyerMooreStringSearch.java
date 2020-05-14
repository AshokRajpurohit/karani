package com.ashok.lang.algorithms;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BoyerMooreStringSearch implements StringSearch {
    final String ref;

    public BoyerMooreStringSearch(final String ref) {
        this.ref = ref;
    }

    @Override
    public int next() {
        return -1;
    }
}
