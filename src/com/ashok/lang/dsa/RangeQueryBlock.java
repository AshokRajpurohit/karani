package com.ashok.lang.dsa;

/**
 * This class is to query min or max in a given range when the range is
 * always more than block size.
 * if the range size is always equal to block size then querying can be done
 * in constant time. for small block size it's better to use
 * {@link SparseTable}.
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class RangeQueryBlock {
    private int[] qar, rar; // qar is for left to right and rar is for right to left.
    private int block;

    public RangeQueryBlock(int[] ar) {
        this(ar, (int) Math.sqrt(ar.length));
    }

    public RangeQueryBlock(int[] ar, int k) {
        block = k;
        qar = new int[ar.length];
        rar = new int[ar.length];

        process(ar);
    }

    private int query(int L, int R) {
        int res = qar[R];
        int i = L - L % block + block - 1;

        for (; i < R; i += block)
            res = operation(qar[i], res);

        return operation(res, rar[L]);
    }

    private void process(int[] ar) {
        qar[0] = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (i % block == 0)
                qar[i] = ar[i];
            else
                qar[i] = operation(ar[i], qar[i - 1]);
        }

        rar[ar.length - 1] = ar[ar.length - 1];
        for (int i = ar.length - 2; i >= 0; i--) {
            if (i % block == block - 1)
                rar[i] = ar[i];
            else
                rar[i] = operation(ar[i], rar[i + 1]);
        }
    }

    private int operation(int a, int b) {
        return a > b ? a : b;
    }
}
