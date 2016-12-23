package com.ashok.lang.dsa;

import java.security.InvalidParameterException;

/**
 * Implementation of Range Query Data Structure using an array of arrays.
 * Preprocessing complexity is order of n * long(n) and quering complexity is
 * order of 1 (constant time). This is useful when the range of the query can
 * be anything from 1 to n. If the min range width is fixed,
 * {@link RangeQueryBlock} is the better option for range query.
 * For two dimensional arrays use {@link Quadtree} or {@link SparseTable2D}
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */
public class SparseTable {
    private int[][] mar;

    public SparseTable(int[] ar) {
        format(ar);
    }

    public int query(int L, int R) {
        if (R >= mar[1].length || L < 0)
            throw new IndexOutOfBoundsException(L + ", " + R);

        if (L > R)
            throw new InvalidParameterException("start index can not be after end index: " + L + ", " + R);

        int half = Integer.highestOneBit(R + 1 - L);
        return operation(mar[half][L], mar[half][R + 1 - half]);
    }

    private void format(int[] ar) {
        mar = new int[ar.length + 1][];
        mar[1] = ar;
        int bit = 2;
        while (bit < mar.length) {
            int half = bit >>> 1;
            mar[bit] = new int[ar.length - bit + 1];
            for (int i = 0; i <= ar.length - bit; i++) {
                mar[bit][i] = operation(mar[half][i], mar[half][i + half]);
            }
            bit <<= 1;
        }
    }

    /**
     * Single operation for query types.
     * @param a
     * @param b
     * @return
     */
    public int operation(int a, int b) {
        return Math.max(a, b);
    }
}
