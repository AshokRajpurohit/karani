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
    private boolean min;

    public RangeQueryBlock(int[] ar) {
        this(ar, (int)Math.sqrt(ar.length), true);
    }

    public RangeQueryBlock(int[] ar, int k) {
        this(ar, k, true);
    }

    public RangeQueryBlock(int[] ar, boolean min) {
        this(ar, (int)Math.sqrt(ar.length), min);
    }

    public RangeQueryBlock(int[] ar, int k, boolean min) {
        block = k;
        this.min = min;
        qar = new int[ar.length];
        rar = new int[ar.length];

        if (min)
            processMin(ar);
        else
            processMax(ar);
        return;
    }

    public int query(int L, int R) {
        if (R >= qar.length || L < 0)
            throw new IndexOutOfBoundsException(L + ", " + R);
        if (min)
            return qMin(L, R);
        return qMax(L, R);
    }

    public String print(int len) {
        if (len > qar.length)
            return "Out Of Range.";

        if (min)
            return printMin(len);
        return printMax(len);
    }

    private int qMin(int L, int R) {
        int res = qar[R];
        int i = L - L % block + block - 1;

        for (; i < R; i += block)
            res = Math.min(qar[i], res);

        return Math.min(res, rar[L]);
    }

    private int qMax(int L, int R) {
        int res = qar[R];
        int i = L - L % block + block - 1;

        for (; i < R; i += block)
            res = Math.max(qar[i], res);

        return Math.max(res, rar[L]);
    }

    /**
     * formats qar and rar for max query.
     * @param ar
     */
    private void processMax(int[] ar) {
        qar[0] = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (i % block == 0)
                qar[i] = ar[i];
            else
                qar[i] = Math.max(ar[i], qar[i - 1]);
        }

        rar[ar.length - 1] = ar[ar.length - 1];
        for (int i = ar.length - 2; i >= 0; i--) {
            if (i % block == block - 1)
                rar[i] = ar[i];
            else
                rar[i] = Math.max(ar[i], ar[i + 1]);
        }
    }

    private void processMin(int[] ar) {
        qar[0] = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (i % block == 0)
                qar[i] = ar[i];
            else
                qar[i] = Math.min(ar[i], qar[i - 1]);
        }

        rar[ar.length - 1] = ar[ar.length - 1];
        for (int i = ar.length - 2; i >= 0; i--) {
            if (i % block == block - 1)
                rar[i] = ar[i];
            else
                rar[i] = Math.min(ar[i], ar[i + 1]);
        }
    }

    private String printMin(int len) {
        StringBuilder sb = new StringBuilder((qar.length + 1 - len) << 1);
        for (int i = 0, j = len - 1; j < qar.length; i++, j++)
            sb.append(qMin(i, j)).append(' ');

        return sb.toString();
    }

    private String printMax(int len) {
        StringBuilder sb = new StringBuilder((qar.length + 1 - len) << 1);
        for (int i = 0, j = len - 1; j < qar.length; i++, j++)
            sb.append(qMax(i, j)).append(' ');

        return sb.toString();
    }
}
